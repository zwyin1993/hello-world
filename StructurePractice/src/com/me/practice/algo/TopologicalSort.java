package com.me.practice.algo;

import java.util.LinkedList;

/**
 * 拓扑排序.
 * 使用有向无环图实现
 *
 * @author zwyin
 */
public class TopologicalSort {
    private int v; // 顶点的个数
    private LinkedList<Integer> adj[]; // 邻接表

    public TopologicalSort(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int s, int t) { // s 先于 t，边 s->t
        adj[s].add(t);
    }

    /**
     * 拓扑排序的kahb实现.
     * 依次计算每个节点的入度，依次输出入度为零的节点.
     */
    public void topoSortByKahn() {
        int[] inDegree = new int[v]; // 统计每个顶点的入度
        for (int i = 0; i < v; ++i) {
            for (int j = 0; j < adj[i].size(); ++j) {
                int w = adj[i].get(j); // i->w
                inDegree[w]++;
            }
        }
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < v; ++i) {
            if (inDegree[i] == 0) queue.add(i);
        }
        while (!queue.isEmpty()) {
            int i = queue.remove();
            System.out.print("->" + i);
            for (int j = 0; j < adj[i].size(); ++j) {
                int k = adj[i].get(j);
                inDegree[k]--;
                if (inDegree[k] == 0) queue.add(k);
            }
        }
    }

    public static void main(String[] args) {
        TopologicalSort sort = new TopologicalSort(6);
        sort.addEdge(1, 2);
        sort.addEdge(1, 3);
        sort.addEdge(4, 2);
        sort.addEdge(4, 3);
        sort.topoSortByKahn();
    }
}
