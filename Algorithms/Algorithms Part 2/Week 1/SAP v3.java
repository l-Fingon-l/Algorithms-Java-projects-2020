import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class SAP 
{
	private Digraph G;
	private int[] ids;
	private int size;
	
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
	   
	   size = this.G.V();
	   ids = new int[size];
	   boolean[] marked = new boolean[size];
	   
	   Digraph G_additional = new Digraph(G);
	   for (int i = 0; i < G_additional.V(); i++)
		   for (int j: G_additional.adj(i))
		   {
			   boolean exists = false;
			   for (int x: G_additional.adj(j))
				   if (x == i) exists = true;
			   if (!exists) G_additional.addEdge(j, i);
		   }
			   
	   for (int i = 0; i < G_additional.V(); i++)
		   if (!marked[i]) dfs(i, i, marked, G_additional);
   }
   
   private void dfs(int v, int id, boolean[] marked, Digraph G)
   {
	   marked[v] = true;
	   ids[v] = id;
	   for (int w: G.adj(v))
		   if (!marked[w]) dfs(w, id, marked, G);
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
	   if (ids[v] != ids[w]) return new pair(-1, -1);
	   
	   ArrayList<Integer> Set1 = new ArrayList<>(1);
	   ArrayList<Integer> Set2 = new ArrayList<>(1);
	   Set1.add(v);
	   Set2.add(w);
	   return bfs_sap(Set1, Set2);
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
	   
	   pair result = new pair(-1, -1);
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
			   else if (side[y] != x.first) 
			   {
				   if (result.first == -1 || depth_relative[y] + depth_relative[x.second] + 1 < result.first)
				   {
					   result.first = depth_relative[y] + depth_relative[x.second] + 1;
					   result.second = y;
				   }
			   }
	   }
	   
	   return result;
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