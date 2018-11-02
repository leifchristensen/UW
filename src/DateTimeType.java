package src;

public class DateTimeType extends ClockSecondsType {

	private int day;
	private int month;
	private int year;
	
	public DateTimeType() {
		super();
		this.day = 0;
		this.month = 0;
		this.year = 0;
	}

	public DateTimeType(int day, int month, int year) {
		super();
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public int getDay() {
		return this.day;
	}
	public int getMonth() {
		return this.month;
	}
	public int getYear() {
		return this.year;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	public String toString() {
		return this.day + " " + this.month + " " + this.year + " " + super.toString();
	}
	
	public boolean equals(Object other) {
		if (other != null) {
			   if (other.getClass() == this.getClass()) {
				   DateTimeType otherTemp = (DateTimeType) other;
				   return 
						   otherTemp.getDay() == this.day &&
						   otherTemp.getMonth() == this.month &&
						   otherTemp.getYear() == this.year;
				   
						   
			   }
			   return false;
		   }
		   return false;
	}

}
