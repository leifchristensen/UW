
public class Burger {
	
	private MyStack<String> burgerStack;
	
	public Burger(boolean theWorks) {
		burgerStack = new MyStack<String>();
		// if default, add single beef patty burger with no ingredients.
		if (!theWorks) {
			addIngredient("BottomBun");
			addIngredient("PattyBeef");
			addIngredient("TopBun");
		}
		
	}
	
	public void changePatties(String pattyType) {
		
	}
	
	public void addPatty() {
		// Must search for the existing patty type and add a new one of the same type
	}
	
	public void addCategory(String type) {
		switch (type) {
		case "Cheese":
			addIngredient("Cheddar");
			addIngredient("Mozzarella");
			addIngredient("Pepperjack");
			break;
		case "Veggies":
			addIngredient("Lettuce");
			addIngredient("Tomato");
			addIngredient("Onions");
			addIngredient("Pickle");
			addIngredient("Mushrooms");
			break;
		case "Sauce":
			addIngredient("Ketchup");
			addIngredient("Mustard");
			addIngredient("Mayonnaise");
			addIngredient("Baron-Sauce");
			break;
		default:
			System.out.println("ERROR: BAD CATEGORY");
			break;
		}
	}
	
	public void removeCategory(String type) {
		
	}
	
	public void addIngredient(String type) {
		// Must be in proper location
	}
	
	public void removeIngredient(String type) {
		MyStack<String> tempStack = new MyStack<String>();
		String tempItem = "";
		// pops each element to the temp stack until the ingredient is found or no elements remain, then adds ingredients back.
		while (!burgerStack.isEmpty()) {
			tempItem = burgerStack.peek();
			tempStack.push(burgerStack.pop());
		}
	}
	
	@Override
	public String toString() {
		return "Burger: " + this.burgerStack.toString();
		
	}
	
	private void addAllIngredients() {
		
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
