package com.me.practice.algo;

/**
 * .
 *
 * @author zwyin
 */
public class TraceBack {
    private int maxW = Integer.MIN_VALUE; // 结果放到 maxW 中
    private int[] weight = {2, 2, 4, 6, 3};  // 物品重量
    private int n = 5; // 物品个数
    private int w = 9; // 背包承受的最大重量
    private boolean[][] mem = new boolean[5][10]; // 备忘录，默认值 false

    public void f(int i, int cw) { // 调用 f(0, 0)
        if (cw == w || i == n) { // cw==w 表示装满了，i==n 表示物品都考察完了
            if (cw > maxW) maxW = cw;
            return;
        }
        if (mem[i][cw]) return; // 重复状态
        mem[i][cw] = true; // 记录 (i, cw) 这个状态
        f(i + 1, cw); // 选择不装第 i 个物品
        if (cw + weight[i] <= w) {
            f(i + 1, cw + weight[i]); // 选择装第 i 个物品
        }
    }

    public static void main(String[] args) {
        TraceBack traceBack = new TraceBack();
        traceBack.f(0, 0);
    }
}
