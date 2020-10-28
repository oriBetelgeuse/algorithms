import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] critProb;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        assert n > 0;
        assert trials > 0;

        Percolation grid;
        critProb = new double[trials];
        int[] indexes = new int[n * n];

        for (int i = 0; i < trials; i++) {
            grid = new Percolation(n);
            int iterator = n * n;
            for (int j = 0; j < n * n; j++)
                indexes[j] = j;

            while (!grid.percolates()) {
                int randIndex = StdRandom.uniform(iterator);
                int index = indexes[randIndex];

                indexes[randIndex] = indexes[iterator - 1];
                iterator--;

                grid.open(index / n + 1, index % n + 1);
            }

            critProb[i] = ((double) grid.numberOfOpenSites()) / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(critProb);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(critProb);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(critProb.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(critProb.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats stat = new PercolationStats(Integer.parseInt(args[0]),
                                                     Integer.parseInt(args[1]));

        System.out.println(String.format("mean = %f", stat.mean()));
        System.out.println(String.format("stddev = %f", stat.stddev()));
        System.out.println(
                String.format("95%% confidence interval = [%f, %f]", stat.confidenceLo(),
                              stat.confidenceHi())
        );
    }
}
