package tree_nodes;

public class StatOp extends Node implements Visitable{

    public StatOp(){
        //costruttore necessario poichè StatOp verrà utilizzata come superclasse
    }

    public StatOp(String name){
        super(name);
    }
    public Object accept(Visitor v) throws Exception {return null;}

    //da utilizzare come superclasse di whilestat, readlnstat etc
}
