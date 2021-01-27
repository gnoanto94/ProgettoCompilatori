package code_generation;

import syntaxanalysis.SymbolTable;
import tree_nodes.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

public class CodeGeneratorVisitor implements Visitor {

    private String programName;
    private String currentProcName;
    private String multipleReturnVars = "";
    private String procedurePrototypes = "";
    private PrintStream ps;

    private static final String TRUE = "1";
    private static final String FALSE = "0";

    public CodeGeneratorVisitor(String programName) throws FileNotFoundException {
        //si elimina l'estensione .toy dal nome del file e la si trasforma in .c
        this.programName = (programName.substring(0, programName.indexOf('.'))) + ".c";
        String temp[] = this.programName.split("/");
        this.programName = "generated_c_files/" + temp[1];
        this.ps = new PrintStream(this.programName);
        //verranno sempre inclusi nel file C generato:
        ps.println("/* Auto-generated code from Toy Compiler */");
        ps.println("#include<stdio.h>");
        ps.println("#include<stdlib.h>");
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
        String res = "";

        //Nel caso in cui all'interno della stringa costante siano presenti caratteri di escape
        //verranno aggiunti letteralmente in essa
        res = s.getValue().replace("\n", "\\" + "n");
        res = res.replace("\t", "\\" + "t");

        //Si aggiungono i doppi apici di inizio e fine stringa costante
        return ("\"" + res + "\"");
    }

    @Override
    public Object visit(NullLeaf n) {
        return "NULL";
    }

    @Override
    public Object visit(TrueLeaf t) {
        return TRUE;
    }

    @Override
    public Object visit(FalseLeaf f) {
        return FALSE;
    }

    private String[] getExprCode(Expr e1, Expr e2) throws Exception {
        //result[0] = e1, result[1] = e2
        String[] result = new String[2];

        if(e1 instanceof CallProcOp){
            result[0] = ((String[]) ((Visitable) e1).accept(this))[1]; //in 1 c'è il contenuto di nostro interesse
        } else {
            result[0] = (String) ((Visitable) e1).accept(this);
        }

        if(e2 != null) {
            if (e2 instanceof CallProcOp) {
                result[1] = ((String[]) ((Visitable) e2).accept(this))[1]; //in 1 c'è il contenuto di nostro interesse
            } else {
                result[1] = (String) ((Visitable) e2).accept(this);
            }
        } else {
            result[1] = null;
        }

        return result;
    }

    @Override
    public Object visit(PlusOp p) throws Exception {
        String[] expr = getExprCode(p.getE1(), p.getE2());
        return expr[0] + " + " + expr[1];
    }

    @Override
    public Object visit(MinusOp m) throws Exception {
        String[] expr = getExprCode(m.getE1(), m.getE2());
        return expr[0] + " - " + expr[1];
    }

    @Override
    public Object visit(UMinusOp u) throws Exception {
        String[] expr = getExprCode(u.getE1(), null);
        return "-" + expr[0];
    }

    @Override
    public Object visit(MultOp m) throws Exception {
        String[] expr = getExprCode(m.getE1(), m.getE2());
        return expr[0] + " * " + expr[1];
    }

    @Override
    public Object visit(DivOp d) throws Exception {
        String[] expr = getExprCode(d.getE1(), d.getE2());
        return expr[0] + " / " + expr[1];
    }

    @Override
    public Object visit(AndOp a) throws Exception {
        String[] expr = getExprCode(a.getE1(), a.getE2());
        return expr[0] + " && " + expr[1];
    }

    @Override
    public Object visit(OrOp o) throws Exception {
        String[] expr = getExprCode(o.getE1(), o.getE2());
        return expr[0] + " || " + expr[1];
    }

    @Override
    public Object visit(NotOp n) throws Exception {
        String[] expr = getExprCode(n.getE1(), null);
        return "!" + expr[0];
    }

    @Override
    public Object visit(LtOp l) throws Exception {
        String[] expr = getExprCode(l.getE1(), l.getE2());
        return expr[0] + " < " + expr[1];
    }

    @Override
    public Object visit(LeOp l) throws Exception {
        String[] expr = getExprCode(l.getE1(), l.getE2());
        return expr[0] + " <= " + expr[1];
    }

    @Override
    public Object visit(GtOp g) throws Exception {
        String[] expr = getExprCode(g.getE1(), g.getE2());
        return expr[0] + " > " + expr[1];
    }

    @Override
    public Object visit(GeOp g) throws Exception {
        String[] expr = getExprCode(g.getE1(), g.getE2());
        return expr[0] + " >= " + expr[1];
    }

    @Override
    public Object visit(NeOp n) throws Exception {
        String[] expr = getExprCode(n.getE1(), n.getE2());
        return expr[0] + " != " + expr[1];
    }

    @Override
    public Object visit(EqOp e) throws Exception {
        String[] expr = getExprCode(e.getE1(), e.getE2());
        return expr[0] + " == " + expr[1];
    }

