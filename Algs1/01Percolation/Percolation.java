import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private final int sizeOfGrid;
  private final int idTop;
  private final int idBottom;
  private int openSites;
  private int[][][] id;
  private final WeightedQuickUnionUF dataStructure;
  private final int[][] neighbours = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};

  /**
   * Constructor.
   * 
   * @param n The grid will be an n*n square.
   */
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }

    // store the size of the grid for later use
    this.sizeOfGrid = n;

    idTop = n * n;
    idBottom = n * n + 1;

    // +2 fields for virtual top and bottom nodes
    dataStructure = new WeightedQuickUnionUF(n * n + 2);

    // +1 layer to store the state of a particular site,
    // closed = 0, open = 1, open and full = 2.
    id = new int[n + 1][n + 1][2]; // simulation needs to be indexed 1..N

    for (int row = 1; row <= n; row++) {
      for (int col = 1; col <= n; col++) {
        // id of one site -- this is the number we reference the
        // item in the optimized, linear data structure with
        id[row][col][0] = ((row - 1) * n + col) - 1;
        // All sites are closed by default.
        id[row][col][1] = 0;
      }
    }
  }

  /**
   * @param row
   * @param col
   */
  /**
   * Check if site is open.
   * 
   * @param row The number of row the site to be opened is in.
   * @param col The number of column the site to be opened is in.
   */
  public void open(int row, int col) {
    checkArgs(row, col);

    // check if site is already open and exit function if so to avoid too many nested ifs
    if (id[row][col][1] != 0) {
      return;
    }
    openSites++;

    // if the site is in the top row, connect it to the virtual top node
    // and mark it as filled
    if (row == 1) {
      dataStructure.union(id[row][col][0], idTop);
      id[row][col][1] = 2;
    } else {
      // simply open the site otherwise
      id[row][col][1] = 1;
    }

    // if the site is in the bottom row, connect it to the virtual bottom node
    if (row == sizeOfGrid) {
      dataStructure.union(id[row][col][0], idBottom);
    }

    // Only sites above, below, to the left and right count as neighbours,
    // diagonally positioned ones don't.
    for (int[] rc : neighbours) {
      // get the coordinates of the next neighbor site
      int nr = row + rc[0];
      int nc = col + rc[1];

      // check if these coordinates remain inside the boundaries of the array
      // if the neighbor is open, unite sites
      if (argsOkay(nr, nc) && isOpen(nr, nc)) {
        dataStructure.union(id[row][col][0], id[nr][nc][0]);
      }
    }

    if (isConnectedToTop(row, col)) {
      checkFill(row, col);
    }
  }

  private boolean argsOkay(int nr, int nc) {
    return (nr >= 1 && nc >= 1 && nr <= sizeOfGrid && nc <= sizeOfGrid);
  }

  private void checkArgs(int row, int col) {
    // check if indices are out of range and throw exception if so
    if (row < 1 || row > sizeOfGrid || col < 1 || col > sizeOfGrid) {
      throw new IllegalArgumentException();
    }
  }

  private void checkFill(int r, int c) {

    if (id[r][c][1] != 2) {
      id[r][c][1] = 2;
    }

    int nr;
    int nc;
    for (int[] rc : neighbours) {
      nr = r + rc[0];
      nc = c + rc[1];
      if (argsOkay(nr, nc) && isOpen(nr, nc) && !isFull(nr, nc)) {
        checkFill(nr, nc);
      }
    }
  }

  /**
   * Check if site is open.
   * 
   * @param row The number of row the site to be checked is in.
   * @param col The number of column the site to be checked is in.
   * @return
   */
  public boolean isOpen(int row, int col) {
    checkArgs(row, col);
    // if a site is full, it is definitely open as well
    return (id[row][col][1] == 1 ? true : false) || isFull(row, col);
  }

  public boolean isFull(int row, int col) {
    checkArgs(row, col);
    return id[row][col][1] == 2;
  }

  public int numberOfOpenSites() {
    return openSites;
  }

  public boolean percolates() {
    return sizeOfGrid == 1 ? isOpen(1, 1) : isTopConnectedToBottom();
  }

  private boolean isConnectedToTop(int row, int col) {
    return dataStructure.connected(id[row][col][0], idTop);
  }

  private boolean isTopConnectedToBottom() {
    return dataStructure.connected(idTop, idBottom);
  }

  public static void main(String[] args) {
    // main
  }

}
