import edu.princeton.cs.algs4.Graph;
import java.util.Stack;
import edu.princeton.cs.algs4.In;
import java.util.Iterator;

public class NonrecursiveDFS 
{
	private boolean[] marked;
	private int count;
	
	public NonrecursiveDFS (Graph G, int s)
	{
		marked = new boolean[G.V()];
		validateVertex(s);
		
		Stack<Layer> stack = new Stack<Layer>(); 
		stack.push(new Layer(G.adj(s)));
		marked[s] = true;
		
		while (!stack.isEmpty())
		{
			if (!stack.lastElement().adj.hasNext())
			{
				stack.pop();
				count++;
				continue;
			}
			
			s = stack.lastElement().adj.next();
			validateVertex(s);
			
			if (!marked[s])
			{
				marked[s] = true;
				stack.push(new Layer(G.adj(s)));
			}
		}
	}
	
	private class Layer
	{
		public Iterator<Integer> adj;
		public Layer(Iterable<Integer> adjacent)
		{
			adj = adjacent.iterator();
		}
	}
	
	public boolean marked (int v)
	{
		validateVertex(v);
		return marked[v];
	}
	
	public int count()
	{
		return count;
	}
	
	private void validateVertex (int v)
	{
		int V = marked.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}
	
	
	public static void main(String[] args)
	{
		In in = new In(args[0]);
		Graph G = new Graph(in);
		int s = Integer.parseInt(args[1]);
		NonrecursiveDFS dfs = new NonrecursiveDFS(G, s);
		for (int v = 0; v < G.V(); v++)
			if (dfs.marked(v))
				System.out.println(v + " ");
		System.out.println();
	}
}
