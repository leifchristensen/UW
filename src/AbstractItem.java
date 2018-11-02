package src;

import java.util.Arrays;

public abstract class AbstractItem {
	private String myCatalogID;
	private String[] myAuthors;
	private String myTitle;
	private int myYearPublished;
	
	public String getMyCatalogID() {
		return this.myCatalogID;
	}
	public void setMyCatalogID(String id) {
		this.myCatalogID = id;
	}
	
	public String[] getMyAuthors() {
		return Arrays.copyOf(this.myAuthors, this.myAuthors.length);
	}
	public void  setMyAuthors(String[] authors) {
		myAuthors = Arrays.copyOf(authors, authors.length);
	}
	
	public String getMyTitle() {
		return myTitle;
	}
	public void setMyTitle(String title) {
		this.myTitle = title;
	}
	
	public int getMyYearPublished() {
		return this.myYearPublished;
	}
	public void setMyYearPublished(int year) {
		this.myYearPublished = year;
	}
	
	public abstract String toString();
	
	public boolean equals(Object other) {
		if (other != null) {
			   if (other.getClass() == this.getClass()) {
				   AbstractItem otherHouse = (AbstractItem) other;
				   return otherHouse.getMyCatalogID().equals(this.getMyCatalogID());
						   /*otherHouse.address.equals(this.address) &&
						   otherHouse.id == this.id &&
						   otherHouse.price == this.price &&
						   Arrays.equals(otherHouse.size, this.size);*/
			   }
			   return false;
		   }
		   return false;
	}
}
