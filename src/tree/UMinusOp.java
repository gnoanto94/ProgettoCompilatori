package tree;

public class UMinusOp extends Node implements Expr, Visitable{
    private Expr e1;

    public UMinusOp(Expr e1) {
        super(Components.UMINUS_OP);
        this.e1 = e1;
    }

    public Expr getE1() {
        return e1;
    }

    public void setE1(Expr e1) {
        this.e1 = e1;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "UMinusOp{" +
                "e1=" + e1 +
                '}';
    }
}
