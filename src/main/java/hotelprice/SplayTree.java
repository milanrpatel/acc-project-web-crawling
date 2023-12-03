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
     * Finds the maximum node in a subtree rooted at xVar.
     * 
     * @param xVar The root node of the subtree.
     * @return The node with the maximum value in the subtree.
     */
    public SearchedQueryFrequency maximum(SearchedQueryFrequency xVar) {
        while (xVar.right != null)
            xVar = xVar.right;
        return xVar;
    }

    /**
     * Performs a left rotation on the SplayTree.
     * 
     * @param xVar The node to perform left rotation on.
     */
    public void leftRotate(SearchedQueryFrequency xVar) {
        SearchedQueryFrequency yVar = xVar.right;
        xVar.right = yVar.left;
        if (yVar.left != null) {
            yVar.left.parent = xVar;
        }
        yVar.parent = xVar.parent;
        if (xVar.parent == null) { // xVar is root
            this.root = yVar;
        } else if (xVar == xVar.parent.left) { // xVar is left child
            xVar.parent.left = yVar;
        } else { // xVar is right child
            xVar.parent.right = yVar;
        }
        yVar.left = xVar;
        xVar.parent = yVar;
    }

    /**
     * Performs a right rotation on the SplayTree.
     * 
     * @param xVar The node to perform left rotation on.
     */
    public void rightRotate(SearchedQueryFrequency xVar) {
        SearchedQueryFrequency yVar = xVar.left;
        xVar.left = yVar.right;
        if (yVar.right != null) {
            yVar.right.parent = xVar;
        }
        yVar.parent = xVar.parent;
        if (xVar.parent == null) { // xVar is root
            this.root = yVar;
        } else if (xVar == xVar.parent.right) { // xVar is left child
            xVar.parent.right = yVar;
        } else { // xVar is right child
            xVar.parent.left = yVar;
        }
        yVar.right = xVar;
        xVar.parent = yVar;
    }

    /**
     * Splays a node in the SplayTree to bring it to the root.
     * 
     * @param nodeVar The node to splay.
     */
    public void splay(SearchedQueryFrequency nodeVar) {
        while (nodeVar.parent != null) { // SearchedQueryFrequency is not root
            if (nodeVar.parent == this.root) { // SearchedQueryFrequency is child of root, one rotation
                if (nodeVar == nodeVar.parent.left) {
                    this.rightRotate(nodeVar.parent);
                } else {
                    this.leftRotate(nodeVar.parent);
                }
            } else {
                SearchedQueryFrequency parentVar = nodeVar.parent;
                SearchedQueryFrequency gpVar = parentVar.parent; // grandparent

                if (nodeVar.parent.left == nodeVar && parentVar.parent.left == parentVar) { // both are left children
                    this.rightRotate(gpVar);
                    this.rightRotate(parentVar);
                } else if (nodeVar.parent.right == nodeVar && parentVar.parent.right == parentVar) { // both are right
                                                                                                     // children
                    this.leftRotate(gpVar);
                    this.leftRotate(parentVar);
                } else if (nodeVar.parent.right == nodeVar && parentVar.parent.left == parentVar) {
                    this.leftRotate(parentVar);
                    this.rightRotate(gpVar);
                } else if (nodeVar.parent.left == nodeVar && parentVar.parent.right == parentVar) {
                    this.rightRotate(parentVar);
                    this.leftRotate(gpVar);
                }
            }
        }
    }

    /**
     * Inserts a new node into the SplayTree.
     * 
     * @param n The node to insert.
     */
    public void insert(SearchedQueryFrequency n) {
        SearchedQueryFrequency yVar = null;
        SearchedQueryFrequency temp = this.root;
        while (temp != null) {
            yVar = temp;
            if (n.compareTo(temp) < 0)
                temp = temp.left;
            else
                temp = temp.right;
        }
        n.parent = yVar;

        if (yVar == null) // Make Root newly added searched query frequency
            this.root = n;
        else if (n.compareTo(yVar) < 0)
            yVar.left = n;
        else
            yVar.right = n;

        this.splay(n);
    }

    /**
     * Method to search for a node in the SplayTree.
     * 
     * @param xVar The node to search for.
     * @return The found node.
     */
    public SearchedQueryFrequency search(SearchedQueryFrequency xVar) {
        return search(root, xVar);
    }

    /**
     * Searches for a node in the SplayTree.
     * 
     * @param xVar The node to search for.
     * @return The found node.
     */
    private SearchedQueryFrequency search(SearchedQueryFrequency n, SearchedQueryFrequency xVar) {
        if (n == null)
            return null;
        if (xVar.compareTo(n) == 0) {
            this.splay(n);
            return n;
        } else if (xVar.compareTo(n) < 0)
            return this.search(n.left, xVar);
        else if (xVar.compareTo(n) > 0)
            return this.search(n.right, xVar);
        else
            return null;
    }

    /**
     * Deletes a node from the SplayTree.
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

    // Traversal:

    /**
     * Performs an inorder traversal of the SplayTree.
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
     * Demonstrates SplayTree functionalities.
     * 
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        SplayTree t = new SplayTree();

        // Demonstration of functionalities
        t.insert(new SearchedQueryFrequency("hotels", 1));
        System.out.println(t.root.getQuery());
        t.insert(new SearchedQueryFrequency("toronto", 1));
        System.out.println(t.root.getQuery());
        t.search(new SearchedQueryFrequency("hotels", 1));
        System.out.println(t.root.getQuery());
    }
}