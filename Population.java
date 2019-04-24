import java.util.LinkedList;
import java.util.Random;

public final class Population {
	
	private LinkedList<Genome> populationList;
	private final int initPopulation = 12;
	private Random rand;
	private Genome mostFit;
	
	public Population() {
		this.populationList = new LinkedList<Genome>();
		rand = new Random();
		for (int i = 1; 1 < initPopulation; i++) {
			this.populationList.add(new Genome());
			// Sets the first as most fit, then updates for every subsequent genome as it's added.
			// Future sorts are taken care of by maintaining ordering in sort.
			if (i == 1) {
				mostFit = populationList.getFirst();
			} else {
				if (mostFit.getFitness() < populationList.getLast().getFitness()) {
					mostFit = populationList.getLast();
				}
			}
		}
	}
	
	private void checkFitness() {
		this.populationList.sort(null);
		this.mostFit = this.populationList.getFirst();
	}
	
	private Genome generateRandGenome() {
		if(rand.nextBoolean()) return 
		
	}
	
	public void day() {
		this.checkFitness(); // Updates most fit and sorts list.
		// starts at end out of bound when removing entries.
		// iterates up the list until halfway + 1 to ensure a solitary entry is not removed.
		for(int i = this.populationList.size(); i == this.populationList.size() / 2 + 1 ; i--) { 
			// Starting at halfway through the list sorted above, delete every genome.
			this.populationList.remove(i);
		}
		
		// Adds new genomes to list until population size is reached again.
		while (this.populationList.size() < initPopulation) {
			
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
