/*
  Program Description:
  --------------------
  BurrowsWheeler.java implements the Burrows-Wheeler transform, which is a
  reversible transformation on a string that improves its entropy, making it
  more amenable for compression. This class provides methods for both
  transforming a string using the Burrows-Wheeler transform and reversing the
  transformation to obtain the original string.

  Features:
  ---------
  - Supports the Burrows-Wheeler transform and its inverse
  - Efficiently transforms and reverses strings using CircularSuffixArray
  - Handles both binary and text input/output using BinaryStdIn and BinaryStdOut

  Class Overview:
  ---------------
  - BurrowsWheeler: Main class implementing the Burrows-Wheeler transform
    functionality
    - transform: Performs the Burrows-Wheeler transform on the input string
    - inverseTransform: Reverses the Burrows-Wheeler transform to obtain the
      original string
    - main: Entry point for running the Burrows-Wheeler transform from the
      command line

  Dependencies:
  -------------
  - The BurrowsWheeler class depends on the CircularSuffixArray class for
    generating circular suffix arrays, which is essential for performing the
    Burrows-Wheeler transform.

  Usage Instructions:
  -------------------
  1. Compile the program using the command: javac-args4-args4 BurrowsWheeler.java
  2. To transform a string, run the command:
       java-args4 BurrowsWheeler - < input.txt > output.bwt
     where "input.txt" contains the string to be transformed.
  3. To reverse the transformation and obtain the original string, run the
     command:
       java-args4 BurrowsWheeler + < output.bwt > original.txt
     where "output.bwt" contains the transformed string.

  Note:
  -----
  - The input string for transformation must be provided through standard
    input (stdin), and the transformed string will be output to standard output
    (stdout).
*/
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
  private static final int R = 256;

  public static void transform() {
    String s = BinaryStdIn.readString();
    CircularSuffixArray suffixArray = new CircularSuffixArray(s);
    int n = suffixArray.length();
    for (int i = 0; i < n; i++) {
      if (suffixArray.index(i) == 0) {
        BinaryStdOut.write(i);
      }
    }
    for (int i = 0; i < n; i++) {
      int j = suffixArray.index(i);
      if (j - 1 >= 0) {
        BinaryStdOut.write(s.charAt(j - 1));
      } else {
        BinaryStdOut.write(s.charAt(n - 1));
      }
    }
    BinaryStdOut.close();
  }

  public static void inverseTransform() {
    int first = BinaryStdIn.readInt();
    String st = BinaryStdIn.readString();
    char[] chars = st.toCharArray();
    int n = chars.length;

    int[] count = new int[R + 1];
    int[] next = new int[n];
    for (char c : chars) {
      count[c + 1]++;
    }
    for (int i = 0; i < R; i++) {
      count[i + 1] += count[i];
    }
    for (int i = 0; i < n; i++) {
      next[count[chars[i]]++] = i;
    }
    int index = next[first];
    for (int i = 0; i < n; i++, index = next[index]) {
      BinaryStdOut.write(chars[index]);
    }
    BinaryStdOut.close();
  }

  public static void main(String[] args) {
    if (args[0].equals("-")) transform();
    else if (args[0].equals("+")) inverseTransform();
    else throw new IllegalArgumentException("Illegal command line argument");
  }
}
