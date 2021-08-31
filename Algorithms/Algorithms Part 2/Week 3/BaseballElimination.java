import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.HashMap;
import java.util.ArrayList;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;

public class BaseballElimination 
{
	private HashMap<String, Integer> team_id;
	private Team[] teams;
	
	private class Team
	{
		public String name; 
		public int wins;
		public int losses;
		public int remaining;
		public int[] against;
		public boolean isEliminated;
		public boolean eliminationCalculated;
		public ArrayList<String> certificateOfElimination;
		public Team(String name, int wins, int losses, int remaining, int[] against)
		{
			this.name = name;
			this.wins = wins;
			this.losses = losses;
			this.remaining = remaining;
			this.against = against.clone();
		}
	}
	
	private int team_id(String team)
	{
		return team_id.get(team);
	}
	
	public BaseballElimination(String filename) // create a baseball division from given filename in format specified below
	{
		if (filename == null) throw new IllegalArgumentException("The filename is null!");
		
		In in = new In(filename);
		int size = in.readInt();
		team_id = new HashMap<>(size);
		teams = new Team[size];
		
		for (int i = 0; i < size; i++)
		{
			String name = in.readString();
			int wins = in.readInt();
			int losses = in.readInt();
			int remaining = in.readInt();
			int[] against = new int[size];
			for (int j = 0; j < size; j++)
				against[j] = in.readInt();
			teams[i] = new Team(name, wins, losses, remaining, against);
			team_id.put(name, i);
		}
	}
	
	public int numberOfTeams() // number of teams
	{
		return team_id.size();
	}
	
	public Iterable<String> teams()  // all teams
	{
		ArrayList<String> result = new ArrayList<>(team_id.size());
		for (int i = 0; i < team_id.size(); i++)
			result.add(teams[i].name);
		
		return result;
	}
	
	public int wins(String team) // number of wins for given team
	{
		validateTeam(team);
		return teams[team_id(team)].wins;
	}
	
	public int losses(String team) // number of losses for given team
	{
		validateTeam(team);
		return teams[team_id(team)].losses;
	}
	
	public int remaining(String team) // number of remaining games for given team
	{
		validateTeam(team);
		return teams[team_id(team)].remaining;
	}
	
	public int against(String team1, String team2) // number of remaining games between team1 and team2
	{
		validateTeam(team1);
		validateTeam(team2);
		return teams[team_id(team1)].against[team_id(team2)];
	}
	
	public boolean isEliminated(String team) // is given team eliminated?
	{
		validateTeam(team);
		if (!teams[team_id(team)].eliminationCalculated)
			calculateElimination(team);
		
		return teams[team_id(team)].isEliminated;
	}
	
	public Iterable<String> certificateOfElimination(String team) // subset R of teams that eliminates given team; null if not eliminated
	{
		validateTeam(team);
		if (!teams[team_id(team)].eliminationCalculated)
			calculateElimination(team);
		
		if (teams[team_id(team)].isEliminated) return teams[team_id(team)].certificateOfElimination;
		else return null;
	}
	
	private void calculateElimination(String team)
	{
		// Trivial elimination 
		int id = team_id(team); 
		int max_wins = teams[id].wins + teams[id].remaining;
		ArrayList<String> certificate = new ArrayList<>(); 
		for (int i = 0; i < team_id.size(); i++) 
			if (teams[i].wins > max_wins)
			{
				certificate.add(teams[i].name);
				teams[id].isEliminated = true;
			}
		if (teams[id].isEliminated)
		{
			teams[id].eliminationCalculated = true;
			teams[id].certificateOfElimination = certificate;
			return;
		}

		// Nontrivial elimination 
		ArrayList<GameVertex> game_vertices = new ArrayList<>(team_id.size() - 1);
		
		int flow = 0;
		int size = team_id.size() + 1;
		for (int i = 0; i < team_id.size() - 1; i++)
			if (i != id)
				for (int j = i + 1; j < team_id.size(); j++)
					if (j != id && teams[i].against[j] != 0)
					{
						game_vertices.add(new GameVertex(i, j));
						flow += teams[i].against[j];
						size++;
					}
		size++; // for t-vertex
		
		FlowNetwork G = new FlowNetwork(size);
		int id_ = team_id.size() + 1;
		for (GameVertex v: game_vertices)
		{
			G.addEdge(new FlowEdge(0, id_, teams[v.team1].against[v.team2]));
			G.addEdge(new FlowEdge(id_, v.team1 + 1, Double.POSITIVE_INFINITY));
			G.addEdge(new FlowEdge(id_, v.team2 + 1, Double.POSITIVE_INFINITY));
			id_++;
		}
		
		for (int i = 0; i < team_id.size(); i++)
			if (i != id)
				G.addEdge(new FlowEdge(i + 1, size - 1, max_wins - teams[i].wins));
		
		FordFulkerson maxflow = new FordFulkerson(G, 0, size - 1);
		if (maxflow.value() == flow)
		{
			teams[id].eliminationCalculated = true;
			return;
		}
		
		for (int i = 0; i < team_id.size(); i++)
			if (i != id && maxflow.inCut(i + 1))
				certificate.add(teams[i].name);
		teams[id].eliminationCalculated = true;
		teams[id].isEliminated = true;
		teams[id].certificateOfElimination = certificate;
	}
	
	private class GameVertex
	{
		public int team1;
		public int team2;
		public GameVertex(int team1, int team2) { this.team1 = team1; this.team2 = team2; }
	}
	
	private void validateTeam(String team)
	{
		if (team == null) throw new IllegalArgumentException("Team is null!");
		if (!team_id.containsKey(team)) throw new IllegalArgumentException(team + " is an invalid team!");
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
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
	    }
	}
}
