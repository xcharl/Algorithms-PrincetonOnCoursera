package edu.princeton.algorithms.sorting.assignment;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final LineSegment[] lineSegments;

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }

        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        StdDraw.show();
    }

    // Finds all line segments containing 4 or more points.
    public FastCollinearPoints(Point[] pointsInput) {
        validateInput(pointsInput);

        var lineSegments = new ArrayList<LineSegment>();

        for (var i = 0; i < pointsInput.length; i++) {
            var points = pointsInput.clone();
            var refPoint = points[i];
            var comparator = refPoint.slopeOrder();

            // First sort by smallest to largest to get correct line
            // segment end points, as merge sort is stable.
            Arrays.sort(points);

            Arrays.sort(points, comparator);

            this.findCollinearPoints(lineSegments, refPoint, points);
        }

        this.lineSegments = lineSegments.toArray(new LineSegment[0]);
    }

    private void findCollinearPoints(ArrayList<LineSegment> lineSegments, Point refPoint, Point[] points) {
        // Compare consecutive points to find groups of 3 (reference point makes it a group of 4).
        // Skip [0] as it's the current point.
        var start = 0;
        var current = 0;

        while (current < points.length) {
            var startSlope = refPoint.slopeTo(points[start]);

            do {
                current++;
            } while (current < points.length && refPoint.slopeTo(points[current]) == startSlope);

            if (current - start >= 3
                    && this.isRefPointSmallestInGroup(refPoint, points, start, current - 1)) {
                lineSegments.add(new LineSegment(refPoint, points[current - 1]));
            }

            start = current;
        }
    }

    private boolean isRefPointSmallestInGroup(Point refPoint, Point[] points, int start, int end) {
        for (var k = start; k <= end; k++) {
            // Only add LineSegment if refPoint is the very lowest point
            // in order to avoid duplicate line segments.
            if (refPoint.compareTo(points[k]) >= 0) {
                return false;
            }
        }

        return true;
    }

    public int numberOfSegments() {
        return this.lineSegments.length;
    }

    public LineSegment[] segments() {
        return this.lineSegments.clone();
    }

    private void validateInput(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (var i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }

            for (var j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    // Duplicate point found.
                    throw new IllegalArgumentException();
                }
            }
        }
    }
}
