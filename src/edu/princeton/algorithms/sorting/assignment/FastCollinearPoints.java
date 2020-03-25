package edu.princeton.algorithms.sorting.assignment;


public class FastCollinearPoints {
    // Finds all line segments containing 4 or more points.
    public FastCollinearPoints(Point[] points) {
        validateInput(points);

        for (var i = 0; i < points.length; i++) {

        }

        // Math.abs(-1);


    }

    public int numberOfSegments(){
        return 0;
    }

    public LineSegment[] segments(){
        return null;
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
