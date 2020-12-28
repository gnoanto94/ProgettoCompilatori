package tree;

public class TrueLeaf extends Node implements Expr, Visitable{
    private boolean trueValue;

    public TrueLeaf() {
        super(Components.TRUE);
        this.trueValue = true;
    }

    public boolean isTrueValue() {
        return trueValue;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "TrueLeaf{" +
                "trueValue=" + trueValue +
                '}';
    }
}
