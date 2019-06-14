package com.me.practice.struc;

public class BinaryTree {

    private Node root;

    public void insert(int target) {
        if (null == root) {
            this.root = new Node(target);
            return;
        }
        // 查找待插入的位置
        Node item = root;
        Node current = root;
        while (null != current) {
            item = current;
            if (target > current.value) {
                current = current.right;
            } else if (target < current.value) {
                current = current.left;
            } else {
                // 假设没有相同的情况
            }
        }

        if (target > item.value) {
            item.right = new Node(target);
        } else {
            item.left = new Node(target);
        }
    }

    /**
     * 前序遍历查找.
     * 中左右
     *
     * @param target 带查找的数字
     * @return
     */
    public Node preSearch(int target) {
        return preSearch(this.root, target);
    }

    public Node preSearch(Node node, int target) {
        if (null == node) {
            return null;
        }
        if (node.value == target) {
            return node;
        }
        Node item = preSearch(node.left, target);
        if (null != item) {
            return item;
        }
        item = preSearch(node.right, target);
        return item;
    }

    /**
     * 中序查找.
     * 左中右
     *
     * @param node
     * @param target
     * @return
     */
    public Node midSearch(Node node, int target) {
        if (null == node) {
            return null;
        }

        Node item = midSearch(node.left, target);
        if (null != item && item.value == target) {
            return item;
        }

        if (node.value == target) {
            return node;
        }

        item = midSearch(node.right, target);
        return item;
    }

    /**
     * 计算数的高度.
     * 采用递归法：height = max(left.height, right.height) + 1
     *
     * @return 树的高度
     */
    public int calHeight() {
        return calHeight(this.root);
    }

    private int calHeight(Node node) {
        if (null == node || node.isLeafNode()) {
            return 0;
        }
        int leftHeight = calHeight(node.left);
        int rightHeight = calHeight(node.right);
        return leftHeight > rightHeight ? leftHeight + 1 : rightHeight + 1;
    }

    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node() {
        }

        public Node(int value) {
            this.value = value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public boolean isLeafNode() {
            return null == this.left && null == this.right;
        }

        @Override
        public String toString() {
            return String.format("value:%s", value);
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.insert(5);
        tree.insert(2);
        tree.insert(6);
        tree.insert(1);
        Node item = tree.midSearch(tree.root, 7);
        System.out.println();
    }
}
