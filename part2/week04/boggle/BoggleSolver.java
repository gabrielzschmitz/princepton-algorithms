/*
  Program Description:
  --------------------
  BoggleSolver.java implements a solver for finding valid words in a Boggle
  board. It utilizes a trie data structure to efficiently search for words from
  a given dictionary in the Boggle board. The solver provides methods to find
  all valid words in the board, calculate the score for a given word, and
  determine if a word exists in the dictionary.

  Features:
  ---------
  - Efficiently finds all valid words in a Boggle board
  - Utilizes a trie data structure for dictionary lookup
  - Supports scoring of words based on Boggle rules
  - Provides methods for checking word existence in the dictionary
  - Capable of handling custom dictionaries and Boggle boards

  Class Overview:
  ---------------
  - BoggleSolver: Main class implementing the Boggle solver functionality
    - Constructor: Initializes the solver with a given dictionary
    - getAllValidWords: Finds all valid words in the Boggle board
    - scoreOf: Calculates the score for a given word
    - contains: Checks if a word exists in the dictionary
    - main: Entry point for running the Boggle solver from the command line

  Dependencies:
  -------------
  - The BoggleSolver class depends on the BoggleBoard class from the same
  package for accessing Boggle board data and methods.

  Usage Instructions:
  -------------------
  1. Compile the program using the command: javac-algs4 BoggleSolver.java
  2. Run the solver using the command:
         java-algs4 BoggleSolver dictionary.txt board.txt
     where "dictionary.txt" contains the dictionary words and "board.txt"
     contains the Boggle board configuration.
  3. The solver will output all valid words found in the board and their
     respective scores based on Boggle rules.

  Note:
  -----
  - Ensure that the dictionary file contains one word per line, and the board file
    follows the format specified in the BoggleBoard class.
*/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {

  private final Node root;
  private final int[] rowLabel = {-1, 0, 1, -1, 1, -1, 0, 1};
  private final int[] colLabel = {-1, -1, -1, 0, 0, 1, 1, 1};

  private static class Node {
    Node[] next = new Node[26];
    int value = -1;
  }

  public BoggleSolver(String[] dictionary) {
    if (dictionary == null) throw new IllegalArgumentException("argument is null");
    root = new Node();

    for (String s : dictionary) createNode(s);
  }

  private void createNode(String s) {
    Node x = root;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (x.next[c - 'A'] == null) x.next[c - 'A'] = new Node();
      x = x.next[c - 'A'];
    }
    x.value = s.length();
  }

  public Iterable<String> getAllValidWords(BoggleBoard board) {
    if (board == null) throw new IllegalArgumentException("argument is null");
    int rows = board.rows();
    int cols = board.cols();
    SET<String> validWords = new SET<>();
    boolean[][] visited = new boolean[rows][cols];

    for (int i = 0; i < rows; i++)
      for (int j = 0; j < cols; j++) dfs(board, root, i, j, "", validWords, visited);
    return validWords;
  }

  private void dfs(
      BoggleBoard board,
      Node x,
      int i,
      int j,
      String word,
      SET<String> validWords,
      boolean[][] visited) {
    char c = board.getLetter(i, j);
    if (x == null || x.next[c - 'A'] == null) {
      return;
    }
    String s;
    if (c == 'Q') {
      s = word + "QU";
      x = x.next['Q' - 'A'];
      if (x.next['U' - 'A'] == null) return;
      x = x.next['U' - 'A'];
    } else {
      s = word + c;
      x = x.next[c - 'A'];
    }
    if (s.length() >= 3 && x.value != -1) validWords.add(s);
    visited[i][j] = true;
    int m = board.rows();
    int n = board.cols();
    for (int p = 0; p < rowLabel.length; p++) {
      int xi = i + rowLabel[p];
      int yj = j + colLabel[p];
      if (xi < 0 || xi >= m || yj < 0 || yj >= n || visited[xi][yj]) continue;
      dfs(board, x, xi, yj, s, validWords, visited);
    }
    visited[i][j] = false;
  }

  public int scoreOf(String word) {
    if (word == null) return 0;
    if (contains(word)) {
      int length = word.length();
      if (length <= 2) return 0;
      if (length == 3 || length == 4) return 1;
      if (length == 5) return 2;
      if (length == 6) return 3;
      if (length == 7) return 5;
      return 11;
    }
    return 0;
  }

  private boolean contains(String s) {
    Node x = root;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (x.next[c - 'A'] == null) return false;
      x = x.next[c - 'A'];
    }
    return x.value != -1;
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard(args[1]);
    int score = 0;
    for (String word : solver.getAllValidWords(board)) {
      StdOut.println(word);
      score += solver.scoreOf(word);
    }
    StdOut.println("Score = " + score);
  }
}
