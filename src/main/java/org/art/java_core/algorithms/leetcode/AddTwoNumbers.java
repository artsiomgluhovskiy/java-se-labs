package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * "Add Two Numbers" quiz solution from LeetCode.
 */
public class AddTwoNumbers {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(0);
        ListNode currentNode = root;
        int sum;
        int carry = 0;

        while (l1 != null && l2 != null) {
            sum = l1.val + l2.val + carry;
            carry = sum > 9 ? 1 : 0;
            ListNode newNode = new ListNode(sum % 10);
            currentNode.next = newNode;
            currentNode = newNode;
            l1 = l1.next;
            l2 = l2.next;
        }

        while (l1 != null) {
            sum = l1.val + carry;
            carry = sum > 9 ? 1 : 0;
            ListNode newNode = new ListNode(sum % 10);
            currentNode.next = newNode;
            currentNode = newNode;
            l1 = l1.next;
        }

        while (l2 != null) {
            sum = l2.val + carry;
            carry = sum > 9 ? 1 : 0;
            ListNode newNode = new ListNode(sum % 10);
            currentNode.next = newNode;
            currentNode = newNode;
            l2 = l2.next;
        }

        if (carry > 0) {
            ListNode newNode = new ListNode(carry);
            currentNode.next = newNode;
        }

        return root.next;
    }

    @Test
    @DisplayName("342 + 465 = 807")
    void test1() {
        ListNode l1 = ListNode.of(2, 4, 3);
        System.out.println("l1 linked list: " + l1);

        ListNode l2 = ListNode.of(5, 6, 4);
        System.out.println("l2 linked list: " + l2);

        AddTwoNumbers addTwoNumbers = new AddTwoNumbers();
        ListNode result = addTwoNumbers.addTwoNumbers(l1, l2);

        assertEquals(ListNode.of(7, 0, 8), result);
    }

    @Test
    @DisplayName("99 + 1 = 100")
    void test2() {
        ListNode l1 = ListNode.of(9, 9);
        System.out.println("l1 linked list: " + l1);

        ListNode l2 = ListNode.of(1);
        System.out.println("l2 linked list: " + l2);

        AddTwoNumbers addTwoNumbers = new AddTwoNumbers();
        ListNode result = addTwoNumbers.addTwoNumbers(l1, l2);

        assertEquals(ListNode.of(0, 0, 1), result);
    }
}
