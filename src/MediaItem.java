/*
 * TCCS 143 Spring 2018
 */
package src;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Represents media item in the library. 
 * @author Monika
 * @version Apr. 30, 2018
 * class invariant runtime >= 0; tracks number >= 1; year <= current year
 */
public class MediaItem extends AbstractItem {
	 
   
    private double myRuntime;
    
    /**
     * number of tracks.
     */
    private int myTracksNumber;
    
    /**
     * UPC of an item.
     */
    private String myUPC;

    /**
     * Constructs media item object with the following properties:
     *  if aRuntime <= 0, runtime = 0; if number of tracks < 1,
     *          number of tracks = 1; if year > current year, year = current year .
     * @param aCatalogID is a string denoting valid library id number
     * @param aTitle is a string denoting valid title
     * @param aYearPublished is the year the item was published, year <= current year
     * @param authors is a list of authors/ artists
     * @param aRuntime is the running time of media item, > 0
     * @param aTracksNumber is the number of tracks of media item, >= 1
     * @param aUPC is a string denoting a valid UPC number      
     */
    public MediaItem(String aCatalogID, String aTitle, int aYearPublished,
                     String[] authors, double aRuntime, int aTracksNumber, String aUPC)  {
    	super(aCatalogID, aTitle, aYearPublished, authors);
    	
        myRuntime = aRuntime;
        myTracksNumber = aTracksNumber;
        myUPC = aUPC;
        if(myRuntime < 0)
        	myRuntime = 0;
        if (myTracksNumber < 1) {
            myTracksNumber = 1;
        }
        		
    }
    
  
    /**
     * Returns runnning time of an item.
     * @returns runnning time of an item
     */
    public double getMyRuntime() {
        return myRuntime;
    }

    /**
     * Assigns running time of an item.
     * @param aRuntime >= 0
     */
    public void setMyRuntime(double aRuntime) {
        myRuntime = aRuntime;
        if(myRuntime < 0)
        	myRuntime = 0;
    }

    /**
     * Returns the number of tracks.
     * @return number of tracks
     */
    public int getMyTracksNumber() {
        return myTracksNumber;
    }

    /**
     * Assigns number of tracks to a media item.
     * @param aTracksNumber is >= 1
     */
    public void setMyTracksNumber(int aTracksNumber) {
        myTracksNumber = aTracksNumber;
        if (myTracksNumber < 1) {
            myTracksNumber = 1;
        }
    }

    /**
     * Returns UPC number of a media item.
     * @return UPC of media item
     */
    public String getMyUPC() {
        return myUPC;
    }

    /**
     * Assigns UPC number to media item.
     * @param aUPC is a valid string containing an item's UPC
     */
    public void setMyUPC(String aUPC) {
        myUPC = aUPC;
    }

    /**
     * Returns a string representing media item contents.
     * @return MediaItem fields as a string
     */
	public String toString() {
		return "MediaItem [myCatalogID=" + getMyCatalogID() + ", myAuthors="
				+ Arrays.toString(getMyAuthors()) + ", myTitle=" + getMyTitle()
				+ ", myYearPublished=" + getMyYearPublished() + ", myRuntime="
				+ myRuntime + ", myTracksNumber=" + myTracksNumber + ", myUPC="
				+ myUPC + "]";
	}

    
    
   
    

    
}
