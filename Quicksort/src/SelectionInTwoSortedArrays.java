public class SelectionInTwoSortedArrays 
{
	private static int Kth(int[] a, int[] b, int a_beg, int b_beg, int a_end, int b_end, int k)
	{
		if (a_beg == a_end) return b[b_beg + k];
		if (b_beg == b_end) return a[a_beg + k];
		
		int middle1 = (a_end - a_beg) / 2;
		int middle2 = (b_end - b_beg) / 2;
		
		if (middle1 + middle2 < k)
		{
			if (a[a_beg + middle1] > b[b_beg + middle2])
				return Kth(a, b, a_beg, b_beg + middle2 + 1, a_end, b_end, k - middle2 - 1);
			else 
				return Kth(a, b, a_beg + middle1 + 1, b_beg, a_end, b_end, k - middle1 - 1);
		}
		else
		{
			if (a[a_beg + middle1] > b[b_beg + middle2])
				return Kth(a, b, a_beg, b_beg, a_beg + middle1, b_end, k);
			else 
				return Kth(a, b, a_beg, b_beg, a_end, b_beg + middle2, k);
		}
	}
	
	private static int Kth(int[] a, int[] b, int k)
	{
		int a_beg = 0, b_beg = 0;
		int a_end = a.length, b_end = b.length;
		return Kth(a, b, a_beg, b_beg, a_end, b_end, k - 1);
	}
	
	public static void main(String[] args)
	{
	    int[] a = {1, 2, 4, 5, 7, 9};
		int[] b = {3, 6, 8, 10};
		int k = 7;
		
		System.out.print(Kth(a, b, k));
	}
}
