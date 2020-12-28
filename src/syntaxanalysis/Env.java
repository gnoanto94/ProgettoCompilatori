package syntaxanalysis;

public class Env {
    private SymbolTable table;
    private Env prev;

    public Env(Env prev) {
        this.table = new SymbolTable();
        this.prev = prev;
    }

    public boolean add(String lessema, int token){
        return table.add(lessema, token);
    }

    public boolean contain(String lessema){
        for (Env e = this; e != null; e = e.prev){
            if (table.contain(lessema)){
                return true;
            }
        }
        return false;
    }

    public SymbolTable getTable() {
        return table;
    }

    public Env getEnv() {
        return prev;
    }
}
