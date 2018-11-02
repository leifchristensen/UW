package src;

import java.util.Arrays;

public class HouseType{
	   private static int OBJECTCOUNT = 1;
	   private String address;
	   private int id;
	   private double price;
	   private int[] size;

	   
	   public HouseType(){
	      address = "";
	      price = 0.0;
	      size = new int[10];
	      id = OBJECTCOUNT;
	      OBJECTCOUNT++;
	   }
	   
	   public HouseType(String add, double aprice, int[] asize){
	      address = add;
	      id = OBJECTCOUNT;
	      OBJECTCOUNT++;
	      price = aprice;
	      size = asize;
	      
	   }
	   
	   public String getAddress(){
	      return address;
	   }
	   
	   public double getPrice(){
	      return price;
	   }
	   
	   public int[] getSize(){
	      return size;
	   }
	   
	   public int getId(){
	      return id;
	   }
	   
	   public void setAddress(String add){
	      address = add;
	   }
	   
	   public void setPrice(double aprice){
	      price = aprice;
	   }
	   
	   public String toString(){
	      return ("id: " + id + " address: " + address + " price: " + price + " size: " + calculateSize());
	   }
	   
	   public void setRoomSize(int index, int sqfeet){
	     size[index] = sqfeet;
	   }
	   
	   public double calculateSize(){
	      double total = 0.0;
	      for (int i = 0; i < size.length; i++){
	         total += size[i];
	      }
	      return total;
	   }
	   
	   public double calculatePricePerFoot(){
	      return price / calculateSize();
	   }
	   
	   public boolean equals(Object other) {
		   if (other != null) {
			   if (other.getClass() == this.getClass()) {
				   HouseType otherHouse = (HouseType) other;
				   return otherHouse.getId() == this.id;
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

