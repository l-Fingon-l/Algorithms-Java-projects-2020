import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class MySP
{
	private EdgeWeightedDigraph G;
	private double[] distTo;
	private DirectedEdge[] edgeTo;
	private char[] c;
	private int s;
	
	private class pair implements Comparable<pair>
	{
		public DirectedEdge edge;
		public double length;
		public pair(DirectedEdge edge, double length) { this.edge = edge; this.length = length; }
		public int compareTo(pair that) {
	        return Double.compare(this.length, that.length);
		}
	}
	
	public MySP(EdgeWeightedDigraph G_, char s_)
	{
		G = G_;
		s = s_ - 'A';
		validateVertex(s);
		distTo = new double[G.V()];
		edgeTo = new DirectedEdge[G.V()];
		c = new char[G.V()];
		for (int i = 'A'; i < G.V() + 'A'; i++)
			c[i - 'A'] = (char)i;
		MinPQ<pair> pq = new MinPQ<>();
		
		for (DirectedEdge e: G.adj(s))
			pq.insert(new pair(e, e.weight()));
		edgeTo[s] = null;
		
		while (!pq.isEmpty())
		{
			pair x = pq.delMin();
			if (edgeTo[x.edge.to()] != null) continue;
			distTo[x.edge.to()] = x.length;
			edgeTo[x.edge.to()] = x.edge;

			for (DirectedEdge e: G.adj(x.edge.to()))
				if (edgeTo[e.to()] == null)
					pq.insert(new pair(e, x.length + (int)e.weight()));
		}
	}
	
	public Iterable<DirectedEdge> ShortestPathTo(char v_)
	{
		int v = v_ - 'A';
		validateVertex(v);
		Stack<DirectedEdge> path = new Stack<>();
		for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
			path.push(e);
		
		return path;
	}
	
	public double distTo(char v)
	{
		validateVertex(v);
		return distTo[v - 'A'];
	}
	
	public void printShortestPath(char v)
	{
		validateVertex(v);
		if (edgeTo[v] == null)
		{
			System.out.println("There is no path to " + v);
			return;
		}
		
		StdOut.printf("%c to %c (%.2f): ", s + 'A', v, distTo(v));
		for (DirectedEdge e: ShortestPathTo(v))
			StdOut.print(c[e.from()] + "->" + c[e.to()] + "" + String.format("%5.2f", e.weight()) + "  ");
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
		MySP SP = new MySP(G, args[1].charAt(0));
		SP.printShortestPath(args[2].charAt(0));
	}
}
