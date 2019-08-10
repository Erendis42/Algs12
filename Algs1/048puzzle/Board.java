import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

public class Board implements Comparable<Board> {
  private final int[][] tiles;
  private final int n;
  private int hamming;
  private int manhattan;

  // construct a board from an n-by-n array of blocks
  // (where blocks[i][j] = block in row i, column j) 
  public Board(int[][] blocks) {
    n = blocks.length;
    this.tiles = deepCopy(blocks);
    hamming = 0;
    calculateHamming();
    manhattan = 0;
    calculateManhattan();
  }

  private int[][] deepCopy(int[][] blocks) {
    return java.util.Arrays.stream(blocks).map(el -> el.clone()).toArray($ -> blocks.clone());
  }

  // board dimension n
  public int dimension() {
    return n;
  }

  // number of blocks out of place
  public int hamming() {
    return hamming;
  }

  private void calculateHamming() {
    for (int r = 0; r < n; r++) {
      for (int c = 0; c < n; c++) {
        if (getTiles()[r][c] != 0 && getTiles()[r][c] != (r * n) + c + 1) {
          hamming++;
        }
      }
    }
  }

  // sum of Manhattan distances between blocks and goal
  public int manhattan() {
    return manhattan;
  }

  private void calculateManhattan() {
    for (int r = 0; r < n; r++) {
      for (int c = 0; c < n; c++) {
        if (getTiles()[r][c] != 0 && getTiles()[r][c] != (r * n) + c + 1) {
          int tile = getTiles()[r][c];
          int x = (tile % n) - 1;
          if (x < 0) {
            x = n;
          }
          int y = (tile / n);
          if (y < 0) {
            y = n;
          }

          int deltaX = Math.abs(c - x);
          int deltaY = Math.abs(r - y);

          manhattan += (deltaX + deltaY);
        }
      }
    }
  }

  // is this board the goal board?
  public boolean isGoal() {
    return manhattan() == 0;
  }

  // a board that is obtained by exchanging any pair of blocks
  public Board twin() {
    int[][] twin = deepCopy(tiles);
    int[][] nbrs = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};

    int r1 = StdRandom.uniform(n);
    int c1 = StdRandom.uniform(n);

    while (twin[r1][c1] == 0) {
      r1 = StdRandom.uniform(n);
      c1 = StdRandom.uniform(n);
    }

    for (int[] nbr : nbrs) {
      int r2 = r1 + nbr[0];
      int c2 = c1 + nbr[1];
      if (r2 >= 0 && r2 < n && c2 >= 0 && c2 < n && twin[r2][c2] != 0) {
        int temp = twin[r1][c1];
        twin[r1][c1] = twin[r2][c2];
        twin[r2][c2] = temp;
        break;
      }
    }

    return new Board(twin);
  }

  // does this board equal y?
  public boolean equals(Object y) {
    if (y.getClass().equals(new Board(new int[][] {}).getClass())) {
      int[][] otherTiles = ((Board) y).getTiles();
      // check if same size
      if (n == otherTiles.length) {
        for (int i = 0; i < n; i++) {
          for (int j = 0; j < n; j++) {
            // check equality element by element
            if (tiles[i][j] != otherTiles[i][j]) {
              return false;
            }
          }
        }
        return true;
      }
    }
    return false;
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {

    Queue<Board> neighbors = new Queue<Board>();

    // there are no neighbors if the size of the board is 1x1
    if (n == 1) {
      return neighbors;
    }

    // a board has at most 4 neighbors (boards 1 move away from this)

    // get position of tile 0

    int row = 0;
    int col = 0;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (getTiles()[i][j] == 0) {
          row = i;
          col = j;
        }
      }
    }

    int[][] nbrs = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
    int temp;

    for (int[] nbr : nbrs) {
      int r = row + nbr[0];
      int c = col + nbr[1];
      if (r >= 0 && r < n && c >= 0 && c < n) {
        int[][] nbrTiles = deepCopy(tiles);

        temp = nbrTiles[r][c];
        nbrTiles[r][c] = 0;
        nbrTiles[row][col] = temp;

        Board b = new Board(nbrTiles);

        neighbors.enqueue(b);
      }
    }

    return neighbors;
  }

  // string representation of this board (in the output format specified below)
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(n + "\n");
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        s.append(String.format("%2d ", getTiles()[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  private int[][] getTiles() {
    return tiles;
  }

  // unit tests (not graded)
  public static void main(String[] args) {}

  public int compareTo(Board that) {
    // TODO Auto-generated method stub
    if (this.manhattan < that.manhattan) {
      return -1;
    }
    if (this.manhattan > that.manhattan) {
      return 1;
    }
    return 0;
  }
}
