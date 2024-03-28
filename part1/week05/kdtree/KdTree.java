/*
  Program Description:
  --------------------
  KdTree.java implements a data structure for storing a set of 2D points
  and performing various operations on them, such as insertion, containment
  checks, finding the nearest point, and finding points within a given
  rectangular region. It uses a 2d-tree algorithm, which is a generalization
  of a binary search tree to two-dimensional keys.

  Implementation Details:
  -----------------------
  - The KdTree class maintains a balanced binary search tree where each node
    corresponds to a point in the plane. The tree alternates between
    vertical and horizontal partitions at each level, based on the x- or
    y-coordinates of the points.
  - It provides methods for inserting points, checking if the tree is empty,
    finding the size of the tree, checking containment of a point, finding
    points within a given rectangular region, and finding the nearest point
    to a given point.
  - The Node class represents individual nodes in the KdTree and includes
    methods for drawing the nodes on a graphical canvas.
  - The main() method is included for unit testing purposes.

  Usage Example:
  --------------
  To use this program, instantiate a KdTree object and perform various
  operations on it, such as inserting points, checking containment, finding
  nearest points, and so on. Here is an example of how to use the KdTree:

  KdTree kdtree = new KdTree();
  kdtree.insert(new Point2D(0.5, 0.5));
  kdtree.insert(new Point2D(0.2, 0.3));
  kdtree.insert(new Point2D(0.8, 0.1));
  boolean contains = kdtree.contains(new Point2D(0.5, 0.5));
  Point2D nearest = kdtree.nearest(new Point2D(0.4, 0.6));
*/
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class KdTree {

  private int size = 0;
  private Point2D point = null;

  private Node root = null;

  private static class Node {
    Point2D point;
    RectHV rect;
    Node lb, rt;
    boolean vertical;

    private Node(Point2D point, boolean vertical, Node prev) {
      this.point = point;
      this.vertical = vertical;

      if (prev == null) {
        this.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
      } else {
        double xmin = prev.rect.xmin();
        double xmax = prev.rect.xmax();
        double ymin = prev.rect.ymin();
        double ymax = prev.rect.ymax();

        int comp = prev.compareTo(point);

        if (this.vertical) {
          if (comp > 0) {
            ymax = prev.point.y();
          } else {
            ymin = prev.point.y();
          }
        } else {
          if (comp > 0) {
            xmax = prev.point.x();
          } else {
            xmin = prev.point.x();
          }
        }

        this.rect = new RectHV(xmin, ymin, xmax, ymax);
      }
    }

    private int compareTo(Point2D that) {
      if (this.vertical) {
        return Double.compare(this.point.x(), that.x());
      } else {
        return Double.compare(this.point.y(), that.y());
      }
    }

    private void draw() {
      StdDraw.setPenRadius(.007);
      if (this.vertical) {
        StdDraw.setPenColor(Color.red);
        StdDraw.line(this.point.x(), this.rect.ymin(), this.point.x(), this.rect.ymax());
      } else {
        StdDraw.setPenColor(Color.blue);
        StdDraw.line(this.rect.xmin(), this.point.y(), this.rect.xmax(), this.point.y());
      }
      StdDraw.setPenRadius(.01);
      StdDraw.setPenColor(Color.black);
      point.draw();
      if (this.lb != null) {
        this.lb.draw();
      }
      if (this.rt != null) {
        this.rt.draw();
      }
    }
  }

  public KdTree() {}

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException("null point");
    }
    root = insert(root, p, true, null);
  }

  private Node insert(Node h, Point2D point, boolean isVertical, Node prev) {
    if (h == null) {
      size++;
      return new Node(point, isVertical, prev);
    }
    if (h.point.compareTo(point) == 0) {
      return h;
    }
    int comp = h.compareTo(point);
    if (comp > 0) {
      h.lb = insert(h.lb, point, !isVertical, h);
    } else {
      h.rt = insert(h.rt, point, !isVertical, h);
    }
    return h;
  }

  private Point2D get(Point2D point) {
    return get(root, point);
  }

  private Point2D get(Node h, Point2D point) {
    if (h == null) {
      return null;
    }
    if (h.point.compareTo(point) == 0) {
      return h.point;
    }
    int comp = h.compareTo(point);
    if (comp > 0) {
      return get(h.lb, point);
    } else {
      return get(h.rt, point);
    }
  }

  public boolean contains(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException("null point");
    }
    return get(p) != null;
  }

  public void draw() {
    if (root != null) {
      root.draw();
    }
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException("null rectangle");
    }
    if (isEmpty()) {
      return null;
    }
    List<Point2D> list = new LinkedList<>();
    range(root, rect, list);
    return list;
  }

  private void range(Node h, RectHV rect, List<Point2D> list) {
    if (h == null) {
      return;
    }
    if (rect.intersects(h.rect)) {
      if (rect.contains(h.point)) {
        list.add(h.point);
      }
      range(h.lb, rect, list);
      range(h.rt, rect, list);
    }
  }

  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException("null point");
    }
    if (isEmpty()) {
      return null;
    }
    point = root.point;
    nearest(root, p);
    return point;
  }

  private void nearest(Node h, Point2D p) {
    if (h == null) {
      return;
    }
    if (point == null) {
      point = h.point;
    }
    if (h.rect.distanceSquaredTo(p) < p.distanceSquaredTo(point)) {
      if (p.distanceSquaredTo(h.point) < p.distanceSquaredTo(point)) {
        point = h.point;
      }
      if (h.compareTo(p) > 0) {
        nearest(h.lb, p);
        nearest(h.rt, p);
      } else {
        nearest(h.rt, p);
        nearest(h.lb, p);
      }
    }
  }

  public static void main(String[] args) {
    // Create a KdTree object
    KdTree kdtree = new KdTree();

    // Generate and insert random points into the tree
    Random random = new Random();
    for (int i = 0; i < 10; i++) {
      double x = random.nextDouble();
      double y = random.nextDouble();
      kdtree.insert(new Point2D(x, y));
    }

    // Draw the KdTree
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 1);
    StdDraw.setYscale(0, 1);
    kdtree.draw();
    StdDraw.show();

    // Find a random point and check if it exists in the KdTree
    Point2D randomPoint = new Point2D(random.nextDouble(), random.nextDouble());
    System.out.println("Random Point: " + randomPoint);
    if (kdtree.contains(randomPoint)) {
      System.out.println("KdTree contains the random point.");
    } else {
      System.out.println("KdTree does not contain the random point.");
    }

    // Find the nearest point to a random query point
    Point2D queryPoint = new Point2D(random.nextDouble(), random.nextDouble());
    System.out.println("Query Point: " + queryPoint);
    Point2D nearestPoint = kdtree.nearest(queryPoint);
    System.out.println("Nearest Point: " + nearestPoint);

    // Find points within a rectangular region
    RectHV rect = new RectHV(0.25, 0.25, 0.75, 0.75);
    System.out.println("Points within the rectangle " + rect + ": ");
    Iterable<Point2D> pointsInRange = kdtree.range(rect);
    for (Point2D point : pointsInRange) {
      System.out.println(point);
    }
  }
}
