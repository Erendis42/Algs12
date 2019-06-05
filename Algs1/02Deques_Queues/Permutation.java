import edu.princeton.cs.algs4.StdIn;

public class Permutation {

  public static void main(String[] args) {
    int k = Integer.parseInt(args[0]);

    RandomizedQueue<String> rqs = new RandomizedQueue<String>();

    String str = StdIn.readString();
    while (!StdIn.isEmpty()) {
      rqs.enqueue(str);
      str = StdIn.readString();
    }

    for (String string : rqs) {
      if(k > 0) {
        System.out.println(string);
        k--;
      }
      else if (k == 0) {
        break;
      }
    }
  }
}
