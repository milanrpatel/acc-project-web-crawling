package hotelprice;

/**
 * Class representing SplayTree.
 */
class SplayTree {
    public SearchedQueryFrequency root;

    /**
     * Constructor to initialize an empty SplayTree.
     */
    public SplayTree() {
        this.root = null;
    }

    /**
     * Method to find the maximum node in a subtree rooted at x.
     * 
     * @param x The root node of the subtree.
     * @return The node with the maximum value in the subtree.
     */
    public SearchedQueryFrequency maximum(SearchedQueryFrequency x) {
        while (x.right != null)
            x = x.right;
        return x;
    }

    /**
     * Method to perform a left rotation on the SplayTree.
     * 
     * @param x The node to perform left rotation on.
     */
    public void leftRotate(SearchedQueryFrequency x) {
        SearchedQueryFrequency y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) { // x is root
            this.root = y;
        } else if (x == x.parent.left) { // x is left child
            x.parent.left = y;
        } else { // x is right child
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    /**
     * Method to perform a right rotation on the SplayTree.
     * 
     * @param x The node to perform left rotation on.
     */
    public void rightRotate(SearchedQueryFrequency x) {
        SearchedQueryFrequency y = x.left;
        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) { // x is root
            this.root = y;
        } else if (x == x.parent.right) { // x is left child
            x.parent.right = y;
        } else { // x is right child
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    /**
     * Method to splay a node in the SplayTree.
     * 
     * @param n The node to splay.
     */
    public void splay(SearchedQueryFrequency n) {
        while (n.parent != null) { // SearchedQueryFrequency is not root
            if (n.parent == this.root) { // SearchedQueryFrequency is child of root, one rotation
                if (n == n.parent.left) {
                    this.rightRotate(n.parent);
                } else {
                    this.leftRotate(n.parent);
                }
            } else {
                SearchedQueryFrequency p = n.parent;
                SearchedQueryFrequency g = p.parent; // grandparent

                if (n.parent.left == n && p.parent.left == p) { // both are left children
                    this.rightRotate(g);
                    this.rightRotate(p);
                } else if (n.parent.right == n && p.parent.right == p) { // both are right children
                    this.leftRotate(g);
                    this.leftRotate(p);
                } else if (n.parent.right == n && p.parent.left == p) {
                    this.leftRotate(p);
                    this.rightRotate(g);
                } else if (n.parent.left == n && p.parent.right == p) {
                    this.rightRotate(p);
                    this.leftRotate(g);
                }
            }
        }
    }

    /**
     * Method to insert a node into the SplayTree.
     * 
     * @param n The node to insert.
     */
    public void insert(SearchedQueryFrequency n) {
        SearchedQueryFrequency y = null;
        SearchedQueryFrequency temp = this.root;
        while (temp != null) {
            y = temp;
            if (n.compareTo(temp) < 0)
                temp = temp.left;
            else
                temp = temp.right;
        }
        n.parent = y;

        if (y == null) // Make Root newly added searched query frequency
            this.root = n;
        else if (n.compareTo(y) < 0)
            y.left = n;
        else
            y.right = n;

        this.splay(n);
    }

    /**
     * Method to search for a node in the SplayTree.
     * 
     * @param x The node to search for.
     * @return The found node.
     */
    public SearchedQueryFrequency search(SearchedQueryFrequency x) {
        return search(root, x);
    }

    private SearchedQueryFrequency search(SearchedQueryFrequency n, SearchedQueryFrequency x) {
        if (n == null)
            return null;
        if (x.compareTo(n) == 0) {
            this.splay(n);
            return n;
        } else if (x.compareTo(n) < 0)
            return this.search(n.left, x);
        else if (x.compareTo(n) > 0)
            return this.search(n.right, x);
        else
            return null;
    }

    /**
     * Method to delete a node from the SplayTree.
     * 
     * @param n The node to delete.
     */
    public void delete(SearchedQueryFrequency n) {
        this.splay(n);

        SplayTree leftSubtree = new SplayTree();
        leftSubtree.root = this.root.left;
        if (leftSubtree.root != null)
            leftSubtree.root.parent = null;

        SplayTree rightSubtree = new SplayTree();
        rightSubtree.root = this.root.right;
        if (rightSubtree.root != null)
            rightSubtree.root.parent = null;

        if (leftSubtree.root != null) {
            SearchedQueryFrequency m = leftSubtree.maximum(leftSubtree.root);
            leftSubtree.splay(m);
            leftSubtree.root.right = rightSubtree.root;
            this.root = leftSubtree.root;
        } else {
            this.root = rightSubtree.root;
        }
    }

    /**
     * Method to perform an inorder traversal of the SplayTree.
     * 
     * @param n The root node for traversal.
     */
    public void inorder(SearchedQueryFrequency n) {
        if (n != null) {
            inorder(n.left);
            System.out.println(n.getQuery());
            inorder(n.right);
        }
    }

    /**
     * Main method to demonstrate SplayTree functionalities.
     * 
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        SplayTree t = new SplayTree();

        t.insert(new SearchedQueryFrequency("hotels", 1));
        System.out.println(t.root.getQuery());
        t.insert(new SearchedQueryFrequency("toronto", 1));
        System.out.println(t.root.getQuery());
        t.search(new SearchedQueryFrequency("hotels", 1));
        System.out.println(t.root.getQuery());
    }
}