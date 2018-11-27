package src;

import java.util.ArrayList;

public class QMultipleAnswer extends QMultipleChoice{
	
	private ArrayList<String> answers;

	public QMultipleAnswer(String question, int numPoints, String answer, ArrayList<String> choices, ArrayList<String> answers) {
			super(question, numPoints, answer, choices);
			setAnswers(answers);
		}

	/**
	 * @return the answers
	 */
	public ArrayList<String> getAnswers() {
		return answers;
	}

	/**
	 * @param answers2 the answers to set
	 */
	public void setAnswers(ArrayList<String> answers2) {
		this.answers = answers2;
	}
	
	public boolean isCorrect(String toCompare) {
		int numCorrect = 0;
		//ArrayList<String> answers = new ArrayList<String>();
		for(Character c : toCompare.toCharArray()) {
			String test = c.toString();
			if(!this.getAnswer().contains(test)) {
				return false; //Returns false if any character in the response is not in the answer
			}
			else {
				numCorrect++; // If there is a match, increment the number of matches by 1.
			}
		}
		
		if(numCorrect == this.getAnswer().length()) {
			return true; // Only return true if the number of matches is equal to the number of answers
		}
		return false; // Return false if answer count numbers do not match
		
	}

	

}
