public class Queue2Stacks<Item>
{
	private Stack in;
	private Stack out;
	private int N;
	
	private class Stack
	{
		private Item[] array;
		private int N;
		
		public Stack()
		{
			array = (Item[]) new Object[1];
		}
		
		private void resize(int size)
		{
			Item[] old = array;
			array = (Item[]) new Object[size];
			for (int i = 0; i < N; i++) array[i] = old[i];
			old = null;
		}
		
		public void push(Item n)
		{
			if (N == array.length) resize(2 * array.length);
			array[N++] = n;
		}
		
		public Item pop()
		{
			if (N < array.length / 4) resize(array.length / 2);
			Item item = array[--N];
			array[N] = null;
			return item;
		}
	}
	
	public Queue2Stacks()
	{
		in = new Stack();
		out = new Stack();
		N = 0;
	}
	
	public void enqueue(Item item)
	{
		in.push(item);
		N++;
	}
	
	public Item dequeue()
	{
		for (int i = 0; i < N; i++)
			out.push(in.pop());
		
		N--;
		Item item =  out.pop();
		
		for (int i = 0; i < N; i++)
			in.push(out.pop());
		
		return item;
	}
	
	public static void main(String[] args)
	{
		Queue2Stacks<Integer> task1 = new Queue2Stacks<>();
		task1.enqueue(10);
		task1.enqueue(2);
		task1.enqueue(17);
		task1.dequeue();
		task1.enqueue(18);
		task1.enqueue(31);
		task1.enqueue(1);
		task1.enqueue(98);
		task1.dequeue();
		System.out.print(task1.dequeue());
	}
}
