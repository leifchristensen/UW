import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Main {

	public static void main(String[] args) {
		// testCodingTree();
		
		

	}
	
	public static void testCodingTree() {
		CodingTree testEncode = new CodingTree("message");
	}
	
	public static void testCompression() {
		try {
			File input = new File("WarAndPeace.txt");
			FileReader fileReader = new FileReader(input);
			
			StringBuilder parsedString = new StringBuilder();
			int i;
			// https://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
			while ((i=fileReader.read()) != -1) { // -1 indicates end of file
				parsedString.append(i);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
	}

}
