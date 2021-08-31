import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Knuth;

public class Boggle 
{
	private Node root;
	private final int amount;
	private char[] board;
	private boolean[] marked;
	private int size;
	private Stack<String> set;
	
	private class Node
	{
		private boolean val;
		private char c;
		private Node left, mid, right;
	}
	
	public Boggle(String file)
	{
		In in = new In(file);
		// create a TST from dictionary
		
		String[] array = in.readAllLines();
		Knuth.shuffle(array);
		amount = array.length;
		for (int i = 0; i < amount; i++)
			put(array[i]);
	}
	
	public Iterable<String> play(String board_file, int size_) // now play the game recursively for each tile
	{
		set = new Stack<>();
		In in = new In(board_file);
		size = size_;
		board = new char[size * size];
		marked = new boolean[size * size];
		for (int i = 0; i < size * size; i++)
			board[i] = in.readString().charAt(0);	
		
		System.out.println("The board input is:");
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
				System.out.print(board[j + size * i] + " ");
			System.out.println();
		}
		
		for (int i = 0; i < size * size; i++)
		{
			StringBuilder word = new StringBuilder();
			Node x = exists(root, board[i]);
			if (x != null) find(x, word, i);
		}
		
		System.out.println();
		System.out.println("The words found:");
		return set;
	}
	
	public void put(String key)
	{
		root = put(root, key, 0);
	}
	
	private Node put(Node x, String key, int d)
	{
		char c = key.charAt(d);
		if (x == null) 
		{
			x = new Node();
			x.c = c;
		}
		if (c < x.c) x.left = put(x.left, key, d);
		else if (c > x.c) x.right = put(x.right, key, d);
		else if (d < key.length() - 1) x.mid = put(x.mid, key, d + 1);
		else x.val = true;
		return x;	
	}
	
	private void find(Node x, StringBuilder word, int tile)
	{
		marked[tile] = true;
		word.append(x.c);
		if (x.val) set.push(word.toString());
		
		for (int j = -1; j < 2; j++)
			for (int i = -1; i < 2; i++)
			{
				int id = tile % size + i + size * (tile / size + j);
				if (onBoard(tile % size + i, tile / size + j) && !marked[id])
				{
					Node next = exists(x.mid, board[id]);
					if (next != null) find(next, word, id);
				}
			}
		
		marked[tile] = false;
		word.deleteCharAt(word.length() - 1);
	}

	private boolean onBoard(int x, int y)
	{
		if (x < 0 || x > size - 1 || y < 0 || y > size - 1) return false;
		else return true;
	}
	
	private Node exists(Node x, char c)
	{
		if (x == null) return null;
		if (c < x.c) return exists(x.left, c);
		else if (c > x.c) return exists(x.right, c);
		else return x;
	}
	
	public static void main(String[] args) 
	{
		Boggle game = new Boggle(args[0]);
		
		int n = 0;
		for (String s: game.play(args[1], Integer.parseInt(args[2])))
		{
			System.out.print(s + " - ");
			if (n % 20 == 19) System.out.println();
			n++;
		}
	}
	
/*	public static void main(String[] args) // parsing English Dictionary to 1 file
	{
		Out out = new Out("Dictionary.txt");
		TST<Boolean> st = new TST<>();
		for (char c = 'A'; c <= 'Z'; c++)
		{
			In in = new In(c + "word.csv");
			String[] array = in.readAllStrings();
			for (int i = 0; i < array.length; i++)
				if (array[i].length() > 1 && !st.contains(array[i]))
					st.put(array[i], true);
		}
		for(String s: st.keys())
			out.println(s);
	} 
	*/
}
