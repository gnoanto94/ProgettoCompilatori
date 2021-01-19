package tree;

import java.util.ArrayList;

public class CallProcOp extends StatOp implements Expr, Visitable{

    private IdLeaf id;
    private ArrayList<Expr> exprList;

    public CallProcOp(IdLeaf id, ArrayList<Expr> exprList) {
        super(Components.CALL_PROC_OP);
        this.id = id;
        this.exprList = exprList;
    }

    public IdLeaf getId() {
        return id;
    }

    public void setId(IdLeaf id) {
        this.id = id;
    }

    public ArrayList<Expr> getExprList() {
        return exprList;
    }

    public void setExprList(ArrayList<Expr> exprList) {
        this.exprList = exprList;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "CallProcOp{" +
                "id=" + id +
                ", exprList=" + exprList +
                '}';
    }
}
