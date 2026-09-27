package kz.aitu;

public class MergeSorter {
    private int maxDepth;
    private long comparisons;

    public void sort(int[] arr) {
        maxDepth = 0;
        comparisons = 0;
        int[] temp = new int[arr.length];
        mergeSort(arr, temp, 0, arr.length - 1, 1);
    }

    private void mergeSort(int[] arr, int[] temp, int left, int right, int depth) {
        if (depth > maxDepth) maxDepth = depth;
        if (right - left <= 15) {
            insertionSort(arr, left, right);
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSort(arr, temp, left, mid, depth + 1);
        mergeSort(arr, temp, mid + 1, right, depth + 1);
        merge(arr, temp, left, mid, right);
    }

    private void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                comparisons++;
                arr[j + 1] = arr[j];
                j--;
            }
            if (j >= left) comparisons++;
            arr[j + 1] = key;
        }
    }

    private void merge(int[] arr, int[] temp, int left, int mid, int right) {
        for (int i = left; i <= right; i++) temp[i] = arr[i];
        int i = left;
        int j = mid + 1;
        int k = left;
        while (i <= mid && j <= right) {
            comparisons++;
            if (temp[i] <= temp[j]) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = temp[j++];
            }
        }
        while (i <= mid) {
            arr[k++] = temp[i++];
        }
    }

    public int getMaxDepth() { return maxDepth; }
    public long getComparisons() { return comparisons; }
}