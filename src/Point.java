import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        // (y1 − y0) / (x1 − x0).
        // Treat the slope of a horizontal line segment as positive zero.
        // Treat the slope of a vertical line segment as positive infinity.
        // Treat the slope of a degenerate line segment (between a point and itself) as negative infinity.

        if (this.x == that.x && this.y == that.y) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.x == that.x) {
            return 0.0d;
        } else if (this.y == that.y) {
            return Double.POSITIVE_INFINITY;
        }

        return (double)(that.y - this.y) / (that.x - this.x);
    }

    public int compareTo(Point that) {
        if (this.y > that.y) {
            return 1;
        } else if (this.y < that.y) {
            return -1;
        }

        if (this.x == that.y) {
            return 0;
        }

        return this.x > that.x ? 1 : -1;
    }

    public Comparator<Point> slopeOrder() {
        return new PointComparator(this);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

//    public static void main(String[] args) {
//
//    }

    private class PointComparator implements Comparator<Point> {

        private final Point point;

        public PointComparator(Point point) {
            this.point = point;
        }

        @Override
        public int compare(Point p1, Point p2) {
            return Double.compare(this.point.slopeTo(p1), this.point.slopeTo(p2));
        }
    }
}
