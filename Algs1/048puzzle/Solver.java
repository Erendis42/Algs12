import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class Solver {
  Board initial;
  MinPQ<Board> steps;
  
  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    this.initial = initial;
    steps = new MinPQ<Board>();
    steps.insert(initial);
    generateSteps(initial, initial);
  }

  private void generateSteps(Board parent, Board current) {
    System.out.println("parent:\t\t");
    System.out.println(parent);
    System.out.println("current:\t\t");
    System.out.println(current);
    for (Board n : current.neighbors()) {
      System.out.println("n:\t\t\t");
      System.out.println(n);
      if(!n.equals(parent) && !n.equals(current)) {
        steps.insert(n);
        if(n.manhattan() != 0) {
          generateSteps(current, n);
        }
      }
    }
  }
  
  private int[][] deepCopy(int[][] blocks) {
    return java.util.Arrays.stream(blocks).map(el -> el.clone()).toArray($ -> blocks.clone());
  }

  // is the initial board solvable?
  public boolean isSolvable() {
    return false;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    return 0;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    Queue<Board> solution = new Queue<Board>();
    
    Board b = steps.delMin();
    solution.enqueue(b);    
    
    while (b != initial) {
      b = steps.delMin();
      solution.enqueue(b); 
    }
    
    return null;
  }

  // solve a slider puzzle (given below)
  public static void main(String[] args) {
    
    Board b = new Board(new int[][] {{1, 0}, {3,2}});
    
    //Board b = new Board(new int[][] {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
    Board twin = b.twin();

    Solver s = new Solver(b);
    
    for (Board b : s.solution()) {
      System.out.println(b);
    }
    
    return;
  }
}
