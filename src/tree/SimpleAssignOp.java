package tree;

public class SimpleAssignOp extends Node implements IdListInit, Visitable {
    private IdLeaf id;
    private Expr expr;

    public SimpleAssignOp(IdLeaf id, Expr expr) {
        super(Components.SIMPLE_ASSIGN);
        this.id = id;
        this.expr = expr;
    }

    public IdLeaf getId() {
        return id;
    }

    public void setId(IdLeaf id) {
        this.id = id;
    }

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "SimpleAssignOp{" +
                "id=" + id +
                ", expr=" + expr +
                '}';
    }
}
