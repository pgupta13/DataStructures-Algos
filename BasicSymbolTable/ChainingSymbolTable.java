//package PA9;

/**
 * BasicSymbolTable implementation using hash table with separate chaining.
 * @param <Key>
 * @param <Value>
 */

/**
 * @author Prachi Gupta G01025257 user: pgupta13 INFS 519 Fall 2016
 * INFS 519
 * Fall 2016
 */

public class ChainingSymbolTable<Key, Value> implements BasicSymbolTable<Key, Value> {
	public static final int REHASH_MIN_THRESHOLD = 2;
	public static final int REHASH_MAX_THRESHOLD = 8;
	public static final int INITIAL_M = 4;

	private EntryList<Key, Value>[] items;
	private int size;

	// Constructor object with default capacity of array
	public ChainingSymbolTable() {
		this(INITIAL_M);
	}

	// Constructor object with user specified array capacity
	public ChainingSymbolTable(int initialM) {
		// for each index of array, create an object of type Entry List which is
		// a linked list
		items = (EntryList<Key, Value>[]) new EntryList[initialM];
		for (int i = 0; i < items.length; i++) {
			items[i] = new EntryList<Key, Value>();
		}
	}

	/**
	 * Gets the number of elements currently in the queue
	 * 
	 * @return size
	 */
	public int size() {
		return size;
	}

	/**
	 * Determines if there are not elements in the queue.
	 * 
	 * @return true is no elements, false otherwise
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Inserts the value into the table using specified key. Overwrites any
	 * previous value with specified value.
	 * 
	 * @param key
	 * @param value
	 * @throws NullPointerException
	 *             if the key or value is null
	 */
	public void put(Key key, Value value) {

		// check for key/value null
		if (key == null || value == null) {
			throw new NullPointerException("Key/Value sent is null");
		}
		// calculate hashIndex for a given key
		int hashIndex = hashFunction(key);
		// double table size if average length of list > REHASH_MAX_THRESHOLD *
		// array capacity
		if (size > REHASH_MAX_THRESHOLD * items.length) {
			rehash(2 * items.length);
		}
		// inserting key value pair at the hashindex using put method of linked
		// list
		items[hashIndex].put(key, value);
		size++;

	}

	/**
	 * Finds Value for the given Key.
	 * 
	 * @param key
	 * @return value that key maps to or null if not found
	 * @throws NullPointerException
	 *             if the key is null
	 */
	public Value get(Key key) {
		// check for key/value null
		if (key == null)
			throw new NullPointerException("Key sent is null");
		// chal hashIndex for the given key
		int hashIndex = hashFunction(key);
		// fetch value for the given hashIndex and the leading EntryList using
		// get(key) of linked list
		Value value = items[hashIndex].get(key);
		return value; // MODIFY CODE
	}

	/**
	 * Removes the Value for the given Key from the table.
	 * 
	 * @param key
	 * @return value that was removed or null if not found
	 * @throws NullPointerException
	 *             if the key is null
	 */
	public Value delete(Key key) {
		// check for key/value null
		if (key == null)
			throw new NullPointerException("Key sent is null");

		// calculate hashIndex for the given key and fetch its value
		int hashIndex = hashFunction(key);
		Value value = get(key);
		// check if the key is present in the EntryList specifies by the
		// hashIndex
		if (items[hashIndex].contains(key) == false) {
			return null;
		} else {
			// delete key, value pair using delete method of EntryList and
			// decrease size
			items[hashIndex].delete(key);
			size--;
		}
		// half the size of table if size of table < 2 * length of items array,
		// also it should be greater than Min threshold capacity of 2
		if (items.length > REHASH_MIN_THRESHOLD && size <= 2 * items.length)
			rehash(items.length / 2);
		return value;

	}

	/**
	 * Iterable that enumerates each key in the table.
	 * 
	 * @return iterable over the keys
	 */
	public Iterable<Key> keys() {
		//iterator for keys of all keys in the table, returns a queue of keys
		Queue<Key> queue = new Queue<Key>();
		for (int i = 0; i < items.length; i++) {
			for (Key key : items[i].keys())
				queue.enqueue(key);
		}
		return queue;

	}

	/**
	 * Creates a new table (and thus new hash function), inserts previous items
	 * using new hash function into the new table, sets new table.
	 * 
	 * @param newM
	 */
	private void rehash(int newM) {
		//create a new table of new size passed as argument
		ChainingSymbolTable<Key, Value> temp = new ChainingSymbolTable<Key, Value>(newM);
		//reinsert all the values in the new table using get and put methods
		for (int i = 0; i < items.length; i++) {
			for (Key key : items[i].keys()) {
				Value value1 = items[i].get(key);
				temp.put(key, value1);
			}

		}
		
		//make new table the current table of class object
		this.size = temp.size;
		this.items = temp.items;

	}

