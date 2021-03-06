import java.util.LinkedList;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
  private final int[][] tiles;
  private final int[][] goal;
  private final int[][] twin;
  
  private final int n;

  private int hammingDistance;
  private int manhattanDistance;

  // create a board from an n-by-n array of tiles,
  // where tiles[row][col] = tile at (row, col)

  /*
   * You may assume that the constructor receives an n-by-n array containing the n^2 integers
   * between 0 and n^2 − 1, where 0 represents the blank square. You may also assume that 2 ≤ n
   * <128.
   */
  public Board(int[][] tiles) {
    this.tiles = java.util.Arrays.stream(tiles).map(el -> el.clone()).toArray($ -> tiles.clone());
    n = tiles.length;
    goal = generateGoal();
    twin = generateTwin();
    hammingDistance = calculateHammingDistance();
    manhattanDistance = calculateManhattanDistance();
  }

  private int[][] generateGoal() {
    /*
     * generate board to be achieved with 0 as the last element -- in the bottom right corner
     */

    int[][] result = new int[n][n];

    for (int row = 0; row < n; row++) {
      for (int col = 0; col < n; col++) {
        result[row][col] = row * n + col + 1;
      }
    }

    result[n - 1][n - 1] = 0;

    return result;
  }

  // string representation of this board
  /*
   * The toString() method returns a string composed of n + 1 lines. The first line contains the
   * board size n; the remaining n lines contains the n-by-n grid of tiles in row-major order, using
   * 0 to designate the blank square.
   */
  public String toString() {
    return boardToString(tiles);
  }

  private String boardToString(int[][] tiles) {
    String boardAsString = Integer.toString(n) + "\n";

    for (int[] row : tiles) {
      for (int element : row) {
        boardAsString = boardAsString.concat(Integer.toString(element));
        boardAsString = boardAsString.concat("\t");
      }
      boardAsString = boardAsString.concat("\n");
    }

    return boardAsString;
  }

  // board dimension n
  public int dimension() {
    return n;
  }

  // number of tiles out of place
  public int hamming() {
    return hammingDistance;
  }

  private int calculateHammingDistance() {
    int result = 0;

    for (int row = 0; row < n; row++) {
      for (int col = 0; col < n; col++) {
        if (tiles[row][col] != 0 && tiles[row][col] != goal[row][col]) {
          result++;
        }
      }
    }

    return result;
  }

  // sum of Manhattan distances between tiles and goal
  public int manhattan() {
    return manhattanDistance;
  }

  private int calculateManhattanDistance() {
    int result = 0;
    int goalX;
    int goalY;
    int diffX;
    int diffY;

    for (int row = 0; row < n; row++) {
      for (int col = 0; col < n; col++) {
        diffX = 0;
        diffY = 0;
        if (tiles[row][col] != 0 && tiles[row][col] != goal[row][col]) {
          // look for the same element in the goal board
          goalX = 0;
          goalY = 0;
          for (int rowG = 0; rowG < n; rowG++) {
            for (int colG = 0; colG < n; colG++) {
              if (tiles[row][col] == goal[rowG][colG]) {
                goalX = colG;
                goalY = rowG;
                /*
                 * When we find the place of an element, we might as well quit searching for it.
                 */
                rowG = n;
                colG = n;
              }
            }
          }
          diffX = Math.abs(col - goalX);
          diffY = Math.abs(row - goalY);
        }
        result += diffX;
        result += diffY;
      }
    }

    return result;
  }

  // is this board the goal board?
  public boolean isGoal() {
    return toString().equals(boardToString(goal));
  }

  // does this board equal y?
  public boolean equals(Object other) {
    if (other instanceof Board) {
      Board b = (Board) other;
      return toString().equals(b.toString());
    }

    return false;
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    LinkedList<Board> nbrs = new LinkedList<Board>();

    int[][] coords = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};

    int rowOfNull = 0;
    int colOfNull = 0;

    for (int row = 0; row < n; row++) {
      for (int col = 0; col < n; col++) {
        if (tiles[row][col] == 0) {
          rowOfNull = row;
          colOfNull = col;
          row = n;
          col = n;
        }
      }
    }

    for (int c = 0; c < 4; c++) {
      int rn = coords[c][0] + rowOfNull;
      int cn = coords[c][1] + colOfNull;

      // check if field neighboring the 0 element isn't outside of the array
      if (rn >= 0 && rn < n && cn >= 0 && cn < n) {
        Board b = new Board(flip(new int[] {rowOfNull, colOfNull}, new int[] {rn, cn}));
        nbrs.add(b);
      }
    }

    return nbrs;
  }

  // a board that is obtained by exchanging any pair of tiles
  public Board twin() {
    return new Board(twin);
  }

  private int[][] generateTwin() {
    int[] c1 = {StdRandom.uniform(n), StdRandom.uniform(n)};
    while (tiles[c1[0]][c1[1]] == 0) {
      c1[0] = StdRandom.uniform(n);
      c1[1] = StdRandom.uniform(n);
    }

    int[] c2 = {StdRandom.uniform(n), StdRandom.uniform(n)};
    while (tiles[c2[0]][c2[1]] == 0 || c1[0] == c2[0] && c1[1] == c2[1]) {
      c2[0] = StdRandom.uniform(n);
      c2[1] = StdRandom.uniform(n);
    }

    return flip(c1, c2);
  }

  private int[][] flip(int[] c1, int[] c2) {
    int[][] t = java.util.Arrays.stream(tiles).map(el -> el.clone()).toArray($ -> tiles.clone());
    int temp = tiles[c1[0]][c1[1]];

    t[c1[0]][c1[1]] = t[c2[0]][c2[1]];
    t[c2[0]][c2[1]] = temp;

    return t;
  }

  // unit testing (not graded)
  public static void main(String[] args) {
    /*
     * int[][] testTiles1 = new int[][] { {8,1,3}, {4,0,2}, {7,6,5} };
     * 
     * int[][] testTiles2 = new int[][] { {8,1,3}, {4,2,0}, {7,6,5} };
     * 
     * int[][] testTiles3 = new int[][] { {8,1,3}, {4,2,5}, {7,6,0} };
     * 
     * Board b1 = new Board(testTiles3);
     * 
     * System.out.println(b1);
     * 
     * for (Board b : b1.neighbors()) { System.out.println(b.toString()); }
     * 
     * //Board b2 = new Board(testTiles2); //Board b3 = b2.twin();
     * 
     * //System.out.println(b2.toString()); //System.out.println(b3.toString());
     * 
     * //System.out.println(b1.equals(b2)); //System.out.println(b1.hamming());
     * //System.out.println(b1.manhattan());
     * 
     */
  }

}
