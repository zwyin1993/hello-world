package com.me.practice.algo;

import java.util.LinkedList;

/**
 * 使用图实现最短路径.
 */
public class Graph {
    private LinkedList<Edge> adj[]; // 邻接表存储相邻节点
    private int v; // 节点个数

    public Graph(int v) {
        this.v = v;
        this.adj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            this.adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int sid, int tid, int w) {
        this.adj[sid].add(new Edge(sid, tid, w));
    }

    private class Edge {
        private int sid; // 起点编号
        private int tid; //终点编号
        private int w; // 权重

        public Edge(int sid, int tid, int w) {
            this.sid = sid;
            this.tid = tid;
            this.w = w;
        }
    }

    private static class Vertex {
        private int id; // 节点编号
        private int dist; // 起始节点到当前节点的最短距离

        public Vertex(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }
    }

    /**
     * 小顶堆实现优先级队列.
     */
    private static class PriorityQueue {
        private Vertex[] nodes;
        private int count;
        private int tail;

        public PriorityQueue(int v) {
            this.nodes = new Vertex[v + 1];
            this.count = v;
            this.tail = 0;
        }

        public Vertex poll() {
            // 删除堆顶元素
            Vertex result = this.nodes[1];
            this.nodes[1] = this.nodes[this.tail];
            this.nodes[this.tail] = null;
            --this.tail;
            // 向下堆化
            int i = 1; // 向下堆化
            int minPos = 1;
            while (true) {
                if (2 * i <= this.tail && this.nodes[minPos].dist > this.nodes[2 * i].dist) {
                    minPos = 2 * i;
                }
                if (2 * i + 1 <= this.tail && this.nodes[minPos].dist > this.nodes[2 * i + 1].dist) {
                    minPos = 2 * i + 1;
                }
                if (minPos == i) {
                    break;
                }
                Vertex tmp = this.nodes[minPos];
                this.nodes[minPos] = this.nodes[i];
                this.nodes[i] = tmp;
                i = minPos;
            }
            return result;
        }

        public void add(Vertex vertex) {
            // 新元素添加在末尾
            this.nodes[++this.tail] = vertex;
            // 向上堆化
            int i = this.tail;
            while (i / 2 > 0) {
                if (this.nodes[i].dist < this.nodes[i / 2].dist) {
                    Vertex tmp = this.nodes[i];
                    this.nodes[i] = this.nodes[i / 2];
                    this.nodes[i / 2] = tmp;
                    i = i / 2;
                } else {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        PriorityQueue queue = new PriorityQueue(7);
        queue.add(new Vertex(2, 5));
        queue.add(new Vertex(1, 6));
        queue.add(new Vertex(3, 1));
        queue.add(new Vertex(4, 4));
        queue.add(new Vertex(5, 3));
        queue.add(new Vertex(6, 9));
        Vertex ss = queue.poll();
        System.out.println(ss.dist);
        ss = queue.poll();
        System.out.println(ss.dist);
        ss = queue.poll();
        System.out.println(ss.dist);
        ss = queue.poll();
        System.out.println(ss.dist);
        ss = queue.poll();
        System.out.println(ss.dist);
        ss = queue.poll();
        System.out.println(ss.dist);
    }
}
