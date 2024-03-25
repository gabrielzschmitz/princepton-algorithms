/*
  Program Description:
  --------------------
  FastCollinearPoints.java is a program that finds collinear points among a set
  of points in the plane using the fast collinear points algorithm. It examines
  the points sorted by their natural order and sorts them by their slopes with
  respect to each point. It then identifies line segments formed by 4 or more
  collinear points, avoiding examining the same set of points multiple times.

  Implementation Details:
  -----------------------
  - The program utilizes the fast collinear points algorithm to efficiently
    identify collinear points among a set of points.
  - It constructs LineSegment objects for each collinear set of points found.
  - The program ensures that no duplicate line segments are produced.
  - The main method reads input points from a file, visualizes the points on
    a canvas, and then computes and visualizes the collinear line segments.

  Usage Example:
  --------------
  To use this program, provide a file containing point coordinates as input:

  java-algs4 FastCollinearPoints input.txt

  Note: FastCollinearPoints.java provides a more efficient way to detect
  collinear points among a set of points compared to brute-force methods,
  making it suitable for larger datasets or applications requiring high
  performance.
*/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public final class FastCollinearPoints {

  private final LineSegment[] segments;

  public FastCollinearPoints(Point[] points) {
    if (points == null) throw new IllegalArgumentException();
    for (int i = 0; i < points.length; i++)
      if (points[i] == null) throw new IllegalArgumentException();

    int length = points.length;
    Point[] myPoints = points.clone();
    Point[] fixedOrder;
    int numSegments = 0;
    double testSlope;
    Point origin;
    LineSegment[] testSegments = new LineSegment[length * length];

    Arrays.sort(myPoints);
    fixedOrder = myPoints.clone();
    for (int i = 0; i < length - 1; i++) {
      if (myPoints[i].compareTo(myPoints[i + 1]) == 0) throw new IllegalArgumentException();
    }

    for (int i = 0; i < length; i++) {
      origin = fixedOrder[i];
      Arrays.sort(myPoints);
      Arrays.sort(myPoints, origin.slopeOrder());

      int j = 0;
      int additionalPoints = 0;

      while (j < length - 2) {

        testSlope = origin.slopeTo(myPoints[j]);
        additionalPoints = 1;
        while (j + additionalPoints < length
            && testSlope == origin.slopeTo(myPoints[j + additionalPoints])) {
          additionalPoints++;
        }
        additionalPoints -= 1;

        if (additionalPoints >= 2) {

          if (origin.compareTo(myPoints[j]) < 0
              && origin.compareTo(myPoints[j + additionalPoints]) < 0) {
            testSegments[numSegments] = new LineSegment(origin, myPoints[j + additionalPoints]);
            numSegments++;
          }
          j = j + additionalPoints;
        }
        j++;
      }
    }
    segments = new LineSegment[numSegments];
    for (int i = 0; i < numSegments; i++) {
      segments[i] = testSegments[i];
    }
  }

  public int numberOfSegments() {
    return segments.length;
  }

  public LineSegment[] segments() {
    int length = numberOfSegments();
    LineSegment[] re = new LineSegment[length];
    for (int i = 0; i < length; i++) re[i] = segments[i];
    return re;
  }

  public static void main(String[] args) {

    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }

    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();

    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
