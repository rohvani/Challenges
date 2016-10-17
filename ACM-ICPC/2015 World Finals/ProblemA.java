import java.util.Scanner;

public class ProblemA {

	public static void main(String[] args)
	{
		Scanner s = new Scanner(System.in);
		
		int p = s.nextInt(), a = s.nextInt(), b = s .nextInt(), c = s.nextInt(), d = s.nextInt();
		int n = s.nextInt();
		 
		double currentMax = Double.MIN_VALUE;
		double currentMin = Double.MAX_VALUE;
		double biggestDiff = 0;
		
		for(int i = 1; i <= n; i++)
		{
			double price = price(i, a, b, c, d, p);
			
			if(price > currentMax) {
				currentMax = price;
				currentMin = Float.MAX_VALUE;
			}
			
			if(price < currentMin) {
				currentMin = price;
				
				if(currentMax - currentMin > biggestDiff)
					biggestDiff = currentMax - currentMin;
			}
		}
		
		System.out.println(biggestDiff);
	}
	
	public static double price(int k, int a, int b, int c, int d, int p)
	{
		return (double) (p * (Math.sin(a * k + b) + Math.cos(c * k + d) + 2));
	}
	
}