package com.me.practice.prac;

import java.util.*;

public class Practice {
    public static void main(String[] args) {
        String[][] input = new String[][]{{"A", "B"}, {"B", "C"}, {"C", "D"}, {"A", "G"}, {"B", "F"}, {"E", "F"}};
        Practice practice = new Practice();
//        practice.findLines(input, "B");
        int s = practice.romanToInt("MCMXCIV");
        System.out.println(s);
    }

    /**
     * 任务依赖查找.
     * 类似spark DAG图构建
     * [(A, B), (B, C), (C, D), (A, G), (B, F)]
     */
    public void findLines(String[][] inputs, String target) {
        for (String[] line : inputs) {
            if (line[0].equals(target)) {
                System.out.println(line[1]);
                findLines(inputs, line[1]);
            }
        }
    }

    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度.
     *
     * @param s 字符串
     * @return 最长子串长度
     */
    public int lengthOfLongestSubstring(String s) {
        int winStartIndex = 0; // 窗口左边界
        int winEndIndex = 0; // 窗口右边界的下一个
        boolean contains = false;
        int maxLength = 0;
        for (int index = 0; index < s.length(); index++) {
            if (winEndIndex == 0) {
                winEndIndex = 1;
                maxLength = 1;
                continue;
            }
            contains = false;
            int winIndex = winStartIndex;
            for (; winIndex < winEndIndex; winIndex++) {
                if (s.charAt(index) == s.charAt(winIndex)) {
                    contains = true;
                    break;
                }
            }
            // 如果新的字符和窗口内的字符相同，缩小窗口，重新比较
            if (contains) {
                winStartIndex = ++winIndex;
                index--;
            } else {
                winEndIndex++;
                int tmpLength = winEndIndex - winStartIndex;
                maxLength = maxLength > tmpLength ? maxLength : tmpLength;
            }
        }
        return maxLength;
    }

    /**
     * 查找最长子回文.
     * 1.制定子回文的窗口，左右边界
     * 2.判断窗口内的序列是否是子回文（根据回文的特点，判断奇数情况和偶数情况）
     *
     * @param s 待查找的字符串
     * @return 最长子回文
     */
    public String longestPalindrome(String s) {
        // 回文类似：上海自来水来自海上
        int startIndex = 0;
        int endIndex = 0;
        int maxLength = 1;
        if (s.length() <= 1) {
            return s;
        }
        for (int charIndex = 1; charIndex < s.length(); charIndex++) {
            // 奇数回文比较
            int tmpStartIndex = charIndex - 1;
            int tmpEndIndex = charIndex + 1;
            for (; tmpStartIndex >= 0; ) {
                if (tmpEndIndex >= s.length() || s.charAt(tmpStartIndex) != s.charAt(tmpEndIndex)) {
                    break;
                }
                tmpStartIndex--;
                tmpEndIndex++;
            }
            ++tmpStartIndex;
            --tmpEndIndex;
            int tmpLength = tmpEndIndex - tmpStartIndex + 1;
            if (maxLength < tmpLength) {
                maxLength = tmpLength;
                startIndex = tmpStartIndex;
                endIndex = tmpEndIndex;
            }

            // 偶数回文比较
            tmpStartIndex = charIndex - 1;
            tmpEndIndex = charIndex;
            for (; tmpStartIndex >= 0; ) {
                if (tmpEndIndex >= s.length() || s.charAt(tmpStartIndex) != s.charAt(tmpEndIndex)) {
                    break;
                }
                tmpStartIndex--;
                tmpEndIndex++;
            }
            ++tmpStartIndex;
            --tmpEndIndex;
            tmpLength = tmpEndIndex - tmpStartIndex + 1;
            if (maxLength < tmpLength) {
                maxLength = tmpLength;
                startIndex = tmpStartIndex;
                endIndex = tmpEndIndex;
            }

        }
        return s.substring(startIndex, endIndex + 1);
    }

