public class InorderTraversal 
{
	public class Node 
	{ 
	    int key; 
	    Node left, right; 
	  
	    public Node(int item) 
	    { 
	        key = item; 
	        left = right = null; 
	    } 
	} 
	
	public class BinaryTree 
	{ 
	    // Root of Binary Tree 
	    Node root; 
	  
	    BinaryTree() 
	    { 
	        root = null; 
	    } 
	    
	    void Traverse (Node node)
	    {
	    	boolean side = false;
	    	boolean up = false;
	    	Node prev = new Node(node.key);
	    	while (true)
	    	{
	    		if (!up)
	    		{
	    			
	    		}
	    		else 
	    		
	    		
	    		if (node.left != null)
	    	}
	    	
	    	if (node == null) 
	            return; 
	  
	        // first recur on left subtree 
	        printPostorder(node.left); 
	  
	        // then recur on right subtree 
	        printPostorder(node.right); 
	  
	        // now deal with the node 
	        System.out.print(node.key + " "); 
	    }
	}
	
	public void main(String[] args)
	{
		BinaryTree tree = new BinaryTree(); 
        tree.root = new Node(3); 
        tree.root.left = new Node(1); 
        tree.root.right = new Node(4); 
        tree.root.left.right = new Node(2); 
        tree.root.right.right = new Node(5); 
	}
}
