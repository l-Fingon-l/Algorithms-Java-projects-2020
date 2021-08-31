import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> 
{
	private Item[] array;
	private int N;
	
	private void resize(int size)
	{
		Item[] old = array;
		array = (Item[]) new Object[size];
		for (int i = 0; i < N; i++) 
		{
			array[i] = old[i];
		}
		old = null;
	}
	
	private class RndQueueIterator implements Iterator<Item>
	{
		private int current;
		private int Number = N;
		private Item[] IDs;
		
		private RndQueueIterator()
		{
			IDs = (Item[]) new Object[N]; 
			for (int i = 0; i < N; i++) IDs[i] = array[i];
		}
		
		public boolean hasNext() 
		 { 
			 return Number != 0; 
		 }
		 public void remove() 
		 { 
			 throw new UnsupportedOperationException();
		 }
		 public Item next()
		 {
			 if (!hasNext()) throw new java.util.NoSuchElementException();
			 
			 current = StdRandom.uniform(Number);
			 Item item = IDs[current];
			 IDs[current] = IDs[--Number];
			 IDs[Number] = null;
			 return item;
		 }
	}
	
    // construct an empty randomized queue
    public RandomizedQueue()
    {
    	array = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty()
    {
    	return N == 0;
    }

    // return the number of items on the randomized queue
    public int size()
    {
    	return N;
    }

    // add the item
    public void enqueue(Item item)
    {
    	if (item == null) throw new IllegalArgumentException();
    	
    	if (N == array.length) resize(2 * array.length);
		array[N++] = item;
    }

    // remove and return a random item
    public Item dequeue()
    {
    	if (N == 0) throw new java.util.NoSuchElementException();
    	
    	if (N < array.length / 4) resize(array.length / 2);
    	int item_id = StdRandom.uniform(N);
    	Item item = array[item_id];
    	array[item_id] = array[--N];
    	array[N] = null;
    	return item;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
    	if (N == 0) throw new java.util.NoSuchElementException();
    	return array[StdRandom.uniform(N)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
    	return new RndQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args)
    {
    	int n = 5;
    	RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
    	for (int i = 0; i < n; i++)
    	    queue.enqueue(i);
    	for (int a : queue) {
    	    for (int b : queue)
    	        StdOut.print(a + "-" + b + " ");
    	    StdOut.println();
    	}
    }
}