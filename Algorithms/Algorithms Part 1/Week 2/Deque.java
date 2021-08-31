import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> 
{
	private Node first;
	private Node last;
	private int N;
	
	private class Node
	{
		private Node next;
		private Node previous;
		private Item item;
	}
	
	 private class ListIterator implements Iterator<Item>
	 {
		 private Node current = first;
		 public boolean hasNext() 
		 { 
			 return current != null; 
		 }
		 public void remove() 
		 { 
			 throw new UnsupportedOperationException();
		 }
		 public Item next()
		 {
			 if (!hasNext()) throw new java.util.NoSuchElementException();
			 Item item = current.item;
			 current = current.next;
			 return item;
		 }
	 }

    // construct an empty deque
    public Deque()
    {
    	first = null;
    	last = null;
    }

    // is the deque empty?
    public boolean isEmpty()
    {
    	return first == null;
    }

    // return the number of items on the deque
    public int size()
    {
    	return N;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
    	if (item == null) throw new IllegalArgumentException();
    	
    	Node old = first;
    	first = new Node();
    	if (N++ == 0) last = first;
    	else old.previous = first;
    	first.next = old;
    	first.item = item;
    	first.previous = null;
    }

    // add the item to the back
    public void addLast(Item item)
    {
    	if (item == null) throw new IllegalArgumentException();
    	
    	Node old = last;
    	last = new Node();
    	last.item = item;
    	last.next = null;
    	last.previous = old;
    	if (N++ != 0) old.next = last;
    	else first = last;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
    	if (N-- < 1) throw new java.util.NoSuchElementException();
    	
    	Node old = first;
    	Item item = old.item;
    	old.item = null;
    	first = first.next;
    	if (N < 1) last = first;
    	else first.previous = null;
    	
    	return item;
    }

    // remove and return the item from the back
    public Item removeLast()
    {
    	if (N-- < 1) throw new java.util.NoSuchElementException();
    	
    	Item item = last.item;
    	last.item = null;
    	last = last.previous;
    	if (N < 1) first = last;
    	else last.next = null;
    	
    	return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()
    {
    	return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args)
    {
    	Deque<Integer> task1 = new Deque<>();
    	System.out.print(task1.removeLast());
    	task1.removeLast();
    	task1.addFirst(4);
    	task1.addFirst(3);
    	System.out.print(task1.removeFirst());
    	System.out.print(task1.removeLast());
    	
    	
    	for(int i: task1)
    	{
    		System.out.print(i);
    	}
    }
}