import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.sql.Date;
import java.util.concurrent.TimeUnit;

import sun.java2d.pipe.BufferedBufImgOps;

public class Main {

	public static void main(String[] args) throws IOException {
		testCodingTree();
		
		
		File input = new File("WarAndPeace.txt");
		testCompression(input);

	}
	
	public static void testCodingTree() {
		
		System.out.println(">    Basic Message");
		CodingTree testEncode = new CodingTree("message");
		
		System.out.println(">------------------------------------<");
		
		System.out.println(">    Advanced Message");
		CodingTree testEncode2 = new CodingTree("TEsnseisni asereui weiiidfn 123 #� sddfjkseo1"
				+ "fsdlfkdf$�) {[]} asdfghjklpoiuytrewqzxcvbnm asdfghjklpoiuytrewqzxcvbnm QWERTYUIOP���LKJHGFDSAZXCVBNM,.-��1234567890+\\�'|<");
		
		System.out.println(">------------------------------------<");
		
		System.out.println(">    Empty Message");
		CodingTree testEncode3 = new CodingTree("");
		
		System.out.println(">------------------------------------<");
	}
	
	public static void testCompression(File input) throws IOException {
		
		
		System.out.println(">------------------------------------<");
		System.out.println(">    " + input.getName());
		
		// Read file
		FileReader fileReader = new FileReader(input);
		BufferedReader buffer = new BufferedReader(fileReader);
		StringBuilder parsedString = new StringBuilder();
		int i;
		while((i=buffer.read()) != -1) {
			parsedString.append(((char) i));
		}
		buffer.close();
		// Compress
		long startTime = System.currentTimeMillis();
		CodingTree testEncode = new CodingTree(parsedString.toString());
		long runtime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime);
		

		System.out.println(">    Original Size: \t" + input.length());
		// https://stackoverflow.com/questions/625433/how-to-convert-milliseconds-to-x-mins-x-seconds-in-java
		System.out.println("Run Time : " + runtime);
	}

}
