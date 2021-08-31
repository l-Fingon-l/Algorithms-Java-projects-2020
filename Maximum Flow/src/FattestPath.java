import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MaxPQ;

public class FattestPath 
{
	private final EdgeWeightedDigraph G;
	private DirectedEdge[] edgeTo;
	private double[] fat;
	private final int s;
	private final int t;
	
	private class edge implements Comparable<edge>
	{
		public double fat;
		public DirectedEdge edge;
		public edge(DirectedEdge edge, double fat) { this.edge = edge; this.fat = fat; }
		public int compareTo(edge that) {
	        return Double.compare(this.fat, that.fat);
		}
	}
	
	public FattestPath(EdgeWeightedDigraph G, int s, int t)
	{
		this.G = G;
		validateVertex(s);
		validateVertex(t);
		this.t = t;
		this.s = s;
		edgeTo = new DirectedEdge[G.V()];
		fat = new double[G.V()];
		
		MaxPQ<edge> pq = new MaxPQ<>();
		for (DirectedEdge e: G.adj(s))
			pq.insert(new edge(e, e.weight()));
		
		while (!pq.isEmpty())
		{
			edge x = pq.delMax();
			if (edgeTo[x.edge.to()] != null) continue;
			fat[x.edge.to()] = x.fat;
			edgeTo[x.edge.to()] = x.edge;
			if (x.edge.to() == t) break;

			for (DirectedEdge e: G.adj(x.edge.to()))
				if (edgeTo[e.to()] == null)
					pq.insert(new edge(e, Math.min(x.fat, e.weight())));
		}
	}
	
	public Iterable<DirectedEdge> path()
	{
		Stack<DirectedEdge> path = new Stack<>();
		for (DirectedEdge e = edgeTo[t]; e != null; e = edgeTo[e.from()])
			path.push(e);
		
		return path;
	}
	
	public void printFattestPath()
	{
		if (edgeTo[t] == null)
		{
			System.out.println("There is no fattest path to " + t);
			return;
		}
			
		StdOut.printf("%d to %d (%.2f): ", s, t, fat[t]);
		for (DirectedEdge e: path())
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
		FattestPath FP = new FattestPath(G, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		FP.printFattestPath();
	}
}
