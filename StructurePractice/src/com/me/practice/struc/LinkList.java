package com.me.practice.struc;

/**
 * 链表练习.
 *
 * @author zwyin
 */
public class LinkList {
    //链表反转
    private Node first;

    private Node last;

    public void add(String e) {
        final Node newNode = new Node(e, null);
        if (null != this.last) {
            this.last.next = newNode;
            this.last = newNode;
            return;
        }

        if (null == this.first) {
            this.first = newNode;
        } else {
            this.first.next = newNode;
            this.last = newNode;
        }
    }

    /**
     * 通过构建一个空节点，进行反转.
     */
    public void reverse() {
        Node dummy = new Node("", this.first);
        this.first = dummy;
        reverse1(dummy, dummy.next, dummy.next.next);
    }

    /**
     * 单链表反转.
     *
     * @param dummy 头节点
     * @param prev  前一个节点
     * @param pcur  当前节点
     */
    private void reverse1(Node dummy, Node prev, Node pcur) {
        prev.next = pcur.next;
        pcur.next = dummy.next;
        dummy.next = pcur;
        pcur = prev.next;
        if (null != pcur.next) {
            reverse1(dummy, prev, pcur);
        } else {
            this.first = pcur;
            pcur.next = dummy.next;
            this.last = prev;
            prev.next = null;
        }
    }

    /**
     * 单链表反转.
     * 遍历，将带移动节点移动到第一个节点之后
     *
     * @param dummy 第一个节点
     * @param prev 待移动节点的前一个节点
     * @param pcur 带移动节点
     */
    private void reverse2(Node dummy, Node prev, Node pcur) {
        // 前一个节点，取代待移动节点的位置
        prev.next = pcur.next;
        // 待移动节点插入到首节点之后
        pcur.next = dummy.next;
        dummy.next = pcur;
        // 继续移动下一个节点
        pcur = prev.next;
        if (null != pcur.next) {
            reverse2(dummy, prev, pcur);
        } else {
            // 最后一个节点移动到首节点，首节点移动到最后一个节点
            pcur.next = dummy.next;
            prev.next = dummy;
            this.first = pcur;
            this.last = prev.next;
            // 切断最后一个节点，不要构成环
            this.last.next = null;
        }
    }

    public static void main(String[] args) {
        testReserse();
    }

    /**
     * 检测闭环，采用快慢指针的方式。
     */
    private static void testCircle() {
        Node a = new Node("a", null);
        Node b = new Node("b", null);
        Node c = new Node("c", null);
        Node d = new Node("d", null);
        Node e = new Node("e", null);
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        e.next = a;
        Node fast = a;
        Node slow = a;
        int count = 0;
        while (true) {
            ++count;
            fast = fast.next;
            slow = slow.next.next;
            if (null == fast) {
                System.out.println("no circle!");
                return;
            }

            if (fast == slow) {
                System.out.println("has circle; count:" + count);
                return;
            }
        }
    }

    private static void testReserse() {
        LinkList list = new LinkList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.reverse2(list.first, list.first.next, list.first.next.next);
        System.out.println();
    }

    private static LinkList mergeList(LinkList first, LinkList second) {
        if (null == first.first) {
            return second;
        }

        if (null == second.first) {
            return first;
        }

        Node pre = first.first;
        Node after = first.first.next;
        Node cur = second.first;

        while (cur.item.compareTo(after.item) > 0 && cur.item.compareTo(after.item) < 0) {
            pre.next = cur;
            pre = cur;
            cur = pre.next;
        }
        return null;
    }

    private static class Node {
        String item;
        Node next;

        Node(String item, Node next) {
            this.item = item;
            this.next = next;
        }
    }
}
