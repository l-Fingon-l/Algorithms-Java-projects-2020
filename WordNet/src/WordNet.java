import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Topological;
import java.util.HashMap;
import java.util.ArrayList;

public class WordNet 
{
	private HashMap<String, ArrayList<Integer>> Words;
	private HashMap<Integer, String> Synsets;
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
           Synsets.put(id, new String(tokens[1]));
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
       Digraph Hypernyms = new Digraph(size);
       while(in_hypernyms.hasNextLine())
       {
    	   String line = in_hypernyms.readLine();
           String[] tokens = line.split(",");
           for (int i = 1; i < tokens.length; i++)
        	   Hypernyms.addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[i]));
       }
       
       Topological rdc = new Topological(Hypernyms);
       if (!rdc.hasOrder()) throw new IllegalArgumentException("The graph is not a DAG!");
       
       int amount = 0;
       for (int v: rdc.order())
    	   if (Hypernyms.outdegree(v) == 0) amount++;
       if (amount != 1) throw new IllegalArgumentException("The DAG is not rooted!");
       
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
	   if (nounA == null) throw new IllegalArgumentException("The word " + nounA + " is 'null'!");
	   if (nounB == null) throw new IllegalArgumentException("The word " + nounB + " is 'null'!");
	   if (!isNoun(nounA)) throw new IllegalArgumentException("The noun " + nounA + " is not contained in a WordNet!");
	   if (!isNoun(nounB)) throw new IllegalArgumentException("The noun " + nounB + " is not contained in a WordNet!");
	   
	   if (nounA.compareTo(nounB) == 0) return 0;
	   
	   ArrayList<Integer> Set1 = Words.get(nounA);
	   ArrayList<Integer> Set2 = Words.get(nounB);
	   
	   return Sap.length(Set1, Set2);
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)
   {
	   if (nounA == null) throw new IllegalArgumentException("The word " + nounA + " is 'null'!");
	   if (nounB == null) throw new IllegalArgumentException("The word " + nounB + " is 'null'!");
	   if (!isNoun(nounA)) throw new IllegalArgumentException("The noun " + nounA + " is not contained in a WordNet!");
	   if (!isNoun(nounB)) throw new IllegalArgumentException("The noun " + nounB + " is not contained in a WordNet!");
	   
	   if (nounA.compareTo(nounB) == 0) return Synsets.get(Words.get(nounA).get(0));
	   
	   ArrayList<Integer> Set1 = Words.get(nounA);
	   ArrayList<Integer> Set2 = Words.get(nounB);
	   
	   return Synsets.get(Sap.ancestor(Set1, Set2));
   }
   
// distance between nounA and nounB (defined below)
   public int distance(String[] nounA, String[] nounB)
   {
	   if (nounA == null) throw new IllegalArgumentException("The word " + nounA + " is 'null'!");
	   if (nounB == null) throw new IllegalArgumentException("The word " + nounB + " is 'null'!");
	   for (String x: nounA) 
		   if (!isNoun(x)) throw new IllegalArgumentException("The noun " + x + " is not contained in a WordNet!");
	   for (String x: nounB)
		   if (!isNoun(x)) throw new IllegalArgumentException("The noun " + x + " is not contained in a WordNet!");
	   
	   ArrayList<Integer> Set1 = new ArrayList<>();
	   for (String x: nounA)
		   Set1.addAll(Words.get(x));
	   ArrayList<Integer> Set2 = new ArrayList<>();
	   for (String x: nounB)
		   Set1.addAll(Words.get(x));
	   
	   return Sap.length(Set1, Set2);
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String[] nounA, String[] nounB)
   {
	   if (nounA == null) throw new IllegalArgumentException("The word " + nounA + " is 'null'!");
	   if (nounB == null) throw new IllegalArgumentException("The word " + nounB + " is 'null'!");
	   for (String x: nounA) 
		   if (!isNoun(x)) throw new IllegalArgumentException("The noun " + x + " is not contained in a WordNet!");
	   for (String x: nounB)
		   if (!isNoun(x)) throw new IllegalArgumentException("The noun " + x + " is not contained in a WordNet!");
	   
	   ArrayList<Integer> Set1 = new ArrayList<>();
	   for (String x: nounA)
		   Set1.addAll(Words.get(x));
	   ArrayList<Integer> Set2 = new ArrayList<>();
	   for (String x: nounB)
		   Set1.addAll(Words.get(x));
	   
	   return Synsets.get(Sap.ancestor(Set1, Set2));
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String[] nounA)
   {
	   if (nounA == null) throw new IllegalArgumentException("The word " + nounA + " is 'null'!");
	   for (String x: nounA) 
		   if (!isNoun(x)) throw new IllegalArgumentException("The noun " + x + " is not contained in a WordNet!");
	   
	   ArrayList<Integer> Set1 = Words.get(nounA[0]);
	   ArrayList<Integer> Set2 = new ArrayList<>();
	   for (int i = 1; i < nounA.length; i++)
	   {
		   Set2 = Words.get(nounA[i]);
		   int x = Sap.ancestor(Set1, Set2);
		   Set1 = new ArrayList<>();
		   Set1.add(x);
	   }
	   
	   return Synsets.get(Set1.get(0));
   }


   // do unit testing of this class
   public static void main(String[] args)
   {
	   WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
	   System.out.println(wordnet.distance("warcraft", "map"));
	   System.out.println(wordnet.sap("warcraft", "map"));
   }
}