package src;

public class ClockSecondsType extends BasicClockType {
	private int numSeconds;
	
	public ClockSecondsType() {
		this.numSeconds = 0;
	}

	public ClockSecondsType(int seconds) {
		super(0, 0);
		this.numSeconds = seconds;
		
	}
	
	public int getSeconds() {
		return this.numSeconds;
	}
	
	public void setTime(int h, int m, int s) {
		this.setTime(h, m);
		this.numSeconds = s;
	}
	
	public String toString() {
		return super.toString() + this.getSeconds();
	}
	
	public boolean equals(Object other) {
		if (other != null) {
			   if (other.getClass() == this.getClass()) {
				   ClockSecondsType otherTemp = (ClockSecondsType) other;
				   return otherTemp.getSeconds() == this.numSeconds;
						   
			   }
			   return false;
		   }
		   return false;
	}

	
}
