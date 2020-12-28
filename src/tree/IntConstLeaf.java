package tree;

public class IntConstLeaf extends Node implements Expr, Visitable{

    private int value;

    public IntConstLeaf(int value) {
        super(Components.INT_CONST);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "IntConstLeaf{" +
                "value=" + value +
                '}';
    }
}
