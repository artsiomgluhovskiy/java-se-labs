package org.art.java_core.algorithms;

import org.art.java_core.algorithms.data_structures.ListNode;

/**
 * The algorithm implementation, which checks
 * if the linked list has a cycled structure.
 */
public class CycledLinkedListFinder {

    static boolean isCycled(ListNode tail) {
        if (tail == null) {
            return false;
        }
        ListNode curNode1 = tail;
        ListNode curNode2 = tail;
        while (true) {
            curNode1 = iterateOnce(curNode1);
            if (curNode1 == null) return false;
            if (curNode1 == curNode2) return true;
            curNode1 = iterateOnce(curNode1);
            if (curNode1 == null) return false;
            if (curNode1 == curNode2) return true;
            curNode2 = iterateOnce(curNode2);
            if (curNode2 == null) return false;
        }
    }

    static ListNode iterateOnce(ListNode tail) {
        if (tail != null && tail.next != null) {
            return tail.next;
        }
        return null;
    }

    static String toString(ListNode tail) {
        return tail == null ? " * " : tail.value + " -> " + toString(tail.next);
    }

    public static void main(String[] args) {
        //Cycled linked list testing
        ListNode node5 = new ListNode(5, null);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode tail = new ListNode(1, node2);
        //Linked list cycling
        node5.next = node2;

        if (isCycled(tail)) {
            System.out.println("The linked list is cycled. Stack overflow error will occur during the printing!");
        } else {
            System.out.println("The linked list is normal:");
            System.out.println(toString(tail));
        }
    }
}
