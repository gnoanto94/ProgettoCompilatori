package tree;

public class ResultTypeOp extends Type implements Visitable{
    public static final String VOID = "void";

    public ResultTypeOp(String value) {
        super.setName(Components.RESULT_TYPE_OP);
        super.setValue(value);
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
    //vengono utilizzati i metodi get/set del padre
}
