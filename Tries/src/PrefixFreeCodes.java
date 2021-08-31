public class PrefixFreeCodes 
{
	private Node root;
	private boolean isPrefixFree = true;
	
	private static class Node
	{
		private int value;
		private Node[] next = new Node[2];
	}
	
	public boolean PrefixCheck(String[] set)
	{
		root = new Node();
		isPrefixFree = true;
		for(int i = 0; i < set.length; i++)
			put(set[i], 1);
		return isPrefixFree;
	}
	
	public void put(String key, int val)
	{
		root = put(root, key, val, 0);
	}
	
	private Node put(Node x, String key, int val, int d)
	{
		if (x == null) x = new Node();
		if (d == key.length())
		{
			x.value = val;
			return x;
		}
		if (x.value == 1) // modification to detect the prefix reuse
		{
			isPrefixFree = false;
			return x;
		}
		char c = (char) (key.charAt(d) - '0');
		x.next[c] = put(x.next[c], key, val, d + 1);
		return x;
	}
	
	public static void main(String[] args) 
	{
		String[] set1 = {"01", "10", "0010", "1111"};
		String[] set2 = {"01", "10", "0010", "10100"};
		PrefixFreeCodes pfc = new PrefixFreeCodes();
		System.out.println("Set1 is prefix-free: " + pfc.PrefixCheck(set1));
		System.out.println("Set2 is prefix-free: " + pfc.PrefixCheck(set2));
	}
}
