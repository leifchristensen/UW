package src;

import java.io.*;
import java.util.*;

import com.sun.media.sound.InvalidDataException;

public class Project4Driver {


	public static void main(String[] args) throws InvalidDataException, FileNotFoundException {
		
		Distance1Map distMap = new Distance1Map();
		
		
		
		
		File dictionary = new File("Files/dictionary.txt");
		Scanner dictScanner = new Scanner(dictionary);			
		distMap.lengthMap.read(dictScanner);
		
		
		String[] input = getInput();
		
		int dist = -1;
		
		//dist = distMap.distance(input[0], input[1]);
		
		System.out.println(distMap.lengthMap.toString());
		
		//System.out.println("Contains " + input[0] + map.lengthMap.contains(input[0]));
		
		if(dist > 0) {
			System.out.println("Distance between words: " + dist);
		} else if ( dist == 0 ) {
			System.out.println("No path between words");
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

}
