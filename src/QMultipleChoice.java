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

}
