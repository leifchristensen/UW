package src;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

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
		this.map = new TreeMap<Integer, Set<String>>();
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
		Set<String> returnVals = new TreeSet<String>(this.getMap().get(length));
		return returnVals;
	}
	
	/**
	 * Attempts to add a string to the LengthMap set for the string's length key.
	 * 
	 * @param s String to attempt to add
	 * @throws InvalidDataException If string is already in data set or otherwise fails to add.
	 */
	private void addString(String s) {
		Integer length = 0;
		length = s.length();
		Set<String> tempset = new TreeSet<String>();
		
		tempset.add(s.toLowerCase());
		
		if(!this.map.isEmpty() && this.map.containsKey(length) && !this.map.get(length).isEmpty()) {
			tempset.addAll(this.map.get(length));
		}
		this.map.put(length, tempset);
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
			this.addString(s);
					
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
