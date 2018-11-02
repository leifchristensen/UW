/*
 * TCCS 143 Spring 2018
 */

import java.util.Arrays;
import java.util.Calendar;

/**
 * Represents a written item in the library.
 * @author Monika
 * @version Apr. 30, 2018
 * class invariant edition >= 1; year published <= current year
 */
public class WrittenItem extends AbstractItem {
	
    
    /**
     * edition or volume number.
     */
    private int myEdition;
    
    /**
     * item ISBN.
     */
    private String myISBN;

    /**
     * Constructs a WrittenItem object with the following properties:
     *                  if edition < 1, edition = 1;
     * 						if year > current year, year = current year
     * @param aCatalogID is a string denoting valid library id number
     * @param aTitle is a string denoting valid title
     * @param aYearPublished is the year the item was published, year <= current year
     * @param aPeople is a list of authors/ artists
     * @param anEdition is the edition number >= 1
     * @param anISBN is a string denoting valid ISBN number 
     */
    public WrittenItem(String aCatalogID, String aTitle, int aYearPublished,
                       String[] authors, int anEdition, String anISBN) {
    	super(aCatalogID, aTitle, aYearPublished, authors);
    	myEdition = anEdition;
    	myISBN = anISBN;
        if (myEdition < 1) {
            myEdition = 1;
        }
        
        
    }

    /**
     * Returns edition number.
     * @return edition number of WrittenItem object
     */
    public int getMyEdition() {
        return myEdition;
    }

    /**
     * Assigns edition number to WrittenItem object.
     * @param anEdition is the edition number >= 1
     */
    public void setMyEdition(int anEdition) {
        myEdition = anEdition;
        if (myEdition < 1) {
            myEdition = 1;
        }
    }

    /**
     * Returns ISBN number.
     * @return ISBN of the WrittenItem object
     */
    public String getMyISBN() {
        return myISBN;
    }

    /**
     * Assigns ISBN number to WrittenNumber object.
     * @param anISBN is a string denoting valid ISBN number
     */
    public void setMyISBN(String anISBN) {
        myISBN = anISBN;
    }

    /**
     * Returns a string representing written item contents.
     * @return WrittenItem fields as a string
     */
	public String toString() {
		return "WrittenItem [myCatalogID=" + getMyCatalogID() + ", myAuthors="
				+ Arrays.toString(getMyAuthors()) + ", myTitle=" + getMyTitle()
				+ ", myYearPublished=" + getMyYearPublished() + ", myEdition="
				+ myEdition + ", myISBN=" + myISBN + "]";
	}
    
    
   
    
    
}