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
            var testArrayByCompare = new Point[points.length];
            for (var j = 0; j < points.length; j++) {
                testArrayByCompare[j] = points[j];
            }

            Arrays.sort(points, comparator);

            var testArrayBySlope = new double[points.length];
            for (var j = 0; j < points.length; j++) {
                testArrayBySlope[j] = points[j].slopeTo(refPoint);
            }

            this.findCollinearPoints(lineSegments, refPoint, points);
        }

        this.lineSegments = lineSegments.toArray(new LineSegment[0]);
    }

    private void findCollinearPoints(ArrayList<LineSegment> lineSegments, Point refPoint, Point[] points) {
        int j;
        for (var i = 0; i < points.length; i += (j - i)) {
            var pointISlope = points[i].slopeTo(refPoint);

            for (j = i + 1; j < points.length; j++) {
                var pointJSlope = points[j].slopeTo(refPoint);

                // Need at least 4 points, so 3 consecutive values in array.
                if (pointISlope != pointJSlope && (j - i) < 3) {
                    break;
                } else if (pointISlope != pointJSlope && (j - i) >= 3) {
                    var startPoint = refPoint.compareTo(points[i]) < 0;
                    checkForExistingLineSegmentAndAdd(lineSegments, points[i], points[j - 1]);
                    return;
                }
            }
        }
    }

    private void checkForExistingLineSegmentAndAdd(
            ArrayList<LineSegment> lineSegments,
            Point startPoint,
            Point endPoint) {
        // TODO - fix this
        for (var lineSegment : lineSegments) {
            if (lineSegment.toString().equals(startPoint.toString() + " -> " + endPoint.toString())) {
                return;
            }
        }

        var lineSegment = new LineSegment(startPoint, endPoint);
        lineSegments.add(lineSegment);
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
