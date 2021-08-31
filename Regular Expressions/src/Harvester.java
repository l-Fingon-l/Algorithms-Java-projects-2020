import edu.princeton.cs.algs4.In;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class Harvester
{
	public static void main(String[] args)
	 {
		String regexp = args[0];
		In in = new In(args[1]);
		String input = in.readAll();
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(input);
		while (matcher.find())
			System.out.println(matcher.group());
	 }
} 
