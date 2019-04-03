package css241;

import java.util.*;

import javax.activity.InvalidActivityException;

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
				dataset.add(other);
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
	 * Tests if two strings differ by 0 or 1 characters
	 * @param s1
	 * @param s2
	 * @return
	 */
	public boolean differBy1(String s1, String s2) {
		if(s1.length() != s2.length()) // If strings are different sizes, return false
			return false;
		
		char[] c1 = new char[s1.length()];
		char[] c2 = new char[s2.length()];
		
		c1 = s1.toCharArray();
		c2 = s2.toCharArray();	
		
		int differing = 0;
		
		for(int i = 0; i < s1.length() && i < s2.length(); i++) {
			if(c1[i] != c2[i])
				differing ++;
		}
		
		if(differing > 1) // False if more than one different char.
			return false;
		
		return true;
	}
	
	/**
	 * Computes the shortest distance between two words. 
	 * Distance is calculated by the number of 1-letter changes from one word to another to transform one word into another.
	 * s1 and s2 can be in any order for the two parameters and the return value will be identical.
	 * -1 is returned if s1 cannot be transformed to s2.
	 * @param s1
	 * @param s2
	 * @return distance between s1 and s2, -1 if no transformation is possible.
	 */
	public int distance(String s1, String s2) {
		int returnVal = -1;
		
		// Case of identical strings
		if(s1.toLowerCase().equals(s2.toLowerCase())){
			return 0;
		} else 
		// Case of different lengths
			if(s1.length() != s2.length()) {
				return -1;
		}
		
		
		
		try {
			Stack<String> matchingStack = this.getPathHelper(s1, s2);
			
			
			
			// If no path, return stack will be size 1. 
			// Because identical strings are handled above, this means no path is possible. 
			if(matchingStack.size() == 1) {
				return -1;
			}
			
			// If the matching stack is greater than 1 element, returns the length of the stack.
			returnVal = matchingStack.size();		
			
			
		}
		
		catch (Exception e) {
			System.out.print(e);
			return -1;
		}
				
		
		return returnVal;
		
	}
	
	/**
	 * Returns a comma-separated list of strings in a path between two words. If no path is possible, error thrown.
	 * s1, queue1base, queue2base, s2
	 * @param s1
	 * @param s2
	 * @return
	 * @throws InvalidActivityException 
	 */
	public String getPath(String s1, String s2) throws InputMismatchException, InvalidActivityException {
		
				
		 // Case of s1 not in dictionary
		if (!this.lengthMap.contains(s1)) {
			throw new InputMismatchException("String not in provided dictionary: " + s1);
		} else 
			
		// Case of s2 not in dictionary
		if (!this.lengthMap.contains(s2)) {
			throw new InputMismatchException("String not in provided dictionary: " + s2);
		}
		
		// Case of identical strings
		if(s1.toLowerCase().equals(s2.toLowerCase())){
			return s1.toLowerCase();			
		} else 
			
		// Case of different lengths
		if(s1.length() != s2.length()) {
			throw new InputMismatchException("Different length strings");
		}
		
		String returnVal = "";
		
	
		Stack<String> matchingStack = this.getPathHelper(s1, s2);
		
		
		
		// If no path, return stack will be size 1. 
		// Because identical strings are handled above, this means no path is possible. 
		if(matchingStack.size() == 1) {
			throw new InvalidActivityException("No path possible");
		}
		
		
		
		// reverses stack
		Stack<String> reverseStack = new Stack<String>();
		while(!matchingStack.isEmpty()) {
			reverseStack.add(matchingStack.pop());
		}
		
		returnVal = returnVal.concat(reverseStack.pop());
		while(!reverseStack.isEmpty()) {
			returnVal = returnVal.concat(", ");
			returnVal = returnVal.concat(reverseStack.pop());
		}
		
		
	
				
		return returnVal;
		
	}
	
	/** Returns a stack of elements from a root word to a search word.
	 * The stack is the shortest distance between the two words.
	 * Each stack begins with a root word and ends with a word 1 away from it.
	 *  
	 * 
	 * Structure of Stacks and Queues:
	 * 
	 * InnerStack: top element is one step further on path. Bottom is root.
	 * OuterQueue: queue of all stacks to test.
	 * 
	 * Method:
	 * Beginning with the root word, if the top element of a stack is not a math then
	 * additional stacks with an additional element are added. 
	 * The additional elements are all of the un-searched words 1 character away from the top element.
	 * This method checks all combinations of 1- 2- 3 ... length stacks in ascending order until a match is found or no 
	 * un-searched words exist to test.
	 * 
	 * @return
	 */
	private Stack<String> getPathHelper(String s1, String s2){
		String searchString = s2.toLowerCase(); // Initializes the search string to s1
		String rootString = s1.toLowerCase();
		
		Set<String> searchedWords = new TreeSet<String>();
		Queue<Stack<String>> outerQueue = new LinkedList<Stack<String>>();
		Stack<String> returnStack = new Stack<String>();
		
		searchedWords.add(rootString);
		returnStack.add(rootString);
		
		Stack<String> initial = new Stack<String>();
		initial.add(rootString);
		outerQueue.add(initial);
		
		
		// Queue begins with the set of words branching from the root word.
		
		// Queue will continue to add branching words until no more branching possibilities remain.
		// If top element of a stack in the queue is not a match, a new stack containing the inspected stack plus every word not already searched will be added to the queue.
		// If a stack has no unexplored branches, no stack will be added to the queue.
		// Once all branches have been explored, queue will be empty.
		// If there is a match, the stack that ends in the search value is returned.
		// If there is no match, the stack returned will contain only the starting value.
		
		while(!outerQueue.isEmpty()) {
			Stack<String> stackToInspect = outerQueue.remove();
			String top = stackToInspect.peek();
			
			//Uncomment to see stack trace in console.
			//System.out.println(stackToInspect.toString());
			
			// Case if top element is a match with the search
			if (top.equals(searchString)) {

				return stackToInspect;
			}
			
			// Case if top element is not a match.
			else {
				// for each word s branching from the top word not in the list of searched words:
				for(String s : this.get1Diff(top)) {
					if (!searchedWords.contains(s)) {
						// Create a copy of the stack to inspect,
						Stack<String> stackToAppend = new Stack<String>();
						stackToAppend.addAll(stackToInspect);
						// Add the branching word s to the top of the stack,
						stackToAppend.add(s);
						//Add word to searched strings
						searchedWords.add(s);
						// And enqueue the stack with the additional word to the queue for processing.
						outerQueue.add(stackToAppend);
					}
				}
			}
			
			
		}
		
		
		return returnStack;
		
		
		
		
	}
	

	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.map.toString();
	}
}
