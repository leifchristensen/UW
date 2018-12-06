package src;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import com.sun.media.sound.InvalidDataException;

/**
 * A map of word lengths to a set of words with a length equal to the key value.
 * Words are stored as lowercase strings.
 * 
 * @author leifc
 *
 */
public class LengthMap {

	private TreeMap<Integer, Set<String>> map;
	
	public LengthMap() {
		this.map = new TreeMap<>();
	}

	/**
	 * @return the map
	 */
	private TreeMap<Integer, Set<String>> getMap() {
		return map;
	}
	
	/**
	 * Gets a deep copy of the set of strings in the LengthMap with a given length.
	 * @param length Returns set of strings of this length
	 * @return Set of strings
	 */
	public Set<String> get(int length) {
		Set<String> returnVals = new LinkedHashSet<String>(this.getMap().get(length));
		return returnVals;
	}
	
	/**
	 * Attempts to add a string to the LengthMap set for the string's length key.
	 * 
	 * @param s String to attempt to add
	 * @throws InvalidDataException If string is already in data set or otherwise fails to add.
	 */
	private void addString(String s) throws InvalidDataException {
		Integer length = 0;
		length = s.length();
		if(this.getMap().containsKey(length)) { //If key exists, check for match. Add s to set if no match.
			if(!this.map
					.get(length)
					.add(s)) {
				throw new InvalidDataException(s);
			}
		}
	}
	
	
	
	/**
	 * Reads input stream as strings and attempts to add each string in the stream to the LengthMap.
	 * @param input Scanner input of strings to add
	 * @throws InvalidDataException If any strings fail to add, throws with list of all strings that failed in description
	 */
	public void read(Scanner input) throws InvalidDataException {
		ArrayList<String> errors = new ArrayList<String>();
		
		while(input.hasNext()) {			
			
			String s = input.next();
			try {
				addString(s);				
			} catch (Exception e) { // Data Exception
				errors.add(s);
			}
					
		}
		
		if (!errors.isEmpty()) {
			String errorList = "";
			errors.forEach(errorList::concat);
			throw new InvalidDataException("Strings failed to add:" + errorList) ;
		}
	}
	
	/**
	 * Tests if a given string exists in the LengthMap.
	 * @param s String to search for.
	 * @return True if s exists in map, false otherwise.
	 */
	public boolean contains(String s) {
		int length = s.length();
		return this.getMap()
				.get(length)
				.contains(s);
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.getMap().toString();
		
	}

}
