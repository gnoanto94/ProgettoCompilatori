package tree;

import java.util.ArrayList;

public class WhileOp extends StatOp implements Visitable{

    private ArrayList<StatOp> statList;
    private Expr expr;
    private DoOp doOp;

    public WhileOp(ArrayList<StatOp> statList, Expr expr, DoOp doOp) {
        super(Components.WHILE_OP);
        this.statList = statList;
        this.expr = expr;
        this.doOp = doOp;
    }

    public ArrayList<StatOp> getStatList() {
        return statList;
    }

    public void setStatList(ArrayList<StatOp> statList) {
        this.statList = statList;
    }

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public DoOp getDoOp() {
        return doOp;
    }

    public void setDoOp(DoOp doOp) {
        this.doOp = doOp;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "WhileOp{" +
                "statList=" + statList +
                ", expr=" + expr +
                ", doOp=" + doOp +
                '}';
    }
}
