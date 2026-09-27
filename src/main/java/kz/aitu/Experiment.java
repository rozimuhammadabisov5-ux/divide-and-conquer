package kz.aitu;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Experiment {
    private String csvFile = "results/results.csv";

    public void runAllExperiments() {
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.append("Algorithm,InputSize,InputType,Time(ns),MaxDepth,Comparisons\n");

            int[] sizes = {1000, 10000, 100000};
            String[] types = {"Random", "Sorted", "Reverse-sorted", "Duplicate-heavy"};

            for (int n : sizes) {
                for (String type : types) {
                    runSortExperiment(writer, "MergeSort", n, type);
                    runSortExperiment(writer, "QuickSort", n, type);
                }
            }

            for (int n : sizes) {
                runSelectExperiment(writer, "DeterministicSelect", n, "Random");
                runClosestPairExperiment(writer, "ClosestPair", n, "Random");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runSortExperiment(FileWriter writer, String algo, int n, String type) throws IOException {
        int[] arr = generateArray(n, type);
        long startTime = System.nanoTime();
        int depth = 0;
        long comps = 0;

        if (algo.equals("MergeSort")) {
            MergeSorter ms = new MergeSorter();
            ms.sort(arr);
            depth = ms.getMaxDepth();
            comps = ms.getComparisons();
        } else {
            QuickSorter qs = new QuickSorter();
            qs.sort(arr);
            depth = qs.getMaxDepth();
            comps = qs.getComparisons();
        }

        long endTime = System.nanoTime();
        writer.append(algo + "," + n + "," + type + "," + (endTime - startTime) + "," + depth + "," + comps + "\n");
    }

    private void runSelectExperiment(FileWriter writer, String algo, int n, String type) throws IOException {
        int[] arr = generateArray(n, type);
        int k = n / 2;
        DeterministicSelector ds = new DeterministicSelector();

        long startTime = System.nanoTime();
        ds.select(arr, k);
        long endTime = System.nanoTime();

        writer.append(algo + "," + n + "," + type + "," + (endTime - startTime) + "," + ds.getMaxDepth() + "," + ds.getComparisons() + "\n");
    }

    private void runClosestPairExperiment(FileWriter writer, String algo, int n, String type) throws IOException {
        Point[] points = generatePoints(n);
        ClosestPairSolver cps = new ClosestPairSolver();

        long startTime = System.nanoTime();
        cps.findClosest(points);
        long endTime = System.nanoTime();

        writer.append(algo + "," + n + "," + type + "," + (endTime - startTime) + "," + cps.getMaxDepth() + "," + cps.getComparisons() + "\n");
    }

    private int[] generateArray(int n, String type) {
        int[] arr = new int[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            if (type.equals("Random")) arr[i] = rand.nextInt(1000000);
            else if (type.equals("Sorted")) arr[i] = i;
            else if (type.equals("Reverse-sorted")) arr[i] = n - i;
            else if (type.equals("Duplicate-heavy")) arr[i] = rand.nextInt(10);
        }
        return arr;
    }

    private Point[] generatePoints(int n) {
        Point[] points = new Point[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            points[i] = new Point(rand.nextDouble() * 10000, rand.nextDouble() * 10000);
        }
        return points;
    }
}