    @Override
    public Object visit(SimpleAssignOp s) throws Exception {
        String name = (String) s.getId().accept(this);
        String[] expr = getExprCode(s.getExpr(), null);
        return name+" = "+expr[0];
    }

    /* STATEMENT */
    @Override
    public Object visit(IfOp i) throws Exception {

        String str = "if(";


        if(i.getExpr() instanceof CallProcOp){
            //Questo cast è possibile poichè callproc è sia statement che expr
            str += getCodeForStmt((StatOp) i.getExpr());
            str = str.replace(";", "");
            str = str.replace("\n", "");
        }else{
            str += ((Visitable) i.getExpr()).accept(this);
        }

        str += "){\n";

        for(StatOp s : i.getStatList()){
            str += "\t"+getCodeForStmt(s);
        }
        str += "}\n";
        //si verifica se vengono usati else if(elif)
        if(i.getElifList() != null){
            for(StatOp s: i.getElifList()){
                str += "\t"+s.accept(this);
            }
        }
        //si verifica se viene utilizzato else
        if(i.getElseOp() != null){
            str += i.getElseOp().accept(this);
        }

        return str;
    }

    @Override
    public Object visit(ElifOp e) throws Exception {

        String str = "else if(";
        if(e.getExpr() instanceof CallProcOp){
            //Questo cast è possibile poichè callproc è sia statement che expr
            str += getCodeForStmt((StatOp) e.getExpr());
            str = str.replace(";", "");
            str = str.replace("\n", "");
        }else{
            str += ((Visitable) e.getExpr()).accept(this);
        }
        str += "){\n";
        for(StatOp s : e.getStatList()){
            str += "\t"+getCodeForStmt(s);
        }
        str += "}\n";

        return str;
    }

    @Override
    public Object visit(ElseOp e) throws Exception {
        String str = "else{\n";

        for(StatOp s : e.getStatList()){
            str += "\t"+getCodeForStmt(s);
        }
        str += "}\n";

        return str;
    }

    @Override
    public Object visit(WhileOp w) throws Exception {

        /* Ci sono due possibili produzioni per while:
         * 1) while statements1 return expr do statements2 od
         * 2) while expr do statements od
         *
         * Nel caso 1, il codice C risultante è il seguente:
         *
         * statements1
         * while(expr){
         *    statements2
         *    statements1
         * }
         * Nel caso 2, il codice C risultante è il seguente:
         *
         * while(condizione){
         *  statements
         * }
         *
         */

        String str = "", stat1 = "";

        if(w.getStatList()!=null){
            for(StatOp s : w.getStatList()){
                stat1 += "\t"+getCodeForStmt(s);
            }
            stat1 += "\n";
        }
        str += stat1 + "while(";

        if(w.getExpr() instanceof CallProcOp){
            //Questo cast è possibile poichè callproc è sia statement che expr
            str += getCodeForStmt((StatOp) w.getExpr());
            str = str.replace(";", "");
            str = str.replace("\n", "");
        }else{
            str += ((Visitable) w.getExpr()).accept(this);
        }

        str += "){\n";
        str += w.getDoOp().accept(this);
        str += stat1;

        return str + "}\n";
    }

    @Override
    public Object visit(DoOp d) throws Exception {

        //Nel linguaggio C non esiste while do, quindi semplicemente si prendono gli statement che andranno nel while
        String str = "";
        for(StatOp s : d.getStatList()){
            str += "\t"+getCodeForStmt(s);
        }

        return str;
    }

