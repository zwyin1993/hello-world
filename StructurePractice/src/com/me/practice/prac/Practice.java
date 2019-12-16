package com.me.practice.prac;

public class Practice {
    public static void main(String[] args) {
        String[][] input = new String[][]{{"A", "B"}, {"B", "C"}, {"C", "D"}, {"A", "G"}, {"B", "F"}, {"E", "F"}};
        Practice practice = new Practice();
//        practice.findLines(input, "B");
        String s = practice.longestPalindrome1("abba");
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
}
