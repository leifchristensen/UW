package src;

public abstract class AbstractQuestion implements QuestionInterface {

	private String question;
	private String answer;
	private int numPoints;
	
	public AbstractQuestion(String question, int numPoints, String answer) {
		setQuestion(question);
		setAnswer(answer);
		setNumPoints(numPoints);
	}
	
	
	@Override
	public boolean isCorrect(String toCompare) {
		// TODO Auto-generated method stub
		return false;
	}


	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}


	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}


	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}


	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}


	/**
	 * @return the numPoints
	 */
	public int getNumPoints() {
		return numPoints;
	}


	/**
	 * @param numPoints the numPoints to set
	 */
	public void setNumPoints(int numPoints) {
		this.numPoints = numPoints;
	}
	
	public String toString() {
		return question + "\t" + answer + "\t" + numPoints;
		
	}

}
