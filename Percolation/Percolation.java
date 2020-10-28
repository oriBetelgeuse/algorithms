import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int size;
    private int[][] grid;
    private UnionFind connection;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        assert n > 0;

        size = n;
        grid = new int[n][n];
        connection = new UnionFind(n * n + 2);

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                grid[i][j] = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        assert row >= 1 && row <= size;
        assert col >= 1 && col <= size;

        grid[row - 1][col - 1] = 1;

        if (row == 1)
            connection.union(size * size, (row - 1) * size + col - 1);
        if (row == size)
            connection.union(size * size + 1, (row - 1) * size + col - 1);

        if (col - 1 <= size && col - 1 >= 1 && isOpen(row, col - 1))
            connection.union(size * (row - 1) + col - 1, size * (row - 1) + col - 2);
        if (col + 1 <= size && col + 1 >= 1 && isOpen(row, col + 1))
            connection.union(size * (row - 1) + col - 1, size * (row - 1) + col);
        if (row - 1 <= size && row - 1 >= 1 && isOpen(row - 1, col))
            connection.union(size * (row - 1) + col - 1, size * (row - 2) + col - 1);
        if (row + 1 <= size && row + 1 >= 1 && isOpen(row + 1, col))
            connection.union(size * (row - 1) + col - 1, size * row + col - 1);

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        assert row >= 1 && row <= size;
        assert col >= 1 && col <= size;

        return grid[row - 1][col - 1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        assert row >= 1 && row <= size;
        assert col >= 1 && col <= size;

        return connection.find((row - 1) * size + (col - 1)) == connection.find(size * size);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int sum = 0;

        for (int[] row : grid)
            for (int value : row)
                sum += value;

        return sum;
    }

    // does the system percolate?
    public boolean percolates() {
        return connection.find(size * size) == connection.find(size * size + 1);
    }
}
