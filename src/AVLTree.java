//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// int find( x )   --> Return item that matches x
// int findMin( )  --> Return smallest item
// int findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order

/**
 * Implements an AVL tree. Note that all "matching" is based on the compareTo
 * method.
 *
 * @author Mark Allen Weiss
 */
public class AVLTree {

    private AvlNode root;

    /**
     * Construct the tree.
     */
    public AVLTree() {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     *
     * @param x the item to insert.
     */
    public void insert(int x) {
        root = insert(x, root, null);
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     *
     * @param x the item to remove.
     */
    public void remove(int x) {
        removeAVL(this.root, x);
    }

    public void removeAVL(AvlNode p, int x) {
        if (p == null) {
            // der Wert existiert nicht in diesem Baum, daher ist nichts zu tun
            return;
        } else {
            if (p.element > x) {
                removeAVL(p.left, x);
            } else if (p.element < x) {
                removeAVL(p.right, x);
            } else if (p.element == x) {
                // we found the node in the tree.. now lets go on!
                removeFoundNode(p);
            }
        }
    }

    public void removeFoundNode(AvlNode q) {
        AvlNode r;
        // at least one child of q, q will be removed directly
        if (q.left == null || q.right == null) {
            // the root is deleted
            if (q.parent == null) {
                if (q.left != null)
                    this.root = q.left;
                else if (q.right != null)
                    this.root = q.right;
                else
                    this.root = null;
                return;
            }
            r = q;
        } else {
            // q has two children --> will be replaced by successor
            r = successor(q);
            q.element = r.element;
        }

        AvlNode p;
        if (r.left != null) {
            p = r.left;
        } else {
            p = r.right;
        }

        if (p != null) {
            p.parent = r.parent;
        }

        if (r.parent == null) {
            this.root = p;
        } else {
            if (r == r.parent.left) {
                r.parent.left = p;
            } else {
                r.parent.right = p;
            }
            // balancing must be done until the root is reached.
            recursiveheight(r.parent);
        }
        r = null;
    }

    public AvlNode successor(AvlNode q) {
        if (q.right != null) {
            AvlNode r = q.right;
            while (r.left != null) {
                r = r.left;
            }
            return r;
        } else {
            AvlNode p = q.parent;
            while (p != null && q == p.right) {
                q = p;
                p = q.parent;
            }
            return p;
        }
    }

    public void recursiveheight(AvlNode cur) {

        // we do not use the height in this class, but the store it anyway
        int balance = height(cur.right) - height(cur.left);

        // check the height
        if (balance == -2) {

            if (height(cur.left.left) >= height(cur.left.right)) {
                cur = rotateWithLeftChild(cur);
            } else {
                cur = doubleWithLeftChild(cur);
            }
        } else if (balance == 2) {
            if (height(cur.right.right) >= height(cur.right.left)) {
                cur = rotateWithRightChild(cur);
            } else {
                cur = doubleWithRightChild(cur);
            }
        }

        // we did not reach the root yet
        if (cur.parent != null) {
            recursiveheight(cur.parent);
        } else {
            this.root = cur;
            System.out
                    .println("------------ Balancing finished ----------------");
        }
    }

    /**
     * Find the smallest item in the tree.
     *
     * @return smallest item or null if empty.
     */
    public int findMin() {
        return elementAt(findMin(root));
    }

    /**
     * Find the largest item in the tree.
     *
     * @return the largest item of null if empty.
     */
    public int findMax() {
        return elementAt(findMax(root));
    }

    /**
     * Find an item in the tree.
     *
     * @param x the item to search for.
     * @return the matching item or null if not found.
     */
    public int find(int x) {
        return elementAt(find(x, root));
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree() {
        if (isEmpty())
            System.out.println("Empty tree");
        else
            printTree(root);
    }

    /**
     * Internal method to get element field.
     *
     * @param t the node.
     * @return the element field or null if t is null.
     */
    private int elementAt(AvlNode t) {
        //System.out.print(t != 1);
        return t == null ? null : t.element;
    }


    /**
     * Internal method to insert into a subtree.
     *
     * @param x the item to insert.
     * @param t the node that roots the tree.
     * @return the new root.
     */
    private AvlNode insert(int x, AvlNode t, AvlNode parent) {
        if (t == null)
            t = new AvlNode(x, null, null, parent);
        else if (x < t.element) {
            t.left = insert(x, t.left, t);
            if (height(t.left) - height(t.right) == 2)
                if (x < t.left.element)
                    t = rotateWithLeftChild(t);
                else
                    t = doubleWithLeftChild(t);
        } else if (x > t.element) {
            t.right = insert(x, t.right, t);
            if (height(t.right) - height(t.left) == 2)
                if (x > t.right.element)
                    t = rotateWithRightChild(t);
                else
                    t = doubleWithRightChild(t);
        } else
            ; // Duplicate; do nothing
        t.height = max(height(t.left), height(t.right)) + 1;
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     *
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode findMin(AvlNode t) {
        if (t == null)
            return t;

        while (t.left != null)
            t = t.left;
        return t;
    }

    /**
     * Internal method to find the largest item in a subtree.
     *
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode findMax(AvlNode t) {
        if (t == null)
            return t;

        while (t.right != null)
            t = t.right;
        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     *
     * @param x is item to search for.
     * @param t the node that roots the tree.
     * @return node containing the matched item.
     */
    private AvlNode find(int x, AvlNode t) {
        while (t != null) {
            if (x < t.element)
                t = t.left;
            else if (x > t.element)
                t = t.right;
            else
                return t; // Match
        }
        return null; // No match
    }

    /**
     * Internal method to print a subtree in sorted order.
     *
     * @param t the node that roots the tree.
     */
    private void printTree(AvlNode t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private static int height(AvlNode t) {
        return t == null ? -1 : t.height;
    }

    /**
     * Return maximum of lhs and rhs.
     */
    private static int max(int lhs, int rhs) {
        return lhs > rhs ? lhs : rhs;
    }

    /**
     * Rotate binary tree node with left child. For AVL trees, this is a single
     * rotation for case 1. Update heights, then return new root.
     */
    private static AvlNode rotateWithLeftChild(AvlNode k2) {
        AvlNode k1 = k2.left;
        k1.parent = k2.parent;
        k2.left = k1.right;
        if (k2.left != null)
            k2.left.parent = k2;
        k1.right = k2;
        k2.parent = k1;

        if (k1.parent != null) {
            if (k1.parent.right == k2) {
                k1.parent.right = k1;
            } else if (k1.parent.left == k2) {
                k1.parent.left = k1;
            }
        }

        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(height(k1.left), k2.height) + 1;
        return k1;
    }

    /**
     * Rotate binary tree node with right child. For AVL trees, this is a single
     * rotation for case 4. Update heights, then return new root.
     */
    private static AvlNode rotateWithRightChild(AvlNode k1) {
        AvlNode k2 = k1.right;
        k2.parent = k1.parent;
        k1.right = k2.left;
        if (k1.right != null)
            k1.right.parent = k1;
        k2.left = k1;
        k1.parent = k2;

        if (k2.parent != null) {
            if (k2.parent.right == k1) {
                k2.parent.right = k2;
            } else if (k2.parent.left == k1) {
                k2.parent.left = k2;
            }
        }

        k1.height = max(height(k1.left), height(k1.right)) + 1;
        k2.height = max(height(k2.right), k1.height) + 1;
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child with its right child;
     * then node k3 with new left child. For AVL trees, this is a double
     * rotation for case 2. Update heights, then return new root.
     */
    private static AvlNode doubleWithLeftChild(AvlNode k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    /**
     * Double rotate binary tree node: first right child with its left child;
     * then node k1 with new right child. For AVL trees, this is a double
     * rotation for case 3. Update heights, then return new root.
     */
    private static AvlNode doubleWithRightChild(AvlNode k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }

    /**
     * The tree root.
     */

    // Test program
    public static void main(String[] args) {
        AVLTree t = new AVLTree();
        final int NUMS = 13;
        final int GAP = 3;

        System.out.println("Checking... (no more output means success)");

        for (int i = GAP; i != 0; i = (i + GAP) % NUMS)
            t.insert(i);

        // if (NUMS < 40)
        t.printTree();
        t.remove(2);
        t.remove(8);
        t.remove(12);
        t.insert(20);
        t.printTree();
        if (t.findMin() != 1 || t.findMax() != NUMS - 1)
            System.out.println("FindMin or FindMax error!");

        for (int i = 1; i < NUMS; i++)
            //if (t.find(i) != i)
            System.out.println("Find error1!");
    }
}

// Basic node stored in AVL trees
// Note that this class is not accessible outside
// of package DataStructures

class AvlNode {
    // Constructors
    AvlNode(int theElement) {
        this(theElement, null, null, null);
    }

    AvlNode(int theElement, AvlNode lt, AvlNode rt, AvlNode parent) {
        element = theElement;
        left = lt;
        right = rt;
        this.parent = parent;
        height = 0;
    }

    // Friendly data; accessible by other package routines
    int element; // The data in the node
    AvlNode left; // Left child
    AvlNode right; // Right child
    AvlNode parent;// Parent
    int height; // Height
}