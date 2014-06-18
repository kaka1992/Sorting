
public class RBTree {
	
	RBNode root;
	RBNode nil;
	
	RBNode grandparent(RBNode n){
		return n.parent != null?n.parent.parent : null;
	}
	
	RBNode uncle(RBNode n){
		if(n.parent == null)
			return null;
		else if(n.parent == grandparent(n).left)
			return grandparent(n).right;
		else
			return grandparent(n).left;
	}
	
	Boolean is_leaf(RBNode n){
		if(n == null)
			return null;
		if(n.left == null && n.right == null)
			return true;
		return false;
	}
	
	RBNode sibling(RBNode n){
		if(n == n.parent.left)
			return n.parent.right;
		else
			return n.parent.left;
	}
	
	RBNode replace_node(RBNode n, RBNode child){
		  return null;
	}
		
	void delete_one_chile(RBNode n){
		RBNode child = is_leaf(n.right) ? n.left: n.right;
		
	}
	
	void insert_case1(RBNode n){
		if(n.parent == null){
			n.color = COLOR.BLACK;
		}
		else
			insert_case2(n);
	}
	
	void insert_case2(RBNode n){
		if(n.parent.color == COLOR.BLACK)
			return;
		insert_case3(n);
	}
	
	void insert_case3(RBNode n){
		if(uncle(n) != null && uncle(n).color == COLOR.RED){
			n.parent.color = COLOR.BLACK;
			grandparent(n).color = COLOR.RED;
			uncle(n).color = COLOR.BLACK;
			insert_case1(grandparent(n));
		}else
			insert_case4(n);
	}
	
	void insert_case4(RBNode n){
		if(n == n.parent.right && n.parent == grandparent(n).left){
			n = rotateWithRightChild(n.parent);
			n = n.left;
		}else if(n == n.parent.left && n.parent == grandparent(n).right){
			n = rotateWithLeftChild(n.parent);
			n = n.right;
		}
		insert_case5(n);
	}
	
	void insert_case5(RBNode n){
		n.parent.color = COLOR.BLACK;
		grandparent(n).color = COLOR.RED;
		if(n == n.parent.left && n.parent == grandparent(n).left){
			rotateWithLeftChild(grandparent(n));
		}else{
			rotateWithRightChild(grandparent(n));
		}
	}	
	
	
	
	private static RBNode rotateWithLeftChild(RBNode k2) {
		RBNode k1 = k2.left;
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
		return k1;
	}

	/**
	 * Rotate binary tree node with right child. For AVL trees, this is a single
	 * rotation for case 4. Update heights, then return new root.
	 */
	private static RBNode rotateWithRightChild(RBNode k1) {
		RBNode k2 = k1.right;
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

		return k2;
	}

	/**
	 * Double rotate binary tree node: first left child with its right child;
	 * then node k3 with new left child. For AVL trees, this is a double
	 * rotation for case 2. Update heights, then return new root.
	 */
	private static RBNode doubleWithLeftChild(RBNode k3) {
		k3.left = rotateWithRightChild(k3.left);
		return rotateWithLeftChild(k3);
	}

	/**
	 * Double rotate binary tree node: first right child with its left child;
	 * then node k1 with new right child. For AVL trees, this is a double
	 * rotation for case 3. Update heights, then return new root.
	 */
	private static RBNode doubleWithRightChild(RBNode k1) {
		k1.right = rotateWithLeftChild(k1.right);
		return rotateWithRightChild(k1);
	}
	
}

class RBNode {
	// Constructors
	RBNode(int theElement) {
		this(theElement, null, null, COLOR.RED, null);
	}

	RBNode(int theElement, RBNode lt, RBNode rt, COLOR color, RBNode parent) {
		element = theElement;
		left = lt;
		right = rt;
		this.color = color;
		this.parent = parent;
	}

	// Friendly data; accessible by other package routines
	int element; // The data in the node
	RBNode left; // Left child
	RBNode right; // Right child
	COLOR color; // COLOR
	RBNode parent; // Parent
}

enum COLOR{
	RED,
	BLACK
}
