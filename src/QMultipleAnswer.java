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
	
	

	

}
