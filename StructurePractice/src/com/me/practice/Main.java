package com.me.practice;

import java.util.*;

/**
 * .
 *
 * @author zwyin
 */
public class Main {
    public static void main(String[] args) {
        int[] arr = {0,1,1,1,4,5,3,7,7,8,10,2,7,8,0,5,2,16,12,1,19,15,5,18,2,2,22,15,8,22,17,6,22,6,22,26,32,8,10,11,2,26,9,12,9,7,28,33,20,7,2,17,44,3,52,27,2,23,19,56,56,58,36,31,1,19,19,6,65,49,27,63,29,1,69,47,56,61,40,43,10,71,60,66,42,44,10,12,83,69,73,2,65,93,92,47,35,39,13,75};
        int[] result = getLeastNumbers(arr, 75);
        System.out.println(Arrays.toString(result));
    }

    public static int[] getLeastNumbers(int[] arr, int k) {
        if (k < 1) {
            return new int[0];
        }
        // insert sort
        int[] result = new int[k];
        int nextIndex = 1;
        result[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (nextIndex < k) { // result未满
                result[nextIndex] = arr[i];
                insertSort(result, nextIndex);
                nextIndex++;
            } else { // result已满
                if (arr[i] < result[k - 1]) {
                    result[k - 1] = arr[i];
                    insertSort(result, k - 1);
                }
            }
        }
        return result;
    }

    public static void insertSort(int[] arr, int lastIndex) {
        for (int i = lastIndex; i > 0; i--) {
            if (arr[i] < arr[i - 1]) {
                int tmp = arr[i];
                arr[i] = arr[i - 1];
                arr[i - 1] = tmp;
            }
        }
    }

    public static List<Integer[]> findContinuousSequence1(int target) {
        List<Integer[]> result = new ArrayList<>();
        // 偶数不会存在连续的两数之和为target
        if (target < 3) {
            return Collections.emptyList();
        }
        if (target == 3) {
            Integer[] tt = {1, 2};
            result.add(tt);
            return result;
        }
        int start = 1;
        // 从头开始遍历，计算所有的可能性
        for (; start <= target / 2; start++) {
            int end = start + 1; // 记录下一个数字
            int sum = start; // 计算当前序列的和
            while(end <= target / 2 + 1) {
                sum += end;
                end++;
                if (sum >= target) {
                    break;
                }
            }
            if (sum == target) {
                Integer[] tmp = new Integer[end - start];
                int val = start;
                for (int i = 0; i < end - start; i++) {
                    tmp[i] = val++;
                }
                result.add(tmp);
            }
        }
        return result;
    }

    public static int[][] findContinuousSequence(int target) {
        List<Integer[]> result = new ArrayList<>();
        // 偶数不会存在连续的两数之和为target
        if (target < 3) {
            return new int[0][0];
        }
        if (target == 3) {
            return new int[][]{{1, 2}};
        }
        int start = 1;
        // 从头开始遍历，计算所有的可能性
        for (; start <= target / 2; start++) {
            int end = start + 1; // 记录下一个数字
            int sum = start; // 计算当前序列的和
            while(end <= target / 2 + 1) {
                sum += end;
                end++;
                if (sum >= target) {
                    break;
                }
            }
            if (sum == target) {
                Integer[] tmp = new Integer[end - start];
                int val = start;
                for (int i = 0; i < end - start; i++) {
                    tmp[i] = val++;
                }
                result.add(tmp);
            }
        }

        int[][] tmp = new int[result.size()][];
        for (int i = 0; i < result.size(); i++) {
            int[] ii = new int[result.get(i).length];
            for (int j = 0; j < ii.length; j++) {
                ii[j] = result.get(i)[j];
            }
            tmp[i] = ii;
        }
        return tmp;
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
