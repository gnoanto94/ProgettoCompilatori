package tree_nodes;

public class VarDeclOp extends Node implements Visitable{

    private Type type;
    private IdListInitOp idListInit;

    public VarDeclOp(Type type, IdListInitOp idListInit) {
        super(Components.VAR_DECL_OP);
        this.type = type;
        this.idListInit = idListInit;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public IdListInitOp getIdListInit() {
        return idListInit;
    }

    public void setIdListInit(IdListInitOp idListInit) {
        this.idListInit = idListInit;
    }

    @Override
    public Object accept(Visitor v) throws Exception{
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "VarDeclOp{" +
                "type=" + type +
                ", idListInit=" + idListInit +
                '}';
    }
}