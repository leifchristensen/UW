import java.util.*;
import java.io.*;

public class ConsoleArgs {
    public static void main(String[] args) throws FileNotFoundException {
    
        File input, output;
        Scanner reading;
        PrintStream writing;
        
        for(String cl : args)
            System.out.println(cl);
            
        input = new File(args[0]);
        output = new File(args[1]);
        
        reading = new Scanner(input);
        writing = new PrintStream(output);
        
        while(reading.hasNextLine()) {
            writing.println(reading.nextLine());
        
        }       
       System.out.println("done");
   }

}       
        
        
    