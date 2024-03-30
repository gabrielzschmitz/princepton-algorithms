/*
  Program Description:
  --------------------
  Outcast.java implements an Outcast data type that identifies the outcast
  noun in a group of nouns based on their distances in a WordNet.

  The Outcast class provides a method to determine the noun that is least
  related to the others in the group. It computes the sum of distances from
  each noun in the group to every other noun and identifies the noun with
  the maximum sum as the outcast.

  Usage Instructions:
  -------------------
  1. Compile the program using the command: javac Outcast.java
  2. Execute the compiled program using the command:
  java-algs4 Outcast <synsets_file> <hypernyms_file> <noun_list_file1> <noun_list_file2> ...
  - Replace <synsets_file> with the path to the synsets file.
  - Replace <hypernyms_file> with the path to the hypernyms file.
  - Replace <noun_list_file1>, <noun_list_file2>, etc., with the paths to the
	files containing lists of nouns.

  Implementation Details:
  -----------------------
  - The Outcast constructor initializes the Outcast data structure with a
	WordNet provided as input.
  - The outcast(String[] nouns) method identifies the outcast noun from an array
    of nouns based on their distances in the WordNet.
  - The main method reads lists of nouns from input files and prints the outcast
    noun for each list.

  Dependencies:
  -------------
  - The program depends on the WordNet class for WordNet representation.

  Example:
  --------
  Upon execution, the Outcast program initializes an Outcast data structure
  using a WordNet provided as input. It then identifies the outcast noun for
  each list of nouns provided in separate input files and prints the result.
*/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

  private final WordNet net;

  public Outcast(WordNet wordnet) {
    net = wordnet;
  }

  public String outcast(String[] nouns) {
    int max = 0;
    int sum;
    int t = -1;
    for (int j = 0; j < nouns.length; j++) {
      sum = 0;
      for (int i = 0; i < nouns.length; i++) {
        sum += net.distance(nouns[j], nouns[i]);
      }
      if (sum > max) {
        max = sum;
        t = j;
      }
    }
    return nouns[t];
  }

  public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
      In in = new In(args[t]);
      String[] nouns = in.readAllStrings();
      StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
  }
}
