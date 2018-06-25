package org.art.java_core.algorithms.data_structures.utils;

import org.art.java_core.algorithms.data_structures.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreeUtils {

    /**
     * Recursively walks through the binary tree and puts the values
     * into a list data structure in a sorted order.
     *
     * @param list the list to put the values
     * @param root the root of the binary tree
     * @return the list of tree values (in a sorted order)
     */
    public static List<Integer> inoderTreeWalk(TreeNode root, List<Integer> list) {
        if (root != null) {
            inoderTreeWalk(root.left, list);
            list.add(root.value);
            inoderTreeWalk(root.right, list);
        }
        return list;
    }

    /**
     * Searches the value in the tree and return the
     * reference to the tree node with such value.
     */
    public static TreeNode search(TreeNode root, int value) {
        if (root == null || root.value == value) {
            return root;
        }
        if (value < root.value) {
            return search(root.left, value);
        } else {
            return search(root.right, value);
        }
    }

    /**
     * Inserts the node into an appropriate position in the tree.
     *
     * @param root the tree to insert the node
     * @param node the node to insert
     */
    public static void insertNode(TreeNode root, TreeNode node) {
        TreeNode y = null;
        TreeNode x = root;
        if (x == null) {
            root = node;
            return;
        }
        while (x != null) {
            y = x;
            if (node.value < x.value) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        node.parent = y;
        if (node.value < y.value) {
            y.left = node;
        } else {
            y.right = node;
        }
    }

    /**
     * Deletes the node from the tree.
     *
     * @param root the root of the tree
     * @param target the target node to delete
     */
    public static void deleteNode(TreeNode root, TreeNode target) {
        if (target.left == null) {
            transplant(root, target, target.right);
        } else if (target.right == null) {
            transplant(root, target, target.left);
        } else {
            TreeNode minNode = minimum(target.right);
            if (minNode.parent != target) {
                transplant(root, minNode, minNode.right);
                minNode.right = target.right;
                minNode.right.parent = minNode;
            }
            transplant(root, target, minNode);
            minNode.left = target.left;
            minNode.left.parent = minNode;
        }
    }

    private static void transplant(TreeNode root, TreeNode target, TreeNode replacement) {
        if (target.parent == null) {
            root = replacement;
        } else if (target.parent.right == target) {
            target.parent.right = replacement;
        } else if (target.parent.left == target) {
            target.parent.left = replacement;
        }
        if (replacement != null) {
            replacement.parent = target.parent;
        }
    }

    private static TreeNode minimum(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    private static void leftRotate(TreeNode root, TreeNode nodeToRotate) {
        TreeNode y = nodeToRotate.right;
        nodeToRotate.right = y.left;
        if (y.left != null) {
            y.left.parent = nodeToRotate;
        }
        y.parent = nodeToRotate.parent;
        if (nodeToRotate.parent == null) {
            root = y;
        } else if (nodeToRotate == nodeToRotate.parent.left) {
            nodeToRotate.parent.left = y;
        } else {
            y.left = nodeToRotate;
        }
        y.left = nodeToRotate;
        nodeToRotate.parent = y;
    }

    public static void main(String[] args) {

        TreeNode root = new TreeNode(6, null, null, null);
        TreeNode node2 = new TreeNode(5, null, null, null);
        TreeNode node3 = new TreeNode(7, null, null, null);
        TreeNode node4 = new TreeNode(2, null, null, null);
        TreeNode node5 = new TreeNode(5, null, null, null);
        TreeNode node6 = new TreeNode(8, null, null, null);

        //Insertion test
        List<Integer> list0 = new ArrayList<>();
        insertNode(root, node2);
        insertNode(root, node3);
        insertNode(root, node4);
        insertNode(root, node5);
        insertNode(root, node6);

        //Tree walk test
        List<Integer> list1 = new ArrayList<>();
        inoderTreeWalk(root, list1);
        System.out.println(list1);

        //Tree search test
        TreeNode targetNode = search(root, 8);
        System.out.println(targetNode);

        //Delete test
        List<Integer> list2 = new ArrayList<>();
        deleteNode(root, node4);
        inoderTreeWalk(root, list2);
        System.out.println(list2);
    }
}
