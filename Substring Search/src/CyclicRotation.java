import edu.princeton.cs.algs4.RabinKarp;

public class CyclicRotation 
{
	public static void main(String[] args)
	{
		String txt = args[0] + args[0];
	    String pat = args[1];

	    RabinKarp searcher = new RabinKarp(pat);
	    int offset = searcher.search(txt);
	    
	    if (offset == txt.length())
	    	System.out.println("The strings are not the cyclic rotations");
	    else
	    {
	    	// print results
	    	System.out.println("The string are cyclic rotations:");
	    	System.out.println("string1: " + txt);

	    	System.out.print("string2: ");
		    for (int i = 0; i < offset; i++)
		    	System.out.print(" ");
		    System.out.println(pat);
	    }
	}
}
