package tree;

import java.util.ArrayList;

public class IdListInitOp extends Node implements Visitable{

    private ArrayList<IdListInit> idListInit;

    public IdListInitOp(ArrayList<IdListInit> idListInit) {
        super(Components.ID_LIST_INIT_OP);
        this.idListInit = idListInit;
    }

    public void add(IdListInit idListInit){
        this.idListInit.add(idListInit);
    }

    public ArrayList<IdListInit> getIdListInit() {
        return idListInit;
    }

    public void setIdListInit(ArrayList<IdListInit> idListInit) {
        this.idListInit = idListInit;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "IdListInitOp{" +
                "idListInit=" + idListInit +
                '}';
    }
}
