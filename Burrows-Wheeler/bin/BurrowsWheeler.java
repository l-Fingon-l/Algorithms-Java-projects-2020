import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler 
{
    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    public static void transform()
    {
    	String input = BinaryStdIn.readString();
    	int n = input.length();
    	CircularSuffixArray csa = new CircularSuffixArray(input);
    	char[] t = new char[n];
    	for (int i = 0; i < n; i++)
    	{
    		int index = csa.index(i);
    		if (index == 0)
    		{
    			BinaryStdOut.write(i);
    			t[i] = input.charAt(n - 1);
    		}
    		else t[i] = input.charAt(index - 1);
    	}
    	for (int i = 0; i < n; i++)
    		BinaryStdOut.write(t[i]);
    	BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform()
    {
    	int first = BinaryStdIn.readInt();
    	String input = BinaryStdIn.readString(); // last column
    	int n = input.length();
    	char[] a = input.toCharArray(); // first column
    	char[] next = sort(a);
    	for (int i = 0; i < n; i++)
    	{
    		BinaryStdOut.write(a[first]);
    		first = next[first];
    	}
    	BinaryStdOut.close();
    }
    
    private static char[] sort(char[] a) // Radix sorting the string to get the 1st column
    {
    	int N = a.length;
    	int R = 256;
    	char[] aux = new char[N];
    	char[] next = new char[N];
      	int[] count = new int[R+1];
      	for (int i = 0; i < N; i++)
      		count[a[i]+1]++;
      	for (int r = 0; r < R; r++)
      		count[r+1] += count[r];
      	for (int i = 0; i < N; i++)
      	{
      		aux[count[a[i]]] = a[i];
      		next[count[a[i]]++] = (char) i; // next array is created right here
      	}
      	for (int i = 0; i < N; i++)
      		a[i] = aux[i];
      	
      	return next;
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args)
    {
    	if (args.length != 1) throw new IllegalArgumentException("Usage: java BurrowsWheeler: "
    			+ "- for encoding; + for decoding");
    	if (args[0].equals("-")) transform();
    	else if (args[0].equals("+")) inverseTransform();
    }
}