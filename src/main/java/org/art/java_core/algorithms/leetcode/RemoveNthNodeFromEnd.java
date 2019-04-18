package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * "Remove Nth Node From End of List" quiz solution from LeetCode.
 */
public class RemoveNthNodeFromEnd {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (n <= 0) {
            return head;
        }
        ListNode dummyNode = new ListNode(Integer.MAX_VALUE);
        dummyNode.next = head;

        ListNode preDelete = dummyNode;
        for (int i = 0; i < n; i++) {
            if (head == null) {
                return null;
            }
            head = head.next;
        }
        while (head != null) {
            preDelete = preDelete.next;
            head = head.next;
        }
        preDelete.next = preDelete.next.next;
        return dummyNode.next;
    }

    @Test
    void test0() {
        ListNode list = ListNode.of(1, 2, 3, 4, 5);
        assertEquals(list, list);
    }

    @Test
    void test1() {
        ListNode list1 = ListNode.of(1, 2, 3, 4, 5);
        ListNode list2 = ListNode.of(1, 2, 3, 4, 5);
        assertEquals(list1, list2);
    }

    @Test
    void test2() {
        ListNode list = ListNode.of(2, 3, 4, 5);
        assertNotEquals(list, null);
    }

    @Test
    void test3() {
        ListNode list = ListNode.of(2, 3, 4, 5);
        assertNotEquals(null, list);
    }

    @Test
    void test4() {
        ListNode list1 = ListNode.of(1, 3, 3, 4, 5);
        ListNode list2 = ListNode.of(1, 2, 3, 4, 5);
        assertNotEquals(list1, list2);
    }

    @Test
    void test5() {
        ListNode list1 = ListNode.of(1, 3, 3, 4, 5, 6);
        ListNode list2 = ListNode.of(1, 2, 3, 4, 5);
        assertNotEquals(list1, list2);
    }

    @Test
    void test6() {
        ListNode list1 = ListNode.of(1, 3, 3, 4, 5);
        ListNode list2 = ListNode.of(1, 2, 3, 4, 5, 6);
        assertNotEquals(list1, list2);
    }

    @Test
    void test7() {
        ListNode list1 = ListNode.of(1);
        ListNode list2 = ListNode.of(2);
        assertNotEquals(list1, list2);
    }

    @Test
    void test8() {
        assertEquals(ListNode.of(2, 3), removeNthFromEnd(ListNode.of(1, 2, 3), 3));
    }

    @Test
    void test9() {
        assertNull(removeNthFromEnd(ListNode.of(1, 2), 3));
    }

    @Test
    void test10() {
        assertNull(removeNthFromEnd(ListNode.of(1), 3));
    }

    @Test
    void test11() {
        assertNull(removeNthFromEnd(ListNode.of(), 3));
    }

    @Test
    void test12() {
        assertEquals(ListNode.of(1, 2, 4, 5), removeNthFromEnd(ListNode.of(1, 2, 3, 4, 5), 3));
    }

    @Test
    void test13() {
        assertEquals(ListNode.of(1, 3), removeNthFromEnd(ListNode.of(1, 2, 3), 2));
    }

    @Test
    void test14() {
        assertEquals(ListNode.of(2, 3), removeNthFromEnd(ListNode.of(1, 2, 3), 3));
    }
}
