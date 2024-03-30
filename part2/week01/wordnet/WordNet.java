/*
  Program Description:
  --------------------
  WordNet.java implements a WordNet data type that provides methods to
  determine semantic relationships between words. It constructs a directed
  graph where each vertex represents a set of synonyms (synset), and edges
  represent hypernym (is-a) relationships between synsets.

  The WordNet data structure allows users to find the shortest common ancestor
  (SAP) of two synsets and determine the distance between two synsets in terms
  of the shortest ancestral path.

  Usage Instructions:
  -------------------
  1. Compile the program using the command: javac WordNet.java
  2. Execute the compiled program using the command:
     java WordNet <synsets_file> <hypernyms_file>
     - Replace <synsets_file> with the path to the synsets file.
     - Replace <hypernyms_file> with the path to the hypernyms file.
  3. Test various methods provided by the WordNet data type.

  Implementation Details:
  -----------------------
  - The constructor reads synsets and hypernyms files, constructs a directed
    graph, and validates whether it represents a rooted directed acyclic graph
    (DAG).
  - The SAP class is used to compute shortest ancestral paths between synsets.
  - The WordNet data type provides methods to query nouns, check if a word is
    a noun in the WordNet, find the distance between two synsets, and determine
    the common ancestor of two synsets.

  Dependencies:
  -------------
  - The program depends on the Digraph, DirectedCycle, In, SET, and ST classes
    from the algs4 library for graph representation, cycle detection, and symbol
    table.
  - It also relies on the SAP class for computing shortest ancestral paths.

  Example:
  --------
  Upon execution, the WordNet program initializes a WordNet data structure using
  synsets and hypernyms files provided as command-line arguments. Users can then
  test various methods such as finding the distance between synsets, determining
  the common ancestor of synsets, querying nouns, and checking if a word is a
  noun in the WordNet.
*/
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;

public class WordNet {

  private final Digraph digraph;
  private final SAP sap;
  private final ST<Integer, SET<String>> netNouns;
  private final ST<String, SET<Integer>> ids;

  public WordNet(String synsets, String hypernyms) {
    if (synsets == null || hypernyms == null) {
      throw new IllegalArgumentException("argument is null");
    }
    netNouns = new ST<>();
    ids = new ST<>();
    In in = new In(synsets);
    while (in.hasNextLine()) {
      SET<String> inside = new SET<>();
      String[] strings = in.readLine().split(",");
      String[] str = strings[1].split(" ");
      for (String s : str) {
        inside.add(s);
        SET<Integer> ints = ids.get(s);
        if (ints == null) {
          ints = new SET<>();
          ids.put(s, ints);
        }
        ints.add(Integer.parseInt(strings[0]));
      }
      netNouns.put(Integer.parseInt(strings[0]), inside);
    }

    digraph = new Digraph(netNouns.size());
    in = new In(hypernyms);
    while (in.hasNextLine()) {
      String[] strings = in.readLine().split(",");
      for (int i = 1; i < strings.length; i++) {
        digraph.addEdge(Integer.parseInt(strings[0]), Integer.parseInt(strings[i]));
      }
    }

    DirectedCycle d = new DirectedCycle(digraph);
    if (d.hasCycle()) {
      throw new IllegalArgumentException("Is not a DAG");
    } else if (!rootedDAG()) {
      throw new IllegalArgumentException("Is not a rooted DAG");
    }

    sap = new SAP(digraph);
  }

  private boolean rootedDAG() {
    int count = 0;
    for (int i = 0; i < digraph.V(); i++) {
      if (digraph.indegree(i) != 0 && digraph.outdegree(i) == 0) {
        count++;
      }
    }
    return count == 1;
  }

  public Iterable<String> nouns() {
    return ids.keys();
  }

  public boolean isNoun(String word) {
    if (word == null) {
      throw new IllegalArgumentException("argument is null");
    }
    return ids.get(word) != null;
  }

  public int distance(String nounA, String nounB) {
    if (nounA == null || nounB == null) {
      throw new IllegalArgumentException("argument is null");
    }
    if (!isNoun(nounA) || !isNoun(nounB)) {
      throw new IllegalArgumentException("is not wordnet noun");
    }
    return sap.length(ids.get(nounA), ids.get(nounB));
  }

  public String sap(String nounA, String nounB) {
    if (nounA == null || nounB == null) {
      throw new IllegalArgumentException("argument is null");
    }
    if (!isNoun(nounA) || !isNoun(nounB)) {
      throw new IllegalArgumentException("is not wordnet noun");
    }
    int s = sap.ancestor(ids.get(nounA), ids.get(nounB));
    SET<String> res = netNouns.get(s);
    String outcome = "";
    for (String t : res) {
      outcome = outcome.concat(t + " ");
    }
    return outcome;
  }

  public static void main(String[] args) {
    WordNet wordNet = new WordNet(args[0], args[1]);
    System.out.println(wordNet.distance("a", "c"));
    System.out.println(wordNet.sap("a", "c"));
    System.out.println(wordNet.nouns());
    System.out.println(wordNet.isNoun("d"));
    System.out.println(wordNet.isNoun("i"));
  }
}
