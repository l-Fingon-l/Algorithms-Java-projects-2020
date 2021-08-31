import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> 
{
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    public Point(int x, int y) 
    {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    public void draw() 
    {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    public void drawTo(Point that)
    {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) 
    {
    	if (this.y == that.y) 
    		if (this.x == that.x) return Double.NEGATIVE_INFINITY;
    		else return +0.0;
    	if (this.x == that.x) return Double.POSITIVE_INFINITY;
    	
    	return ((double)that.y - this.y) / (that.x - this.x);
    }

    public int compareTo(Point that) 
    {
    	if (this.y < that.y) return -1;
		if (this.y > that.y) return 1;
		if (this.x < that.x) return -1;
		if (this.x > that.x) return 1;
		else return 0;
    }

    public Comparator<Point> slopeOrder()
    {
    	return new ByPoint();
    }
    
    private class ByPoint implements Comparator<Point>
    {
    	public int compare(Point a, Point b)
    	{
    		double slope_a = slopeTo(a), slope_b = slopeTo(b);
    		if (slope_a == Double.NEGATIVE_INFINITY)
    			if (slope_b != Double.NEGATIVE_INFINITY) return -1;
    		if (slope_b == Double.NEGATIVE_INFINITY)
        		if (slope_a != Double.NEGATIVE_INFINITY) return 1;
    		
    		if (slope_a == Double.POSITIVE_INFINITY)
    			if (slope_b != Double.POSITIVE_INFINITY) return 1;
    		if (slope_b == Double.POSITIVE_INFINITY)
    			if (slope_a != Double.POSITIVE_INFINITY) return -1;

    		if (slopeTo(a) > slopeTo(b)) return 1;
    		if (slopeTo(a) < slopeTo(b)) return -1;
    		else return 0;
    	}
    }

    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) 
    {
    	Point a = new Point(200, 300);
    	Point b = new Point(20, 40);
    	a.draw();
    	b.draw();
    	
    	System.out.println(0 < a.compareTo(b) ? "a is greater than b" : "b is greater than a");
    }
}