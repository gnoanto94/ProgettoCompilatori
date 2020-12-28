package tree;

public class IdLeaf extends Node implements Expr, IdListInit, Visitable{

    private String idEntry;

    public IdLeaf(String idEntry) {
        super(Components.ID);
        this.idEntry = idEntry;
    }

    public String getIdEntry() {
        return idEntry;
    }

    public void setIdEntry(String idEntry) {
        this.idEntry = idEntry;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "IdLeaf{" +
                "idEntry='" + idEntry + '\'' +
                '}';
    }
}
