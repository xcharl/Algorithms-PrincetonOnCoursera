public class BruteCollinearPoints {

    private final Point[] points;

    public BruteCollinearPoints(Point[] points) {
        this.points = points;

        for (var i = 0; i < points.length; i++) {
            for (var j = i + 1; j < points.length; j++) {
                for (var k = j + 1; k < points.length; k++) {
                    for (var l = k + 1; l < points.length; l++) {

                    }
                }
            }
        }
    }

    public int numberOfSegments() {

    }

    public LineSegment[] segments() {

    }
}
