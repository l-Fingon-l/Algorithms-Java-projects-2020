import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.PrimMST;

public class EdgeInMST // 2*E checks at max
{
	private EdgeWeightedGraph G;
	private boolean[] marked;
	
	public EdgeInMST(EdgeWeightedGraph G)
	{
		this.G = G;
		marked = new boolean[G.V()];
	}
	
	public boolean isInMST(Edge e)
	{
		int v = e.either();
		int w = e.other(v);
		for (int i = 0; i < G.V(); i++)
			marked[i] = false;
		marked[v] = marked[w] = true;
		Queue<Integer> q = new Queue<>(); 
		q.enqueue(v);
		
		while(!q.isEmpty())
		{
			int a = q.dequeue();
			for (Edge b: G.adj(a))
				if (b.compareTo(e) < 0)
					if (!marked[b.other(a)])
					{
						q.enqueue(b.other(a));
						marked[b.other(a)] = true;
					}
					else if (b != e && b.other(a) == w) return false;
		}
		
		return true;
	}
	
	public static void main(String[] args)
	{
		In in = new In(args[0]);
		EdgeWeightedGraph G = new EdgeWeightedGraph(in);
		
		EdgeInMST eim = new EdgeInMST(G);
		PrimMST mst = new PrimMST(G);
		for (Edge x: mst.edges())
			System.out.println(x.either() + "-" + x.other(x.either()) + (eim.isInMST(x) ? " YES" : " NO"));
		
		System.out.println();
		
		for (Edge x: G.adj(4))
			System.out.println(x.either() + "-" + x.other(x.either()) + (eim.isInMST(x) ? " YES" : " NO"));
	}
}
