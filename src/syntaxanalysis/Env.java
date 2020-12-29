package syntaxanalysis;

public class Env {
    private SymbolTable table;
    private Env prev, next = null;
//    public static Env top = null;
//    public static Env saved = null;

    public Env(Env prev) {
        this.table = new SymbolTable();
        this.prev = prev;
        if(this.prev != null){this.prev.next = this;}
    }

    public SymbolTable.SymbolTableRow add(String lessema, int token){
        if(table.contain(lessema)){
            return table.get(lessema);
        }
        return table.add(lessema, token);
    }

    public SymbolTable.SymbolTableRow lookup(String lessema){
        for (Env e = this; e != null; e = e.prev){
            if (e.table.contain(lessema)){
                return e.table.get(lessema);
            }
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
        if(this.table.equals(((Env) obj).table)){
            return true;
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
/*
    public static Env push(Env e){
        saved = top;
        top = e;
        return top;
    }

    public static Env pop(){
        top = saved;
        return top;
    }*/
}
