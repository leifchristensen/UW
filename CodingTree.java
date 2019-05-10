/*
 * Leif Christensen
 * Assignment 3
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
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
		
		// streams message as chars.
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
		// At this point, the frequency list should have all characters in the message with their frequency.		
		// Next step is to create a frequency tree.
		
		PriorityQueue<CharacterNode> charTree = new PriorityQueue<CodingTree.CharacterNode>();
		for(WeightedCharacter wc : frequencyList) {
			charTree.add(new CharacterNode(wc));
		}
				
		// beginning with the null character of the char tree, pop two nodes with the lowest weight,
		// meaning the top two nodes, and re-add to priority queue, with the new node having a left branch of the newly added node.
		
		// System.out.println("--Begin Create Tree ");
		while (charTree.size() > 1) {
			CharacterNode rightBranch = charTree.poll();
			CharacterNode leftBranch = charTree.poll();
			charTree.add(new CharacterNode(leftBranch, rightBranch));
		}
		// System.out.println("--End Create Tree");
		
		// At this point, the queue contains a single node with branches with a left/right pattern of frequency.
		// Branches to the left are more common, branches to the right are less common.		
		// Next step is to traverse the tree and map to the shortest possible unique series of bytes. 
		
		// System.out.println("--Begin Create Map");
		// Only attempt to map nodes if nodes exist.
		if (!charTree.isEmpty()) {
			traverseTree(charTree.poll(), "");
		}
		// System.out.println("--End Create Map");
		// System.out.println("CODES: " + this.codes.toString());
		
		// At this point, the map contains all characters in the message and their associated binary representation.		
		// next task is to encode the message as a string in binary.
		
		// System.out.println("--Begin Encode");
		encode(message);
		// System.out.println("--End Encode Map");
		
		// At this point, the message is encoded in binary.		
		// Next task is to write the binary message to files.
		
		writeFile();
		
		// System.out.println("--Finished!");
	}
	
	// maps a set of unique string codes for every node in a tree.
	private void traverseTree(CharacterNode node, String startCode) {
		if (node.leftBranch == null && node.rightBranch == null) {
			if(node.weightedCharacter.character != null) { // Only attempt to map characters that exist.

				this.codes.put(node.weightedCharacter.character, startCode);
			}
		} else {
			traverseTree(node.leftBranch, startCode + "0");
			traverseTree(node.rightBranch, startCode + "1");
		}
	}
	
	
	
	

	// Creates a string file writer and a byte file writer
	private void writeFile() {
		System.out.println("--Begin Write File");
		// Text output f codes
		// https://stackoverflow.com/questions/6981555/how-to-output-binary-data-to-a-file-in-java
		try {
			// txt file output.
			
			FileWriter fileOut = new FileWriter("codes-" + UUID.randomUUID() + ".txt");
			fileOut.write(this.codes.toString());
			fileOut.close();
			// System.out.println("--String File Written");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
			
		try {
			// Binary output
			File dataFile = new File("data-" + UUID.randomUUID() + ".txt");
			FileOutputStream fs = new FileOutputStream(dataFile, true);
			ByteArrayOutputStream byteData = parseBytes(bits);
			
			// Writes the byte data to the data file.
			fs.write(byteData.toByteArray());
			
			fs.close();
			System.out.println(">    Final Size: \t" + dataFile.length());
			System.out.println("--Data File Written");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	// parses bytes from a string of binary data. 
	// string is read 8 characters at a time, then a bytes is created from the chunk of the string until the entire string is consumed.
	private ByteArrayOutputStream parseBytes(String binaryString) {
		OfInt charStream = binaryString.chars().iterator();
		LinkedList<String> chunks = new LinkedList<String>();
				
		// parse the string for chunks of 8 characters.
		while(charStream.hasNext()) {
			// initial byte must equal zero to prevent overflow.
			String chunk = "";
			int leadBit = charStream.next();
			// if leading bit is 1, byte will overflow. 
			// Change subsequent bits to inverses and 
			boolean isOverflow = (leadBit == '1');
			if(isOverflow) chunk = "-";
			chunk = chunk + parseBit(leadBit, isOverflow);
			
			for (int i = 1; i < Byte.SIZE; i++) {
				
				if(charStream.hasNext()) {
					chunk = chunk + parseBit(charStream.next(), isOverflow);
				}
				// if the end of the file has been reached, append zeros.
				else {
					chunk = chunk + 0;
				}
			}
			chunks.add(chunk);
		}
		
		Stream<String> stringChunks = chunks.stream();
		
		// At this point, the chunks should be filled with strings each containing the most compressed version of the binary string. 
		// each chunk represents a byte.
		
		// next task is to parse the list of chunks into bytes and add the bytes to the return stream.		
		ByteArrayOutputStream byteChunks = new ByteArrayOutputStream();
		stringChunks.map(o -> Byte.parseByte(o, 2)).forEach(byteChunks::write);;		
		
		
		
		// At this point, the stream is full of the most compressed possible bytes with the last byte appended with zeros if needed.
		return byteChunks;
	}
	
	// Ensures that all characters are binary.
	// Gets two's compliments of a binary string if invert is true.
	private char parseBit(int toParse, boolean invert) {
		if (toParse != '0' && toParse != '1' ) {
			throw new IllegalArgumentException("non-binary Character: " + toParse);
		}
		
		return (char) (invert ? (toParse == '1' ? '0' : '1') : toParse);
	}
	
	// For each character in the message, look up the mapped binary string representation and append it to the bytes string.
	private void encode(String message) {
		// https://www.techiedelight.com/convert-intstream-string-vice-versa/
		IntStream messageStream = message.chars();
		Stream<String> binaryStream = messageStream.mapToObj(o -> codes.get((char) o));
		
		bits = binaryStream.collect(Collectors.joining());
	}
	
	// A character in the frequency tree.
	private class WeightedCharacter implements Comparable<WeightedCharacter> {
		
		public Character character;
		public int weight;
		
		public WeightedCharacter(Integer inputChar) {
			if (inputChar == null) {
				// null chars are valid, as this may be used to fill space in end of char strings.
				this.character = null;
			}
			else {				
				this.character = (char) inputChar.intValue(); 
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
			// Ordered by frequency/weight
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
		
		// Leaf node
		public CharacterNode(WeightedCharacter weightedCharacter) {
			this.weightedCharacter = weightedCharacter;
			weight =  weightedCharacter.weight;
			this.leftBranch = null;
			this.rightBranch = null;
		}
		
		// Parent node
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
