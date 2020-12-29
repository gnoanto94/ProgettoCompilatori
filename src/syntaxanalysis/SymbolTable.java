package syntaxanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SymbolTable {
    private static HashMap<String, Integer> keywords;
    public static boolean global;
    static {
        global = true;
        //inserimento delle keywords
        keywords = new HashMap<>(Map.of(
                "int", sym.INT,
                "string", sym.STRING,
                "float", sym.FLOAT,
                "bool", sym.BOOL,
                "proc", sym.PROC,
                "corp", sym.CORP,
                "void", sym.VOID,
                "if",sym.IF,
                "then", sym.THEN,
                "elif", sym.ELIF));

        keywords.putAll(Map.of(
                "fi", sym.FI,
                "else", sym.ELSE,
                "while", sym.WHILE,
                "do", sym.DO,
                "od", sym.OD,
                "readln", sym.READ,
                "write", sym.WRITE));
    }
    private ArrayList<SymbolTableRow> symTab;
    private String arr_keywords[] = {"int", "string", "float", "bool", "proc", "corp", "void",
            "if", "then", "elif", "fi", "else", "while", "do", "od", "readln", "write"};
    private int symTabId;

    public SymbolTable() {
        symTabId = 0;
        this.symTab = new ArrayList<>();
        if(global) {
            for (String s : arr_keywords) {
                add(s, keywords.get(s));
            }
            global = false;
        }

    }

    public SymbolTableRow add(String lessema, int token){
        SymbolTableRow row = new SymbolTableRow(symTabId++, lessema, token);
        if(symTab.add(row)){
            return row;
        }
        return null;
    }

    public SymbolTableRow get(String lessema){
        int index = symTab.indexOf(new SymbolTableRow(0, lessema, 0));
        return symTab.get(index);
    }

    public boolean contain(String lessema){
        for(SymbolTableRow s: symTab){
            if(s.lessema.equals(lessema)){
                return true;
            }
        }
        return false;
    }

    public static int retrieveKeyword(String lessema){
        if(keywords.containsKey(lessema)){
            return keywords.get(lessema);
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(this.symTab.equals(((SymbolTable) obj).symTab)){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String out= "";
        for(SymbolTableRow s: symTab){
            out += s.toString() + "\n";
        }

        return out;
    }

    public class SymbolTableRow {
        private int id;
        private String lessema;
        private int token;
        //aggiungere type variabile e type parametri/return

        public SymbolTableRow(int id, String lessema, int token) {
            this.id = id;
            this.lessema = lessema;
            this.token = token;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getToken() {
            return token;
        }

        public void setToken(int token) {
            this.token = token;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SymbolTableRow)) return false;
            SymbolTableRow that = (SymbolTableRow) o;
            return lessema.equals(that.lessema);
        }

        @Override
        public String toString() {
            return "ID: " + id + "\t | Lessema: " + lessema;
        }
    }
}
