package src;

import java.io.*;
import java.util.*;

public class PhoneList {

	ArrayList<PhoneCall> list;
	boolean shallow;
	
	public PhoneList(boolean isDeepCopy) {
		this.shallow = isDeepCopy;
		this.list = new ArrayList<PhoneCall>();
	}
	
	public void add(PhoneCall call) {
		if(shallow) {
			list.add(call);
		}
		else {
			list.add(new PhoneCall(call));
		}
	}
	
	public String getCalls() {
		String val = "";
		for (PhoneCall i : this.list) {
			val = val + "\r\n" + i.toString();
		}
		return val;
	}
	
	public double getLongest() {
		double val = 0;
		for (PhoneCall i : this.list) {
			if (i.getMinutes() > val)
				val = i.getMinutes();
		}
		return val;
	}
	
	public PhoneCall getLongestRecord() {
		PhoneCall val = new PhoneCall(0, null, 0, null, 0);
		for (PhoneCall i : this.list) {
			if (i.getMinutes() > val.getMinutes())
				val = i;
		}
		return val;
	}
	
	public int getFromCount(int area, String number) {
		int numCalls = 0;
		for(PhoneCall i : this.list) {
			if(i.getFromAreaCode() == area && i.getFromNumber().equals(number))
				numCalls++;
		}
		return numCalls;
	}
	
	public void sort() {
		this.list.sort(null);
	}
	
	public void printList(PrintStream stream) {
		stream.print(this.getCalls());
	}
	
	public void readList(String filename) throws FileNotFoundException {
		File input = new File(filename);
		if(!input.canRead())
			throw new FileNotFoundException();
		Scanner inputReader = new Scanner(input);	
		inputReader.nextLine();
		while(inputReader.hasNextLine()) {
			Scanner lineReader = new Scanner(inputReader.nextLine());
			lineReader.useDelimiter(",");
			int inArea = 0;
			String inNum = "";
			int outArea = 0;
			String outNum = "";
			double length = 0.0;
			
			
			inArea = lineReader.nextInt();
			inNum = lineReader.next();
			outArea = lineReader.nextInt();
			outNum = lineReader.next();
			length = lineReader.nextDouble();
		
			PhoneCall i = new PhoneCall(inArea, inNum, outArea, outNum, length);
			this.list.add(i);
			lineReader.close();
		}
		inputReader.close();
		
		
	}

}
