package src;

public abstract class AbstractQuestion implements QuestionInterface {

	private String question;
	private String answer;
	private int numPoints;
	
	/** Creates a new AbstractQuestion, for use in child classes
	 * @param question
	 * @param numPoints
	 * @param answer
	 */
	protected AbstractQuestion(String question, int numPoints, String answer) { // Protected for use by child classes in package, not for direct calls by client
		setQuestion(question);
		setAnswer(answer);
		setNumPoints(numPoints);
	}
	
	
	/**
	 * @return the question text
	 */
	public String getQuestion() {
		return question;
	}


	/**
	 * @param question the question text to set
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
	 * @return the number of points the question is worth
	 */
	public int getNumPoints() {
		return numPoints;
	}


	/**
	 * @param numPoints the number of points to set the value of the question to
	 */
	public void setNumPoints(int numPoints) {
		this.numPoints = numPoints;
	}
	
	/* (non-Javadoc)
	 * @see src.QuestionInterface#isCorrect(java.lang.String)
	 */
	@Override
	public abstract boolean isCorrect(String toCompare);
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return question + "\t" + answer + "\t" + numPoints;
		
	}

}
