package com.me.practice.struc;

import java.util.LinkedList;
import java.util.Queue;

public class AhoCorasick {

    private AcNode root = new AcNode('/');

    public static class AcNode {
        public char data;
        public AcNode[] children = new AcNode[26]; // 字符集只包含 a~z 这 26 个字符
        public boolean isEndingChar = false; // 结尾字符为 true
        public int length = -1; // 当 isEndingChar=true 时，记录模式串长度
        public AcNode fail; // 失败指针
        public AcNode(char data) {
            this.data = data;
        }
    }

    /**
     * 1. p.fail = q;
     * 2. 如果q的子节点qc == p的子节点pc；那么pc.fail = qc;如果找不到，那pc.fail = root;
     */
    public void buildFailurePointer() {
        Queue<AcNode> queue = new LinkedList<>();
        root.fail = null;
        queue.add(root);
        while (!queue.isEmpty()) {
            AcNode p = queue.remove();
            for (int i = 0; i < 26; ++i) {
                AcNode pc = p.children[i];
                if (pc == null) continue;
                if (p == root) {
                    pc.fail = root;
                } else {
                    AcNode q = p.fail;
                    while (q != null) {
                        AcNode qc = q.children[pc.data - 'a']; // ？这里的( pc.data - 'a' == i ) 是否成立；因为i表示字符的位置，
                                                               // pc.data - 'a' 也表示字符的位置
                        if (qc != null) {
                            pc.fail = qc;
                            break;
                        }
                        q = q.fail;
                    }
                    if (q == null) {
                        pc.fail = root;
                    }
                }
                queue.add(pc);
            }
        }
    }

    public void insert(String word) {
        AcNode cur = this.root;
        char[] words = word.toCharArray();
        for(int i = 0; i < words.length; i++) {
            int index = words[i] - 'a';
            if (cur.children[index] == null) {
                AcNode node = new AcNode(words[i]);
                cur.children[index] = node;
                cur.children[index].length = i + 1; // 记录当前节点到根节点的长度
            }
            cur = cur.children[index];
        }
        cur.isEndingChar = true;
    }

    public void match(char[] text) { // text 是主串
        int n = text.length;
        AcNode p = root;
        for (int i = 0; i < n; ++i) {
            int idx = text[i] - 'a';
            while (p.children[idx] == null && p != root) {
                p = p.fail; // 失败指针发挥作用的地方
            }
            p = p.children[idx];
            if (p == null) p = root; // 如果没有匹配的，从 root 开始重新匹配
            AcNode tmp = p;
            while (tmp != root) { // 打印出可以匹配的模式串
                if (tmp.isEndingChar) {
                    int pos = i-tmp.length+1;
                    System.out.println(" 匹配起始下标 " + pos + "; 长度 " + tmp.length);
                }
                tmp = tmp.fail;
            }
        }
    }


    public static void main(String[] args) {
        String ss = "helloword";
        AhoCorasick ahoCorasick = new AhoCorasick();
        ahoCorasick.insert("abcd");
        ahoCorasick.insert("bc");
        ahoCorasick.insert("bcd");
        ahoCorasick.buildFailurePointer();
        ahoCorasick.match("abcd".toCharArray());
    }

}
