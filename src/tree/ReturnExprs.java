package tree;

import java.util.ArrayList;

public class ReturnExprs extends Node implements Visitable {

    private ArrayList<Expr> exprList;

    public ReturnExprs(ArrayList<Expr> exprList) {
        super(Components.RETURN_EXPRS);
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
        return "ReturnExprs{" +
                "exprList=" + exprList +
                '}';
    }
}
