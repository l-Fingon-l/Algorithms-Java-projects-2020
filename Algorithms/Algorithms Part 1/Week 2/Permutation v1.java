import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation 
{
	public static void main(String[] args)
	{
/*		int k = Integer.parseInt(args[0]);
		int N = 0;
		int index = 0;
		String s;
		RandomizedQueue<String> task3 = new RandomizedQueue<>();
		while(!StdIn.isEmpty())
		{
			N++;
			s = StdIn.readString();
			if (N > k) 
				index = StdRandom.uniform(N);
				if (index < k)
				{
					
				}
				else continue;
			task3.enqueue(s);
		}*/
		Deque<String> task3_1 = new Deque<>();
		while (!StdIn.isEmpty())
		{
			if (StdRandom.uniform(2) == 1) task3_1.addFirst(StdIn.readString());
			else task3_1.addLast(StdIn.readString());
		}
		System.out.println("It's alright");
		for (int i = 0, k = Integer.parseInt(args[0]), N = task3_1.size(); i < k; i++)
		{
			int ID = (int)(StdRandom.uniform() * ((double)N / k));
			int x = (int)((double)N / k) - ID - 1;
			while(ID-- != 0) task3_1.removeFirst();
			System.out.println(task3_1.removeFirst());
			while(x-- != 0) task3_1.removeFirst();
		}
	}
}
