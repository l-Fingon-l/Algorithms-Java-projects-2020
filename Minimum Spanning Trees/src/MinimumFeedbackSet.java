import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.SET;

public class MinimumFeedbackSet 
{
	private EdgeWeightedGraph G;
	private boolean[] marked;
	private boolean[] cycle;
	private Stack<Edge> edgeTo;
	private SET<Edge> Set;
	
	public MinimumFeedbackSet(EdgeWeightedGraph G_)
	{
		G = G_;
		Set = new SET<Edge>();
		marked = new boolean[G.V()];
		cycle = new boolean[G.V()];
		edgeTo = new Stack<Edge>();
		for (int i = 0; i < G.V(); i++)
			if (!marked[i])
				DFS(i);		
	}
	
	private void DFS(int v)
	{
		marked[v] = cycle[v] = true;
		
		for (Edge e: G.adj(v))
			if (!marked[e.other(v)])
			{
				edgeTo.push(e);
				DFS(e.other(v));
				edgeTo.pop();
			}
			else if (cycle[e.other(v)] && edgeTo.peek() != e) 
			{
				edgeTo.push(e);
				addFeedbackEdge(v, e.other(v));
				edgeTo.pop();
			}
		cycle[v] = false;
	}
	
	private void addFeedbackEdge(int v, int w)
	{
		Edge min = edgeTo.peek();
		for (Edge e: edgeTo)
			if (v != w)
			{
				if (e.compareTo(min) < 0 && !Set.contains(e))
				{
					min = e;
					v = min.other(v);
				}
			}
			else break;
				
		Set.add(min);
	}
	
	public Iterable<Edge> edges()
	{
		 SET<Edge> result = new SET<>();
		 for (Edge e: Set)
			 result.add(e);
		 return result;
	}
	
	public static void main(String[] args)
	{
		In in = new In(args[0]);
		EdgeWeightedGraph G = new EdgeWeightedGraph(in);
		MinimumFeedbackSet mfs = new MinimumFeedbackSet(G);
		for (Edge e: mfs.edges())
			System.out.println(e);
	}
}
