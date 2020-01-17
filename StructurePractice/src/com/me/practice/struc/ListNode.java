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
        ListNode node1 = new ListNode(4);
        node1.next = new ListNode(2);
        node1.next.next = new ListNode(1);
        node1.next.next.next = new ListNode(3);


        ListNode result = node1.insertionSortList(node1);
        System.out.println(result);
    }

    public ListNode insertionSortList(ListNode head) {
        if (null == head.next) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = head; // 指向current的前一个节点的前一个节点
        ListNode curr = head.next;
        while (null != curr) {
            ListNode comparePre = dummy; // 依次从头开始比较curr
            while (true) { // 插入curr
                if (comparePre.next == pre) {  // XX,comparePre,pre,curr的情况
                    if (pre.val > curr.val) {
                        pre.next = curr.next;
                        curr.next = comparePre.next;
                        comparePre.next = curr;
                        curr = pre.next;
                    } else {
                        pre = pre.next;
                        curr = curr.next;
                    }
                    break;
                }
                if (comparePre.next.val >= curr.val) {
                    pre.next = curr.next;
                    curr.next = comparePre.next;
                    comparePre.next = curr;
                    curr = pre.next;
                    break;
                }
                comparePre = comparePre.next;
            }
        }
        return dummy.next;
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
