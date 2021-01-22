package code_generation;

import syntaxanalysis.SymbolTable;
import tree_nodes.*;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

public class CodeGeneratorVisitor implements Visitor {

    private String programName;
    private PrintStream ps;
    private boolean typeRequired = false;

    private static final int TRUE = 1;
    private static final int FALSE = 0;

    public CodeGeneratorVisitor(String programName) throws FileNotFoundException {
        //si elimina il .toy dal nome del file e lo si trasforma in .c
        this.programName = (programName.substring(0, programName.indexOf('.'))) + ".c";
        this.ps = new PrintStream(programName);
        //verrà sempre inclusa
        ps.println("#include<stdio.h>"); //I/O
        ps.println("#include<stdlib.h>");
        ps.println("#include<string.h>"); //per lavorare con le stringhe
        ps.println();
    }

    @Override
    public Object visit(FloatConstLeaf f) {
        return null;
    }

    @Override
    public Object visit(IntConstLeaf i) {
        return null;
    }

    @Override
    public Object visit(IdLeaf i) throws Exception {
//        String type, name;
//        type = ((SymbolTable.VarRow) i.getTableEntry()).getVarType().getValue();
//        name = i.getTableEntry().getLessema();
//
//        if(type.equals(Type.STRING)){
//            type = "char";
//            name = name + "[]";
//        } else if(type.equals(Type.BOOL)){
//            type = "int";
//        }
//
//        if(typeRequired){
//            ps.println(type + " " + name);
//        } else {
//            ps.println(name);
//        }
        return null;
    }

    @Override
    public Object visit(StringConstLeaf s) {
        ps.print("\"" + s.getValue() + "\"");
        return null;
    }

    @Override
    public Object visit(NullLeaf n) {
        return null;
    }

    @Override
    public Object visit(TrueLeaf t) {
        return null;
    }

    @Override
    public Object visit(FalseLeaf f) {
        return null;
    }

    @Override
    public Object visit(PlusOp p) throws Exception {
        return null;
    }

    @Override
    public Object visit(MinusOp m) throws Exception {
        return null;
    }

    @Override
    public Object visit(UMinusOp u) throws Exception {
        return null;
    }

    @Override
    public Object visit(MultOp m) throws Exception {
        return null;
    }

    @Override
    public Object visit(DivOp d) throws Exception {
        return null;
    }

    @Override
    public Object visit(AndOp a) throws Exception {
        return null;
    }

    @Override
    public Object visit(OrOp o) throws Exception {
        return null;
    }

    @Override
    public Object visit(NotOp n) throws Exception {
        return null;
    }

    @Override
    public Object visit(LtOp l) throws Exception {
        return null;
    }

    @Override
    public Object visit(LeOp l) throws Exception {
        return null;
    }

    @Override
    public Object visit(GtOp g) throws Exception {
        return null;
    }

    @Override
    public Object visit(GeOp g) throws Exception {
        return null;
    }

    @Override
    public Object visit(NeOp n) throws Exception {
        return null;
    }

    @Override
    public Object visit(EqOp e) throws Exception {
        return null;
    }

    @Override
    public Object visit(SimpleAssignOp s) throws Exception {
        return null;
    }

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
        return null;
    }

    @Override
    public Object visit(WriteOp w) throws Exception {
        return null;
    }

    @Override
    public Object visit(MultipleAssignOp m) throws Exception {
        return null;
    }

    @Override
    public Object visit(CallProcOp c) throws Exception {
        return null;
    }

    @Override
    public Object visit(IdListInitOp i) throws Exception {
        for(IdListInit id: i.getIdListInit()){
            ((Visitable) id).accept(this);
        }
        return null;
    }

    @Override
    public Object visit(ParamDeclOp p) throws Exception {
        return null;
    }

    @Override
    public Object visit(ResultTypeOp r) {
        return null;
    }

    @Override
    public Object visit(ProcOp p) throws Exception {
        return null;
    }

    @Override
    public Object visit(ProcOpBody p) throws Exception {
        return null;
    }

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
    public Object visit(ReturnExprs r) throws Exception {
        return null;
    }

    @Override
    public Object visit(Type t) {
        return null;
    }

    @Override
    public Object visit(VarDeclOp v) throws Exception {
            v.getIdListInit().accept(this);
        return null;
    }
}
