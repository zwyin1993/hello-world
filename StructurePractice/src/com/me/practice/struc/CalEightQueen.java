package com.me.practice.struc;

import java.util.Arrays;

/**
 * 8皇后问题.
 *
 * @author zwyin
 */
public class CalEightQueen {
    public static void main(String[] args) {
        CalEightQueen queen = new CalEightQueen();
        queen.calEightQueens(0);
        queen.printQueens(queen.result);
        System.out.println("-------");
        System.out.println(Arrays.toString(queen.result));
    }

    int[] result = new int[8]; // 数组下标存的的是行值，值存的是列值

    /**
     * 回溯法，当前行不断的和之前的行进行比较，知道找到满足的情况.
     *
     * @param row 待计算的行
     */
    public void calEightQueens(int row) {
        if (row >= 8) {
            printQueens(result);
            return;
        }

        for (int column = 0; column < 8; column++) {
            if (isOk(row, column)) {
                result[row] = column;
                calEightQueens(row + 1);
            }
        }
    }

    /**
     * 检查前面的每一行，看哪一行不满足条件.
     *
     * @param row
     * @param column
     * @return
     */
    private boolean isOk(int row, int column) {
        int left = column - 1;
        int right = column + 1;
        for (int i = row - 1; i >= 0; i--) {
            if (result[i] == column) {
                return false;
            }

            if (left >= 0) {
                if (result[i] == left) {
                    return false;
                }
            }

            if (right < 8) {
                if (result[i] == right) {
                    return false;
                }
            }
            --left;
            ++right;
        }
        return true;
    }

    public void printQueens(int[] queens) {
        for (int row = 0; row < queens.length; row++) {
            for (int column = 0; column < queens.length; column++) {
                if (queens[row] == column) {
                    System.out.print("Q ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
