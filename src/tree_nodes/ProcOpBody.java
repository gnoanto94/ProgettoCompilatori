package tree_nodes;

import java.util.ArrayList;

public class ProcOpBody extends Node implements Visitable{

    private ArrayList<VarDeclOp> varDeclList;
    private ArrayList<StatOp> statList;
    private ReturnExprs returnExprs;
    
    public ProcOpBody(ArrayList<VarDeclOp> varDeclList, ArrayList<StatOp> statList, ReturnExprs returnExprs) {
        super(Components.PROC_BODY_OP);
        this.varDeclList = varDeclList;
        this.statList = statList;
        this.returnExprs = returnExprs;
    }

    public ArrayList<VarDeclOp> getVarDeclList() {
        return varDeclList;
    }

    public void setVarDeclList(ArrayList<VarDeclOp> varDeclList) {
        this.varDeclList = varDeclList;
    }

    public ArrayList<StatOp> getStatList() {
        return statList;
    }

    public void setStatList(ArrayList<StatOp> statList) {
        this.statList = statList;
    }

    public ReturnExprs getReturnExprs() {
        return returnExprs;
    }

    public void setReturnExprs(ReturnExprs returnExprs) {
        this.returnExprs = returnExprs;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "ProcOpBody{" +
                "varDeclList=" + varDeclList +
                ", statList=" + statList +
                ", returnExprs=" + returnExprs +
                '}';
    }
}
