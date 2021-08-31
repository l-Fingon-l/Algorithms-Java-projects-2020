import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
public class MoveToFront 
{
	private static class List // underlying data structure 
	{
		public final int size = 256;
		public Node head;
		
		private class Node
		{
			public Node next;
			public int c;
			public Node(Node next, int c) { this.next = next; this.c = c; }
		}
		
		public List()
		{
			head = new Node(null, 0);
			Node prev = head;
			for (int i = 1; i < size; i++)
			{
				prev.next = new Node(null, i);
				prev = prev.next;
			}
		}
		
		// helper functions
		
		public int code(int c) // encode the byte
		{
			int code = 0;
			Node x = head;
			Node prev = null;
			
			while(x.c != c)
			{
				prev = x;
				x = x.next;
				code++;
			}
			
			update(prev, x);
			return code;
		}
		
		public int getc(int code)
		{
			Node x = head;
			Node prev = null;
			for (int i = 0; i < code; i++)
			{
				prev = x;
				x = x.next;
			}
			
			update(prev, x);
			return x.c;
		}
		
		public void update(Node prev, Node x)
		{
			if (prev == null) return;
			
			prev.next = x.next;
			x.next = head;
			head = x;
		}
	}
	
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode()
    {
    	List table = new List();
        while(!BinaryStdIn.isEmpty())
        {
        	int c = BinaryStdIn.readInt(8);
        	BinaryStdOut.write(table.code(c), 8);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode()
    {
    	List table = new List();
    	while(!BinaryStdIn.isEmpty())
        {
         	int c = BinaryStdIn.readInt(8);
         	BinaryStdOut.write(table.getc(c), 8);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args)
    {
    	if (args.length != 1) throw new IllegalArgumentException("Usage: java MoveToFrint: - for encoding; + for decoding");
    	if (args[0].equals("-")) encode();
    	else if (args[0].equals("+")) decode();
    }
}