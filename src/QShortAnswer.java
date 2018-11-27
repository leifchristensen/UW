package src;

public class QShortAnswer extends AbstractQuestion {

	public QShortAnswer(String question, int numPoints, String answer) {
		super(question, numPoints, answer);
		
	}

	@Override
	public boolean isCorrect(String toCompare) {
		if(toCompare.toUpperCase().equals(this.getAnswer().toUpperCase())) {
			return true; // True if and only if response matches answer, case insensitive.
		}
		return false;
	}

}