    public String longestPalindrome1(String s) {
        // 回文类似：上海自来水来自海上
        int startIndex = 0;
        int endIndex = 0;
        int maxLength = 1;
        if (s.length() <= 1) {
            return s;
        }
        for (int charIndex = 1; charIndex < s.length(); charIndex++) {
            int tmpStartIndex = charIndex - 1;
            int tmpOddEndIndex = charIndex + 1; // 奇回文终点索引
            int tmpEvenEndIndex = charIndex; // 偶回文重点索引
            boolean oddSuccess = true; // 奇数比较成功标志
            boolean evenSuccess = true; // 偶数比较成功标志
            for (; tmpStartIndex >= 0; ) {
                if (tmpOddEndIndex >= s.length()) {
                    oddSuccess = false;
                }
                if (tmpEvenEndIndex >= s.length()) {
                    evenSuccess = false;
                }
                if (!oddSuccess && !evenSuccess) {
                    break;
                }
                // 比较奇回文
                if (oddSuccess && tmpOddEndIndex < s.length()
                        && s.charAt(tmpStartIndex) != s.charAt(tmpOddEndIndex)) {
                    oddSuccess = false;
                }
                // 比较偶回文
                if (evenSuccess && tmpEvenEndIndex < s.length()
                        && s.charAt(tmpStartIndex) != s.charAt(tmpEvenEndIndex)) {
                    evenSuccess = false;
                }
                if (!evenSuccess && !oddSuccess) {
                    break;
                }
                tmpStartIndex--;
                if (oddSuccess) {
                    tmpOddEndIndex++;
                }
                if (evenSuccess) {
                    tmpEvenEndIndex++;
                }
            }
            ++tmpStartIndex;
            --tmpOddEndIndex;
            --tmpEvenEndIndex;
            int tmpOddLength = tmpOddEndIndex - tmpStartIndex + 1;
            int tmpEvenLength = tmpEvenEndIndex - tmpStartIndex + 1;
            if (maxLength >= tmpEvenLength && maxLength >= tmpOddLength) {
                continue;
            }
            startIndex = tmpStartIndex;
            if (tmpEvenLength < tmpOddLength) {
                maxLength = tmpOddLength;
                endIndex = tmpOddEndIndex;
            } else {
                maxLength = tmpEvenLength;
                endIndex = tmpEvenEndIndex;
            }
        }
        return s.substring(startIndex, endIndex + 1);
    }

    /**
     * z字形变换.
     * Z行字符串的特点，每个周期内有numRows + （numRows - 2）个元素；周期指没有最后一行
     *
     * @param s       给定的字符串
     * @param numRows 行数
     * @return Z字形字符串
     */
    public String convert(String s, int numRows) {
        return "";
    }

    /**
     * 字符串转int.
     *
     * @param str
     * @return
     */
    public int myAtoi(String str) {
        int flag = 0; // 符号位
        int tmpRes = 0;
        boolean begin = false;
        for (char ch : str.toCharArray()) {
            int charAsc = (int)ch;
            if (!begin && (charAsc == 32 || charAsc == 34)) { // 过滤起始为空的和"的字符串
                continue;
            }
            begin = true;
            // - 号处理，首位的话，置为-1，否则过滤
            if (charAsc == 45 && flag == 0) {
                flag = -1;
                continue;
            }
            // + 号处理，首位的话，置为1，否则过滤
            if (charAsc == 43 && flag == 0) {
                flag = 1;
                continue;
            }
            // 过滤非 - 和非数字字符
            if (charAsc < 48 || charAsc > 57) {
                break;
            }
            int pom = Integer.parseInt(String.valueOf(ch));
            if (flag == 0) {
                flag = 1;
                tmpRes = pom;
                continue;
            }
            if (flag * tmpRes > Integer.MAX_VALUE / 10 || (flag * tmpRes == Integer.MAX_VALUE / 10 && pom > 7)) {
                return Integer.MAX_VALUE;
            }
            if (flag * tmpRes < Integer.MIN_VALUE / 10 || (flag * tmpRes == Integer.MIN_VALUE / 10 && pom > 8)) {
                return Integer.MIN_VALUE;
            }
            tmpRes = 10 * tmpRes + pom;
        }
        return flag * tmpRes;
    }

    public static class Section {
        private int left;
        private int right;

        public Section(int left, int right) {
            this.left = left;
            this.right = right;
        }

        public int getLeft() {
            return left;
        }

        public int getRight() {
            return right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Section section = (Section) o;
            return left == section.left &&
                    right == section.right;
        }

        @Override
        public int hashCode() {

            return Objects.hash(left, right);
        }

        @Override
        public String toString() {
            return String.format("[%s, %s]", this.left, this.right);
        }
    }

    public int romanToInt(String s) {
        // init
        Map<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("IV", 4);
        map.put("V", 5);
        map.put("IX", 9);
        map.put("X", 10);
        map.put("XL", 40);
        map.put("L", 50);
        map.put("XC", 90);
        map.put("C", 100);
        map.put("CD", 400);
        map.put("D", 500);
        map.put("CM", 900);
        map.put("M", 1000);

        // build
        int result = 0;
        String pre = "";
        String curr = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            curr = String.valueOf(s.charAt(i));
            if (pre.isEmpty()) {
                result = result + map.get(curr);
                pre = curr;
                continue;
            }
            if (map.get(curr) < map.get(pre)) { // 4和9的情况
                result = result - map.get(pre);
                result = result + map.get(curr + pre);
            } else {
                result = result + map.get(curr);
            }
            pre = curr;
        }
        return result;
    }
}
