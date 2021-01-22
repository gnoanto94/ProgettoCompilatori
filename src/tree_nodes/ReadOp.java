package tree_nodes;

import java.util.ArrayList;

public class ReadOp extends StatOp implements Visitable{

    private ArrayList<IdLeaf> idList;

    public ReadOp(ArrayList<IdLeaf> idList) {
        super(Components.READ_OP);
        this.idList = idList;
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
        return "ReadOp{" +
                "idList=" + idList +
                '}';
    }
}
