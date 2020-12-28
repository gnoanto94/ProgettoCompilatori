package tree;

public class FalseLeaf extends Node implements Expr, Visitable{

    private boolean falseValue;

    public FalseLeaf() {
        super(Components.FALSE);
        this.falseValue = false;
    }

    public boolean isFalseValue() {
        return falseValue;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "FalseLeaf{" +
                "falseValue=" + falseValue +
                '}';
    }
}
