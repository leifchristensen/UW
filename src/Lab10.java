package src;



public class Lab10{
	public static int counter;
	
	public static void main(String[] args) {
		/*counter = 0;
		System.out.println(geom(3));
		
		counter = 0;
		System.out.println(sqrt(50,8,0.1));
		*/
		System.out.print(interest(5,1000.00,0.12));
		
	}
	
	public static int geom(int levels) {
		counter++;
		if(levels == 0) {
			System.out.println("Val: " + 1 +" Base: 1"+ " Counter: " + counter);
			return 1;
		}
		else {
			int i = (4 * geom(levels-1));
			System.out.println("Val: " + i + " Recurse Level: " + levels + " Counter: " + counter);
			return i;
		}
		
		
	}


	public static double sqrt(double n, double approx, double tol) {
		counter++;
		System.out.println("Base: "+ n + " Tolerance: " + tol + " Counter: " + counter + " Approx: " + approx);
		
		if(Math.abs(Math.pow(approx, 2)-n ) <= tol) {
			return approx;
		} 
		else {		
			return sqrt(n,Math.abs((Math.pow(approx, 2)+n)/(2 * approx)),tol);
		}
	}
	
	public static double interest(int years, double amount, double rate) {
		if(years == 0) {
			return amount;
		}
		
		return interest(years - 1, amount * (1+rate), rate);
	}
}
