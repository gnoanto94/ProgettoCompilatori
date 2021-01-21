package tree;

import syntaxanalysis.SymbolTable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TypeCheckingVisitor implements Visitor{

    private boolean singleMainDeclaration = false; //flag per controllare se viene usata più di una procedura main

    /* Le visit dei nodi foglia ritornano sempre il loro type*/
    @Override
    public Object visit(FloatConstLeaf f)  {
        return Type.FLOAT;
    }

    @Override
    public Object visit(IntConstLeaf i) {
        return Type.INT;
    }

    @Override
    public Object visit(IdLeaf i) throws Exception{
        SymbolTable.SymbolTableRow row = i.getTableEntry();

        if (row instanceof SymbolTable.VarRow){
            return ((SymbolTable.VarRow) row).getVarType().getValue();
        }

        if (row instanceof SymbolTable.ProcRow){
            return ((SymbolTable.ProcRow) row).getReturnTypes();
        }

        throw new Exception("Errore Semantico: Utilizzo di variabile non dichiarata");
    }

    @Override
    public Object visit(StringConstLeaf s) {
        return ResultTypeOp.STRING;
    }

    @Override
    public Object visit(NullLeaf n) {
        return null;
    }

    @Override
    public Object visit(TrueLeaf t) {
        return ResultTypeOp.BOOL;
    }

    @Override
    public Object visit(FalseLeaf f) {
        return ResultTypeOp.BOOL;
    }

    @Override
    public Object visit(PlusOp p) throws Exception {
        return optype2(Operator.SUM, p.getE1(), p.getE2());
    }

    @Override
    public Object visit(MinusOp m) throws Exception {
        return optype2(Operator.SUB, m.getE1(), m.getE2());
    }

    @Override
    public Object visit(UMinusOp u) throws Exception {
        return optype1(Operator.UMINUS, u.getE1());
    }

    @Override
    public Object visit(MultOp m) throws Exception {
        return optype2(Operator.MUL, m.getE1(), m.getE2());
    }

    @Override
    public Object visit(DivOp d) throws Exception{
        return optype2(Operator.DIV, d.getE1(), d.getE2());
    }

    @Override
    public Object visit(AndOp a) throws Exception {
        return optype2(Operator.AND, a.getE1(), a.getE2());
    }

    @Override
    public Object visit(OrOp o) throws Exception{
        return optype2(Operator.OR, o.getE1(), o.getE2());
    }

    @Override
    public Object visit(NotOp n) throws Exception {
        return optype1(Operator.NOT, n.getE1());
    }

    @Override
    public Object visit(LtOp l) throws Exception {
        return optype2(Operator.LT, l.getE1(), l.getE2());
    }

    @Override
    public Object visit(LeOp l) throws Exception {
        return optype2(Operator.LE, l.getE1(), l.getE2());
    }

    @Override
    public Object visit(GtOp g) throws Exception {
        return optype2(Operator.GT, g.getE1(), g.getE2());
    }

    @Override
    public Object visit(GeOp g) throws Exception {
        return optype2(Operator.GE, g.getE1(), g.getE2());
    }

    @Override
    public Object visit(NeOp n) throws Exception {
        return optype2(Operator.NE, n.getE1(), n.getE2());
    }

    @Override
    public Object visit(EqOp e) throws Exception {
        return optype2(Operator.EQ, e.getE1(), e.getE2());
    }

    @Override
    public Object visit(SimpleAssignOp s) throws Exception {

        String type1, type2;
        type1 = (String) s.getId().accept(this);
        type2 = (String) ((Visitable) s.getExpr()).accept(this);
        if(!type1.equals(type2)){
            throw new Exception("Errore Semantico: Type Mismatch in Simple Assign");
        }

        return ResultTypeOp.VOID; //si restituisce void in quanto lo statement readln non ha un tipo di ritorno
    }

    @Override
    public Object visit(IfOp i) throws Exception{

        String type = (String) ((Visitable) i.getExpr()).accept(this);
        ArrayList<ElifOp> elifList = i.getElifList();
        ArrayList<StatOp> statList = i.getStatList();

        if(!(type.equals(ResultTypeOp.BOOL))){
            throw new Exception("Errore Semantico: la condizione dell'if deve essere un'espressione booleana ");
        }

        //si deve chiamare la accept per effettuare il type checking a tutti gli statement nel corpo dell'if
        for(StatOp s : statList){
            s.accept(this);
        }

        if(elifList != null){
            //si controlla se esiste una o più elif così da richiamare la accept per ognuna di esse
            for(ElifOp e : elifList){
                e.accept(this);
            }
        }
        return ResultTypeOp.VOID; //si restituisce void in quanto lo statement if non ha un tipo di ritorno
    }

    @Override
    public Object visit(ElifOp e) throws Exception{

        String type = (String) ((Visitable) e.getExpr()).accept(this);
        ArrayList<StatOp> statList = e.getStatList();

        if(!(type.equals(ResultTypeOp.BOOL))){
            throw new Exception("Errore Semantico: la condizione dell'elif deve essere un'espressione booleana ");
        }

        //si deve chiamare la accept per effettuare il type checking a tutti gli statement nel corpo dell'elif
        for(StatOp s : statList){
            s.accept(this);
        }

        return ResultTypeOp.VOID; //si restituisce void in quanto lo statement elif non ha un tipo di ritorno
    }

    @Override
    public Object visit(ElseOp e) throws Exception{
        ArrayList<StatOp> statList = e.getStatList();
        //si deve chiamare la accept per effettuare il type checking a tutti gli statement nel corpo dell'elif
        for(StatOp s : statList){
            s.accept(this);
        }
        return ResultTypeOp.VOID; //si restituisce void in quanto lo statement else non ha un tipo di ritorno
    }

    @Override
    public Object visit(WhileOp w) throws Exception{

        String type = (String) ((Visitable) w.getExpr()).accept(this);
        ArrayList<StatOp> statList = w.getStatList();

        if(!(type.equals(ResultTypeOp.BOOL))){
            throw new Exception("Errore Semantico: la condizione dell'if deve essere un'espressione booleana ");
        }

        //si deve chiamare la accept per effettuare il type checking a tutti gli statement nel corpo del while

        if(statList != null){ //while potrebbe non avere una statlist
            for(StatOp s : statList){
                s.accept(this);
            }
        }
        //si effettua il type checking anche per il corpo del do
        w.getDoOp().accept(this);
        return ResultTypeOp.VOID; //si restituisce void in quanto lo statement while non ha un tipo di ritorno
    }

    @Override
    public Object visit(DoOp d) throws Exception{

        ArrayList<StatOp> statList = d.getStatList();
        //Bisogna effettuare il type checking per tutti gli statement nel corpo del do
        for(StatOp s : statList){
            s.accept(this);
        }
        return ResultTypeOp.VOID; //si restituisce void in quanto lo statement do non ha un tipo di ritorno
    }

    @Override
    public Object visit(ReadOp r) throws Exception {
        //Si va a verificare che i parametri passati alla readln siano stati dichiarati prima del loro utilizzo
        for(IdLeaf i: r.getIdList()){
            //Si effettua il controllo con null, in quanto il parser metterà null come entry della symbol table
            //se non trova la dichiarazione della variabile.
            if(i.getTableEntry() == null){
                throw new Exception("Errore Semantico: utilizzo di variabile non dichiarata in readln");
            }
        }
        return ResultTypeOp.VOID; //si restituisce void in quanto lo statement readln non ha un tipo di ritorno
    }

    @Override
    public Object visit(WriteOp w) throws Exception {

        ArrayList<ResultTypeOp> resultTypeList;

        for(Expr e: w.getExprList()){
            //Si effettua un controllo solo sugli elementi che sono variabili, in quanto è necessario assicurarsi
            //che siano state precedentemente dichiarate.
            if(e instanceof IdLeaf){
                //Si effettua il controllo con null, in quanto il parser metterà null come entry della symbol table
                //se non trova la dichiarazione della variabile.
                if(((IdLeaf) e).getTableEntry() == null) {
                    throw new Exception("Errore Semantico: utilizzo di variabile non dichiarata in write");
                }
            if(e instanceof CallProcOp){
                // Bisogna controllare se si ha a che fare con una chiamata a procedura
                // per verificare che non restituisca VOID
                resultTypeList = (ArrayList<ResultTypeOp>) ((CallProcOp) e).accept(this);
                if(resultTypeList.size() == 1){
                    //Si ha una procedura con un solo valore di ritorno, bisogna verificare che non sia void
                    if(resultTypeList.get(0).equals(ResultTypeOp.VOID)){
                        throw new Exception("Errore Semantico: Write non accetta valore void");
                    }
                }
            }
                //negli altri casi non è necessario effettuare alcun controllo.
            }
        }
        return ResultTypeOp.VOID; //si restituisce void in quanto lo statement write non ha un tipo di ritorno
    }

    @Override
    public Object visit(MultipleAssignOp m) throws Exception {

        String type1, type2;
        ArrayList<String> rightSideTypes = new ArrayList<>();
        ArrayList<Type> procTypes;
        ArrayList<IdLeaf> idList = m.getIdList();
        ArrayList<Expr> exprList = m.getExprList();
        int exprListSize = 0;

        for(Expr e : exprList){
            if(e instanceof CallProcOp){
                //Nel caso in cui la exprList contiene delle chiamate a procedure bisogna aumentare la size
                //in base al numero di valori di ritorno ritornati dalle procedure in questione
                procTypes = (ArrayList<Type>) ((CallProcOp) e).accept(this);
                exprListSize += procTypes.size();
                for(Type t : procTypes){
                    rightSideTypes.add(t.getValue());
                }
            }else{
                rightSideTypes.add((String) ((Visitable)e).accept(this));
                exprListSize++;
            }
        }

        if(idList.size() != exprListSize){
            throw new Exception("Errore Semantico: il numero di argomenti nell'assegnazione multipla non coincide");
        }

        for (int i = 0; i < exprListSize ; i++){
            type1 = (String) idList.get(i).accept(this);
            type2 = rightSideTypes.get(i);

            if(!(type1.equals(type2))){
                throw new Exception("Errore Semantico: Type Mismatch in MultipleAssign");
            }
        }

        return ResultTypeOp.VOID; //si restituisce void in quanto lo statement readln non ha un tipo di ritorno
    }

    @Override
    public Object visit(CallProcOp c) throws Exception {
        //Bisogna verificare che la procedura chiamata sia stata definita.

        SymbolTable.ProcRow procRow = null;
        //Si recupera la symbol table entry riferita alla procedura
        SymbolTable.SymbolTableRow row = c.getId().getTableEntry();

        //Si verifica che la riga ottenuta sia quella di una procedure
        if(row instanceof SymbolTable.ProcRow){
            procRow = (SymbolTable.ProcRow) row;
        }

        //Se è definita, bisogna recuperare l'array con i tipi di ritorno dalla
        //entry della symbol table
        if(procRow != null){
            return procRow.getReturnTypes();
        }

        //Se NON è definita si lancia un'eccezione.
        throw new Exception("Errore Semantico: Procedura non definita");
    }

    @Override
    public Object visit(IdListInitOp i) throws Exception{
        for(IdListInit id : i.getIdListInit()){
            ((Visitable) id).accept(this);
        }
        return ResultTypeOp.VOID; //si restituisce void in quanto idListInit non ha un tipo di ritorno
    }

    @Override
    public Object visit(ParamDeclOp p) {
        //non viene effettuato alcun controllo perchè si ha a che fare con dichiarazioni di parametri
        //e conseguentemente il metodo visit in questione non viene mai chiamato
        return null;
    }

    @Override
    public Object visit(ResultTypeOp r) {
        return r.getValue();
    }

    @Override
    public Object visit(ProcOp p) throws Exception{

        //Controllo dell'unicità della procedura main
        if(!(singleMainDeclaration)){
            if(p.getId().getIdEntry().equals("main")){
                singleMainDeclaration = true;
            }
        }else{
            if(p.getId().getIdEntry().equals("main")){
                throw new Exception("Errore Semantico: la procedura main deve essere unica");
            }
        }

        //resultTypeList fa riferimento ai tipi di ritorno nella firma della procedura
        ArrayList<ResultTypeOp> resultTypeList = p.getResultTypeList();

        if(p.getProcBody().getReturnExprs() != null){

            //returnTypes fa riferimento ai tipi di ritorno delle espressioni alla fine della procedura
            ArrayList<String> returnTypes = (ArrayList<String>) p.getProcBody().getReturnExprs().accept(this);

            if(returnTypes.size() != resultTypeList.size()){
                throw new Exception("Errore Semantico: I valori restituiti dalla procedura non sono in numero eguale " +
                        "a quelli definiti nella firma della procedura");
            }

            for(int i = 0; i < returnTypes.size(); i++){
                if(!(returnTypes.get(i).equals(resultTypeList.get(i).accept(this)))){
                    throw new Exception("Errore Semantico: Type Mismatch in procedure - " +
                            "i tipi di ritorno non coincidono con quelli della firma");
                }
            }
        }else{
            if(!(resultTypeList.size() == 1 && resultTypeList.get(0).getValue().equals(ResultTypeOp.VOID))){
                throw new Exception("Errore Semantico: valore di ritorno mancante per la procedura");
            }
        }

        p.getProcBody().accept(this);
        return ResultTypeOp.VOID; //si restituisce void in quanto una procedura non ha un tipo di ritorno
    }

    @Override
    public Object visit(ProcOpBody p) throws Exception{
        //i controlli dei tipi di ritorno della procedura vengono già fatti all'interno della visit di procop
        ArrayList<StatOp> statList = p.getStatList();
        ArrayList<VarDeclOp> varDeclList = p.getVarDeclList();

        if(varDeclList != null){
            for(VarDeclOp v : varDeclList){
                v.accept(this);
            }
        }
        if(statList != null){
            for(StatOp s : statList){
                s.accept(this);
            }
        }

        return ResultTypeOp.VOID; //si restituisce void in quanto ProcOpBody non ha un tipo di ritorno
    }

    @Override
    public Object visit(ProgramOp p) throws Exception {
        ArrayList<VarDeclOp> varDeclList = p.getVarDeclList();
        ArrayList<ProcOp> procList = p.getProcOpList();

        //Se dichiarate, bisogna effettuare il type checking per tutte le variabili
        if(varDeclList != null){
            for(VarDeclOp v : varDeclList){
                v.accept(this);
            }
        }
        //Per ogni procedura all'interno del programma bisogna effettuare il type checking
        for(ProcOp proc : procList){
            proc.accept(this);
        }
        return ResultTypeOp.VOID; //si restituisce void in quanto program non ha un tipo di ritorno
    }

    @Override
    public Object visit(ReturnExprs r) throws Exception{
        ArrayList<String> returnTypes = new ArrayList<>();
        //Vengono recuperati tutti i tipi delle espressioni dopo il return
        for (Expr e: r.getExprList()){
            returnTypes.add((String) ((Visitable) e).accept(this));
        }
        return returnTypes;
    }

    @Override
    public Object visit(Type t) {
        return t.getValue();
    }

    @Override
    public Object visit(VarDeclOp v) throws Exception{

        v.getIdListInit().accept(this);
        return ResultTypeOp.VOID; //si restituisce void in quanto varDeclOp non ha un tipo di ritorno
    }

    private Object optype1(Operator op, Expr first) throws Exception{

        String type = (String) ((Visitable) first).accept(this);

        switch (op) {
            case UMINUS:
                //Per poter utilizzare il meno unario, è necessario che l'operando sia di tipo integer o float.

                //Se l'operando NON è di tipo integer o float, lancia eccezione.
                //È implicito il controllo su null
                if (!(type.equals(ResultTypeOp.INT) || type.equals(ResultTypeOp.FLOAT))) {
                    throw new Exception("Errore Semantico: tipo non valido per l'operando " + first);
                }

                //A questo punto l'operando è del tipo corretto e viene restituito il tipo.
                return type;

            case NOT:
                //Per poter utilizzare il not logico, è necessario che l'operando sia di tipo bool.

                //Se l'operando NON è di tipo bool, lancia eccezione
                //È implicito il controllo su null
                if(!type.equals(ResultTypeOp.BOOL)) {
                    throw new Exception("Errore Semantico: tipo non valido per l'operando " + first);
                }

               //A questo punto l'operando è del tipo corretto e viene restituito il tipo.
                return type;
        }
        //caso generale, non dovrebbe mai accadere.
        throw new Exception("Errore Semantico");
    }


    private Object optype2(Operator op, Expr first, Expr second) throws Exception {

        ArrayList<ResultTypeOp> resultTypeOp; //tipi di ritorno di CallProcOp
        String type1, type2;

        //Ricavo i tipi degli operandi
        type1 = (String)((Visitable) first).accept(this);
        type2 = (String) ((Visitable) second).accept(this);

        switch (op) {
            //operazioni aritmetiche
            case SUM, SUB, MUL, DIV:

                //Per poter effettuare un'operazione aritmetica è necessario che i due operandi
                //siano integer o float

                //Se il primo operando NON è di tipo integer o un float, lancia eccezione
                //È implicito il controllo su null
                if (!(type1.equals(ResultTypeOp.INT) || type1.equals(ResultTypeOp.FLOAT))) {
                    throw new Exception("Errore Semantico: tipo non valido per l'operando " + first);
                }

                //Il primo operando è del tipo corretto, si passa a verificare il secondo.
                //Se il secondo operando NON è di tipo integer o un float, lancia eccezione
                //È implicito il controllo su null
                if (!(type2.equals(ResultTypeOp.INT) || type2.equals(ResultTypeOp.FLOAT))) {
                    throw new Exception("Errore Semantico: tipo non valido per l'operando " + second);
                }

                //Prima di procedere oltre, è necessario effettuare una verifica solo per l'operando DIV
                //per escludere che si stia tentando una divisione per zero.
                if (op == Operator.DIV) {
                    if ((type1.equals(ResultTypeOp.INT)) && (type2.equals(ResultTypeOp.INT))) {
                        IntConstLeaf s = (IntConstLeaf) second;
                        int v = s.getValue();
                        if (v == 0) {
                            throw new Exception("Errore Semantico: Divisione intera per zero sconosciuta.");
                        }
                    }
                }

                //Ora è possibile procedere per tutte le altre operazioni.
                //A questo punto i due operandi sono del tipo corretto e quindi è necessario solo verificare
                //se abbiano lo stesso tipo oppure no
                if (type1.equals(type2)) {
                    return type1; //si restituisce il primo in quanto uno vale l'altro in quanto uguali
                }

                //nel caso in cui i tipi del primo e del secondo operando sono diversi,
                // vale quello "più grande", ossia float
                return ResultTypeOp.FLOAT;

            //operazioni logiche
            case AND, OR:

                //Per poter effettuare un'operazione logica è necessario che i due operandi siano di tipo bool

                //Se il primo operando NON è di tipo bool, lancia eccezione
                //È implicito il controllo su null
                if(!type1.equals(ResultTypeOp.BOOL)){
                    throw new Exception("Errore Semantico: tipo non valido per l'operando " + first);
                }

                //Il primo operando è del tipo corretto, si passa a verificare il secondo.
                //Se il secondo operando NON è di tipo bool, lancia eccezione.
                //È implicito il controllo su null
                if(!type2.equals(ResultTypeOp.BOOL)){
                    throw new Exception("Errore Semantico: tipo non valido per l'operando " + second);
                }

                return ResultTypeOp.BOOL;

            case EQ,NE,GT,GE,LT,LE:

                //Per poter effettuare una comparazione, è necessario che i due operandi siano di tipo integer, float
                //oppure string.
                //Se il primo operando NON è di tipo integer, float o string, lancia eccezione
                //È implicito il controllo su null
                if (!(type1.equals(ResultTypeOp.INT) || type1.equals(ResultTypeOp.FLOAT) || type1.equals(ResultTypeOp.STRING))) {
                    throw new Exception("Errore Semantico: tipo non valido per l'operando " + first);
                }

                //A questo punto il primo operando è del tipo corretto, si passa a verificare il secondo.
                //Se il secondo operando NON è di tipo integer, float o string, lancia eccezione
                //È implicito il controllo su null
                if (!(type2.equals(ResultTypeOp.INT) || type2.equals(ResultTypeOp.FLOAT) || type1.equals(ResultTypeOp.STRING))) {
                    throw new Exception("Errore Semantico: tipo non valido per l'operando " + second);
                }

                //È necessario effettuare ulteriori controlli se il tipo del primo operando è string.
                if (type1.equals(ResultTypeOp.STRING)) {
                    //in questo caso, in quanto il primo operando è string, per effettuare la comparazione,
                    //è strettamente necessario che anche il secondo operando sia di tipo string.
                    if (type2.equals(ResultTypeOp.STRING)) {
                        return ResultTypeOp.BOOL;
                    }
                    //altrimenti lancia eccezione
                    throw new Exception("Errore Semantico: Type Mismatch in operazione binaria");
                }

                //Se il primo operando non è di tipo string, allora effettuiamo il controllo sul secondo
                if (type2.equals(ResultTypeOp.STRING)) {
                    //in questo lancia eccezione in questo non sono entrambi string
                    throw new Exception("Errore Semantico: Type Mismatch in operazione binaria");
                }

                //negli altri casi le comparazioni sono ammesse e il tipo risultante sarà bool.
                return ResultTypeOp.BOOL;

        }//end switch
        //caso generale, non dovrebbe mai accadere.
        throw new Exception("Errore Semantico");
    }

    private String getCpReturnType(CallProcOp p) throws Exception {
        ArrayList<ResultTypeOp> resultTypeOp = (ArrayList<ResultTypeOp>) p.accept(this);

        ResultTypeOp rt;
        //si può effettuare la somma solo se la procedura ha un unico tipo di ritorno
        if(resultTypeOp.size() != 1){
            throw new Exception("Errore Semantico: la procedura restituisce più tipi di ritorno");
        }
        rt = resultTypeOp.get(0);

        if(rt.equals(ResultTypeOp.INT)){
            return ResultTypeOp.INT;
        }

        if(rt.equals(ResultTypeOp.FLOAT)){
            return ResultTypeOp.FLOAT;
        }

        if(rt.equals(ResultTypeOp.STRING)){
            return ResultTypeOp.STRING;
        }

        if(rt.equals(ResultTypeOp.BOOL)){
            return ResultTypeOp.BOOL;
        }

        if(rt.equals(ResultTypeOp.VOID)){
            return ResultTypeOp.VOID;
        }

        throw new Exception("Errore Semantico: il tipo di ritorno della procedura non è valido");
    }
}
