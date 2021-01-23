package code_generation;

import syntaxanalysis.SymbolTable;
import tree_nodes.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

public class CodeGeneratorVisitor implements Visitor {

    private String programName;
    private PrintStream ps;

    private static final String TRUE = "1";
    private static final String FALSE = "0";

    public CodeGeneratorVisitor(String programName) throws FileNotFoundException {
        //si elimina il .toy dal nome del file e lo si trasforma in .c
        this.programName = (programName.substring(0, programName.indexOf('.'))) + ".c";
        String temp[] = this.programName.split("/");
        this.programName = "generated_c_files/" + temp[1];
        this.ps = new PrintStream(this.programName);
        //verrà sempre inclusa
        ps.println("/* Auto-generated code from Toy Compiler */");
        ps.println("#include<stdio.h>"); //I/O
        ps.println("#include<stdlib.h>");
        ps.println("#include<string.h>"); //per lavorare con le stringhe
        ps.println();
    }

    @Override
    public Object visit(FloatConstLeaf f) {
        return String.valueOf(f.getValue());
    }

    @Override
    public Object visit(IntConstLeaf i) {
        return String.valueOf(i.getValue());
    }

    @Override
    public Object visit(IdLeaf i) throws Exception {
        return i.getTableEntry().getLessema();
    }

    @Override
    public Object visit(StringConstLeaf s) {
        return ("\"" + s.getValue() + "\"");
    }

    @Override
    public Object visit(NullLeaf n) {
        return "null";
    }

    @Override
    public Object visit(TrueLeaf t) {
        return TRUE;
    }

    @Override
    public Object visit(FalseLeaf f) {
        return FALSE;
    }

    @Override
    public Object visit(PlusOp p) throws Exception {
        String e1 = (String) ((Visitable)p.getE1()).accept(this);
        String e2 = (String) ((Visitable)p.getE2()).accept(this);
        return e1+" + "+e2;
    }

    @Override
    public Object visit(MinusOp m) throws Exception {
        String e1 = (String) ((Visitable)m.getE1()).accept(this);
        String e2 = (String) ((Visitable)m.getE2()).accept(this);
        return e1+" - "+e2;
    }

    @Override
    public Object visit(UMinusOp u) throws Exception {
        String e1 = (String) ((Visitable)u.getE1()).accept(this);
        return "-"+e1;
    }

    @Override
    public Object visit(MultOp m) throws Exception {
        String e1 = (String) ((Visitable)m.getE1()).accept(this);
        String e2 = (String) ((Visitable)m.getE2()).accept(this);
        return e1+" * "+e2;
    }

    @Override
    public Object visit(DivOp d) throws Exception {
        String e1 = (String) ((Visitable)d.getE1()).accept(this);
        String e2 = (String) ((Visitable)d.getE2()).accept(this);
        return e1+" / "+e2;
    }

    @Override
    public Object visit(AndOp a) throws Exception {
        String e1 = (String) ((Visitable)a.getE1()).accept(this);
        String e2 = (String) ((Visitable)a.getE2()).accept(this);
        return e1+" && "+e2;
    }

    @Override
    public Object visit(OrOp o) throws Exception {
        String e1 = (String) ((Visitable)o.getE1()).accept(this);
        String e2 = (String) ((Visitable)o.getE2()).accept(this);
        return e1+" || "+e2;
    }

    @Override
    public Object visit(NotOp n) throws Exception {
        String e1 = (String) ((Visitable)n.getE1()).accept(this);
        return "!"+e1;
    }

    @Override
    public Object visit(LtOp l) throws Exception {
        String e1 = (String) ((Visitable)l.getE1()).accept(this);
        String e2 = (String) ((Visitable)l.getE2()).accept(this);
        return e1+" < "+e2;
    }

    @Override
    public Object visit(LeOp l) throws Exception {
        String e1 = (String) ((Visitable)l.getE1()).accept(this);
        String e2 = (String) ((Visitable)l.getE2()).accept(this);
        return e1+" <= "+e2;
    }

    @Override
    public Object visit(GtOp g) throws Exception {
        String e1 = (String) ((Visitable)g.getE1()).accept(this);
        String e2 = (String) ((Visitable)g.getE2()).accept(this);
        return e1+" > "+e2;
    }

