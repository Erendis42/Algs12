import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
  private final ArrayList<LineSegment> lineSegments;
  private final Point[] myPoints;

  public FastCollinearPoints(Point[] points) {
    checkInput(points);
    // defensive copy
    this.myPoints = Arrays.copyOf(points, points.length);
    findDuplicates();
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
  }

  private void findDuplicates() {
    // look for dupes
    Arrays.parallelSort(myPoints);
    for (int i = 1; i < myPoints.length; i++) {
      if (myPoints[i - 1].compareTo(myPoints[i]) == 0) {
        throw new IllegalArgumentException();
      }
    }
  }

  private void generateSegments() {
    // loop through the array and see if the actual point and at least 3 other points make the same
    // slope.

    // Arrays.parallelSort(myPoints, new Point(0,0).slopeOrder());
    ArrayList<Point> listOfAdjacentPoints = new ArrayList<Point>();
    for (int i = 0; i < myPoints.length; i++) {
      Point[] otherPoints = new Point[myPoints.length - 1];
      int index = 0;
      for (int j = 0; j < myPoints.length; j++) {
        if (i != j) {
          otherPoints[index++] = myPoints[j];
        }
      }

      Arrays.parallelSort(otherPoints, myPoints[i].slopeOrder());
      Double slope = myPoints[i].slopeTo(otherPoints[0]);

      for (int j = 0; j < otherPoints.length; j++) {
        if (j == 0) {
          listOfAdjacentPoints.add(myPoints[i]);
        } else {
          if (slope != myPoints[i].slopeTo(otherPoints[j])) {
            if (listOfAdjacentPoints.size() > 3) {
              Point min = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
              Point max = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
              for (Point point : listOfAdjacentPoints) {
                if (point.compareTo(min) < 0) {
                  min = point;
                }
                if (point.compareTo(max) > 0) {
                  max = point;
                }
              }
              lineSegments.add(new LineSegment(min, max));
            }
            listOfAdjacentPoints.clear();
          }
          // Check if list is empty or the new point we want to add and the last point in the list
          // not only have slopes in common BUT they are actually on the same line.
          // if(listOfAdjacentPoints.isEmpty() ||
          //    onTheSameLine(listOfAdjacentPoints.get(listOfAdjacentPoints.size() - 1), otherPoints[j], slope)) {
            listOfAdjacentPoints.add(otherPoints[j]);
          //}
          slope = myPoints[i].slopeTo(otherPoints[j]);
        }
      }
    }
  }

  private boolean onTheSameLine(Point p, Point q, double slope) {
    
    // if the slope is 0,  
    
    // the equation of a line is y - y1 = m*(x-x1)    
    // ugly hack to extract x and y coordinates of points from their toString() output
    String pStr = p.toString();
    String qStr = q.toString();
    pStr = pStr.replaceAll("[()]","");
    qStr = qStr.replaceAll("[()]","");
    
    int pX = Integer.parseInt(pStr.split(", ")[0]);
    int pY = Integer.parseInt(pStr.split(", ")[1]);
    int qX = Integer.parseInt(qStr.split(", ")[0]);
    int qY = Integer.parseInt(qStr.split(", ")[1]);
    
    return pY - qY == (int) slope * (pX - qX);
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
