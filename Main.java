
public class Main {

	public static void main(String[] args) {
		try {
			testMyHashTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void testMyHashTable() throws Exception {
		final int capacity = 32768;
		
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
		try {
			MyHashTable<String, String> test2 = new MyHashTable<String, String>(4);
			test2.put("Key0", "Val0");
			test2.put("Key1", "Val1");
			test2.put("Key2", "Val2");
			test2.put("Key3", "Val3");
			System.out.println(test2.toString());
			test2.stats();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
		
		// Puts another value where # values > capacity
		System.out.println(">---" + "# Puts > Initial Capacity" + "---<");
		try {
			MyHashTable<String, String> test2 = new MyHashTable<String, String>(4);
			test2.put("Key0", "Val0");
			test2.put("Key1", "Val1");
			test2.put("Key2", "Val2");
			test2.put("Key3", "Val3");
			test2.put("Key4", "Val4");
			System.out.println(test2.toString());
			test2.stats();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
