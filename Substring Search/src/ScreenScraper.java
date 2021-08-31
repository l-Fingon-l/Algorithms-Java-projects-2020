import edu.princeton.cs.algs4.In;
public class ScreenScraper 
{
	public static void main(String[] args)
	{
		In in = new In("https://tft.w3replayers.com/replay/29739");
		String text = in.readAll();
		int start = text.indexOf("username\'>Fingon", 0);
		int from = text.indexOf("\'apm\'>(", start);
		int to = text.indexOf(")</div>", from);
		String apm = text.substring(from + 7, to);
		System.out.println(args[1] + ": " + apm);
	}
}
