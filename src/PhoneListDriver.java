package src;

import java.io.*;

public class PhoneListDriver {

	

	public static void main(String[] args) throws FileNotFoundException {
		PhoneList list = new PhoneList(true);
		File output = new File("outputPhone.txt");
		PrintStream writer = new PrintStream(output);
		PrintStream writer2 = new PrintStream(System.out);
		
		try {
			list.readList("phonerecords.csv");
			list.printList(writer);
			list.printList(writer2);
			
			
			
		} catch (FileNotFoundException e) {
			System.out.print("FileNotFound");
			e.printStackTrace();
		}

	}

}
