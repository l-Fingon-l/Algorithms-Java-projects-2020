import edu.princeton.cs.algs4.KMP;

public class TandemRepeat 
{
	public static void main(String[] args) 
	{
		String b = args[0];
        String s = args[1];

        int offset = -b.length(), start_offset = 0, length = 0, longest_length = 0, longest_offset = -1, L = s.length(), M = b.length();
        KMP kmp = new KMP(b);
        do
        {
        	offset = offset + M + kmp.search(s.substring(offset + M));
        	if (offset == L) // safety check
        		break;
        	
        	if (offset - start_offset != M) // start of a new tandem repeat
        	{
        		length = 1;
        		start_offset = offset;
        	}
        	else length++;
        	
        	if (length > longest_length)
    		{
    			longest_length = length;
    			longest_offset = start_offset;
    		}
        }
        while (offset + M != L);
        
        if (longest_length == 0)
        	System.out.println("There are no tandem repeats in the text");
        else
        {
        	// print results
        	System.out.println("The length of the longest tandem repeat is " + longest_length + " copies.");
            System.out.println("text:          " + s);

            System.out.print  ("tandem repeat: ");
            for (int i = 0; i < longest_offset; i++)
            	System.out.print(" ");
            for (int i = 0; i < longest_length; i++)
            	System.out.print(b);
        }  
	}

}
