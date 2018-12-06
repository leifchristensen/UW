package src;

import java.util.*;

import com.sun.xml.internal.fastinfoset.util.CharArray;

public class Distance1Map {
	
	Map<String, Set<String>> map;

	public Distance1Map() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Returns a map of all Strings which differ by length 1.
	 * The keys of the map are all words of the given length, and the set associated with each key is the set of words that differ by one letter.
	 * @return
	 */
	public void map(Set<String> input) {
		Map<String, Set<String>> map = new TreeMap<String, Set<String>>();
		
		for (String string : input) {
			Set<String> dataset = new HashSet<String>();
			//TODO: create method to fill dataset
			map.put(string, dataset);
		}
		
		this.map = map;
	}
	
	
	
	/**
	 * Tests if the map contains the given string.
	 * @param s
	 * @return
	 */
	public boolean contains(String s) {
		return this.map.keySet().contains(s);
	}
	
	/**
	 * Computes the distance between two words. 
	 * Distance is calculated by the number of 1-letter changes from one word to another to transform one word into another.
	 * s1 and s2 can be in any order for the two parameters and the return value will be identical.
	 * -1 is returned if s1 cannot be transformed to s2.
	 * @param s1
	 * @param s2
	 * @return distance between s1 and s2, -1 if no transformation is possible.
	 */
	public int distance(String s1, String s2) {
		int returnVal = -1;
		Queue<Stack<String>> queue = new LinkedList<Stack<String>>();
		
		
		if (s1.length() != s2.length()) { //If two strings are different lengths, no mapping is possible.
			throw new InputMismatchException("Strings of different lengths, no mapping possible");
		}
		else if(){
			
		}
	}
	
	private Queue<Stack<String>> enqueueStrings(){
		return null;
		
	}
	
	/**
	 * Tests if two strings differ by a 
	 * @param s1
	 * @param s2
	 * @return
	 */
	private boolean differBy1(String s1, String s2) {
		if(s1.length() != s2.length()) // If strings are different sizes, return false
			return false;
		
		char[] c1 = new char[s1.length()];
		char[] c2 = new char[s2.length()];
		
		c1 = s1.toCharArray();
		c2 = s2.toCharArray();	
		
		int differing = 0;
		
		for(int i = 0; i < c1.length && i < c2.length; i++) {
			if(c1[i] != c2[i])
				differing ++;
		}
		
		if(differing < 1) // False if more than one different char.
			return false;
		
		return true;
	}
	
	public String getPath(String s1, String s2) {
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.map.toString();
	}
}
