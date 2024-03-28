/*
  Program Description:
  --------------------
  RangeSearchVisualizer.java reads points from a file specified as a
  command-line argument and displays them on the standard draw window. Users can
  then select a rectangular region by dragging the mouse, and the program
  visually indicates all points within that region.

  The program utilizes both brute-force and kd-tree algorithms for range search.
  Results obtained using the brute-force algorithm are drawn in red, while those
  obtained using the kd-tree algorithm are drawn in blue.

  Usage Instructions:
  -------------------
  1. Compile the program using the command: javac RangeSearchVisualizer.java
  2. Execute the compiled program using the command:
     java RangeSearchVisualizer input.txt
  3. Click and drag the mouse to select a rectangular region.
  4. Points within the selected region are displayed in both red and blue,
     representing the results obtained using brute-force and kd-tree algorithms,
     respectively.

  Implementation Details:
  -----------------------
  - The main method reads points from the specified input file and inserts them
    into both a PointSET (brute-force) and a KdTree data structure.
  - The program continuously checks for mouse events to enable users to select
    rectangular regions on the standard draw window.
  - It visually displays the points within the selected region using different
    colors to distinguish between results obtained from brute-force and kd-tree
    algorithms.

  Dependencies:
  -------------
  - The program depends on the PointSET and KdTree classes for range search
    operations.
  - It also relies on the StdDraw class from the algs4 library for graphical
    rendering.

  Example:
  --------
  Upon execution, the RangeSearchVisualizer program loads points from the input
  file specified (e.g., input.txt). Users can then click and drag the mouse to
  select a rectangular region on the draw window. Points within the selected
  region are highlighted in red (brute-force) and blue (kd-tree), allowing users
  to compare the results obtained from both algorithms.
*/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class RangeSearchVisualizer {

  public static void main(String[] args) {

    String filename = args[0];
    In in = new In(filename);
    PointSET brute = new PointSET();
    KdTree kdtree = new KdTree();
    while (!in.isEmpty()) {
      double x = in.readDouble();
      double y = in.readDouble();
      Point2D p = new Point2D(x, y);
      kdtree.insert(p);
      brute.insert(p);
    }

    double x0 = 0.0, y0 = 0.0;
    double x1 = 0.0, y1 = 0.0;
    boolean isDragging = false;

    StdDraw.clear();
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(0.01);
    brute.draw();
    StdDraw.show();

    StdDraw.enableDoubleBuffering();
    while (true) {

      if (StdDraw.isMousePressed() && !isDragging) {
        x0 = x1 = StdDraw.mouseX();
        y0 = y1 = StdDraw.mouseY();
        isDragging = true;
      } else if (StdDraw.isMousePressed() && isDragging) {
        x1 = StdDraw.mouseX();
        y1 = StdDraw.mouseY();
      } else if (!StdDraw.isMousePressed() && isDragging) {
        isDragging = false;
      }

      StdDraw.clear();
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setPenRadius(0.01);
      brute.draw();

      RectHV rect =
          new RectHV(
              Math.min(x0, x1), Math.min(y0, y1),
              Math.max(x0, x1), Math.max(y0, y1));
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setPenRadius();
      rect.draw();

      StdDraw.setPenRadius(0.03);
      StdDraw.setPenColor(StdDraw.RED);
      for (Point2D p : brute.range(rect)) p.draw();

      StdDraw.setPenRadius(0.02);
      StdDraw.setPenColor(StdDraw.BLUE);
      for (Point2D p : kdtree.range(rect)) p.draw();

      StdDraw.show();
      StdDraw.pause(20);
    }
  }
}
