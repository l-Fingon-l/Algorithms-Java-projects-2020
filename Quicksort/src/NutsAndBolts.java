import java.util.Random; 

public class NutsAndBolts 
{
	private static int partition(int[] a, int b, int lo, int hi)
	{
		int i = lo, j = hi;
		while (true)
		{
			while (less(a[i], b))
			{
				if (i == hi) break;
				i++;
			}
				
			
			while (less(b, a[j]))
			{
				if (lo == j) break;
				j--;
			}
				

			if (i >= j) break;
			exch(a, i, j);
		}
		return j;
	} 
	
	private static void exch(int[] a, int hi, int lo)
	{
		int buf = a[hi];
		a[hi] = a[lo];
		a[lo] = buf;
	}
	
	private static boolean less(int a, int b)
	{
		if (a < b) return true;
		else return false;
	}
	
	private static void shuffle(int[] a)
	{
		int N = a.length;
		Random rand = new Random(); 
		
		for(int i = 1; i < N; i++)
			exch(a, i, rand.nextInt(i));
	}
	
	private static void sort(int[] nuts, int[] bolts, int lo, int hi)
	{
		 if (hi <= lo) return;
		 int j = partition(nuts, bolts[lo], lo, hi);
		 partition(bolts, nuts[j], lo, hi);
		 
		 sort(nuts, bolts, lo, j-1);
		 sort(nuts, bolts, j+1, hi);
	}
	
	public void SortNutsAndBolts(int[] nuts, int[] bolts)
	{
		shuffle(nuts);
		shuffle(bolts);
		sort(nuts, bolts, 0, nuts.length - 1);
	}
	
	public static void main(String[] args)
	{
		int[] nuts = {-50, -20, -33, 4, 55, 666, 7, -8, 23, 19};
		int[] bolts = {-33, 666, 19, -8, -50, 7, 4, -20, 55, 23};
		int N = nuts.length;
		
		NutsAndBolts task1 = new NutsAndBolts();
		task1.SortNutsAndBolts(nuts, bolts);
		
		System.out.println("Nuts:");
		for (int i = 0; i < N; i++)
			System.out.println(nuts[i]);
		
		System.out.println("Bolts:");
		for (int i = 0; i < N; i++)
			System.out.println(bolts[i]);
	}
}
