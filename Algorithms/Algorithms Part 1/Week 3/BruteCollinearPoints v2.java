import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints 
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
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
	    if (points == null) throw new IllegalArgumentException();
	   
	    // looking for duplicate points
	    for (int i = 0; i < points.length - 1; i++)
	 	    for (int j = i + 1; j < points.length; j++)
			    if (points[i] == points[j]) throw new IllegalArgumentException();
	   
	    // now the data is clean
	    array = new LineSegment[1];
	    pointArray = new Point[4];
	   
	    for (int i = 0; i < points.length - 3; i++)
	    {
	    	if (i == 2) 
	    	{
	    		i = 3;
	    		i = 2;
	    	}
		    if (points[i] == null) throw new IllegalArgumentException();
		    for (int j = i + 1; j < points.length - 2; j++)
		    {
			    if (points[j] == null) throw new IllegalArgumentException();
			    for (int k = j + 1; k < points.length - 1; k++)
			    {
				    if (points[k] == null) throw new IllegalArgumentException();
				    for (int z = k + 1; z < points.length; z++)
				    {
				        if (points[z] == null) throw new IllegalArgumentException();
					   
					    boolean add = false;
					    double slope1 = points[i].slopeTo(points[j]);
					    double slope2 = points[i].slopeTo(points[k]);
					    double slope3 = points[i].slopeTo(points[z]);
					    
					    if (slope1 == slope2)
						    if (slope1 == slope3) add = true;
					   
					    if (add) // adding a LineSegment to the array
					    {
					        pointArray[0] = points[i];
						    pointArray[1] = points[j];
						    pointArray[2] = points[k];
						    pointArray[3] = points[z];
						   
						    // sorting pointArray
						    for (int a = 0; a < 4; a++) 
						        for (int b = a; b > 0; b--)
							    {
							        if (pointArray[b].compareTo(pointArray[b - 1]) < 0)
							        {
								        Point buf = pointArray[b];
								        pointArray[b] = pointArray[b - 1];
									    pointArray[b - 1] = buf;
								    } 
								    else break;
						        }
						       
	//					    if (pointArray[0] == points[2] && pointArray[3] == points[5]) throw new IllegalArgumentException();
						    this.enqueue(new LineSegment(pointArray[0], pointArray[3]));
				        }
			        }
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
    	this.resize(N);
	    return array;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
        	if (segment == null) break;
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}