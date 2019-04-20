package CSS342;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

public static void main(String[] args) {
		
		System.out.println("-----STACK TEST-----");
		testMyStack();
		

		System.out.println("-----BURGER TEST-----");
		testBurger();
		
		// Tests all lines in customer.txt
		System.out.println("-----LINE TEST-----");
		try {
			Scanner customerScanner = new Scanner(new File("./customer.txt"));
			int orderNum = 0;
			while (customerScanner.hasNext()) {
				String line = customerScanner.nextLine();
				System.out.println("Processing Order " + orderNum + ": " + line);
				parseLine(line);
				System.out.println();
				orderNum++;
			}
			customerScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("expected file: ./customer.txt");
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	} 
	
	public static void parseLine(String line) {
		
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
				pattyType = "PattyVeggie";
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
	
	public static void testBurger() {
		
		
		System.out.println("--Default burger");
		Burger testBurger0 = new Burger(false);		
		System.out.println(testBurger0.toString());
		System.out.println();
		
		System.out.println("--Full Baron Burger");
		Burger testBurger1 = new Burger(true);		
		System.out.println(testBurger1.toString());
		System.out.println();
		
		System.out.println("--Default Burger with a single added ingredient");
		Burger testBurger3 = new Burger(false);
		testBurger3.addIngredient("Onions");
		System.out.println(testBurger3.toString());
		System.out.println();
		
		System.out.println("--Default Burger with a single added category");
		Burger testBurger4 = new Burger(false);
		testBurger4.addCategory("Sauce");
		System.out.println(testBurger4.toString());
		System.out.println();
		
		System.out.println("--Default Burger with a single added category and a single removed ingredient");
		Burger testBurger5 = new Burger(false);
		testBurger5.addCategory("Veggies");
		System.out.println(testBurger5.toString());
		testBurger5.removeIngredient("Mushrooms");
		System.out.println(testBurger5.toString());
		System.out.println();
		
		System.out.println("--Default burger with added patty and cheese");
		Burger testBurger6 = new Burger(false);
		testBurger6.addCategory("Cheese");
		testBurger6.addPatty();
		System.out.println(testBurger6);
		System.out.println();
		
		System.out.println("--Change previous burger patty type");
		testBurger6.changePatties("PattyChicken");
		System.out.println(testBurger6);
		System.out.println();
		
		System.out.println("--Baron burger with removed category");
		Burger testBurger7 = new Burger(true);
		testBurger7.removeCategory("Cheese");
		System.out.println(testBurger7);
		System.out.println();
	}
	
	public static void testMyStack() {
		
		
		// Create an empty test stack of Integers
		System.out.println("--New stack");
		MyStack<Integer> testStack0 = new MyStack<Integer>();
		System.out.println();
		
		// Tests is empty while empty
		System.out.println("--Empty");
		System.out.println("empty = " + testStack0.isEmpty());
		System.out.println(testStack0.toString());
		System.out.println();
		
		// Adds an integer to the stack
		System.out.println("--Push");
		testStack0.push(14);
		System.out.println("empty = " + testStack0.isEmpty());
		System.out.println(testStack0.toString());
		System.out.println();
		
		// Tests peek
		System.out.println("--Peek");
		System.out.println("return = " +testStack0.peek());
		System.out.println("empty = " + testStack0.isEmpty());
		System.out.println(testStack0.toString());
		System.out.println();
		
		// Tests size
		System.out.println("--Size");
		System.out.println("size = " + testStack0.size());
		System.out.println(testStack0.toString());
		System.out.println();
		
		// Tests pop
		System.out.println("--Pop");
		System.out.println("return = " +testStack0.pop());
		System.out.println("empty = " + testStack0.isEmpty());
		System.out.println("size = " + testStack0.size());
		System.out.println(testStack0.toString());
		System.out.println();
		
		// Tests MyStack of String Arrays
		System.out.println("--New stack String Array");
		MyStack<String[]> testStack1 = new MyStack<String[]>();
		testStack1.push(new String[] {"String0", "String1"});
		testStack1.push(new String[] {"String2", "String3"});
		System.out.println(testStack1.toString());
		System.out.println();
		
		// Tests null
		System.out.println("--New stack null");
		MyStack<Object> testStack2 = new MyStack<Object>();
		testStack1.push(null);
		System.out.println(testStack2.toString());
		System.out.println();
		
		// Tests null pop
		System.out.println("--Null Pop");
		try {
			System.out.println("return = " +testStack2.pop());
			System.out.println("empty = " + testStack2.isEmpty());
			System.out.println("size = " + testStack2.size());
			System.out.println(testStack2.toString());
		} catch (Exception e) {
			System.out.println(e.getClass());
		}
		System.out.println();
	}

}
