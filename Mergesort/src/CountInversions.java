public class CountInversions
{
	private static int amount = 0;
	
	private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi)
	{
		 for (int k = 0; k <= mid - lo; k++)
			 aux[k] = a[k + lo];
		 int i = 0, j = mid+1;
		 for (int k = lo; k <= hi; k++)
		 {
			 if (i > mid - lo) a[k] = a[j++];
			 else if (j > hi) a[k] = aux[i++];
			 else if (less(a[j], aux[i])) 
			 {
				 amount += j - k;
				 a[k] = a[j++];
			 }
			 else a[k] = aux[i++];
		 }
	}

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi)
	{
	    if (hi <= lo) return;
	    int mid = lo + (hi - lo) / 2;
	    sort(a, aux, lo, mid);
	    sort(a, aux, mid+1, hi);
	    merge(a, aux, lo, mid, hi);
	}
    
    private static boolean less(Comparable v, Comparable w)
    { 
    	return v.compareTo(w) < 0; 
    }
    
    private static boolean equal(Comparable v, Comparable w)
    {
    	return v.compareTo(w) == 0;
    }

	public static void sort(Comparable[] a)
	{
	    Comparable[] aux = new Comparable[a.length / 2 + 1];
	    sort(a, aux, 0, a.length - 1);
	}
	
	public int Count(Comparable[] a)
	{
		int N = a.length, low = 0, middle = 0, high = N - 1;
		sort(a);
		
		return amount;
	}
	
	public static void main(String[] args)
	{
		CountInversions task2 = new CountInversions();
		Comparable[] a = new Comparable[6];
		a[0] = 60;
		a[1] = 4;
		a[2] = 2;
		a[3] = 5;
		a[4] = 10; 
		a[5] = 9;
		
		System.out.println("There are " + task2.Count(a) + " inversions in the array.");
	}
}
