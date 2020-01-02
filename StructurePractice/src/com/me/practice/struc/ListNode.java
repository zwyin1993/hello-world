package com.me.practice.struc;

public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        node1.next = new ListNode(2);
        node1.next.next = new ListNode(3);
        node1.next.next.next = new ListNode(4);
        node1.next.next.next.next = new ListNode(5);
        node1.next.next.next.next.next = new ListNode(6);
        node1.next.next.next.next.next.next = new ListNode(7);
        node1.next.next.next.next.next.next.next = new ListNode(8);
        node1.next.next.next.next.next.next.next.next = new ListNode(9);
        node1.next.next.next.next.next.next.next.next.next = new ListNode(10);

        ListNode result = node1.removeNthFromEnd(node1, 7);
        System.out.println(result);
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (null == head) {
            return null;
        }
        ListNode root = reverse(head);
        ListNode pre = root;
        if (n == 1) {
            return null == root.next ? null : reverse(root.next);
        }
        int index = 2;
        for (; index < n; index++) {
            ListNode curr = pre.next;
            if (null == curr) {
                return null;
            }
            pre = curr;
        }
        if (index > n) {
            return null;
        }
        pre.next = pre.next.next; // 删除第n个节点
        return reverse(root);
    }

    public ListNode reverse(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = head;
        ListNode curr = head.next;
        while (null != curr) {
            pre.next = curr.next;
            curr.next = dummy.next;
            dummy.next = curr;
            curr = pre.next;
        }
        return dummy.next;
    }
}
