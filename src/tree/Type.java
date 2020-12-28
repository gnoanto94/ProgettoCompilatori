package tree;

public class Type extends Node implements Visitable{

    private String value;
    public static final String INT = "int";
    public static final String BOOL = "bool";
    public static final String FLOAT = "float";
    public static final String STRING = "string";

    public Type(){
        //Il costruttore vuoto è necessario perchè la classe Type è stata utilizzata come
        //superclasse di ResultTypeOp
    }

    public Type(String value) {
        super(Components.TYPE);
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
        return "Type{" +
                "value='" + value + '\'' +
                '}';
    }
}
