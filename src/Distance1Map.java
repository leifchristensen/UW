package src;

import java.util.*;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;

public class Distance1Map {
	
	Map<String, Set<String>> map;
	
	public LengthMap lengthMap;

	public Distance1Map() {
		this.lengthMap = new LengthMap();
	}
	
	
	
	/**
	 * Returns a map of all Strings which differ by length 1.
	 * The keys of the map are all words of the given length, and the set associated with each key is the set of words that differ by one letter.
	 * @return
	 */
	private void map(int length) {
		Map<String, Set<String>> map = new TreeMap<String, Set<String>>();
		
		if(lengthMap.get(length).isEmpty()) {
			throw new NullPointerException("Map is empty");
		}
		
		//Adds each string in the lengthmap set as a key, 
		//	and adds the set of strings that differ by 1 from the key as values to each key.
		for (String string : this.lengthMap.get(length)) {
			Set<String> dataset = new HashSet<String>();
			//TODO: Fill out dataset
			dataset = this.get1Diff(string);
			
			map.put(string, dataset);
		}
		
		this.map = map;
	}
	
	/**
	 * Gets the set of strings in the LengthMap that are 1 different that a given string.
	 * @param s
	 * @return
	 */
	private Set<String> get1Diff(String s){
		Set<String> dataset = new HashSet<String>();
		for(String other : this.lengthMap.get(s.length())) {
			if(this.differBy1(s, other)) {
				dataset.add(s);
			}
		}
		return dataset;
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
	 * REturns a queue of stacked strings. Each stack contains two elements, a top element with a word differing from the input by 1, and the bottom element containing the original input.
	 * @param input
	 * @return
	 */
	private Queue<Stack<String>> enqueueString(String input){
		
		Queue<Stack<String>> queue = new LinkedList<Stack<String>>();
		
		Set<String> mappedWords = this.get1Diff(input);
		
		for(String s : mappedWords) {
			Stack<String> tempStack = new Stack<String>();
			tempStack.push(input);
			tempStack.push(s);
			queue.add(tempStack);
		}
		
		return queue;
		
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
		
		int counter = 0;
		
		try {
			Stack<Queue<Stack<String>>> outerStack = this.getPathHelper(s1, s2);
			

			while(!outerStack.isEmpty()) {
				outerStack.pop();
				counter++;
			}
			
			returnVal = counter;
		}
		
		catch (Exception e) {
			System.out.print(e);
			return returnVal;
		}
		
		return returnVal;
		
	}
	
	/**
	 * s1 -> queue1base -> queue2base -> s2
	 * @param s1
	 * @param s2
	 * @return
	 */
	public String getPath(String s1, String s2) {
		
		String returnVal = s1.toLowerCase();
		
		try {
			Stack<Queue<Stack<String>>> outerStack = this.getPathHelper(s1, s2);
			

			while(!outerStack.isEmpty()) {
				Queue<Stack<String>> innerQueue = new LinkedList<Stack<String>>(outerStack.pop());
				
				while(!innerQueue.isEmpty()) {
					
				}
				
				returnVal.concat(" -> " + innerQueue.remove().get(0));
			}
			
			
		}
		
		catch (Exception e) {
			System.out.print(e);
			return "No Path";
		}
		
		return returnVal;
		
	}
	
	/**
	 * 
	 * Structure of Stacks and Queues:
	 * 
	 * Inner Stack:	Set of Strings that vary by one from a root string. Top element is the word that varies by 1.
	 * Inner Queue:	Queue of inner stacks. Iterate over words matching a root word by reading this queue.
	 * Outer Stack:	For each queue that does not contain the end value, adds a new queue to the stack until a match is found in the inner queue.
	 * 
	 * @return
	 */
	private Stack<Queue<Stack<String>>> getPathHelper(String s1, String s2) throws InternalException {
		Stack<Queue<Stack<String>>> outerStack = new Stack<Queue<Stack<String>>>();
		boolean hasFoundMatch = false;
		int infinite = 0;
		final int NUMLOOPS = 100;
		
		String searchString = s1.toLowerCase(); // Initializes the search string to s1
		
		do {
			Queue<Stack<String>> innerQueue = this.enqueueString(searchString);
			Queue<Stack<String>> innerQueueCopy = new LinkedList<Stack<String>>(innerQueue);
			
			while (!innerQueueCopy.isEmpty()) {
				if(innerQueueCopy.remove().peek().equals(s2)) {
					hasFoundMatch = true;
				}
				
			}
			
			infinite++; // Increments counter for timeout loop error
			
			outerStack.push(innerQueue); // Adds queue to outer stack, regardless of whether or not the queue contained a match.
			
		} while (!hasFoundMatch && infinite < NUMLOOPS); // If a match is found, stop adding queues. Must add at least the initial queue to the stack.
		
		if( infinite >= NUMLOOPS) {
			throw new InternalException("Match not found in " + NUMLOOPS + "levels of search.");
		}
		
		
		return outerStack;
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.map.toString();
	}
}
