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


    /*
    A questo metodo equals verrà passata una stringa come parametro e non un oggetto
    ResultTypeOp in quanto la verifica è fatta su una stringa
     */
    public boolean equals(String str) {
        if(this.getValue().equals(str)){
            return true;
        }
        return false;
    }
}
