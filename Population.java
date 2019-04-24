import java.util.LinkedList;
import java.util.Random;

public final class Population {
	
	private LinkedList<Genome> populationList;
	private final int initPopulation = 60;
	private Random rand;
	private Genome mostFit;
	
	public Population() {
		this.populationList = new LinkedList<Genome>();
		rand = new Random();
		for (int i = 1; i <= initPopulation; i++) {
			this.populationList.add(new Genome(.4));
			// Sets the first as most fit, then updates for every subsequent genome as it's added.
			// Future sorts are taken care of by maintaining ordering in sort.
			if (i == 1) {
				mostFit = populationList.getFirst();
			} else {
				if (mostFit.fitness() < populationList.getLast().fitness()) {
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
		// Get one of the most fit genomes
		Genome randGenome = populationList.get(rand.nextInt(initPopulation/2));
		// CLones a random genome
		Genome newGenome = new Genome(randGenome);
		// Half of the time, cross this random genome with another random genome
		if(rand.nextBoolean()) {
			newGenome.crossover(populationList.get(rand.nextInt(initPopulation/2)));
		} 
		// Mutate the result whether crossed over or not.
		newGenome.mutate();
		return newGenome;
	}
	
	public void day() {
		//this.checkFitness(); 
		for(int i = this.populationList.size()-1; i >= initPopulation / 2  ; i--) { 
			this.populationList.remove(i);
		}
		
		
		// Adds new genomes to list until population size is reached again.
		while (this.populationList.size() < initPopulation) {
			populationList.add(generateRandGenome());
		}
		checkFitness();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MOST FIT:  " + this.mostFit.fitness() + " " + this.mostFit.toString() + "\r\n");
		/*
		for (Genome g : this.populationList) {
			sb.append(g.toString() + "\r\n");
		} */
		return sb.toString();
	}

	public static void main(String[] args) {
		Population pop = new Population();
		int i = 0;
		while (pop.mostFit.fitness() > 0 && i < 10000 ) {
			pop.day();
			i++;
			
		}
		
		System.out.print(i + pop.toString());

	}

}
