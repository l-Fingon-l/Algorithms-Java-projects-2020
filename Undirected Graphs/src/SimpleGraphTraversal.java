import java.util.Random;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

public class SimpleGraphTraversal 
{
	public boolean[] marked;
	private int[] edgeTo;
	private int beg;
	private int d = -1; // diameter
	
	public SimpleGraphTraversal (Graph G)
	{
		Random rand = new Random(); 
		int s = rand.nextInt(G.V());
		
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		int beg_ = bfs(G, s);
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		beg = bfs(G, beg_);
	}
	
	private int bfs (Graph G, int s)
	{
		Queue<Integer> q = new Queue<Integer>();
		q.enqueue(s);
		marked[s] = true;
		edgeTo[s] = s;
		int end = s;
		while(!q.isEmpty())
		{
			int v = q.dequeue();
			for (int w: G.adj(v))
			{
				if (!marked[w])
				{
					q.enqueue(w);
					end = w;
					marked[w] = true;
					edgeTo[w] = v;
				}
			}
		}
		return end;
	}
	
	public void Diameter()
	{
		int i = beg, way = 0;
		while (i != edgeTo[i])
		{
			System.out.print(i + " - ");
			i = edgeTo[i];
			way++;
		}
		System.out.println(i + ".");
		d = way;
	}
	
	public int Centre()
	{
		int way = 0, i = beg; // getting the length
		if (d != -1) way = d / 2;
		else 
		{
			while (i != edgeTo[i])
			{
				i = edgeTo[i];
				way++;
			}
			i = beg;
			way /= 2;
		}
		
		while (way > 0) // getting to the centre
		{
			i = edgeTo[i];
			way--;
		}
		
		return i;
	}
	
	public static void main(String[] args)
	{
		In in = new In(args[0]);
		Graph G = new Graph(in);
		SimpleGraphTraversal sgt = new SimpleGraphTraversal(G);
		for (int v = 0; v < G.V(); v++)
			if (sgt.marked[v])
				System.out.println(v + " ");
		System.out.println();
		sgt.Diameter();
		System.out.println("The centre is at: " + sgt.Centre());
	}
}
