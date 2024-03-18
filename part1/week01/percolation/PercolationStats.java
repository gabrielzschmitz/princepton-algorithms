/*
  Program Description:
  --------------------
  PercolationStats.java is a Java class that performs a series of computational
  experiments to estimate the percolation threshold for a given n-by-n grid. The
  percolation threshold represents the fraction of open sites required for a
  percolation system to percolate.

  Implementation Details:
  -----------------------
  - Imports the StdOut, StdRandom, and StdStats classes from the algs4.jar
    library for performing standard output, generating random numbers, and
    computing statistical values, respectively.
  - Provides methods to initialize a series of percolation experiments, estimate
    the mean and standard deviation of percolation thresholds, and compute a
    confidence interval.
  - Conducts Monte Carlo simulations to estimate the percolation threshold by
    performing multiple independent computational experiments on an n-by-n grid.
  - Utilizes statistical formulas to calculate the mean, standard deviation, and
    confidence interval of the percolation thresholds.

  Note: This class facilitates accurate estimation of the percolation threshold
  through computational experiments, providing valuable insights into the
  behavior of percolation systems under varying conditions.
*/
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  private static final double CONFIDENCE95 = 1.96;
  private final int trials;
  private final double[] siteData;

  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }
    this.trials = trials;
    this.siteData = new double[trials];
    for (int i = 0; i < trials; i++) {
      Percolation p = new Percolation(n);
      while (!p.percolates()) {
        int first = StdRandom.uniformInt(1, n + 1);
        int second = StdRandom.uniformInt(1, n + 1);
        if (!p.isOpen(first, second)) {
          p.open(first, second);
        }
      }
      this.siteData[i] = (double) p.numberOfOpenSites() / (n * n);
    }
  }

  public static void main(String[] args) {

    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);
    PercolationStats stats = new PercolationStats(n, trials);

    StdOut.println("mean                    = " + stats.mean());
    StdOut.println("stddev                  = " + stats.stddev());
    StdOut.println(
        "95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
  }

  public double mean() {
    return StdStats.mean(siteData);
  }

  public double stddev() {
    return StdStats.stddev(siteData);
  }

  public double confidenceLo() {
    return mean() - CONFIDENCE95 * stddev() / Math.sqrt(trials);
  }

  public double confidenceHi() {
    return mean() + CONFIDENCE95 * stddev() / Math.sqrt(trials);
  }
}
