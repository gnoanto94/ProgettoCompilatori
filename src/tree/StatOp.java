package tree;

public class StatOp extends Node{

    public StatOp(){
        //costruttore necessario poichè StatOp verrà utilizzata come superclasse
    }

    public StatOp(String name){
        super(name);
    }

    //da utilizzare come superclasse di whilestat, readlnstat etc


}
