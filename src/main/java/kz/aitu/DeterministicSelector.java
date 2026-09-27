package kz.aitu;

import java.util.Arrays;

public class DeterministicSelector {
    private int maxDepth;
    private long comparisons;

    public int select(int[] arr, int k) {
        maxDepth = 0;
        comparisons = 0;
        return medianOfMedians(arr, 0, arr.length - 1, k, 1);
    }

    private int medianOfMedians(int[] arr, int left, int right, int k, int depth) {
        if (depth > maxDepth) maxDepth = depth;
        int n = right - left + 1;
        if (n <= 5) {
            Arrays.sort(arr, left, right + 1);
            return arr[left + k - 1];
        }

        int numGroups = (int) Math.ceil((double) n / 5);
        int[] medians = new int[numGroups];
        for (int i = 0; i < numGroups; i++) {
            int groupLeft = left + i * 5;
            int groupRight = Math.min(left + i * 5 + 4, right);
            Arrays.sort(arr, groupLeft, groupRight + 1);
            medians[i] = arr[groupLeft + (groupRight - groupLeft) / 2];
        }

        int pivot = medianOfMedians(medians, 0, numGroups - 1, numGroups / 2 + 1, depth + 1);
        int pivotIndex = partition(arr, left, right, pivot);
        int rank = pivotIndex - left + 1;

        if (k == rank) {
            return arr[pivotIndex];
        } else if (k < rank) {
            return medianOfMedians(arr, left, pivotIndex - 1, k, depth + 1);
        } else {
            return medianOfMedians(arr, pivotIndex + 1, right, k - rank, depth + 1);
        }
    }

    private int partition(int[] arr, int left, int right, int pivot) {
        for (int i = left; i <= right; i++) {
            comparisons++;
            if (arr[i] == pivot) {
                swap(arr, i, right);
                break;
            }
        }
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