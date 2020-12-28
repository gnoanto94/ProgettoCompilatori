package tree;

public class FloatConstLeaf extends Node implements Expr, Visitable{

    private float value;

    public FloatConstLeaf(float value) {
        super(Components.FLOAT_CONST);
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "FloatConstLeaf{" +
                "value=" + value +
                '}';
    }
}
