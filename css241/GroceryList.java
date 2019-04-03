package css241;


/**
 * This class is a wrapper for an array of GroceryItemOrder objects.
 * @author leif chrsitensen
 * @version 10/29/2018
 *
 */
public class GroceryList {
	GroceryItemOrder[] list;
	
	/**
	 * Empty constructor. Creates a list with no elements
	 */
	public GroceryList() {
		this.list = new GroceryItemOrder[0];
	}
	
	
	/**
	 * Would create new instance of GroceryItemOrder object if there was a get field for the fields or a copy constructor for that class.
	 * Without a means of retrieving all fields needed for the GroceryItemOrder constructor, this can only be a shallow copy.
	 * @param other GroceryList to copy
	 */
	public GroceryList(GroceryList other) {
		this.list = new GroceryItemOrder[other.list.length];
		for (int i = 0; i < other.list.length; i++) {
			this.list[i] = other.list[i]; // Missing copy constructor or get methods in GroceryItemOrder		
			// When GroceryItemOrder contains a copy constructor, uncomment the following:
			/*
			 * this.list[i] = new GroceryItemOrder(other.list[i]);
			 */
			// If GroceryItemOrder contains get methods for all fields in full constructor, uncomment the following:
			/*
			 * this.list[i] = new GroceryItemOrder(other.list[i].getName, other.list[i].getQuantity, other.list[i].getPricePerUnit);
			 */
		}
	}
	
	/**
	 * Appends a new item to the end of the Grocery Order list
	 * @param item Item to append
	 */
	public void add(GroceryItemOrder item) {
		GroceryItemOrder[] tempList = new GroceryItemOrder[this.list.length+1];
		tempList[tempList.length-1] = item;
		this.list = tempList;
	}
	
	/**
	 * Returns total cost of all Grocery Item Orders in the list
	 * @return sum as unrounded double
	 */
	public double getTotalCost() {
		double returnValue = 0.0;
		for (GroceryItemOrder i : this.list) {
			returnValue += i.getCost();
		}
		return returnValue;
	}
	

	public String toString() {
		String returnString = new String();
		for (GroceryItemOrder i : this.list) {
			returnString.concat(i.toString() + "\r\n");
		}
		return returnString;
	}
	
}

