import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private Item[] arrayOfItems;
  private int numberOfItems = 0;

  // construct an empty randomized queue
  public RandomizedQueue() {
    arrayOfItems = (Item[]) new Object[1];
  }

  // is the randomized queue empty?
  public boolean isEmpty() {
    return numberOfItems == 0;
  }

  // return the number of items on the randomized queue
  public int size() {
    return numberOfItems;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (numberOfItems == arrayOfItems.length)
      resize(2 * arrayOfItems.length);
    arrayOfItems[numberOfItems++] = item;
  }

  // remove and return a random item
  public Item dequeue() {
    if(numberOfItems == 0) {
      throw new NoSuchElementException();
    }

    int r = StdRandom.uniform(numberOfItems);
    Item item = arrayOfItems[r]; // remove a random item
    arrayOfItems[r] = arrayOfItems[numberOfItems - 1]; // overwrite random item with last item of
                                                       // the array
    arrayOfItems[numberOfItems - 1] = null; // free up unused memory
    numberOfItems--;

    if (numberOfItems > 0 && numberOfItems == arrayOfItems.length / 4)
      resize(arrayOfItems.length / 2);

    return item;
  }

  private void resize(int capacity) {
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < numberOfItems; i++) {
      copy[i] = arrayOfItems[i];
    }
    arrayOfItems = copy;
  }

  // return a random item (but do not remove it)
  public Item sample() {
    if(numberOfItems == 0) {
      throw new NoSuchElementException();
    }
    
    int r = StdRandom.uniform(numberOfItems);
    Item item = arrayOfItems[r];

    return item;
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new RandomIterator();
  }

  private class RandomIterator implements Iterator<Item> {
    int counter = numberOfItems - 1;

    int[] indices = StdRandom.permutation(numberOfItems);

    public boolean hasNext() {
      return counter >= 0;
    }

    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Item nextItem = arrayOfItems[indices[counter--]];
      return nextItem;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  // unit testing (optional)
  public static void main(String[] args) {

    RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

    rq.enqueue(1);
    rq.enqueue(2);
    rq.enqueue(3);
    rq.enqueue(4);
    rq.enqueue(5);

    System.out.println(rq.sample());
    System.out.println(rq.sample());

    /*
     * System.out.println(rq.dequeue()); System.out.println(rq.dequeue());
     * System.out.println(rq.dequeue()); System.out.println(rq.dequeue());
     * System.out.println(rq.dequeue());
     */

    /*
     * for (Integer i : rq) { System.out.println(i); }
     */
  }
}
