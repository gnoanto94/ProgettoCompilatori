package tree;

public class StringConstLeaf extends Node implements Expr, Visitable{

    private String value;

    public StringConstLeaf(String value) {
        super(Components.STRING_CONST);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "StringConstLeaf{" +
                "value='" + value + '\'' +
                '}';
    }
}
