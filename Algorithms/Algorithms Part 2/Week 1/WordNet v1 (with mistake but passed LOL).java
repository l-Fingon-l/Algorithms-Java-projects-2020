import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import java.util.HashMap;
import java.util.ArrayList;

public class WordNet 
{
	private HashMap<String, ArrayList<Integer>> Words;
	private HashMap<Integer, String> Synsets;
	private Digraph Hypernyms;
	private SAP Sap;
	private int size = 0;
	
   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms)
   {
	   if (synsets == null) throw new IllegalArgumentException("The synset file is 'null'!");
	   if (hypernyms == null) throw new IllegalArgumentException("The hypernyms file is 'null'!");
	   
	   In in_words = new In(synsets);
	   Words = new HashMap<String, ArrayList<Integer>>();
	   Synsets = new HashMap<Integer, String>();
       while (in_words.hasNextLine()) 
       {
           String line = in_words.readLine();
           String[] tokens = line.split(",");
           int id = Integer.parseInt(tokens[0]);
           Synsets.put(id, tokens[1]);
           String[] words_ = tokens[1].split(" ");
           for (String word: words_)
        	   if (!Words.containsKey(word))
               {
        		   ArrayList<Integer> ids = new ArrayList<Integer>();
        		   ids.add(id);
            	   Words.put(word, ids);
               }
        	   else Words.get(word).add(id);
           size++;
       }
       
       In in_hypernyms = new In(hypernyms);
       Hypernyms = new Digraph(size);
       while(in_hypernyms.hasNextLine())
       {
    	   String line = in_hypernyms.readLine();
           String[] tokens = line.split(",");
           for (int i = 1; i < tokens.length; i++)
        	   Hypernyms.addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
       }
       
       ShortestDirectedCycle sdc = new ShortestDirectedCycle(Hypernyms);
       if (!sdc.acyclic()) throw new IllegalArgumentException("The hypernyms graph is not a DAG!");
       Sap = new SAP(Hypernyms);
   }

   // returns all WordNet nouns
   public Iterable<String> nouns()
   {
	   SET<String> result = new SET<String>();
	   for (String word: Words.keySet())
		   result.add(word);
	   return result;
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word)
   {
	   if (word == null) throw new IllegalArgumentException("The word is 'null'!");
	   return Words.containsKey(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB)
   {
	   if (nounA == null) throw new IllegalArgumentException("The word A is 'null'!");
	   if (nounB == null) throw new IllegalArgumentException("The word B is 'null'!");
	   if (!isNoun(nounA)) throw new IllegalArgumentException("The noun A is not contained in a WordNet!");
	   if (!isNoun(nounB)) throw new IllegalArgumentException("The noun B is not contained in a WordNet!");
	   
	   if (nounA.compareTo(nounB) == 0) return 0;
	   
	   ArrayList<Integer> Set1 = Words.get(nounA);
	   ArrayList<Integer> Set2 = Words.get(nounB);
	   
	   if (Set1.size() == 1 && Set2.size() == 1) return Sap.length(Set1.get(0), Set2.get(0));
	   else return Sap.length(Set1, Set2);
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)
   {
	   if (nounA == null) throw new IllegalArgumentException("The word A is 'null'!");
	   if (nounB == null) throw new IllegalArgumentException("The word B is 'null'!");
	   if (!isNoun(nounA)) throw new IllegalArgumentException("The noun A is not contained in a WordNet!");
	   if (!isNoun(nounB)) throw new IllegalArgumentException("The noun B is not contained in a WordNet!");
	   
	   if (nounA.compareTo(nounB) == 0) return nounA;
	   
	   ArrayList<Integer> Set1 = Words.get(nounA);
	   ArrayList<Integer> Set2 = Words.get(nounB);
	   
	   if (Set1.size() == 1 && Set2.size() == 1) return Synsets.get(Sap.ancestor(Set1.get(0), Set2.get(0)));
	   else return Synsets.get(Sap.length(Set1, Set2));
   }

   // do unit testing of this class
   public static void main(String[] args)
   {
	   
   }
}