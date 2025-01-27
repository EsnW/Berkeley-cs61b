
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * 0      1
 * open  blocked
 */
public class Percolation {
    private WeightedQuickUnionUF sets;
    private WeightedQuickUnionUF sets2; // no bottom
    private int[][] grid;
    private int[] dir1;
    private int[] dir2;
    private int opensize;
    private int N;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be positive");
        }
        grid = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = 1;  // initially blocked
            }

            /**  N * N stands for watersource, N * N + 1 stands for waterdrain */
            sets = new WeightedQuickUnionUF(N * N + 2);
            sets2 = new WeightedQuickUnionUF(N * N + 1);
            dir1 = new int[]{0, -1, 1, 0};
            dir2 = new int[]{-1, 0, 0, 1};
            opensize = 0;
            this.N = N;
        }
    }

    private int twoindex(int row, int col) {
        return col + row * grid.length;
    }

    private boolean outside(int x) {
        return x < 0 || x >= grid.length;
    }

    public void open(int row, int col) {
        if (outside(row) || outside(col)) {
            throw new IndexOutOfBoundsException("row and col should be [0, N - 1]");
        }
        if (isOpen(row, col)) {
            return;
        }
        opensize += 1;
        grid[row][col] = 0; // open that site
        /**  the top site */
        if (row == 0) {
            sets.union(N * N, twoindex(row, col));
            sets2.union(N * N, twoindex(row, col));
        }
        /**  the bottom line */
        if (row == N - 1) {
            sets.union(N * N + 1, twoindex(row, col));
        }
        for (int i = 0; i < 4; i++) {
            if (!outside(row + dir1[i]) && !outside(col + dir2[i])) {
                if (isOpen(row + dir1[i], col + dir2[i])) {
                    sets.union(twoindex(row, col), twoindex(row + dir1[i], col + dir2[i]));
                    sets2.union(twoindex(row, col), twoindex(row + dir1[i], col + dir2[i]));
                }
            }
        }

    }

    public boolean isOpen(int row, int col) {
        if (outside(row) || outside(col)) {
            throw new IndexOutOfBoundsException("row and col should be [0, N - 1]");
        }
        return grid[row][col] == 0;
    }

    public boolean isFull(int row, int col) {
        if (outside(row) || outside(col)) {
            throw new IndexOutOfBoundsException("row and col should be [0, N - 1]");
        }
        return sets2.connected(twoindex(row, col), N * N);
    }

    public int numberOfOpenSites() {
        return opensize;
    }

    public boolean percolates() {
        return sets.connected(N * N, N * N + 1);
    }


    public static void main(String[] args) {
        /**
         p1.open(2, 2);
         p1.open(2, 3);
         System.out.println(p1.isOpen(2, 2));
         System.out.println(p1.isFull(2, 2));
         p1.open(0, 2);
         System.out.println(p1.isOpen(0, 2));
         p1.open(0, 3);
         System.out.println(p1.percolates());
         System.out.println(p1.numberOfOpenSites());
         p1.open(3, 2);
         p1.open(1, 3);
         System.out.println(p1.isFull(2, 2));
         System.out.println(p1.percolates());

         System.out.println();

         Percolation p2 = new Percolation(3);
         p2.open(1, 1);
         p2.open(2, 1);
         System.out.println(p2.percolates());
         p2.open(0, 1);
         System.out.println(p2.percolates());
         */
    }
}
