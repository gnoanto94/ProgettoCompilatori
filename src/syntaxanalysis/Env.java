package syntaxanalysis;

public class Env {
    private SymbolTable table;
    private Env prev, next = null;
    private String name;

    public Env(Env prev, String name) {
        this.table = new SymbolTable();
        this.prev = prev;
        this.name = name;
        if(this.prev != null){this.prev.next = this;}
    }

    public SymbolTable.SymbolTableRow add(String lessema, int token, Kind kind){
        if(table.contain(lessema)){
            return table.get(lessema);
        }
        return table.add(lessema, token, kind);
    }

    public SymbolTable.SymbolTableRow lookup(String lessema){
        for (Env e = this; e != null; e = e.prev){
            if (e.table.contain(lessema)){
                return e.table.get(lessema);
            }
        }
        return null;
    }

    public SymbolTable.SymbolTableRow currentLookup(String lessema){
        if (this.table.contain(lessema)) {
            return this.table.get(lessema);
        }
        return null;
    }

    public SymbolTable getTable() {
        return table;
    }

    public Env getPrev() {
        return prev;
    }

    public Env getNext() {
        return next;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Env){
            if(this.name.equals(((Env) obj).name)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Env{" +
                //"table=" + table +
                ", prev=" + prev +
                ", next=" + next +
                '}';
    }

    public String getName(){
        return name;
    }
}
