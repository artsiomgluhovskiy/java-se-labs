package org.art.java_core.algorithms.data_structures;

/**
 * Binary Tree node implementation.
 */
public class TreeNode {

    public int value;
    public TreeNode left;
    public TreeNode right;
    public TreeNode parent;

    public TreeNode(int value, TreeNode left, TreeNode right, TreeNode parent) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "TreeNode {" +
                " value = " + value +
                ", left value = " + (left != null ? left.value : "null") +
                ", right value = " + (right != null ? right.value : "null") +
                ", parent value = " + (parent != null ? parent.value : "null") +
                " }";
    }
}
