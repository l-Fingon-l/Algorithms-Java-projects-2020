import java.util.Random;

public class RandomizedPriorityQueue 
{
	private int[] arr;
	private int N;
	
	public int sample()
	{
		Random rand = new Random();
		return arr[rand.nextInt(N) + 1];
	}
	
	public int delRandom()
	{
		if (N == 0) throw new IllegalArgumentException();
		Random rand = new Random();
		int k = rand.nextInt(N) + 1;
		int x = arr[k];
		
		exch(k, N--);
		sink(k);
		
		return x;
	}
	
	//============================================
	
	public RandomizedPriorityQueue()
	{
		arr = new int[2];
		N = 0;
	}
	
	public void insert(int x)
	{
		if (++N + 1 > arr.length)
			resize(arr.length * 2);
		arr[N] = x;
		swim(N);
	}
	
	public int delMax()
	{
		int Max = arr[1];
		exch(1, N--);
		sink(1);
		if (N < arr.length / 4) resize(arr.length / 2);
		return Max;
	}
	
	public int Max()
	{
		return arr[1];
	}
	
	public int size()
	{
		return N;
	}
	
	private void resize(int size)
	{
		int[] old = arr;
		arr = new int[size];
		for (int i = 1; i < N; i++)
			arr[i] = old[i];
		old = null;
	}
	
	private void swim(int k)
	{
		while (k > 1 && arr[k / 2] < arr[k])
		{
			exch(k / 2, k);
			k /= 2;
		}
	}
	
	private void sink(int k)
	{
		while (2 * k <= N)
		{
			int j = 2 * k;
			if (j < N && arr[j + 1] > arr[j]) j++;
			if (!(arr[k] < arr[j])) break;
			exch(k, j);
			k = j;
		}
	}
	
	private void exch(int i, int j)
	{
		int buf = arr[i];
		arr[i] = arr[j];
		arr[j] = buf;
	}
	
	public static void main(String[] args)
	{
		RandomizedPriorityQueue task2 = new RandomizedPriorityQueue();
		
		task2.insert(0);
		task2.insert(2);
		task2.insert(1);
		task2.insert(7);
		task2.insert(6);
		task2.insert(0);
		task2.insert(4);
		
		System.out.println(task2.sample());
		System.out.println(task2.sample());
		System.out.println(task2.sample());
		System.out.println("");
		
		for (int i = 0, N = task2.size(); i < N; i++)
			System.out.println(task2.delRandom());
	}
}
