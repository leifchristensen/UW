package css241;

import java.util.Objects;


/**
 * Represents a US phonecall object.
 * @author Monika
 * @version Apr. 28, 2018
 * class invariant: fromAreaCode >= 200 or 0 to indicate error
 * 			   toAreaCode >= 200 or 0 to indicate error
 * 			   minutes >=0
 */
public class PhoneCall implements Comparable<PhoneCall> {
	/**
	 * caller area code.
	 */
	private int fromAreaCode;
	/**
	 * caller number.
	 */
	private String fromNumber;
	/**
	 * callee area code.
	 */
	private int toAreaCode;
	/**
	 * callee number.
	 */
	private String toNumber;
	/** 
	 * conversation length in minutes.
	 */
	private double minutes;
	
	/**
	 * Parameterized constructor: constructs a phonecall object.
	 * @param fAreaCode is a caller area code, >= 200
	 * @param fNum is a string representing caller's number
	 * @param tAreaCode >= 200
	 * @param tNum is a string representing callee's number
	 * @param mins >= 0
	 */
	public PhoneCall(int fAreaCode, String fNum, int tAreaCode,
			String tNum, double mins) {
		fromAreaCode = fAreaCode;
		if(fromAreaCode < 200)
			fromAreaCode = 0;
		fromNumber = fNum;
		toAreaCode = tAreaCode;
		if(toAreaCode < 200)
			toAreaCode = 0;
		toNumber = tNum;
		minutes = mins;
		if(minutes < 0)
			minutes = 0;
	}
	
	/**
	 * Copy constructor: creates a deep copy of a phonecall.
	 * @param other is the object to be copied
	 */
	public PhoneCall(PhoneCall other) {
		this(other.fromAreaCode, other.fromNumber, other.toAreaCode, other.toNumber, other.minutes);
	}
	
	/**
	 * Returns caller's area code.
	 * @return caller's area code
	 */
	public int getFromAreaCode() {
		return fromAreaCode;
	}
	
	/**
	 * Resets caller's area code.
	 * @param fAreaCode >= 200
	 */
	public void setFromAreaCode(int fAreaCode) {
		fromAreaCode = fAreaCode;
		if(fromAreaCode < 200)
			fromAreaCode = 0;
	}
	
	/**
	 * Returns caller's phone number.
	 * @return caller's phone number
	 */
	public String getFromNumber() {
		return fromNumber;
	}
	
	/**
	 * Resets caller's phone number.
	 * @param fNumber is a string representing a caller's number
	 */
	public void setFromNumber(String fNumber) {
		fromNumber = fNumber;
	}
	
	/**
	 * Returns calle's area code.
	 * @return calle's area code
	 */
	public int getToAreaCode() {
		return toAreaCode;
	}
	
	/**
	 * Resets callee's area code.
	 * @param tAreaCode >= 200
	 */
	public void setToAreaCode(int tAreaCode) {
		toAreaCode = tAreaCode;
		if(toAreaCode < 200)
			toAreaCode = 0;
	}
	
	/**
	 * Returns callee's phone number.
	 * @return callee's phone number
	 */
	public String getToNumber() {
		return toNumber;
	}
	
	/**
	 * Resets callee's phone number.
	 * @param tNumber is a string representing callee's number
	 */
	public void setToNumber(String tNumber) {
		toNumber = tNumber;
	}
	
	/**
	 * Returns the phonecall time as minutes.
	 * @return phonecall time as minutes
	 */
	public double getMinutes() {
		return minutes;
	}
	
	/**
	 * Resets conversation's minutes.
	 * @param minutes >= 0
    */
	public void setMinutes(double mins) {
		minutes = mins;
		if(minutes < 0)
			minutes = 0;
	}

	/**
	 * Constructs a string representation of phonecall fields.
	 * @return string representation of phonecall fields
	 */
	@Override
	public String toString() {
		return "PhoneCall [fromAreaCode=" + fromAreaCode + ", fromNumber="
				+ fromNumber + ", toAreaCode=" + toAreaCode + ", toNumber="
				+ toNumber + ", minutes=" + minutes + "]";
	}

	/** 
	 * Compares this with another phonecall.
	 * @param obj is the other phonecall to compare
	 * @return true if both phone calls equivalent; false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhoneCall other = (PhoneCall) obj;
		return fromAreaCode == other.fromAreaCode && toAreaCode == other.toAreaCode &&
				Math.abs(minutes - other.minutes) < 0.000001 && Objects.equals(fromNumber, other.fromNumber) &&
				Objects.equals(toNumber, other.toNumber);
		
	}

	@Override
	public int compareTo(PhoneCall o) {
		if(  !(this.getFromAreaCode() == o.getFromAreaCode()) )
			return this.getFromAreaCode() - o.getFromAreaCode();
		if( !this.getFromNumber().equals(o.getFromNumber()) )
			return this.getFromNumber().compareTo(o.getFromNumber());
		/*if(!(this.getMinutes() == o.getMinutes()))
			return (int) (this.getMinutes()-o.getMinutes());
		if(!(this.getToAreaCode() == o.getToAreaCode()))
			return this.getToAreaCode() - o.getToAreaCode();
		if(!this.getToNumber().equals(o.getToNumber()))
			return this.getToNumber().compareTo(o.getToNumber());
		*/
		return 0;
	}
	
	
}
