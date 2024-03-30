/*
  Program Description:
  --------------------
  BaseballElimination.java implements algorithms to determine if a baseball
  team is eliminated from playoff contention in a division, based on the
  team's wins, losses, and remaining games against other teams.

  The class provides methods to:
  - Read team information from a file, including wins, losses, and remaining
    games against other teams.
  - Check if a team is trivially or non-trivially eliminated from playoff
    contention.
  - Find a certificate of elimination for non-trivially eliminated teams.

  Usage Instructions:
  -------------------
  1. Compile the program using the command: javac-algs4 BaseballElimination.java
  2. Execute the compiled program using the command:
     java-algs4 BaseballElimination <filename>
     - Replace <filename> with the path to the input file containing team
       information.

  Implementation Details:
  -----------------------
  - The BaseballElimination constructor reads team information from the input
    file and initializes the instance variables.
  - The isEliminated(String team) method checks if a team is eliminated from
    playoff contention, either trivially or non-trivially.
  - The certificateOfElimination(String team) method finds the certificate of
    elimination for a non-trivially eliminated team.
  - Trivial elimination occurs when the total number of wins possible for a
    team is less than the maximum wins of any other team in the division.
  - Non-trivial elimination is determined by constructing a flow network and
    finding the maximum flow using the Ford-Fulkerson algorithm.

  Dependencies:
  -------------
  - The program depends on the FlowEdge, FlowNetwork, and FordFulkerson
    classes from the algs4 library.

  Example:
  --------
  Upon execution, the BaseballElimination program reads team information
  from the specified input file. It then determines whether each team is
  eliminated from playoff contention, printing the results accordingly,
  along with any certificates of elimination for non-trivially eliminated
  teams.
*/
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BaseballElimination {
  private final Map<String, Integer> allTeams;
  private final String[] teamNames;
  private final int[] wins;
  private final int[] losses;
  private final int[] remaining;
  private final int maxWin;
  private int maxWinningTeamId;
  private final int[][] g;
  private final int n;
  private List<String> list;

  public BaseballElimination(String filename) {
    if (filename == null) throw new IllegalArgumentException("argument is null");

    In in = new In(filename);
    n = Integer.parseInt(in.readLine());
    allTeams = new LinkedHashMap<>();
    teamNames = new String[n];
    wins = new int[n];
    losses = new int[n];
    remaining = new int[n];
    g = new int[n][n];

    int i = 0;
    int maxWins = 0;
    while (!in.isEmpty()) {
      String line = in.readString();
      allTeams.put(line, i);
      teamNames[i] = line;
      wins[i] = in.readInt();
      losses[i] = in.readInt();
      remaining[i] = in.readInt();
      for (int j = 0; j < n; j++) g[i][j] = in.readInt();
      if (wins[i] > maxWins) {
        maxWins = wins[i];
        maxWinningTeamId = i;
      }
      i++;
    }
    maxWin = maxWins;
  }

  public int numberOfTeams() {
    return teamNames.length;
  }

  public Iterable<String> teams() {
    return allTeams.keySet();
  }

  public int wins(String team) {
    validate(team);
    return wins[allTeams.get(team)];
  }

  public int losses(String team) {
    validate(team);
    return losses[allTeams.get(team)];
  }

  public int remaining(String team) {
    validate(team);
    return remaining[allTeams.get(team)];
  }

  public int against(String team1, String team2) {
    validate(team1);
    validate(team2);
    return g[allTeams.get(team1)][allTeams.get(team2)];
  }

  public boolean isEliminated(String team) {
    validate(team);
    if (trivialElimination(team)) return true;
    return nonTrivialElimination(team);
  }

  public Iterable<String> certificateOfElimination(String team) {
    validate(team);
    if (!isEliminated(team)) return null;
    if (trivialElimination(team)) {
      list = new LinkedList<>();
      list.add(teamNames[maxWinningTeamId]);
    } else nonTrivialElimination(team);
    return list;
  }

  private void validate(String name) {
    if (name == null) throw new IllegalArgumentException("argument is null");
    for (String str : teamNames) if (name.equals(str)) return;
    throw new IllegalArgumentException("argument is illegal");
  }

  private boolean trivialElimination(String team) {
    return wins[allTeams.get(team)] + remaining[allTeams.get(team)] < maxWin;
  }

  private boolean nonTrivialElimination(String team) {
    int teamID = allTeams.get(team);
    int size = (n * (n - 1)) / 2;
    FlowNetwork flowNetwork = new FlowNetwork(size + 2);
    int s = size;
    int t = size + 1;

    int gameIndex = n - 1, firstTeamId, secondTeamId, totalRemaining = 0;
    for (int i = 0; i < n; i++) {
      if (i == teamID) continue;
      if (i > teamID) firstTeamId = i - 1;
      else firstTeamId = i;
      for (int j = i + 1; j < n; j++) {
        if (j == teamID) continue;
        if (j > teamID) secondTeamId = j - 1;
        else secondTeamId = j;
        flowNetwork.addEdge(new FlowEdge(s, gameIndex, g[i][j]));
        totalRemaining += g[i][j];
        flowNetwork.addEdge(new FlowEdge(gameIndex, firstTeamId, Double.POSITIVE_INFINITY));
        flowNetwork.addEdge(new FlowEdge(gameIndex++, secondTeamId, Double.POSITIVE_INFINITY));
      }
    }

    for (int i = 0; i < n; i++) {
      if (i == teamID) continue;
      if (i > teamID) firstTeamId = i - 1;
      else firstTeamId = i;
      int capacityToTarget = wins[teamID] + remaining[teamID] - wins[i];
      if (capacityToTarget < 0) continue;
      flowNetwork.addEdge(new FlowEdge(firstTeamId, t, capacityToTarget));
    }

    FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, s, t);

    list = new LinkedList<>();
    for (int i = 0; i < n; i++) {
      if (i == teamID) continue;
      if (i > teamID) firstTeamId = i - 1;
      else firstTeamId = i;
      if (fordFulkerson.inCut(firstTeamId)) list.add(teamNames[i]);
    }

    return (int) fordFulkerson.value() != totalRemaining;
  }

  public static void main(String[] args) {
    BaseballElimination division = new BaseballElimination(args[0]);
    for (String team : division.teams()) {
      if (division.isEliminated(team)) {
        StdOut.print(team + " is eliminated by the subset R = { ");
        for (String t : division.certificateOfElimination(team)) {
          StdOut.print(t + " ");
        }
        StdOut.println("}");
      } else StdOut.println(team + " is not eliminated");
    }
  }
}
