import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public final class Genome implements Comparable<Genome> {
	// "CHRISTOPHER PAUL MARRIOTT"
	private static final String target = "CHRISTOPHER PAUL MARRIOTT";
	
	private LinkedList<Character> charSequence;
	
	private static final char[] validChars = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',' ','-','\''};
	
	private Random rand;
	private double mutationRate;
	
	

	public Genome(double mutationRate) {
		if(mutationRate < 0 || mutationRate > 1) {
			throw new IllegalArgumentException("mutationRate must be between 0 and 1");
		}
		this.charSequence = new LinkedList<Character>();
		charSequence.add(validChars[0]);
		rand = new Random();
		this.mutationRate = mutationRate;
	}
	
	public Genome(Genome other) {
		this.charSequence = other.getCharSequence();
		rand = new Random();
		this.mutationRate = other.getMutationRate();
	}
	
	public LinkedList<Character> getCharSequence() {
		return new LinkedList<Character>(charSequence);
	}
	
	public double getMutationRate() {
		return mutationRate;
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
		// Only remove if length 2+.
		if (this.charSequence.size() > 2) {
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
		double toAdd = rand.nextDouble();
		double toRemove = rand.nextDouble();
		double toChange = rand.nextDouble();
		
		if (toAdd < this.mutationRate) this.add();
		if (toRemove < this.mutationRate) this.remove();
		if (toChange < this.mutationRate) this.change();
	}
	
	public void crossover(Genome other) {
		LinkedList<Character> currentString = this.getCharSequence();
		LinkedList<Character> newString = new LinkedList<Character>();
		int index = 0;
		while (index <= currentString.size()) {
			if (rand.nextBoolean()) {
				currentString = other.getCharSequence();
			}
			else {
				currentString = this.charSequence;
			}
			// If the current string has a character at the current index, add it to the new string.
			if (index < currentString.size()) {
				newString.add(currentString.get(index));
			}
			index++;
		}
	}
	
	///*
	public Integer fitness() {
		int fitness = 0;
		char[] targetChars = target.toUpperCase().toCharArray();
		// Adds difference in length.
		fitness += Math.abs(this.charSequence.size() - targetChars.length);
		int maxLength = Math.max(this.charSequence.size(), targetChars.length);
		// Adds one for each mismatching character
		for (int i = 0; i < maxLength; i++) {
			// Checks for length discrepancies before checking for equality. This should prevent index exceptions.
			if(maxLength > this.charSequence.size() || maxLength > targetChars.length || this.charSequence.get(i) != targetChars[i]) {
				fitness++;
			}
		}
		
		return fitness;
	}
	//*/
	
	/*
	
	public Integer fitness() {
		int n = this.charSequence.size();
		int m = this.target.length();
		
		int[][] D = new int[n+1][m+1];
		
		for (int i = 1; i <= n; i++) {
			D[i][0] = i;
			
		}
		for (int i = 1; i <= m; i++) {
			D[0][i] = i;
		}
		
		for (int row = 1; row <= n; row++) {
			for (int col = 1; col <= m; col++) {
				if (this.charSequence.get(row-1) == target.toCharArray()[col-1]) {
					D[row][col] = D[row-1][col-1];
				} else {
					int deletion = D[row][col-1]+1;
					int insert = D[row-1][col]+1;
					int sub = D[row-1][col-1]+1;
					D[row][col] = Math.min(deletion, Math.min(insert, sub));
				}
			}
		}
		
		return D[n][m] + (int)((Math.abs(n-m) + 1)/2);
	}
	*/
	
	@Override
	public String toString() {
		return "Genome " + this.fitness() + " |\t" + this.charSequence.toString();
	}
	
	@Override
	public int compareTo(Genome other) {		
		return this.fitness() - Objects.requireNonNull(other).fitness();
	}
	
	

	public static void main(String[] args) {
		Genome a = new Genome(.5);
		a.mutate();
		System.out.println(a.toString());
	}

	

}
