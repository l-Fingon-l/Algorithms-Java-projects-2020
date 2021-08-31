public class AmericanFlagSort 
{
	private static class Entry // u may change an array of integers to array of Entries
	{
		public int key;
		public Object value;
		public Entry(int key, Object value) { this.key = key; this.value = value; }
	}
	
	public AmericanFlagSort(int[] a, int R)
	{
		// Initialise
		int N = a.length;
		int[] count = new int[R+1];
		int[] bound = new int[R];
		int buffer = -1;
		
		for (int i = 0; i < N; i++) // count frequencies
			count[a[i]+1]++;
		
		for (int r = 0; r < R; r++) // compute cumulates and boundaries
			bound[r] = count[r+1] += count[r];
		
		 for (int i = 0; i < N; i++) // move items
		 {
			 buffer = a[i];
			 
			 while (buffer != -1)
			 {
				 int id = count[buffer];
				 if (id == bound[buffer]) 
				 {
					 buffer = -1;
					 break;
				 }
				 else
				 {
					 int buf = a[id]; // swap a[id] and buffer
					 a[count[buffer]++] = buffer; // increment count
					 buffer = buf;
				 }
			 }	 
		 }
	}

	public static void main(String[] args) 
	{
		int[] array = { 0, 1, 3, 0, 3, 1, 2, 0, 2, 0, 3 };
		int R = 4;
		
		AmericanFlagSort sort = new AmericanFlagSort(array, R);
		
		for (int i = 0; i < array.length; i++)
			System.out.print(array[i] + " ");
	}
}
