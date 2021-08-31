public class DutchNationalFlag
{
	private Bucket[] array;
	int colour_number = 0;
	int swap_number = 0;
	
	static class Bucket
	{
		private String pebble;
		public Bucket(String s)
		{
			pebble = s;
		}
	}
	
	public void sort()
	{
		int lowR = 0, lowL = 0, highR = array.length - 1, highL = array.length - 1;
		String LowR, HighL;
		boolean left = true, right = true;
		
		LowR = colour(lowR);
		HighL = colour(highL);
		if (LowR == "White") left = false;
		if (HighL == "Blue") right = false;
		
		while (true)
		{
			if (LowR != "Blue") 
			{
				if (LowR == "Red") 
				{
					if (!left) left = true;
				}
				else
				{
					if (left) swap(lowL++, lowR);
					else lowL++;
				}
				if (++lowR < highL) LowR = colour(lowR);
			}
			else if (HighL != "White") 
			{
				if (HighL == "Red") 
				{
					if (!right) right = true;
				}
				else
				{
					if (right) swap(highR--, highL);
					else highR--;
				}
				if (lowR < --highL) HighL = colour(highL);
			}
			else 
			{
				swap(lowR, highL);
				HighL = "Blue";
				LowR = "White";
				
				if (left) 
				{
					swap(lowL++, lowR);
					LowR = "Red";
				}
				else lowL++;
				if (right)
				{
					swap(highR--, highL);
					HighL = "Red";
				}
				else highR--;
					
				if (++lowR < highL) LowR = colour(lowR);
			}
				
			if (lowR == highL) break;			
		}
	}
	
	private void swap(int i, int j)
	{
		Bucket buffer = array[i];
		array[i] = array[j];
		array[j] = buffer;
		swap_number++;
	}
	
	private String colour(int i)
	{
		colour_number++;
		return array[i].pebble;
	}
	
	public DutchNationalFlag(Bucket[] input)
	{
		array = input;
	}
	
	public static void main(String[] args)
	{
		Bucket[] array = new Bucket[20];
		array[0] = new Bucket("Red");
		array[1] = new Bucket("Blue");
		array[2] = new Bucket("White");
		array[3] = new Bucket("Red");
		array[4] = new Bucket("Red");
		array[5] = new Bucket("White");
		array[6] = new Bucket("White");
		array[7] = new Bucket("Red");
		array[8] = new Bucket("Blue");
		array[9] = new Bucket("White");
		array[10] = new Bucket("Blue");
		array[11] = new Bucket("Blue");
		array[12] = new Bucket("White");
		array[13] = new Bucket("Blue");
		array[15] = new Bucket("White");
		array[16] = new Bucket("White");
		array[17] = new Bucket("Red");
		array[18] = new Bucket("Blue");
		array[19] = new Bucket("White");
		
		DutchNationalFlag task3 = new DutchNationalFlag(array);
		task3.sort();
		
		for (int i = 0; i < 20; i++)
			System.out.println(i + 1  + ") " + array[i].pebble);
	}
} 
