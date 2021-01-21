package tree;

import syntaxanalysis.SymbolTable;

import java.util.Objects;

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

    public SymbolTable.SymbolTableRow getTableEntry() {
        return tableEntry;
    }

    public void setTableEntry(SymbolTable.SymbolTableRow tableEntry) {
        this.tableEntry = tableEntry;
    }

    public void setIdEntry(String idEntry) {
        this.idEntry = idEntry;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "IdLeaf{" +
                "idEntry='" + idEntry + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IdLeaf)){
            return false;
        }
        IdLeaf leaf = (IdLeaf) o;

        if(leaf.getTableEntry().equals(this.getTableEntry()) && leaf.getIdEntry().equals(this.idEntry)){
            return true;
        }
        return false;

    }

}
