import edu.princeton.cs.algs4.Digraph;

public class RootedDAGCheck 
{
	public String exception;
	public boolean isRootedDAG = false;
	private int amount = 0;
	private int size;
	
	public RootedDAGCheck(Digraph G)
	{
		size = G.V();
		
		ShortestDirectedCycle sdc = new ShortestDirectedCycle(G);
	    if (!sdc.acyclic()) 
	    {
	    	exception = "The hypernyms graph is not a DAG!";
	    	return;
	    }
	    
	    int root = root_dfs(0, G);
	    Digraph GRe = new Digraph(G.reverse());
	    boolean[] marked = new boolean[size];
	    dfs(root, marked, GRe);
	    if (amount != size) 
	    {
	    	exception = "The hypernyms DAG is not rooted!";
	    	return;
	    }
	    
	    isRootedDAG = true;
	}
	
	private void dfs(int v, boolean[] marked, Digraph G)
    {
        marked[v] = true;
		for (int w: G.adj(v))
	        if (!marked[w]) dfs(w, marked, G);
		amount++;
	}
	
    private int root_dfs(int v, Digraph G)
	{
	    for (int w: G.adj(v))
			return root_dfs(w, G);
		return v;
	}
}
