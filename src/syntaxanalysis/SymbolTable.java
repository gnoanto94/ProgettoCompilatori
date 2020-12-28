package syntaxanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private ArrayList<SymbolTableRow> symTab;
    private HashMap<String, Integer> keywords;
    private String arr_keywords[] = {"int", "string", "float", "bool", "proc", "corp", "void",
            "if", "then", "elif", "fi", "else", "while", "do", "od", "readln", "write"};
    private int symTabId;

    public SymbolTable() {
        symTabId = 0;
        this.symTab = new ArrayList<>();

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

        for(String s: arr_keywords){
            add(s, keywords.get(s));
        }

    }

    public boolean add(String lessema, int token){
        return symTab.add(new SymbolTableRow(symTabId++, lessema, token));
    }

    public boolean contain(String lessema){
        for(SymbolTableRow s: symTab){
            if(s.lessema.equals(lessema)){
                return true;
            }
        }
        return false;
    }

    public int retrieveKeyword(String lessema){
        if(keywords.containsKey(lessema)){
            return keywords.get(lessema);
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        String out= "";
        for(SymbolTableRow s: symTab){
            out += s.toString() + "\n";
        }

        return out;
    }

    class SymbolTableRow {
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
        public String toString() {
            return "ID: " + id + "\t | Lessema: " + lessema;
        }
    }
}
