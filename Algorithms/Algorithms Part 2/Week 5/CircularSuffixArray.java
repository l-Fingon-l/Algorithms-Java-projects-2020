public class CircularSuffixArray 
{
	private int[] index;
	private String s;
	private final int n;
	
    // circular suffix array of s
    public CircularSuffixArray(String s)
    {
    	if (s == null) throw new IllegalArgumentException("The input string is null!");
    	this.s = s;
    	n = s.length();
    	index = new int[n];
    	
    	for (int i = 0; i < n; i++)
    		index[i] = i;
    	
    	sort(0, n - 1, 0); 
    }

    // length of s
    public int length()
    {
    	return n;
    }

    // returns index of ith sorted suffix
    public int index(int i)
    {
    	if (i < 0 || i >= n) throw new IllegalArgumentException(i + " is outside the range of the string!");
    	return index[i];
    }
    
    // helper functions for suffix array sorting
    
    private int charAt(int i, int d)
	{
		if (d < n) return s.charAt((i + d) % n); 
		else return -1; 
	}
    
    private void exch(int x, int y)
    {
    	int buf = index[x];
    	index[x] = index[y];
    	index[y] = buf;
    }
    
	private void sort(int lo, int hi, int d)
	{
		if (hi <= lo) return;
		int lt = lo, gt = hi;
		int v = charAt(index[lo], d);
		int i = lo + 1;
		while (i <= gt)
		{
			int t = charAt(index[i], d);
			if (t < v) exch(lt++, i++);
			else if (t > v) exch(i, gt--);
			else i++;
		}
		
		// a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]
		sort(lo, lt-1, d);
		if (v >= 0) sort(lt, gt, d+1);
		sort(gt+1, hi, d);
	}
	
    // unit testing (required)
    public static void main(String[] args)
    {
    	String s = "ABRACADABRA!";
    	CircularSuffixArray csa = new CircularSuffixArray(s);
    	for (int i = 0; i < csa.length(); i++)
    		System.out.println(csa.index(i));
    }
}