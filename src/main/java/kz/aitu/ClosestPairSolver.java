package kz.aitu;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairSolver {
    private int maxDepth;
    private long comparisons;

    public double findClosest(Point[] points) {
        maxDepth = 0;
        comparisons = 0;
        Point[] sortedByX = points.clone();
        Arrays.sort(sortedByX, Comparator.comparingDouble(p -> p.x));
        return closestPair(sortedByX, 0, sortedByX.length - 1, 1);
    }

    private double closestPair(Point[] points, int left, int right, int depth) {
        if (depth > maxDepth) maxDepth = depth;
        if (right - left <= 3) {
            return bruteForce(points, left, right);
        }

        int mid = left + (right - left) / 2;
        Point midPoint = points[mid];

        double dl = closestPair(points, left, mid, depth + 1);
        double dr = closestPair(points, mid + 1, right, depth + 1);
        double d = Math.min(dl, dr);

        Point[] strip = new Point[right - left + 1];
        int j = 0;
        for (int i = left; i <= right; i++) {
            comparisons++;
            if (Math.abs(points[i].x - midPoint.x) < d) {
                strip[j++] = points[i];
            }
        }

        Arrays.sort(strip, 0, j, Comparator.comparingDouble(p -> p.y));
        for (int i = 0; i < j; ++i) {
            for (int k = i + 1; k < j && (strip[k].y - strip[i].y) < d; ++k) {
                comparisons++;
                double dist = distance(strip[i], strip[k]);
                if (dist < d) d = dist;
            }
        }
        return d;
    }

    private double bruteForce(Point[] points, int left, int right) {
        double min = Double.MAX_VALUE;
        for (int i = left; i <= right; ++i) {
            for (int j = i + 1; j <= right; ++j) {
                comparisons++;
                double dist = distance(points[i], points[j]);
                if (dist < min) min = dist;
            }
        }
        return min;
    }

    private double distance(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    public int getMaxDepth() { return maxDepth; }
    public long getComparisons() { return comparisons; }
}