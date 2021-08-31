import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast 
{
	private WordNet wordnet;
   
	public Outcast(WordNet wordnet)         // constructor takes a WordNet object
    {
	   this.wordnet = wordnet;
    }
	
    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
    {
    	int biggest_id = 0;
    	for (int i = 0, biggest = 0; i < nouns.length; i++)
    	{
    		int d = 0;
    		for (int j = 0; j < nouns.length; j++)
    			d += wordnet.distance(nouns[i], nouns[j]);
    		if (d > biggest)
    		{
    			biggest = d;
    			biggest_id = i;
    		}
    	}
    	
    	return nouns[biggest_id];
    }

    public static void main(String[] args) 
    {/*
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);  
   
        for (int t = 2; t < args.length; t++) 
        {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        } */
        
    	WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        In in = new In(args[0]);
        String[] nouns = in.readAllStrings();
 //       System.out.println("Lishnee: " + outcast.outcast(nouns));
        System.out.println("Obshee: " + wordnet.sap(nouns));
    }
}