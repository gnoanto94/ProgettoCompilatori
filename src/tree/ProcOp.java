package tree;

import java.util.ArrayList;

public class ProcOp extends Node implements Visitable{

    private IdLeaf id;
    private ArrayList<ParamDeclOp> paramDeclList;
    private ArrayList<ResultTypeOp> resultTypeList;
    private ProcOpBody procBody;

    public ProcOp(IdLeaf id, ArrayList<ParamDeclOp> paramDeclList, ArrayList<ResultTypeOp> resultTypeList, ProcOpBody procBody) {
        super(Components.PROC_OP);
        this.id = id;
        this.paramDeclList = paramDeclList;
        this.resultTypeList = resultTypeList;
        this.procBody = procBody;
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

    @Override
    public Object accept(Visitor v) {
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
