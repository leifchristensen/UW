import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public final class Genome implements Comparable<Genome> {
	
	private static final String target = "LEIF CHRISTENSEN";
	
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
		int insertPos = rand.nextInt(this.charSequence.size()+1); // +1 to allow for insert at position at the end of list.
		int insertChar = rand.nextInt(validChars.length);
		try {
			this.charSequence.add(insertPos, validChars[insertChar]);
		} catch (IndexOutOfBoundsException e) {
			this.charSequence.add(validChars[insertChar]);
		}
	}
	
	private void remove() {
		// Only remove if there is an element to remove.
		if (this.charSequence.size() > 0) {
			int removePos = rand.nextInt(this.charSequence.size());
			this.charSequence.remove(removePos);
		}
		
	}
	
	private void change() {
		// Only change if there is an element to change.
		if (this.charSequence.size() > 0) {
			int changePos = rand.nextInt(this.charSequence.size()); 
			int changeChar = rand.nextInt(validChars.length);
			this.charSequence.set(changePos, validChars[changeChar]);
		}
		
	}
	
	public void mutate() {
		boolean toAdd = rand.nextBoolean();
		boolean toRemove = rand.nextBoolean();
		boolean toChange = rand.nextBoolean();
		
		if (toAdd) this.add();
		if (toRemove) this.remove();
		if (toChange) this.change();
	}
	
	public int getFitness() {
		int fitness = 0;
		char[] targetChars = target.toUpperCase().toCharArray();
		// Adds difference in length.
		fitness += Math.abs(this.charSequence.size() - targetChars.length);
		int maxLength = Math.max(this.charSequence.size(), targetChars.length);
		// Adds one for each mismatching character
		for (int i = 0; i < maxLength; i++) {
			// Checks for length discrepancies before checking for equality. This should prevent index exceptions.
			if(maxLength >= this.charSequence.size() || maxLength >= targetChars.length || this.charSequence.get(i) != targetChars[i]) {
				fitness++;
			}
		}
		
		return fitness;
	}
	
	@Override
	public String toString() {
		return "Genome" + this.charSequence.toString();
	}
	
	@Override
	public int compareTo(Genome other) {		
		return this.getFitness(target) - Objects.requireNonNull(other).getFitness(target);
	}
	
	

	public static void main(String[] args) {
		Genome a = new Genome();
		a.mutate();
		System.out.println(a.toString());
	}

	

}
