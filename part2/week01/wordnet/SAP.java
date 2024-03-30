/*
  Program Description:
  --------------------
  SAP.java implements a Shortest Ancestral Path (SAP) data type that provides
  methods for finding the length of the shortest ancestral path and the common
  ancestor in a directed graph. It uses breadth-first search to efficiently
  compute the shortest ancestral path between vertices in the graph.

  The SAP class supports both single-source and multiple-source queries to find
  the shortest ancestral path and common ancestor between vertices.

  Usage Instructions:
  -------------------
  1. Compile the program using the command: javac SAP.java
  2. Execute the compiled program using the command:
     java SAP <input_file>
     - Replace <input_file> with the path to the file containing the directed
       graph representation.
  3. Input vertex pairs through standard input to test single-source queries.

  Implementation Details:
  -----------------------
  - The SAP constructor initializes the SAP data structure with a directed graph
    provided as input.
  - The length(int v, int w) and ancestor(int v, int w) methods find the length
    of the shortest ancestral path and the common ancestor between vertices v
    and w, respectively.
  - The length(Iterable<Integer> v, Iterable<Integer> w) and
    ancestor(Iterable<Integer> v, Iterable<Integer> w) methods support
    multiple-source queries, where v and w are iterable collections of vertices.

  Dependencies:
  -------------
  - The program depends on the Digraph and BreadthFirstDirectedPaths classes
    from the algs4 library for graph representation and breadth-first search.

  Example:
  --------
  Upon execution, the SAP program initializes a SAP data structure using a
  directed graph provided as input. Users can input vertex pairs through
  standard input to test the length and ancestor methods for single-source
  queries.
*/
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

  private final Digraph digraph;

  public SAP(Digraph G) {
    if (G == null) {
      throw new IllegalArgumentException("argument is null");
    }
    digraph = new Digraph(G);
  }

  public int length(int v, int w) {
    check(v, w);

    BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
    BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

    return result(bfsV, bfsW)[0];
  }

  public int ancestor(int v, int w) {
    check(v, w);

    BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
    BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

    return result(bfsV, bfsW)[1];
  }

  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    checkIterable(v, w);

    int c = 0, r = 0;
    for (int ignored : v) {
      c++;
    }
    for (int ignored : w) {
      r++;
    }

    if (c == 0 || r == 0) {
      return -1;
    }

    BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
    BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

    return result(bfsV, bfsW)[0];
  }

  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    checkIterable(v, w);

    int c = 0, r = 0;
    for (int ignored : v) {
      c++;
    }
    for (int ignored : w) {
      r++;
    }

    if (c == 0 || r == 0) {
      return -1;
    }

    BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
    BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

    return result(bfsV, bfsW)[1];
  }

  private void check(int v, int w) {
    if (v < 0 || w < 0 || v > digraph.V() - 1 || w > digraph.V() - 1) {
      throw new IllegalArgumentException("not in range");
    }
  }

  private void checkIterable(Iterable<Integer> v, Iterable<Integer> w) {
    if (v == null || w == null) {
      throw new IllegalArgumentException("argument is null");
    }
    for (Integer val : v) {
      if (val == null) {
        throw new IllegalArgumentException("value inside iterable is invalid");
      }
    }
    for (Integer val : w) {
      if (val == null) {
        throw new IllegalArgumentException("value inside iterable is invalid");
      }
    }
  }

  private int[] result(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
    int[] res = new int[2];
    int len = -1;
    int p = -1;

    for (int i = 0; i < digraph.V(); i++) {
      if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
        int lv = bfsV.distTo(i);
        int wv = bfsW.distTo(i);
        if (lv + wv < len || len == -1) {
          len = lv + wv;
          p = i;
        }
      }
    }
    res[0] = len;
    res[1] = p;
    return res;
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);

    // test 1(single source)
    while (!StdIn.isEmpty()) {
      int v = StdIn.readInt();
      int w = StdIn.readInt();
      int length = sap.length(v, w);
      int ancestor = sap.ancestor(v, w);
      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }

    // test 2(multiple source(iterable))
    // while (!StdIn.isEmpty()) {
    //   int m = StdIn.readInt();
    //   int n = StdIn.readInt();
    //   Stack<Integer> v = new Stack<>();
    //   Stack<Integer> w = new Stack<>();
    //   for (int i = 0; i < m; i++) {
    //     v.push(StdIn.readInt());
    //   }
    //   for (int i = 0; i < n; i++) {
    //     w.push(StdIn.readInt());
    //   }
    //   int length = sap.length(v, w);
    //   int ancestor = sap.ancestor(v, w);
    //   StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    // }
  }
}
