import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

public class zpanzzzzz 
{
	private EdgeWeightedGraph G;
	private MinPQ<triple> pq;
	private boolean[] marked;
	private int[] edgeTo;
	private char[] c;
	
	private class triple implements Comparable<triple>
	{
		public int id;
		public int length;
		public int edgeTo;
		public triple(int id, int length, int edgeTo) { this.id = id; this.length = length; this.edgeTo = edgeTo; }
		public int compareTo(triple that) {
	        return Integer.compare(this.length, that.length);
	    }
	}
	
	public zpanzzzzz(EdgeWeightedGraph G_)
	{
		G = G_;
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		c = new char[G.V()];
		for (int i = 'A'; i < G.V() + 'A'; i++)
			c[i - 'A'] = (char)i;
		pq = new MinPQ<>();
	}
	
	public void printShortestPath(int v, int w)
	{
		validateVertex(v);
		validateVertex(w);
		
		boolean connected = false;
		pq.insert(new triple(v, 0, v));
		edgeTo[v] = v;
		
		while (!pq.isEmpty())
		{
			triple x = pq.delMin();
			if (marked[x.id]) continue;
			marked[x.id] = true;
			edgeTo[x.id] = x.edgeTo;
			if (x.id == w) 
			{
				connected = true;
				System.out.println("Length of the path is " + x.length);
				break;
			}
			for (Edge e: G.adj(x.id))
				if (!marked[e.other(x.id)])
					pq.insert(new triple(e.other(x.id), x.length + (int)e.weight(), x.id));
		}
		
		if (!connected)
		{
			System.out.println(w + " and " + v + " are not connected!\n");
			return;
		}
		
		boolean first = false;
		while (v != w)
		{
			if (!first)
			{
				first = true;
				System.out.print(c[w]);
			}
			else System.out.print("-" + c[w]);
			w = edgeTo[w];
		}
		System.out.print("-" + c[w]);
	}
	
	private void validateVertex(int v) {
        if (v < 0 || v >= G.V())
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (G.V()-1));
    }
	
	public static void main(String[] args)
	{
		In in = new In(args[0]);
		EdgeWeightedGraph G = new EdgeWeightedGraph(in);
		zpanzzzzz x = new zpanzzzzz(G);
		x.printShortestPath(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
	}
}
