package org.art.java_core.algorithms.leetcode;

/**
 * Helper class for Leetcode coding problems related to Single Linked Lists.
 */
public class ListNode {

    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    public static ListNode of(int... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        ListNode list = new ListNode(values[0]);
        ListNode resList = list;
        for (int i = 1; i < values.length; i++) {
            list.next = new ListNode(values[i]);
            list = list.next;
        }
        return resList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode node = this;
        sb.append(node.val);
        while (node.next != null) {
            node = node.next;
            sb.append("->");
            sb.append(node.val);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (that == null) {
            return false;
        }
        if (!(that instanceof ListNode)) {
            return false;
        }
        ListNode ptr1 = this;
        ListNode ptr2 = (ListNode) that;
        while (ptr1 != null && ptr2 != null) {
            if (ptr1.val != ptr2.val) {
                return false;
            }
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }
        return ptr1 == null && ptr2 == null;
    }
}
