/*
  Program Description:
  --------------------
  NearestNeighborVisualizer.java reads points from a file specified as a
  command-line argument and displays them on the standard draw window. It then
  continuously highlights the closest point to the mouse cursor in real-time.

  The program utilizes both brute-force and kd-tree algorithms to determine the
  nearest neighbor. The nearest neighbor according to the brute-force algorithm
  is drawn in red, while the nearest neighbor using the kd-tree algorithm is
  drawn in blue.

  Usage Instructions:
  -------------------
  1. Compile the program using the command: javac NearestNeighborVisualizer.java
  2. Execute the compiled program using the command:
     java NearestNeighborVisualizer input.txt
  3. Move the mouse cursor within the standard draw window to see the nearest
     neighbor highlighted in real-time.

  Implementation Details:
  -----------------------
  - The main method reads points from the specified input file and inserts them
    into both a PointSET (brute-force) and a KdTree data structure.
  - The program continuously checks the location of the mouse cursor and uses
    both data structures to determine the nearest neighbor.
  - It visually highlights the nearest neighbor using different colors to
    distinguish between results obtained from brute-force and kd-tree
	algorithms.

  Dependencies:
  -------------
  - The program depends on the PointSET and KdTree classes for nearest neighbor
    search operations.
  - It also relies on the StdDraw class from the algs4 library for graphical
    rendering.

  Example:
  --------
  Upon execution, the NearestNeighborVisualizer program loads points from the
  input file specified (e.g., input.txt). Users can then move the mouse cursor
  within the draw window, and the program highlights the nearest neighbor point
  in real-time, indicating whether it was determined using brute-force (red) or
  kd-tree (blue) algorithm.
*/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class NearestNeighborVisualizer {

  public static void main(String[] args) {

    // initialize the two data structures with point from file
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

    // process nearest neighbor queries
    StdDraw.enableDoubleBuffering();
    while (true) {

      // the location (x, y) of the mouse
      double x = StdDraw.mouseX();
      double y = StdDraw.mouseY();
      Point2D query = new Point2D(x, y);

      // draw all of the points
      StdDraw.clear();
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setPenRadius(0.01);
      brute.draw();

      // draw in red the nearest neighbor (using brute-force algorithm)
      StdDraw.setPenRadius(0.03);
      StdDraw.setPenColor(StdDraw.RED);
      brute.nearest(query).draw();
      StdDraw.setPenRadius(0.02);

      // draw in blue the nearest neighbor (using kd-tree algorithm)
      StdDraw.setPenColor(StdDraw.BLUE);
      kdtree.nearest(query).draw();
      StdDraw.show();
      StdDraw.pause(40);
    }
  }
}
