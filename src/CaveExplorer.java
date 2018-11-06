package src;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.management.InvalidAttributeValueException;

/**
 * This class represents an explorer in a cave system. 
 * 
 * The cave system is represented by a 2d array where each position has an amount of work needed to move the explorer into that position. 
 * The explorer is represented by a -1 value in the array.
 * Positions where the explorer has already traveled are represented by a 0 value in the array.
 * 
 * The explorer moves on vertical and horizontal axis only into the array position with the lowest work value.
 * The explorer cannot move into positions where he has already traveled.
 * The explorer succeeds when he reaches a position on the edge of the cave array.
 * The explorer fails when he reaches a position where all of the surrounding tiles have already been explored.
 * 
 * @author leif christensen
 *
 */
public class CaveExplorer {
	private int[][] cave;
	private int locX;
	private int locY;
	private int numMoves;
	private int effort;
	private ArrayList<int[]> moves;
	
	/**
	 * Creates a cave system grid of a given size. 
	 * Each cell in the grid can have a maximum effort value of a given size.
	 * Instantiates Explorer at random X and Y coordinates.
	 * sets initial effort, and number of moves to 0.
	 * sets the initial state of the moves list to the starting position of the explorer.
	 * @param x Cave width
	 * @param y Cave height
	 * @param bound Maximum effort value
	 * @throws InvalidAttributeValueException 
	 */
	public CaveExplorer(int x, int y, int bound) throws InvalidAttributeValueException{
		// Ensures that x, y, and bound are all > 0
		if(x < 0 || y < 0 || bound < 0) {
			throw new NegativeArraySizeException("CaveExplorer array can not have negative dimensions");
		}
		
		this.cave = new int[x][y];
		for(int[] i : cave) {
			for(int j = 0; j < i.length; j++) {
				i[j] = (int) (Math.random() * bound-1) + 1; // adding 1 to 1 less than the bound removes the possibility of settings squares to 0
			}
		}
		
		//setLocation((int)Math.random()*this.cave.length, (int) Math.random()*this.cave[0].length);
		this.numMoves = 0;
		this.effort = 0;
		this.moves = new ArrayList<int[]>();
	}
	
	/**
	 * Deep copy constructor
	 * @param other CaveExplorer
	 * @throws InvalidAttributeValueException 
	 */
	public CaveExplorer(CaveExplorer other) throws InvalidAttributeValueException {
		this.cave = new int[other.cave.length][other.cave[0].length];
		for(int i = 0; i < this.cave.length; i++) {
			for(int j = 0; j < this.cave[0].length; j++) {
				this.cave[i][j] = other.cave[i][j];
			}
		}
		
		setLocation(other.locX, other.locY);
		this.numMoves = 0;
		this.effort = 0;
		this.moves = new ArrayList<>();
		
		for (int[] i : other.moves) {
			this.moves.add(i);
		}
	}
	
	/**
	 * Get method for number of moves the cave explorer has taken.
	 * @return number of moves
	 */
	public int numOfMoves() {
		return this.numMoves;
	}
	
	/**
	 * Get method for the amount of effort the explorer has used.
	 * @return amount of effort - a sum of all square values the explorer has passed through.
	 */
	public int effort() {
		return this.effort;
	}
	
	/**
	 * Sets the position of the explorer within the cave system.
	 * WARNING: This does not change the value of any cave array position. 
	 * To ensure integrity between the cave system position values and the location of the explorer, please use CaveExplorer.Move();
	 * updates list of moves with new location's index values.
	 * @param x Cave width position
	 * @param y Cave height position
	 * @throws InvalidAttributeValueException Error if X or Y are negative
	 */
	public void setLocation(int x, int y) throws InvalidAttributeValueException {
		// Checks if there is an array to set the initial position within.
		if(this.cave == null) {
			throw new NullPointerException("CaveExplorer array can not be null");
		}
		if(x < 0 || y < 0) {
			throw new InvalidAttributeValueException("CaveExplorer array can not have negative dimensions");
		}
		this.locX = x;
		this.locY = y;
		
	}
	
	/**
	 * Gets the value of effort at a given coordinate
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return value of effort needed to move into the square
	 */
	public int getLocation(int x, int y) {
		int returnArr = cave[x][y];
		return returnArr;
	}
	
