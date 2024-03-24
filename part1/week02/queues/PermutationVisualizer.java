/*
  Program Description:
  --------------------
  PermutationVisualizer.java is a program that visualizes the process of enqueuing
  and dequeuing items from a randomized queue. It takes an integer k as a command-line
  argument, reads a sequence of strings from standard input using StdIn.readString(),
  and visually demonstrates the operations of enqueueing and dequeuing k items from
  the randomized queue.

  Implementation Details:
  -----------------------
  - The program utilizes the StdDraw library for visualization.
  - It creates a randomized queue and enqueues items while visualizing the process
    of each enqueue operation.
  - After enqueuing all items, it dequeues k items from the queue, visualizing each
    dequeue operation.
  - The visualization includes displaying the current state of the queue with filled
    squares representing items, where the selected item for dequeue is highlighted
    in light blue.

  Usage Example:
  --------------
  To use this program, run it with a command-line argument specifying the number of
  items to dequeue from the randomized queue. For example:

  java-algs4 PermutationVisualizer 3 < input.txt

  Note: PermutationVisualizer.java provides an interactive way to visualize the
  process of enqueuing and dequeuing items from a randomized queue, aiding in
  understanding the behavior of the data structure.
*/
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import java.util.ArrayList;
import java.util.List;

public class PermutationVisualizer {
  private static final double SQUARE_SIZE = 0.1;
  private static final double PADDING = 0.01;
  private static final int DELAY = 250;
  private static final int WAIT = 1500;

  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Usage: java PermutationVisualizer <k>");
      return;
    }

    RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
    int k = Integer.parseInt(args[0]);
    List<String> itemsList = new ArrayList<>();
    List<String> selectedList = new ArrayList<>();

    StdDraw.enableDoubleBuffering();

    System.out.println("Enqueue started.");
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      randomizedQueue.enqueue(item);
      itemsList.add(item);
      visualizeEnqueue(item, itemsList);
      System.out.println(item);
    }
    System.out.println("Enqueue completed.\n");

    StdDraw.pause(WAIT);
    System.out.println("Dequeue started.");
    for (int i = 0; i < k; i++) {
      String dequeuedItem = randomizedQueue.dequeue();
      itemsList.remove(dequeuedItem);
      selectedList.add(dequeuedItem);
      visualizeDequeue(dequeuedItem, itemsList, selectedList);
      System.out.println(dequeuedItem);
    }
    System.out.println("Dequeue completed.");
    System.out.println("\nAll visualization was completed.");
  }

  private static void visualizeEnqueue(String item, List<String> itemsList) {
    StdDraw.clear();

    StdDraw.text(0.5, 0.95, "Enqueue: " + item);

    double totalWidth = itemsList.size() * (SQUARE_SIZE + PADDING);
    double startX = (1 - totalWidth) / 2;

    double currentX = startX + SQUARE_SIZE / 2;
    for (String listItem : itemsList) {
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.filledSquare(currentX, 0.6, SQUARE_SIZE / 2);
      StdDraw.setPenColor(StdDraw.WHITE);
      StdDraw.filledSquare(currentX, 0.6, SQUARE_SIZE / 2 - PADDING / 2);
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.text(currentX, 0.6, listItem);
      currentX += SQUARE_SIZE + PADDING;
    }

    currentX = startX + SQUARE_SIZE / 2;
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.filledSquare(currentX, 0.4, SQUARE_SIZE / 2);
    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
    StdDraw.filledSquare(currentX, 0.4, SQUARE_SIZE / 2 - PADDING / 2);
    StdDraw.setPenColor(StdDraw.BLACK);

    StdDraw.show();
    StdDraw.pause(DELAY);
  }

  private static void visualizeDequeue(
      String item, List<String> itemsList, List<String> selectedList) {
    StdDraw.clear();

    StdDraw.text(0.5, 0.95, "Dequeue: " + item);

    double totalWidth = itemsList.size() * (SQUARE_SIZE + PADDING);
    double startX = (1 - totalWidth) / 2;

    double currentX = startX + SQUARE_SIZE / 2;
    for (String listItem : itemsList) {
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.filledSquare(currentX, 0.6, SQUARE_SIZE / 2);
      StdDraw.setPenColor(StdDraw.WHITE);
      StdDraw.filledSquare(currentX, 0.6, SQUARE_SIZE / 2 - PADDING / 2);
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.text(currentX, 0.6, listItem);
      currentX += SQUARE_SIZE + PADDING;
    }
    currentX = startX + SQUARE_SIZE / 2;
    for (String selectedItem : selectedList) {
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.filledSquare(currentX, 0.4, SQUARE_SIZE / 2);
      StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
      StdDraw.filledSquare(currentX, 0.4, SQUARE_SIZE / 2 - PADDING / 2);
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.text(currentX, 0.4, selectedItem);
      currentX += SQUARE_SIZE + PADDING;
    }
    StdDraw.show();
    StdDraw.pause(DELAY);
  }
}
