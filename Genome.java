import java.util.LinkedList;
import java.util.Random;

public final class Genome {
	
	private LinkedList<Character> charSequence;
	
	private static final char[] validChars = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',' ','-','\''};
	
	private Random rand;
	
	public Genome() {
		this.charSequence = new LinkedList<Character>();
		charSequence.add(validChars[0]);
		rand = new Random();
	}
	
	public Genome(Genome other) {
		this.charSequence = other.getCharSequence();
		rand = new Random();
	}
	
	public LinkedList<Character> getCharSequence() {
		return new LinkedList<Character>(charSequence);
	}
	
	private void add() {
		int insertPos = rand.nextInt(validChars.length+1); // +1 to allow for insert at position at the end of list.
		int insertChar = rand.nextInt(validChars.length);
		try {
			this.charSequence.add(insertPos, validChars[insertChar]);
		} catch (IndexOutOfBoundsException e) {
			this.charSequence.add(validChars[insertChar]);
		}
	}
	
	private void remove() {
		int removePos = rand.nextInt(validChars.length);
		this.charSequence.remove(removePos);
	}
	
	private void change() {
		int changePos = rand.nextInt(validChars.length); 
		int changeChar = rand.nextInt(validChars.length);
		this.charSequence.set(changePos, validChars[changeChar]);
	}
	
	public void mutate() {
		boolean toAdd = rand.nextBoolean();
		boolean toRemove = rand.nextBoolean();
		boolean toChange = rand.nextBoolean();
		
		if (toAdd) this.add();
		if (toRemove) this.remove();
		if (toChange) this.change();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
