package src;

import java.io.IOException;
import java.io.OutputStream;

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
	
	/**
	 * Creates a cave system grid of a given size. 
	 * Each cell in the grid can have a maximum effort value of a given size.
	 * Instantiates Explorer at random X and Y coordinates.
	 * @param x Cave width
	 * @param y Cave height
	 * @param bound Maximum effort value
	 */
	public CaveExplorer(int x, int y, int bound){
		// Ensures that x, y, and bound are all > 0
		if(x < 0 || y < 0 || bound < 0) {
			throw new NegativeArraySizeException("CaveExplorer array can not have negative dimensions");
		}
		
		this.cave = new int[x][y];
		for(int[] i : cave) {
			for(int j : i) {
				j = (int) (Math.random() * bound);
			}
		}
		
		setLocation((int)Math.random()*this.cave.length, (int) Math.random()*this.cave[0].length);
	}
	
	/**
	 * Deep copy constructor
	 * @param other CaveExplorer
	 */
	public CaveExplorer(CaveExplorer other) {
		this.cave = new int[other.cave.length][other.cave[0].length];
		for(int i = 0; i < this.cave.length; i++) {
			for(int j = 0; j < this.cave[0].length; j++) {
				this.cave[i][j] = other.cave[i][j];
			}
		}
		
		setLocation(other.locX, other.locY);
	}
	
	/**
	 * Sets the position of the explorer within the cave system.
	 * WARNING: This does not change the value of any cave array position. 
	 * To ensure integrity between the cave system position values and the location of the explorer, please use CaveExplorer.Move();
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
	 * Returns a byte array stream of the cave system for use in multiple output formats.
	 * Format is tab separated between values in a row, and carriage return character between rows.
	 * 
	 * -1 represents the current location of the explorer.
	 * 0 represents positions where the explorer has traveled.
	 * >0 represents the amount of work needed for the explorer to enter a location.
	 * 
	 * IN: {{1	1	2	3	1}     {2	1	3	3	1}}
	 * OUT: {1	1	2	3	1	\r	2	1	3	3	1 \r}
	 * 
	 * @param out
	 * @throws IOException
	 */
	public void draw(OutputStream out) throws IOException {
		
		
		byte[] tempArr = new byte[this.cave.length*((this.cave[0].length*2)+1)];
		for(int i = 0; i < this.cave.length; i++) {
			for(int j = 0; j < this.cave[0].length; i++) {
				tempArr[i] = (byte) this.cave[i][j];
				tempArr[++i] = '\t';
			}
			// Adds carriage return byte at the end of each row. 
			tempArr[i++] = '\r';
				
			//OS Independent line feed byte:
			//System.getProperty("line.separator").getBytes()[0];
		}		
		
		// Writes temp byte array to output stream
		out.write(tempArr);
	}
	
	
	
}
