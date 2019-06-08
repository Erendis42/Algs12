import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.DoubleStream;

public class BruteCollinearPoints {
  private Point[] points;
  private int n;
  private LineSegment[] lineSegments;
  private Point[][] pointPairs;
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
    pointPairs = new Point[lineSegments.length][2];
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
        pointPairs[index][0] = p;
        pointPairs[index][1] = q;
        index++;
      }
    }
    
    // also tried
    // double[] slopesUnique = DoubleStream.of(Arrays.sort(Arrays.copyOf(slopes, slopes.length))).distinct().toArray();
    // ...but it won't work 
    

    double[] slopesSorted = Arrays.copyOf(slopes, slopes.length);
    Arrays.sort(slopesSorted);
    double[] slopesUnique = DoubleStream.of(slopesSorted).distinct().toArray();

    HashMap<Double, ArrayList<LineSegment>> lines = new HashMap<Double, ArrayList<LineSegment>>();
    
    for (int i = 0; i < slopesUnique.length; i++) {
      ArrayList<LineSegment> lsl = new ArrayList<LineSegment>();;
      for (int j = 0; j < slopes.length; j++) {
        if(slopesUnique[i] == slopes[j]) {
          lsl.add(lineSegments[j]);
        }
      }
      lines.put(slopesUnique[i], lsl);
    }
    
    // I think I need a fresh start with complex data structures in mind :)
  }  
}
