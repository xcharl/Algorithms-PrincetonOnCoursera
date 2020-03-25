package edu.princeton.algorithms.sorting.assignment.tests;

import edu.princeton.algorithms.sorting.assignment.BruteCollinearPoints;
import edu.princeton.algorithms.sorting.assignment.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BruteCollinearPointsTest {

    @Test
    void segments_collinearPoints_returnsCorrectSegment() {
        var points = new Point[4];
        points[0] = new Point(1, 1);
        points[1] = new Point(2, 2);
        points[2] = new Point(3, 3);
        points[3] = new Point(4, 4);

        var brute = new BruteCollinearPoints(points);
        var segment = brute.segments()[0];

        Assertions.assertEquals(1, brute.numberOfSegments());
        Assertions.assertEquals("(1, 1) -> (4, 4)", segment.toString());
    }

    @Test
    void segments_notCollinearPoints_returnsNoSegments() {
        var points = new Point[4];
        points[0] = new Point(1, 4);
        points[1] = new Point(2, 2);
        points[2] = new Point(3, 3);
        points[3] = new Point(4, 4);

        var brute = new BruteCollinearPoints(points);

        Assertions.assertEquals(0, brute.numberOfSegments());
        Assertions.assertEquals(0, brute.segments().length);
    }
}
