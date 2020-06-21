package edu.princeton.algorithms.binary_search_trees.assignment;

import edu.princeton.cs.algs4.*;

public class PointSET {
    private final SET<Point2D> set;

    // Construct an empty set of points
    public PointSET() {
        this.set = new SET<>();
    }

    // Is the set empty?
    public boolean isEmpty() {
        return this.size() == 0;
    }

    // Number of points in the set
    public int size() {
        return this.set.size();
    }

    // Add the point to the set (if it is not already in the set)
    // O(log n)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (!this.set.contains(p)) {
            this.set.add(p);
        }
    }

    // Does the set contain point p?
    // O(log n)
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return this.set.contains(p);
    }

    // Draw all points to standard draw
    public void draw() {
        for (var point : this.set) {
            StdDraw.setPenRadius(0.01);
            point.draw();
        }
    }

    // All points that are inside the rectangle (or on the boundary)
    // O(n)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        var pointsInRect = new Stack<Point2D>();

        for (var point : this.set) {
            if (point.x() >= rect.xmin()
                    && point.x() <= rect.xmax()
                    && point.y() >= rect.ymin()
                    && point.y() <= rect.ymax()) {
                pointsInRect.push(point);
            }
        }

        return pointsInRect;
    }

    // A nearest neighbor in the set to point p; null if the set is empty
    // O(n)
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Point2D nearestPoint = null;
        for (var point : this.set) {
            if (nearestPoint == null) {
                nearestPoint = point;
            } else if (point.distanceSquaredTo(p) < nearestPoint.distanceSquaredTo(p)) {
                nearestPoint = point;
            }

        }

        return nearestPoint;
    }
}
