import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class MaximumWeightClosure
{
	private Graph G;
	private int[] subset;
	private int[] weight;
	private int max = 0;
	private int max_id = -1;
	
	public MaximumWeightClosure(In in)
	{
		// init
		readGraph(in);
		subset = new int[G.V()];
		for (int i = 0; i < G.V(); i++)
			subset[i] = -1;
		
		// calculation of the sets
		for (int i = 0; i < G.V(); i++)
			if (subset[i] != -1)
			{
				DFS(i, i);
				if (weight[i] > max) 
				{
					max = weight[i];
					max_id = i;
				}
			}
	}
	
	private void DFS(int v, int source)
	{
		subset[v] = source;
		for (int w: G.adj(v))
			if (subset[w] != -1)
			{
				DFS(w, source);
				weight[source] += weight[w];
			}	
	}
	
	private void readGraph(In in)
	{
		if (in == null) throw new IllegalArgumentException("argument is null");
		int V = in.readInt();
		if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
		G = new Graph(V);
		weight = new int[V];
		try {
            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("number of edges in a Graph must be nonnegative");
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                weight[i] = in.readInt();
                validateVertex(v);
                validateVertex(w);
                G.addEdge(v, w); 
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
	}
	
	private void validateVertex(int v) {
        if (v < 0 || v >= G.V())
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (G.V()-1));
    }
	
	public Iterable<Integer> MaxSubset()
	{
		Stack<Integer> set = new Stack<>();
		for (int i = 0; i < G.V(); i++)
			if (subset[i] == max_id)
				set.push(i);
		
		return set;
	}
}
