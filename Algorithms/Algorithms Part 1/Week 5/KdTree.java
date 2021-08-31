import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Iterator;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree 
{
	private Tree2D kdtree;
	private int N;
	
	private class Tree2D
	{
		private Node root;
		private boolean dir; // direction
		
		private class Node
		{
		    private Point2D point;
			private Node left, right;
			
			public Node(Point2D p)
			{
				point = p;
			}
		}
		
		public void add(Point2D p)
		{ 
			dir = true;
		    root = put(root, p); 
		}
		
		private Node put(Node n, Point2D p)
		{
			if (n == null) return new Node(p);
			
			int cmp;
			if (dir) 
			{
				if (p.x() < n.point.x()) cmp = -1;
				else cmp = 1;
			}
			else
			{
				if (p.y() < n.point.y()) cmp = -1;
				else cmp = 1;

			}
			dir = !dir;
			
			if (cmp < 0) n.left = put(n.left, p);
		    else if (cmp > 0) n.right = put(n.right, p);
//		    else if (cmp == 0) throw new IllegalArgumentException("2 non-distinct points!");
			///////////////////////////////////////////////////// here if 2 points are equal
		    return n;
		}
		
		public Point2D get(Point2D p)
		{
			Node n = root;
			dir = true;
			int cmp;
			while (n != null)
			{
				if (dir) 
				{
					double x2 = n.point.x();
					double x1 = p.x();
					if (x1 < x2) cmp = -1;
					else if (p.y() == n.point.y() && x1 == x2) cmp = 0;
					else cmp = 1;
				}
				else
				{
					double y1 = p.y();
					double y2 = n.point.y();
					if (y1 < y2) cmp = -1;
					else if (p.x() == n.point.x() && y1 == y2) cmp = 0;
					else cmp = 1;
				}
				dir = !dir;
				if (cmp < 0) n = n.left;
				else if (cmp > 0) n = n.right;
				else return n.point;
			}
			return null;
		}
		
		public boolean contains(Point2D p)
		{
			return get(p) != null;
		}
		
		public boolean isEmpty()
		{
			return root == null;
		}
		
		public void draw()
		{
			draw(root, true, 0, 1, 0, 1);
		}
		
		private void draw(Node n, boolean color, double a, double b, double c, double d)
	    {
	    	if (n == null) return;
	    	
	    	StdDraw.setPenRadius(0.03);
	    	StdDraw.setPenColor();
	    	StdDraw.point(n.point.x(), n.point.y());
	    	if (color)
	    	{
	    		StdDraw.setPenRadius();
	    		StdDraw.setPenColor(StdDraw.RED);
	    		StdDraw.line(n.point.x(), c, n.point.x(), d);
	    		
	    		draw(n.left, !color, a, n.point.x(), c, d);
	    		draw(n.right, !color, n.point.x(), b, c, d);
	    	}
	    	else
	    	{
	    		StdDraw.setPenRadius();
	    		StdDraw.setPenColor(StdDraw.BLUE);
	    		StdDraw.line(a, n.point.y(), b, n.point.y());
	    		
	    		draw(n.left, !color, a, b, c, n.point.y());
	    		draw(n.right, !color, a, b, n.point.y(), d);
	    	}
	    	return;
	    }
		
		public Iterable<Point2D> RangePoints(RectHV rect)
		{
			return new Points(rect);
		}
		
		private class Points implements Iterable<Point2D>
	    {
	    	public SET<Point2D> bst;
	    	private RectHV rect;
	    	private RectHV cmp;
	    	
	    	public Points(RectHV rect)
	    	{
	    		bst = new SET<Point2D>();
	    		this.rect = rect;
	    		findPoints(root, true, 0, 1, 0, 1);
	    	}

	    	public void findPoints(Node n, boolean color, double a, double b, double c, double d)
	    	{
	    		if (n == null) return;	    		
	    		
	    		cmp = new RectHV(a, c, b, d);	 
	    		if (!rect.intersects(cmp)) return;
	    		
	    		if (rect.contains(n.point)) 
	    			if (!bst.contains(n.point))
	    				bst.add(n.point);
	    		
	    		if (color)
				{
	    			findPoints(n.left, !color, a, n.point.x(), c, d);
		    		findPoints(n.right, !color, n.point.x(), b, c, d);
				}
	    		else
	    		{
	    		    findPoints(n.left, !color, a, b, c, n.point.y());
		    		findPoints(n.right, !color, a, b, n.point.y(), d);
	    		}
	    		return;
	    	}
	    	
			public Iterator<Point2D> iterator() 
			{
				return bst.iterator();
			}
	    }
		
		public Point2D closest(Point2D p)
		{
			closestTo search = new closestTo(p);
			return search.closest();
		}
		
		private class closestTo
		{
			private Point2D closestPoint;
			private Point2D p;
			private double MinDistance;
			private RectHV cmp;
			
			public closestTo(Point2D p)
			{
				MinDistance = 10;
				this.p = p;
			}
			
			public Point2D closest()
			{
				closest(root, true, 0, 1, 0, 1);
				return closestPoint;
			}
			
			private void closest(Node n, boolean color, double a, double b, double c, double d)
			{
				if (n == null) return;
				
				double distance = n.point.distanceTo(p);
				
				if (distance < MinDistance)
				{
					if (distance == 0)
					{
						closestPoint = n.point;
						MinDistance = 0;
						return;
					}
					
					closestPoint = n.point;
					MinDistance = distance;
				}
				
				if (color)
				{
					if (p.x() < n.point.x())
					{
						closest(n.left, !color, a, n.point.x(), c, d);
						a = n.point.x();
						
						cmp = new RectHV(a, c, b, d);
						if (MinDistance < cmp.distanceTo(p)) return;
						
						closest(n.right, !color, a, b, c, d);
						return;
					}
					else
					{
						closest(n.right, !color, n.point.x(), b, c, d);
						b = n.point.x();
						
						cmp = new RectHV(a, c, b, d);
						if (MinDistance < cmp.distanceTo(p)) return;
						
						closest(n.left, !color, a, b, c, d);
						return;
					}
				}
				else
				{
					if (p.y() < n.point.y())
					{
						closest(n.left, !color, a, b, c, n.point.y());
						c = n.point.y();

						cmp = new RectHV(a, c, b, d);
						if (MinDistance < cmp.distanceTo(p)) return;
						
						closest(n.right, !color, a, b, c, d);
						return;
					}
					else
					{
						closest(n.right, !color, a, b, n.point.y(), d);
						d = n.point.y();
						
						cmp = new RectHV(a, c, b, d);
						if (MinDistance < cmp.distanceTo(p)) return;
						
						closest(n.left, !color, a, b, c, d);
						return;
					}
				}
			}
		}
	}

    public KdTree() // construct an empty set of points 
    {
    	kdtree = new Tree2D();
    }
    
    public boolean isEmpty() // is the set empty? 
    {
    	return kdtree.isEmpty();
    }
    
    public int size() // number of points in the set 
    {
    	return N;
    }
    
    public void insert(Point2D p) // add the point to the set (if it is not already in the set)
    {
    	if (p == null) throw new IllegalArgumentException();
	    if (!kdtree.contains(p))
	    {
	    	kdtree.add(p);
	    	N++;
	    }
    }
    
    public boolean contains(Point2D p) // does the set contain point p? 
    {
    	if (p == null) throw new IllegalArgumentException();
	    return kdtree.contains(p);
    }
    
    public void draw() // draw all points to standard draw 
    {
    	kdtree.draw();
    }
    
    public Iterable<Point2D> range(RectHV rect) // all points that are inside the rectangle (or on the boundary) 
    {
    	if (rect == null) throw new IllegalArgumentException();
    	return kdtree.RangePoints(rect);
    }
    
    public Point2D nearest(Point2D p) // a nearest neighbor in the set to point p; null if the set is empty 
    {
    	if (p == null) throw new IllegalArgumentException();
    	if (kdtree.isEmpty()) return null;
    	return kdtree.closest(p);
    }

    public static void main(String[] args) // unit testing of the methods (optional) 
    {
    	KdTree kd = new KdTree();
    	
    	kd.insert(new Point2D(1.0, 0.25));
    	kd.draw();
    	StdDraw.pause(300);
    	kd.insert(new Point2D(0.5, 1.0));
    	kd.draw();
    	StdDraw.pause(300);
    	kd.insert(new Point2D(0.75, 0.0));
    	kd.draw();
    	StdDraw.pause(300);
    	kd.insert(new Point2D(1.0, 0.5));
    	kd.draw();
    	StdDraw.pause(300);
    	kd.insert(new Point2D(0.75, 0.25));
    	kd.draw();
    	StdDraw.pause(300);
    	kd.insert(new Point2D(0.0, 0.75));
    	kd.draw();
    	StdDraw.pause(300);
    	kd.insert(new Point2D(0.5, 0.5));
    	kd.draw();
    	StdDraw.pause(300);
    	kd.insert(new Point2D(0.75, 0.25));
    	kd.draw();
    	StdDraw.pause(300);
    	
    	System.out.print(kd.size());
    }
}