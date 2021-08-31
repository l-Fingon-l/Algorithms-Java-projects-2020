import edu.princeton.cs.algs4.MaxPQ;

public class TaxicabNumbers
{
	private static class Cube implements Comparable<Cube>
	{
		public int i;
		public int j;
		public long sum_of_cubes;
		
		public Cube(int i_, int j_)
		{
			i = i_;
			j = j_;
			sum_of_cubes = (long)i * i * i + (long)j * j * j;
		}
		
		public int compareTo(Cube that) 
		{
	        if (this.sum_of_cubes < that.sum_of_cubes) return -1;
	        else if (this.sum_of_cubes > that.sum_of_cubes) return +1;
	        else return  0;
	    }

	    public String toString() 
	    {
	        return i + "^3 + " + j + "^3";
	    }
	}
	
	public static void main(String[] args)
	{
		int N = Integer.parseInt(args[0]);
		
		// to know which columns and rows are free, "freedom controller"
		int[] rows = {N - 1, N - 1};
		int[] cols = {N - 1, N - 1};
		
		MaxPQ<Cube> pq = new MaxPQ<>();
		
		pq.insert(new Cube(N - 1, N - 1));
		Cube top;
		
		Cube prev = new Cube(N - 1, N);
		int run = 1;
		while (!pq.isEmpty())
		{
			// popping the max element and updating the state of "freedom controller"
			top = pq.delMax();
			int top_type;
			if (pq.isEmpty())
			{
				rows[0] = -1;
				rows[1] = -1;
				cols[0] = -1;
				cols[1] = -1;
				top_type = 0;
			}
			else
			{
				if (pq.max().i < top.i) // we popped the higher one
				{
					cols[1] = -1;
					rows[0] = -1;
					top_type = 1;
				}
				else // we popped the lower one
				{
					cols[0] = -1;
					rows[0] = -1;
					top_type = 2;
				}
			}
			
			
			if (top.i > 1 && top.i - 1 >= top.j) // adding the left one
				if (top.i - 1 != cols[0] && top.i - 1 != cols[1])
				{
					pq.insert(new Cube(top.i - 1, top.j));
					
					if (top_type == 0)
					{
						cols[0] = top.i - 1;
						rows[1] = top.j;
						rows[0] = rows[1];
						cols[1] = cols[0];
					}
					
					if (top_type == 1)
					{
						cols[1]--;
						rows[0] = top.j;
					}
					
					if (top_type == 2)
					{
						cols[0]--;
						rows[1] = top.j;
					}
				}
			
			if (top.j > 1) // adding the higher one
				if (top.j - 1 != rows[0] && top.j - 1 != rows[1])
				{
					pq.insert(new Cube(top.i, top.j - 1));
					
					if (top_type == 0)
					{
						rows[0] = top.j - 1;
						cols[1] = top.i;
						rows[1] = rows[0];
						cols[0] = cols[1];
					}
					
					if (top_type == 1)
					{
						cols[1] = top.i;
						rows[0]--;
					}
					
					if (top_type == 2)
					{
						cols[0] = top.i;
						rows[1]--;
					}
				}
			
			
			
			if (prev.sum_of_cubes == top.sum_of_cubes) 
			{
	            run++;
	            if (run == 2) System.out.print(prev.sum_of_cubes + " = " + prev);
	            System.out.print(" = " + top);
	        }
	        else 
	        {
	            if (run > 1)
	            {
	            	System.out.println();
	            	run = 1;
	            }
	        }
	        prev = top;
		}
	}
}
