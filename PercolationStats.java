import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
   private int trials;
   private double mean;
   private double stddev;
   private double lo;
   private double hi;
   private double[] simulationResult;             // double array to store the threshold of each simulation
   public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
   {
      this.trials = trials;
      if (n <= 0 || trials <= 0) {
         throw new IllegalArgumentException();
      }else{
         simulationResult = new double [trials];
         for (int i = 0; i < trials; i++){
            Percolation perc = new Percolation(n);
            while(!perc.percolates()){ 
               int row = 1 + StdRandom.uniform(n);
               int col = 1 + StdRandom.uniform(n);
               perc.open(row, col);
            }
            double numberOfOpenSites = perc.numberOfOpenSites();
            simulationResult [i] = numberOfOpenSites/(n*n);
         }
      }
   }
   public double mean() {mean = StdStats.mean(simulationResult); return mean;} // sample mean of percolation threshold
   public double stddev() {stddev = StdStats.stddev(simulationResult); return stddev;}  // sample standard deviation of percolation threshold
   public double confidenceLo()                  // low  endpoint of 95% confidence interval
   {
      if (mean == 0 || stddev == 0) {
         mean();
         stddev();
      }
      lo = mean - 1.96 * stddev / Math.sqrt(trials);
      return lo;
   }
   public double confidenceHi()                  // high endpoint of 95% confidence interval
   {
      if (mean == 0 || stddev == 0) {
         mean();
         stddev();
      }
      hi = mean + 1.96 * stddev / Math.sqrt(trials);
      return hi;
   }
   public static void main(String[] args)        // test client (described below)
   {
      int n = StdIn.readInt();
      int trials = StdIn.readInt();
      // int n = 2;
      // int trials = 10000;
      PercolationStats percstats = new PercolationStats(n, trials);
      System.out.println("mean                    = " + percstats.mean());
      System.out.println("stddev                  = " + percstats.stddev());
      System.out.println("95% confidence interval = [" + percstats.confidenceLo() + ", " + percstats.confidenceHi() + "]");
   }
}