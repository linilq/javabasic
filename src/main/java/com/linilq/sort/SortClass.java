package com.linilq.sort;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/16
 */
public class SortClass {
    public static void main(String[] args) {
        int[] k = {1, 0, 7, 6, 5, 4, 3, 2, 9, 8};
//        mySort(k);
        bubbleSort2(k);
        k = new int[]{1, 0, 7, 6, 5, 4, 3, 2, 9, 8};
        bubbleSort3(k);
//        int[] k = {1, 0, 7, 6, 5, 4, 3, 2, 9, 8};
//        bubbleSort(k);
//        k = new int[]{1, 0, 7, 6, 5, 4, 3, 2, 9, 8};
//        bubbleSort1(k);

    }

    private static void outProcess(int i, int[] k) {
        System.out.println("第" + i + "轮排序结果：");
        for (int j = 0; j < k.length; j++) {
            System.out.print(k[j] + " ");
        }
        System.out.println();
    }

    public static void exchange(int[] a, int i, int j) {
        a[i] ^= a[j];
        a[j] ^= a[i];
        a[i] ^= a[j];
    }

    /**
     * 选择排序：选择最小的值放在最前面，第二小的放第二位...以此类推；
     * 需要记录将要放置的位置，选择最小值
     * 由于需要每次都选出一个目标值，所以对比次数为：(N-1)+(N-2)+……+1 = N(N-1)/2
     * 交换次数为：N
     *
     * @param a
     */
    public static void chooseSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int min = i;
            for (int j = 1; j < a.length; j++) {
                if (a[min] > a[j]) {
                    min = j;
                }
            }

            if (min != i) {
                exchange(a, min, i);
                outProcess(i, a);
            }
        }
    }

    /**
     * 插入排序：
     * 给每个值按要求找到它的位置，然后放入；
     * 循环处理每一个位置，将它与在它之前的元素对比，满足条件就交换两者的位置
     *
     * 对比次数为：
     *
     * @param a
     */
    public static void insertSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
                if (a[j] < a[j - 1]) {
                    exchange(a, j - 1, j);
                }
            }
            outProcess(i, a);
        }
    }

    public static void bubbleSort(int[] a) {
        int checkNum = 0;
        for (int i = 0; i < a.length; i++) {
            int j = a.length - 1;
            for (; j > i; j--) {
                checkNum++;
                if (a[j - 1] > a[j]) {
                    exchange(a, j - 1, j);
                }
            }
            outProcess(i, a);
        }
        System.out.println("对比次数：" + checkNum);
    }

    public static void bubbleSort1(int[] a) {
        boolean exchaged = true;
        int checkNum = 0;
        for (int i = 0; i < a.length && exchaged; i++) {
            int j = a.length - 1;
            for (exchaged = false; j > i; j--) {
                checkNum++;
                if (a[j - 1] > a[j]) {
                    exchaged = true;
                    exchange(a, j - 1, j);
                }
            }
            outProcess(i, a);
        }
        System.out.println("对比次数：" + checkNum);
    }

    public static void bubbleSort2(int[] a) {
        int checkNum = 0;
        for (int i = a.length-1; i >0 ; i--) {
            int j = 0;
            for (; j < i; j++) {
                checkNum++;
                if (a[j + 1] < a[j]) {
                    exchange(a, j + 1, j);
                }
            }
            outProcess(i, a);
        }
        System.out.println("对比次数：" + checkNum);
    }

    public static void bubbleSort3(int[] a) {
        boolean exchaged = true;
        int checkNum = 0;
        for (int i = a.length-1; i >0 && exchaged; i--) {
            exchaged = false;
            for (int j = 0; j < i; j++) {
                checkNum++;
                if (a[j + 1] < a[j]) {
                    exchaged = true;
                    exchange(a, j + 1, j);
                }
            }
            outProcess(i, a);
        }
        System.out.println("对比次数：" + checkNum);
    }

    /**
     *  不标准的选择排序法  交换次数多，不需要额外占用内存
     * @param k
     */
    public static void mySort(int[] k) {
        int checkNum = 0;
        for (int i = 0; i < k.length; i++) {
            for (int j = i + 1; j < k.length; j++) {
                checkNum++;
                if (k[i] > k[j]) {
                    exchange(k, i, j);
                }
            }
            outProcess(i, k);
        }
        System.out.println("对比次数：" + checkNum);
    }

}
