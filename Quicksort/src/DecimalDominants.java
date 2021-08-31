import java.util.Random;

public class DecimalDominants 
{
	private static int partition(int[] a, int lo, int hi)
	{
	    if (hi <= lo) return lo;
		int lt = lo, gt = hi;
		int v = a[lo];
		int i = lo;
		while (i <= gt)
		{
			if (a[i] < v) exch(a, lt++, i++);
			else if (a[i] > v) exch(a, i, gt--);
			else i++;
		}
		
		return i;
	}
	
	private static void check(int[] a, int k, int length)
	{
		int lo = 0, hi = a.length - 1, j = 0;
		while (hi > lo)
		{
		    j = partition(a, lo, hi);
	     	if (j < k) lo = j + 1;
		    else if (j > k) hi = j - 1;
			else break;
		}
		
		lo = 0;
		hi = a.length - 1;
		
		int cmp = a[j];
		int len = 1, x = j;
		while (j > lo)
			if (cmp == a[--j]) 
				len++;
			else break;
		
		j = x;
		while (j < hi)
			if (cmp == a[++j]) 
				len++;
			else break;
		
		if (len > length) System.out.println(a[x]);
	}
	
	public static void printDecimalDominants(int[] a)
	{
		shuffle(a);
		
		int beg = a.length / 20;
		int length = a.length / 10;
		for (int i = 0; i < 9; i++)
		{
			check(a, beg, length);
		}
	}
	
	private static void exch(int[] a, int i, int j)
	{
		int buf = a[i];
		a[i] = a[j];
		a[j] = buf;
	}
	
	private static void shuffle(int[] a)
	{
		int N = a.length;
		Random rand = new Random(); 
		
		for(int i = 1; i < N; i++)
			exch(a, i, rand.nextInt(i));
	}
	
	public static void main(String[] args)
	{
		int[] array = {0, 3, 2, 3, 2, 1, 2, 4, 5, 6, 1, 8, 10};
		printDecimalDominants(array);
	}
}
