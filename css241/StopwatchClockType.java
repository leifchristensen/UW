package css241;

public class StopwatchClockType extends ClockSecondsType {

	private int deciSecs;
	
	public StopwatchClockType() {
		this.deciSecs = 0;
	}

	public StopwatchClockType(int decisecs) {
		super(0);
		this.deciSecs = decisecs;
	}
	
	public int getDeciSecs() {
		return this.deciSecs;
	}
	
	public void setTime(int h, int m, int s, int d) {
		super.setTime(h, m, s);
		this.deciSecs = d;
	}
	
	public String toString() {
		return super.toString() + this.deciSecs; 
	}
	
	public boolean equals(Object other) {
		if (other != null) {
			   if (other.getClass() == this.getClass()) {
				   StopwatchClockType otherTemp = (StopwatchClockType) other;
				   return otherTemp.getDeciSecs() == this.deciSecs;
						   
			   }
			   return false;
		   }
		   return false;
	}

}
