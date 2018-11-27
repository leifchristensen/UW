package src;

import java.util.ArrayList;

public class QMatching extends QMultipleChoice {

	public QMatching(String question, int numPoints, String answer,  ArrayList<String> choices) {
		super(question, numPoints, answer, choices);
		
	}
	
	@Override
	public boolean isCorrect(String toCompare) {
		int numCorrect = 0;
		ArrayList<Character[]> input = new ArrayList<Character[]>(); // list of input matches
		ArrayList<Character[]> answers = new ArrayList<Character[]>(); // list of answer matches
		
		// Populate lists
		
		// Input list
		for(int i = 0; i < toCompare.length(); i += 2) { // creates an array of 2 sequential characters and adds to list
			Character[] temp = {toCompare.charAt(i),toCompare.charAt(i+1)};
			input.add(temp);
		}
		
		// Answers list
		for(int i = 0; i < this.getAnswer().length(); i += 2) { // creates an array of 2 sequential characters and adds to list
			Character[] temp = {this.getAnswer().charAt(i),this.getAnswer().charAt(i+1)};
			answers.add(temp);
		}
		
		// Check if answers contains each pair in input
		for(Character[] c : input) {
			
			if(!answers.contains(c)) {
				return false; //Returns false if any character in the response is not in the answer
			}
			else {
				numCorrect++; // If there is a match, increment the number of matches by 1.
			}
		}
		
		if(numCorrect == answers.size()) {
			return true; // Only return true if the number of matches is equal to the number of answers
		}
		return false; // Return false if answer count numbers do not match
		
	}

}
