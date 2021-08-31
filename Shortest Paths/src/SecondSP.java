import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class SecondSP // this one is a slight modification of Sedgewick and Wayne's implementation of Dijkstra's algorithm
{
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private double[] distTo2;          // distTo[v] = distance  of shortest s->v path2
    private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private DirectedEdge[] edgeTo2;    // edgeTo[v] = last edge on shortest s->v path2
    private IndexMinPQ<Double> pq;    // priority queue of vertices

    public SecondSP(EdgeWeightedDigraph G, int s) 
    {
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }

        distTo = new double[G.V()];
        distTo2 = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        edgeTo2 = new DirectedEdge[G.V()];

        validateVertex(s);

        for (int v = 1; v < G.V(); v++)
            distTo[v] = distTo2[v] = Double.POSITIVE_INFINITY;
        distTo[s] = distTo2[s] = 0.0;

        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : G.adj(v))
                relax(e);
        }
    }

    private void relax(DirectedEdge e) 
    {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) 
        {
            if (distTo2[w] == Double.POSITIVE_INFINITY)
            {
            	distTo2[w] = distTo[v] + e.weight();
                edgeTo2[w] = e;
            }
            else
            {
            	distTo2[w] = distTo[w];
            	edgeTo2[w] = edgeTo[w];
            }
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            	
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
        else if (distTo[w] == distTo2[w] || distTo2[w] > distTo[v] + e.weight())
        {
        	distTo2[w] = distTo[v] + e.weight();
        	edgeTo2[w] = e;
        	if (!pq.contains(w)) pq.insert(w, distTo[w]);
        }
    }
    
    public double distTo(int v) {
        validateVertex(v);
        return distTo2[v];
    }

    public boolean hasSecondPathTo(int v) {
        validateVertex(v);
        if (distTo2[v] == distTo[v]) 
        	return false;
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) 
    {
        validateVertex(v);
        if (!hasSecondPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo2[v]; e != null; e = edgeTo2[e.from()]) {
            path.push(e);
        }
        return path;
    }

    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public static void main(String[] args) 
    {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        int s = Integer.parseInt(args[1]);

        // compute second shortest paths
        SecondSP sp = new SecondSP(G, s);

        // print second shortest path
        for (int t = 0; t < G.V(); t++) {
            if (sp.hasSecondPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                for (DirectedEdge e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no 2nd path\n", s, t);
            }
        }
    }
}
