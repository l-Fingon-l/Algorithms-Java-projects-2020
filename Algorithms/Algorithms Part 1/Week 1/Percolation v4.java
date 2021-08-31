import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation 
{
	private boolean[] grid;
	private int size;
	private int numberOfOpened;
	private WeightedQuickUnionUF WQU;
	private WeightedQuickUnionUF WQU2;
	
	private boolean siteAccessable(int row, int col)
	{
		return ((row > 0) && (row < size + 1) && (col > 0) && (col < size + 1));
	}
	
	private int id(int row, int col)
	{
		return (row - 1) * size + (col - 1) + 1;
	}
	
	// creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
    	if (n < 1) throw new IllegalArgumentException("n is less than 1");
    	size = n;
    	grid = new boolean[size * size + 2];
    	numberOfOpened = 0;
    	grid[0] = true; // top site
    	for (int i = 1; i <= size * size; i++)
    	{
    		grid[i] = false;
    	}
    	grid[size * size + 1] = true; // bottom site
    	WQU = new WeightedQuickUnionUF(size * size + 1);
    	WQU2 = new WeightedQuickUnionUF(size * size + 2);
    	for (int i = 1; i <= size; i++) WQU.union(0, i); // connect top
    	
    	for (int i = 1; i <= size; i++) WQU2.union(0, i);
//    	for (int i = 1; i <= size; i++) WQU2.union(size * size + 1, size * size + 1 - i);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
    	if (!siteAccessable(row, col)) throw new IllegalArgumentException("wrong site");
    	if (isOpen(row, col)) return;
    	
    	grid[id(row, col)] = true;
    	numberOfOpened++;
    	
    	if (col != 1) if (isOpen(row, col - 1)) 
    	{
    		WQU.union(id(row, col), id(row, col - 1)); // left
    		WQU2.union(id(row, col), id(row, col - 1)); // left
    	}
    	if (col != size) if (isOpen(row, col + 1))
    	{
    		 WQU.union(id(row, col), id(row, col + 1)); // right
    		 WQU2.union(id(row, col), id(row, col + 1)); // right
    	}
    	if (row != 1) if (isOpen(row - 1, col)) 
    	{
    		WQU.union(id(row, col), id(row - 1, col)); // up
    		WQU2.union(id(row, col), id(row - 1, col)); // up
    	}
    	if (row != size) if (isOpen(row + 1, col)) 
    	{
    		WQU.union(id(row, col), id(row + 1, col)); // down
    		WQU2.union(id(row, col), id(row + 1, col)); // down
    	}
    	if (row == size) WQU2.union(size * size + 1, id(row, col)); // connect bottom
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
    	if (!siteAccessable(row, col)) throw new IllegalArgumentException("wrong site");
    	return grid[id(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
    	if (!siteAccessable(row, col)) throw new IllegalArgumentException("wrong site");
    	if (isOpen(row, col)) return WQU.find(0) == WQU.find(id(row, col));
    	else return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
    	return numberOfOpened;
    }

    // does the system percolate?
    public boolean percolates()
    {
    	return WQU2.find(0) == WQU2.find(size * size + 1);
    }

    // test client (optional)
    public static void main(String[] args)
    {
    	
    }
}
