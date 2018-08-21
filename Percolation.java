import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   private boolean[] sites;
   private     int numberOfOpenSites;
   private     int size;                       //size of the grid
   private WeightedQuickUnionUF wquuf;
   private WeightedQuickUnionUF wquuf1;
   public Percolation(int n){               // create n-by-n grid, with all sites blocked 
      size = n;
      if (size>0) {
         sites = new boolean [size*size];
         for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size ; j++) {
               sites [i*size+j] = false;
            }
         }
         numberOfOpenSites = 0;             //initialize the counter
         wquuf = new WeightedQuickUnionUF(size*size+2);
         wquuf1 = new WeightedQuickUnionUF(size*size+1);
      }else{
         throw new IllegalArgumentException(); 
      }
   }               
   public    void open(int row, int col) {  // open site (row, col) if it is not open already
      if (row > 0 && row < size+1 && col > 0 && col < size+1) {
         if (sites [(row-1)*size+col-1] != true) {
            sites [(row-1)*size+col-1] = true;
            numberOfOpenSites++;            //count number of opensites
            if (row == 1) {wquuf.union(0, (row-1)*size+col); wquuf1.union(0, (row-1)*size+col);}
            if (row == size) {wquuf.union(size*size+1, (row-1)*size+col);}
            if (row > 1) {                  //union neighboring opensites
               if (isOpen(row-1, col)){ wquuf.union((row-1-1)*size+col, (row-1)*size+col); wquuf1.union((row-1-1)*size+col, (row-1)*size+col);}
            }
            if (col > 1) {
               if (isOpen(row, col-1)){ wquuf.union((row-1)*size+col-1, (row-1)*size+col); wquuf1.union((row-1)*size+col-1, (row-1)*size+col);}
            }
            if (row < size) {
               if (isOpen(row+1, col)){ wquuf.union((row+1-1)*size+col, (row-1)*size+col); wquuf1.union((row+1-1)*size+col, (row-1)*size+col);}
            }
            if (col < size) {
               if (isOpen(row, col+1)){ wquuf.union((row-1)*size+col+1, (row-1)*size+col); wquuf1.union((row-1)*size+col+1, (row-1)*size+col);}
            }

         }
      }else{
         throw new IllegalArgumentException();
      }
   }   
   public boolean isOpen(int row, int col) { // is site (row, col) open?
      if (row > 0 && row < size+1 && col > 0 && col < size+1) {
         return sites [(row-1)*size+col-1] == true;
      }else{
         throw new IllegalArgumentException();
      }
   }
   public boolean isFull(int row, int col) { // is site (row, col) full?
      if (row > 0 && row < size+1 && col > 0 && col < size+1) {
         return wquuf1.connected(0, (row-1)*size+col);
      }else{
         throw new IllegalArgumentException();
      }
   }
   public     int numberOfOpenSites() { return numberOfOpenSites; }      // number of open sites
   public boolean percolates() { return wquuf.connected(0, size*size+1); }             // does the system percolate?

   public static void main(String[] args)   // test client (optional)
   {
      int n = 3;
      Percolation p = new Percolation(n);
      p.open(2,2);
      p.open(1,2);
      p.open(2,1);
      p.open(3,1);
      if (p.isOpen(2,2)) { System.out.println("isOpen");}
      if (p.percolates()) { System.out.println("isPercolates");}
      if (!p.isFull(1,1)) { System.out.println("isnotFull");}
      if (p.isFull(1,2)) { System.out.println("isFull");}
      System.out.println(p.numberOfOpenSites());
   }
}
