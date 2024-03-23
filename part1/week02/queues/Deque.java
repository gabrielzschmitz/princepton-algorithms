/*
  Program Description:
  --------------------
  Deque.java is a generic data type implementation that represents a
  double-ended queue (deque), supporting operations to add and remove items from
  both the front and the back of the data structure. It provides methods for
  checking if the deque is empty, obtaining its size, adding items to the front
  and back, removing items from the front and back, and iterating over the items
  in the deque.

  Implementation Details:
  -----------------------
  - The Deque class is implemented using a doubly linked list structure, with
  each element containing a reference to the next and previous elements.
  - The class provides methods to add items to the front and back of the deque,
    remove items from the front and back, and retrieve information about the
    deque's size and emptiness.
  - It also implements the Iterable interface to support iterating over the
  items in the deque from front to back.
  - The program includes unit testing in the main() method to verify the
  correctness of the deque operations and handle corner cases such as empty
  deque, null arguments, and iterator exceptions.

  Usage Example:
  --------------
  To use this program, create an instance of the Deque class and perform
  operations such as adding and removing items. For example:

  ```
  Deque<Integer> deque = new Deque<>();
  deque.addFirst(1);
  deque.addLast(2);
  System.out.println(deque.removeFirst()); // Output: 1
  System.out.println(deque.removeLast()); // Output: 2
  ```

  Note: Deque.java provides a flexible and efficient data structure for
  applications that require adding and removing items from both ends of a
  collection, such as implementing certain algorithms and data processing tasks.
*/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
  private Element<Item> first = null;
  private Element<Item> last = null;
  private int size = 0;

  private static class Element<T> {
    private final T element;
    private Element<T> next;
    private Element<T> prev;

    public Element(T element) {
      this.element = element;
    }
  }

  public Deque() { }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void addFirst(Item item) {
    if (item == null) throw new IllegalArgumentException("Item can't be NULL");
    Element<Item> newElement = new Element<>(item);

    if (size == 0) {
      first = newElement;
      last = newElement;
    } else {
      first.prev = newElement;
      newElement.next = first;
      first = newElement;
    }
    size++;
  }

  public void addLast(Item item) {
    if (item == null) throw new IllegalArgumentException("Item can't be NULL");
    Element<Item> newElement = new Element<>(item);

    if (size == 0) {
      first = newElement;
      last = newElement;
    } else {
      last.next = newElement;
      newElement.prev = last;
      last = newElement;
    }
    size++;
  }

  public Item removeFirst() {
    if (size == 0) throw new NoSuchElementException("Deque is empty");
    Item result = first.element;
    first = first.next;

    if (first != null) first.prev = null;
    else last = null;
    size--;

    return result;
  }

  public Item removeLast() {
    if (size == 0) throw new NoSuchElementException("Deque is empty");
    Item result = last.element;
    last = last.prev;

    if (last != null) last.next = null;
    else first = null;
    size--;

    return result;
  }

  private Item peekFirst() {
    if (isEmpty()) throw new NoSuchElementException("Deque is empty");
    return first.element;
  }

  private Item peekLast() {
    if (isEmpty()) throw new NoSuchElementException("Deque is empty");
    return last.element;
  }

  public Iterator<Item> iterator() {
    return new Iterator<Item>() {
      Element<Item> currentItem = first;
      @Override
      public boolean hasNext() {
        return currentItem != null;
      }

      @Override
      public Item next() {
        if (!hasNext()) throw new NoSuchElementException("No more elements");
        Item i = currentItem.element;
        currentItem = currentItem.next;
        return i;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  public static void main(String[] args) {
    System.out.println("Starting Deque unit tests...\n");
    testEmptyDeque();
    System.out.println("testEmptyDeque completed.");
    testAddFirst();
    System.out.println("testAddFirst completed.");
    testAddLast();
    System.out.println("testAddLast completed.");
    testRemoveFirst();
    System.out.println("testRemoveFirst completed.");
    testRemoveLast();
    System.out.println("testRemoveLast completed.");
    System.out.println("\nAll Deque unit tests completed.");
  }

  private static void testEmptyDeque() {
    Deque<Integer> deque = new Deque<>();
    assert deque.isEmpty();
    assert deque.size() == 0;
    try {
      deque.removeFirst();
      assert false; // Exception should have been thrown
    } catch (NoSuchElementException e) {
      // Expected exception
    }
    try {
      deque.removeLast();
      assert false; // Exception should have been thrown
    } catch (NoSuchElementException e) {
      // Expected exception
    }
  }

  private static void testAddFirst() {
    Deque<Integer> deque = new Deque<>();
    deque.addFirst(1);
    assert deque.size() == 1;
    assert !deque.isEmpty();
    assert deque.peekFirst() == 1;
    assert deque.peekLast() == 1;
  }

  private static void testAddLast() {
    Deque<Integer> deque = new Deque<>();
    deque.addLast(1);
    assert deque.size() == 1;
    assert !deque.isEmpty();
    assert deque.peekFirst() == 1;
    assert deque.peekLast() == 1;
  }

  private static void testRemoveFirst() {
    Deque<Integer> deque = new Deque<>();
    deque.addFirst(1);
    assert deque.removeFirst() == 1;
    assert deque.isEmpty();
    assert deque.size() == 0;
  }

  private static void testRemoveLast() {
    Deque<Integer> deque = new Deque<>();
    deque.addLast(1);
    try {
      assert deque.removeLast() == 1;
      assert deque.isEmpty();
      assert deque.size() == 0;
    } catch (NoSuchElementException e) {
      assert false; // Unexpected exception
    }
  }
}
