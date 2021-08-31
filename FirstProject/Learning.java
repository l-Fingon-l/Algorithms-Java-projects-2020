public class Learning
{
	private int[] roots;
	private int[] size;
	private int amount;
	
	public Learning(int n)
	{
		roots = new int[n];
		size = new int[n];
		amount = n;
		
		for (int i = 0; i < n; i++)
		{
			roots[i] = i;
			size[i] = 1;
		}
	}
	
	public int amount()
	{
		return amount;
	}
	
	public int root(int n)
	{
		while (roots[n] != n)
		{
			roots[n] = roots[roots[n]];
			n = roots[n];
		}
		return n;
	}
	
	public boolean connected(int a, int b)
	{
		return root(a) == root(b);
	}
	
	public void union(int a, int b)
	{
		int root_a = root(a);
		int root_b = root(b);
		if (root_a != root_b)
		{
			if (size[root_a] > size[root_b])
			{
				roots[root_b] = root_a;
				size[root_a] += size[root_b];
			}
			else
			{
				roots[root_a] = root_b;
				size[root_b] += size[root_a];
			}
			amount--;
		}
	}

	
	public static void main(String[] args) 
	{
		int n = 15;
		int date = 0, a = 0, b = 0;
		Learning WQUPC = new Learning(n);
		while (true)
		{
			date = StdIn.readInt();
			if (date == -1) break;
			a = StdIn.readInt();
			b = StdIn.readInt();
			WQUPC.union(a, b);
			if (WQUPC.amount() == 1) break;
		}
		StdOut.println("The date is: " + date);
		
/*		int n = 15, m = 150;
		for (int i = 0; i < m; i++)
		{
			StdOut.println(i + " " + (int)(Math.random() * n) + " " + (int)(Math.random() * n) + '\n');
		}
		StdOut.println(-1 + " "); */
	}
}