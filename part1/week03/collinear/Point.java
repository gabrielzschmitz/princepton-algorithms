/*
  Program Description:
  --------------------
  Point.java defines an immutable data type for points in a 2D plane.
  It provides methods for creating points, drawing them to a standard canvas,
  computing slopes between points, comparing points by their coordinates, and
  ordering points based on their slopes with respect to a reference point.

  Implementation Details:
  -----------------------
  - The Point class represents a point with integer coordinates (x, y).
  - It provides methods for drawing points and line segments between points.
  - The slopeTo() method computes the slope between two points.
  - Points can be compared by their y-coordinate, breaking ties by x-coordinate.
  - The slopeOrder() method defines a comparator to compare points by their
    slopes.
  - The program includes a main() method for unit testing the Point data type.

  Usage Example:
  --------------
  To use this program, create Point objects and utilize its methods as needed:

  Point p1 = new Point(1, 1);
  Point p2 = new Point(2, 2);
  double slope = p1.slopeTo(p2);
  Comparator<Point> slopeComparator = p1.slopeOrder();

  Note: Point.java is primarily designed for use with other classes that operate
  on sets of points, such as algorithms for finding collinear points.
*/
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

public class Point implements Comparable<Point> {

  private final int x; // x-coordinate of this point
  private final int y; // y-coordinate of this point

  /**
   * Initializes a new point.
   *
   * @param x the <em>x</em>-coordinate of the point
   * @param y the <em>y</em>-coordinate of the point
   */
  public Point(int x, int y) {
    /* DO NOT MODIFY */
    this.x = x;
    this.y = y;
  }

  /** Draws this point to standard draw. */
  public void draw() {
    /* DO NOT MODIFY */
    StdDraw.point(x, y);
  }

  /**
   * Draws the line segment between this point and the specified point to standard draw.
   *
   * @param that the other point
   */
  public void drawTo(Point that) {
    /* DO NOT MODIFY */
    StdDraw.line(this.x, this.y, that.x, that.y);
  }

  /**
   * Returns the slope between this point and the specified point. Formally, if the two points are
   * (x0, y0) and (x1, y1), then the slope is (y1 - y0) / (x1 - x0). For completeness, the slope is
   * defined to be +0.0 if the line segment connecting the two points is horizontal;
   * Double.POSITIVE_INFINITY if the line segment is vertical; and Double.NEGATIVE_INFINITY if (x0,
   * y0) and (x1, y1) are equal.
   *
   * @param that the other point
   * @return the slope between this point and the specified point
   */
  public double slopeTo(Point that) {
    if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
    else if (this.x == that.x) return Double.POSITIVE_INFINITY;
    else if (this.y == that.y) return 0.0;
    else return (double) (that.y - this.y) / (that.x - this.x);
  }

  /**
   * Compares two points by y-coordinate, breaking ties by x-coordinate. Formally, the invoking
   * point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if y0
   * = y1 and x0 < x1.
   *
   * @param that the other point
   * @return the value <tt>0</tt> if this point is equal to the argument point (x0 = x1 and y0 =
   *     y1); a negative integer if this point is less than the argument point; and a positive
   *     integer if this point is greater than the argument point
   */
  public int compareTo(Point that) {
    if (this.y < that.y || (this.y == that.y && this.x < that.x)) return -1;
    else if (this.x == that.x && this.y == that.y) return 0;
    else return 1;
  }

  /**
   * Compares two points by the slope they make with this point. The slope is defined as in the
   * slopeTo() method.
   *
   * @return the Comparator that defines this ordering on points
   */
  public Comparator<Point> slopeOrder() {
    return new BySlope();
  }

  private class BySlope implements Comparator<Point> {

    public int compare(Point a, Point b) {
      double slopeA = Point.this.slopeTo(a);
      double slopeB = Point.this.slopeTo(b);

      if (slopeA == slopeB) return 0;
      else if (slopeA < slopeB) return -1;
      else return 1;
    }
  }

  /**
   * Returns a string representation of this point. This method is provide for debugging; your
   * program should not rely on the format of the string representation.
   *
   * @return a string representation of this point
   */
  public String toString() {
    /* DO NOT MODIFY */
    return "(" + x + ", " + y + ")";
  }

  /** Unit tests the Point data type. */
  public static void main(String[] args) {
    Point a = new Point(1, 1);
    Point b = new Point(2, 1);
    StdOut.println(a.slopeTo(b));
  }
}
