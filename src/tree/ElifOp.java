package tree;

import java.util.ArrayList;

public class ElifOp extends StatOp implements Visitable{

    private Expr expr;
    private ArrayList<StatOp> statList;

    public ElifOp(Expr expr, ArrayList<StatOp> statList) {
        super(Components.ELIF_OP);
        this.expr = expr;
        this.statList = statList;
    }

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public ArrayList<StatOp> getStatList() {
        return statList;
    }

    public void setStatList(ArrayList<StatOp> statList) {
        this.statList = statList;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "ElifOp{" +
                "expr=" + expr +
                ", statList=" + statList +
                '}';
    }
}
