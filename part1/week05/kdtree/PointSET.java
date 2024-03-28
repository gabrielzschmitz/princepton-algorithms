/*
  Program Description:
  --------------------
  PointSET.java implements a data structure for storing a set of 2D points
  and performing various operations on them, such as insertion, containment
  checks, finding the nearest point, and finding points within a given
  rectangular region.

  Implementation Details:
  -----------------------
  - The PointSET class uses a SET data structure from the algs4 library to
    store the 2D points.
  - It provides methods for inserting points, checking if the set is empty,
    finding the size of the set, checking containment of a point, finding
    points within a given rectangular region, and finding the nearest point
    to a given point.
  - The main() method reads points from a file specified as input and inserts
    them into the PointSET. It then demonstrates various operations on the
    PointSET, such as containment check, checking if it's empty, and finding
    its size.

  Usage Example:
  --------------
  To use this program, create a text file containing 2D points (each point
  on a separate line with x and y coordinates separated by space). Then, run
  the PointSET class with the filename as input:

  java-algs4 PointSET input.txt

  The program will read the points from the file, perform various operations
  on them, and print the results to the console.
*/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdIn;

public class PointSET {

  private final SET<Point2D> all;

  public PointSET() {
    all = new SET<>();
  }

  public boolean isEmpty() {
    return all.isEmpty();
  }

  public int size() {
    return all.size();
  }

  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException("null point");
    }
    if (!all.contains(p)) {
      all.add(p);
    }
  }

  public boolean contains(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException("null point");
    }
    return all.contains(p);
  }

  public void draw() {
    for (Point2D p : all) {
      p.draw();
    }
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException("null rectangle");
    }
    SET<Point2D> list = new SET<>();
    for (Point2D p : all) {
      if (rect.contains(p)) {
        list.add(p);
      }
    }
    return list;
  }

  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException("null point");
    }
    if (all.isEmpty()) {
      return null;
    }
    Point2D n = null;
    for (Point2D q : all) {
      if (n == null) {
        n = q;
        continue;
      }
      if (p.distanceSquaredTo(q) < p.distanceSquaredTo(n)) {
        n = q;
      }
    }
    return n;
  }

  public static void main(String[] args) {
    String filename = StdIn.readLine();
    In in = new In(filename);
    PointSET brute = new PointSET();
    while (!in.isEmpty()) {
      double x = in.readDouble();
      double y = in.readDouble();
      Point2D p = new Point2D(x, y);
      brute.insert(p);
    }
    System.out.println(brute.contains(new Point2D(0.956346, 0.295675)));
    System.out.println(brute.isEmpty());
    System.out.println(brute.size());
  }
}
