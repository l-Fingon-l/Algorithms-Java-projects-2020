import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver 
{
	private MinPQ<SearchNode> pq1;
	private MinPQ<SearchNode> pq2;
	private int moves;
	private boolean key;
	private final boolean isSolvable;
	private final Solution solution_;
	
	private final class SearchNode implements Comparable<SearchNode>
	{
		public final Board board;
		public final int moves;
		public final SearchNode prev;
		private final int priority;
		
		public SearchNode(Board board_, SearchNode prev_)
		{
			board = board_;
			prev = prev_;
			
			if (prev == null)
				moves = -1;
			else moves = prev.moves + 1;
			
			if (board == null)
				priority = 0;
			else priority = board.manhattan() + moves;
		}

		public int compareTo(SearchNode that) 
		{
			if (priority > that.priority) return 1;
			else if (priority < that.priority) return -1;
			else
			{
				int manhattan1 = priority - moves;
				int manhattan2 = that.priority - that.moves;
				if (manhattan1 > manhattan2) return 1;
				else if (manhattan1 < manhattan2) return -1;
				else return 0;
			}	
		}
	}
	
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
    	if (initial == null) throw new IllegalArgumentException();
    	
    	pq1 = new MinPQ<SearchNode>();
    	pq2 = new MinPQ<SearchNode>();
    	moves = -2;
    	key = true;
    	
    	pq1.insert(new SearchNode(initial, new SearchNode(null, null)));
    	pq2.insert(new SearchNode(initial.twin(), new SearchNode(null, null)));
    	
    	SearchNode curr;
    	do
    	{
    		if (key)
    		{
    			curr = pq1.delMin();
    			if (curr.board.isGoal()) break; // the solution is found
    			
    			for(Board board: curr.board.neighbors())
    			{
    				if (!board.equals(curr.prev.board))
    					pq1.insert(new SearchNode(board, curr));
    			}
    			key = false;
    		}
    		else
    		{
    			curr = pq2.delMin();
    			if (curr.board.isGoal()) break; // the solution is found (unsolvable)
    			
    			for(Board board: curr.board.neighbors())
    			{
    				if (!board.equals(curr.prev.board))
    					pq2.insert(new SearchNode(board, curr));
    			}
    			key = true;
    		}
    	}
    	while (true);
    	pq1 = null;
    	pq2 = null;
    	
    	if (key)
    	{
    		isSolvable = true;
    		SearchNode solution = curr;
    		moves = solution.moves;
    		
    		SearchNode curr_ = solution;
    		Board[] steps = new Board[moves + 1];
    		for (int i = 0; i < moves + 1; i++)
    		{
    			steps[i] = curr_.board;
    			curr_ = curr_.prev;
    		}
    		solution_ = new Solution(steps);
    	}
    	else 
    	{
    		isSolvable = false;
    		moves++;
    		solution_ = null;
    	}
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable()
    {
    	return isSolvable;
    }

    // min number of moves to solve initial board
    public int moves() 
    {
    	return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution()
    {
    	return solution_;
    }
    
    private final class Solution implements Iterable<Board>
    {
    	private SolutionSequence solutionSequence;
    	private final Board[] steps;
    	
    	public Solution(Board[] steps_)
    	{
    		steps = new Board[moves + 1];
    		for (int i = 0; i < moves + 1; i++)
    			steps[i] = steps_[i];
    	}
		
		public Iterator<Board> iterator() 
		{
			solutionSequence = new SolutionSequence();
			return solutionSequence;
		}
    	
		private final class SolutionSequence implements Iterator<Board>
		{
			private int N = moves;
			
			public boolean hasNext() 
			{
				return N > -1;
			}
			public void remove() 
	    	{ /* not supported */ 
	    		throw new UnsupportedOperationException();
	    	}
			public Board next() 
			{
				if (!hasNext()) throw new NoSuchElementException();

				return steps[N--];
			}
		}
    }

    // test client (see below) 
    public static void main(String[] args) 
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println(initial);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else 
        {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}