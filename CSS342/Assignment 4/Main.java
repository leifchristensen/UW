import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		try {
			//testMyHashTable();
			testCodingTree();
			File input = new File("WarAndPeace.txt");
			//compress(input);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void compress(File inputFile) throws Exception {
		try {
			String s = new String(Files.readAllBytes(inputFile.toPath()));
			CodingTree encode0 = new CodingTree(s);
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println(e.getStackTrace());
		}
	}
	
	public static void testCodingTree() {
		CodingTree tree0 = new CodingTree("TEST TEST Message");
		System.out.println("Words\t" + Arrays.deepToString(tree0.words));
		System.out.println(tree0.frequency);
		System.out.println(tree0.codes);
		System.out.println(tree0.bits);
	}
	
	public static void testMyHashTable() throws Exception {
		
		// Put a single value into a table
		System.out.println(">---" + "Single Value" + "---<");
		MyHashTable<String, String> test0 = new MyHashTable<String, String>(4);
		test0.put("Key0", "Val0");
		System.out.println(test0.toString());
		test0.stats();
		System.out.println();
		
		// Test Get and hash
		System.out.println(">---" + "Get value" + "---<");
		System.out.println(test0.get("Key0"));
		System.out.println();	
		
		// Put values in table with same hash value 
		System.out.println(">---" + "Same Hash" + "---<");
		MyHashTable<String, String> test1 = new MyHashTable<String, String>(4);
		test1.put("Key0", "Val0");
		test1.put("Key1", "Val1");
		System.out.println(test1.toString());
		test1.stats();
		System.out.println();
		
		
		
		// Put values in table with same hash value but over capacity 
		System.out.println(">---" + "# Puts = Initial Capacity" + "---<");
		MyHashTable<String, String> test2 = new MyHashTable<String, String>(4);
		test2.put("Key0", "Val0");
		test2.put("Key1", "Val1");
		test2.put("Key2", "Val2");
		test2.put("Key3", "Val3");
		System.out.println(test2.toString());
		test2.stats();
		System.out.println();
		
		// Puts a new value on an existing key
		System.out.println(">---" + "Update Value" + "---<");
		test2.put("Key0", "NEWVALUE");
		System.out.println(test2.toString());
		System.out.println();
		
		// Contains
		System.out.println(">---" + "Contains" + "---<");
		System.out.println(test2.toString());
		System.out.println("Contains Key1: " + test2.contains("Key2"));
		System.out.println("Contains Key7: " + test2.contains("Key7"));
		System.out.println();
		
		// Puts another value where # values > capacity
		System.out.println(">---" + "# Puts > Initial Capacity" + "---<");
		System.out.println("Should Throw Loop Exception");
		try {
			MyHashTable<String, String> test3 = new MyHashTable<String, String>(4);
			test3.put("Key0", "Val0");
			test3.put("Key1", "Val1");
			test3.put("Key2", "Val2");
			test3.put("Key3", "Val3");
			test3.put("Key4", "Val4");
			System.out.println(test3.toString());
			test3.stats();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
		
		
		
	}

	
	
}
