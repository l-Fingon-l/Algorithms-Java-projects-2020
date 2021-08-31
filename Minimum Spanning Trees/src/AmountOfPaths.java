import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

public class AmountOfPaths 
{
	public static void main(String[] args) 
	{
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		Topological topological = new Topological(G);
		int[] Amount = new int[G.V()];
		boolean start = false;
		Amount[Integer.parseInt(args[1])] = 1;
		for (int v: topological.order())
		{
			if (!start && v == Integer.parseInt(args[1])) start = true;
			if (start) if (v != Integer.parseInt(args[2]))
					for (int w: G.adj(v))
						Amount[w] += Amount[v];
			else break;
		}
		System.out.println("There are " + Amount[Integer.parseInt(args[2])] + " ways between the points given");
	}
}