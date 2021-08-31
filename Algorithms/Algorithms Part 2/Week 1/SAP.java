import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Vector;

public class SAP 
{
	private Digraph G;
	private int size;
	
	private class pair
	{
		public int first;
		public int second;
		public pair(int first, int second) { this.first = first; this.second = second; }
	}
	
	private class triple
    {
		public int side;
		public int id;
		public int depth;
		public triple(int side, int id, int depth) { this.side = side; this.id = id; this.depth = depth; }
	}
	
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G)
    {
	    if (G == null) throw new IllegalArgumentException("The digraph is 'null'!");
	    this.G = new Digraph(G);
	   
	    size = this.G.V();
    }
   
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w)
    {
	    return bfs_sap(v, w).first;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w)
    {
	    return bfs_sap(v, w).second;
    }
   
    private pair bfs_sap(int v, int w)
    {
	    validateVertex(v);
	    validateVertex(w);
	   
	    ArrayList<Integer> Set1 = new ArrayList<>(1);
	    ArrayList<Integer> Set2 = new ArrayList<>(1);
	    Set1.add(v);
	    Set2.add(w);
	    return bfs_sap(Set1, Set2);
    }
   
    private pair bfs_sap(Iterable<Integer> v, Iterable<Integer> w)
    {
	    for (Integer x: v) validateVertex(x);
	    for (Integer x: w) validateVertex(x);
	   
	    Queue<triple> bfs = new Queue<triple>(); // 1st value is side, 2nd is an id and 3rd is a depth
	    boolean[][] marked = new boolean[2][size];
 	    int[] side = new int[size];
	    int[] depth_relative = new int[size];
	   
	    pair result = new pair(-1, -1);
	   
	    for(int x: v)
		    if (!marked[0][x])
		    {
			    marked[0][x] = true;
			    bfs.enqueue(new triple(0, x, 0));
		    }
	    for (int x: w)
		    if (marked[0][x]) return new pair(0, x);
		    else if (!marked[1][x])
		    {
			    marked[1][x] = true;
			    bfs.enqueue(new triple(1, x, 0));
		    }
	    
	    while (!bfs.isEmpty())
	    {
		    triple x = bfs.dequeue();
		    for (int y: G.adj(x.id))
			    if (!marked[x.side][y])
			    {
				    marked[x.side][y] = true;
				    bfs.enqueue(new triple(x.side, y, x.depth + 1));
				   
				    if (!marked[oppo(x.side)][y])
				    {
					    side[y] = x.side;
					    depth_relative[y] = x.depth + 1;
				    }
				    else if (result.first == -1 || depth_relative[y] + x.depth + 1 < result.first)
				    {
					    result.first = depth_relative[y] + x.depth + 1;
					    result.second = y;
			 	    }
	 		    }
	    }
	   
	    return result;
    }
   
    private int oppo(int i)
    {
	    if (i == 1) return 0;
	    else return 1;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w)
    {
	    if (v == null) throw new IllegalArgumentException("The first set is 'null'!");
	    if (w == null) throw new IllegalArgumentException("The second set is 'null'!");
 	    return bfs_sap(v, w).first;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
    {
	    if (v == null) throw new IllegalArgumentException("The first set is 'null'!");
	    if (w == null) throw new IllegalArgumentException("The second set is 'null'!");
	    return bfs_sap(v, w).second;
    }

    private void validateVertex(Integer v)
	{
	    if (v == null) throw new IllegalArgumentException("The vertex is null!");
	    if (v < 0 || v >= size)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (size-1));
	}
   
   // do unit testing of this class
    public static void main(String[] args) 
    {
       In in = new In(args[0]);
	   Digraph G = new Digraph(in);
	   SAP sap = new SAP(G);

	   while (!StdIn.isEmpty()) 
	   {
	       int v = StdIn.readInt();
	       int w = StdIn.readInt();
	       int length   = sap.length(v, w);
	       int ancestor = sap.ancestor(v, w);
	       StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	   } 
	}
}