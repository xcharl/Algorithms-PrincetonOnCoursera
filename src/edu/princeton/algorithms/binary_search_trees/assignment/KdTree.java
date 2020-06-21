package edu.princeton.algorithms.binary_search_trees.assignment;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.ArrayList;

public class KdTree {

    private int count = 0;
    private Node root;

    // Construct an empty set of points
    public KdTree() {
    }

    // Is the set empty?
    public boolean isEmpty() {
        return this.size() == 0;
    }

    // Number of points in the set
    public int size() {
        return this.count;
    }

    // Add the point to the set (if it is not already in the set)
    public void insert(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }

        this.root = insert(root, null, point);
    }

    private Node insert(Node node, Node parent, Point2D newPoint) {
        if (node == null) {
            var depth = parent == null ? 0 : parent.depth + 1;
            this.count++;
            return new Node(newPoint, parent, depth);
        }

        if (node.point.compareTo(newPoint) == 0) {
            return node;
        }

        var compare = node.isVertical
                      ? newPoint.x() - node.point.x()
                      : newPoint.y() - node.point.y();

        if (compare < 0) {
            node.bottomLeft = this.insert(node.bottomLeft, node, newPoint);
        } else {
            node.topRight = this.insert(node.topRight, node, newPoint);
        }

        return node;
    }

    // Does the set contain point p?
    public boolean contains(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }

        var currNode = this.root;

        while (true) {
            if (currNode == null) {
                return false;
            }

            if (currNode.point.compareTo(point) == 0) {
                return true;
            }

            var compare = currNode.isVertical
                          ? point.x() - currNode.point.x()
                          : point.y() - currNode.point.y();

            if (compare > 0) {
                currNode = currNode.topRight;
            } else {
                currNode = currNode.bottomLeft;
            }
        }
    }

    // Draw all points to standard draw
    public void draw() {
        if (this.root != null) {
            StdDraw.setPenRadius(0.01);
            this.draw(this.root);
        }
    }

    private void draw(Node node) {
        if (node.bottomLeft != null) {
            this.draw(node.bottomLeft);
        }

        if (node.topRight != null) {
            this.draw(node.topRight);
        }

        if (node.isVertical) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.setPenRadius(0.002);
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.setPenRadius(0.002);
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point.draw();
    }

    // All points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        var points = new ArrayList<Point2D>();
        range(this.root, rect, points);
        return points;
    }

    private void range(Node node, RectHV targetRect, ArrayList<Point2D> points) {
        if (node == null) {
            return;
        }

        if (targetRect.intersects(node.rect)) {
            if (targetRect.contains(node.point)) {
                points.add(node.point);
            }

            this.range(node.topRight, targetRect, points);
            this.range(node.bottomLeft, targetRect, points);
        }
    }

    // A nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D targetPoint) {
        if (targetPoint == null) {
            throw new IllegalArgumentException();
        }

        if (this.root == null) {
            return null;
        }

        return this.nearest(this.root, targetPoint, this.root.point);
    }

    private Point2D nearest(Node node, Point2D targetPoint, Point2D nearestPoint) {
        if (node == null) {
            return nearestPoint;
        }

        if (node.point.distanceSquaredTo(targetPoint) < nearestPoint.distanceSquaredTo(targetPoint)) {
            nearestPoint = node.point;
        }

        var distRectToTarget = node.rect.distanceSquaredTo(targetPoint);
        var distNearestToTarget = nearestPoint.distanceSquaredTo(targetPoint);
        if (distNearestToTarget < distRectToTarget) {
            return nearestPoint;
        }

        var compare = node.isVertical
                      ? targetPoint.x() - node.point.x()
                      : targetPoint.y() - node.point.y();

        if (compare > 0) {
            nearestPoint = this.nearest(node.topRight, targetPoint, nearestPoint);
            nearestPoint = this.nearest(node.bottomLeft, targetPoint, nearestPoint);
        } else {
            nearestPoint = this.nearest(node.bottomLeft, targetPoint, nearestPoint);
            nearestPoint = this.nearest(node.topRight, targetPoint, nearestPoint);
        }

        return nearestPoint;
    }

    private class Node {
        private final Point2D point;
        private final RectHV rect;
        private final boolean isVertical;
        private final int depth;
        private Node topRight;
        private Node bottomLeft;

        public Node(Point2D point, Node parent, int depth) {
            this.point = point;
            this.depth = depth;
            this.isVertical = depth % 2 == 0;

            if (parent == null) {
                this.rect = new RectHV(0, 0, 1, 1);
                return;
            }

            if (parent.isVertical) {
                var compare = point.x() - parent.point.x();
                if (compare < 0) {
                    this.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                           parent.point.x(), parent.rect.ymax());
                } else {
                    this.rect = new RectHV(parent.point.x(), parent.rect.ymin(),
                                           parent.rect.xmax(), parent.rect.ymax());
                }
            } else {
                var compare = point.y() - parent.point.y();
                if (compare < 0) {
                    this.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                           parent.rect.xmax(), parent.point.y());
                } else {
                    this.rect = new RectHV(parent.rect.xmin(), parent.point.y(),
                                           parent.rect.xmax(), parent.rect.ymax());
                }
            }
        }
    }
}
