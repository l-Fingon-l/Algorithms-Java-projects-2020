import java.util.Arrays;

public class Sum3QuadraticTime 
{
	private int[] array;
	
	private void Sort()
	{
		Arrays.sort(array);
	}
	
	public Sum3QuadraticTime(int[] input)
	{
		array = input;
		Sort();
	}
	
	public int Calculate()
	{
		int amount = 0, high = 0;
		int base_id = 0, low_id = 1, high_id = array.length - 1, highBorder_id = high_id;
		boolean side = false;
		for (int base = array[base_id], low = array[low_id]; base + low < 0; base = array[++base_id],
				low_id = base_id + 1, low = array[low_id]) // no more than (N - 1)/2 cycles
		{
			// base - 1st element
			// low - 2nd element
			
			// looking for the high border
			while (-(base + low) < array[highBorder_id]) 
			{
				if (highBorder_id == low_id) return amount; //safety check
				highBorder_id--;
			}
			side = true; // true - right, false - left
			high_id = highBorder_id;
			high = array[high_id]; // 3rd element
			
			while (low != high) // no more than (N - base_id - 3) cycles
			{
				if (side)
				{
					do
					{
						if (low == high) break; //safety check
						if (-(base + low) == high) amount++;
						high = array[--high_id];
					}
					while (-(base + low) <= high);
					
					side = false;
				}
				else
				{
					do 
					{
						if (low == high) break; //safety check
						if (-(base + low) == high) amount++;
						low = array[++low_id];
					}
					while (-(base + low) > high);
					
					side = true;
				}
			}
		}
		
		return amount;
	}
	
	
	
	public static void main(String[] args)
	{
		int amount = Integer.parseInt(args[0]);
		int[] array = new int[amount];
		for (int i = 0; i < amount; i++) array[i] = Integer.parseInt(args[i + 1]);
		
		Sum3QuadraticTime task1 = new Sum3QuadraticTime(array);
		System.out.println("There are " + task1.Calculate() + " triples that sum to zero.");
	}

}