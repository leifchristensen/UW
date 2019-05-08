import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PrimitiveIterator.OfInt;
import java.util.PriorityQueue;
import java.util.UUID;
import java.util.stream.*;

public final class CodingTree {
	
	public HashMap<Character, String> codes;
	
	public String bits;

	public CodingTree(String message) {
		// Creates a priority queue with character elements sorted by frequency.
		// Frequency is updated 
		ArrayList<WeightedCharacter> frequencyList = new ArrayList<CodingTree.WeightedCharacter>();
		codes = new HashMap<Character, String>();
		bits = new String();
		// streams message as chars an
		// Stream used to keep memory usage low for long files & practice.
		// https://www.baeldung.com/java-string-to-stream
		// https://stackoverflow.com/questions/38021061/how-to-use-if-else-logic-in-java-8-stream-foreach
		Stream<WeightedCharacter> messageParserStream = message.chars().mapToObj(o -> new WeightedCharacter(o));
		messageParserStream.forEach(o -> {
			if (frequencyList.contains(o)) {
				// increment weight if character exists already.
				 frequencyList.get(frequencyList.indexOf(o)).weight++;
			} else {
				// add character if it doesn't exist.
				o.weight++;
				frequencyList.add(o);
			}
		});
		// Add a null char with weight 0.
		frequencyList.add(new WeightedCharacter(null));
		// At this point, the frequency list should have all characters in the message with their frequency.
		
		// Next step is to create a frequency tree.
		//frequencyList.sort(null);
		//System.out.println(frequencyList);
		
		PriorityQueue<CharacterNode> charTree = new PriorityQueue<CodingTree.CharacterNode>();
		for(WeightedCharacter wc : frequencyList) {
			charTree.add(new CharacterNode(wc));
		}
		// beginning with the null character of the char tree, pop two nodes with the lowest weight,
		// meaning the top two nodes, and re-add to priority queue, with the new node having a left branch of the newly added node.
	
		
		
		while (charTree.size() > 1) {
			CharacterNode rightBranch = charTree.poll();
			CharacterNode leftBranch = charTree.poll();
			charTree.add(new CharacterNode(leftBranch, rightBranch));
		}
		
		//System.out.println(charTree);
		
		// At this point, the queue contains a single node with branches with a left/right pattern of frequency.
		// Branches to the left are more common, branches to the right are less common.
		// Next step is to traverse the tree and map to the shortest possible unique series of bytes. 
		
		traverseTree(charTree.poll(), "");
		
		System.out.println("CODES: " + this.codes.toString());
		
		encode(message);
		
		writeFile();
		
		System.out.println("--Encoded");
	}
	
	// maps a set of unique string codes for every node in a tree.
	private void traverseTree(CharacterNode node, String startCode) {
		if (node.leftBranch == null && node.rightBranch == null) {
			this.codes.put(node.weightedCharacter.character, startCode);
		} else {
			traverseTree(node.leftBranch, startCode + "0");
			traverseTree(node.rightBranch, startCode + "1");
		}
	}
	
	private void encode(String message) {
		IntStream messageStream = message.chars();
		messageStream.forEach(o -> {
			
				bits = bits.concat((codes.get((char) o)));
			
		});
	}
	
	
	
