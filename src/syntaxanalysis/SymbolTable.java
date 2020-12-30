package syntaxanalysis;

import tree.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                add(s, keywords.get(s), Kind.KEYWORD);
            }
            global = false;
        }
    }

    public SymbolTableRow add(String lessema, int token, Kind kind){
        SymbolTableRow row;
        switch (kind){
            case KEYWORD:
                row = new SymbolTableRow(symTabId++, lessema, token);
                break;

            case VARIABLE:
                row = new VarRow(symTabId++, lessema, token);
                break;

            case PROCEDURE:
                row = new ProcRow(symTabId++, lessema, token);
                break;

            default: row = new SymbolTableRow(symTabId++, lessema, token);
        }

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
        private boolean fref; //flag per controlli su dichiarazioni e utilizzi
        //fref si imposta a true quando troviamo un utilizzo senza dichiarazione, false altrimenti

        //aggiungere type variabile e type proc ( parametri/return )

        public SymbolTableRow(){}

        public SymbolTableRow(int id, String lessema, int token) {
            this.id = id;
            this.lessema = lessema;
            this.token = token;
        }

        public boolean isFref() {
            return fref;
        }

        public void setFref(boolean fref) {
            this.fref = fref;
        }

        public String getLessema() {
            return lessema;
        }

        public void setLessema(String lessema) {
            this.lessema = lessema;
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

    public class VarRow extends SymbolTableRow{
        private Type varType;

        public VarRow(int id, String lessema, int token, Type varType) {
            super(id, lessema, token);
            this.varType = varType;
        }

        public VarRow(int id, String lessema, int token) {
            super(id, lessema, token);
            this.varType = null;
        }

        public Type getVarType() {
            return varType;
        }

        public void setVarType(Type varType) {
            this.varType = varType;
        }

        @Override
        public String toString() {
            return "VarRow{" +
                    "id=" + getId() +
                    ", lessema='" + getLessema() + '\'' +
                    ", token=" + getToken() +
                    ", fref=" + isFref() +
                    ", varType=" + varType +
                    '}';
        }
    }

    public class ProcRow extends SymbolTableRow{
        private ArrayList<ResultTypeOp> returnTypes;
        private ArrayList<Type> paramTypes;

        public ProcRow(int id, String lessema, int token) {
            super(id, lessema, token);
            this.returnTypes = null;
            this.paramTypes = null;
        }

        public ProcRow(int id, String lessema, int token, ArrayList<ResultTypeOp> returnTypes,
                       ArrayList<Type> paramTypes) {
            super(id, lessema, token);
            this.returnTypes = returnTypes;
            this.paramTypes = paramTypes;
        }

        public ArrayList<ResultTypeOp> getReturnTypes() {
            return returnTypes;
        }

        public void setReturnTypes(ArrayList<ResultTypeOp> returnTypes) {
            this.returnTypes = returnTypes;
        }

        public ArrayList<Type> getParamTypes() {
            return paramTypes;
        }

        public void setParamTypes(ArrayList<Type> paramTypes) {
            this.paramTypes = paramTypes;
        }

        @Override
        public String toString() {
            return "ProcRow{" +
                    "id=" + getId() +
                    ", lessema='" + getLessema() + '\'' +
                    ", token=" + getToken() +
                    ", fref=" + isFref() +
                    ", paramTypes=" + paramTypes +
                    ", returnTypes=" + returnTypes +
                    '}';
        }
    }
}
