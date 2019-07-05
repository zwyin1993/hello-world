package com.me.practice.struc;

public class Trie {
    private Node root;

    /**
     * Initialize your data structure here.
     */
    public Trie() {
        this.root = new Node('/');
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        Node cur = this.root;
        char[] words = word.toCharArray();
        for (int i = 0; i < words.length; i++) {
            int index = words[i] - 'a';
            if (cur.childs[index] == null) {
                Node node = new Node(words[i]);
                cur.childs[index] = node;
            }
            cur = cur.childs[index];
        }
        cur.isEndingChar = true;
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        Node cur = this.root;
        char[] words = word.toCharArray();
        for (int i = 0; i < words.length; i++) {
            int index = words[i] - 'a';
            if (null == cur.childs[index]) {
                return false;
            }
            cur = cur.childs[index];
        }

        return cur.isEndingChar;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        Node cur = this.root;
        char[] words = prefix.toCharArray();
        for (int i = 0; i < words.length; i++) {
            int index = words[i] - 'a';
            if (null == cur.childs[index]) {
                return false;
            }
            cur = cur.childs[index];
        }
        return true;
    }

    public static class Node {
        public char val;
        public Node[] childs;
        public boolean isEndingChar; // 是否是叶节点

        public Node(char val) {
            this.val = val;
            this.childs = new Node[26];
            this.isEndingChar = false;
        }
    }
}
