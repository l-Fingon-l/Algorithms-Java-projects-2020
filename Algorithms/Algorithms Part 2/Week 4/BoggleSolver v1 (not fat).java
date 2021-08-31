import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Knuth;
import edu.princeton.cs.algs4.SET;

public class BoggleSolver
{
	private Node root;
	private final int amount;
	private boolean[] marked;
	private char[] board;
	private int size; // width
	private int height;
	private SET<String> set;
	
	private class Node
	{
		private boolean val;
		private char c;
		private Node left, mid, right;
	}
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary)
    {
		Knuth.shuffle(dictionary);
		amount = dictionary.length;
		for (int i = 0; i < amount; i++)
			if (dictionary[i].length() > 2)
				put(dictionary[i]);	
    }
    
    private void put(String key)
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
		if (x.val) 
		{
			String word_ = word.toString();
			if (!set.contains(word_)) set.add(word_);
		}
		
		for (int j = -1; j < 2; j++)
			for (int i = -1; i < 2; i++)
			{
				int id = tile % size + i + size * (tile / size + j);
				if (onBoard(tile % size + i, tile / size + j) && !marked[id])
				{
					char letter = board[id];
					Node next = exists(x.mid, letter);
					if (next != null)
						if (letter == 'Q') 
						{
							letter = 'U';
							next = exists(next.mid, letter);
							if (next != null) 
							{
								word.append('Q');
								find(next, word, id);
								word.deleteCharAt(word.length() - 1);
							}
						}
						else find(next, word, id);
				}
			}
		
		marked[tile] = false;
		word.deleteCharAt(word.length() - 1);
	}

	private boolean onBoard(int x, int y)
	{
		if (x < 0 || x > size - 1 || y < 0 || y > height - 1) return false;
		else return true;
	}
	
	private Node exists(Node x, char c)
	{
		if (x == null) return null;
		if (c < x.c) return exists(x.left, c);
		else if (c > x.c) return exists(x.right, c);
		else return x;
	}

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board)
    {
    	set = new SET<>();
		int width = size = board.cols();
		height = board.rows();
		marked = new boolean[width * height];
		this.board = new char[width * height];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				this.board[i * width + j] = board.getLetter(i, j);
		
		for (int i = 0; i < width * height; i++)
		{
			StringBuilder word = new StringBuilder();
			char letter = this.board[i];
			Node x = exists(root, letter);
			if (x != null && letter == 'Q') 
			{
				x = exists(x.mid, 'U');
				word.append('Q');
			}
			if (x != null) find(x, word, i);
		}
		
		return set;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word)
    {
    	if (contains(word)) 
    	{
    		int length = word.length();
    		switch(length)
    		{
    		case 3:
    		case 4: return 1;
    		case 5: return 2;
    		case 6: return 3;
    		case 7: return 5;
    		default:
    			if (length > 7) return 11;
    		}
    	}
    	return 0;
    }
    
    private boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != false;
    }

    private boolean get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with null argument");
        }
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.val;
    }
    
    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        char c = key.charAt(d);
        if      (c < x.c)              return get(x.left,  key, d);
        else if (c > x.c)              return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid,   key, d+1);
        else                           return x;
    }
    
    public static void main(String[] args) 
    {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
		System.out.println("The board input is:"); 
		System.out.println(board);
		
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            System.out.println(word);
            score += solver.scoreOf(word);
        }
        System.out.println("Score = " + score);
    }
}