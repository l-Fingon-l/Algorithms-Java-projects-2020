// faster but fatter version
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;

public class BoggleSolver
{
	private static final int R = 26;
	private Node root;      // root of trie
	private final int amount;
	private boolean[] marked;
	private char[] board;
	private int size; // width
	private int height;
	private SET<String> set;
	
	private static class Node {
        private Node[] next = new Node[R];
        private boolean isString;
    }
	
	private void add(String key) {
        if (key == null) throw new IllegalArgumentException("argument to add() is null");
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) 
        	x.isString = true;
        else {
            char c = (char) (key.charAt(d) - 'A');
            x.next[c] = add(x.next[c], key, d+1);
        }
        return x;
    }
	
	private boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.isString;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = (char) (key.charAt(d) - 'A');
        return get(x.next[c], key, d+1);
    }
	
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary)
    {
//    	Knuth.shuffle(dictionary);
		amount = dictionary.length;
		for (int i = 0; i < amount; i++)
			if (dictionary[i].length() > 2)
				add(dictionary[i]);	
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
			word.append(letter);
			if (x != null && letter == 'Q') 
			{
				x = exists(x, 'U');
				word.append('U');
			}
			if (x != null) 
				find(x, word, i);
		}
		
		return set;
    }
    
    private void find(Node x, StringBuilder word, int tile)
	{
		marked[tile] = true;
		if (x.isString) 
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
					Node next = exists(x, letter);
					if (next != null)
					{
						word.append(letter);
						if (letter == 'Q')
						{
							next = exists(next, 'U');
							if (next != null) 
							{
								word.append('U');
								find(next, word, id);
								word.deleteCharAt(word.length() - 1);
							}
						}
						else find(next, word, id);

						word.deleteCharAt(word.length() - 1);
					}
				}
			}
		
		marked[tile] = false;
	}
    
    private Node exists(Node x, char c)
	{
		if (x == null) return null;
        return x.next[c - 'A'];
	}
    
    private boolean onBoard(int x, int y)
	{
		if (x < 0 || x > size - 1 || y < 0 || y > height - 1) return false;
		else return true;
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
    
    public static void main(String[] args) 
    {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        for (int i = 0; i < 100000; i++)
        {
        	BoggleBoard board = new BoggleBoard();
        	solver.getAllValidWords(board);
        }
        /*
        BoggleBoard board = new BoggleBoard(args[1]);
		System.out.println("The board input is:"); 
		System.out.println(board);
		
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            System.out.println(word);
            score += solver.scoreOf(word);
        }
        System.out.println("Score = " + score); */
    }
}