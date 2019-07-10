package com.me.practice.algo;

/**
 * 动态规划.
 *
 * @author zwyin
 */
public class DynamicProgramming {
    // weight: 物品重量，n: 物品个数，w: 背包可承载重量

    /**
     * 背包问题，计算可以加入背包中的最大重量
     *
     * @param weight 每个物品的重量
     * @param n      物品总数
     * @param w      背包限重
     * @return 可装入总量
     */
    public int knapsack(int[] weight, int n, int w) {
        boolean[][] states = new boolean[n][w + 1]; // 默认值 false
        states[0][0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
        if (weight[0] <= w) {
            states[0][weight[0]] = true;
        }
        for (int i = 1; i < n; ++i) { // 动态规划状态转移
            for (int j = 0; j <= w; ++j) {// 不把第 i 个物品放入背包
                if (states[i - 1][j]) states[i][j] = states[i - 1][j];
            }
            for (int j = 0; j <= w - weight[i]; ++j) {// 把第 i 个物品放入背包
                if (states[i - 1][j]) states[i][j + weight[i]] = true;
            }
        }
        for (int i = w; i >= 0; --i) { // 输出结果
            if (states[n - 1][i]) return i;
        }
        return 0;
    }

    /**
     * 一维数组实现背包问题.
     * statues[物品数][总重量] = 是否出现过(boolean)
     */
    public static int knapsack2(int[] items, int n, int w) {
        boolean[] states = new boolean[w + 1]; // 默认值 false
        states[0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
        if (items[0] <= w) {
            states[items[0]] = true;
        }
        for (int i = 1; i < n; ++i) { // 动态规划
            // 这里采用从大到小进行赋值，如果从小到大赋值，会影响后面的判断
            // 举例：当i==2时，应该赋值的是0， 2， 4；如果从小到大赋值的话，会是0，2,4，6本来应该是i==3之后出现的状态在这里全都出现了，有问题！！！
            for (int j = w-items[i]; j >= 0; --j) {// 把第 i 个物品放入背包
                if (states[j]) states[j+items[i]] = true;
            }
//            for (int j = 0; j <= w - items[i]; ++j) {// 把第 i 个物品放入背包
//                if (states[j]) states[j + items[i]] = true;
//            }
        }
        for (int i = w; i >= 0; --i) { // 输出结果
            if (states[i]) return i;
        }
        return 0;
    }

    /**
     * 每个东西都有价值.
     * statues[物品数][总重量] = 总价值(int)
     */
    public static int knapsack3(int[] weight, int[] value, int n, int w) {
        int[][] states = new int[n][w + 1];
        for (int i = 0; i < n; ++i) { // 初始化 states
            for (int j = 0; j < w + 1; ++j) {
                states[i][j] = -1;
            }
        }
        states[0][0] = 0;
        if (weight[0] <= w) {
            states[0][weight[0]] = value[0];
        }
        for (int i = 1; i < n; ++i) { // 动态规划，状态转移
            for (int j = 0; j <= w; ++j) { // 不选择第 i 个物品
                if (states[i - 1][j] >= 0) states[i][j] = states[i - 1][j];
            }
            for (int j = 0; j <= w - weight[i]; ++j) { // 选择第 i 个物品
                if (states[i - 1][j] >= 0) {
                    int v = states[i - 1][j] + value[i];
                    if (v > states[i][j + weight[i]]) {
                        states[i][j + weight[i]] = v;
                    }
                }
            }
        }
        // 找出最大值
        int maxvalue = -1;
        for (int j = 0; j <= w; ++j) {
            if (states[n - 1][j] > maxvalue) maxvalue = states[n - 1][j];
        }
        return maxvalue;
    }


    public static void main(String[] args) {
        DynamicProgramming dd = new DynamicProgramming();
        int aa = dd.knapsack3(new int[]{2, 3, 4, 5, 6}, new int[]{1, 2, 3, 4, 5}, 5, 16);
//        int aa = dd.knapsack2(new int[]{2, 2, 4, 6, 3}, 5, 16);
        System.out.println(aa);
    }
}
