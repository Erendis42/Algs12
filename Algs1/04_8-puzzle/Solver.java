import java.util.LinkedList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
  private boolean isSolvable;

  private class SearchNode implements Comparable<SearchNode> {
    private Board board;
    private int moves;
    private int priority;
    private SearchNode prev;

    public SearchNode(Board board, int moves, SearchNode prev) {
      this.board = board;
      this.moves = moves;
      priority = board.manhattan() + this.moves;
      isSolvable = false;
    }

    private int getMoves() {
      return moves;
    }

    private int getPriority() {
      return priority;
    }

    private SearchNode getPrev() {
      return prev;
    }

    @Override
    public int compareTo(SearchNode that) {
      return Integer.compare(this.getPriority(), that.getPriority());
    }
  }

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    if (initial == null) {
      throw new IllegalArgumentException();
    }

    SearchNode node = new SearchNode(initial, 0, null);
    MinPQ<SearchNode> nodes = new MinPQ<SearchNode>();
    nodes.insert(node);

    SearchNode twin = new SearchNode(node.board.twin(), 0, null);
    MinPQ<SearchNode> twinNodes = new MinPQ<SearchNode>();
    twinNodes.insert(twin);

    /*
     * Now we can start searching for the solution of two boards, seing which one is able to reach
     * the goal. (Either the original set of tiles or the twin has a solution.)
     */

    while (!nodes.min().board.isGoal() && !twinNodes.min().board.isGoal()) {
      SearchNode min = nodes.min();
      SearchNode minTwin = twinNodes.min();
      LinkedList<Board> nodeNeighbors = (LinkedList<Board>) nodes.min().board.neighbors();
      LinkedList<Board> twinNeighbors = (LinkedList<Board>) twinNodes.min().board.neighbors();

      for (Board board : nodeNeighbors) {
        if (board.equals(min.prev.board)) {
          nodeNeighbors.remove(board);
        } else {
          nodes.insert(new SearchNode(board, min.getMoves() + 1, min));
        }
      }

      for (Board board : twinNeighbors) {
        if (board.equals(minTwin.prev.board)) {
          twinNeighbors.remove(board);
        } else {
          twinNodes.insert(new SearchNode(board, minTwin.getMoves() + 1, minTwin));
        }
      }
    }
  }

  // is the initial board solvable? (see below)
  public boolean isSolvable() {
    return isSolvable;
  }

  // min number of moves to solve initial board
  public int moves() {
    return 0;
  }

  // sequence of boards in a shortest solution
  public Iterable<Board> solution() {
    return null;
  }

  // test client
  public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] tiles = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        tiles[i][j] = in.readInt();
    Board initial = new Board(tiles);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }

}
