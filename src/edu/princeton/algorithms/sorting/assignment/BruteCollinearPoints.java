package edu.princeton.algorithms.sorting.assignment;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

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
        StdDraw.setPenRadius();
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }

        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        StdDraw.show();
    }

    public BruteCollinearPoints(Point[] pointsInput) {
        this.validateInput(pointsInput);

        var points = pointsInput.clone();
        Arrays.sort(points);
        var lineSegments = new ArrayList<LineSegment>();
        for (var i = 0; i < points.length - 3; i++) {
            for (var j = i + 1; j < points.length - 2; j++) {
                for (var k = j + 1; k < points.length - 1; k++) {
                    for (var l = k + 1; l < points.length; l++) {
                        var point1 = points[i];
                        var point2 = points[j];
                        var point3 = points[k];
                        var point4 = points[l];

                        var slope12 = point1.slopeTo(point2);
                        var slope13 = point1.slopeTo(point3);
                        var slope14 = point1.slopeTo(point4);

                        if (slope12 == slope13 && slope13 == slope14) {
                            lineSegments.add(new LineSegment(point1, point4));
                        }
                    }
                }
            }
        }

        this.lineSegments = lineSegments.toArray(new LineSegment[0]);
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

    public int numberOfSegments() {
        return this.lineSegments.length;
    }

    public LineSegment[] segments() {
        return this.lineSegments.clone();
    }
}
