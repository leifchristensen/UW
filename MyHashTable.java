import java.util.ArrayList;

public class MyHashTable<K, V> {

	int capacity;
	ArrayList<V> values;
	
	public MyHashTable(int capacity) {
		// default capacity is 32768 = 2^15
		capacity = 32768;
		// TODO: initialize hash table.
		values = new ArrayList<V>();
	}
	
	public void put(K searchKey, V newValue) {
		if(searchKey == null || V == null) {
			throw new NullPointerException();
		}
		
		
		
		values.set(hash(searchKey), V)
	}
	
	public V get(K searchKey) {
		return values.get(hash(searchKey));
	}
	
	private int hash(K key) {
		int index;
		while(values.get(index = System.identityHashCode(key) % capacity) == null) {
			
		}
		// TODO:  takes a key and returns an int in the range [0...capacity]
		return index;
	}

}
