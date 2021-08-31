import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import java.util.Stack;
import java.util.Vector;

public class EulerCycle 
{
	private Vector<Integer>[] adj;
	private int[] edge_counter;
	private boolean isEulerian = true;
	
	public EulerCycle(Graph G) 
	{
		int V = G.V(), degree = 0;
		adj = new Vector[V];
		edge_counter = new int[V];
		for (int i = 0; i < V; i++)
		{
			adj[i] = new Vector<Integer>();
			for (int w: G.adj(i))
				adj[i].add(w);
			degree = G.degree(i);
			if (degree % 2 == 1) 
			{
				isEulerian = false;
				break;
			}
			edge_counter[i] = degree;
		}
	}
	
	public boolean isEulerian()
	{
		return isEulerian;
	}
	
	public void printEulerianCycle()
	{
		if (!isEulerian)
		{
			System.out.println("The graph does not have an Eulerian cycle.");
			return;
		}
		
		Stack<Integer> path = new Stack<Integer>();
		Stack<Integer> cycle = new Stack<Integer>();
		int curr = 0, next = 0;
		path.add(0);
		
		while (!path.isEmpty())
		{
			if (edge_counter[curr] != 0)
			{
				path.add(curr);
				next = adj[curr].firstElement();
				
				adj[curr].removeElementAt(0);
				adj[next].removeElement(curr);
				edge_counter[curr]--;
				edge_counter[next]--;
				
				curr = next;
			}
			else
			{
				cycle.add(curr);
				curr = path.lastElement();
				path.pop();
			}
		}
		
		for (int i = cycle.size() - 1; i > -1; i--)
		{
			System.out.print(cycle.pop());
			if (!cycle.isEmpty())
				System.out.print(" - ");
		}
	}

	public static void main(String[] args)
	{
		In in = new In(args[0]);
		Graph G = new Graph(in);
		
		EulerCycle EC = new EulerCycle(G);
		if (EC.isEulerian())
		{
			System.out.println("The graph does an Eulerian cycle.");
			EC.printEulerianCycle();
		}
		else System.out.println("The graph does not have an Eulerian cycle.");
	}
}
