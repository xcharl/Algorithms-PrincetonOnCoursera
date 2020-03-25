package edu.princeton.algorithms.sorting.assignment;

import java.io.IOException;
import java.util.ArrayList;

public class BruteCollinearPoints {

    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        this.validateInput(points);

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

        for (var point : points) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    public int numberOfSegments() {
        return this.lineSegments.length;
    }

    public LineSegment[] segments() {
        return this.lineSegments;
    }
}
