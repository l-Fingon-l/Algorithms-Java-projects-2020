import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import java.util.Stack;

public class ShortestDirectedCycle 
{
	private int[] path; 
	private int[] v_depth;
	private int[] id;
	private int[] smallestCycle = {Integer.MAX_VALUE , -1}; // 1st int is it's length; 2nd is one of it's elements
	private Stack<Integer> Path;
	
	public ShortestDirectedCycle(Digraph G)
	{
		int size = G.V();
		path = new int[size];
		v_depth = new int[size];
		id = new int[size];
		
		for (int i = 0; i < size; i++)
			path[i] = id[i] = -1;
		
		for (int i = 0; i < size; i++)
			if (path[i] == -1)
			{
				path[i] = id[i] = i;
				v_depth[i] = 0;
				DFS(i, 0, i, G);
			}
	}
	
	private void DFS(int v, int depth, int id_, Digraph G)
	{
		for (int w: G.adj(v))
		{
			if (path[w] == -1) // new vertex
			{
				path[w] = v;
				v_depth[w] = depth + 1;
				id[w] = id_;
				DFS(w, depth + 1, id_, G);
				id[w] = w;
			}
			else
			{
				if (id[w] != id_) continue; // in case it's an another branch of a graph
				int length = depth + 1 - v_depth[w];
				if (length < smallestCycle[0])
				{
					smallestCycle[0] = length;
					smallestCycle[1] = w;
					path[w] = v;
				}
			}
		}	
	}
	
	public boolean acyclic()
	{
		return smallestCycle[0] == Integer.MAX_VALUE;
	}
	
	public void printShortestCycle()
	{
		if (acyclic()) 
		{
			System.out.println("The graph is acyclic!");
			return;
		}
		Path = new Stack<Integer>();
		Path.add(0);
		Path.add(smallestCycle[1]);
		for (int i = path[smallestCycle[1]]; i != smallestCycle[1]; i = path[i])
			Path.add(i);
		Path.add(smallestCycle[1]);
		System.out.print(Path.pop());
		for (int i = (int) Path.pop(); !Path.isEmpty(); i = (int) Path.pop())
			System.out.print("-" + i);
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
			
		ShortestDirectedCycle sdc = new ShortestDirectedCycle(G);
		sdc.printShortestCycle();
	}
}
