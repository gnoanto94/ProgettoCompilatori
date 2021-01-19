package tree;

import syntaxanalysis.Env;
import syntaxanalysis.StackEnv;
import syntaxanalysis.SymbolTable;
import java.util.ArrayList;

public class TypeCheckingVisitor implements Visitor{

    private Env currentTable = StackEnv.top();

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
    public Object visit(IdLeaf i) {
        SymbolTable.SymbolTableRow row = i.getTableEntry();

        if (row instanceof SymbolTable.VarRow){
            return ((SymbolTable.VarRow) row).getVarType();
        }

        if (row instanceof SymbolTable.ProcRow){
            return ((SymbolTable.ProcRow) row).getReturnTypes();
        }

        return null;
    }

    @Override
    public Object visit(StringConstLeaf s) {
        return new Type(Type.STRING);
    }

    @Override
    public Object visit(NullLeaf n) {
        return new Type();
    }

    @Override
    public Object visit(TrueLeaf t) {
        return new Type(Type.BOOL);
    }

    @Override
    public Object visit(FalseLeaf f) {
        return new Type(Type.BOOL);
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
    public Object visit(SimpleAssignOp s) {
        return null;
    }

    @Override
    public Object visit(IfOp i) {
        return null;
    }

    @Override
    public Object visit(ElifOp e) {
        return null;
    }

    @Override
    public Object visit(ElseOp e) {
        return null;
    }

    @Override
    public Object visit(WhileOp w) {
        return null;
    }

    @Override
    public Object visit(DoOp d) {
        return null;
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

        for(Expr e: w.getExprList()){
            //Si effettua un controllo solo sugli elementi che sono variabili, in quanto è necessario assicurarsi
            //che siano state precedentemente dichiarate.
            if(e instanceof IdLeaf){
                //Si effettua il controllo con null, in quanto il parser metterà null come entry della symbol table
                //se non trova la dichiarazione della variabile.
                if(((IdLeaf) e).getTableEntry() == null){
                    throw new Exception("Errore Semantico: utilizzo di variabile non dichiarata in write");
                }
                //negli altri casi non è necessario effettuare alcun controllo.
            }
        }
        return ResultTypeOp.VOID; //si restituisce void in quanto lo statement write non ha un tipo di ritorno
    }

    @Override
    public Object visit(MultipleAssignOp m) {
        return null;
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
    public Object visit(IdListInitOp i) {
        return null;
    }

    @Override
    public Object visit(ParamDeclOp p) {
        return null;
    }

    @Override
    public Object visit(ResultTypeOp r) {
        return null;
    }

    @Override
    public Object visit(ProcOp p) {

        return null;
    }

    @Override
    public Object visit(ProcOpBody p) {
        return null;
    }

    @Override
    public Object visit(ProgramOp p) {
        return null;
    }

    @Override
    public Object visit(ReturnExprs r) {
        return null;
    }

    @Override
    public Object visit(Type t) {
        return null;
    }

    @Override
    public Object visit(VarDeclOp v) {
        Type type = v.getType();
        IdListInitOp idListOp = v.getIdListInit();
        ArrayList<IdListInit> idList = idListOp.getIdListInit();
        for(IdListInit i : idList){
            if(i instanceof IdLeaf){
                ((SymbolTable.VarRow)((IdLeaf) i).getTableEntry()).setVarType(type);
            }
            if(i instanceof SimpleAssignOp){
                ((SymbolTable.VarRow)((SimpleAssignOp) i).getId().getTableEntry()).setVarType(type);
            }
        }
        return null;
    }

    private Object optype1(Operator op, Expr first) throws Exception{

        String type = null;

        //first = IntConstLeaf
        if(first instanceof IntConstLeaf) {//first è un intero
            type = (String) ((IntConstLeaf) first).accept(this);
        }
        //first = FloatConstLeaf
        if(first instanceof FloatConstLeaf){
            type = (String) ((FloatConstLeaf) first).accept(this);
        }
        //first = CallProcOp
        if(first instanceof CallProcOp){
            type = getCpReturnType((CallProcOp) first);
        }
        //first = idLeaf
        if(first instanceof IdLeaf){
            type = (String) ((IdLeaf) first).accept(this);
        }
        //first = trueLeaf
        if(first instanceof TrueLeaf){
            type = (String)((TrueLeaf) first).accept(this);
        }
        //first = falseLeaf
        if(first instanceof FalseLeaf){
            type = (String)((FalseLeaf) first).accept(this);
        }

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
        String type1 = null, type2 = null;

        //controllo dei tipi di first

        //first = TrueLeaf
        if(first instanceof TrueLeaf){
            type1 = (String)((TrueLeaf) first).accept(this);
        }
        //first = FalseLeaf
        if(first instanceof FalseLeaf){
            type1 = (String)((FalseLeaf) first).accept(this);
        }
        //first = IntConstLeaf
        if(first instanceof IntConstLeaf) {//first è un intero
            type1 = (String) ((IntConstLeaf) first).accept(this);
        }
        //first = FloatConstLeaf
        if(first instanceof FloatConstLeaf){
            type1 = (String) ((FloatConstLeaf) first).accept(this);
        }
        //first = FloatConstLeaf
        if(first instanceof StringConstLeaf){
            type1 = (String) ((StringConstLeaf) first).accept(this);
        }
        //first = CallProcOp
        if(first instanceof CallProcOp){
            type1 = getCpReturnType((CallProcOp) first);
        }
        //first = idLeaf
        if(first instanceof IdLeaf){
            type1 = (String) ((IdLeaf) first).accept(this);
        }

        //controllo dei tipi di second

        //second = TrueLeaf
        if(second instanceof TrueLeaf){
            type2 = (String)((TrueLeaf) second).accept(this);
        }

        //second = FalseLeaf
        if(second instanceof FalseLeaf){
            type2 = (String)((FalseLeaf) second).accept(this);
        }

        //second = IntConstLeaf
        if(second instanceof IntConstLeaf){
            type2 = (String) ((IntConstLeaf) second).accept(this);
        }
        //second = FloatConstLeaf
        if(second instanceof FloatConstLeaf){
            type2 = (String) ((FloatConstLeaf) second).accept(this);
        }
        //second = StringConstLeaf
        if(second instanceof StringConstLeaf){
            type2 = (String) ((StringConstLeaf) second).accept(this);
        }
        //second = CallProcOp
        if(second instanceof CallProcOp){
            type2 = getCpReturnType((CallProcOp) second);
        }
        //second = idLeaf
        if(second instanceof IdLeaf){
            type2 = (String) ((IdLeaf) second).accept(this);
        }

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
                    throw new Exception("Errore Semantico: Type Mismatch");
                }

                //Se il primo operando non è di tipo string, allora effettuiamo il controllo sul secondo
                if (type2.equals(ResultTypeOp.STRING)) {
                    //in questo lancia eccezione in questo non sono entrambi string
                    throw new Exception("Errore Semantico: Type Mismatch");
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
