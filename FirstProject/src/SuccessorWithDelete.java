public class SuccessorWithDelete 
{
	private int[] roots;
	private int[] size;
	private int[] biggest;
	private int amount;
	private int max;
	private boolean max_crossed;
	
	public SuccessorWithDelete(int n)
	{
		roots = new int[n];
		size = new int[n];
		biggest = new int[n];
		amount = n;
		max = n - 1;
		max_crossed = false;
		
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
	
	public int FindTheSuccessor(int n)
	{
		int result = biggest[root(n)];
		if (result == max && max_crossed) return -1;
		return result;
	}
	
	private int root(int n)
	{
		while (roots[n] != n)
		{
			roots[n] = roots[roots[n]];
			n = roots[n];
		}
		return n;
	}
	
	private void union(int a, int b)
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
	
	public void remove(int x)
	{
		if (x == max)
		{
			max_crossed = true;
			return;
		}
		union(x, x + 1);
	}
}
