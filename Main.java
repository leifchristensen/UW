
public final class Main {
	public static void main(String[] args) {
		for (int trial = 1; trial < 5; trial++) {
			long startTime = System.nanoTime();
			Population pop = new Population();
			int i = 0;
			while (pop.mostFit().fitness() > 0 && i < 10000 ) {
				pop.day();
				i++;
				
			}
			System.out.println("Trial #:        " + trial);
			System.out.println("Number of days: " + i);
			System.out.println("Time in seconds:" + (System.nanoTime()-startTime)/Math.pow(10, 9));
			System.out.println();
		}
		
		testGenome();
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
		for (int i = 0; i < 7; i++) {
			
			gen1.mutate();
			System.out.println("GENOME : MUTATE " + i);
			System.out.println("GEN1: " + gen1);
		}
		System.out.println();
		
		// Tests getters
		
		System.out.println("GENOME : GETTERS");
		System.out.println("GEN1: MUTATION RATE " + gen1.getMutationRate());
		System.out.println("GEN1: CHAR SEQ      " + gen1.getCharSequence());
		System.out.println();
		
		// Tests Crossover
		// Mutates a new genome
		Genome gen3 = new Genome(gen1);
		
		for (int i = 0; i < 7; i++) {
			
			gen3.mutate();
		}
		System.out.println(gen3);
		for (int i = 0; i < 7; i++) {
			
			gen1.crossover(gen3);
			System.out.println("GENOME : CROSSOVER " + i);
			System.out.println("GEN1: " + gen1);
		}
		System.out.println();
				
	}
}
