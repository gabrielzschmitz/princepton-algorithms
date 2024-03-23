/*
  Program Description:
  --------------------
  Permutation.java is a client program that takes an integer k as a command-line
  argument, reads a sequence of strings from standard input using StdIn.readString(),
  and prints exactly k of them, uniformly at random. Each item from the sequence is
  printed at most once.

  Implementation Details:
  -----------------------
  - The program reads a sequence of strings from standard input and stores them in
    a randomized queue.
  - It then dequeues and prints exactly k strings from the randomized queue, ensuring
    uniform randomness.
  - The program implements the specified API, ensuring that the running time is
    linear in the size of the input.

  Usage Example:
  --------------
  To use this program, run it with a command-line argument specifying the number of
  strings to print randomly from the input sequence. For example:

  java Permutation 3 < input.txt

  Note: Permutation.java provides a flexible and efficient way to randomly select
  a subset of strings from an input sequence, with linear running time and minimal
  memory usage.
*/
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Usage: java Permutation <k>");
      return;
    }

    RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
    int k = Integer.parseInt(args[0]);

    while (!StdIn.isEmpty()) randomizedQueue.enqueue(StdIn.readString());
    for (int i = 0; i < k; i++) System.out.println(randomizedQueue.dequeue());
  }
}
