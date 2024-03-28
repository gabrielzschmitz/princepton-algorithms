/*
  Program Description:
  --------------------
  PuzzleVisualizer.java implements a program to visualize the solving process
  of number board puzzles using the A* search algorithm with Manhattan
  distance heuristic. It takes an initial board configuration as input,
  solves the puzzle, and visually displays each step of the solution.

  Implementation Details:
  -----------------------
  - The PuzzleVisualizer class contains a draw() method to draw the board
    configuration, highlighting the changes between consecutive steps.
  - It uses StdDraw library to create a graphical representation of the
    board and display relevant information such as Hamming and Manhattan
    distances.
  - The main() method reads the initial board from input, solves the puzzle
    using the Solver class, and visualizes the solution steps.

  Usage Example:
  --------------
  To use this program, provide an initial board configuration as input and
  run the PuzzleVisualizer:

  java-algs4 PuzzleVisualizer input.txt

  Note: input.txt should contain the dimensions of the board followed by
  the board configuration. The PuzzleVisualizer will visualize the solving
  process, showing each step of the solution and indicating if the puzzle
  is solvable or not.
*/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.Font;

public class PuzzleVisualizer {
  private static final int DELAY = 1500;

  public static void draw(Board previous, Board current) {
    int dimension = current.dimension();
    StdDraw.clear();
    StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setXscale(-0.05 * dimension, 1.05 * dimension);
    StdDraw.setYscale(-0.05 * dimension, 1.05 * dimension);

    int[][] previousBlocksCache = previous.getBlocksCache();
    int[][] currentBlocksCache = current.getBlocksCache();

    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        int prevValue = previousBlocksCache[i][j];
        int currValue = currentBlocksCache[i][j];
        if (prevValue != currValue) {
          StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        } else {
          StdDraw.setPenColor(StdDraw.WHITE);
        }
        StdDraw.filledSquare(j + 0.5, dimension - i - 0.5, 0.45);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(j + 0.5, dimension - i - 0.5, String.format(" %2d", currValue));
      }
    }

    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.text(0.25 * dimension, -0.025 * dimension, "Hamming: " + current.hamming());
    StdDraw.text(0.5 * dimension, -0.025 * dimension, "Goal: " + current.isGoal());
    StdDraw.text(0.75 * dimension, -0.025 * dimension, "Manhattan: " + current.manhattan());
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        blocks[i][j] = in.readInt();
      }
    }
    Board initial = new Board(blocks);
    Solver solver = new Solver(initial);

    draw(initial, initial);
    StdDraw.show();
    StdDraw.pause(DELAY);

    StdDraw.enableDoubleBuffering();
    if (!solver.isSolvable()) {
      StdDraw.text(0.5 * n, 1 * n, "Unsolvable puzzle");
      StdDraw.show();
      StdDraw.pause(DELAY);
    } else {
      Board previous = initial;
      Board last = initial;
      for (Board current : solver.solution()) {
        draw(previous, current);
        StdDraw.text(0.5 * n, 1 * n, "Minimum number of moves = " + solver.moves() + "\n");
        StdDraw.show();
        StdDraw.pause(DELAY);
        previous = current;
        last = current;
      }
      draw(initial, last);
      StdDraw.text(0.5 * n, 1 * n, "Minimum number of moves = " + solver.moves() + "\n");
      StdDraw.show();
      StdDraw.pause(DELAY);
    }
  }
}
