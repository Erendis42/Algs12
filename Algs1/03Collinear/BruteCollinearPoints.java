import java.util.Arrays;

public class BruteCollinearPoints {
  private Point[] points;
  private int n;
  private LineSegment[] lineSegments;
  private double[] slopes;
  private int index = 0;
  
  //finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {
    if(points == null) {
      throw new java.lang.IllegalArgumentException();
    }
    this.points = points;
    n = points.length;
    checkPoints();

    // worst case: unique pairs
    lineSegments = new LineSegment[n*(n-1)/2];
    slopes = new double[lineSegments.length];
    index = 0;
    
    calculateSegments();
  }
  
  //the number of line segments
  public int numberOfSegments() {
    return lineSegments.length;
  }

  // defensive copying
  public LineSegment[] segments() {
    return Arrays.copyOf(lineSegments, lineSegments.length);
  }                
  
  // check if any point in the array is null, or if the array contains a repeated point
  private void checkPoints() {
    Arrays.sort(points);
    
    for (int i = 0; i < points.length; i++) {
      if(points[i] == null) {
        throw new java.lang.IllegalArgumentException();
      }
    }
    
    for (int i = 1; i < points.length; i++) {
      if (points[i-1] == points[i]) {
        throw new java.lang.IllegalArgumentException();
      }
    }
  }

  private void calculateSegments() {
    for (int i = 0; i < points.length-1; i++) {
      for (int j = i+1; j < points.length; j++) {
        Point p = points[i];
        Point q = points[j];
        lineSegments[index] = new LineSegment(p, q);
        slopes[index] = p.slopeTo(q);          
        index++;
      }
    }
    
    // TODO: merge points on the same line into one segment and shrink
    // lineSegments[] array accordingly
    // (collect point with same slope value into a jagged array)
  }  
}
