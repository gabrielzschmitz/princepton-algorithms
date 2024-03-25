/*
  Program Description:
  --------------------
  LineSegment.java defines an immutable data type for line segments in a 2D
  plane.
  It provides methods for creating line segments, drawing them to a standard
  canvas, and obtaining a string representation of the line segment.

  Implementation Details:
  -----------------------
  - The LineSegment class represents a line segment with two endpoints, p and q,
    which are instances of the Point class.
  - It ensures that endpoints p and q are not null and are distinct.
  - The draw() method draws the line segment to a standard canvas using the
    drawTo() method from the Point class.
  - The toString() method returns a string representation of the line segment in
    the format "p -> q".
  - The hashCode() method is not supported to maintain simplicity and avoid
    complexity introduced by hashing.

  Usage Example:
  --------------
  To use this program, create LineSegment objects and utilize its methods as
  needed:

  Point p1 = new Point(1, 1);
  Point p2 = new Point(2, 2);
  LineSegment line = new LineSegment(p1, p2);
  line.draw();
  System.out.println(line.toString());

  Note: LineSegment.java is designed to work in conjunction with the Point class
  to represent line segments and is intended for use in algorithms operating on
  sets of points, such as finding collinear points.
*/
public class LineSegment {
  private final Point p; // one endpoint of this line segment
  private final Point q; // the other endpoint of this line segment

  /**
   * Initializes a new line segment.
   *
   * @param p one endpoint
   * @param q the other endpoint
   * @throws NullPointerException if either <tt>p</tt> or <tt>q</tt> is <tt>null</tt>
   */
  public LineSegment(Point p, Point q) {
    if (p == null || q == null) {
      throw new IllegalArgumentException("argument to LineSegment constructor is null");
    }
    if (p.equals(q)) {
      throw new IllegalArgumentException(
          "both arguments to LineSegment constructor are the same point: " + p);
    }
    this.p = p;
    this.q = q;
  }

  /** Draws this line segment to standard draw. */
  public void draw() {
    p.drawTo(q);
  }

  /**
   * Returns a string representation of this line segment This method is provide for debugging; your
   * program should not rely on the format of the string representation.
   *
   * @return a string representation of this line segment
   */
  public String toString() {
    return p + " -> " + q;
  }

  /**
   * Throws an exception if called. The hashCode() method is not supported because hashing has not
   * yet been introduced in this course. Moreover, hashing does not typically lead to good
   * *worst-case* performance guarantees, as required on this assignment.
   *
   * @throws UnsupportedOperationException if called
   */
  public int hashCode() {
    throw new UnsupportedOperationException("hashCode() is not supported");
  }
}
