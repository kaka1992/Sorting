import java.util.Stack;


public class TreeAlgorithm {

    public static void main(String[] args) {
        Ttree tree = new Ttree("a");
        tree.addLChild(tree.root, new TtreeNode("b"));
        tree.addRChild(tree.root, new TtreeNode("c"));
        tree.addLChild(tree.root.lchild, new TtreeNode("d"));
        tree.addRChild(tree.root.lchild, new TtreeNode("e"));
        tree.traversal_last_no(tree.root);


        Dtree dtree = new Dtree();
        dtree.setString("asdgasg");
        dtree.setString("hrthrth");
    }

	/*
	 *            a
	 *        b       c
	 *     d      e 
	 * 
	 * 
	 * */

}

class Ttree {

    public TtreeNode root;

    public Ttree(String value) {
        root = new TtreeNode();
        root.value = value;
    }

    public void addLChild(TtreeNode root, TtreeNode node) {
        root.lchild = node;
    }

    public void addRChild(TtreeNode root, TtreeNode node) {
        root.rchild = node;
    }

    public String setLnode(TtreeNode fnode, String value) {
        if (fnode == null) {
            return null;
        }
        if (fnode.lchild != null) {
            String result = fnode.lchild.value;
            fnode.lchild.value = value;
            return result;
        } else {
            fnode.lchild = new TtreeNode();
            fnode.lchild.value = value;
            return value;
        }
    }

    public String setRnode(TtreeNode fnode, String value) {
        if (fnode == null) {
            return null;
        }
        if (fnode.rchild != null) {
            String result = fnode.rchild.value;
            fnode.rchild.value = value;
            return result;
        } else {
            fnode.rchild = new TtreeNode();
            fnode.rchild.value = value;
            return value;
        }
    }

    public void traversal_first(TtreeNode root) {
        if (root != null) {
            System.out.print(root.value + "-");
            traversal_first(root.lchild);
            traversal_first(root.rchild);
        }
    }

    public void traversal_first_no(TtreeNode root) {
        Stack<TtreeNode> buf = new Stack<TtreeNode>();
        buf.push(root);
        while (!buf.empty()) {
            TtreeNode temp = buf.pop();
            System.out.print(temp.value + "-");
            if (temp.rchild != null) buf.push(temp.rchild);
            if (temp.lchild != null) buf.push(temp.lchild);
        }
    }

    public void traversal_mid(TtreeNode root) {
        if (root != null) {
            traversal_mid(root.lchild);
            System.out.print(root.value + "-");
            traversal_mid(root.rchild);
        }
    }

    public void traversal_mid_no(TtreeNode root) {
        Stack<TtreeNode> buf = new Stack<TtreeNode>();
        TtreeNode temp = root;
        while (temp != null || !buf.empty()) {
            while (temp != null) {
                buf.push(temp);
                temp = temp.lchild;
            }
            if (!buf.empty()) {
                temp = buf.pop();
                System.out.print(temp.value + "-");
                temp = temp.rchild;
            }
        }
    }

    public void traversal_last(TtreeNode root) {
        if (root != null) {
            traversal_last(root.lchild);
            traversal_last(root.rchild);
            System.out.print(root.value + "-");
        }
    }

    public void traversal_last_no(TtreeNode root) {
        Stack<TtreeNode> buf = new Stack<TtreeNode>();
        TtreeNode temp = root;
        TtreeNode cur = null;
        while (temp != null || !buf.empty()) {
            while (temp != null) {
                buf.push(temp);
                temp = temp.lchild;
            }
            temp = buf.peek();
            if (temp.rchild == null || temp.rchild == cur) {
                buf.pop();
                System.out.print(temp.value + "-");
                temp = null;
            } else {
                temp = temp.rchild;
                cur = temp;
            }
        }
    }
}

class TtreeNode {
    TtreeNode rchild;
    TtreeNode lchild;
    String value;

    public TtreeNode(String value) {
        this.value = value;
    }

    public TtreeNode() {

    }

}

class NtreeNode {
    NtreeNode[] child;
    String value;
}

class DtreeNode {
    public static final int size = 26;
    DtreeNode[] child;
    int count;

    public DtreeNode(boolean i) {
        if (i)
            child = new DtreeNode[this.size];
        count = 0;
    }

    public DtreeNode() {
        count = 0;
    }

}


class Dtree {
    DtreeNode root;

    public Dtree() {
        root = new DtreeNode(true);
    }

    public void setString(String line) {
        DtreeNode tree = root;
        if (line == null & line.equals(""))
            return;
        line = line.toLowerCase();
        char[] temp = line.toCharArray();
        for (int i = 0; i < temp.length - 1; i++) {
            int num = temp[i] - 'a';
            if (tree.child[num] == null) {
                tree.child[num] = new DtreeNode(true);
            }
            tree.child[num].count++;
            tree = tree.child[num];
        }
    }

}


