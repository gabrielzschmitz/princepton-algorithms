/*
  Program Description:
  --------------------
  This Java program, named RandomWord.java, reads a sequence of words from
  standard input and prints one of those words uniformly at random. It utilizes
  Knuth’s method to achieve this, selecting a word with probability 1/i to be
  the champion as each word is read, replacing the previous champion.
  After reading all words, it prints the surviving champion.

  Implementation Details:
  -----------------------
  - Imports necessary classes from the algs4.jar library, including StdIn,
    StdOut, and StdRandom.
  - The main method serves as the entry point of the program.
  - Initializes variables champion and count to keep track of the current
    champion word and the total number of words read.
  - Reads words from standard input using StdIn.readString() and counts the
    number of words read.
  - Selects the current word as the champion with probability 1/count using
    StdRandom.bernoulli(1.0 / count).
  - Prints the surviving champion using StdOut.println(champion).

  Note: This program efficiently selects a random word without storing the
  words in an array or list, making it suitable for scenarios with large input
  streams or memory constraints.
*/
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
  public static void main(String[] args) {
    String champion = "";
    int count = 0;

    while (!StdIn.isEmpty()) {
      String word = StdIn.readString();
      count++;

      if (StdRandom.bernoulli(1.0 / count)) champion = word;
    }

    StdOut.println(champion);
  }
}
