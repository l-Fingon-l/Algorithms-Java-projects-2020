import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints 
{
	private LineSegment[] array;
	private Point[] pointArray;
	private int N;
	
	private void resize(int size)
	{
		LineSegment[] old = array;
		array = new LineSegment[size];
		for (int i = 0; i < N; i++) 
		{
			array[i] = old[i];
		}
		old = null;
	}
	
	private void enqueue(LineSegment item)
    {
    	if (item == null) throw new IllegalArgumentException();
    	
    	if (N == array.length) resize(2 * array.length);
		array[N++] = item;
    }
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static void merge(Point[] a, Point[] aux, int lo, int mid, int hi, Comparator<Point> comparator)
	{
		 for (int k = 0; k <= mid - lo; k++)
			 aux[k] = a[k + lo];
		 int i = 0, j = mid+1;
		 for (int k = lo; k <= hi; k++)
		 {
			 if (i > mid - lo) a[k] = a[j++];
			 else if (j > hi) a[k] = aux[i++];
			 else if (less(comparator, a[j], aux[i])) a[k] = a[j++];
			 else a[k] = aux[i++];
		 }
	}

    private static void sort(Point[] a, Point[] aux, int lo, int hi, Comparator<Point> comparator)
	{
	    if (hi <= lo) return;
	    int mid = lo + (hi - lo) / 2;
	    sort(a, aux, lo, mid, comparator);
	    sort(a, aux, mid+1, hi, comparator);
	    merge(a, aux, lo, mid, hi, comparator);
	}
    
    private static boolean less(Comparator<Point> comparator, Point v, Point w)
    { 
    	return comparator.compare(v, w) < 0; 
    }

	private void sort(Point[] a, Comparator<Point> comparator)
	{
		Point[] aux = new Point[a.length / 2 + 1];
	    sort(a, aux, 0, a.length - 1, comparator);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
	    if (points == null) throw new IllegalArgumentException();
	    pointArray = new Point[points.length];
	    if (points[0] == null) throw new IllegalArgumentException();

	   
        // looking for duplicate points
	    for (int i = 0; i < points.length - 1; i++)
	    {
	    	if (points[i] == null) throw new IllegalArgumentException();
	    	pointArray[i] = points[i];
	    	for (int j = i + 1; j < points.length; j++)
	 	    {
	 	        if (points[j] == null) throw new IllegalArgumentException();
	 	    	if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
	 	    }
	    }
	    pointArray[points.length - 1] = points[points.length - 1];
	    
	    int a = 0, b = 0;
	    Point beg, end; 
	    boolean exists = false;
	    
	    // now the data is clean
	    array = new LineSegment[1];
	    for (int i = 0; i < points.length; i++)
	    {
	    	this.sort(pointArray, points[i].slopeOrder());
	    	
	    	a = 0;
	    	while (a < points.length - 2)
	    	{
	    		exists = false;
	    		while (a < points.length - 2)
		    	{
		    		if (points[i].slopeTo(pointArray[a]) == points[i].slopeTo(pointArray[a + 1]))
		    			if (points[i].slopeTo(pointArray[a + 1]) == points[i].slopeTo(pointArray[a + 2]))
		    			{
		    				exists = true;
		    				break;
		    			}
		    		a++;
		    	}
		    	
		    	if (exists)
		    	{
		    		b = a;
		    		while (b < points.length - 2)
		    		{
		    			if (points[i].slopeTo(pointArray[b]) == points[i].slopeTo(pointArray[b + 1])
		    			&& points[i].slopeTo(pointArray[b + 1]) == points[i].slopeTo(pointArray[b + 2]))
		    				b++;
		    			else break;
		    		}
		    		
		    		beg = points[i];
		    		end = beg;
		    		for (int i1 = a; i1 < b + 2; i1++) // low point
		    			if (pointArray[i1].compareTo(beg) < 0) beg = pointArray[i1];
		    		
		    		if (points[i].compareTo(beg) == 0)
		    		{
		    			for (int i1 = a; i1 < b + 2; i1++) // high point
			    			if (pointArray[i1].compareTo(end) > 0) end = pointArray[i1];
		    			
		    			this.enqueue(new LineSegment(beg, end));
		    		}
		    		
		    		a = b + 2;
		    	}
	    	}
	    }
    }
   
    public int numberOfSegments()        // the number of line segments
    {
	    return N;
    }
   
    public LineSegment[] segments()                // the line segments
    {
    	LineSegment[] final_array = new LineSegment[N];
		for (int i = 0; i < N; i++) 
		{
			final_array[i] = array[i];
		}
    	
	    return final_array;
    }
    
    public static void main(String[] args)
    {
   	    // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        System.out.println("Calculation is done.");
        StdDraw.show(); 
    }
}