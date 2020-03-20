package com.me.practice.struc;

import java.util.*;
import java.util.concurrent.*;

/**
 * .
 *
 * @author zwyin
 */
public class Practice {

    public static TreeNode stringToTreeNode(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return null;
        }

        String[] parts = input.split(",");
        String item = parts[0];
        TreeNode root = new TreeNode(Integer.parseInt(item));
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);

        int index = 1;
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.remove();

            if (index == parts.length) {
                break;
            }

            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int leftNumber = Integer.parseInt(item);
                node.left = new TreeNode(leftNumber);
                nodeQueue.add(node.left);
            }

            if (index == parts.length) {
                break;
            }

            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int rightNumber = Integer.parseInt(item);
                node.right = new TreeNode(rightNumber);
                nodeQueue.add(node.right);
            }
        }
        return root;
    }

    public static void main(String[] args) {
        System.out.println((new Practice()).majorityElement(new int[]{2, 2, 3, 3}));

    }

    public int majorityElement(int[] nums) { // 最多数元素
        Arrays.sort(nums);
        if (nums[0] == nums[nums.length / 2]) {
            return nums[0];
        }
        for (int i = nums.length / 2 + 1; i < nums.length; i++) { // i表示当前要比较的数字索引
            for (int j = i - nums.length / 2; j <= i; j++) { // j标识n/2的起始位
                if (j + nums.length / 2 > nums.length - 1) { // FIXME:下标和长度的判断，要考虑下标比长度少1
                    break;
                }
                if (nums[j] == nums[j + nums.length / 2]) { // 因为数组拍过续，所以如果nums[j] == nums[j + nums.length / 2]，则j处为多数元素
                    return nums[j];
                }
            }
        }
        return 0;
    }

    /**
     * 最大公因子，辗转相除法.
     * 跳出条件：余数为0，除数即为最大公约数
     * 第二次循环时，第一次的余数作为本次除数，第一次的除数%第一次的余数作为本次余数
     *
     * @param a 除数
     * @param b 余数
     * @return 最大公约数
     */
    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return String.format("val:%s;left:%s;right:%s", val, null == left ? "" : left.val, null == right ? "" : right.val);
        }
    }

    public int diameterOfBinaryTree(TreeNode root) {
        // 左节点的深度+右节点的深度
        if (null == root || (null == root.left && null == root.right)) {
            return 0;
        }
        int leftDepth = null == root.left ? 0 : getDepth(root.left, 1);
        int rightDepth = null == root.right ? 0 : getDepth(root.right, 1);
        System.out.println("left:" + leftDepth + "; right:" + rightDepth);
        return leftDepth + rightDepth > this.max ? leftDepth + rightDepth : this.max;
    }

    private int max = -1;

    private int getDepth(TreeNode node, int currDepth) {
        if (null == node.left && null == node.right) {
            return currDepth;
        }

        int leftDepth = null == node.left ? currDepth : getDepth(node.left, currDepth + 1);
        int rightDepth = null == node.right ? currDepth : getDepth(node.right, currDepth + 1);
        int currentDiameter = leftDepth + rightDepth - 2 * currDepth;
        this.max = this.max < currentDiameter ? currentDiameter : this.max;
        return Math.max(leftDepth, rightDepth);
    }

    public boolean canJump(int[] nums) {
        return nums.length == 1 || calJumps(nums, 0);
    }

    private boolean calJumps(int[] nums, int i) {
        if (nums[i] == 0 && i < nums.length - 1) {
            return false;
        }
        // 计算当前节点是否满足
        for (int j = 1; j <= nums[i]; j++) {
            if (i + j >= nums.length - 1) {
                return true;
            }
            if (calJumps(nums, i + j)) {
                return true;
            }
        }
        return false;
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
