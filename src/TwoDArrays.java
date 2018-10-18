package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Demonstrates comparing 2-2D arrays for equality and displaying
 * both arrays formatted. The 7 arrays' contents are read in from the input
 * file Arrays.txt. Each array in this file is preceeded by its dimensions 
 * (2 values of course). We also demonstrate here opening input and output 
 * files where all input and output is read from and sent to files.
 *
 * @author David Schuessler, Monika Sobolewska
 * @version Arpril 2018
  */
public class TwoDArrays {

  /*
   * Driver method of the class TwoDArrays processing.
   *
   * @param theArgs is used for command line input - not used.
   */
  public static void main (String[] theArgs) throws FileNotFoundException {
    Scanner input = new Scanner(new File("Arrays.txt"));
    PrintStream output = new PrintStream(new File("ArraysOutput.txt"));
    
    // 7  2-D arrays set up and data read from the file
    int[][] a1 = getArray(input);
    int[][] a2 = getArray(input);
    int[][] a3 = getArray(input);
    int[][] a4 = getArray(input);
    int[][] a5 = getArray(input);
    int[][] a6 = getArray(input);
    int[][] a7 = getArray(input);
                  
    // Use various sets of arrays from above as arguments passed to the
    // methods compare2D, which in turn calls print2Arrays2D & areEqual2D 
    // to output the results of the comparisons
    output2DCompares(a1, a2, output);
    output2DCompares(a1, a3, output);
    output2DCompares(a1, a4, output);
    output2DCompares(a1, a5, output);
    output2DCompares(a1, a6, output);
    output2DCompares(a1, a7, output);
    
    // etc.

  // Instead of using multiple 2D arrays, we could more easily use a 
  // single 3D array:
  // You can comment out the code declaring the 7 arrays and
  // the 6 calls to output2DCompares above and un-comment the following
  // code by removing the start and end multiline comment marks below:
  
  /*  Take out this line and the end of multiline mark below
  
  int[][][] a = new int[7][][];
  for (int depth = 0; depth < 7 && input.hasNext(); depth++) {
    a[depth] = getArray(input);
  }
  // Perhaps compare the first array with each of the others:
  for (int i = 1; i < a.length; i++) {
    output2DCompares(a[0], a[i], output);
  }
  */                  
  } // end of main
  
  /**
   * Prints out the 2 received arrays to the output file by calling
   * the method print2Arrays2D for the output of both arrays and the
   * method areEqual2D to determine whether or not they are equal.
   *
   * @param theA first array to be compared, values assigned.
   * @param theB second array to be compared, values assigned.
   * @param theOut output file opened for writing.
   */
  public static void output2DCompares(int[][] theA, int[][] theB, 
                                      PrintStream theOut) {
    theOut.print("The arrays:\n" + print2Arrays2D(theA, theB) +
                  (areEqual2D(theA, theB) ? "are" : "are not") + " equal!\n\n"); 
  }  

  /** 
   * Reads from the input file the size of the array to create it (rows and 
   * columns) and the values to be stored in a single array and returns
   * the resulting array to the calling method.
   *
   * @param theInput open input file that contains the array dimensions and its values.
   *
   * @return A 2-dimensional array containing the file input values.
   */
  public static int[][] getArray(Scanner theInput) {
    // Create the array based on the input dimensions
    int[][] a = new int[theInput.nextInt()][theInput.nextInt()];
    
    // Read in the values in Row Major Order:
    for (int row = 0; row < a.length; row++) {
      for (int col = 0; col < a[row].length; col++) {
        a[row][col] = theInput.nextInt();
      }
    }
    return a;
  }

  /**
   * Determines whether or not the 2 passed arrays are equal.
   * Because Java supports "Jagged Arrays" the length of each
   * row (number of columns) of both arrays needs to be compared
   * as well as the number of rows of both arrays.
   *
   * @param a 2D array to be compared, values assigned.
   * @param b 2D array to be compared, values assigned.
   *
   * @return true if dimensions of a == dimensions of b and all values of a[i][j] == values of b[i][j],
   * false otherwise.
   */
  public static boolean areEqual2D(int[][] a, int[][] b) {
    // Compare the number of rows of each array.
    if (a.length != b.length)
      return false;
    for (int row = 0; row < a.length; row++) {
      // Compare the number of columns on the current row.
      if (a[row].length != b[row].length)
         return false;

      // Compare the stored values in the current row.
      for (int col = 0; col < a[row].length; col++) {
        if( a[row][col] != b[row][col])
            return false;
      }
    }
    return true;
  }
  
  /**
   * This method returns a String representation of the contents
   * of both arrays received as parameters. The arrays will appear
   * left to right (theA to theB, respectively) in the returned
   * resulting String. If one array has more rows than the other,
   * appropriate measures are taken to maintain consistency in the
   * resulting returned String.
   * Note, this does not format the column widths based on the 
   * largest value in the array(s), i.e. the column widths for
   * both arrays are the same in spite of the largest number size
   * in either array.
   *
   * @param theA 2-D array, values assigned.
   * @param theB 2-D array, values assigned.
   *
   * @return A String representation of the contents of both arrays
   * received as parameters.
   */
  public static String print2Arrays2D(int[][] theA, int[][] theB) {
    String result = "";
    int row = 0;
    // The position of B: is based on the number of columns in the theA array
    result += String.format("%s%" + (8 * theA[0].length + 5) + "s\n", "A:", "B:");
    for (row = 0; row < theA.length; row++) {
      // Fencepost for the first value in theA array in the current row:
      result += String.format("%5s", theA[row][0]);
      // concatenate remaining values in theA array:
      for (int col = 1; col < theA[row].length; col++) {
        result += ", " + String.format("%6s", theA[row][col]);
      }
      // concatenate the current row of theB array if it exists:
      if (row < theB.length) {
        // Fencepost for the first value in theB array in the current row:
        result += String.format("%14s", theB[row][0]);
        // concatenate remaining values in theB array:
        for (int col = 1; col < theB[row].length; col++) {
          result += ", " + String.format("%6s", theB[row][col]);
        }
      }
      result += "\n";
    }
    // If more rows in theB array, concatenate its rows to the String:
    for (; row < theB.length; row++) {
      // Fencepost for the first value in theB array in the current row:
      result += String.format("%" + (8 * theA[0].length + 11) + 
                              "s", theB[row][0]);
      // concatenate remaining values in theB array:
      for (int col = 1; col < theB[row].length; col++) {
        result += ", " + String.format("%6s", theB[row][col]);
      }
      result += "\n";
    }
    return result;
  }
}
        
                     
                  