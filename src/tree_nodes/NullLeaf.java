package tree_nodes;

public class NullLeaf extends Node implements Expr, Visitable{

    private Object value;

    public NullLeaf() {
        super(Components.NULL);
        this.value = null;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "NullLeaf{" +
                "value=" + value +
                '}';
    }
}
