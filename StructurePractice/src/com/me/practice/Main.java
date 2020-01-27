package com.me.practice;

import java.util.LinkedList;

/**
 * .
 *
 * @author zwyin
 */
public class Main {
    public static void main(String[] args) {
        char[][] input = new char[][]{
                {'.', '4', '.', '.', '7', '.', '.', '.', '.'},
                {'.', '.', '4', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '7', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
        System.out.println(isValidSudoku(input));
    }

    public static boolean isValidSudoku(char[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                // 比较3*3是否满足
                if ((row + 1) % 3 == 0 && (col + 1) % 3 == 0) {
                    int newRow = row / 3;
                    int newCol = col / 3;
                    boolean[] status = new boolean[9]; // 下标表示第几位数，true表示3*3的九宫格内已经存在该数
                    for (int i = newRow * 3; i <= row; i++) {
                        for (int j = newCol * 3; j <= col; j++) {
                            if (board[i][j] == '.') {
                                continue;
                            }
                            int curr = board[i][j] - '1';
                            if (status[curr]) { // 如果已经存在
                                return false;
                            } else {
                                status[curr] = true;
                            }
                        }
                    }
                }

                if (board[row][col] == '.') {
                    continue;
                }
                if (!isOk(board, row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isOk(char[][] board, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i][col] == board[row][col]) {
                return false;
            }
        }
        for (int i = 0; i < col; i++) {
            if (board[row][i] == board[row][col]) {
                return false;
            }
        }

        return true;
    }

    private int v; // 顶点的个数
    private LinkedList<Integer> adj[]; // 邻接表

    public Main(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int s, int t) { // s 先于 t，边 s->t
        adj[s].add(t);
    }

    public void topoSortByDFS() {
        // 先构建逆邻接表，边 s->t 表示，s 依赖于 t，t 先于 s
        LinkedList<Integer> inverseAdj[] = new LinkedList[v];
        for (int i = 0; i < v; ++i) { // 申请空间
            inverseAdj[i] = new LinkedList<>();
        }
        for (int i = 0; i < v; ++i) { // 通过邻接表生成逆邻接表
            for (int j = 0; j < adj[i].size(); ++j) {
                int w = adj[i].get(j); // i->w
                inverseAdj[w].add(i); // w->i
            }
        }
        boolean[] visited = new boolean[v];
        for (int i = 0; i < v; ++i) { // 深度优先遍历图
            if (!visited[i]) {
                visited[i] = true;
                dfs(i, inverseAdj, visited);
            }
        }
    }

    private void dfs(
            int vertex, LinkedList<Integer> inverseAdj[], boolean[] visited) {
        for (int i = 0; i < inverseAdj[vertex].size(); ++i) {
            int w = inverseAdj[vertex].get(i);
            if (visited[w]) continue;
            visited[w] = true;
            dfs(w, inverseAdj, visited);
        } // 先把 vertex 这个顶点可达的所有顶点都打印出来之后，再打印它自己
        System.out.print("->" + vertex);
    }
}
