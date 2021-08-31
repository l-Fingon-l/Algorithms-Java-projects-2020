import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP 
{
	private Digraph G;
	private int[] depth;
	private int[] ids;
	private int size;
	private boolean rooted = false;
	
	private class pair
	{
		public int first;
		public int second;
		public pair(int first, int second) { this.first = first; this.second = second; }
	}
	
   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G)
   {
	   if (G == null) throw new IllegalArgumentException("The digraph is 'null'!");
	   this.G = new Digraph(G);
	   ShortestDirectedCycle sdc = new ShortestDirectedCycle(this.G);
       if (!sdc.acyclic()) throw new IllegalArgumentException("The graph given is not a DAG!");
	   size = this.G.V();
	   depth = new int[size];
	   ids = new int[size];
	   boolean[] marked = new boolean[size];
	   Digraph GR = this.G.reverse();
	   
	   int amount = 0;
	   for(int i = 0; i < size; i++)
		   if (!marked[i])
		   {
			   int root = root_dfs(i, this.G);
			   ids[root] = root;
			   marked[root] = true;
			   
			   Queue<pair> bfs = new Queue<pair>(); // 1st value is depth and 2nd is an id
			   bfs.enqueue(new pair(0, root));
			   
			   while(!bfs.isEmpty()) // marking the depth array for an optimisation
			   {
				   pair v = bfs.dequeue();
				   for (int w: GR.adj(v.second))
					   if (depth[w] == 0) 
					   {
						   depth[w] = v.first + 1;
						   ids[w] = root;
						   marked[w] = true;
						   bfs.enqueue(new pair(depth[w], w));
					   }
			   }
			   amount++;
		   }
	   
	   if (amount == 1) rooted = true;
   }
   
   private int root_dfs(int v, Digraph G)
   {
	   for (int w: G.adj(v))
		   return root_dfs(w, G);
	   return v;   
   }
   
   private pair bfs_sap_single(int v, int w) // 1st value is the length of SAP, 2nd is an ancestor
   {
	   validateVertex(v);
	   validateVertex(w);
	   
	   if (v == w) return new pair(0, v);
	   
	   if (!rooted)
		   if (ids[v] != ids[w]) return new pair(-1, -1);
	   
	   Queue<Integer> bfs = new Queue<Integer>(); // 1st value is depth and 2nd is an id
	   boolean[] marked = new boolean[size];
	   int[] depth_relative = new int[size];
	   int v_depth = depth[v];
	   int w_depth = depth[w];
	   
	   do
	   {
		   int res = v_depth - w_depth;
		   if (res == 0)
		   {
			   if (!marked[v])
			   {
				   bfs.enqueue(v);
				   marked[v] = true;
			   } 
			   if (!marked[w])
			   {
				   bfs.enqueue(w);
				   marked[w] = true;
			   }
		   }
		   else
		   {
			   if (!marked[v] && res > 0)
			   {
				   bfs.enqueue(v);
				   marked[v] = true;
			   } 
			   if (!marked[w] && res < 0)
			   {
				   bfs.enqueue(w);
				   marked[w] = true;
			   }
		   }
		   
		   if (v_depth > w_depth) v_depth--;
		   else if (v_depth < w_depth) w_depth--;
		   
		   int x = bfs.dequeue();
		   for (int y: G.adj(x))
			   if (!marked[y] && y != w && y != v) 
			   {
				   marked[y] = true;
				   depth_relative[y] = depth_relative[x] + 1;
				   bfs.enqueue(y);
			   }
			   else return new pair(depth_relative[y] + depth_relative[x] + 1, y);
	   }
	   while (!bfs.isEmpty());
	   
	   return new pair(-1, -1);
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w)
   {
	   return bfs_sap_single(v, w).first;
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w)
   {
	   return bfs_sap_single(v, w).second;
   }
   
   private pair bfs_sap(Iterable<Integer> v, Iterable<Integer> w)
   {
	   for (int x: v) validateVertex(x);
	   for (int x: w) validateVertex(x);
	   
	   Queue<pair> bfs = new Queue<pair>(); // 1st value is side and 2nd is an id
	   boolean[] marked = new boolean[size];
	   int[] side = new int[size];
	   int[] depth_relative = new int[size];
	   
	   for(int x: v)
		   if (!marked[x])
		   {
			   marked[x] = true;
			   bfs.enqueue(new pair(0, x));
		   }
	   for (int x: w)
		   if (!marked[x])
		   {
			   marked[x] = true;
			   side[x] = 1;
			   bfs.enqueue(new pair(1, x));
		   }
		   else if (side[x] == 0) return new pair(0, x); // a tricky moment
	   
	   while (!bfs.isEmpty())
	   {
		   pair x = bfs.dequeue();
		   for (int y: G.adj(x.second))
			   if (!marked[y])
			   {
				   marked[y] = true;
				   side[y] = x.first;
				   depth_relative[y] = depth_relative[x.second] + 1;
				   bfs.enqueue(new pair(x.first, y));
			   }
			   else if (side[y] != x.first) return new pair(depth_relative[y] + depth_relative[x.second] + 1, y);
	   }
	   
	   return new pair(-1, -1);
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

   private void validateVertex(int v)
	{
		if (v >= size) throw new IllegalArgumentException("The vertex " + v + " is outside the range of a graph!");
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