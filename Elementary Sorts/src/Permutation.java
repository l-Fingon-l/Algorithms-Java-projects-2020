public class Permutation 
{
    private int[] a;
	private int[] b;
	private int N;
	
	public Permutation(int[] a_, int[] b_)
	{
		a = a_;
		b = b_;
		N = a.length;
	}
	
	public boolean arePermutations()
	{
		if (a.length != b.length) return false;
		sort(a);
		sort(b);
		for (int i = 0; i < N; i++)
			if (a[i] != b[i]) return false;
		return true;
	}
	
	private static void sort(int[] a)
    {
	    int N = a.length;
		int h = 1;
		while (h < N/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, ...
		while (h >= 1)
		{ 
		    // h-sort the array.
			for (int i = h; i < N; i++)
				for (int j = i; j >= h && less_x(a[j], a[j-h]); j -= h)
					 exch(a, j, j-h);
			h = h/3;
	    }
	}
	
	private static boolean less_x(int v, int w)
	{
	    return v < w; 
	}
	
	private static void exch(int[] a, int i, int j)
	{ 
        int swap = a[i];
	    a[i] = a[j];
	    a[j] = swap;
	}
	
	public static void main(String[] args)
    {
		int[] a = {1, 2, 3, 4, 5};
		int[] b = {1, 3, 4, 2, 5};
		Permutation task2 = new Permutation(a, b);
		System.out.println(task2.arePermutations());
		
		int[] c = {1, 2, 3, 6, 5};
		int[] d = {1, 3, 4, 2, 5};
		Permutation task2_1 = new Permutation(c, d);
		System.out.print(task2_1.arePermutations());
    }
}
