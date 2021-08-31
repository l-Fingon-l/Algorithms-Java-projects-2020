public class UFWithSpecificElementFind
{
	private int[] roots;
	private int[] size;
	private int[] biggest;
	private int amount;
	
	public UFWithSpecificElementFind(int n)
	{
		roots = new int[n];
		size = new int[n];
		biggest = new int[n];
		amount = n;
		
		for (int i = 0; i < n; i++)
		{
			roots[i] = i;
			size[i] = 1;
			biggest[i] = i;
		}
	}
	
	public int amount()
	{
		return amount;
	}
	
	public int find(int n)
	{
		return biggest[root(n)];
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
		int biggest_ = (biggest[root_a] > biggest[root_b]) ? biggest[root_a] : biggest[root_b];
		if (root_a != root_b)
		{
			if (size[root_a] > size[root_b])
			{
				roots[root_b] = root_a;
				size[root_a] += size[root_b];
				biggest[root_a] = biggest_;
			}
			else
			{
				roots[root_a] = root_b;
				size[root_b] += size[root_a];
				biggest[root_b] = biggest_;
			}
			amount--;
		}
	}
	
	
	
	public static void main(String[] args) 
	{
		int n = StdIn.readInt(), x = 55;
		int a = 0, b = 0;
		UFWithSpecificElementFind WQUPC = new UFWithSpecificElementFind(n);
		while (true)
		{
			a = StdIn.readInt();
			if (a == -1) break;
			b = StdIn.readInt();
			WQUPC.union(a, b);
		}
		StdOut.println("The biggest element in the component with " + x + " is: " + WQUPC.find(x));
	}

}
