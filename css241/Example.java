package css241;
import java.util.*;
import java.io.*;

public class Example {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        
        System.out.print("file name? ");
        Scanner input1 = new Scanner(new File(console.nextLine()));
        System.out.println();

        ArrayList<String> list1 = getWords(input1);
        System.out.println(list1);
        removePlurals(list1);
        System.out.println(list1);
   }

    public static ArrayList<String> getWords(Scanner input) {
        // read all words 
        ArrayList<String> words = new ArrayList<String>();
        while (input.hasNext()) {
            String next = input.next().toLowerCase();
            words.add(next);
        }
       
        return words;
    }
    
    public static void removePlurals(ArrayList<String> allWords) {
    
         for (int i = 0; i < allWords.size(); i++) {
            String word = allWords.get(i);
            if (word.endsWith("s")) {
               System.out.println(word);
               allWords.remove(i);
            }
         }
    }
 }       
        
        
    