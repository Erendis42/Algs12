import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
  private int size;
  private Node first;

  private class Node {
    Item item;
    Node next;
  }

  // construct an empty deque
  public Deque() {
    size = 0;
    first = null;
  }

  // is the deque empty?
  public boolean isEmpty() {
    return size == 0;
  }

  // return the number of items on the deque
  public int size() {
    return size;
  }

  // add the item to the front
  public void addFirst(Item item) {

    checkIfArgumentIsNull(item);

    if (size == 0) {
      first = new Node();
      first.item = item;
      first.next = null;
    } else {
      Node newNode = new Node();
      newNode.item = item;
      newNode.next = first;
      first = newNode;
    }

    size++;
  }

  // add the item to the end
  public void addLast(Item item) {
    checkIfArgumentIsNull(item);

    Node newNode = new Node();
    newNode.item = item;
    newNode.next = null;

    if (isEmpty()) {
      first = newNode;
    } else {
      Node last = first;
      while (last.next != null) {
        last = last.next;
      }

      last.next = newNode;
    }

    size++;
  }

  private void checkIfArgumentIsNull(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
  }


  // remove and return the item from the front
  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    Item item = first.item;
    first = first.next;

    size--;

    return item;
  }


  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    if (size == 1) {
      return removeFirst();
    } else {
      Node p = first;

      // get to the penultimate element
      for (int i = 0; i < size - 2; i++) {
        p = p.next;
      }

      // store last element
      Node last = p.next;

      // make penultimate node the new last node
      p.next = null;

      size--;

      return last.item;
    }
  }

  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {
    private Node current = first;

    public boolean hasNext() {
      return current != null;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      if (current == null) {
        throw new NoSuchElementException();
      }
      Item item = current.item;
      current = current.next;
      return item;
    }
  }

  public static void main(String[] args) {

    Deque<String> d = new Deque<String>();

    /*
     * d.addFirst("1st item"); d.addFirst("2nd item"); d.addFirst("3rd item");
     * d.addFirst("4th item"); d.addFirst("5th item"); d.addFirst("6th item");
     */

    // /*
    d.addLast("1st item");
    d.addLast("2nd item");
    d.addLast("3rd item");
    d.addLast("4th item");
    d.addLast("5th item");
    d.addLast("6th item");
    // */

    /*
     * d.addFirst("1st item"); d.addLast("2nd item"); d.addFirst("3rd item"); d.addLast("4th item");
     * d.addFirst("5th item"); d.addLast("6th item");
     */

    /*
     * System.out.println(d.removeFirst()); System.out.println(d.removeFirst());
     * System.out.println(d.removeFirst()); System.out.println(d.removeFirst());
     * System.out.println(d.removeFirst()); System.out.println(d.removeFirst());
     */

    /*
     * System.out.println(d.removeLast()); System.out.println(d.removeLast());
     * System.out.println(d.removeLast()); System.out.println(d.removeLast());
     * System.out.println(d.removeLast()); System.out.println(d.removeLast());
     */

    /*
     * System.out.println(d.removeLast()); System.out.println(d.removeFirst());
     * System.out.println(d.removeLast()); System.out.println(d.removeFirst());
     * System.out.println(d.removeLast()); System.out.println(d.removeFirst());
     */

    for (String string : d) {
      System.out.println(string);
    }
  }
}
