package tree;

public class MultOp extends Node implements Expr, Visitable{

    private Expr e1;
    private Expr e2;

    public MultOp(Expr e1, Expr e2) {
        super(Components.MULT_OP);
        this.e1 = e1;
        this.e2 = e2;
    }

    public Expr getE1() {
        return e1;
    }

    public void setE1(Expr e1) {
        this.e1 = e1;
    }

    public Expr getE2() {
        return e2;
    }

    public void setE2(Expr e2) {
        this.e2 = e2;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "TimesOp{" +
                "e1=" + e1 +
                ", e2=" + e2 +
                '}';
    }
}
