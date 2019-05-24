import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CodingTree {
	
	public final int DEFAULTCAPACITY = 32768;
	
	public MyHashTable<String, String> codes;
	public MyHashTable<String, Integer> frequency;
	public PriorityQueue<Node> freqTree;
	public String bits;
	public String[] words;

	public CodingTree(String fulltext) {
		codes = new MyHashTable<String, String>(DEFAULTCAPACITY);
		frequency = new MyHashTable<String, Integer>(DEFAULTCAPACITY);
		bits = new String();
		// "(?![\d|[a-z]|[A-Z]|])" regex for if the next char is not alphanumeric
		// Splits words if the next character is a non-word character or 
		//  if a word character is preceded by a  non-word character.
		words = fulltext.split("(?![\\d|[a-z]|[A-Z]|-|'])|(?<![\\d|[a-z]|[A-Z]|-|'])");
		
		// words is now an array of words and non-word characters.
		// Next step is to map each word / char to a frequency.
		for(String word : words) {
			try {
				frequency.put(word, (frequency.contains(word) ? frequency.get(word)+1 : 1));
			} catch (Exception e) {
				// Throws if in infinite loop.
				e.printStackTrace();
			}
		}
		
		// At this point, the frequencies of each word are mapped. 
		// Next task is to create the frequency tree
		freqTree = new PriorityQueue<Node>();
		for(MyHashTable<String, Integer>.KVPair pair : frequency.map) {
			if(pair != null) freqTree.add(new Node(pair.getKey(), pair.getValue()));
		}
		
		// beginning with the null character of the char tree, pop two nodes with the lowest weight,
		// meaning the top two nodes, and re-add to priority queue, with the new node having a left branch of the newly added node.
		
		while (freqTree.size() > 1) {
			Node rightBranch = freqTree.poll();
			Node leftBranch = freqTree.poll();
			freqTree.add(new Node(leftBranch, rightBranch));
		}
		
		// At this point, the queue contains a single node with branches with a left/right pattern of frequency.
		// Branches to the left are more common, branches to the right are less common.		
		// Next step is to traverse the tree and map to the shortest possible unique series of bytes. 
		
		if (!freqTree.isEmpty()) {
			traverseTree(freqTree.poll(), "");
		}
		
		// At this point, the map contains all characters in the message and their associated binary representation.		
		// next task is to encode the message as a string in binary.
				
		encode(fulltext);
	}
	
	// maps a set of unique string codes for every node in a tree.
	private void traverseTree(Node node, String startCode) {
		if (node.leftBranch == null && node.rightBranch == null) {
			if(node.word != null) { // Only attempt to map characters that exist.

				try {
					this.codes.put(node.word, startCode);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			traverseTree(node.leftBranch, startCode + "0");
			traverseTree(node.rightBranch, startCode + "1");
		}
	}
	
	// For each character in the message, look up the mapped binary string representation and append it to the bytes string.
	private void encode(String message) {
		Stream<String> binaryStream = Arrays.stream(words).map(o -> codes.get(o));		
		bits = binaryStream.collect(Collectors.joining());
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
