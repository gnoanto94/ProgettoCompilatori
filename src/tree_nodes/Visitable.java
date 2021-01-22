package tree_nodes;

public interface Visitable {
    Object accept(Visitor v) throws Exception;
}
