import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Burger {
	
	private HashMap<String, Integer> recipieMap;
	
	private MyStack<String> burgerStack;
	
	public Burger(boolean theWorks) {
		burgerStack = new MyStack<String>();
		initializeRecipieMap();
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
		String pattyType = "";
		
		MyStack<String> tempStack = new MyStack<String>();
		// Assumes patty element types start with the string "Patty"
		// for each element, if that element's priority is equal to the patty priority, set the patty type.
		while (!this.burgerStack.isEmpty()) {
			String tempItem = this.burgerStack.pop();
			if(tempItem.contains("Patty")) {
				pattyType = tempItem;
				
			}
			tempStack.push(tempItem);

		}
		
		boolean pattyAdded = false;
		// Add elements back
		while (!tempStack.isEmpty()) {
			
			String tempItem = tempStack.pop();
			// check the priority of each item added back into the stack. 
			// when the next item to be added, tempItem, has a higher priority than the highest patty priority,
			// insert the next patty into the stack before adding the rest of the ingredients.
			
			
			if(recipieMap.get(tempItem) >= 8 && !pattyAdded) { // Assuming patties are added at recipie priority 8
				this.burgerStack.push(pattyType);
				pattyAdded = true;
			}			
			this.burgerStack.push(tempItem);
		}
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
			System.out.println("ERROR: BAD CATEGORY " + type);
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
			System.out.println("ERROR: BAD CATEGORY " + type);
			break;
		}
	}
	
	public void addIngredient(String type) {
		// Must be in proper location
		MyStack<String> tempStack = new MyStack<String>();
		String tempItem = "";
		// Only can add an ingredient that is in recipie.
		if(!this.recipieMap.containsKey(type)) {
			throw new IllegalArgumentException("ERROR: recipie does not contain " + type);
		}
		int targetPriority = this.recipieMap.get(type); // Stack priority to insert element at.
		// removes all ingredients from the temp stack with a higher priority than the one to be added,
		// adds the new ingredient to the temp stack,
		// then adds all ingredients back to the main stack.
		
		// If empty, add the ingredient to the main stack regardless of position.
		if(burgerStack.isEmpty()) {
			this.burgerStack.push(type);
		} else {

			int tempPriority = this.recipieMap.get(burgerStack.peek());
			while (!burgerStack.isEmpty() && tempPriority >= targetPriority) {
				tempItem = burgerStack.pop();
				// Priority looks at the next object in the stack. 
				// If this next object has a lower priority than the target, break the loop.
				if (burgerStack.isEmpty()) {
					tempPriority = -1;
				} else {
					tempPriority = this.recipieMap.get(burgerStack.peek());
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
		
		
	}
	
	public void removeIngredient(String type) {
		MyStack<String> tempStack = new MyStack<String>();
		String tempItem = "";
		// Throws exception if type is not in recipe 
		if(!this.recipieMap.containsKey(type)) {
			throw new IllegalArgumentException("ERROR: recipie does not contain " + type);
		}
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
	
	private void initializeRecipieMap() {
		// Sets priorities for each ingredient. Priorities determine placement when adding new ingredients.
		this.recipieMap = new HashMap<String, Integer>();
		recipieMap.put("BottomBun", 0); // Bottom bun has lowest priority.
		recipieMap.put("Ketchup", 1);
		recipieMap.put("Mustard", 2);
		recipieMap.put("Mushrooms", 3);
		recipieMap.put("PattyBeef", 4);
		recipieMap.put("PattyChicken", 4);
		recipieMap.put("PattyVeggie", 4);
		recipieMap.put("Cheddar", 5);
		recipieMap.put("Mozzarella", 6);
		recipieMap.put("Pepperjack", 7);
		recipieMap.put("Onions", 8);
		recipieMap.put("Tomato", 9);
		recipieMap.put("Lettuce", 10);
		recipieMap.put("Baron-Sauce", 11);
		recipieMap.put("Mayonnaise", 12);
		recipieMap.put("TopBun", 13);
		recipieMap.put("Pickle", 14);
	}

	public static void main(String[] args) {
		
		testBurger();
		
		// Tests all lines in customer.txt
		try {
			Scanner customerScanner = new Scanner(new File("./customer.txt"));
			while (customerScanner.hasNext()) {
				parseLine(customerScanner.nextLine());
			}
			customerScanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			parseLine("Burger");
			parseLine("Baron Burger with no Veggies Sauce but Baron-Sauce");
		}
		
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
		System.out.println(testBurger5.toString());
		testBurger5.removeIngredient("Mushrooms");
		System.out.println(testBurger5.toString());
		
		// Baron burger with added patty
		Burger testBurger6 = new Burger(false);
		testBurger6.addCategory("Cheese");
		testBurger6.addPatty();
		System.out.println(testBurger6);
		
		// Change burger6 patty type
		testBurger6.changePatties("PattyChicken");
		System.out.println(testBurger6);
	}
	
	public static void testMyStack() {
		System.out.println("-----STACK TEST-----");
	}
	
	public static void parseLine(String line) {
		System.out.println("-----LINE TEST-----");
		System.out.println(line);
		boolean theWorks = false; // True if baron burger
		int numPatties = 1; // double, triple are other options
		String pattyType = "PattyBeef";
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
				pattyType = "PattyChicken";
			} else if (tempWord.equals("Veggie")) {
				pattyType = "PattyVegie";
			}
			
			// Omissions/Additions/Exceptions
			else if(tempWord.equals("with")) {
				// On the word With, add ingredients to omissions/additions
				while(lineScanner.hasNext()) {
					tempWord = lineScanner.next();
					// Add all ingredients to omissions until exceptions, passing over the word no.
					if(!tempWord.equals("no") && !tempWord.equals("but")) {
						ommissionsOrAdditions.add(tempWord);
					} else if(tempWord.equals("but")) { // On the word but, add all remaining words to exceptions passing over the word no.
						while(lineScanner.hasNext()) {
							tempWord = lineScanner.next();
							if(!tempWord.equals("no")) {
								exceptions.add(tempWord);
							}
							
						}
					}
				}
			}
				
			
			
		}
		lineScanner.close();
		// Build burger from variables
		// Initialize with theWorks
		Burger testBurger = new Burger(theWorks);
		
		
		// if theWorks, remove ommissions and then add exceptions
		if(theWorks) {
			for(String s : ommissionsOrAdditions) {
				try { // Tries to remove each ingredient/category.
					// If adding an ingredient throws an exception, try to removing the string as a category instead.
					testBurger.removeIngredient(s);
				} catch (IllegalArgumentException e) {
					testBurger.removeCategory(s);
				}
			}
			// Adds back exceptions
			for(String s : exceptions) {
				try { // Tries to add each ingredient/category.
					// If removing an ingredient throws an exception, try to removing the string as a category instead.
					testBurger.addIngredient(s);
				} catch (IllegalArgumentException e) {
					testBurger.addCategory(s);
				}
			}
		} else { // If standard, add additions and then remove exceptions 
			for(String s : ommissionsOrAdditions) {
				try { 
					testBurger.addIngredient(s);
				} catch (IllegalArgumentException e) {
					testBurger.addCategory(s);
				}
			}
			// Adds back exceptions
			for(String s : exceptions) {
				try { 
					testBurger.removeIngredient(s);
				} catch (IllegalArgumentException e) {
					testBurger.removeCategory(s);
				}
			}
		}
		
		// Adds patties
		for(int i = 1; i < numPatties; i++) {
			testBurger.addPatty();
		}
		
		// Changes patty type
		testBurger.changePatties(pattyType);

		
		System.out.println(testBurger.toString());
		
	}

}
