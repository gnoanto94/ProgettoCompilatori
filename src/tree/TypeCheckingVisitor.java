package tree;

import syntaxanalysis.SymbolTable;

import javax.swing.tree.ExpandVetoException;
import java.util.ArrayList;

public class TypeCheckingVisitor implements Visitor{

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
    public Object visit(PlusOp p) {
        return null;
    }

    @Override
    public Object visit(MinusOp m) {
        return null;
    }

    @Override
    public Object visit(UMinusOp u) {
        return null;
    }

    @Override
    public Object visit(MultOp m) {
        return null;
    }

    @Override
    public Object visit(DivOp d) {
        return null;
    }

    @Override
    public Object visit(AndOp a) {
        Visitable left = (Visitable) a.getE1();
        Visitable right = (Visitable) a.getE2();
        relopCheckType(left,right);
        return null;
    }

    @Override
    public Object visit(OrOp o) {
        Visitable left = (Visitable) o.getE1();
        Visitable right = (Visitable) o.getE2();
        relopCheckType(left,right);
        return null;
    }

    @Override
    public Object visit(NotOp n) {
        Visitable left = (Visitable) n.getE1();
        //relopCheckType(left,right);
        return null;
    }

    @Override
    public Object visit(LtOp l) {
        Visitable left = (Visitable) l.getE1();
        Visitable right = (Visitable) l.getE2();
        relopCheckType(left,right);
        return null;
    }

    @Override
    public Object visit(LeOp l) {
        Visitable left = (Visitable) l.getE1();
        Visitable right = (Visitable) l.getE2();
        relopCheckType(left,right);
        return null;
    }

    @Override
    public Object visit(GtOp g) {
        Visitable left = (Visitable) g.getE1();
        Visitable right = (Visitable) g.getE2();
        relopCheckType(left,right);
        return null;
    }

    @Override
    public Object visit(GeOp g) {
        Visitable left = (Visitable) g.getE1();
        Visitable right = (Visitable) g.getE2();
        relopCheckType(left,right);
        return null;
    }

    @Override
    public Object visit(NeOp n) {
        Visitable left = (Visitable) n.getE1();
        Visitable right = (Visitable) n.getE2();
        relopCheckType(left,right);
        return null;
    }

    @Override
    public Object visit(EqOp e) {
        Visitable left = (Visitable) e.getE1();
        Visitable right = (Visitable) e.getE2();
        relopCheckType(left,right);
        return null;
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
    public Object visit(ReadOp r) {
        return null;
    }

    @Override
    public Object visit(WriteOp w) {
        return null;
    }

    @Override
    public Object visit(MultipleAssignOp m) {
        return null;
    }

    @Override
    public Object visit(CallProcOp c) {
        /*
            Bisogna effettuare la lookup nella tabella globale per verificare
            che la procedura chiamata sia stata definita.
            Se NON è definita si lancia un'eccezione.
            Se è definita, bisogna recuperare l'array con i tipi di ritorno dalla
            tabella dei simboli
         */
        return null;
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

    private Object relopCheckType(Visitable left, Visitable right){
        Object leftType;
        Object rightType;
        SymbolTable.SymbolTableRow leftId;

        leftType = left.accept(this);
        rightType = right.accept(this);


        return null;
    }

    private Object optype2(Operator op, Expr first, Expr second) throws Exception {
        /*
        TrueLeaf
        FalseLeaf
        IntConstLeaf
        FloatConstLeaf
        IdLeaf
        NullLeaf
        CallProcOP
        */
        ArrayList<ResultTypeOp> resultTypeOp; //tipi di ritorno di CallProcOp

        switch (op){
            case SUM, SUB, MUL, DIV:
                if(!(first instanceof IntConstLeaf || first instanceof FloatConstLeaf || first instanceof CallProcOp)){
                    throw new Exception("Errore Semantico: tipo non valido per l'operando " + first);
                }

                if(!(second instanceof IntConstLeaf || second instanceof FloatConstLeaf || second instanceof CallProcOp)){
                    throw new Exception("Errore Semantico: tipo non valido per l'operando " + second);
                }

                ResultTypeOp rt1, rt2;
                String type1 = null, type2 = null;

                //first = IntConstLeaf
                if(first instanceof IntConstLeaf) {//first è un intero
                    type1 = (String) ((IntConstLeaf) first).accept(this);
                }
                //first = FloatConstLeaf
                if(first instanceof FloatConstLeaf){
                    type1 = (String) ((FloatConstLeaf) first).accept(this);
                }
                //first = CallProcOp
                if(first instanceof CallProcOp){
                    type1 = getCpReturnType((CallProcOp) first);
                }
                //first = idLeaf
                if(first instanceof IdLeaf){
                    type1 = (String) ((IdLeaf) first).accept(this);
                }
                //second = IntConstLeaf
                if(second instanceof IntConstLeaf){
                    type2 = (String) ((IntConstLeaf) second).accept(this);
                }
                //second = FloatConstLeaf
                if(second instanceof FloatConstLeaf){
                    type2 = (String) ((FloatConstLeaf) second).accept(this);
                }
                //second = CallProcOp
                if(second instanceof CallProcOp){
                    type2 = getCpReturnType((CallProcOp) second);
                }
                //second = idLeaf
                if(second instanceof IdLeaf){
                    type2 = (String) ((IdLeaf) second).accept(this);
                }

                if(type1 != null && type2 != null){
                    if(type1.equals(type2)){
                        return type1;
                    }

                    if(op == Operator.DIV){
                        if((type1 == ResultTypeOp.INT) && (type2 == ResultTypeOp.INT)){
                            IntConstLeaf s = (IntConstLeaf) second;
                            int v = s.getValue();
                            if(v == 0){
                                throw new Exception("Errore Semantico: Divisione intera per zero sconosciuta.");
                            }
                        }
                    }
                    //nel caso in cui i tipi sono diversi, vale quello "più grande", ossia float
                    return ResultTypeOp.FLOAT;
                }

                throw new Exception("Errore Semantico: Type Mismatch");

            case AND, OR, NOT:


        } //end switch

        return null; //da verificare
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

        throw new Exception("Errore Semantico: il tipo di ritorno della procedura non è valido");
    }
}
