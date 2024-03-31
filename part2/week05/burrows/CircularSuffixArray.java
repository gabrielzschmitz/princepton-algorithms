/*
  Program Description:
  --------------------
  CircularSuffixArray.java generates a circular suffix array of a given string.
  A circular suffix array is an array of integers that represents the
  lexicographic order of the circular suffixes of a string. It uses the
  Burrows-Wheeler transform to efficiently compute the circular suffix array.

  Features:
  ---------
  - Generates a circular suffix array for a given string
  - Uses the Burrows-Wheeler transform to compute the suffix array efficiently
  - Provides methods to access the length of the array and the index of a
    specified circular suffix

  Class Overview:
  ---------------
  - CircularSuffixArray: Main class for generating the circular suffix array
    - CircularSuffix: Inner class representing a circular suffix
      - compareTo: Compares two circular suffixes based on their lexicographic
        order
    - Constructor: Computes the circular suffix array for the given string
    - length: Returns the length of the circular suffix array
    - index: Returns the index of the ith circular suffix in the array
    - main: Entry point for running the circular suffix array generation

  Usage Instructions:
  -------------------
  1. Compile the program using the command: javac-args4 CircularSuffixArray.java
  2. Run the program using the command:
       java-args4 CircularSuffixArray
  3. The program will output the length of the circular suffix array followed
     by the indices of the circular suffixes in lexicographic order.

  Note:
  -----
  - The CircularSuffixArray class depends on the StdOut class from
    edu.princeton.cs.algs4 package for printing output.
*/
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class CircularSuffixArray {

  private final int n;
  private final char[] str;
  private final int[] indices;

  private class CircularSuffix implements Comparable<CircularSuffix> {
    int index;

    public CircularSuffix(int index) {
      this.index = index;
    }

    @Override
    public int compareTo(CircularSuffix that) {
      for (int i = 0; i < n; i++) {
        if (str[(this.index + i) % n] < str[(that.index + i) % n]) {
          return -1;
        }
        if (str[(this.index + i) % n] > str[(that.index + i) % n]) {
          return 1;
        }
      }
      return 0;
    }
  }

  public CircularSuffixArray(String s) {
    if (s == null) {
      throw new IllegalArgumentException("argument is null");
    }
    n = s.length();
    str = s.toCharArray();
    indices = new int[n];

    CircularSuffix[] suffixes = new CircularSuffix[n];
    for (int i = 0; i < n; i++) {
      suffixes[i] = new CircularSuffix(i);
    }
    Arrays.sort(suffixes);

    for (int i = 0; i < n; i++) {
      indices[i] = suffixes[i].index;
    }
  }

  public int length() {
    return n;
  }

  public int index(int i) {
    if (i < 0 || i > n - 1) {
      throw new IllegalArgumentException("invalid index");
    }
    return indices[i];
  }

  public static void main(String[] args) {
    CircularSuffixArray array = new CircularSuffixArray("abcdefghijklmnopqrstuvwxyz0123456789");
    int n = array.length();
    StdOut.println(n);
    for (int i = 0; i < n; i++) {
      StdOut.println(array.index(i));
    }
  }
}
