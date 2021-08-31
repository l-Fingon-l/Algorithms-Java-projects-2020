import java.util.Random;

public class SearchInABitonicArray
{
	private int[] array;
	private int a; // iterator
	private int b; // iterator
	private int middle; // iterator
	private int MaxArrayLength;
	private int step;
	
	boolean is_present;
	
	private int check; // value
	
	private int searched; // value
	private boolean side; // false = left, true = right
	
	private boolean binarySearch()
	{
		if (a == middle) 
		{
			if (array[a] == searched) return true;
			if (array[b] == searched) return true;
			return false;
		}
		
		if (side ^ (array[middle] > searched))
		{
			b = middle - 1;
			middle = (a + b) / 2;
			return binarySearch();
		}
		else if (side ^ (array[middle] < searched))
		{
			a = middle + 1;
			middle = (a + b) / 2;
			return binarySearch();
		} 
		else return true;
	}
	
	// finding bitonic point  
    private boolean findBitonicPoint(int l, int r) 
    { 
    	if (array[middle] == searched) return true;
    	if (array[middle] < searched)
		{
            middle = (r + l) / 2; 
            if (array[middle] > array[middle - 1] && array[middle] > array[middle + 1]) 
            { 
                if(array[middle] == searched) return true;
                else return false;
            }
            else 
            { 
                if (array[middle] > array[middle - 1] && array[middle] < array[middle + 1]) 
                { 
                    findBitonicPoint(middle, r); 
                } 
                else 
                { 
                    if (array[middle] < array[middle - 1] && array[middle] > array[middle + 1]) 
                    { 
                        findBitonicPoint(l, middle); 
                    } 
                } 
            } 
            return false; 
		}
    	else 
		{
			int a_ = a, b_ = b, middle_ = middle;
			if (!binarySearch())
			{
				side = !side;
				a = a_;
				b = b_;
				middle = middle_;
				return binarySearch();
			}
			else return true;
		}
    } 
	
	public SearchInABitonicArray(int x, int length, int step_) // with input generation
	{
		step = step_;
		MaxArrayLength = length;
		searched = x;
		side = false; 
		a = 0;
		GenerateBitonicArray();
		b = array.length - 1;
		middle = (a + b) / 2;
	}
	
	public SearchInABitonicArray(int x, int[] input) // with given array
	{
		array = input;
		searched = x;
		side = false; 
		a = 0;
		b = array.length - 1;
		middle = (a + b) / 2;
	}
	
	public boolean Search(boolean phase)
	{
		if (array[middle] == searched) return true;
		if (array[middle] < searched)
		{
			int check_id = (middle + (side ? a : b)) / 2;
			check = array[check_id];
			if (array[middle] == check) 
			{
				if (array[middle + 1] == searched) return true;
				else return false;
			}
			
			if (array[middle] < check)
			{
				if (!side) a = middle + 1;
				else b = middle - 1;
				middle = check_id;
				phase &= false;
				return Search(phase);
			}
			else 
			{
				if (phase) return findBitonicPoint(a, b);
				phase = !phase;
				side = !side;
				return Search(phase);
			}
		}
		else 
		{
			int a_ = a, b_ = b, middle_ = middle;
			if (!binarySearch())
			{
				side = !side;
				a = a_;
				b = b_;
				middle = middle_;
				return binarySearch();
			}
			else return true;
		}
	}
	
	private void GenerateBitonicArray()
	{
		// create instance of Random class 
        Random rand = new Random(); 
  
        // Generate random integers in range 0 to 999 
        int size = rand.nextInt(MaxArrayLength);
        array = new int[size];
        int size1 = rand.nextInt(size);
        int starting_point = rand.nextInt((MaxArrayLength + 2) * step) - rand.nextInt((MaxArrayLength + 2) * step);
        for (int i = 0; i < size1; i++) 
        {
        	array[i] = rand.nextInt(step) - starting_point + i * step;
        	if (array[i] == 0) is_present = true;
        }
        	
        starting_point = array[size1 - 1] - 1;
        for (int i = size1; i < size; i++) 
        {
        	array[i] = rand.nextInt(step) + starting_point - i * step;
        	if (array[i] == 0) is_present = true;
        }
        
/*        System.out.print(size);
        char c = ' ', c1 = ',';
        for (int i = 0; i < size; i++)
        {
        	System.out.append(c);
        	System.out.append(c1);
        	System.out.print(array[i]);
        }*/
	}

	
	public static void main(String[] args)
	{
		int searched = 12452;
		
		SearchInABitonicArray task2 = new SearchInABitonicArray(searched, 10000000, 100);
		System.out.println("The number " + searched + (task2.Search(false) ? " is present in the array."
		: " is not present in the array."));
	}
}
