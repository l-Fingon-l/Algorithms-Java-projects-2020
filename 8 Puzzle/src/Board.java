import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class Board 
{
	private final int N;
	private final int[][] tiles;
	private final int hamming;
	private final int manhattan;
	private final int zero_pos;
	private final int indx1;
	private final int indx2;
	
	// create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
    	int zero_pos = 0;
    	this.N = tiles.length;
    	this.tiles = new int[N][N];
    	int hamming = 0;
    	int manhattan = 0;
    	for (int i = 0; i < N; i++)
    		for (int j = 0; j < N; j++)
    		{
    			int tile = tiles[i][j];
        		this.tiles[i][j] = tile;
        		if (tile == 0) zero_pos = i * N + j;
        		if (tiles[i][j] != i * N + j + 1 && tiles[i][j] != 0) hamming++;
        		manhattan += manhattan(i, j);
        	}
    	this.zero_pos = zero_pos;
    	this.hamming = hamming;
    	this.manhattan = manhattan;
    	
    	Random rand = new Random();
    	int N_ = N * N;
    	int indx1 = rand.nextInt(N_);
    	while (tiles[indx1 / N][indx1 % N] == 0)
    		indx1 = rand.nextInt(N_);
    	int indx2 = rand.nextInt(N_);
    	while (indx1 == indx2 || tiles[indx2 / N][indx2 % N] == 0)
    		indx2 = rand.nextInt(N_);
    	this.indx1 = indx1;
    	this.indx2 = indx2;
    	
    	tiles = null;
    }
    
    private int manhattan(int i, int j)
    {
    	int tile = tiles[i][j] - 1;
    	if (tile == -1) return 0;
    	return Math.abs(tile / N - i) + Math.abs(tile % N - j);
    }
                                           
    // string representation of this board
    public String toString()
    {
    	StringBuilder str = new StringBuilder(Integer.toString(N));
    	for (int i = 0; i < N; i++)
    	{
    		str.append("\n ");
    		for (int j = 0; j < N; j++)
    		{
    			str.append(Integer.toString(tiles[i][j]));
    			str.append("  ");
    		}
    	}
    	
    	return str.toString();
    }

    // board dimension n
    public int dimension()
    {
    	return N;
    }

    // number of tiles out of place
    public int hamming()
    {
    	return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
    	return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
    	return hamming == 0;
    }

    // does this board equal y?
    public boolean equals(Object y)
    {
    	if (y == null) return false;
    	if (y.getClass() != this.getClass()) return false;
    	
    	Board y_ = (Board)y;
    	if (N != y_.dimension()) return false;
   	
    	for (int i = 0; i < N; i++)
    		for (int j = 0; j < N; j++)
    			if (this.tiles[i][j] != y_.tiles[i][j]) return false;

    	return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
    	return new BoardNeighbors();
    }
    
    private class BoardNeighbors implements Iterable<Board>
    {
    	private Board[] neighbors;
    	private int N_ = 0;
    	private int current = 0;
    	
    	public BoardNeighbors()
    	{
    		int[][] tiles_ = new int[N][N];
    		for (int i = 0; i < N; i++)
        		for (int j = 0; j < N; j++)
        			tiles_[i][j] = tiles[i][j];
    		neighbors = new Board[4];
    		
    		if (zero_pos / N != 0)
    		{
    			exch(tiles_, zero_pos / N, zero_pos % N, zero_pos / N - 1, zero_pos % N);
    			neighbors[current++] = new Board(tiles_);
    			exch(tiles_, zero_pos / N, zero_pos % N, zero_pos / N - 1, zero_pos % N);
    			N_++;
    		}
    			
    		if (zero_pos / N != N - 1)
    		{
    			exch(tiles_, zero_pos / N, zero_pos % N, zero_pos / N + 1, zero_pos % N);
    			neighbors[current++] = new Board(tiles_);
    			exch(tiles_, zero_pos / N, zero_pos % N, zero_pos / N + 1, zero_pos % N);
    			N_++;
    		}
    			
    		if (zero_pos % N != 0) 
    		{
    			exch(tiles_, zero_pos / N, zero_pos % N - 1, zero_pos / N, zero_pos % N);
    			neighbors[current++] = new Board(tiles_);
    			exch(tiles_, zero_pos / N, zero_pos % N - 1, zero_pos / N, zero_pos % N);
    			N_++;
    		}
    		
    		if (zero_pos % N != N - 1)
    		{
    			exch(tiles_, zero_pos / N, zero_pos % N + 1, zero_pos / N, zero_pos % N);
    			neighbors[current++] = new Board(tiles_);
    			exch(tiles_, zero_pos / N, zero_pos % N + 1, zero_pos / N, zero_pos % N);
    			N_++;
    		}
    		
    		current = 0;
    	}
    	
		public Iterator<Board> iterator() 
		{
			return new BoardNeighborsIterator();
		}
		
		private class BoardNeighborsIterator  implements Iterator<Board>
		{
			public boolean hasNext() { return current < N_; }
	    	public void remove() 
	    	{ /* not supported */ 
	    		throw new UnsupportedOperationException();
	    	}
	    	public Board next()
	    	{
	    		if (!hasNext()) throw new NoSuchElementException();
	    		
	    		return neighbors[current++];
	    	}
		}
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    {
    	int[][] tiles = new int[N][N];
    	
    	for (int i = 0; i < N; i++)
    		for (int j = 0; j < N; j++)
    			tiles[i][j] = this.tiles[i][j];
    	
    	exch(tiles, indx1 / N, indx1 % N, indx2 / N, indx2 % N);
    	
    	return new Board(tiles);
    }
    
    private void exch(int[][]a, int i1, int j1, int i2, int j2)
    {
    	int buf = a[i1][j1];
    	a[i1][j1] = a[i2][j2];
    	a[i2][j2] = buf;
    }

    // unit testing (not graded)
    public static void main(String[] args)
    {
    	
    }
}
