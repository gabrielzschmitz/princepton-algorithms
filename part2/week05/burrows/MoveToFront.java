/*
  Program Description:
  --------------------
  MoveToFront.java implements the move-to-front encoding and decoding
  algorithms. Move-to-front encoding is a data compression technique that
  replaces frequently occurring characters with lower indices, thus allowing
  repeated characters to be represented with fewer bits. This encoding scheme is
  particularly effective for data with localized entropy.

  Features:
  ---------
  - Implements move-to-front encoding and decoding algorithms
  - Uses a character array to maintain the order of characters based on their
    frequency of occurrence
  - Reads input from BinaryStdIn and writes output to BinaryStdOut
  - Supports encoding and decoding operations from command-line arguments

  Class Overview:
  ---------------
  - MoveToFront: Main class for implementing move-to-front encoding and decoding
    - encode: Encodes the input data using move-to-front encoding
    - decode: Decodes the input data using move-to-front decoding
    - main: Entry point for running move-to-front encoding or decoding

  Usage Instructions:
  -------------------
  1. Compile the program using the command: javac-args4 MoveToFront.java
  2. To encode a file, run the program with "-" as the command-line argument
     followed by the file name, and redirect the input from the file:
       java-args4 MoveToFront - < input.txt > encoded.txt
  3. To decode a file, run the program with "+" as the command-line argument
     followed by the file name, and redirect the input from the file:
       java-args4 MoveToFront + < encoded.txt > decoded.txt

  Note:
  -----
  - The MoveToFront class depends on the BinaryStdIn and BinaryStdOut classes
    from the edu.princeton.cs.algs4 package for reading input and writing
    output.
*/
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
  private static final int R = 256;

  public static void encode() {
    char[] arr = new char[R];
    for (char i = 0; i < R; i++) {
      arr[i] = i;
    }
    int i;
    while (!BinaryStdIn.isEmpty()) {
      char c = BinaryStdIn.readChar();
      for (i = 0; i < R; i++) {
        if (c == arr[i]) {
          break;
        }
      }
      BinaryStdOut.write((char) i);
      for (int j = i; j > 0; j--) {
        arr[j] = arr[j - 1];
      }
      arr[0] = c;
    }
    BinaryStdOut.close();
  }

  public static void decode() {
    char[] arr = new char[R];
    for (char i = 0; i < R; i++) {
      arr[i] = i;
    }
    while (!BinaryStdIn.isEmpty()) {
      int x = BinaryStdIn.readChar();
      char c = arr[x];
      BinaryStdOut.write(c);
      for (int j = x; j > 0; j--) {
        arr[j] = arr[j - 1];
      }
      arr[0] = c;
    }
    BinaryStdOut.close();
  }

  public static void main(String[] args) {
    if (args[0].equals("-")) encode();
    else if (args[0].equals("+")) decode();
    else throw new IllegalArgumentException("Illegal command line argument");
  }
}
