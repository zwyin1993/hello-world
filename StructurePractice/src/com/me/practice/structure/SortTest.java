package com.me.practice.structure;

import java.util.Arrays;
import java.util.Collections;

/**
 * .
 *
 * @author zwyin
 */
public class SortTest {
    public static void main(String[] args) {
//        int[] arr = {1, 5, 2};
        int[] arr = {1, 5, 6, 7, 8, 10, 10, 10, 10, 10, 10};
//        mergeSort(arr);
        int index = binarysearch1(arr, 9);
//        System.out.println(Arrays.toString(index));
        System.out.println(index);
    }

    /**
     * 查找第一个大于等于给定值的元素.
     * 如果是查找最后一个小于等于给定值的元素；修改两个地方
     * 1.当arr[mid] == target的时候，向右移；low = mid
     * 2.跳出循环后，交换两个if的顺序，并修改大于号为小于号
     *
     * @param arr
     * @param target
     * @return
     */
    public static int binarysearch1(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        while (low + 1 < high) {
            int mid = low + ((high - low) >> 1);
            if (arr[mid] == target) {
                high = mid;
            } else if (arr[mid] < target) {
                low = mid;
            } else if (arr[mid] > target) {
                high = mid;
            }
        }
        if (arr[low] >= target) {
            return low;
        }
        if (arr[high] >= target) {
            return high;
        }
        return -1;
    }

    /**
     * 在有序数列中插入一个数.
     * 思路：找到第一个>=target的数据，然后在这之前插入
     *
     * @param arr    有序数列 {1, 2, 6}
     * @param target 待插入的数 5
     */
    public static int[] binaryInsert(int[] arr, int target) {
        int[] result = new int[arr.length + 1];
        if (arr[arr.length - 1] <= target) {
            System.arraycopy(arr, 0, result, 0, arr.length);
            result[result.length - 1] = target;
            return result;
        }
        if (arr[0] >= target) {
            System.arraycopy(arr, 0, result, 1, arr.length);
            result[0] = target;
            return result;
        }
        int low = 0;
        int high = arr.length - 1;
        // 1.找到待插入的位置
        int index = 0;
        while (low + 1 < high) {
            int mid = low + ((high - low) >> 1);
            if (arr[mid] == target) {
                low = mid;
                break;
            }
            if (arr[mid] < target) {
                low = mid;
            } else if (arr[mid] > target) {
                high = mid;
            }
        }
        if (arr[low] >= target) {
            index = low;
        } else {
            index = high;
        }
        int lessLength = index;
        System.arraycopy(arr, 0, result, 0, lessLength);
        result[index] = target;
        System.arraycopy(arr, index, result, index + 1, arr.length - lessLength);
        return result;
    }

    public static int binarySearch(int[] arr, int target) {
        return binarySearch(arr, 0, arr.length - 1, target);
    }

    public static int binarySearch(int[] arr, int low, int high, int target) {
        while (low + 1 < high) {
            int mid = low + ((high - low) >> 1);
            if (arr[mid] == target) {
                return mid;
            }
            if (arr[mid] < target) {
                low = mid;
            } else if (arr[mid] > target) {
                high = mid;
            }
        }

        if (arr[low] == target) {
            return low;
        }
        if (arr[high] == target) {
            return high;
        }
        return -1;
    }

    /**
     * 归并排序.
     *
     * @param arr 带排序数组
     */
    public static void mergeSort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }

        int mid = (start + end) / 2;
        mergeSort(arr, start, mid);
        mergeSort(arr, mid + 1, end);
        merge(arr, start, mid, end);
    }

    private static void merge(int[] arr, int start, int mid, int end) {
        int[] tmp = new int[arr.length];
        for (int i = start, j = mid + 1, point = start; point <= end; point++) {
            if (i <= mid && (j > end || arr[i] <= arr[j])) {
                tmp[point] = arr[i];
                i++;
            } else if (j <= end) {
                tmp[point] = arr[j];
                j++;
            }
        }

        for (int i = start; i <= end; i++) {
            arr[i] = tmp[i];
        }
    }

    /**
     * 快排之分区.
     *
     * @param arr   数组
     * @param start 开始索引
     * @param end   结束索引
     */
    private static int partition(int[] arr, int start, int end) {
        int point = arr[end];
        int i = start;
        for (int j = start; j <= end; j++) {
            if (arr[j] < point) {
                int tmp = arr[j];
                arr[j] = arr[i];
                arr[i] = tmp;
                i++;
            }
        }

        arr[end] = arr[i];
        arr[i] = point;
        return i;
    }

    public static void quickSort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }

        int point = partition(arr, start, end);
        quickSort(arr, start, point - 1);
        quickSort(arr, point + 1, end);
    }

    /**
     * 计数排序.
     */
    public static void countSort(int[] arr, int start, int end) {
        // 1.构建计数数组
        int max = 0;
        for (int val : arr) {
            max = max < val ? val : max;
        }
        // 记录每个<=桶值的个数
        int[] countArr = new int[max + 1];
        for (int anArr : arr) {
            countArr[anArr]++;
        }
        for (int i = 1; i < countArr.length; i++) {
            countArr[i] = countArr[i] + countArr[i - 1];
        }

        // 2.将原始数组中的值，排序到新的数组中
        int[] tmp = new int[arr.length];
        for (int anArr : arr) {
            int index = countArr[anArr] - 1;
            tmp[index] = anArr;
            countArr[anArr]--;
        }
        System.arraycopy(tmp, 0, arr, 0, arr.length);
    }

}
