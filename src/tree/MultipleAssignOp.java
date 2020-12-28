package tree;

import java.util.ArrayList;

public class MultipleAssignOp extends StatOp implements Visitable{

    private ArrayList<IdLeaf> idList;
    private ArrayList<Expr> exprList;

    public MultipleAssignOp(ArrayList<IdLeaf> idList, ArrayList<Expr> exprList) {
        super(Components.MULTIPLE_ASSIGN_OP);
        this.idList = idList;
        this.exprList = exprList;
    }

    public ArrayList<IdLeaf> getIdList() {
        return idList;
    }

    public void setIdList(ArrayList<IdLeaf> idList) {
        this.idList = idList;
    }

    public ArrayList<Expr> getExprList() {
        return exprList;
    }

    public void setExprList(ArrayList<Expr> exprList) {
        this.exprList = exprList;
    }

    //reminder: indice uguale dei due array = (nomevar, valore)
    //ad esempio: idList.get(i) := exprList.get(i)

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "MultipleAssignOp{" +
                "idList=" + idList +
                ", exprList=" + exprList +
                '}';
    }
}
