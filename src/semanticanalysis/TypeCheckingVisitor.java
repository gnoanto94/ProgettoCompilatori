package semanticanalysis;

import syntaxanalysis.StackEnv;
import syntaxanalysis.SymbolTable;
import tree_nodes.*;

import java.util.ArrayList;

public class TypeCheckingVisitor implements Visitor {

    private boolean singleMainDeclaration = false; //flag per controllare se viene usata più di una procedura main
    //Si utilizza nullType nelle occorrenze del valore null in maniera da potergli attribuire un valore
    //così da poter semplificare la fase di Type Checking in presenza di null
    private String nullType;
    private final String NULL = "null";

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

        throw new Exception("Errore Semantico: variabile " +i.getIdEntry()+ " non dichiarata");
    }

    @Override
    public Object visit(StringConstLeaf s) {
        return ResultTypeOp.STRING;
    }

    @Override
    public Object visit(NullLeaf n) {
        return NULL;
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
            throw new Exception("Errore Semantico: Type Mismatch in assegnazione");
        }

        return null; //si restituisce null in quanto SimpleAssign non ha un tipo di ritorno
    }

    private String getCpTypeForCondition(CallProcOp c) throws Exception {
        ArrayList<ResultTypeOp> cpReturns;
        cpReturns = ((SymbolTable.ProcRow) c.getId().getTableEntry()).getReturnTypes();
        if(cpReturns.size() != 1){
            throw new Exception("Errore Semantico: Impossibile utilizzare la procedura " +
                    c.getId().getIdEntry() + " come condizione");
        }
        return cpReturns.get(0).getValue();
    }

    @Override
    public Object visit(IfOp i) throws Exception{

        String type;
        if(i.getExpr() instanceof CallProcOp){
            type = getCpTypeForCondition((CallProcOp) i.getExpr());
        } else {
            type = (String) ((Visitable) i.getExpr()).accept(this);
        }

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
        //si deve chiamare la accept per effettuare il type checking a tutti gli statement nel corpo dell'else
        if(i.getElseOp() != null){ //se esiste la clausola else
            i.getElseOp().accept(this);
        }
        return null; //si restituisce null in quanto lo statement if non ha un tipo di ritorno
    }

    @Override
    public Object visit(ElifOp e) throws Exception{

        String type;
        if(e.getExpr() instanceof CallProcOp){
            type = getCpTypeForCondition((CallProcOp) e.getExpr());
        } else {
            type = (String) ((Visitable) e.getExpr()).accept(this);
        }

        ArrayList<StatOp> statList = e.getStatList();

        if(!(type.equals(ResultTypeOp.BOOL))){
            throw new Exception("Errore Semantico: la condizione dell'elif deve essere un'espressione booleana ");
        }

        //si deve chiamare la accept per effettuare il type checking a tutti gli statement nel corpo dell'elif
        for(StatOp s : statList){
            s.accept(this);
        }

        return null; //si restituisce null in quanto lo statement elif non ha un tipo di ritorno
    }

    @Override
    public Object visit(ElseOp e) throws Exception{
        ArrayList<StatOp> statList = e.getStatList();
        //si deve chiamare la accept per effettuare il type checking a tutti gli statement nel corpo dell'elif
        for(StatOp s : statList){
            s.accept(this);
        }
        return null; //si restituisce null in quanto lo statement else non ha un tipo di ritorno
    }

    @Override
    public Object visit(WhileOp w) throws Exception{

        String type;
        if(w.getExpr() instanceof CallProcOp){
            type = getCpTypeForCondition((CallProcOp) w.getExpr());
        } else {
            type = (String) ((Visitable) w.getExpr()).accept(this);
        }

        ArrayList<StatOp> statList = w.getStatList();

        if(!(type.equals(ResultTypeOp.BOOL))){
            throw new Exception("Errore Semantico: la condizione del while deve essere un'espressione booleana ");
        }

        //si deve chiamare la accept per effettuare il type checking a tutti gli statement nel corpo del while
        if(statList != null){ //while potrebbe non avere una statlist
            for(StatOp s : statList){
                s.accept(this);
            }
        }
        //si effettua il type checking anche per il corpo del do
        w.getDoOp().accept(this);
        return null; //si restituisce null in quanto lo statement while non ha un tipo di ritorno
    }

    @Override
    public Object visit(DoOp d) throws Exception{

        ArrayList<StatOp> statList = d.getStatList();
        //Bisogna effettuare il type checking per tutti gli statement nel corpo del do
        for(StatOp s : statList){
            s.accept(this);
        }
        return null; //si restituisce null in quanto lo statement do non ha un tipo di ritorno
    }

    @Override
    public Object visit(ReadOp r) throws Exception {
        //Si verifica che i parametri passati alla readln siano stati dichiarati prima del loro utilizzo
        for(IdLeaf i: r.getIdList()){
            //Si effettua il controllo con null, in quanto il parser metterà null come entry della symbol table
            //se non trova la dichiarazione della variabile.
            if(i.getTableEntry() == null){
                throw new Exception("Errore Semantico: variabile " +i.getIdEntry()+ " non dichiarata in readln");
            }
        }
        return null; //si restituisce null in quanto lo statement readln non ha un tipo di ritorno
    }

    @Override
    public Object visit(WriteOp w) throws Exception {

        ArrayList<ResultTypeOp> resultTypeList;

        for(Expr e: w.getExprList()){
            //Si effettua un controllo solo sugli elementi che sono variabili, in quanto è necessario assicurarsi
            //che siano state precedentemente dichiarate.
            if(e instanceof IdLeaf) {
                //Si effettua il controllo con null, in quanto il parser metterà null come entry della symbol table
                //se non trova la dichiarazione della variabile.
                if (((IdLeaf) e).getTableEntry() == null) {
                    throw new Exception("Errore Semantico: variabile " +((IdLeaf)e).getIdEntry()+ " non dichiarata in write");
                }
            }
            if(e instanceof CallProcOp){
                //Bisogna controllare se si ha a che fare con una chiamata a procedura
                //per verificare che non restituisca VOID
                resultTypeList = (ArrayList<ResultTypeOp>) ((CallProcOp) e).accept(this);
                if(resultTypeList.size() == 1){
                    //Si ha una procedura con un solo valore di ritorno, bisogna verificare che non sia void
                    if(resultTypeList.get(0).equals(ResultTypeOp.VOID)){
                        throw new Exception("Errore Semantico: write non accetta valore void");
                    }
                }
            }
            //Si effettua un controllo solo nel caso venga passato come valore "null"
            //negli altri casi (valori costanti) non è necessario effettuare alcun controllo
            if(e instanceof NullLeaf){
                throw new Exception("Errore Semantico: write non accetta valori null");
            }

        }
        return null; //si restituisce null in quanto lo statement write non ha un tipo di ritorno
    }

    @Override
    public Object visit(MultipleAssignOp m) throws Exception {

        String type1, type2;
        ArrayList<String> rightSideTypes = new ArrayList<>();
        ArrayList<Type> procTypes;
        ArrayList<IdLeaf> idList = m.getIdList();
        ArrayList<Expr> exprList = m.getExprList();//exprList corrisponde ai valori nella parte destra dell'assegnazione
        int exprListSize = 0;

        for(Expr e : exprList){
            if(e instanceof CallProcOp){
                //Nel caso in cui la exprList contiene delle chiamate a procedure bisogna aumentare la size
                //in base al numero di valori di ritorno restituiti dalle procedure in questione
                procTypes = (ArrayList<Type>) ((CallProcOp) e).accept(this);
                exprListSize += procTypes.size();
                for(Type t : procTypes){
                    rightSideTypes.add(t.getValue());
                }
            } else {
                //Per tutti gli altri. Nel caso di null viene inserito "null"
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

            if(type2.equals(NULL)){ //il null fa match con tutti i tipi, quindi il type checking non darà errore.
                type2 = type1;
            }

            if(!(type1.equals(type2))){
                throw new Exception("Errore Semantico: type mismatch in assegnazione multipla");
            }
        }

        return null; //si restituisce null in quanto MultipleAssignOp non ha un tipo di ritorno
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

        ArrayList<Type> formalParametersTypes;
        ArrayList<String> actualParametersTypes = new ArrayList<>();
        ArrayList<Type> procRetTypes;
        String type1, type2;
        int j = 0; //indice usato per attribuire un tipo al valore null per type mismatch

        if(procRow != null){
            //È necessario verificare che i tipi dei parametri attuali corrispondano a quelli dei parametri formali
            formalParametersTypes = procRow.getParamTypes(); //tipi dei parametri formali
            //Si calcola il numero di parametri attuali passati alla procedura
            if(c.getExprList() != null){ //verifico che la procedura abbia parametri
                for(Expr e: c.getExprList()){

                    if(e instanceof CallProcOp){ //se alla procedura viene passato come parametro un'altra procedura
                        procRetTypes = ((ArrayList<Type>) ((CallProcOp) e).accept(this));
                        for(Type t: procRetTypes){
                            actualParametersTypes.add(t.getValue());
                        }
                    }
                    //Nel caso in cui all'interno della exprList è presente null, dobbiamo attribuirgli un tipo adatto
                    //altrimenti si avrebbe type mismatch
                    else if(e instanceof NullLeaf){
                        nullType = formalParametersTypes.get(j).getValue();
                        actualParametersTypes.add(nullType);
                    } else {
                        actualParametersTypes.add((String) ((Visitable) e).accept(this));
                    }

                    j++;
                }//end for
            }

            if(formalParametersTypes != null) {//viene eseguito solo se la procedura prevede parametri
                if (formalParametersTypes.size() != actualParametersTypes.size()) {
                    throw new Exception("Errore Semantico: il numero di parametri passato alla procedura " +
                            c.getId().getIdEntry() + " non coincide con con quello della firma.");
                }

                for (int i = 0; i < formalParametersTypes.size(); i++) {
                    type1 = formalParametersTypes.get(i).getValue();
                    type2 = actualParametersTypes.get(i);
                    if (!(type1.equals(type2))) {
                        throw new Exception("Errore Semantico: i tipi dei parametri attuali non coincidono con quelli " +
                                "dei parametri formali nella procedura "+c.getId().getIdEntry());
                    }
                } //end for
            }
            //Se la procedura è definita, alla fine bisogna recuperare l'array
            //con i tipi di ritorno dalla entry della symbol table
            return procRow.getReturnTypes();
        }
        //Se NON è definita si lancia un'eccezione.
        throw new Exception("Errore Semantico: Procedura "+c.getId().getIdEntry()+" non definita");
    }

    @Override
    public Object visit(IdListInitOp i) throws Exception{
        for(IdListInit id : i.getIdListInit()){
            ((Visitable) id).accept(this);
        }
        return null; //si restituisce null in quanto idListInitOp non ha un tipo di ritorno
    }

    @Override
    public Object visit(ParamDeclOp p) {
        //non viene effettuato alcun controllo perchè si ha a che fare con dichiarazioni di parametri
        //e conseguentemente il metodo visit in questione non viene mai chiamato perchè i tipi di ritorno
        //vengono recuperati dalla tabella dei simboli
        return null; //si restituisce null in quanto ParamDeclOp non ha un tipo di ritorno
    }

    @Override
    public Object visit(ResultTypeOp r) {
        return r.getValue();
    }

    @Override
    public Object visit(ProcOp p) throws Exception{

        //Salvo il nome della procedura corrente per i messaggi di errore
        String currentProcName = p.getId().getIdEntry();

        //resultTypeList fa riferimento ai tipi di ritorno nella firma della procedura
        ArrayList<ResultTypeOp> resultTypeList = p.getResultTypeList();

        //Controllo dell'unicità della procedura main
        if(!(singleMainDeclaration)){
            if(p.getId().getIdEntry().equals("main")){
                singleMainDeclaration = true;
                //In Toy si è deciso che la procedura main ha la seguente firma:
                //proc main() void
                if(resultTypeList.size() == 1){
                    if(!(resultTypeList.get(0).equals(ResultTypeOp.VOID))){
                        throw new Exception("Errore Semantico: la procedura main deve avere la seguente " +
                                "firma:\nproc main() void");
                    }
                }
            }
        }else{
            if(p.getId().getIdEntry().equals("main")){
                throw new Exception("Errore Semantico: la procedura main deve essere unica");
            }
        }


        //returnTypes fa riferimento ai tipi di ritorno delle espressioni alla fine della procedura
        if(p.getProcBody().getReturnExprs() != null){ //caso in cui la funzione non ha come tipo di ritorno void
            //non viene verificato se si ha a che fare con un ArrayList<String> perché il metodo
            //visit(ReturnExprs) (chiamato da accept) in questo visitor lo restituisce sempre
            //Nel seguente array ci sono i tipi delle espressioni dopo il simbolo di return ->
            ArrayList<String> returnTypes = (ArrayList<String>) p.getProcBody().getReturnExprs().accept(this);

            if(returnTypes.size() != resultTypeList.size()){
                throw new Exception("Errore Semantico: I valori restituiti dalla procedura non sono in numero eguale " +
                        "a quelli definiti nella firma della procedura "+currentProcName);
            }

            //si è certi che il numero di espressioni dopo il return coincidono con il numero di valori di ritorno
            //della procedura.
            //Si gestisce il caso in cui siano presenti dei valori null nelle espressioni di ritorno
            for(int i = 0; i < returnTypes.size(); i++){
                if(returnTypes.get(i).equals(NULL)){ //se c'è un valore null
                    //si sostituisce il "tipo" di null con il tipo corretto presente nella firma della procedura
                    //in modo che i controlli seguenti di type checking non diano type mismatch
                    returnTypes.get(i).replace(NULL, resultTypeList.get(i).getValue());
                }
            } //end for

            for(int i = 0; i < returnTypes.size(); i++){
                //returnTypes fa riferimento ai tipi di ritorno delle espressioni alla fine della procedura
                //resultTypeList fa riferimento ai tipi di ritorno nella firma della procedura
                if(!(returnTypes.get(i).equals(resultTypeList.get(i).accept(this)))){
                    throw new Exception("Errore Semantico: Type Mismatch nella procedura "+currentProcName+
                            " - i tipi di ritorno non coincidono con quelli della firma");
                }
            }
        } else {
            if(!(resultTypeList.size() == 1 && resultTypeList.get(0).getValue().equals(ResultTypeOp.VOID))){
                throw new Exception("Errore Semantico: valore di ritorno mancante per la procedura "+currentProcName);
            }
        }

        p.getProcBody().accept(this);
        return null; //si restituisce null in quanto una procedura non ha un tipo di ritorno
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

        return null; //si restituisce null in quanto ProcOpBody non ha un tipo di ritorno
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

        //Bisogna verificare che le procedure utilizzate all'interno del programma siano state tutte definite.
        SymbolTable st = StackEnv.top().getTable();
        SymbolTable.ProcRow current;
        for(SymbolTable.SymbolTableRow s: st.getSymTab()){
            if(s instanceof SymbolTable.ProcRow){
                current = (SymbolTable.ProcRow) s;
                if(current.isFref()){
                    throw new Exception("Errore Semantico: Procedura "+current.getLessema()+" non definita");
                }
            }
        }
        return null; //si restituisce null in quanto Program non ha un tipo di ritorno
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
        return null; //si restituisce null in quanto varDeclOp non ha un tipo di ritorno
    }

    private Object optype1(Operator op, Expr first) throws Exception{
        String type;

        //Si ricava il tipo dell'operando
        //È necessario effettuare una differenziazione per le chiamate a procedura in quanto possono avere
        // più tipi di ritorno.
        if(first instanceof CallProcOp){
            type = getCpReturnType((CallProcOp) first);
        } else {
            type = (String) ((Visitable) first).accept(this);
        }

        if(type.equals(NULL)){
            throw new Exception("Errore Semantico: l'operazione unaria NON accetta come operando un valore null");
        }

        switch (op) {
            case UMINUS:
                //Per poter utilizzare il meno unario, è necessario che l'operando sia di tipo integer o float.
                //Se l'operando NON è di tipo integer o float, si lancia un'eccezione.
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
        throw new Exception("Errore Semantico in operatore unario");
    }


    private Object optype2(Operator op, Expr first, Expr second) throws Exception {

        String type1, type2;

        //Si ricavano i tipi degli operandi
        //È necessario effettuare una differenziazione per le chiamate a procedura in quanto possono avere
        // più tipi di ritorno.
        if(first instanceof CallProcOp){
            type1 = getCpReturnType((CallProcOp) first);
        } else {
            type1 = (String)((Visitable) first).accept(this);
        }

        if(second instanceof CallProcOp){
            type2 = getCpReturnType((CallProcOp) second);
        } else {
            type2 = (String) ((Visitable) second).accept(this);
        }

        if(type1.equals(NULL) && type2.equals(NULL)){
            throw new Exception("Errore Semantico: impossibile eseguire operazione binaria con entrambi gli operandi null");
        }

        switch (op) {
            //operazioni aritmetiche
            case SUM, SUB, MUL, DIV:

                //Per poter effettuare un'operazione aritmetica è necessario che i due operandi
                //siano integer o float

                //Se il primo operando NON è di tipo integer o float, si lancia un'eccezione
                //È implicito il controllo su null
                if (!(type1.equals(ResultTypeOp.INT) || type1.equals(ResultTypeOp.FLOAT))) {
                    throw new Exception("Errore Semantico: tipo non valido per l'operando " + first);
                }

                //Il primo operando è del tipo corretto, si passa a verificare il secondo.
                //Se il secondo operando NON è di tipo integer o float, si lancia un'eccezione
                //È implicito il controllo su null
                if (!(type2.equals(ResultTypeOp.INT) || type2.equals(ResultTypeOp.FLOAT))) {
                    throw new Exception("Errore Semantico: tipo non valido per l'operando " + second);
                }

                //Prima di procedere oltre, è necessario effettuare una verifica solo per l'operando DIV
                //per escludere che si stia tentando una divisione intera per zero (costante, e.g. 100/0).
                if (op == Operator.DIV) {
                    if ((type1.equals(ResultTypeOp.INT)) && (type2.equals(ResultTypeOp.INT))) {
                        //se second non è una variabile (il cui valore è noto solo a runtime) allora, verifichiamo
                        //che non sia zero.
                        if (!(second instanceof IdLeaf)) {
                            IntConstLeaf s = (IntConstLeaf) second;
                            int v = s.getValue();
                            if (v == 0) {
                                throw new Exception("Errore Semantico: Divisione intera per zero sconosciuta.");
                            }
                        }
                    }
                }

                //Ora è possibile procedere per tutte le altre operazioni.
                //A questo punto i due operandi sono del tipo corretto e quindi è necessario solo verificare
                //se abbiano lo stesso tipo o meno
                if (type1.equals(type2)) {
                    return type1; //si restituisce il primo in quanto uguali
                }

                //nel caso in cui i tipi del primo e del secondo operando sono diversi,
                //vale quello "più grande", ossia float
                return ResultTypeOp.FLOAT;

            //operazioni logiche
            case AND, OR:

                //Per poter effettuare un'operazione logica è necessario che i due operandi siano di tipo bool
                //Se il primo operando NON è di tipo bool, si lancia un'eccezione
                //È implicito il controllo su null
                if(!type1.equals(ResultTypeOp.BOOL)){
                    throw new Exception("Errore Semantico: tipo non valido per l'operando " + first);
                }

                //Il primo operando è del tipo corretto, si passa a verificare il secondo.
                //Se il secondo operando NON è di tipo bool, si lancia un'eccezione
                //È implicito il controllo su null
                if(!type2.equals(ResultTypeOp.BOOL)){
                    throw new Exception("Errore Semantico: tipo non valido per l'operando " + second);
                }

                return ResultTypeOp.BOOL;

            case EQ,NE,GT,GE,LT,LE:

                //Per poter effettuare una comparazione, è necessario che i due operandi siano di tipo integer, float
                //oppure string.
                //Se il primo operando NON è di tipo integer, float o string, si lancia un'eccezione
                //È implicito il controllo su null
                if (!(type1.equals(ResultTypeOp.INT) || type1.equals(ResultTypeOp.FLOAT) ||
                        type1.equals(ResultTypeOp.STRING) || type1.equals(NULL))) {
                    throw new Exception("Errore Semantico: tipo non valido per l'operando " + first);
                }

                //A questo punto il primo operando è del tipo corretto, si passa a verificare il secondo.
                //Se il secondo operando NON è di tipo integer, float o string, si lancia un'eccezione
                //È implicito il controllo su null
                if (!(type2.equals(ResultTypeOp.INT) || type2.equals(ResultTypeOp.FLOAT) ||
                        type1.equals(ResultTypeOp.STRING) || type2.equals(NULL))) {
                    throw new Exception("Errore Semantico: tipo non valido per l'operando " + second);
                }

                //È necessario effettuare ulteriori controlli se il tipo del primo operando è string.
                if (type1.equals(ResultTypeOp.STRING)) {
                    //in questo caso, in quanto il primo operando è string, per effettuare la comparazione,
                    //è strettamente necessario che anche il secondo operando sia di tipo string.
                    if (type2.equals(ResultTypeOp.STRING) || type2.equals(NULL)) {
                        return ResultTypeOp.BOOL;
                    }
                    //altrimenti si lancia un'eccezione
                    throw new Exception("Errore Semantico: Type Mismatch in operazione binaria");
                }

                //Se il primo operando non è di tipo string, allora effettuiamo il controllo sul secondo
                if (type2.equals(ResultTypeOp.STRING)) {
                    if(type1.equals(NULL)){ //l'unico tipo accettato per type1, se type2 è string, è null
                        return ResultTypeOp.BOOL;
                    }
                    //in questo caso si lancia un'eccezione in quanto non sono entrambi string
                    throw new Exception("Errore Semantico: Type Mismatch in operazione binaria");
                }

                //negli altri casi le comparazioni sono ammesse e il tipo risultante sarà bool.
                return ResultTypeOp.BOOL;

        }//end switch
        //caso generale, non dovrebbe mai accadere.
        throw new Exception("Errore Semantico in operazione binaria");
    }

    private String getCpReturnType(CallProcOp p) throws Exception {
        ArrayList<ResultTypeOp> resultTypeOp = (ArrayList<ResultTypeOp>) p.accept(this);

        ResultTypeOp rt;
        //si può effettuare la somma solo se la procedura ha un unico tipo di ritorno
        if(resultTypeOp.size() != 1){
            throw new Exception("Errore Semantico: la procedura "+p.getId().getIdEntry()+" restituisce più tipi di ritorno");
        }
        rt = resultTypeOp.get(0);

        //Non è possibile effettuare operazioni in cui uno degli operandi è void
        if(rt.equals(ResultTypeOp.VOID)){
            throw new Exception("Errore Semantico: non è possibile effettuare un'operazione con operando void");
        }

        //In tutti gli altri casi viene semplicemente restituito il valore di ritorno della procedura
        return rt.getValue();
    }
}
