package kz.aitu;

import java.util.Random;

public class QuickSorter {
    private int maxDepth;
    private long comparisons;
    private Random rand = new Random();

    public void sort(int[] arr) {
        maxDepth = 0;
        comparisons = 0;
        quickSort(arr, 0, arr.length - 1, 1);
    }

    private void quickSort(int[] arr, int left, int right, int depth) {
        while (left < right) {
            if (depth > maxDepth) maxDepth = depth;
            int pivotIndex = left + rand.nextInt(right - left + 1);
            swap(arr, pivotIndex, right);
            int p = partition(arr, left, right);

            if (p - left < right - p) {
                quickSort(arr, left, p - 1, depth + 1);
                left = p + 1;
            } else {
                quickSort(arr, p + 1, right, depth + 1);
                right = p - 1;
            }
            depth++;
        }
    }

    private int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            comparisons++;
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, right);
        return i + 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public int getMaxDepth() { return maxDepth; }
    public long getComparisons() { return comparisons; }
}