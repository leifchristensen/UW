import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.*;

public final class CodingTree {
	
	public HashMap<Character, String> codes;
	
	public LinkedList<Byte> bits;

	public CodingTree(String message) {
		// Creates a priority queue with character elements sorted by frequency.
		// Frequency is updated 
		ArrayList<WeightedCharacter> frequencyList = new ArrayList<CodingTree.WeightedCharacter>();
		codes = new HashMap<Character, String>();
		bits = new LinkedList<Byte>();
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
		System.out.println(frequencyList);
		
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
		
		System.out.println(charTree);
		
		// At this point, the queue contains a single node with branches with a left/right pattern of frequency.
		// Branches to the left are more common, branches to the right are less common.
		// Next step is to traverse the tree and map to the shortest possible unique series of bytes. 
		
		traverseTree(charTree.poll(), "");
		
		System.out.println(this.codes.toString());
		
		encode(message);
		
		writeFile();
		
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
		Stream<Character> messageStream = message.chars().mapToObj(o -> new Character((char) o));
		messageStream.forEach(o -> {
			this.codes.get(o).chars().forEach(code -> {
				// for each character in the message, get the corresponding code and add the code to the bit list.
				if(code == '0') {
					this.bits.add((byte) 0);
				} else if (code == '1') {
					this.bits.add((byte) 1);
				} else {
					throw new IllegalArgumentException("Unrecognized character: " + code);
				}
			});
			
		});
	}
	
	private void writeFile() {
		// Creates a string builder and a bitwise file writer
		StringBuilder sb = new StringBuilder();
		try {
			FileOutputStream fs = new FileOutputStream("data.dat", true);
			for(byte b : this.bits) {
				// Appends bits to the string builder
				sb.append(decode(this.codes,b));
				
				// Appends bits to the bitwise file.
				
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		try {
			PrintWriter stringOut = new PrintWriter("data.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Character decode(Map<Character, String> encodedMap, byte encodedByte) {
		
		
		encodedMap.keySet().forEach(key -> {
			if (encodedMap.get(key).equals(encodedByteString)) {
				return;
			}
		});
		return null;
	}
	
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
