import java.util.ArrayList;

public class BruteCollinearPoints {  
  private ArrayList<LineSegment> lineSegments;  
  
  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {
    lineSegments = new ArrayList<LineSegment>();
    
    // this is where the brute forcing starts:
    // 4-big sliding window to check if 4 points are on the same line
    // (two points are on the same line if their slopes are equal)
    for (int i = 0; i < points.length-3; i++) {
      Point p = points[i];
      Point q = points[i+1];
      Point r = points[i+2];
      Point s = points[i+3];
      Point min = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
      Point max = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);

      if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)) {
        // find the minimum and maximum of these points to define a line segment
        for (int j = 0; j < 4; j++) {
          Point t = points[i+j]; 
          if(t.compareTo(min) < 0) {
            min = t;
          }
          if(t.compareTo(max) > 0) {
            max = t;
          }
        }
        
        lineSegments.add(new LineSegment(min, max));
        i+=3;
      }
    }
  }
  
  //the line segments
  public LineSegment[] segments() {
    return lineSegments.toArray(new LineSegment[lineSegments.size()]);
  }
  
  //the number of line segments
  public int numberOfSegments() {
    return lineSegments.size();
  }
}
