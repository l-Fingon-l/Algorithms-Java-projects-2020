import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class MonotonicSP
{
	private EdgeWeightedDigraph G;
	private double[] distTo;
	private DirectedEdge[] edgeTo;
	private int s;
	
	private class triple implements Comparable<triple>
	{
		public DirectedEdge edge;
		public double length;
		public int m; // monotonic 0 = decreasing, 1 = increasing, -1 = yet unknown
		public triple(DirectedEdge edge, double length, int m) { this.edge = edge; this.length = length; this.m = m; }
		public int compareTo(triple that) {
	        return Double.compare(this.length, that.length);
		}
	}
	
	public MonotonicSP(EdgeWeightedDigraph G_, int s)
	{
		G = G_;
		validateVertex(s);
		this.s = s;
		distTo = new double[G.V()];
		edgeTo = new DirectedEdge[G.V()];
		MinPQ<triple> pq = new MinPQ<>();
		
		for (DirectedEdge e: G.adj(s))
			pq.insert(new triple(e, e.weight(), -1));
		edgeTo[s] = null;
		
		while (!pq.isEmpty())
		{
			triple x = pq.delMin();
			if (edgeTo[x.edge.to()] != null) continue;
			distTo[x.edge.to()] = x.length;
			edgeTo[x.edge.to()] = x.edge;

			for (DirectedEdge e: G.adj(x.edge.to()))
				if (edgeTo[e.to()] == null)
				{
					int m;
					if (e.weight() < x.edge.weight()) m = 0;
					else if (e.weight() > x.edge.weight()) m = 1;
					else continue;
					
					if (x.m == m || x.m == -1) 
						pq.insert(new triple(e, x.length + (int)e.weight(), m));
					else continue;
				}
		}
	}
	
	public Iterable<DirectedEdge> ShortestPathTo(int v)
	{
		validateVertex(v);
		Stack<DirectedEdge> path = new Stack<>();
		for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
			path.push(e);
		
		return path;
	}
	
	public double distTo(int v)
	{
		validateVertex(v);
		return distTo[v];
	}
	
	public void printShortestPath(int v)
	{
		validateVertex(v);
		if (edgeTo[v] == null)
		{
			System.out.println("There is no monotonic path to " + v);
			return;
		}
			
		StdOut.printf("%d to %d (%.2f): ", s, v, distTo(v));
		for (DirectedEdge e: ShortestPathTo(v))
			StdOut.print(e.from() + "->" + e.to() + "" + String.format("%5.2f", e.weight()) + "  ");
		StdOut.println();
	}
	
	private void validateVertex(int v) {
        if (v < 0 || v >= G.V())
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (G.V()-1));
    }
	
	public static void main(String[] args)
	{
		In in = new In(args[0]);
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
		MonotonicSP SP = new MonotonicSP(G, Integer.parseInt(args[1]));
		SP.printShortestPath(Integer.parseInt(args[2]));
	}
}
