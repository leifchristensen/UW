/*
 * Leif Christensen
 * Assignment 2
 * Apr 25 2019
 */

import java.util.LinkedList;
import java.util.Random;

public final class Population {
	
	private LinkedList<Genome> populationList;
	private final int initPopulation = 60;
	private Random rand;
	// Specs seem to show this as a public field instead of a getter method, is this correct?
	public Genome mostFit;
	
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
	
	public Genome mostFit() {
		return new Genome(this.mostFit);
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
		sb.append("MOST FIT: Fitness: " + this.mostFit.fitness() + "\t" + this.mostFit.toString());
		return sb.toString();
	}


}
