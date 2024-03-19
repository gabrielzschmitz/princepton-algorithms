/*
  Program Description:
  --------------------
  Percolation.java is a Java class that models a percolation system using an
  n-by-n grid of sites. Each site can either be open or blocked. The primary
  objective of this class is to determine whether the system percolates, i.e.,
  if there exists a path of open sites from the top row to the bottom row.
  Implementation Details:
  -----------------------
  - Imports the WeightedQuickUnionUF class from the algs4.jar library for
    performing union-find operations efficiently.
  - Provides methods for opening sites, checking if sites are open or full,
    determining the number of open sites, and checking if the system percolates.
  - Utilizes the WeightedQuickUnionUF data structure to efficiently model
    connectivity between open sites.
  - Implements methods to convert 2D coordinates to a 1D index and performs
    necessary validations.
  - Utilizes two instances of WeightedQuickUnionUF to avoid backwash issue.
  - Ensures performance requirements by using appropriate data structures and
    algorithms.
  Note: This class provides essential functionality for modeling and analyzing
  percolation systems, enabling scientists and researchers to conduct
  experiments and estimate percolation thresholds accurately.
*/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private static final int TOP = 0;
  private final int size;
  private final WeightedQuickUnionUF sites;
  private final WeightedQuickUnionUF sitesTrack;
  private final int bot;
  private boolean[][] grid;
  private int nOpenSites;

  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }
    this.size = n;
    this.nOpenSites = 0;
    this.sites = new WeightedQuickUnionUF(n * n + 2);
    this.sitesTrack = new WeightedQuickUnionUF(n * n + 2);
    this.bot = n * n;
    this.grid = new boolean[n][n];
  }

  public void open(int row, int col) {
    isIllegal(row, col);
    if (!isOpen(row, col)) {
      this.grid[row - 1][col - 1] = true;
      this.nOpenSites++;

      if (row == 1) {
        sites.union(TOP, xyTo1D(row, col));
        sitesTrack.union(TOP, xyTo1D(row, col));
      }
      if (row == this.size) {
        sites.union(bot, xyTo1D(row, col));
      }

      if (row > 1 && isOpen(row - 1, col)) {
        sites.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        sitesTrack.union(xyTo1D(row, col), xyTo1D(row - 1, col));
      }
      if (row < this.size && isOpen(row + 1, col)) {
        sites.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        sitesTrack.union(xyTo1D(row, col), xyTo1D(row + 1, col));
      }

      if (col > 1 && isOpen(row, col - 1)) {
        sites.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        sitesTrack.union(xyTo1D(row, col), xyTo1D(row, col - 1));
      }
      if (col < this.size && isOpen(row, col + 1)) {
        sites.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        sitesTrack.union(xyTo1D(row, col), xyTo1D(row, col + 1));
      }
    }
  }

  public boolean isOpen(int row, int col) {
    isIllegal(row, col);
    return this.grid[row - 1][col - 1];
  }

  private void isIllegal(int row, int col) {
    if (row < 1 || row > this.size || col < 1 || col > this.size) {
      throw new IllegalArgumentException();
    }
  }

  public boolean isFull(int row, int col) {
    isIllegal(row, col);
    return isOpen(row, col) && sitesTrack.find(TOP) == sitesTrack.find(xyTo1D(row, col));
  }

  public int numberOfOpenSites() {
    return this.nOpenSites;
  }

  public boolean percolates() {
    return sites.find(TOP) == sites.find(bot);
  }

  private int xyTo1D(int row, int col) {
    return (row - 1) * this.size + col;
  }
}
