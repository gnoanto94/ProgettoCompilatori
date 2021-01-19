package tree;

import java.util.ArrayList;

public class IfOp extends StatOp implements Visitable{

    private Expr expr;
    private ArrayList<StatOp> statList;
    private ArrayList<ElifOp> elifList;
    private ElseOp elseOp;

    public IfOp(Expr expr, ArrayList<StatOp> statList, ArrayList<ElifOp> elifList, ElseOp elseOp) {
        super(Components.IF_OP);
        this.expr = expr;
        this.statList = statList;
        this.elifList = elifList;
        this.elseOp = elseOp;
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

    public ArrayList<ElifOp> getElifList() {
        return elifList;
    }

    public void setElifList(ArrayList<ElifOp> elifList) {
        this.elifList = elifList;
    }

    public ElseOp getElseOp() {
        return elseOp;
    }

    public void setElseOp(ElseOp elseOp) {
        this.elseOp = elseOp;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "IfOp{" +
                "expr=" + expr +
                ", statList=" + statList +
                ", elifList=" + elifList +
                ", elseOp=" + elseOp +
                '}';
    }
}
