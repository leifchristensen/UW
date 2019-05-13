import java.util.ArrayList;

public class MyHashTable<K, V> {

	int capacity;
	ArrayList<K> keys;
	ArrayList<V> values;
	
	public MyHashTable(int capacity) {
		// default capacity is 32768 = 2^15
		capacity = 32768;
		// TODO: initialize hash table.
		values = new ArrayList<V>();
	}
	
	public void put(K searchKey, V newValue) {
		if(searchKey == null || newValue == null) {
			throw new NullPointerException();
		}
		int index = hash(searchKey);
		keys.set(index, searchKey);
		values.set(index, newValue);
	}
	
	public V get(K searchKey) {
		return values.get(hash(searchKey));
	}
	
	private int hash(K key) {
		int index = System.identityHashCode(key) % capacity;
		// Return value of index of key if a key exists, or the first empty index where the key would go if the key does not exist.
		while(!keys.get(index).equals(key) && keys.get(index) != null) {
			// Breaks loop if the index is equal to an existing key or if there is a null key in an index before the key is found.
			index = (index+1) % capacity;
		}
		// TODO:  takes a key and returns an int in the range [0...capacity]
		return index;
	}

}
