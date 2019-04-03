package css241;

public class RandomIncrementer implements Incrementable {

	private int value;
	
	public RandomIncrementer() {
		this.value = (int) (Math.random()*100);
	}

	public void increment() {
		this.value = (int) (Math.random()*100);

	}

	public int getValue() {
		
		return this.value;
	}

}
