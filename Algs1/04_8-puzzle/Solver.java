import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    class SearchNode implements Comparable<SearchNode>{
        private Board board;
        private int moves;
        private int priority;
        private SearchNode prev;

        public SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.priority = board.manhattan() + this.moves;
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
        if(initial == null) {
            throw new IllegalArgumentException();
        }
        
        SearchNode node = new SearchNode(initial, 0, null);        
        MinPQ<SearchNode> nodes = new MinPQ<SearchNode>();        
        nodes.insert(node);
        
        SearchNode twin = new SearchNode(node.board.twin(), 0, null);        
        MinPQ<SearchNode> twins = new MinPQ<SearchNode>();
        twins.insert(twin);
        
        /* Now we can start searching for the solution of two boards,
         * seing which onw is able to reach the goal. Either the original
         * set of tiles or the twin has a solution.
         */
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() { return false; }

    // min number of moves to solve initial board
    public int moves() { return 0; }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() { return null; }

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
