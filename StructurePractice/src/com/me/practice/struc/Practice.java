package com.me.practice.struc;

import java.util.*;
import java.util.concurrent.*;

/**
 * .
 *
 * @author zwyin
 */
public class Practice {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

    }



    private static final String LINE_FIELD = "-";
    private static final String COL_FIELD = "|";

    static int[][] matrix;
    static int value = 0;

    /**
     * 蛇形矩阵.
     *
     * @param limit limit届矩阵
     */
    public static void snakeCode(int limit) {
        // 方向计数器，偶数表示正向，奇数表示反向
        // 行的正向是向右，列的正向是向下
        int lineDir = 0;
        int colDir = 0;

        matrix = new int[limit][limit];

        int lineStart = 0;
        int colStart;

        int line = 0;
        int col;

        for (int step = limit - 1; step >= 0; ) {
            // 正向走一行
            lineWalk(lineStart, lineDir, step, line);
            lineDir++;
            col = lineStart + step;
            colStart = line + 1;

            --step;
            // 正向走一列
            colWalk(colStart, colDir, step, col);
            colDir++;
            lineStart = col - 1;
            line = colStart + step;

            // 反向走一行
            lineWalk(lineStart, lineDir, step, line);
            lineDir++;
            col = lineStart - step;
            colStart = line - 1;

            --step;
            //反向走一列
            colWalk(colStart, colDir, step, col);
            colDir++;
            lineStart = col + 1;
            line = colStart - step;
        }
    }

    private static void lineWalk(int lineStart, int dir, int step, int line) {
        boolean front = 0 == dir % 2;
        // 正向的话，在起点加要走的步数
        if (front) {
            for (int index = lineStart; index <= lineStart + step; index++) {
                matrix[line][index] = ++value;
            }
            return;
        }

        // 反向的话，在起点减要走的步数
        for (int index = lineStart; index >= lineStart - step; index--) {
            matrix[line][index] = ++value;
        }
    }

    private static void colWalk(int colStart, int dir, int step, int col) {
        boolean front = 0 == dir % 2;
        // 正向的话，在起点加要走的步数
        if (front) {
            for (int index = colStart; index <= colStart + step; index++) {
                matrix[index][col] = ++value;
            }
            return;
        }

        // 反向的话，在起点减要走的步数
        for (int index = colStart; index >= colStart - step; index--) {
            matrix[index][col] = ++value;
        }
    }

    /**
     * 两数之和.
     * target等nums中的哪两个数之和
     *
     * @param nums   集合
     * @param target 待查找的数
     * @return 两数
     */
    public static int[] twoSum1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{nums[i], nums[j]};
                }
            }
        }

        return new int[0];
    }

    /**
     * 求两数之和.
     * 利用hashmap的查询速度为O(1)的特点.
     */
    public static int[] twoSum2(int[] nums, int target) {
        // 记录集合中的每个值，及其索引
        Map<Integer, Integer> numWithIndex = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            numWithIndex.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int other = target - nums[i];
            // 如果集合中包含另一个数，并且另一个数和当前数不是一个数，返回两个值
            if (numWithIndex.containsKey(other) && numWithIndex.get(other) != i) {
                return new int[]{other, nums[i]};
            }
        }

        return new int[0];
    }

    /**
     * 求两数之和.
     * 利用hashmap的查询速度为O(1)的特点，并且只需要一层循环.
     */
    public static int[] twoSum3(int[] nums, int target) {
        Map<Integer, Integer> numWithIndex = new HashMap<>(nums.length);

        for (int i = 0; i < nums.length; i++) {
            int other = target - nums[i];
            // 如果集合中包含另一个数，并且另一个数和当前数不是一个数，返回两个值
            if (numWithIndex.containsKey(other) && numWithIndex.get(other) != i) {
                return new int[]{other, nums[i]};
            }
            numWithIndex.put(nums[i], i);
        }
        return new int[0];
    }
}