    @Override
    public Object visit(GeOp g) throws Exception {
        String e1 = (String) ((Visitable)g.getE1()).accept(this);
        String e2 = (String) ((Visitable)g.getE2()).accept(this);
        return e1+" >= "+e2;
    }

    @Override
    public Object visit(NeOp n) throws Exception {
        String e1 = (String) ((Visitable)n.getE1()).accept(this);
        String e2 = (String) ((Visitable)n.getE2()).accept(this);
        return e1+" != "+e2;
    }

    @Override
    public Object visit(EqOp e) throws Exception {
        String e1 = (String) ((Visitable)e.getE1()).accept(this);
        String e2 = (String) ((Visitable)e.getE2()).accept(this);
        return e1+" == "+e2;
    }

    @Override
    public Object visit(SimpleAssignOp s) throws Exception {
        String name = (String) s.getId().accept(this);
        String expr = (String) ((Visitable) s.getExpr()).accept(this);
        return name+" = "+expr;
    }

    /* STATEMENT */
    @Override
    public Object visit(IfOp i) throws Exception {
        return null;
    }

    @Override
    public Object visit(ElifOp e) throws Exception {
        return null;
    }

    @Override
    public Object visit(ElseOp e) throws Exception {
        return null;
    }

    @Override
    public Object visit(WhileOp w) throws Exception {
        return null;
    }

    @Override
    public Object visit(DoOp d) throws Exception {
        return null;
    }

    @Override
    public Object visit(ReadOp r) throws Exception {
        String str = "";
        String type;
        String vars = "";

        for(IdLeaf id : r.getIdList()){
            type = ((SymbolTable.VarRow) id.getTableEntry()).getVarType().getValue();
            if(type.equals(ResultTypeOp.INT)){
                str += "%d ";
                vars += id.getIdEntry()+",";
            }
            if(type.equals(ResultTypeOp.FLOAT)){
                str += "%f ";
                vars += id.getIdEntry()+",";
            }
            if(type.equals(ResultTypeOp.BOOL)){
                str += "%d ";
                vars += id.getIdEntry()+",";
            }
            if(type.equals(ResultTypeOp.STRING)){
                str += "%s ";
                vars += id.getIdEntry()+",";
            }
        }
        vars = vars.substring(0,vars.lastIndexOf(","));
        return "scanf(\""+str+"\","+vars+")";
    }

    @Override
    public Object visit(WriteOp w) throws Exception {

        ArrayList<Expr> exprs = w.getExprList();
        String text = "", vars = "";

        for(Expr e : exprs){

            if(e instanceof IdLeaf){
                vars += ((IdLeaf) e).accept(this) + ", ";

                SymbolTable.VarRow row = (SymbolTable.VarRow) ((IdLeaf) e).getTableEntry();
                String type = row.getVarType().getValue();

                text += getPlaceHolders(type);
            } else if(e instanceof CallProcOp){
                    String[] info = (String[])((CallProcOp) e).accept(this);
                    text += info[0];
                    vars += info[1];
            } else {
                //Nel caso in cui siano costanti
                text += ((Visitable) e).accept(this);
            }
        }
        vars = vars.substring(0, vars.lastIndexOf(','));
        return "printf(\"" + text + "\", " + vars + ");";
    }

    public String getPlaceHolders(String type){
        if(type.equals(Type.INT)){
            return "%d ";
        }

        if(type.equals(Type.FLOAT)){
            return "%f ";
        }

        if(type.equals(Type.BOOL)){
            return "%d ";
        }

        if(type.equals(Type.STRING)){
            return "%s ";
        }

        return "";
    }

    @Override
    public Object visit(MultipleAssignOp m) throws Exception {
        String str = "";
        ArrayList<IdLeaf> idList = m.getIdList();
        ArrayList<Expr> exprList = m.getExprList();

        ArrayList<String> values = new ArrayList<>();

        for(Expr e: exprList){
            if(e instanceof CallProcOp){
                String[] info = (String[]) ((CallProcOp) e).accept(this);
                String[] info2 = info[1].split(",");

               for(int i = 0; i < info2.length; i++){
                   values.add(info2[i]);
               }
            }
            values.add((String) ((Visitable) e).accept(this));
        }

        for(int i = 0; i < idList.size(); i++){
            str += idList.get(i) + " = " + values.get(i)+";\n";
        }
        return str;
    }

