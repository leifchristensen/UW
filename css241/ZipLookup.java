package css241;
// This program uses a file of zip code information to allow a user
// to find zip codes within a certain distance of another zip code.

import java.util.*;
import java.io.*;

/**
 * This program queries the user for a zip code and location and radius and provides a list of zip codes within the radius from the user-input zip code.
 * 
 * @author leifc14
 *
 */
public class ZipLookup {
    
    /**
     * radius of sphere.  Here its the Earth, in miles.
     */
    public static final double RADIUS = 3956.6;

    /**
     * Main entry point for ZipLookup. 
     * Gets zipcode data from txt file, processes data, and displays to user.
     * 
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        giveIntro();
        Scanner console = new Scanner(System.in);

        System.out.print("What zip code are you interested in? ");
        String target = console.next();
        System.out.print("And what proximity (in miles)? ");
        double miles = console.nextDouble();
        System.out.println();

        Scanner input = new Scanner(new File("zipcode.txt"));
        String targetCoordinates = find(target, input);
        Scanner file = new  Scanner(new File("zipcode.txt"));
        showMatches(targetCoordinates, file, miles);
        console.close();
        input.close();
        file.close();
    }

    
    /**
     * Introduces the program to the user.
     * 
     */
    public static void giveIntro() {
        System.out.println("Welcome to the zip code database.");
        System.out.println("Give me a 5-digit zip code and a");
        System.out.println("proximity and I'll tell you where");
        System.out.println("that zip code is located along");
        System.out.println("with a list of other zip codes");
        System.out.println("within the given proximity.");
        System.out.println();
    }

    
    /**
     * // Searches for the given string in the input file; if found, returns the coordinates; otherwise returns (0, 0).
     * 
     * @param target User-input zip code, 5 digits
     * @param input Scanner for zip code data file.
     * @return coordinates of user-input zip code
     */
    public static String find(String target, Scanner input) {
        while (input.hasNextLine()) {
            String zip = input.nextLine();
            String city = input.nextLine();
            String coordinates = input.nextLine();
            if (zip.equals(target)) {
                System.out.println(zip + ": " + city);
                return coordinates;
            }
        }
        // at this point we know the zip code isn't in the file
        // we return fictitious (no match) coordinates
        return "0 0";
    }

    
    /**
     * Shows all matches for the given coordinates within the given number of miles.
     * 
     * @param targetCoordinates Coordinates of user-input zip code
     * @param input input Scanner for zip code data file.
     * @param miles Search radius
     */
    public static void showMatches(String targetCoordinates,
                                   Scanner input, double miles) {
        Scanner data = new Scanner(targetCoordinates);
        double lat1 = data.nextDouble();
        double long1 = data.nextDouble();
        System.out.println("zip codes within " + miles + " miles:");
        while (input.hasNextLine()) {
            String zip = input.nextLine();
            String city = input.nextLine();
            String coordinates = input.nextLine();
            // variable data re-pointed to a new Scanner object
            data = new Scanner(coordinates);
            double lat2 = data.nextDouble();
            double long2 = data.nextDouble();
            double distance = distance(lat1, long1, lat2, long2);
            if (distance <= miles) {
                System.out.printf("    %s %s, %3.2f miles\n",
                                  zip, city, distance);
            }
        }
    }

    
    /**
     * Returns Spherical distance in miles given the latitude and longitude of two points (depends on constant RADIUS).
     * 
     * @param lat1 Latitude of first zip code
     * @param long1 Longitude of first zip code
     * @param lat2 Latitude of second zip code
     * @param long2 Longitude of second zip code
     * @return Distance between the two coordintes in miles.
     */
    public static double distance(double lat1, double long1,
                                  double lat2, double long2) {
        lat1 = Math.toRadians(lat1);
        long1 = Math.toRadians(long1);
        lat2 = Math.toRadians(lat2);
        long2 = Math.toRadians(long2);
        double theCos = Math.sin(lat1) * Math.sin(lat2) +
            Math.cos(lat1) * Math.cos(lat2) * Math.cos(long1 - long2);
        double arcLength = Math.acos(theCos);
        return arcLength * RADIUS;
    }
}
