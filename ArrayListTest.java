import java.util.*;
public class ArrayListTest {
   public static void main(String[] args) {
   
      ArrayList<Integer> list = new ArrayList<Integer>();
      list.add(3);
      list.add(12);
      list.add(2);
      System.out.print(minToFront(list));
      
      
   }
   public static ArrayList<Integer> minToFront(ArrayList<Integer> list) {
       ArrayList<Integer> returnList = new ArrayList<Integer>();
       int minIndex = 0;
       int minVal = (int) list.get(0);
       for (int i = 0; i < list.size(); i++)
       {
         if((int) list.get(i) < minVal)
         {
            minVal = (int) list.get(i);
            minIndex = i;
            
         }
       }
       returnList.add(list.get(minIndex));
       for (int i = 0; i < minIndex; i++) 
       {
         returnList.add(list.get(i));
       }
       for (int i = minIndex + 1; i < list.size(); i++) 
       {
         returnList.add(list.get(i));
       }
       return returnList;
}
}