	private void writeFile() {
		// Creates a string builder and a bitwise file writer
		// https://stackoverflow.com/questions/6981555/how-to-output-binary-data-to-a-file-in-java
		
		
		try {
			// txt file output.
			StringBuilder stringData = new StringBuilder();
			for(int b : this.bits.chars().toArray()) {
				// Appends bits to the string builder
				stringData.append((char) b);
				
				
			}
			FileWriter fileOut = new FileWriter("data" + UUID.randomUUID() + ".txt");
			BufferedWriter stringOut = new BufferedWriter(fileOut);
			stringOut.write(stringData.toString());
			stringOut.close();
			
			// Binary output
			FileOutputStream fs = new FileOutputStream("data" + UUID.randomUUID() + ".dat", true);
			DataOutputStream binaryData = new DataOutputStream(fs);
			
			// parses the byte data from the bit string.
			byte[] byteData = parseBytes(bits);
			// Writes the byte data to the data file.
			binaryData.write(byteData);
			binaryData.close();
			fs.close();

			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	// parses bytes from a string of binary data. 
	// string is read 8 characters at a time, then a bytes is created from the chunk of the string until the entire string is consumed.
	private byte[] parseBytes(String binaryString) {
		OfInt charStream = binaryString.chars().iterator();
		LinkedList<String> chunks = new LinkedList<String>();
				
		// parse the string for chunks of 8 characters.
		while(charStream.hasNext()) {
			String chunk = "0";
			for (int i = 0; i < Byte.SIZE -1; i++) {
				if(charStream.hasNext()) {
					chunk = chunk + parseBit(charStream.next());
				}
				// if the end of the file has been reached, append zeros.
				else {
					chunk = chunk + 0;
				}
			}
			chunks.add(chunk);
		}
		// At this point, the chunks should be filled with strings each containing the most compressed version of the binary string. 
		// each chunk represents a byte.
		// next task is to parse the list of chunks into bytes and add the bytes to the return array.
		byte[] bytesParsed = new byte[chunks.size()];
		for(int i  = 0; i < chunks.size(); i++) {
			String chunk = chunks.get(i);
			Byte chunkByte = Byte.parseByte(chunk,2);
			//bytesParsed[i] = (byte) Integer.parseInt(chunks.get(i), 2);
			bytesParsed[i] = chunkByte;
		}
		
		// At this point, the array is full of the most compressed possible bytes with the last byte appended with zeros if needed.
		return bytesParsed;
	}
	
	private char parseBit(int toParse) {
		switch (toParse) {
		case '0':
			return '0';
		case '1':
			return '1';
		default:
			throw new IllegalArgumentException("non-binary Character: " + toParse);
		}
	}
	
	
	// A character in the frequency tree.
	private class WeightedCharacter implements Comparable<WeightedCharacter> {
		
		public Character character;
		public int weight;
		
		public WeightedCharacter(Integer inputChar) {
			if (inputChar == null) {
				this.character = null;
			}
			else {
				this.character = (char) inputChar.intValue(); // null chars are valid, as this will fill space in end of char strings.
			}
			
			this.weight = 0;
		}
		
		
		
		@Override
		public boolean equals(Object o) {
			if(o == null) return false;
			if(o.getClass() != WeightedCharacter.class) return false;
			if(((WeightedCharacter) o).character == null) return false;
			return this.character.equals(((WeightedCharacter) o).character);
		}

		@Override
		public int compareTo(WeightedCharacter o) {
			if(o == null) return 1;
			return this.weight - o.weight;
		}
		
		public String toString() {
			return (this.character != null ? this.character : "null") + "|" + this.weight;
		}
	}
	
	// Nodes in the frequency tree.
	class CharacterNode implements Comparable<CharacterNode>{
		WeightedCharacter weightedCharacter;
		int weight;
		CharacterNode leftBranch;
		CharacterNode rightBranch;
		
		public CharacterNode(WeightedCharacter weightedCharacter) {
			this.weightedCharacter = weightedCharacter;
			weight =  weightedCharacter.weight;
			this.leftBranch = null;
			this.rightBranch = null;
		}
		
		public CharacterNode(CharacterNode left, CharacterNode right) {
			weight = left.weight + right.weight;
			this.leftBranch = left;
			this.rightBranch = right;
		}
		
		public String toString() {
			if (leftBranch == null && rightBranch == null) return this.weightedCharacter.toString();
			return "{" + (this.leftBranch == null ? "null" : this.leftBranch.toString()) + " | " + (this.rightBranch == null ? "null" : this.rightBranch.toString()) + "}";
		}

		@Override
		public int compareTo(CharacterNode o) {

			return this.weight - o.weight;
		}
	}

}
