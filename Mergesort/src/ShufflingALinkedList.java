import edu.princeton.cs.algs4.StdRandom;

public class ShufflingALinkedList
{
	 private List.Node b;
	 private List.Node c;
	 private List.Node beg;
	
	public static class List
	{
		public Node first;
		public Node current;
		public int N;
		
		public class Node
		{
			public Node next;
			public String item;
		}
		
		public List()
		{
			first = null;
			N = 0;
		}
		
		public void push(String s)
		{
			Node old = first;
			first = new Node();
			first.item = s;
			first.next = old;
			N++;
		}
	}
	
	private List.Node merge(List.Node a, List.Node b, int a_size, int b_size, List.Node next)
	{
		int N = a_size + b_size;
		boolean empty = true;
		
		List merged = new List();
		int a_N = 0, b_N = 0;
		for (int i = 0; i < N; i++)
		{
			if (empty)
			{
				if (StdRandom.uniform(2) == 0) 
				{
					merged.first = a;
					a = a.next;
					a_N++;
				}
				else 
				{
					merged.first = b;
					b = b.next;
					b_N++;
				}
				empty = false;
				merged.current = merged.first;
			}
			else if (a_N >= a_size)
			{
				merged.current.next = b;
				merged.current = merged.current.next;
				b = b.next;
				b_N++;
			}
			else if (b_N >= b_size)
			{
				merged.current.next = a;
				merged.current = merged.current.next;
				a = a.next;
				a_N++;
			}
			else	
			{
				if (StdRandom.uniform(2) == 0) 
				{
					merged.current.next = a;
					merged.current = merged.current.next;
					a = a.next;
					a_N++;
				}
				else
				{
					merged.current.next = b;
					merged.current = merged.current.next;
					b = b.next;
					b_N++;
				}
			}
		}
		
		a = null;
		b = null;
		merged.current.next = next; 
		
		List.Node result = merged.first;
		merged = null;
		
		return result;
	}
	
	private void shuffle(List a, int lo, int hi)
	{
	    if (hi <= lo) return;
	    
	    int mid = lo + (hi - lo) / 2;
	    
	    shuffle(a, lo, mid);
	    shuffle(a, mid+1, hi);
	    
	    beg = a.first;
	    c = a.first;
	    List.Node end;
	    for (int i = 0; i < lo; i++)
	    {
	    	if (i != 0) beg = beg.next;
	    	c = c.next;
	    }
	    	
	    b = c;
	    for (int i = lo; i < mid + 1; i++)
	    	b = b.next;
	    end = b;
	    for (int i = 0; i < hi - mid; i++)
	    {
	    	end = end.next;
	    }
	    
	    if (lo == 0) a.first = merge(c, b, mid + 1 - lo, hi - mid, end);
	    else beg.next = merge(c, b, mid + 1 - lo, hi - mid, end);
	}
	
	public List shuffle(List a)
	{
		beg = a.first;
		shuffle(a, 0, a.N - 1);
		return a;
	}
	
	public static void main(String[] args)
	{
		List a = new List();
		a.push("A");
		a.push("B");
		a.push("c");
     	a.push("D");
 	    a.push("e");
		a.push("F");
		a.push("G");
		a.push("h");
		a.push("i");
		a.push("J");
		a.push("k");
		a.push("L");
		a.push("m");
		
		ShufflingALinkedList task3 = new ShufflingALinkedList();
		a = task3.shuffle(a);
		a.current = a.first;
		for (int i = 0; i < 13; i++)
		{
			System.out.println(a.current.item);
			a.current = a.current.next;
		}
	}
}