    @Override
    public Object visit(CallProcOp c) throws Exception {
        String text="", vars="";
        SymbolTable.ProcRow p_row = (SymbolTable.ProcRow) c.getId().getTableEntry();
        ArrayList<String> types = new ArrayList<>();

        for(ResultTypeOp t: p_row.getReturnTypes()){
            types.add(t.getValue());
        }

        for(String s: types){
            text += getPlaceHolders(s);
        }


        String proc_name = c.getId().getIdEntry();
        ArrayList<Expr> actualParam = c.getExprList();

        vars += proc_name + "(";
        if(actualParam != null) {
            for (Expr e2 : actualParam) {
                vars += ((Visitable) e2).accept(this) + ", ";
            }
        }
        vars += ")";

        if(types.size() == 1){ //se la procedura ha un unico tipo di ritorno
            text += getPlaceHolders(types.get(0));
        } else { //la procedura ha più tipi di ritorno
            int counter = 1;
            for(String s2: types){
                text += getPlaceHolders(s2);
                vars += proc_name + "_" + counter + ", ";
                counter++;
            }
        }
        String[] result = {text, vars};
        return result;
    }

    @Override
    public Object visit(IdListInitOp i) throws Exception {

        String idList = "";
        for(IdListInit id: i.getIdListInit()) {
            idList += ((Visitable) id).accept(this) + ", ";
        }
        idList = idList.substring(0,idList.lastIndexOf(",")) +";";
        return idList;
    }

    /* PROCEDURE */
    @Override
    public Object visit(ParamDeclOp p) throws Exception {
        String var = "";
        ArrayList<IdLeaf> idList = p.getIdList();
        String type = (String) p.getType().accept(this);
        //in C una stringa corrisponde ad un array di caratteri
        if(type.equals(Type.STRING)){
            type = "char*";
            //in C non esistono booleane, per cui usiamo i valori interi 0 e 1
        } else if(type.equals(Type.BOOL)){
            type = "int";
        }

        for(IdLeaf id: idList){
            var += type + " " + id.accept(this) + ", ";
        }

        var = var.substring(0, var.lastIndexOf(','));

        return var;
    }

    @Override
    public Object visit(ProcOp p) throws Exception {
        /* Creazione delle variabili globali per i valori di ritorno successivi al primo
         * Nella firma della funzione C come tipo di ritorno ci sarà solo il primo
         */
        return null;
    }

    @Override
    public Object visit(ProcOpBody p) throws Exception {
        /*Return solo del primo valore di ritorno (o dell'unico valore)
         * e assegnazione dei valori di ritorno successivo al primo alle variabili globali create precedentemente */
        return null;
    }

    @Override
    public Object visit(ReturnExprs r) throws Exception {
        return null;
    }

    @Override
    public Object visit(ResultTypeOp r) {
        return r.getValue();
    }

    /* PROGRAM */
    @Override
    public Object visit(ProgramOp p) throws Exception {
        ArrayList<VarDeclOp> varDeclList = p.getVarDeclList();
        ArrayList<ProcOp> procList = p.getProcOpList();

        if(varDeclList != null) { //potrebbero non esserci variabili globali
            for (VarDeclOp v : varDeclList) {
                v.accept(this);
            }
        }

        for(ProcOp pr: procList){
            pr.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(Type t) {
        return t.getValue();
    }

    @Override
    public Object visit(VarDeclOp v) throws Exception {
        //posso sempre prendere il tipo della variabile perchè non posso trovarmi all'interno di un altro contesto
        //che non sia l'inizio del programma
        String var = (String) v.getIdListInit().accept(this);
        String type = (String) v.getType().accept(this);

        //in C una stringa corrisponde ad un array di caratteri
        if(type.equals(Type.STRING)){
            type = "char*";
        //in C non esistono booleane, per cui usiamo i valori interi 0 e 1
        } else if(type.equals(Type.BOOL)){
            type = "int";
        }

        ps.println(type + " " + var);

        return null;
    }
}
