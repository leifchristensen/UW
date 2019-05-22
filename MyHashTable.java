import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MyHashTable<K, V> {

	int capacity = 32768;
	//ArrayList<KVPair> map;
	KVPair[] map;
	
	public MyHashTable(int capacity) {
		//https://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java
		// default capacity is 32768 = 2^15
		this.capacity = capacity;
		//map = new ArrayList<KVPair>(capacity);
		map = (KVPair[]) Array.newInstance(KVPair.class, capacity);
	}
	
	public void put(K searchKey, V newValue) {
		if(searchKey == null || newValue == null) {
			throw new NullPointerException();
		}
		int index = hash(searchKey);
		
		if (map[index]!=null)  {
			int checkRound = 0;
			int initialIndex = index;
			
			while(map[index] != null) {
				
				
				// Linear probing
				index = (index+1) % capacity;
				// Quadratic probing
				//index += (Math.pow(2, checkRound) % capacity);
				if(index == initialIndex) {
					break;
				}
			}
		}
		// At this point, the index is pointing to a null space in the array.
		map[index] = new KVPair(searchKey, newValue);
		
	}
	
	public V get(K searchKey) {
		return ((KVPair) map[hash(searchKey)]).getValue();
	}
	
	private int hash(K key) {
		//mod 15 selects first 15 bits.
		// in case of conflict, you can get another set of bits by dividing by 2^15 and rehashing the result.
		
		int index = System.identityHashCode(key) % capacity;
		return index;
	}
	
	private void stats() {
		//Gather Stats
		int entries = 0;
		int buckets = this.map.length;
		int[] probes = new int[this.map.length];
		for(int i = 0; i < this.map.length; i++) {
			if(!map[i].equals(null)) {
				entries++;
			}
			int hash = map[i].hashCode();
			if(i > hash) probes[i-hash]++;
			else probes[hash - i + this.map.length]++;
			
		}
		
		// For each distinct value, calculate the difference between the expected hash and the final hash.
		
		
		StringBuilder sb = new StringBuilder("Hash Table Stats");
		sb.append("\n================\n");
		
		sb.append("Number of Entries: " + entries);
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Map:");
		Arrays.stream(this.map).forEach(pair -> {
			if(pair != null) {
				sb.append("\t[" + pair + " | " + hash(pair.getKey()) + "]");
			}
		});
		return sb.toString();
	}
	
	// KVPair is the nodes of the map.
	class KVPair {
		private K key;
		private V value;
		
		KVPair(K newKey, V newValue) {
			this.key = Objects.requireNonNull(newKey);
			this.value = newValue;
		}
		
		K getKey() {
			return key;
		}
		
		V getValue() {
			return value;
		}
		
		@Override
		public String toString() {
			return this.getKey().toString() + ", " + this.getValue().toString();
		}
	}

}
