public class IntersectionOfTwoSets 
{
	private point2D[] a;
	private point2D[] b;
	private int N;
	
	public IntersectionOfTwoSets (point2D[] a_, point2D[] b_)
	{
		a = a_;
		b = b_;
		N = a.length;
	}
	
	public int CountIntersections ()
	{
		sort(a);
		sort(b);
		
		int number = 0, i = 0, j = 0;
		
		while (i < N && j < N)
		{
			if (less(a[i], b[j])) i++;
			else if (less(b[j], a[i])) j++; 
			else
			{
				number++;
				i++;
			}
		}

		return number;
	}
		
	private void sort(point2D[] a)
    {
	    int N = a.length;
		int h = 1;
		while (h < N/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, ...
		while (h >= 1)
		{ 
		    // h-sort the array.
			for (int i = h; i < N; i++)
				for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)
					 exch(a, j, j-h);
			 h = h/3;
		}
	}
	
	private boolean equal(point2D a, point2D b)
	{
		return ((a.x == b.x) && (a.y == b.y)) ? true : false;
	}
	
	private static boolean less(point2D v, point2D w)
	 {
		if (v.x < w.x) return true;
		if (v.x > w.x) return false;
		if (v.y < w.y) return true;
		else return false;
	 }
	
	 private static void exch(point2D[] a, int i, int j)
	 { 
		 point2D swap = a[i];
		 a[i] = a[j];
		 a[j] = swap;
	 }
	
	public static class point2D
	{
		public int x;
		public int y;
		public point2D (int x_, int y_)
		{
			x = x_;
			y = y_;
		}
	}
	
	public static void main(String[] args)
	{
		point2D[] a = new point2D[7];
		point2D[] b = new point2D[7];
		a[0] = new point2D(-246, -444);
		a[1] = new point2D(274, 1);
		a[2] = new point2D(2, 1);
		a[3] = new point2D(24, 2);
		a[4] = new point2D(2, 4);
		a[5] = new point2D(86, 0);
		a[6] = new point2D(0, 14);
		
		b[0] = new point2D(0, 1);
		b[1] = new point2D(4, 1);
		b[2] = new point2D(7, 13);
		b[3] = new point2D(2, 4);
		b[4] = new point2D(3, 3);
		b[5] = new point2D(564, 0);
		b[6] = new point2D(-246, -444);
		
		IntersectionOfTwoSets task1 = new IntersectionOfTwoSets(a, b);
		
		System.out.println("There are " + task1.CountIntersections() + " intersections in the arrays.");
	}
}
