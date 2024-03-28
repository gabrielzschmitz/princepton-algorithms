/*
  Program Description:
  --------------------
  Solver.java implements a program to solve number board puzzles using the
  A* search algorithm with Manhattan distance heuristic. It takes an initial
  board configuration as input and finds the minimum number of moves required
  to reach the goal state. If the initial board is unsolvable, it prints a
  message indicating so.

  Implementation Details:
  -----------------------
  - The Solver class contains methods to solve number board puzzles, check
    solvability, count moves, and obtain the solution path.
  - It uses the A* search algorithm with a priority queue to efficiently
    explore possible moves and select the most promising ones.
  - The Node class represents a search node with the board configuration,
    previous node, Manhattan distance, and number of moves.
  - The ManhattanNodeComparator class compares nodes based on their Manhattan
    distances for priority queue ordering.
  - The main() method reads the initial board from input, solves the puzzle,
    and prints the minimum number of moves and solution path.

  Usage Example:
  --------------
  To use this program, provide an initial board configuration as input and
  run the Solver:

  java-algs4 Solver input.txt

  Note: input.txt should contain the dimensions of the board followed by
  the board configuration. The Solver will print the minimum number of moves
  and the solution path if the puzzle is solvable, or indicate if it is
  unsolvable.
*/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver {
  private final List<Board> solution = new ArrayList<>();

  public Solver(Board initial) {
    if (initial == null) {
      throw new IllegalArgumentException("initial board can't be null");
    }
    solve(initial);
  }

  private void solve(Board b) {
    MinPQ<Node> pq = new MinPQ<>(new ManhattanNodeComparator());
    MinPQ<Node> pqTwin = new MinPQ<>(new ManhattanNodeComparator());

    Node currentNode = new Node(b, null, 0);
    Node currentTwinNode = new Node(b.twin(), null, 0);

    pq.insert(currentNode);
    pqTwin.insert(currentTwinNode);

    while (!currentNode.board.isGoal() && !currentTwinNode.board.isGoal()) {
      currentNode = pq.delMin();
      for (Board neighBoard : currentNode.board.neighbors()) {
        if (currentNode.previous == null || !currentNode.previous.board.equals(neighBoard)) {
          pq.insert(new Node(neighBoard, currentNode, currentNode.moves + 1));
        }
      }

      currentTwinNode = pqTwin.delMin();
      for (Board neighBoard : currentTwinNode.board.neighbors()) {
        if (currentTwinNode.previous == null
            || !currentTwinNode.previous.board.equals(neighBoard)) {
          pqTwin.insert(new Node(neighBoard, currentTwinNode, currentTwinNode.moves + 1));
        }
      }
    }

    if (currentNode.board.isGoal() && !currentTwinNode.board.isGoal()) {
      while (currentNode != null) {
        solution.add(currentNode.board);
        currentNode = currentNode.previous;
      }
      Collections.reverse(solution);
    }
  }

  public boolean isSolvable() {
    return !solution.isEmpty();
  }

  public int moves() {
    return solution.size() - 1;
  }

  public Iterable<Board> solution() {
    if (isSolvable()) {
      return solution;
    } else {
      return null;
    }
  }

  private static class Node {
    private final Board board;
    private final Node previous;
    private final int manhattan;
    private final int moves;

    private Node(Board board, Node previous, int moves) {
      this.board = board;
      this.previous = previous;
      this.moves = moves;
      this.manhattan = board.manhattan() + moves;
    }
  }

  private static class ManhattanNodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node n1, Node n2) {
      return n1.manhattan - n2.manhattan;
    }
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

    if (!solver.isSolvable()) {
      StdOut.println("Unsolvable puzzle");
    } else {
      StdOut.println("Minimum number of moves = " + solver.moves() + "\n");
      for (Board board : solver.solution()) {
        StdOut.println(board);
      }
    }
  }
}
