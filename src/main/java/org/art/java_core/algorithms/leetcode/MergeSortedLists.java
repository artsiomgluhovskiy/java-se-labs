package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * "Merge Two Sorted Lists" quiz solution from LeetCode.
 */
public class MergeSortedLists {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode l3 = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                l3.next = l1;
                l1 = l1.next;
            } else {
                l3.next = l2;
                l2 = l2.next;
            }
            l3 = l3.next;
        }
        if (l1 == null) {
            l3.next = l2;
        }
        if (l2 == null) {
            l3.next = l1;
        }
        return dummy.next;
    }

    @Test
    void test0() {
        ListNode l1 = ListNode.of(1, 2, 4);
        ListNode l2 = ListNode.of(1, 3, 4);
        ListNode result = ListNode.of(1, 1, 2, 3, 4, 4);
        assertEquals(result, mergeTwoLists(l1, l2));
    }

    @Test
    void test1() {
        ListNode l1 = ListNode.of(1, 2, 4);
        ListNode l2 = ListNode.of(1, 3, 4, 6);
        ListNode result = ListNode.of(1, 1, 2, 3, 4, 4, 6);
        assertEquals(result, mergeTwoLists(l1, l2));
    }
}


