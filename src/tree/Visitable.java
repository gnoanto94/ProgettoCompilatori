package tree;

public interface Visitable {
    Object accept(Visitor v);
}
