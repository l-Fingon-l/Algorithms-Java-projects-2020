import edu.princeton.cs.algs4.TST;

public class SuffixTreeUncompressed 
{
	private TST<Boolean> tree;
	
	public SuffixTreeUncompressed(String word)
	{ 
		tree = new TST<>();
		for(int i = 0; i < word.length(); i++)
			tree.put(word.substring(i), true); 
	}
}
