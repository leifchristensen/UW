package src;

import java.util.ArrayList;

public class QMultipleChoice extends AbstractQuestion {	

	private ArrayList<String> choices;
	
	public QMultipleChoice(String question, int quesPoints, String answer, ArrayList<String> ansChoices) {
		super(question,  quesPoints, answer);
		setChoices(ansChoices);
	}

	/**
	 * @return the choices
	 */
	public ArrayList<String> getChoices() {
		return choices;
	}

	/**
	 * @param choices the choices to set
	 */
	public void setChoices(ArrayList<String> choices) {
		this.choices = choices;
	}
	
	public String getQuestion() {
		String returnVal = "";
		returnVal = super.getQuestion();		
		for(String s : this.choices) {
			returnVal = returnVal.concat("\r\t" + s);
		}
		return returnVal;
	}
	
	public boolean isCorrect(String toCompare) {
		int numCorrect = 0;
		//ArrayList<String> answers = new ArrayList<String>();
		for(Character c : toCompare.toUpperCase().toCharArray()) {
			String test = c.toString();
			if(!this.getAnswer().toUpperCase().contains(test)) {
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
