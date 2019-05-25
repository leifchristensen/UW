import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.UUID;
import java.util.PrimitiveIterator.OfInt;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CodingTree {
	
	public final int DEFAULTCAPACITY = 32768;
	
	private String text;
	public MyHashTable<String, String> codes;
	
	public HashMap<String, Integer> frequency;
	
	//public MyHashTable<String, Integer> frequency;
	public PriorityQueue<Node> freqTree;
	public String bits;
	public String[] words;

	public CodingTree(String fulltext) {
		codes = new MyHashTable<String, String>(DEFAULTCAPACITY);
		//frequency = new MyHashTable<String, Integer>(DEFAULTCAPACITY);
		frequency = new HashMap<String, Integer>(DEFAULTCAPACITY);
		bits = new String();
		text = fulltext;
		// Splits words if the next character is a non-word character or 
		//  if a word character is preceded by a  non-word character.
		words = fulltext.split("(?![\\d|[a-z]|[A-Z]|-|'])|(?<![\\d|[a-z]|[A-Z]|-|'])");
		// words is now an array of words and non-word characters.
		// Next step is to map each word / char to a frequency.
		for(String word : words) {
			try {
				frequency.put(word, frequency.getOrDefault(word, 0)+1);
			} catch (Exception e) {
				// Throws if in infinite loop.
				System.out.println(word);
				System.out.println(frequency.get(word));
				e.printStackTrace();
			}
		}
		
		// At this point, the frequencies of each word are mapped. 
		// Next task is to create the frequency tree
		freqTree = new PriorityQueue<Node>();
		for(Entry<String, Integer> pair : frequency.entrySet()) {
			freqTree.add(new Node(pair.getKey(), pair.getValue()));
		}
		//System.out.println(freqTree);
		
		// beginning with the null character of the char tree, pop two nodes with the lowest weight,
		// meaning the top two nodes, and re-add to priority queue, with the new node having a left branch of the newly added node.
		
		while (freqTree.size() > 1) {
			Node rightBranch = freqTree.poll();
			Node leftBranch = freqTree.poll();						
			freqTree.add(new Node(leftBranch, rightBranch));
		}
		//System.out.println(freqTree);
		// At this point, the queue contains a single node with branches with a left/right pattern of frequency.
		// Branches to the left are more common, branches to the right are less common.		
		// Next step is to traverse the tree and map to the shortest possible unique series of bytes. 
		
		if (!freqTree.isEmpty()) {
			traverseTree(freqTree.poll(), "");
		}
		
		// At this point, the map contains all characters in the message and their associated binary representation.		
		// next task is to encode the message as a string in binary.
				
		encode();
		writeFile();
	}
	
	// maps a set of unique string codes for every node in a tree.
	private void traverseTree(Node node, String startCode) {
		if(node.word != null) { // Only attempt to map characters that exist.

			try {
				this.codes.put(node.word, startCode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {	
			
			traverseTree(node.leftBranch, startCode + "0");
			traverseTree(node.rightBranch, startCode + "1");
		}
	}
	
	// For each character in the message, look up the mapped binary string representation and append it to the bytes string.
	private void encode() {		
		Stream<String> binaryStream = Arrays.stream(words).map(o -> codes.get(o));		
		bits = binaryStream.collect(Collectors.joining());
	}
	
	// Creates a string file writer and a byte file writer
	private void writeFile() {
		
		// Text output f codes
		// https://stackoverflow.com/questions/6981555/how-to-output-binary-data-to-a-file-in-java
		try {
			// txt file output.
			
			FileWriter fileOut = new FileWriter("codes-" + UUID.randomUUID() + ".txt");
			fileOut.write(this.codes.toString());
			fileOut.close();
			
			FileWriter fileOut2 = new FileWriter("frequency-" + UUID.randomUUID() + ".txt");
			fileOut2.write(this.frequency.toString());
			fileOut2.close();
			// System.out.println("--String File Written");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
			
		try {
			// Binary output
			File dataFile = new File("data-" + UUID.randomUUID() + ".txt");
			FileOutputStream fs = new FileOutputStream(dataFile, true);
			ByteArrayOutputStream byteData = parseBytes(bits);
			fs.write(byteData.toByteArray());
			
			fs.close();
			System.out.printf("Initial Size: %,d > Final Size: %,dkb", text.length()/1024, dataFile.length()/1024);
			System.out.println();
			System.out.printf("Compression Ratio: %d%%", dataFile.length()*100/text.length());
			System.out.println();
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
	
	
	// Nodes in the frequency tree.
	class Node implements Comparable<Node>{
		String word;
		int weight;
		Node leftBranch;
		Node rightBranch;
		
		// Leaf node
		public Node(String word, int weight) {
			this.word = word;
			this.weight = weight;
			this.leftBranch = null;
			this.rightBranch = null;
		}
		
		// Parent node
		public Node(Node left, Node right) {
			weight = left.weight + right.weight;
			this.leftBranch = left;
			this.rightBranch = right;
		}
		
		public String toString() {
			if (leftBranch == null && rightBranch == null) return this.word.toString();
			return "{" + (this.leftBranch == null ? "null" : this.leftBranch.toString()) + " | " + (this.rightBranch == null ? "null" : this.rightBranch.toString()) + "}";
		}

		@Override
		public int compareTo(Node o) {
			return this.weight - o.weight;
		}
	}

	
	
}
