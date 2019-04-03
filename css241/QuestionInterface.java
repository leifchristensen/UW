package css241;

public interface QuestionInterface {

	/**
	 * Returns boolean value comparing the question's answer to the correct answer
	 * @param toCompare Variable input of answer to question, to be compared to static question answer value
	 * @return true if answer is correct, false if not
	 */
	public boolean isCorrect(String toCompare);
	
	
	public String toString();
}