    @Override
    public Object visit(ReadOp r) throws Exception {
        String str = "";
        String type;
        String vars = "";

        for(IdLeaf id : r.getIdList()){
            type = ((SymbolTable.VarRow) id.getTableEntry()).getVarType().getValue();
            str += getPlaceHolders(type);
            vars += "&" + id.getIdEntry()+",";
        }
        vars = vars.substring(0,vars.lastIndexOf(","));
        return "scanf(\""+str+"\","+vars+");\n";
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
                    vars += info[1] + ",";
            } else {
                //Nel caso in cui siano costanti
                if(e instanceof StringConstLeaf){
                    String str = (String) ((Visitable) e).accept(this);
                    text += str.replace("\"", "");
                } else {
                    text += ((Visitable) e).accept(this);
                }
            }
        }
        if(!vars.equals("")) {
            vars = vars.substring(0, vars.lastIndexOf(","));
            vars = ", " + vars;
        }
        return "printf(\"" + text + "\"" + vars + ");\n";
    }

    public String getPlaceHolders(String type){
        if(type.equals(Type.INT)){
            return "%d";
        }

        if(type.equals(Type.FLOAT)){
            return "%f";
        }

        if(type.equals(Type.BOOL)){
            return "%d";
        }

        if(type.equals(Type.STRING)){
            return "%s";
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
                int parIndex = info[1].indexOf(")");
                String[] proc = new String[2];
                proc[0] = info[1].substring(0, parIndex);
                proc[1] = info[1].substring(parIndex+1);
                //proc[0] = prova(parametro1, parametro2
                proc[0] += ")";
                String[] info2 = proc[1].split(",");
                values.add(proc[0]);

               for(int i = 0; i < info2.length; i++){
                   values.add(info2[i]);
               }
            } else {
                values.add((String) ((Visitable) e).accept(this));
            }
        }

        for(int i = 0; i < idList.size(); i++){
            str += idList.get(i).getIdEntry() + " = " + values.get(i)+";\n";
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

//        for(String s: types){
//            text += getPlaceHolders(s);
//        }

        String proc_name = c.getId().getIdEntry();
        ArrayList<Expr> actualParam = c.getExprList();

        vars += proc_name + "(";
        if(actualParam != null) {
            for (Expr e2 : actualParam) {
                vars += ((Visitable) e2).accept(this) + ", ";
            }
        }
        int lastCommaIndex = vars.lastIndexOf(",");
        if(lastCommaIndex != -1) {
            vars = vars.substring(0, vars.lastIndexOf(",")) + ")";
        } else {
            vars += ")";
        }

        if(types.size() == 1){ //se la procedura ha un unico tipo di ritorno
            text += getPlaceHolders(types.get(0));
        } else { //la procedura ha più tipi di ritorno
            text += getPlaceHolders(types.get(0));
            for(int i = 1; i<types.size(); i++){
                text += getPlaceHolders(types.get(i));
                vars += proc_name + "_" + i + ", ";
            }
            vars = vars.substring(0,vars.lastIndexOf(","));
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
        currentProcName = p.getId().getIdEntry();

        String str = "";
        ArrayList<String> results = new ArrayList<>();

        //Poichè in C non si può usare un main con ritorno void, si imposta ad int e si utilizza return 0 alla fine
        if(currentProcName.equals("main")) {
            results.add("int");
        } else{
            for (ResultTypeOp r : p.getResultTypeList()) {
                if(r.getValue().equals(ResultTypeOp.BOOL)){
                    results.add("int");
                }else{
                results.add(r.getValue());
                }
            }
        }
        str += results.get(0) + " " + currentProcName+"(";

        for(int i = 1; i < results.size(); i++){
            multipleReturnVars += results.get(i)+" "+currentProcName+"_"+i+";\n";
        }

        if(p.getParamDeclList() != null) {
            for (ParamDeclOp param : p.getParamDeclList()) {
                str += param.accept(this);
            }
        }
        procedurePrototypes += str+");\n";
        str += "){ \n" + p.getProcBody().accept(this)+"\n}\n";

        return str;
    }

    @Override
    public Object visit(ProcOpBody p) throws Exception {
        /*Return solo del primo valore di ritorno (o dell'unico valore)
         *e assegnazione dei valori di ritorno successivi al primo alle variabili globali create precedentemente */

        String str = "", ret = "";

        if(p.getVarDeclList() != null) {
            for (VarDeclOp v : p.getVarDeclList()) {
                str += (v.accept(this)) + "\n";
            }
        }

        if(p.getStatList() != null) {
            for (StatOp s : p.getStatList()) {
                str += getCodeForStmt(s);
            }
        }

        if(currentProcName.equals("main")) {
            ret = "return 0;";
        }else{
            if (p.getReturnExprs() != null) {
                ArrayList<String> exprs = (ArrayList<String>) p.getReturnExprs().accept(this);
                int counter = 1;
                for (int i = 0; i < exprs.size(); i++) {
                    if (i == 0) {
                        ret = "return " + exprs.get(i)+";";
                    } else {
                        str += currentProcName +"_"+ counter + " = " + exprs.get(i) + ";\n";//assegnazione variabili
                        counter++;
                    }
                }
            }
        }
        return str+ret;
    }

    @Override
    public Object visit(ReturnExprs r) throws Exception {
        ArrayList<String> returnExprs = new ArrayList<>();

        for(Expr e : r.getExprList()){
            returnExprs.add((String)((Visitable)e).accept(this));
        }
        return returnExprs;
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
        String str = "";

        if(varDeclList != null) { //potrebbero non esserci variabili globali
            for (VarDeclOp v : varDeclList) {
                ps.println(v.accept(this));
            }
        }

        for(ProcOp pr: procList){
           str += pr.accept(this);
        }

        ps.println(multipleReturnVars);
        procedurePrototypes = procedurePrototypes.replace("int main();","");
        ps.println(procedurePrototypes);
        ps.println(str);
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

        return type + " " + var;
    }

    private String getCodeForStmt(StatOp s) throws Exception{

        String str = "";
        if(s instanceof CallProcOp){
            str += ((String[]) s.accept(this))[1] + "; \n";
        } else {
            str += s.accept(this);
        }
        return str;
    }
}
