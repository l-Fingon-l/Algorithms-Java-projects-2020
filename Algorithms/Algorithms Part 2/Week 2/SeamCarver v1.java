import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class SeamCarver 
{
	private Picture picture;
	private pixel[] map;
	private int width;
	private int height;
	private int Width;
	private final double border_energy = 1000.0;
	
	private class pixel
	{
		public int id;
		public double energy = 0.0;
		public pixel(int id) { this.id = id; }
	}
	
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture)
    {
 	    this.picture = picture;
 	    Width = width = picture.width();
 	    height = picture.height();
 	    int size = width * height;
 	    map = new pixel[size];
 	    for (int i = 0; i < size; i++)
 		    map[i] = new pixel(i);
 	    for (int col = 0; col < width; col++)
 	    	for (int row = 0; row < height; row++)
 	    		calculate_energy(col, row);
    }

    // current picture
    public Picture picture()
    {
    	Picture result = new Picture(width, height);
    	for (int col = 0; col < width; col++)
            for (int row = 0; row < height; row++)
            	result.setRGB(col, row, picture.getRGB(pixel(col, row).id % picture.width(), 
            			pixel(col, row).id / picture.width()));
    	
	    return result;
    }

    // width of current picture
    public int width()
    {
	    return width;
    }

    // height of current picture
    public int height()
    {
	    return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y)
    {
    	validate(x, y);
	    return pixel(x, y).energy;
    }
    
    private pixel pixel(int x, int y)
    {
    	return map[x + Width * y];
    }
   
    private void calculate_energy(int x, int y)
    {
    	validate(x, y);
 	   
	    if (x == 0 || x == width - 1 || y == 0 || y == height - 1) 
	    {
	    	map[x + Width * y].energy = border_energy;
	    	return;
	    }
	    
	    int cx1 = getRGB(x - 1, y);
	    int cx2 = getRGB(x + 1, y);
	    int cy1 = getRGB(x, y - 1);
	    int cy2 = getRGB(x, y + 1);
	    
	    map[x + Width * y].energy = Math.sqrt(
	    		+ sq(((cx2 >> 16) & 0xFF) - ((cx1 >> 16) & 0xFF)) 
	    		+ sq(((cx2 >> 8) & 0xFF) - ((cx1 >> 8) & 0xFF)) 
	    		+ sq((cx2 & 0xFF) - (cx1 & 0xFF)) 
	    		+ sq(((cy2 >> 16) & 0xFF) - ((cy1 >> 16) & 0xFF)) 
	    		+ sq(((cy2 >> 8) & 0xFF) - ((cy1 >>  8) & 0xFF)) 
	    		+ sq((cy2 & 0xFF) - (cy1 & 0xFF))); 
    }
    
    private int getRGB (int col, int row)
    {
    	return picture.getRGB(pixel(col, row).id % picture.width(), 
    			pixel(col, row).id / picture.width());
    }
    
    private double sq(double x)
    {
	    return x * x;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam()
    {
    	if (height == 1 || height == 2)
    	{
    		int[] result = new int[width];
    		for (int i = 0; i < width; i++)
    			result[i] = 0;
    		return result;
    	}
    	if (width == 1)
    	{
    		int[] result = new int[1];
    		result[0] = 0;
    		return result;
    	}
    	if (width == 2)
    	{
    		int[] result = new int[2];
    		result[0] = result[1] = 0;
    		return result;
    	}
    	
    	int size = (width - 2) * (height - 2);
    	double[] distTo = new double[size];
    	int[] edgeTo = new int[size];
    	
    	for (int y = 0; y < height - 2; y++)
    		distTo[(width - 2) * y] = pixel(1, y + 1).energy;
    	for (int x = 1; x < width - 2; x++)
    		for (int y = 0; y < height - 2; y++)
    			distTo[x + (width - 2) * y] = Double.POSITIVE_INFINITY;
    	
    	int end = 0;
    	double min = Double.POSITIVE_INFINITY;
    	for (int x = 0; x < width - 4; x++) // relaxing inner vertices
    		for (int y = 0; y < height - 2; y++) 
    	    	relax_horizontal(x, y, distTo, edgeTo);
    	
    	if (width == 3)
    	{
    		for (int y = 0; y < height - 2; y++) // relaxing border vertices
    			if (distTo[y] < min)
    			{
    				end = y;
    				min = distTo[y];
    			}
    	}
    	else
    	{
    		for (int y = 0; y < height - 2; y++) // relaxing border vertices
    		{
    			int v = relax_horizontal(width - 4, y, distTo, edgeTo);
    			if (min > distTo[v])
    			{
    				min = distTo[v];
    				end = v;
    			}
    		}	
    	}
    	
    	int[] seam = new int[width];
    	if (size == 1) seam[1] = end + 1;
    	else
    		for (int i = end, j = width - 2; j != -1; i = edgeTo[i], j--)
        		seam[j] = (int)i / (width - 2) + 1;
    	seam[width - 1] = seam[width - 2]; 
    	seam[0] = seam[1];
    	
    	return seam;
    }
    
    private int relax_horizontal(int x, int y, double[] distTo, int[] edgeTo)
    {
    	double min = Double.POSITIVE_INFINITY;
    	int min_ = 0;
    	for (int i = - 1; i < 2; i++)
    	{
    		if (y + i < 0 || y + i >= height - 2) continue;
    		int v = x + (width - 2) * y;
    		int w = v + 1 + (width - 2) * i;
    		if (distTo[w] > distTo[v] + pixel(x + 2, y + 1 + i).energy)
    		{
    			distTo[w] = distTo[v] + pixel(x + 2, y + 1 + i).energy;
    			edgeTo[w] = v;
    		}
    		if (distTo[w] < min) 
    		{
    			min = distTo[w];
    			min_ = w;    		
    		}
    	}
    	return min_;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam()
    {
    	if (width == 1 || width == 2)
    	{
    		int[] result = new int[height];
    		for (int i = 0; i < height; i++)
    			result[i] = 0;
    		return result;
    	}
    	if (height == 1)
    	{
    		int[] result = new int[1];
    		result[0] = 0;
    		return result;
    	}
    	if (height == 2)
    	{
    		int[] result = new int[2];
    		result[0] = result[1] = 0;
    		return result;
    	}
    	
    	int size = (width - 2) * (height - 2);
    	double[] distTo = new double[size];
    	int[] edgeTo = new int[size];
    	
    	for (int v = 0; v < width - 2; v++)
    		distTo[v]  = pixel(v + 1, 1).energy;
    	for (int v = width - 2; v < size; v++)
    		 distTo[v] = Double.POSITIVE_INFINITY;
    	
    	int end = 0;
    	double min = Double.POSITIVE_INFINITY;
    	for (int y = 0; y < height - 4; y++) // relaxing inner vertices
    		for (int x = 0; x < width - 2; x++) 
    	    	relax_vertical(x, y, distTo, edgeTo);
    	
    	if (height == 3)
    	{
    		for (int x = 0; x < width - 2; x++) // relaxing border vertices
    			if (distTo[x] < min)
    			{
    				end = x;
    				min = distTo[x];
    			}
    	}
    	else
    	{
    		for (int x = 0; x < width - 2; x++) // relaxing border vertices
    		{
    			int v = relax_vertical(x, height - 4, distTo, edgeTo);
    			if (min > distTo[v])
    			{
    				min = distTo[v];
    				end = v;
    			}
    		}
    	}
    	
    	int[] seam = new int[height];
    	if (size == 1) seam[1] = end + 1;
    	else
    		for (int i = end, j = height - 2; j != -1; i = edgeTo[i], j--)
        		seam[j] = (int)i % (width - 2) + 1;
    	seam[height - 1] = seam[height - 2]; 
    	seam[0] = seam[1];
    	
    	return seam;
    }
    
    private int relax_vertical(int x, int y, double[] distTo, int[] edgeTo)
    {
    	double min = Double.POSITIVE_INFINITY;
    	int min_ = 0;
    	for (int i = - 1; i < 2; i++)
    	{
    		if (x + i < 0 || x + i >= width - 2) continue;
    		int v = x + (width - 2) * y;
    		int w = v + (width - 2) + i;
    		if (distTo[w] > distTo[v] + pixel(x + 1 + i, y + 2).energy)
    		{
    			distTo[w] = distTo[v] + pixel(x + 1 + i, y + 2).energy;
    			edgeTo[w] = v;
    		}
    		if (distTo[w] < min) 
    		{
    			min = distTo[w];
    			min_ = w;    		
    		}
    	}
    	return min_;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam)
    { 
	    if (seam == null) throw new IllegalArgumentException("Horizontal null can not be removed!");
	    if (height <= 1) throw new IllegalArgumentException("The image is too thin to remove a horizontal seam!");
	    if (seam.length != width) throw new IllegalArgumentException("The seam is of a wrong width!");
	    for (int i = 1; i < width; i++)
	    	if (seam[i] < (seam[i - 1] - 1) || seam[i] > (seam[i - 1] + 1)) throw new IllegalArgumentException("Wrong seam!");
	    
	    height--;	
	    for (int x = 0; x < width; x++)
	    {
	    	for (int y = seam[x]; y < height; y++)
	    		map[x + Width * y] = pixel(x, y + 1);
	    	calculate_energy(x, seam[x] - 1);
	    	calculate_energy(x, seam[x]);
	    }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam)
    {
	    if (seam == null) throw new IllegalArgumentException("Vertical null can not be removed!");
	    if (width <= 1) throw new IllegalArgumentException("The image is too thin to remove a vertical seam!");
	    if (seam.length != height) throw new IllegalArgumentException("The seam is of a wrong height!");
	    for (int i = 1; i < height; i++)
	    	if (seam[i] < (seam[i - 1] - 1) || seam[i] > (seam[i - 1] + 1)) throw new IllegalArgumentException("Wrong seam!");
	    
	    width--;
	    for (int y = 0; y < height; y++)
	    {
	    	int pos = seam[y] + Width * y;
	    	System.arraycopy(map, pos + 1, map, pos, Width - seam[y] - 1);
	    } 
	    
	    for (int y = 0; y < height; y++)
	    {
	    	calculate_energy(seam[y] - 1, y);
	    	calculate_energy(seam[y], y);
	    }
    }
   
    private void validate(int x, int y)
    {
	    if (x < 0 || x >= width)
            throw new IllegalArgumentException("vertex's x-cord " + x + " is not between 0 and " + (width-1));
	    if (y < 0 || y >= height)
            throw new IllegalArgumentException("vertex's y-cord " + y + " is not between 0 and " + (height-1));
    }

    //  unit testing (optional) 
    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        StdOut.printf("image is %d columns by %d rows\n", picture.width(), picture.height());
        picture.show();        
        SeamCarver sc = new SeamCarver(picture);
        
        StdOut.printf("Displaying energy calculated for each pixel.\n");
        SCUtility.showEnergy(sc);

    }
}