import edu.princeton.cs.algs4.SET;
import java.util.Iterator;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET 
{
    private SET<Point2D> BST;
	
    public PointSET()                               // construct an empty set of points 
    {
 	    BST = new SET<Point2D>();
    }
   
    public boolean isEmpty()                      // is the set empty? 
    {
        return BST.isEmpty();
    }
   
    public int size()                         // number of points in the set 
    {
	    return BST.size();
    }
   
    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
    	if (p == null) throw new IllegalArgumentException();
	    if (!BST.contains(p)) BST.add(p);
    }
   
    public boolean contains(Point2D p)            // does the set contain point p? 
    {
    	if (p == null) throw new IllegalArgumentException();
	    return BST.contains(p);
    }
   
    public void draw()                         // draw all points to standard draw 
    {
        for (Point2D point: BST)
		    point.draw();
    }
   
    public Iterable<Point2D> range(RectHV rect)  // all points that are inside the rectangle (or on the boundary) 
    {
    	if (rect == null) throw new IllegalArgumentException();
    	return new Points(rect);
    }
    
    private class Points implements Iterable<Point2D>
    {
    	public SET<Point2D> bst;
    	
    	public Points(RectHV rect)
    	{
    		bst = new SET<Point2D>();
    		for (Point2D point: BST)
    			if (rect.contains(point)) bst.add(point);
    	}

		public Iterator<Point2D> iterator() 
		{
			return bst.iterator();
		}
    }
    
    public Point2D nearest(Point2D p)   // a nearest neighbor in the set to point p; null if the set is empty 
    {
    	if (p == null) throw new IllegalArgumentException();
    	if (BST.isEmpty()) return null;
    	Point2D nearest = null;
    	double dis1 = 10, dis2;
    	for (Point2D point: BST)
    	{
    		dis2 = point.distanceTo(p);
    		if (dis2 < dis1)
    		{
    			nearest = point;
    			dis1 = dis2;
    		}
    	}
    	return nearest;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
    	
    }
}