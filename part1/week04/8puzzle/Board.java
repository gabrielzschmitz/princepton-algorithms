/*
  Program Description:
  --------------------
  Board.java implements an API for a number board, particularly designed
  for solving the 8-puzzle problem but adaptable to any size. It provides
  methods for creating boards, calculating board metrics such as Hamming
  and Manhattan distances, determining whether a board is the goal state,
  obtaining neighboring boards, and comparing boards for equality.

  Implementation Details:
  -----------------------
  - The Board class represents a number board with dimensions defined by
    the input array of blocks.
  - It calculates Hamming and Manhattan distances to evaluate board
    similarity.
  - The isGoal() method checks if the board is in the goal state.
  - The twin() method returns a twin board by swapping any pair of blocks.
  - The equals() method compares two boards for equality.
  - The neighbors() method generates neighboring boards by swapping zero
    with adjacent blocks.
  - The toString() method provides a string representation of the board.
  - The main() method demonstrates board functionality, including
    creating boards, checking equality, and printing metrics.

  Usage Example:
  --------------
  To use this program, create a Board object and utilize its methods to
  solve number board problems:

  int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
  Board board = new Board(blocks);
  System.out.println(board.toString());
  System.out.println("Hamming distance: " + board.hamming());
  System.out.println("Manhattan distance: " + board.manhattan());
  System.out.println("Is goal board? " + board.isGoal());

  Note: Board.java is designed to represent and manipulate number boards
  efficiently for solving puzzles like the 8-puzzle, sliding puzzles, and
  other related problems.
*/
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.List;

public class Board {
  private final int[][] blocksCache;
  private int zeroI;
  private int zeroJ;

  private final int dimension;
  private int hamming;
  private int manhattan;

  public Board(int[][] blocks) {
    this(blocks, blocks.length);
  }

  private Board(int[][] blocks, int dimension) {
    if (blocks == null) {
      throw new IllegalArgumentException("blocks can't be null");
    }
    this.dimension = dimension;
    this.blocksCache = blocksCopy(blocks);
    calculateDistances(blocks);
  }

  public int[][] getBlocksCache() {
    return blocksCache;
  }

  private int[][] blocksCopy(int[][] sourceBlocks) {
    int[][] copy = new int[dimension][dimension];
    for (int i = 0; i < dimension; i++) {
      System.arraycopy(sourceBlocks[i], 0, copy[i], 0, dimension);
    }
    return copy;
  }

  private void calculateDistances(int[][] blocks) {
    int manhattanCalc = 0;
    int hammingCalc = 0;
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks[i].length; j++) {
        if (blocks[i][j] != 0 && blocks[i][j] != ((dimension * i) + j + 1)) {
          hammingCalc++;
        }
        if (blocks[i][j] == 0) {
          zeroI = i;
          zeroJ = j;
        }
        if (blocks[i][j] == 0) {
          continue;
        }
        int calcI = (blocks[i][j] - 1) / dimension;
        int calcJ = (blocks[i][j] - 1) % dimension;
        if (i != calcI || j != calcJ) {
          int distanceI = i - calcI;
          manhattanCalc += distanceI < 0 ? distanceI * -1 : distanceI;

          int distanceJ = j - calcJ;
          manhattanCalc += distanceJ < 0 ? distanceJ * -1 : distanceJ;
        }
      }
    }
    this.manhattan = manhattanCalc;
    this.hamming = hammingCalc;
  }

  public int dimension() {
    return dimension;
  }

  public int hamming() {
    return hamming;
  }

  public int manhattan() {
    return manhattan;
  }

  public boolean isGoal() {
    return hamming == 0;
  }

  public Board twin() {
    int swapToI;
    if (zeroI == 0) {
      swapToI = zeroI + 1;
    } else {
      swapToI = zeroI - 1;
    }

    int swapToJ;
    if (zeroJ == 0) {
      swapToJ = zeroJ + 1;
    } else {
      swapToJ = zeroJ - 1;
    }
    return neighborSwap(swapToI, zeroJ, swapToI, swapToJ);
  }

  public boolean equals(Object y) {
    if (y == null) {
      return false;
    }
    if (y == this) {
      return true;
    }
    if (y.getClass() != this.getClass()) {
      return false;
    }
    Board that = (Board) y;

    if (that.dimension != dimension || that.manhattan != manhattan || that.hamming != hamming) {
      return false;
    }

    for (int i = 0; i < blocksCache.length; i++) {
      for (int j = 0; j < blocksCache[i].length; j++) {
        if (blocksCache[i][j] != that.blocksCache[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  public Iterable<Board> neighbors() {
    List<Board> neighbors = new ArrayList<>();
    addNeighbor(neighborSwap(zeroI, zeroJ, zeroI - 1, zeroJ), neighbors);
    addNeighbor(neighborSwap(zeroI, zeroJ, zeroI + 1, zeroJ), neighbors);
    addNeighbor(neighborSwap(zeroI, zeroJ, zeroI, zeroJ - 1), neighbors);
    addNeighbor(neighborSwap(zeroI, zeroJ, zeroI, zeroJ + 1), neighbors);
    return neighbors;
  }

  private Board neighborSwap(int fromI, int fromJ, int toI, int toJ) {
    if (toI < 0
        || toI >= dimension
        || toJ < 0
        || toJ >= dimension
        || fromI < 0
        || fromI >= dimension
        || fromJ < 0
        || fromJ >= dimension) {
      return null;
    }

    int[][] blocks = blocksCopy(this.blocksCache);

    int tmp = blocks[fromI][fromJ];
    blocks[fromI][fromJ] = blocks[toI][toJ];
    blocks[toI][toJ] = tmp;
    return new Board(blocks, dimension);
  }

  private void addNeighbor(Board b, List<Board> neighbors) {
    if (b != null) {
      neighbors.add(b);
    }
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(dimension + "\n");
    for (int i = 0; i < blocksCache.length; i++) {
      for (int j = 0; j < blocksCache[i].length; j++) {
        sb.append(String.format(" %2d", blocksCache[i][j]));
      }
      sb.append("\n");
    }
    return sb.toString();
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
    blocks[n - 1][n - 1] = 3;
    Board initial2 = new Board(blocks);

    printBoard(initial);
    System.out.println(initial.equals(initial2));

    initial.neighbors().forEach(b -> printBoard(b));
  }

  private static void printBoard(Board b) {
    System.out.println(b);
    System.out.println("Dimmension: " + b.dimension);
    System.out.println("Hamming: " + b.hamming);
    System.out.println("Manhattan: " + b.manhattan);
    System.out.println("Goal: " + b.isGoal());
    System.out.println("\n\n");
  }
}
