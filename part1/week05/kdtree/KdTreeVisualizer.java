/*
  Program Description:
  --------------------
  KdTreeVisualizer.java provides a graphical user interface for visualizing
  the KdTree data structure. It allows users to interactively add points by
  clicking on the standard draw window. The program then inserts these points
  into a kd-tree and draws the resulting kd-tree.

  Usage Instructions:
  -------------------
  1. Compile the program using the command: javac KdTreeVisualizer.java
  2. Execute the compiled program using the command: java KdTreeVisualizer
  3. Click on the standard draw window to add points to the kd-tree.
  4. The program will continuously update and display the kd-tree as points
     are added.

  Implementation Details:
  -----------------------
  - The KdTreeVisualizer class initializes a rectangular region within the
    standard draw window and creates an instance of the KdTree class.
  - It continuously checks for mouse clicks in the draw window and adds the
    clicked points to the kd-tree.
  - As points are added, the program clears the draw window, redraws the
    kd-tree, and displays the updated visualization.

  Dependencies:
  -------------
  - The program relies on the KdTree class for storing and managing the
    points in the kd-tree.
  - It also uses the StdDraw class from the algs4 library for graphical
    rendering.

  Example:
  --------
  After running the KdTreeVisualizer program, users can click on the draw
  window to add points. As points are added, the kd-tree visualization will
  update accordingly, showing the structure of the kd-tree based on the
  inserted points.
*/
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTreeVisualizer {

  public static void main(String[] args) {
    RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
    StdDraw.enableDoubleBuffering();
    KdTree kdtree = new KdTree();
    while (true) {
      if (StdDraw.isMousePressed()) {
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();
        StdOut.printf("%8.6f %8.6f\n", x, y);
        Point2D p = new Point2D(x, y);
        if (rect.contains(p)) {
          StdOut.printf("%8.6f %8.6f\n", x, y);
          kdtree.insert(p);
          StdDraw.clear();
          kdtree.draw();
          StdDraw.show();
        }
      }
      StdDraw.pause(20);
    }
  }
}
