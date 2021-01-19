package tree;

import java.util.ArrayList;

public class ProgramOp extends Node implements Visitable{

    private ArrayList<VarDeclOp> varDeclList;
    private ArrayList<ProcOp> procOpList;

    public ProgramOp(ArrayList<VarDeclOp> varDeclList, ArrayList<ProcOp> procOpList) {
        super(Components.PROGRAM_OP);
        this.varDeclList = varDeclList;
        this.procOpList = procOpList;
    }

    public ArrayList<VarDeclOp> getVarDeclList() {
        return varDeclList;
    }

    public void setVarDeclList(ArrayList<VarDeclOp> varDeclList) {
        this.varDeclList = varDeclList;
    }

    public ArrayList<ProcOp> getProcOpList() {
        return procOpList;
    }

    public void setProcOpList(ArrayList<ProcOp> procOpList) {
        this.procOpList = procOpList;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "ProgramOp{" +
                "\nvarDeclList=" + varDeclList +
                "\n, procOpList=" + procOpList +
                '}';
    }
}
