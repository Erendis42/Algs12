import java.util.ArrayList;

public class FastCollinearPoints {
  private final ArrayList<LineSegment> lineSegments;

  public FastCollinearPoints(Point[] points) {
    checkInput(points);
    lineSegments = new ArrayList<LineSegment>();
    generateSegments(points);
  }

  private void checkInput(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }

    for (Point point : points) {
      if (point == null) {
        throw new IllegalArgumentException();
      }
    }

    for (int i = 1; i < points.length; i++) {
      if (points[i - 1] == points[i]) {
        throw new IllegalArgumentException();
      }
    }
  }

  private void generateSegments(Point[] points) {
    // the sliding window method is applicable when the array is sorted by the slope they make
    // with an arbitrary point, which is smaller than or equal to every element in the array,
    // in this case the origo is the best choice (0 , 0)

    // TODO: sort

    for (int i = 0; i < points.length - 3; i++) {
      Point p = points[i];
      Point q = points[i + 1];
      Point r = points[i + 2];
      Point s = points[i + 3];
      Point min = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
      Point max = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);

      if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)) {
        // find the minimum and maximum of these points to define a line segment
        for (int j = 0; j < 4; j++) {
          Point t = points[i + j];
          if (t.compareTo(min) < 0) {
            min = t;
          }
          if (t.compareTo(max) > 0) {
            max = t;
          }
        }

        lineSegments.add(new LineSegment(min, max));
        i += 3;
      }
    }
  }


  // the line segments
  public LineSegment[] segments() {
    return lineSegments.toArray(new LineSegment[lineSegments.size()]);
  }

  // the number of line segments
  public int numberOfSegments() {
    return lineSegments.size();
  }
}
