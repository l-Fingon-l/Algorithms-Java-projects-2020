import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats 
{
	private double[] thresholds;
	private Percolation Simulation;
	private double mean;
	private double stddev;
	private double confidenceLo;
	private double confidenceHi;
	
	private double performMonteCarloSimulation (int n)
	{
		Simulation = new Percolation(n);
		boolean grid[] = new boolean[n * n]; // let's try the copied grid for faster access
		for(int i = 0; i < n * n; i++) grid[i] = false; // copied grid
		while (!Simulation.percolates())
		{
			int x = Simulation.numberOfOpenSites();
			int y = n * n - x;
			int id = StdRandom.uniform(y); // copied grid
			int id_converted = -1; // copied grid
			for (int i = -1; i < id; id_converted++) if (!grid[id_converted + 1]) i++; // copied grid
			grid[id_converted] = true; // copied grid
			Simulation.open(id_converted / n + 1, id_converted % n + 1);
		}
		return (double)Simulation.numberOfOpenSites() / (n * n);
	}
	
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
    	if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Negative arguments are impossible!");
    	thresholds = new double[trials];
    	for (int i = 0; i < trials; i++) thresholds[i] = performMonteCarloSimulation(n);
    }

    // sample mean of percolation threshold
    public double mean()
    {
    	mean = StdStats.mean(thresholds);
    	return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
    	stddev = StdStats.stddev(thresholds);
    	return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
    	confidenceLo = mean - (1.96 * stddev) / Math.sqrt(thresholds.length);
    	return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
    	confidenceHi = mean + (1.96 * stddev) / Math.sqrt(thresholds.length);
    	return confidenceHi;
    }

   // test client (see below)
   public static void main(String[] args)
   {
	   int n = 0, T = 0;
	   n = Integer.parseInt(args[0]);
	   T = Integer.parseInt(args[1]);
	   PercolationStats Research = new PercolationStats(n, T);
	   System.out.println("mean                    = " + Research.mean());
	   System.out.println("stddev                  = " + Research.stddev());
	   System.out.println("95% confidence interval = [" + Research.confidenceLo() + ", " + Research.confidenceHi() + ']');
   }
}