import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class SkippableSP // this one is a slight modification of Sedgewick and Wayne's implementation of Dijksrta's algorithm
{
	private double[] distTo;          // distTo[v] = distance  of shortest s->v path
	private double[] max;
    private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices

    public SkippableSP(EdgeWeightedDigraph G, int s) 
    {
        distTo = new double[G.V()];
        max = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];

        validateVertex(s);

        for (int v = 1; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        pq = new IndexMinPQ<>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : G.adj(v))
                relax(e);
        }
    }

    private void relax(DirectedEdge e)
    {
        int w = e.to();
        
        if (distTo[w] > distTo(e)) 
        {
            distTo[w] = distTo(e);
            edgeTo[w] = e;
            max[w] = bigger(max[w], e.weight());
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }
    
    private double distTo(DirectedEdge e)
    {
    	int v = e.from();
    	if (max[v] > e.weight())
    		return distTo[v] + e.weight() ;
    	else return distTo[v] + max[v];
    }
    
    public double distTo(int v)
    {
    	 validateVertex(v);
         return distTo[v];
    }
    
    private double bigger(double x, double y)
    {
    	if (x > y) return x;
    	else return y;
    }
	
    private void validateVertex(int v) 
    {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
    
    public boolean hasPathTo(int v) 
    {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }
    
    public Iterable<DirectedEdge> pathTo(int v) 
    {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        boolean marked = false;
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) 
        {
        	if (!marked)
        		if (e.weight() == max[v])
        		{
        			marked = true;
        			path.push(new DirectedEdge(e.from(), e.to(), 0));
        		}
        		else path.push(e);
        	else path.push(e);
        }
        return path;
    }
	
	public static void main(String[] args) 
	{
		In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        int s = Integer.parseInt(args[1]);

        // compute skippable shortest paths
        SkippableSP sp = new SkippableSP(G, s);

        // print skippable shortest path
        int t = Integer.parseInt(args[2]);
        if (sp.hasPathTo(t)) 
        {
            StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
            for (DirectedEdge e : sp.pathTo(t)) 
            {
                StdOut.print(e + "   ");
            }
            StdOut.println();
        }
        else 
        {
            StdOut.printf("%d to %d         no skippable path\n", s, t);
        }
	}
}
