import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.*;

public final class CodingTree {
	
	public HashMap<Character, String> codes;
	
	public LinkedList<Byte> bits;

	public CodingTree(String message) {
		// Creates a priority queue with character elements sorted by frequency.
		// Frequency is updated 
		ArrayList<WeightedCharacter> frequencyList = new ArrayList<CodingTree.WeightedCharacter>();
		
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
		
		// Next step is to map binary data to each character by frequency.
		// frequencyList.sort(null);
		System.out.print(frequencyList);
		
		PriorityQueue<CharacterNode> charTree = new PriorityQueue<CodingTree.CharacterNode>();
		for(WeightedCharacter wc : frequencyList) {
			charTree.add(new CharacterNode(wc));
		}
	
		
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
		WeightedCharacter[] characters;
		int weight;
		CharacterNode leftBranch;
		CharacterNode rightBranch;
		
		public CharacterNode(WeightedCharacter...weightedCharacters) {
			this.characters = weightedCharacters;
			weight = 0;
			for (WeightedCharacter weightedCharacter : weightedCharacters) {
				weight += weightedCharacter.weight;
			}
			this.leftBranch = null;
			this.rightBranch = null;
		}
		
		public String toString() {
			return Arrays.toString(characters);
		}

		@Override
		public int compareTo(CharacterNode o) {

			return this.weight - o.weight;
		}
	}

}
