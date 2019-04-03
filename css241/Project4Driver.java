package css241;

import java.io.*;
import java.util.*;

/** Project 4
 * 
 * @author Leif Christensen
 *
 */
public class Project4Driver {

	// Error checking implemented on getPathData, which feeds from Distance1Map getpath and distance methods.
	public static void main(String[] args) throws FileNotFoundException {
				
		File dictionary = new File("Files/dictionary.txt");
		File output = new File("Files/Project4.txt");
		
		// Creates file output writer
		// Same output stream is used for all output attempts of the main do loop.
		PrintStream outputStream = new PrintStream(output);
		
		// Runs at least one getPath instance, then prompts user for additional tries.
		
		// Error checker gets status messages from any error thrown in the methods called by getPathData.
		// Error handler prints these error messages and continues to prompt for extra input.
		do {
			
			try {
				getPathData(dictionary, outputStream);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			System.out.println();
			
			
		} while (continueChoice()); // Get user input to continue
		
		System.out.println("Goodbye!");
		
		outputStream.close();

	}

	private static void getPathData(File dictionary, PrintStream outputStream) throws Exception {
		// New instance of map
		Distance1Map distMap = new Distance1Map();
		
		// Creates file output writer
		//PrintStream outputStream = new PrintStream(output);
				
		
		// Sets the default distance between words to an invalid value.
		int dist = -1;
		
		// Prints the path between words to the console and file
		try{
			
			// Gets dictionary data
			Scanner dictScanner = new Scanner(dictionary);			
			distMap.lengthMap.read(dictScanner);
			
			// Gets user input for the two strings, root and search.
			String[] input = getInput();
			String pathString = distMap.getPath(input[0], input[1]);
			
			// Prints path as a string.
			System.out.println();
			System.out.println(pathString);
			
			// Writes to output stream			
			outputStream.println("Root Word:\t\t" + input[0]);
			outputStream.println("Search Word:\t" + input[1]);
			outputStream.println(pathString);

			// Prints distance between words
			dist = distMap.distance(input[0], input[1]);
						
			if(dist > 0) {
				System.out.println("Distance between words: " + dist);
				outputStream.println("Distance between words: " + dist);
			} else if ( dist == 0 ) {
				System.out.println("Words are the same.");
				outputStream.println("Words are the same.");
			} else {
				System.out.println("No path between words.");
				outputStream.println("No path between words.");
			}
			// Spacer line
			outputStream.println();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	private static String[] getInput() {
		
		String[] returnVal = new String[2];
		
		Scanner console = new Scanner(System.in);
		
		System.out.print("Type First Word: ");
		
		returnVal[0] = console.next();
		
		System.out.print("Type Second Word: ");
		
		returnVal[1] = console.next();
		
		return returnVal;
	}

	private static boolean continueChoice() {
		Scanner console = new Scanner(System.in);
		System.out.println("Continue? Y / N");
		String s = console.next();
		console.nextLine(); // Flushes the input queue;

		
		if(s.toLowerCase().startsWith("y")) {
			return true;
		} else {
			return false;
		}
	}
}