	/**
	 * Returns a byte array stream of the cave system for use in multiple output formats.
	 * Format is tab separated between values in a row, and carriage return character between rows.
	 * 
	 * -1 represents the current location of the explorer.
	 * 0 represents positions where the explorer has traveled.
	 * >0 represents the amount of work needed for the explorer to enter a location.
	 * 
	 * IN: {{1	1	2	3	1}     {2	1	3	3	1}}
	 * OUT: {1	1	2	3	1	\r	2	1	3	3	1 \r}
	 * @param out2 
	 * 
	 * @param out
	 * @throws IOException
	 */
	public void draw(PrintStream out) throws IOException {
		
		for(int i = 0; i < this.cave.length; i++) {
			for(int j = 0; j < this.cave[0].length; j++) {
				out.print(this.cave[i][j]);
				out.print("\t");
			}
			// Adds carriage return byte at the end of each row. 
			out.println();
		}
	}
	
	/**
	 * Tests if the cave explorer has reached the edge of the cave system.
	 * @return
	 */
	public boolean isOut() {
		if (this.locX == 0 || this.locX == this.cave.length-1 ||
			this.locY == 0 || this.locY == this.cave[0].length-1) {
				return true;
		}
		return false;
	}
	
	/**
	 * Tests to see if any possible moves for the cave explorer exist. 
	 * @return true if move locations exist or if explorer is on edge of cave. False if all surrounding squares have been previously explored.
	 */
	public boolean canMove() {
		if (!isOut()) {
			if (	this.locX +1 == 0 &&
					this.locX -1 == 0 &&
					this.locY -1 == 0 &&
					this.locY +1 == 0) {
				return false;
			}
			return true;
		}
		return true;
		
	}
	
	/**
	 * Moves the location of the cave explorer to the adjacent square with the lowest required effort to enter.
	 * The total effort gained will be increased by the effort needed to enter the lowest-effort square.
	 * Updates the number of moves by one for each move.
	 * 
	 * @throws InvalidAttributeValueException
	 */
	public void move() throws InvalidAttributeValueException {
		if(!this.isOut() && canMove()) {
			int xplus = getLocation(this.locX +1, this.locY);
			int xmin = getLocation(this.locX -1, this.locY);
			int yplus = getLocation(this.locX , this.locY +1);
			int ymin = getLocation(this.locX , this.locY-1);
			
			int[] effort = {xplus, xmin, yplus, ymin};
			int lowestEffort = -1; //This default index value causes ties between all values to fail if no direction is chosen.
			int effortIndex = 0;
			
			//Changes effortIndex to first non-zero value
			while(effort[effortIndex]==0 && effortIndex < effort.length) {
				effortIndex++;
			}
			
			for (int i = 0; i < effort.length; i++) {
				// Only sets lowestEffort to valid value if effortIndex is set to lowest nonzero value.
				if (effort[i] <= effort[effortIndex] && effort[i] > 0) {
					effortIndex = i;
					lowestEffort = effortIndex; 
				}
			}
			
			//lowesteffort can be: xplus, xmin, yplus, ymin
			switch (lowestEffort) {
			case 0:
				this.effort += this.cave[this.locX+1][this.locY];
				this.setLocation(this.locX+1, this.locY);
				setToZero();
				this.updateMoves();
				this.numMoves++;
				break;
			case 1:
				this.effort += this.cave[this.locX-1][this.locY];
				this.setLocation(this.locX-1, this.locY);
				setToZero();
				this.updateMoves();
				this.numMoves++;
				break;
			case 2:
				this.effort += this.cave[this.locX][this.locY+1];
				this.setLocation(this.locX, this.locY+1);
				setToZero();
				this.updateMoves();
				this.numMoves++;
				break;
			case 3:
				this.effort += this.cave[this.locX][this.locY-1];
				this.setLocation(this.locX, this.locY-1);
				setToZero();
				this.updateMoves();			
				this.numMoves++;
				break;

			default:
				
				break;
			}
		}
		
		
		
		
	}
	
	/**
	 * returns a series of line-separated strings representing the moves taken by the explorer.
	 * @return [x,y] coordinates of each move
	 */
	public String getPath() {
		String route = "";
		for (int i = 0; i < this.moves.size(); i++) {
			route = route.concat(this.moves.get(i)[0] + ", " + this.moves.get(i)[1]);
			route = route.concat("\r\n");
		}
		return route;
	}
	
	/**
	 * Quick means of setting the value of the explorer's position to 0. Used primarily in move().
	 */
	private void setToZero() {
		this.cave[this.locX][this.locY] = 0;
	}
	
	private void updateMoves() {
		int[] loc = {this.locX,this.locY};
		this.moves.add(loc);
	}
	
	
	
}
