package src;

import java.util.*;

public class Lab10 {

	public Lab10() {
		// TODO Auto-generated constructor stub
	}

	public void shift(Stack<Integer> input, int n) {
		Queue<Integer> queue = new LinkedList<Integer>();
		int numElements = input.size();
		
		for(int i = 0; i < numElements; i++ ) {
			Integer j = input.pop();
			if(i > numElements-  n)
				queue.add(j);
			else {
				
				input.add(j);
			}
		}
		
		while(!queue.isEmpty()) {
			
			input.add(queue.remove());
	}
		
		
		
		
		
				
	}
	
	public void mirror(Stack<Integer> input) throws IllegalAccessException {
		Stack<Integer> temp = new Stack<Integer>();
		int size = input.size();
		
		
		
		for(int i = 0; i < size; i++) {
			Integer j = input.pop()
			temp.push(j);
			input.push(j);
		}
		
		for(int i = 0; i < size; i++) {
			temp.push(temp.pop());
		}
	}
	
	public HashMap<String, Integer> reverse(Map<Integer, String> input) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		
		for(Integer i : input.keySet()) {
			String s = input.get(i);
			if(!map.keySet().contains(s)) {
				map.put(s, i);
			}
		}
		
		return map;
		
	}
	
	public int maxOccurrences(List<Integer> input) {
		int mode = 0;
		
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for(Integer i : input) {
			if (map.containsKey(i)) {
				map.put(i, Integer.sum(map.get(i), 1));
			}
			else {
				map.put(i, 1);
			}
		}
		
		for(Integer i : map.keySet()) {
			if(map.get(i) > mode) {
				mode = map.get(i);
			}
		}
		
		return mode;
	}
	
	public boolean isUnique(Map<String, String> map) {
		for(String s1 : map.keySet()) {
			for(String s2 : map.keyset()) {
				if(map.get(s1).equals(map.get(s2)) && !(s1.equals(s2))) {
					return false
				}
			}
		}
		return true;
	}


public boolean isPalindrome(Queue<Integer> input) {
	    Queue<Integer> queue = new LinkedList<Integer>();
	    Stack<Integer> stack = new Stack<Integer>();
	    int numletters = 0;
	    
	    while(!input.isEmpty()) {
	    	Integer i = input.remove();
	    	queue.add(i);
	    	stack.add(i);    	
	    	numletters++;
	    }
	    
	    while(!queue.isEmpty()) {
	    	Integer int1 = queue.remove();
	    	Integer int2 = stack.pop();
	    	input.add(int1);
	    	if(!int1.equals(int2))
	    		return false;
	    }
	    
	    return true;
	    
	    
	}
}
