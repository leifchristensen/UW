package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;



public class CaveExplorerDriver {

   public static void main(String[] args) throws FileNotFoundException {
   
      PrintStream out1 = new PrintStream(new File("cave_system.txt"));
      PrintStream out2 = System.out;
      
      // create one cave system  
      CaveExplorer myt1 = new CaveExplorer(15, 13, 15);
      // create another cave system, equivalent to the first one
      CaveExplorer myt2 = new CaveExplorer(myt1);
      
      // for the first cave system, set the location of the explorer and draw
      myt1.setLocation(15/2, 13/2);
      myt1.draw(out2);
      myt1.draw(out1);
     
      System.out.println("********************");
      out1.println("********************");
      
      // for the second cave system, set the location of the explorer and draw
      myt2.setLocation(8, 7);
      myt2.draw(out2);
      myt2.draw(out1);
      System.out.println("********************");
      out1.println("********************");

      // system 1: try to get out
      while(!myt1.isOut() && myt1.canMove()) {
         myt1.move();
      }
      
      // system 2: try to get out
      while(!myt2.isOut() && myt2.canMove()) {
         myt2.move();
      }
        
      // system 1: print the system after the exploration along with the label, path, and effort   
      if (myt1.isOut() )
         System.out.println("found the way");
      else
         System.out.println("no way out");
      myt1.draw(out2);
      myt1.draw(out1);
      
      System.out.println("my path: " + myt1.getPath());
      System.out.println(myt1.numOfMoves());
      System.out.println(myt1.effort());
            
      System.out.println("********************");
      out1.println("********************");
      
      // system 2: print the system after the exploration along with the label, path, and effort  
      if (myt2.isOut() )
         System.out.println("found the way");
      else
         System.out.println("no way out");
      myt2.draw(out2);
      myt2.draw(out1);
      
      System.out.println("my path: " + myt2.getPath());
      System.out.println(myt2.numOfMoves());
      System.out.println(myt2.effort());

   }   
}