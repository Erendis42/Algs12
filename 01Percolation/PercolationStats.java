import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private final int trials;
  private final int sizeOfGrid;
  private double[] fractions;
  private double mean;
  private double stddev;
  private double confidenceLo;
  private double confidenceHi;

  /**
   * Constructor.
   * @param n Size of the grid.
   * @param trials Number of trials to do the math.
   */
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }
    this.sizeOfGrid = n;
    this.trials = trials;
    fractions = new double[trials];
    initialize();
  }

  private void initialize() {
    calculateFractions();
    mean = StdStats.mean(fractions);
    stddev = StdStats.stddev(fractions);
    calculateConfidence();
  }

  private void calculateFractions() {
    Percolation p;
    for (int i = 0; i < trials; i++) {
      p = new Percolation(sizeOfGrid);
      int row;
      int col;

      while (!p.percolates()) {
        row = StdRandom.uniform(sizeOfGrid) + 1;
        col = StdRandom.uniform(sizeOfGrid) + 1;
        if (!p.isOpen(row, col)) {
          p.open(row, col);
        }
      }

      fractions[i] = (double) p.numberOfOpenSites() / (sizeOfGrid * sizeOfGrid);
    }
  }

  public double mean() {
    return mean;
  }

  public double stddev() {
    return stddev;
  }

  public double confidenceLo() {
    return confidenceLo;
  }

  private void calculateConfidence() {
    double frac = ((1.96 * stddev) / Math.sqrt(trials));
    confidenceLo = mean - frac;
    confidenceHi = mean + frac;
  }

  public double confidenceHi() {
    return confidenceHi;
  }

  /**
   * Main function. Accepts two parameters from the command line and uses them as size of grid
   * and number of trials for the simulation, respectively.
   * @param args String array to store command line arguments.
   */
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);

    PercolationStats ps = new PercolationStats(n, trials);

    System.out.println("mean: \t\t\t= " + ps.mean());
    System.out.println("stddev: \t\t= " + ps.stddev());
    System.out.println(
        "95% confidence interval = " + "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
  }
}
