/*
  Program Description:
  --------------------
  BruteCollinearPoints.java is a program that finds collinear points among a
  set of points in the plane using the brute-force algorithm. It examines all
  possible combinations of 4 points and checks if they are collinear. If 4
  points are collinear, it considers them as a line segment and stores them
  for further processing.

  Implementation Details:
  -----------------------
  - The program utilizes the brute-force approach to examine all combinations
    of 4 points and checks if they are collinear.
  - It constructs LineSegment objects for each collinear set of points found.
  - The program ensures that no duplicate line segments are produced.
  - The main method reads input points from a file, visualizes the points on
    a canvas, and then computes and visualizes the collinear line segments.

  Usage Example:
  --------------
  To use this program, provide a file containing point coordinates as input:

  java-algs4 BruteCollinearPoints input.txt

  Note: BruteCollinearPoints.java provides a straightforward way to detect
  collinear points among a set of points, making it suitable for small datasets
  or educational purposes. For larger datasets, more efficient algorithms are
  recommended.
*/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public final class BruteCollinearPoints {

  private final LineSegment[] segments;

  public BruteCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }

    for (int i = 0; i < points.length; i++) {
      if (points[i] == null) {
        throw new IllegalArgumentException();
      }
    }

    int length = points.length;
    Point[] myPoints = points.clone();
    LineSegment[] tempSegments = new LineSegment[length * length];
    int count = 0;
    Point pa, pb, pc, pd;

    Arrays.sort(myPoints);
    for (int i = 0; i < length - 1; i++) {
      if (myPoints[i].compareTo(myPoints[i + 1]) == 0) throw new IllegalArgumentException();
    }

    for (int a = 0; a < length; a++) {
      for (int b = a + 1; b < length; b++) {
        for (int c = b + 1; c < length; c++) {
          for (int d = c + 1; d < length; d++) {
            pa = myPoints[a];
            pb = myPoints[b];
            pc = myPoints[c];
            pd = myPoints[d];
            if (pa.slopeTo(pb) == pa.slopeTo(pc) && pa.slopeTo(pc) == pa.slopeTo(pd)) {
              tempSegments[count++] = new LineSegment(pa, pd);
            }
          }
        }
      }
    }

    segments = new LineSegment[count];
    for (int i = 0; i < count; i++) {
      segments[i] = tempSegments[i];
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

    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
