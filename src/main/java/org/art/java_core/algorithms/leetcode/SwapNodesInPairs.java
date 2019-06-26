package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * "Swap Nodes in Pairs" quiz solution from LeetCode.
 */
public class SwapNodesInPairs {

    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode ptr1 = head;
        ListNode ptr2 = head.next;
        while (ptr2 != null) {
            ptr1.val = ptr1.val + ptr2.val;
            ptr2.val = ptr1.val - ptr2.val;
            ptr1.val = ptr1.val - ptr2.val;
            ptr1 = ptr1.next.next;
            if (ptr1 == null || ptr1.next == null) {
                return head;
            }
            ptr2 = ptr2.next.next;
        }
        return head;
    }

    @Test
    void test0() {
        ListNode input = ListNode.of(1, 2, 3, 4);
        ListNode expected = ListNode.of(2, 1, 4, 3);
        ListNode result = swapPairs(input);
        assertEquals(expected, result);
    }
}
