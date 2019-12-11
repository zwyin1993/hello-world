package com.me.practice.prac;

public class Practice {
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

    public static void main(String[] args) {
        String[][] input = new String[][]{{"A", "B"}, {"B", "C"}, {"C", "D"}, {"A", "G"}, {"B", "F"}, {"E", "F"}};
        Practice practice = new Practice();
        practice.findLines(input, "B");
    }
}
