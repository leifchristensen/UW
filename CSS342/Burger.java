/*
 * Leif Christensen
 * Assignment 1
 * Apr 5 2019
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Burger {
	
	
	
	// Contains the list of valid ingredients. Ingredients are mapped to their numerical order.
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
	
	// Helper method to create Baron Burger
	private void addAllIngredients() {
		addCategory("Cheese");
		addCategory("Veggies");
		addCategory("Sauce");
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
	
	
	public void addPatty() {
		// Must search for the existing patty type and add a new one of the same type
		String pattyType = "";
		
		MyStack<String> tempStack = new MyStack<String>();
		// Assumes patty element types start with the string "Patty"
		// If an element contains "Patty", change the patty type
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
			// when the next item to be added back has a higher priority than the patty priority,
			// insert a patty into the stack before adding the rest of the ingredients.
			
			
			if(recipieMap.get(tempItem) >= 8 && !pattyAdded) { // Assuming patties are added at recipie priority 8
				this.burgerStack.push(pattyType);
				pattyAdded = true;
			}			
			this.burgerStack.push(tempItem);
		}
	}
	

	
	public void changePatties(String pattyType) {
		this.stackChange(pattyType, "PattyBeef", "PattyChicken", "PattyVeggie");
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
	
	// Helper method to change ingredients	
	
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
	
	@Override
	public String toString() {
		return "Burger: " + this.burgerStack.toString();		
	}
	


}
