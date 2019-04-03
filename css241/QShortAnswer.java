package css241;

public class QShortAnswer extends AbstractQuestion {

	/** Creates a new Short Answer question
	 * @param question
	 * @param numPoints
	 * @param answer
	 */
	public QShortAnswer(String question, int numPoints, String answer) {
		super(question, numPoints, answer);
		
	}

	/* (non-Javadoc)
	 * @see src.AbstractQuestion#isCorrect(java.lang.String)
	 */
	@Override
	public boolean isCorrect(String toCompare) {
		if(toCompare.toUpperCase().equals(this.getAnswer().toUpperCase())) {
			return true; // True if and only if response matches answer, case insensitive.
		}
		return false;
	}

}
