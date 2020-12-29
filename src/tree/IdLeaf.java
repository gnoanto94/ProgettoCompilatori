package tree;

import syntaxanalysis.SymbolTable;

public class IdLeaf extends Node implements Expr, IdListInit, Visitable{

    private String idEntry;
    private SymbolTable.SymbolTableRow tableEntry;

    public IdLeaf(String idEntry, SymbolTable.SymbolTableRow tableEntry) {
        super(Components.ID);
        this.idEntry = idEntry;
        this.tableEntry = tableEntry;
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
