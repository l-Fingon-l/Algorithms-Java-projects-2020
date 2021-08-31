public class StackWithMax 
{
	private double[] array;
	private StackWithMax Maxs;
	private int N;
	private double max;
	private boolean inner;
	
	public StackWithMax()
	{
		array = new double[1];
		inner = false;
		Maxs = new StackWithMax(true);
	}
	
	public StackWithMax(boolean state)
	{
		array = new double[1];
		inner = state;
	}
	
	private void resize(int size)
	{
		double[] old = array;
		array = new double[size];
		for (int i = 0; i < N; i++) array[i] = old[i];
		old = null;
	}
	
	public void push(double n)
	{
		if (N == array.length) resize(2 * array.length);
		array[N++] = n;
		if (!inner)
			if (n > max) 
			{
				max = n;
				Maxs.push(n);
			}
	}
	
	public double pop()
	{
		if (N-- < array.length / 4) resize(array.length / 2);
		double item = array[N];
		if (!inner) 
			if (item == max)
			{
				Maxs.pop();
				max = Maxs.pop();
				Maxs.push(max);
			}
		return item;
	}
	
	public double returnTheMaximum()
	{
		return max;
	}
	
	public static void main(String[] args)
	{
		StackWithMax task2 = new StackWithMax();
		task2.push(10);
		task2.push(2);
		task2.push(17);
		task2.pop();
		task2.push(18);
		task2.push(31);
		task2.push(1);
		task2.push(98);
		task2.pop();
		System.out.print(task2.returnTheMaximum());
	}
}
