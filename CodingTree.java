import java.util.Scanner;

public class CodingTree {
	
	public final int DEFAULTCAPACITY = 32768;
	
	public MyHashTable<String, String> codes;	
	public String bits;
	public String[] words;

	public CodingTree(String fulltext) {
		codes = new MyHashTable<String, String>(DEFAULTCAPACITY);
		bits = new String();
		// "(?![\d|[a-z]|[A-Z]|])"
		words = fulltext.split("(?![\\d|[a-z]|[A-Z]|-|'])");
		words.toString();
	}

}
