package com.me.practice.prac;

import java.util.Stack;

/**
 * .
 *
 * @author zwyin
 */
public class Solution {

    public static void main(String[] args) {
        int[] arr = {4,2,0,3,2,5};
        int result = (new Solution()).trap(arr);
        System.out.println(result);
    }
    /**
     * 接雨滴问题.
     * 单调栈求解,按行计算
     * 还有一种是动态规划，可以参考leetcode
     *
     * @param height
     * @return
     */
    public int trap(int[] height) {
        if (height.length < 2) {
            return 0;
        }
        Stack<Integer> stack = new Stack<>(); // 存的是下标
        stack.push(0);
        int sum = 0;
        for (int i = 1; i < height.length; i++) {
            // 如果当前高度大于栈顶元素，表示可以接雨滴；否则入栈
            while (!stack.isEmpty() && height[stack.peek()] < height[i]) {
                // 计算单位面积
                int bottomIndex = stack.pop();
                while (!stack.isEmpty() && height[stack.peek()] <= height[bottomIndex]) {
                    stack.pop();
                }
                if (!stack.isEmpty()) { // 如果左侧没有墙，就接不了雨滴
                    sum += (i - stack.peek() - 1) * (Math.min(height[i], height[stack.peek()]) - height[bottomIndex]); // 面积 = 长 * 高
                }
            }
            stack.push(i);
        }
        return sum;
    }
}
