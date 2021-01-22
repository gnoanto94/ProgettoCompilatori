package tree_nodes;

import java.util.ArrayList;

public class WriteOp extends StatOp implements Visitable{

    private ArrayList<Expr> exprList;

    public WriteOp(ArrayList<Expr> exprList) {
        super(Components.WRITE_OP);
        this.exprList = exprList;
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
        return "WriteOp{" +
                "exprList=" + exprList +
                '}';
    }
}
