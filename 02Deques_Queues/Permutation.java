import edu.princeton.cs.algs4.StdIn;

public class Permutation {

  public static void main(String[] args) {
    int k = Integer.parseInt(args[0]);
    // int k = 3;

    RandomizedQueue<String> rqs = new RandomizedQueue<String>();

    String str = StdIn.readString();

    // In in = new In("src/test-data/distinct.txt");
    // String str = in.readLine();
    for (String s : str.split("\\s+")) {
      rqs.enqueue(s);
    }

    for (String string : rqs) {
      System.out.print(string);
      k--;
      if (k == 0) {
        break;
      }
    }
  }

}
