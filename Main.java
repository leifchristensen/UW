/*
 * Leif Christensen
 * Assignment 2
 * Apr 25 2019
 */


public final class Main {
	public static void main(String[] args) {
		for (int trial = 0; trial < 1; trial++) { // Increase the number of trials here
			long startTime = System.nanoTime();
			Population pop = new Population();
			int i = 0;
			while (pop.mostFit().fitness() > 0 && i < 10000 ) {
				pop.day();
				System.out.println("GENERATION #" + i + "\t  " + pop);
				i++;
			}
			System.out.println("Trial #:        " + trial);
			System.out.println("Number of days: " + i);
			System.out.println("Time in seconds:" + (System.nanoTime()-startTime)/Math.pow(10, 9));
			System.out.println();
		}
		
		// Uncomment to run specific tests.
		testGenome();
		testPopulation();
	}
	
	public static void testGenome() {

		// Tests construction / string
		Genome gen1 = new Genome(.5);
		Genome gen2 = new Genome(gen1);
		
		System.out.println("GENOME : CONSTRUCTOR");
		System.out.println("GEN1: " + gen1);
		System.out.println("GEN2: " + gen2);
		System.out.println();
		
		// Tests mutate
		System.out.println("-----MUTATION");
		for (int i = 0; i < 7; i++) {
			
			gen1.mutate();
			System.out.println("MUTATE " + i);
			System.out.println("GEN1: " + gen1);
		}
		System.out.println();
		
		// Tests getters
		
		System.out.println("-----GETTERS");
		System.out.println("GEN1: MUTATION RATE " + gen1.getMutationRate());
		System.out.println("GEN1: CHAR SEQ      " + gen1.getCharSequence());
		System.out.println();
		
		// Tests Crossover

		// Mutates a new genome
		Genome gen3 = new Genome(gen1);
		
		for (int i = 0; i < 7; i++) {
			
			gen3.mutate();
		}
		
		System.out.println("-----CROSSOVERS");
		System.out.println("GEN3: " + gen3);
		System.out.println();
		// Crosses over gen1 with gen3
		for (int i = 0; i < 7; i++) {
			
			gen1.crossover(gen3);
			System.out.println("CROSSOVER X GEN3 #" + i);
			System.out.println("GEN1: " + gen1);
		}
		System.out.println();
		
		// Tests comparison
		System.out.println("-----COMPARETO");
		System.out.println("GEN1: " + gen1);
		System.out.println("GEN2: " + gen2);
		System.out.println("GEN3: " + gen3);
		System.out.println();

		System.out.println("1x1:  " + gen1.compareTo(gen1));
		
		System.out.println("1x2:  " + gen1.compareTo(gen2));
		System.out.println("2x1:  " + gen2.compareTo(gen1));

		System.out.println("1x3:  " + gen1.compareTo(gen3));
		System.out.println("3x1:  " + gen3.compareTo(gen1));		

		
		try {
			System.out.println("1xNULL:  " + gen1.compareTo(null));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("1xNULL:  " + e.getClass());
		}
			
		System.out.println();
	}
	
	public static void testPopulation() {

		// Tests construction / string
		System.out.println("-----POPULATION CONSTRUCTOR");
		Population p1 = new Population();
		System.out.println(p1);
		System.out.println();
		
		// Tests Days
		System.out.println("-----DAYS");
		for (int i = 0; i < 20; i++) {
			p1.day();
			System.out.println(p1);
		}
		System.out.println();
		
		// Tests Getter
		System.out.println("-----GETTERS");
		System.out.println("MOST FIT GENOME: " + p1.mostFit());
		System.out.println();
		
	}
}
