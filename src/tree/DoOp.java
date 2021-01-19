package tree;

import java.util.ArrayList;

public class DoOp extends StatOp implements Visitable{

    private ArrayList<StatOp> statList;

    public DoOp(ArrayList<StatOp> statList) {
        super(Components.DO_OP);
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
        return "DoOp{" +
                "statList=" + statList +
                '}';
    }
}
