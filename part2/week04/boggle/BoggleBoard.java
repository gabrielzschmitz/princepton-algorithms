/*
  Program Description:
  --------------------
  BoggleBoard.java defines a data type to represent Boggle boards. A Boggle
  board is a 2D grid of characters typically used for playing the game
  Boggle. The class provides methods to initialize Boggle boards using
  various methods and to retrieve information about the board.

  Features:
  ---------
  - Supports initialization of Boggle boards using different techniques:
    - Random 4x4 board based on Hasbro dice
    - Random boards based on English letter frequencies
    - Custom boards from a 2D character array
    - Boards loaded from a file
  - Provides methods to retrieve information about the board:
    - Number of rows and columns
    - Access to individual letters on the board
    - String representation of the board

  Usage Instructions:
  -------------------
  1. Compile the program using the command: javac-algs4 BoggleBoard.java
  2. Execute the compiled program using the command: java-algs4 BoggleBoard

  Dependencies:
  -------------
  - The BoggleBoard class depends on the StdRandom, In, and StdOut classes
    from the algs4 library for generating random numbers, reading input, and
    printing output.

  Example Usage:
  --------------
  - The main method contains examples demonstrating the creation of Boggle
    boards using different techniques and printing their representations.

  Notes:
  ------
  - The BoggleBoard class provides flexibility in initializing Boggle boards
    for various purposes, including testing algorithms and generating custom
    game setups.
*/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class BoggleBoard {
  // the 16 Boggle dice (1992 version)
  private static final String[] BOGGLE_1992 = {
    "LRYTTE", "VTHRWE", "EGHWNE", "SEOTIS",
    "ANAEEG", "IDSYTT", "OATTOW", "MTOICU",
    "AFPKFS", "XLDERI", "HCPOAS", "ENSIEU",
    "YLDEVR", "ZNRNHL", "NMIQHU", "OBBAOJ"
  };

  // the 16 Boggle dice (1983 version)
  private static final String[] BOGGLE_1983 = {
    "AACIOT", "ABILTY", "ABJMOQ", "ACDEMP",
    "ACELRS", "ADENVZ", "AHMORS", "BIFORX",
    "DENOSW", "DKNOTU", "EEFHIY", "EGINTV",
    "EGKLUY", "EHINPS", "ELPSTU", "GILRUW",
  };

  // the 25 Boggle Master / Boggle Deluxe dice
  private static final String[] BOGGLE_MASTER = {
    "AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM",
    "AEEGMU", "AEGMNN", "AFIRSY", "BJKQXZ", "CCNSTW",
    "CEIILT", "CEILPT", "CEIPST", "DDLNOR", "DHHLOR",
    "DHHNOT", "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU",
    "FIPRSY", "GORRVW", "HIPRRY", "NOOTUW", "OOOTTU"
  };

  // the 25 Big Boggle dice
  private static final String[] BOGGLE_BIG = {
    "AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM",
    "AEEGMU", "AEGMNN", "AFIRSY", "BJKQXZ", "CCENST",
    "CEIILT", "CEILPT", "CEIPST", "DDHNOT", "DHHLOR",
    "DHLNOR", "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU",
    "FIPRSY", "GORRVW", "IPRRRY", "NOOTUW", "OOOTTU"
  };

  // letters and frequencies of letters in the English alphabet
  private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final double[] FREQUENCIES = {
    0.08167, 0.01492, 0.02782, 0.04253, 0.12703, 0.02228,
    0.02015, 0.06094, 0.06966, 0.00153, 0.00772, 0.04025,
    0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987,
    0.06327, 0.09056, 0.02758, 0.00978, 0.02360, 0.00150,
    0.01974, 0.00074
  };

  private final int m;
  private final int n;
  private char[][] board;

  public BoggleBoard() {
    m = 4;
    n = 4;
    StdRandom.shuffle(BOGGLE_1992);
    board = new char[m][n];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        String letters = BOGGLE_1992[n * i + j];
        int r = StdRandom.uniformInt(letters.length());
        board[i][j] = letters.charAt(r);
      }
    }
  }

  public BoggleBoard(String filename) {
    In in = new In(filename);
    m = in.readInt();
    n = in.readInt();
    if (m <= 0) throw new IllegalArgumentException("number of rows must be a positive integer");
    if (n <= 0) throw new IllegalArgumentException("number of columns must be a positive integer");
    board = new char[m][n];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        String letter = in.readString().toUpperCase();
        if (letter.equals("QU")) board[i][j] = 'Q';
        else if (letter.length() != 1)
          throw new IllegalArgumentException("invalid character: " + letter);
        else if (!ALPHABET.contains(letter))
          throw new IllegalArgumentException("invalid character: " + letter);
        else board[i][j] = letter.charAt(0);
      }
    }
  }

  public BoggleBoard(int m, int n) {
    this.m = m;
    this.n = n;
    if (m <= 0) throw new IllegalArgumentException("number of rows must be a positive integer");
    if (n <= 0) throw new IllegalArgumentException("number of columns must be a positive integer");
    board = new char[m][n];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        int r = StdRandom.discrete(FREQUENCIES);
        board[i][j] = ALPHABET.charAt(r);
      }
    }
  }

  public BoggleBoard(char[][] a) {
    this.m = a.length;
    if (m == 0) throw new IllegalArgumentException("number of rows must be a positive integer");
    this.n = a[0].length;
    if (n == 0) throw new IllegalArgumentException("number of columns must be a positive integer");
    board = new char[m][n];
    for (int i = 0; i < m; i++) {
      if (a[i].length != n) throw new IllegalArgumentException("char[][] array is ragged");
      for (int j = 0; j < n; j++) {
        if (ALPHABET.indexOf(a[i][j]) == -1)
          throw new IllegalArgumentException("invalid character: " + a[i][j]);
        board[i][j] = a[i][j];
      }
    }
  }

  public int rows() {
    return m;
  }

  public int cols() {
    return n;
  }

  public char getLetter(int i, int j) {
    return board[i][j];
  }

  public String toString() {
    StringBuilder sb = new StringBuilder(m + " " + n + "\n");
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        sb.append(board[i][j]);
        if (board[i][j] == 'Q') sb.append("u ");
        else sb.append("  ");
      }
      sb.append("\n");
    }
    return sb.toString().trim();
  }

  public static void main(String[] args) {
    StdOut.println("Hasbro board:");
    BoggleBoard board1 = new BoggleBoard();
    StdOut.println(board1);
    StdOut.println();

    StdOut.println("Random 4-by-4 board:");
    BoggleBoard board2 = new BoggleBoard(4, 4);
    StdOut.println(board2);
    StdOut.println();

    StdOut.println("4-by-4 board from 2D character array:");
    char[][] a = {
      {'D', 'O', 'T', 'Y'},
      {'T', 'R', 'S', 'F'},
      {'M', 'X', 'M', 'O'},
      {'Z', 'A', 'B', 'W'}
    };
    BoggleBoard board3 = new BoggleBoard(a);
    StdOut.println(board3);
    StdOut.println();

    String filename = "tests/board-quinquevalencies.txt";
    StdOut.println("4-by-4 board from file " + filename + ":");
    BoggleBoard board4 = new BoggleBoard(filename);
    StdOut.println(board4);
    StdOut.println();
  }
}
