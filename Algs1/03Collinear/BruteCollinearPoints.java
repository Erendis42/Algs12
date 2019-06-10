import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
  private final ArrayList<LineSegment> lineSegments;
  private Point[] points;

  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {
    checkInput(points);
    this.points = points;
    lineSegments = new ArrayList<LineSegment>();
    generateSegments();
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
    
    // look for duplicates    
    Point[] pointsCopy = Arrays.copyOf(points, points.length);    
    Arrays.parallelSort(pointsCopy);
    for (int i = 1; i < pointsCopy.length; i++) {
      if (pointsCopy[i - 1].compareTo(pointsCopy[i]) == 0) {
        throw new IllegalArgumentException();
      }
    }
  }

  private void generateSegments() {
    // this is where the brute forcing starts
    for (int i = 0; i < points.length - 3; i++) {
      for (int j = i + 1; j < points.length - 2; j++) {
        for (int k = j + 1; k < points.length - 1; k++) {
          for (int l = k + 1; l < points.length; l++) {
            Point[] pqrs = {points[i], points[j], points[k], points[l]};
            Point p = pqrs[0];
            Point q = pqrs[1];
            Point r = pqrs[2];
            Point s = pqrs[3];
            Point min = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
            Point max = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);

            if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)) {
              // find the minimum and maximum of these points to define a line segment
              for (Point t : pqrs) {
                if (t.compareTo(min) < 0) {
                  min = t;
                }
                if (t.compareTo(max) > 0) {
                  max = t;
                }
              }
              lineSegments.add(new LineSegment(min, max));
            }
          }
        }
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
