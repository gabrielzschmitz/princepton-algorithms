/*
  Program Description:
  --------------------
  RandomizedQueue.java is a generic data type implementation that represents
  a randomized queue, similar to a stack or queue, where the item removed is
  chosen uniformly at random among items in the data structure. It provides
  methods for adding items, removing a random item, obtaining the number of
  items, checking if the queue is empty, and obtaining a random item without
  removing it.

  Implementation Details:
  -----------------------
  - RandomizedQueue class is implemented using an array-based data structure.
  - It supports adding items to the queue, removing a random item, and
    obtaining a random item without removing it.
  - Each iterator returns items in a uniformly random order, maintaining its
    own random order independently from other iterators.
  - The program includes unit testing in main() method to verify correctness
    and handle corner cases such as empty queue and null arguments.

  Usage Example:
  --------------
  To use this program, create an instance of RandomizedQueue class and perform
  operations such as adding items, removing a random item, or obtaining the
  number of items. For example:

  RandomizedQueue<Integer> queue = new RandomizedQueue<>();
  queue.enqueue(1);
  queue.enqueue(2);
  System.out.println(queue.dequeue()); // Output: Random item

  Note: RandomizedQueue.java provides a flexible and efficient data structure
  for applications needing a randomized queue, such as certain algorithms and
  simulations.
*/
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private int n = 1;
  private Item[] items;
  private int size = 0;

  public RandomizedQueue() {
    items = (Item[]) new Object[n];
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void enqueue(Item item) {
    if (item == null) throw new IllegalArgumentException("Item can't be null");
    if (size == n) {
      resizeArray(n * 2);
    }
    items[size++] = item;
  }

  public Item dequeue() {
    if (size == 0) throw new NoSuchElementException("Queue is empty");
    int randomIndex = StdRandom.uniformInt(size);
    Item randomItem = items[randomIndex];
    items[randomIndex] = items[--size];
    items[size] = null;
    return randomItem;
  }

  public Item sample() {
    if (size == 0) throw new NoSuchElementException("Queue is empty");
    return items[StdRandom.uniformInt(size)];
  }

  public Iterator<Item> iterator() {
    return new QueueIterator();
  }

  private void resizeArray(int newSize) {
    n = newSize;
    Item[] newArray = (Item[]) new Object[n];
    for (int i = 0; i < items.length; i++) {
      newArray[i] = items[i];
    }
    items = newArray;
  }

  private class QueueIterator implements Iterator<Item> {
    private int currentIndex = 0;
    private final Item[] iteratorItems;

    public QueueIterator() {
      iteratorItems = (Item[]) new Object[size];
      for (int i = 0; i < size; i++) {
        iteratorItems[i] = items[i];
      }
      StdRandom.shuffle(iteratorItems);
    }

    @Override
    public boolean hasNext() {
      return currentIndex < iteratorItems.length;
    }

    @Override
    public Item next() {
      if (!hasNext()) throw new NoSuchElementException("No more elements");
      return iteratorItems[currentIndex++];
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  public static void main(String[] args) {
    System.out.println("Starting RandomizedQueue unit tests...\n");
    testEmptyQueue();
    System.out.println("testEmptyQueue completed.");
    testEnqueue();
    System.out.println("testEnqueue completed.");
    testDequeue();
    System.out.println("testDequeue completed.");
    testSample();
    System.out.println("testSample completed.");
    System.out.println("\nAll RandomizedQueue unit tests completed.");
  }

  private static void testEmptyQueue() {
    RandomizedQueue<Integer> queue = new RandomizedQueue<>();
    assert queue.isEmpty();
    assert queue.size() == 0;
    try {
      queue.dequeue();
      assert false;
    } catch (NoSuchElementException e) {
      // Expected exception
    }
    try {
      queue.sample();
      assert false;
    } catch (NoSuchElementException e) {
      // Expected exception
    }
  }

  private static void testEnqueue() {
    RandomizedQueue<Integer> queue = new RandomizedQueue<>();
    queue.enqueue(1);
    assert queue.size() == 1;
    assert !queue.isEmpty();
    assert queue.sample() == 1;
  }

  private static void testDequeue() {
    RandomizedQueue<Integer> queue = new RandomizedQueue<>();
    queue.enqueue(1);
    assert queue.dequeue() == 1;
    assert queue.isEmpty();
    assert queue.size() == 0;
  }

  private static void testSample() {
    RandomizedQueue<Integer> queue = new RandomizedQueue<>();
    queue.enqueue(1);
    assert queue.sample() == 1;
    assert queue.size() == 1;
    assert !queue.isEmpty();
  }
}
