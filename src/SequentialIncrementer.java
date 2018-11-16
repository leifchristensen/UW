package src;

public class SequentialIncrementer implements Incrementable {

	private int value;
	
	public SequentialIncrementer() {
		this.value = 0;
	}

	public void increment() {
		this.value++;

	}

	public int getValue() {
		
		return this.value;
	}

}
