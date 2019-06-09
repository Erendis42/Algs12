import java.util.ArrayList;

public class FastCollinearPoints  {
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
    // TODO
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
