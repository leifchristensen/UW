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
	
	public void put(K searchKey, V newValue) throws Exception {
		if(searchKey == null || newValue == null) {
			throw new NullPointerException();
		}
		int index = hash(searchKey);
		
		if (map[index]!=null)  {
			int checkRound = 0;
			int initialIndex = index;
			
			// Return the index of the matching key, or if it doesn't exist, the first empty bucket where it can be placed.
			while(map[index] != null && !map[index].getKey().equals(searchKey)) {
				
				
				// Linear probing
				index = (index+1) % capacity;
				// Quadratic probing
				//index += (Math.pow(2, checkRound) % capacity);
				if(index == initialIndex) {
					throw new Exception("Loop");
				}
			}
		}
		// At this point, the index is pointing to a null space in the array.
		map[index] = new KVPair(searchKey, newValue);
		
	}
	
	public V get(K searchKey) {
		return ((KVPair) map[hash(searchKey)]).getValue();
	}
	
	public boolean contains(K searchKey) {
		int initial = hash(searchKey);
		
		
		for(int i = initial; this.map[i] != null; i++) {
			if(this.map[i].getKey().equals(searchKey)) return true;
		}
		return false;
	}
	
	private int hash(K key) {
		//mod 15 selects first 15 bits.
		// in case of conflict, you can get another set of bits by dividing by 2^15 and rehashing the result.
		
		int index = System.identityHashCode(key) % capacity;
		return index;
	}
	
	public void stats() {
		//Gather Stats
		int entries = 0;
		int buckets = this.map.length;
		int[] probes = new int[this.map.length];
		int maxProbes = 0;
		double avg = 0;
		
		for(int i = 0; i < this.map.length; i++) {
			if(map[i]!=null) {
				entries++;
				int hash = System.identityHashCode(map[i].key) % capacity;
				if(i >= hash) {
					if (maxProbes < (i-hash)%buckets) maxProbes = (i-hash)%buckets;
					probes[(i-hash)%buckets]++;
				}
				else {
					if (maxProbes < (hash-i+buckets)%buckets) maxProbes = (hash-i+buckets)%buckets;
					probes[(hash-i+buckets)%buckets]++;
				}
			}			
		}
		
		for(int i = 0; i < probes.length; i++) {
			avg += (i+1) * probes[i];
		}
		avg = avg / entries;
		
		// For each distinct value, calculate the difference between the expected hash and the final hash.
		
		
		System.out.println("Hash Table Stats");
		System.out.println("================");		
		System.out.println("Number of Entries: " + entries);
		System.out.println("Number of Buckets: " + buckets);
		System.out.println(Arrays.toString(probes));
		System.out.printf("Bucket Fill %%: %d", Math.floorDiv(entries*100, buckets));
		System.out.println();
		System.out.printf("Max # Probes: %d", maxProbes);
		System.out.println();
		System.out.printf("Avg Probes: %f", avg);
		System.out.println();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Map:");
		for(int i = 0; i < this.map.length; i++) {
			KVPair pair = this.map[i];
			if(pair != null) {
				sb.append("\t" + i + "[" + pair + " | " + hash(pair.getKey()) + "]");
			}
		}
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
