import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;

import sun.java2d.pipe.BufferedBufImgOps;

public class Main {

	public static void main(String[] args) throws IOException {
		testCodingTree();
		
		testCompression();

	}
	
	public static void testCodingTree() {
		CodingTree testEncode = new CodingTree("message");
		
		CodingTree testEncode2 = new CodingTree("TEsnseisni asereui weiiidfn 123 #£ sddfjkseo1"
				+ "fsdlfkdf$€) {[]} asdfghjklpoiuytrewqzxcvbnm asdfghjklpoiuytrewqzxcvbnm QWERTYUIOPÅÆØLKJHGFDSAZXCVBNM,.-æø1234567890+\\¨'|<");
	}
	
	public static void testCompression() throws IOException {
		
		File input = new File("WarAndPeace.txt");
		FileReader fileReader = new FileReader(input);
		BufferedReader buffer = new BufferedReader(fileReader);
		StringBuilder parsedString = new StringBuilder();
		int i;
		while((i=buffer.read()) != -1) {
			parsedString.append(((char) i));
		}

		CodingTree testEncode = new CodingTree(parsedString.toString());
		buffer.close();
		
		
	}

}
