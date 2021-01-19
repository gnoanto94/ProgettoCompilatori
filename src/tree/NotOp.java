package tree;

public class NotOp extends Node implements Expr, Visitable{

    private Expr e1;

    public NotOp(Expr e1) {
        super(Components.NOT_OP);
        this.e1 = e1;
    }

    public Expr getE1() {
        return e1;
    }

    public void setE1(Expr e1) {
        this.e1 = e1;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "NotOp{" +
                "e1=" + e1 +
                '}';
    }
}
