package tree;

import java.util.ArrayList;

public class ElseOp extends StatOp implements Visitable{

    private ArrayList<StatOp> statList;

    public ElseOp(ArrayList<StatOp> statList) {
        super(Components.ELSE_OP);
        this.statList = statList;
    }

    public ArrayList<StatOp> getStatList() {
        return statList;
    }

    public void setStatList(ArrayList<StatOp> statList) {
        this.statList = statList;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "ElseOp{" +
                "statList=" + statList +
                '}';
    }
}
