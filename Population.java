import java.util.LinkedList;

public final class Population {
	
	private LinkedList<Genome> populationList;
	private final int initPopulation = 12;
	
	private Genome mostFit;
	
	public Population() {
		this.populationList = new LinkedList<Genome>();
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
	
	public void day() {
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