	/**
	 * Determines an index in [0,M-1] using specified key to generate hash code.
	 * 
	 * @param key
	 *            with properly implemented hash code
	 * @param M
	 * @return index in [0,M-1]
	 */
	private int hashFunction(Key key) {
		
		//returns the hashIndex for a given key using hashcode function
		return (key.hashCode() & 0x7fffffff) % items.length; // MODIFY CODE
	}

	// ------------------ DO NOT MODIFY BELOW THIS LINE -------------------//

	/**
	 * The expected length of the linked lists, i.e. the load factor.
	 * 
	 * @return N / M
	 */
	public double loadFactor() {
		return this.getN() / (double) this.getM();
	}

	// Utility method
	public int getN() {
		return this.size;
	}

	// Utility method
	public int getM() {
		return this.items.length;
	}

	@Override
	public String toString() {
		// Uses the iterator to build String
		StringBuilder buf = new StringBuilder();
		boolean first = true;
		buf.append("[");
		for (Key key : this.keys()) {
			Value item = this.get(key);

			if (first)
				first = false;
			else
				buf.append(", ");

			buf.append("\n");

			buf.append(key);

			buf.append("->");
			buf.append(item.toString());
		}
		buf.append("]");
		return buf.toString();
	}

	public String toTableString(boolean verbose) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < this.items.length; i++) {
			String prefix = "\n[" + i + "] size=" + this.items[i].size() + ":";
			buf.append(prefix);
			if (verbose) {
				boolean first = true;
				for (Key key : this.items[i].keys()) {
					if (first)
						first = false;
					else
						buf.append(", ");

					buf.append("(");
					buf.append(key);
					buf.append(")");
				}
			}
		}
		return buf.toString();
	}

	/**
	 * Unit tests the ST data type.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ChainingSymbolTable<ProductID, Product> st = new ChainingSymbolTable<ProductID, Product>();

		if (Stdio.isInteger(args[0])) {
			int n = Integer.parseInt(args[0]);
			java.util.Random rand = new java.util.Random();
			Clock time = new Clock();
			for (int i = 0; i < n; i++) {
				String university = "GMU" + rand.nextInt();
				int identifer = rand.nextInt();
				String name = "[" + university + "," + identifer + "]";
				int age = 0;
				double grade = 0.0;

				ProductID key = new ProductID(university, identifer);
				Product value = new Product(key, name, age, grade);

				st.put(key, value);

				if (st.get(key) == null)
					throw new IllegalStateException("Put failed: " + key);
				if (i % 100000 == 0)
					Stdio.println("Put " + i);
			}
			Stdio.println("Put " + n + " items took " + time);
			Stdio.println("Final symbol table=" + st.toTableString(false));
			Stdio.printf("Load factor=%.2f\n", st.loadFactor());
		} else {
			Stdio.open(args[0]);
			while (Stdio.hasNext()) {
				String method = Stdio.readString();
				if (method.equalsIgnoreCase("put")) {
					String epc = Stdio.readString();
					int serialNumber = Stdio.readInt();
					String description = Stdio.readString();
					int quantity = Stdio.readInt();
					double cost = Stdio.readDouble();

					ProductID key = new ProductID(epc, serialNumber);
					Product value = new Product(key, description, quantity, cost);

					st.put(key, value);
					// Stdio.println( "put="+key.toString() );
				} else if (method.equalsIgnoreCase("delete")) {
					String epc = Stdio.readString();
					int serialNumber = Stdio.readInt();
					ProductID key = new ProductID(epc, serialNumber);

					Product deletedValue = st.delete(key);
					// Stdio.println( "deleted="+deletedValue );
				} else if (method.equalsIgnoreCase("get")) {
					String epc = Stdio.readString();
					int serialNumber = Stdio.readInt();
					ProductID key = new ProductID(epc, serialNumber);

					Product value = st.get(key);
					Stdio.println("get=" + value);
				} else if (method.equalsIgnoreCase("size")) {
					Stdio.println("size=" + st.size());
				} else if (method.equalsIgnoreCase("isEmpty")) {
					Stdio.println("isEmpty?=" + st.isEmpty());
				} else if (method.equalsIgnoreCase("toString")) {
					Stdio.println("toString?=" + st.toTableString(true));
				}
			}
			Stdio.println("Final mappings=" + st.toString());
			Stdio.println("Final symbol table=" + st.toTableString(true));
			Stdio.printf("Load factor=%.2f\n", st.loadFactor());
			Stdio.close();
		}
	}
}
