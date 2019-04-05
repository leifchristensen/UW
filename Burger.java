import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Burger {
	
	private HashMap<String, Integer> priorityMap;
	
	private MyStack<String> burgerStack;
	
	public Burger(boolean theWorks) {
		burgerStack = new MyStack<String>();
		initializePrioityMap();
		// for all burgers, add single beef patty and buns.
		addIngredient("BottomBun");
		addIngredient("TopBun");
		addIngredient("PattyBeef");
		
		if (theWorks) {
			addAllIngredients();
		} 
		
	}
	
	public void changePatties(String pattyType) {
		this.stackChange(pattyType, "PattyBeef", "PattyChicken", "PattyVeggie");
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
		switch (type) {
		case "Cheese":
			removeIngredient("Cheddar");
			removeIngredient("Mozzarella");
			removeIngredient("Pepperjack");
			break;
		case "Veggies":
			removeIngredient("Lettuce");
			removeIngredient("Tomato");
			removeIngredient("Onions");
			removeIngredient("Pickle");
			removeIngredient("Mushrooms");
			break;
		case "Sauce":
			removeIngredient("Ketchup");
			removeIngredient("Mustard");
			removeIngredient("Mayonnaise");
			removeIngredient("Baron-Sauce");
			break;
		default:
			System.out.println("ERROR: BAD CATEGORY");
			break;
		}
	}
	
	public void addIngredient(String type) {
		// Must be in proper location
		MyStack<String> tempStack = new MyStack<String>();
		String tempItem = "";
		try {
			int targetPriority = this.priorityMap.get(type); // Stack priority to insert element at.
			// removes all ingredients from the temp stack with a higher priority than the one to be added,
			// adds the new ingredient to the temp stack,
			// then adds all ingredients back to the main stack.
			
			// If empty, add the ingredient to the main stack regardless of position.
			if(burgerStack.isEmpty()) {
				this.burgerStack.push(type);
			} else {

				int tempPriority = this.priorityMap.get(burgerStack.peek());
				while (!burgerStack.isEmpty() && tempPriority >= targetPriority) {
					tempItem = burgerStack.pop();
					// Priority looks at the next object in the stack. 
					// If this next object has a lower priority than the target, break the loop.
					if (burgerStack.isEmpty()) {
						tempPriority = -1;
					} else {
						tempPriority = this.priorityMap.get(burgerStack.peek());
					}
					
					tempStack.push(tempItem);
					
				}	
				
				// Add the new ingredient after elements with a higher priority have been removed.
				tempStack.push(type);
				
				
				// Add elements back
				while (!tempStack.isEmpty()) {
					this.burgerStack.push(tempStack.pop());
				}
			}
		} catch (NullPointerException e) {
			
			throw new NullPointerException("Invalid Ingredient: " + type);
		}
		
	}
	
	public void removeIngredient(String type) {
		MyStack<String> tempStack = new MyStack<String>();
		String tempItem = "";
		// pops each element to the temp stack except ingredients to remove, then adds ingredients back to the main stack.
		while (!burgerStack.isEmpty()) {
			tempItem = burgerStack.pop();
			if (!tempItem.equals(type)) {
				tempStack.push(tempItem);
			}
			// If item is equal to the input type param, do not copy it to the temp stack.
		}
		
		// Add elements back
		while (!tempStack.isEmpty()) {
			this.burgerStack.push(tempStack.pop());
		}
	}
	
	@Override
	public String toString() {
		return "Burger: " + this.burgerStack.toString();
		
	}
	
	private void addAllIngredients() {
		addCategory("Cheese");
		addCategory("Veggies");
		addCategory("Sauce");
	}
	
	private void stackChange(String itemToSwapTo, String... itemsToSwapFrom) {
		// Searches the whole stack for instances of an object, and if found swaps the value of that object with a new value.
		MyStack<String> tempStack = new MyStack<String>();
		

		//for each element, if that element's item equals an item to swap from, change it to the item to swap to and save to the temp stack.
		while (!this.burgerStack.isEmpty()) {
			String tempItem = this.burgerStack.pop();
			for (int i = 0; i < itemsToSwapFrom.length; i++) {
				if (itemsToSwapFrom[i].equals(tempItem)) {
					tempItem = itemToSwapTo;
				} 
			}
			tempStack.push(tempItem);

		}
		// Add elements back
		while (!tempStack.isEmpty()) {
			this.burgerStack.push(tempStack.pop());
		}
		
	}
	
	private void initializePrioityMap() {
		// Sets priorities for each ingredient. Priorities determine placement when adding new ingredients.
		this.priorityMap = new HashMap<String, Integer>();
		priorityMap.put("BottomBun", 0); // Bottom bun has lowest priority.
		priorityMap.put("Ketchup", 1);
		priorityMap.put("Mustard", 2);
		priorityMap.put("Mushrooms", 3);
		priorityMap.put("PattyBeef", 4);
		priorityMap.put("PattyChicken", 4);
		priorityMap.put("PattyVeggie", 4);
		priorityMap.put("Cheddar", 5);
		priorityMap.put("Mozzarella", 6);
		priorityMap.put("Pepperjack", 7);
		priorityMap.put("Onions", 8);
		priorityMap.put("Tomato", 9);
		priorityMap.put("Lettuce", 10);
		priorityMap.put("Baron-Sauce", 11);
		priorityMap.put("Mayonnaise", 12);
		priorityMap.put("TopBun", 13);
		priorityMap.put("Pickle", 14);
	}

	public static void main(String[] args) {
		
		testBurger();
		parseLine("Burger");
	}
	
	public static void testBurger() {
		
		System.out.println("-----BURGER TEST-----");
		
		// Default burger
		Burger testBurger0 = new Burger(false);		
		System.out.println(testBurger0.toString());
		
		// Full Baron Burger
		Burger testBurger1 = new Burger(true);		
		System.out.println(testBurger1.toString());
		
		// Default Burger with a single added ingredient
		Burger testBurger3 = new Burger(false);
		testBurger3.addIngredient("Onions");
		System.out.println(testBurger3.toString());
		
		// Default Burger with a single added category
		Burger testBurger4 = new Burger(false);
		testBurger4.addCategory("Sauce");
		System.out.println(testBurger4.toString());
		
		// Default Burger with a single added category and a single removed ingredient
		Burger testBurger5 = new Burger(false);
		testBurger5.addCategory("Veggies");
		testBurger5.removeIngredient("Mushrooms");
		System.out.println(testBurger5.toString());
	}
	
	public static void testMyStack() {
		System.out.println("-----STACK TEST-----");
	}
	
	public static void parseLine(String line) {
		System.out.println("-----LINE TEST-----");
		System.out.println(line);
		boolean theWorks = false; // True if baron burger
		int numPatties = 1; // double, triple are other options
		String pattyType = "Beef";
		LinkedList<String> ommissionsOrAdditions = new LinkedList<String>(); // Ommisions if theWorks, additions if default burger.
		LinkedList<String> exceptions = new LinkedList<String>();
		
		// Scan for variables
		Scanner lineScanner = new Scanner(line);
		while(lineScanner.hasNext()) {
			String tempWord = lineScanner.next();
			// theWorks
			if(tempWord.equals("Baron")) {
				theWorks = true;
			}  
			
			// Patty number
			else if(tempWord.equals("Double")) {
				numPatties = 2;
			} else if(tempWord.equals("Triple")) {
				numPatties = 3;
			} 
			
			// Patty Type
			else if(tempWord.equals("Chicken")) {
				pattyType = "pattyChicken";
			} else if (tempWord.equals("Veggie")) {
				pattyType = "pattyVegie";
			}
			
			// Ommissions/Additions/Exceptions
			else if(tempWord.equals("With")) {
				// On the word With, add ingredients to ommissions/additions
				while(lineScanner.hasNext()) {
					tempWord = lineScanner.next();
					// Add all indredients to ommisions until exceptions, passing over known non-ingredient words.
					if(!tempWord.equals("no") && !tempWord.equals("except")) {
						ommissionsOrAdditions.add(tempWord);
					} else if(tempWord.equals("except")) { // On the word Except, add all remaining words to exceptions.
						while(lineScanner.hasNext()) {
							exceptions.add(lineScanner.next());
						}
					}
				}
			}
				
			// Build burger from variables
			// Initialize with theWorks
			Burger testBurger = new Burger(theWorks);
			// Adds patties
			for(int i = 0; i < numPatties; i++) {
				testBurger.addPatty();
			}
			// Changes patty type
			testBurger.changePatties(pattyType);
			// if theWorks, remove ommissions and then add exceptions
			if(theWorks) {
				for(String s : ommissionsOrAdditions) {
					try { // Tries to remove each ingredient/category.
						// If adding an ingredient throws an exception, try to removing the string as a category instead.
						testBurger.removeIngredient(s);
					} catch (Exception e) {
						testBurger.removeCategory(s);
					}
				}
				// Adds back exceptions
				for(String s : exceptions) {
					try { // Tries to add each ingredient/category.
						// If removing an ingredient throws an exception, try to removing the string as a category instead.
						testBurger.addIngredient(s);
					} catch (Exception e) {
						testBurger.addCategory(s);
					}
				}
			} else { // If standard, add additions and then remove exceptions 
				for(String s : ommissionsOrAdditions) {
					try { 
						testBurger.removeIngredient(s);
					} catch (Exception e) {
						testBurger.removeCategory(s);
					}
				}
				// Adds back exceptions
				for(String s : exceptions) {
					try { 
						testBurger.addIngredient(s);
					} catch (Exception e) {
						testBurger.addCategory(s);
					}
				}
			}
			
		}
		
		
	}

}
