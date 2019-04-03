package css241;

public class Player {

	private String firstName;
	private String lastName;
	private int points;
	
	public Player(String firstname, String lastName) {
		setFirstName(firstname);
		setLastName(lastName);
		setPoints(0);
	}
	
	/**
	 * @return The player's first name
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param The first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return The player's last name
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName The last name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the number of points
	 */
	public int getPoints() {
		return points;
	}
	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	
	/**
	 * Increments the player's total number of points. Negative values decrement total
	 * @param numPoints number of points to increment/decrement total by.
	 */
	public void incrementPoints(int numPoints) {
		this.points += numPoints;
	}
	
	

}
