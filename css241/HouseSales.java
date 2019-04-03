package css241;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HouseSales {

	public static void main(String[] args) throws FileNotFoundException {
		final int ARRAYLENGTH = 20;
		String[] HouseAddress = new String[ARRAYLENGTH];
		String[] HousePrice = new String[ARRAYLENGTH];
		
		Scanner input = new Scanner(new File("buyem.dat"));
		
		int counter = 0;
		while(input.hasNextLine() || counter == ARRAYLENGTH) {
			HouseAddress[counter] = input.nextLine();
			HousePrice[counter] = input.next();
		}

	}
	
	

}
