public class DynamicMedian 
{
	private int N;
	private MaxPQ maxPQ;
	private MinPQ minPQ;
	
	private class MaxPQ
	{
		private int[] arr;
		private int N;
		
		public MaxPQ()
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
	}
	
	private class MinPQ
	{
		private int[] arr;
		private int N;
		
		public MinPQ()
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
		
		public int delMin()
		{
			int Min = arr[1];
			exch(1, N--);
			sink(1);
			if (N < arr.length / 4) resize(arr.length / 2);
			return Min;
		}
		
		public int size()
		{
			return N;
		}
		
		private void resize(int size)
		{
			int[] old = arr;
			arr = new int[size];
			for (int i = 0; i < N; i++)
				arr[i] = old[i];
			old = null;
		}
		
		private void swim(int k)
		{
			while (k > 1 && arr[k / 2] > arr[k])
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
				if (j < N && arr[j + 1] < arr[j]) j++;
				if (!(arr[k] > arr[j])) break;
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
	}
	
	public DynamicMedian()
	{
		maxPQ = new MaxPQ();
		minPQ = new MinPQ();
		N = 0;
	}
	
	public void insert(int x)
	{
		if (N++ == 0)
		{
			maxPQ.insert(x);
			return;
		}
		
		if (x > maxPQ.Max())
		{
			minPQ.insert(x);
			if (maxPQ.size() < minPQ.size())
				maxPQ.insert(minPQ.delMin());
		}
		else 
		{
			maxPQ.insert(x);
			if (maxPQ.size() - 1 > minPQ.size())
				minPQ.insert(maxPQ.delMax());
		}
	}
	
	public int find_the_median()
	{
		return maxPQ.Max();
	}
	
	public int remove_the_median()
	{
		if (N == 0) throw new IllegalArgumentException();
		int Median = maxPQ.delMax();
		N--;
		
		if (maxPQ.size() < minPQ.size())
			maxPQ.insert(minPQ.delMin());
		
		return Median;
	}
	
	public static void main(String[] args)
	{
		DynamicMedian task1 = new DynamicMedian();
		
		task1.insert(0);
		task1.insert(2);
		task1.insert(1);
		task1.insert(7);
		task1.insert(6);
		task1.insert(0);
		task1.insert(4);
		
		System.out.println(task1.find_the_median());
		System.out.println(task1.remove_the_median());
		
		task1.insert(10);
		
		System.out.println(task1.remove_the_median());
		System.out.println(task1.remove_the_median());
		System.out.println(task1.remove_the_median());
		System.out.println(task1.remove_the_median());
		System.out.println(task1.remove_the_median());
		System.out.println(task1.remove_the_median());
	}
}
