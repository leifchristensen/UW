package css241;

/**
 * Represents a clock object consisting of hours and minutes
 * @author Monika
 * @version Autumn, 2018
 */
public class BasicClockType {
	
	/**
	 * represents the number of hours.
	 */
	private int hours;
	
	/**
	 * represents the number of minutes.
	 */
	private int minutes;
	
	/**
	 * Parameterless constructor - creates a BasicClockType object, representing 00:00
	 */
	public BasicClockType() {
		hours = 0;
		minutes = 0;
	}
	
	/**
	 * Parameterized constructor - creates a BasicClockType object, representing newHours:newMinutes.
	 * @param newHours >= 0 && <= 23
	 * @param newMinutes >= 0 && <= 59 
	 */
	public BasicClockType(int newHours, int newMinutes) {
		hours = newHours;
		minutes = newMinutes;
	}

	/**
	 * Copy constructor - creates a Basic ClockType as the deep copy of the other.
	 * @param other represents another BasicClockType object to be copied, !null
	 */
	public BasicClockType(BasicClockType other) {
		this(other.hours, other.minutes);	
	}
	
	/**
	 * Returns the number of hours.
	 * @return hours
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * Returns the number of minutes.
	 * @return minutes
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * Resets the number of hours and minutes to newHours and newMinutes.
	 * @param newHours >= 0 && <= 23
    * @param newMinutes >= 0 && <= 59
	 */
	public void setTime(int newHours, int newMinutes) {
		hours = newHours;
		minutes = newMinutes;
	}

	
	/** 
	 * Overrides Java toString method.
	 * @return a String object representing BasicClockType
    */
	public String toString() {
		String representation = "";
		if(hours < 10)
			representation += "0";
		representation = representation + hours + ":";
		if(minutes < 10)
			representation += "0";
		representation = representation + minutes;
		return representation;
	}
}
