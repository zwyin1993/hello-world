package com.me.practice.structure;

/**
 * .
 *
 * @author zwyin
 */
public class LinkStack {
    public static class StackNode {
        private String value;
        private StackNode next;

        public StackNode(String value, StackNode next) {
            this.value = value;
            this.next = next;
        }

        public StackNode(String value) {
            this.value = value;
            this.next = null;
        }

        public boolean hasNext() {
            return null != this.next;
        }

        public void setNext(StackNode next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return String.format("value:%s", this.value);
        }
    }

    private StackNode first;

    public void push(String value) {
        StackNode item = new StackNode(value);
        if (null == first) {
            first = item;
            return;
        }
        StackNode pre = searchPreOfLast();
        if (null == pre.next) {
            pre.setNext(item);
            return;
        }
        pre.next.setNext(item);
    }

    public StackNode poll() {
        StackNode result = null;
        if (null == first.next) {
            result = first;
            first = null;
            return result;
        }
        StackNode pre = searchPreOfLast();
        result = pre.next;
        pre.next = null;
        return result;
    }

    private StackNode searchPreOfLast() {
        // 当只有一个节点是，返回第一个节点；两个节点以上就正常了
        StackNode last = first;
        StackNode pre = first;
        while (last.hasNext()) {
            pre = last;
            last = pre.next;
        }
        return pre;
    }

//    private StackNode searchLast() {
//        StackNode last = first;
//        StackNode pre;
//        while (last.hasNext()) {
//            pre = last;
//            last = pre.next;
//        }
//        return last;
//    }

    public static void main(String[] args) {
        LinkStack linkStack = new LinkStack();
        linkStack.push("zhangsan");
        linkStack.push("lisi");
        linkStack.push("wangwu");
//        linkStack.push("zhaoliu");

        StackNode last = linkStack.poll();
        System.out.println();
    }
}

