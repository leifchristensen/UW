import java.util.ArrayList;
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
				frequencyList.add(o);
			}
		});
		
		// At this point, the frequency list should have all characters in the message with their frequency.
		
		// Next step is to map binary data to each character by frequency.
		
		
	}
	
	private class WeightedCharacter implements Comparable<WeightedCharacter> {
		
		// Char data kept as int. Decode will need to convert this back to a char.
		public int character;
		public int weight;
		
		public WeightedCharacter(int inputChar) {
			this.character = Objects.requireNonNull(inputChar);
			this.weight = 0;
		}

		@Override
		public int compareTo(WeightedCharacter o) {
			return this.weight - o.weight;
		}
		
	}

}
