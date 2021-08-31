import java.util.Stack;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class HamiltonianPathInDigraph 
{
	private boolean hasHamiltonian = true;
	private boolean[] marked;
	private int marked_ = 0;
	private Stack<Integer> path;
	
	public HamiltonianPathInDigraph(Digraph G)
	{
		int size = G.V();
		marked = new boolean[size];	

		path = new Stack<Integer>();
		DFS(0, G);
		if (marked_ != size) hasHamiltonian = false;
		Stack<Integer> copy = (Stack<Integer>) path.clone();
		for (int i = path.pop(), stop = 0; !path.isEmpty();)
		{
			int v = path.pop();
			stop = 1;
			for(int w: G.adj(i))
				if (w == v) stop = 0;
			i = v;
			
			if (stop == 1) 
			{
				hasHamiltonian = false;
				break;
			}
		}
		if (hasHamiltonian) path = (Stack<Integer>) copy.clone();
	}
	
	private void DFS(int v, Digraph G)
	{
		marked[v] = true;
		marked_++;
		for (int w: G.adj(v))
			if (marked[w] == false) DFS(w, G);
		path.add(v);
	}
	
	public boolean hasHamiltonian()
	{
		return hasHamiltonian;
	}
	
	public void printHamiltonianPath()
	{
		if (!hasHamiltonian)
		{
			System.out.println("The graph does not have a Hamiltonian path.");
			return;
		}
		
		System.out.print(path.pop());
		do System.out.print("-" + path.pop());
		while(!path.isEmpty());	
	}
	

	public static void main(String[] args) 
	{
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		for (int i = 0; i < G.V(); i++)
		{
		for (int w: G.adj(i))
			System.out.println(i + "-" + w + " ");
		}
		System.out.println();
			
		HamiltonianPathInDigraph hpd = new HamiltonianPathInDigraph(G);
		hpd.printHamiltonianPath();
	}
}
