package src;

public class MoneyType {
	private String currency;
	private int wholePart;
	private int fractPart;
	
	// Parameterless Constructor
	public MoneyType() {
		this.currency = "USD";
		this.wholePart = 0;
		this.fractPart = 0;
	}
	
	// Full Constructor
	public MoneyType(int whole, int fract, String type) {
		this.currency = type;
		this.wholePart = whole;
		this.fractPart = fract;
	}
	
	public String getType() {
		return this.currency;
	}
	public int getWholePart() {
		return this.wholePart;
	}
	public int getFractPart() {
		return this.fractPart;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String toString() {
		return "(" + this.currency + ") " + this.wholePart + "." + this.fractPart;
		// (USD) 10.34
	}
	
	public MoneyType add(MoneyType addition) {
		if(addition.currency.equals(this.currency)) {
			
			return new MoneyType(
					addition.wholePart + this.wholePart + (addition.fractPart + this.fractPart)/100,
					(addition.fractPart + this.fractPart)%100,
					this.currency
					);
			// Adding sum of fracts / 100 will round down to 0 if sum of fracts is below 100, round to 1 if fracts add to more than 100.
			// Modulus to remove trailing zeroes from fractional part. 
		} else {
			return null;
		}
	}
	
}
