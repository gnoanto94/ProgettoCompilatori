package tree;

import syntaxanalysis.SymbolTable;

import java.util.ArrayList;

public class TypeCheckingVisitor implements Visitor{

    /* Le visit dei nodi foglia ritornano sempre il loro type*/
    @Override
    public Object visit(FloatConstLeaf f)  {
        return new Type(Type.FLOAT);
    }

    @Override
    public Object visit(IntConstLeaf i) {
        return new Type(Type.INT);
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
}
