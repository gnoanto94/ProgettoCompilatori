package tree_nodes;

import java.util.ArrayList;

public class ParamDeclOp extends Node implements Visitable{

    private Type type;
    private ArrayList<IdLeaf> idList;

    public ParamDeclOp(Type type, ArrayList<IdLeaf> idList) {
        super(Components.PARAM_DECL_OP);
        this.type = type;
        this.idList = idList;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ArrayList<IdLeaf> getIdList() {
        return idList;
    }

    public void setIdList(ArrayList<IdLeaf> idList) {
        this.idList = idList;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "ParamDeclOp{" +
                "type=" + type +
                ", idList=" + idList +
                '}';
    }
}
