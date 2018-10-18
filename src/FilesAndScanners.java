package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Demonstrates opening and reading from an input file with a Scanner
 * and using various Scanner methods. Also, passing file objects Scanner and 
 * PrintStream, to a method.
 *
 * @author David Schuessler, Monika Sobolewska
 * @version April 2, 2018
 */
public class FilesAndScanners {

  /*
   * Driver method of the class FilesAndScanners processing.
   *
   * @param theArgs is used for command line input - none expected.
   */
  public static void main(String[] theArgs) throws FileNotFoundException {
    // Scanner for an input file
    Scanner in = null;
    // PrintStream object for the output file
    PrintStream out = null;
    // opening files
    File myInput = new File("in.txt");
    in = new Scanner(myInput);
    
    File myOutput = new File("out.txt");
    out = new PrintStream(myOutput);
    
    int counter = 0;
        
    if (myInput.canRead() && myOutput.canWrite()) {
      // Count all types of tokens in a file:
      counter = countTokens(in, out);
      out.printf("There are %d tokens in the input!\n", counter);
      in.close();
    } // End if 
    
    // Reopen the same input file to count token types
    in = new Scanner(new File("in.txt"));
    
    if (myInput.canRead() && myOutput.canWrite()) {
      countEachType(in, out);
      out.close();
    } // End if (fileOK)
  }

  /**
   * Counts all the tokens in the given input file and outputs the
   * results to an output file: out.txt.
   *
   * @param Scanner to the input file - file exists and can be read
   * @param PrintStream to the output file: out.txt - file exists and can be written to
   *
   * @return a count of the total number of tokens in the input file.
   */
  public static int countTokens(Scanner theIn, PrintStream theOut) {
      int counter = 0;
      while (theIn.hasNext()) {
        theOut.println(theIn.next());
        counter++;
      }
      return counter;
  }

  /**
   * Counts the various types of tokens (integers, Doubles, and Strings in 
   * the given input file and outputs the results to an output file: out.txt.
   *
   * @param Scanner to the input file - file exists and can be read
   * @param PrintStream to the output file: out.txt - file exists and can be written to
   */
  public static void countEachType(Scanner theIn, PrintStream theOut) {
      int counter = 0, intCount = 0, floatCount = 0;
      while (theIn.hasNext()) {
        if (theIn.hasNextInt()) {
          theOut.print("The current token is an int: " + theIn.nextInt() + "\n");
          intCount++;
        } else if (theIn.hasNextDouble()) {
          theOut.print("The current token is a double: " + theIn.nextDouble() + "\n");
          floatCount++;
        } else {
          theOut.print("The current token is a String: " + theIn.next() + "\n");
          counter++;
        }
      }
      // Uses String.format
      theOut.println(String.format("\nToken counts are as follows:\n" +
                                "\tDoubles = %5d" + 
                                "\n\tIntegers = %4d" + 
                                "\n\tStrings = %5d",
                                floatCount, intCount, counter));          
  }  
}