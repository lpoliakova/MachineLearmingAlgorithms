package grammar;

public class Tree {
    private WordType type;
    private Tree firstChild;
    private Tree secondChild;

    public Tree(WordType type) {
        this.type = type;
    }

    public Tree(WordType type, Tree firstChild, Tree secondChild) {
        this.type = type;
        this.firstChild = firstChild;
        this.secondChild = secondChild;
    }

    public WordType getType() {
        return type;
    }

    public Tree getFirstChild() {
        return firstChild;
    }

    public Tree getSecondChild() {
        return secondChild;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tree tree = (Tree) o;

        if (type != tree.type) return false;
        if (firstChild != null ? !firstChild.equals(tree.firstChild) : tree.firstChild != null) return false;
        return secondChild != null ? secondChild.equals(tree.secondChild) : tree.secondChild == null;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (firstChild != null ? firstChild.hashCode() : 0);
        result = 31 * result + (secondChild != null ? secondChild.hashCode() : 0);
        return result;
    }
}
