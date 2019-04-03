package css241;

//import java.io.FileNotFoundException;
import javax.management.InvalidAttributeValueException;



/**
 * Alternate version of CaveExplorerDriver with support for diagonals and statistical analysis.
 * @author leif christensen
 * @version 11/05/2018
 *
 */
public class CaveExplorerDriverEC {

	/**
	 * This runs several iterations of an explorer and prints statistical info on the series of explorers. Diagonal moves are allowed.
	 * @param args Not Implemented 
	 */
	public static void main(String[] args) {

		final int XBOUND = 14;
		final int YBOUND = 10;
		final int BOUND = 12;

		// Statistical collection arrays. Change value of NUMRUNS to change number of repeated attempts.
		final int NUMRUNS = 1000;
		int[] runsEffort = new int[NUMRUNS];
		int[] runsMoves = new int[NUMRUNS];
		int[] runsExit = new int[NUMRUNS];
		int totalEffort = 0;
		int totalMoves = 0;
		int totalExit = 0;
		double avgEffort = 0;
		double avgMoves = 0;
		double avgExit = 0;

		// Runs simulation and stores each run's stats in arrays.
		for (int i = 0; i < NUMRUNS; i++) {
			try {
				// Initializes new Explorer with proper boundaries
				int[] results = runExplorer(XBOUND, YBOUND, BOUND);
				runsEffort[i] = results[0];
				runsMoves[i] = results[1];
				runsExit[i] = results[2];
			}
			catch (Exception e){
				System.out.println(e);
			}
			
			
		}
		
		// Sets totals
		for (int i = 0; i < NUMRUNS; i++) {
			totalEffort = arraySum(runsEffort);
			totalMoves = arraySum(runsMoves);
			totalExit = arraySum(runsExit);		
		}
		avgEffort = (double) totalEffort / NUMRUNS;
		avgMoves = (double) totalMoves / NUMRUNS;
		avgExit = (double) totalExit / NUMRUNS;
		
		// Prints results to console
		System.out.println("# of Runs:\t" + NUMRUNS);
		System.out.println();
		
		System.out.println("Effort:");
		System.out.println("**************");
		System.out.println("Total:\t\t" + totalEffort);
		System.out.println("Average:\t" + avgEffort);
		System.out.println();
		
		System.out.println("Moves:");
		System.out.println("**************");
		System.out.println("Total:\t\t" + totalMoves);
		System.out.println("Average:\t" + avgMoves);
		System.out.println();
		
		System.out.println("# Exited:");
		System.out.println("**************");
		System.out.println("Total:\t\t" + totalExit);
		System.out.println("Average:\t" + (avgExit* 100) + "%");
		System.out.println();


	} 
	
	/**
	 * Helper function for adding arrays.
	 * @param in Array to sum
	 * @return sum of array
	 */
	private static int arraySum(int[] in) {
		
		int returnVal = 0;
		for(int i : in) {
			returnVal += i;
		}
		return returnVal;
	}

	public static int[] runExplorer(int xBound, int yBound, int effortBound) throws InvalidAttributeValueException {
		// results array is in format [effort,moves,exited]

		int[] results = new int[3];
		
		// create one cave system  
		CaveExplorer explorer = new CaveExplorer(xBound, yBound, effortBound);
		
		// Sets explorer location
		int x = 0;
		int y = 0;
		
		while(x <= 1 || x >= xBound) {
			x = (int) (Math.random()*xBound);
		}
		while(y <= 1 || y >= yBound) {
			y = (int) (Math.random()*yBound);
		}
		
		explorer.setLocation(x, y);


		// try to get out. Diagonal Moves accepted
		while(!explorer.isOut() && explorer.canMove() && explorer.canMoveDiag()) {
			explorer.moveDiag();
		}


		// sets exited index to 1 if exited, 0 if not  
		if (explorer.isOut() )
			results[2] = 1;
		else
			results[2] = 0;

		results[1] = explorer.numOfMoves();
		results[0] = explorer.effort();

		return results;

	}
}