package tree;

import syntaxanalysis.Env;

import java.util.ArrayList;

public class ProcOp extends Node implements Visitable{

    private IdLeaf id;
    private ArrayList<ParamDeclOp> paramDeclList;
    private ArrayList<ResultTypeOp> resultTypeList;
    private ProcOpBody procBody;

    private Env procSymbolTable;

    public ProcOp(IdLeaf id, ArrayList<ParamDeclOp> paramDeclList, ArrayList<ResultTypeOp> resultTypeList,
                  ProcOpBody procBody, Env procSymbolTable) {
        super(Components.PROC_OP);
        this.id = id;
        this.paramDeclList = paramDeclList;
        this.resultTypeList = resultTypeList;
        this.procBody = procBody;
        this.procSymbolTable = procSymbolTable;
    }

    public IdLeaf getId() {
        return id;
    }

    public void setId(IdLeaf id) {
        this.id = id;
    }

    public ArrayList<ParamDeclOp> getParamDeclList() {
        return paramDeclList;
    }

    public void setParamDeclList(ArrayList<ParamDeclOp> paramDeclList) {
        this.paramDeclList = paramDeclList;
    }

    public ArrayList<ResultTypeOp> getResultTypeList() {
        return resultTypeList;
    }

    public void setResultTypeList(ArrayList<ResultTypeOp> resultTypeList) {
        this.resultTypeList = resultTypeList;
    }

    public ProcOpBody getProcBody() {
        return procBody;
    }

    public void setProcBody(ProcOpBody procBody) {
        this.procBody = procBody;
    }

    public Env getProcSymbolTable() {
        return procSymbolTable;
    }

    public void setProcSymbolTable(Env procSymbolTable) {
        this.procSymbolTable = procSymbolTable;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "ProcOp{" +
                "id=" + id +
                ", paramDeclList=" + paramDeclList +
                ", resultTypeList=" + resultTypeList +
                ", procBody=" + procBody +
                '}';
    }
}
