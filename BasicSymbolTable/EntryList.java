//package PA9;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * SymbolTable implementation that holds key/value pairs in a singly linked list.  
 * @param <Key>
 * @param <Value>
 */

/**
 * @author Prachi Gupta G01025257 user: pgupta13 INFS 519 Fall 2016 INFS 519
 *         Fall 2016
 */

public class EntryList<Key, Value> implements BasicSymbolTable<Key, Value> {

	// Create a Entry class as node of linked list EntryList
	private class Entry {
		Key key;
		Value value;
		public Entry next;
	}

	// private varibles required in Entry list
	private Entry head;
	private int size;

	// Constructor for class
	public EntryList() {
		// ADD CODE
		head = null;
		size = 0;
	}

	/**
	 * Gets the number of elements currently in this symbol table
	 * 
	 * @return size
	 */
	public int size() {
		return size;
	}

	/**
	 * Determines if there are not elements in this symbol table.
	 * 
	 * @return true if no elements, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Inserts the value into this symbol table using specified key. Overwrites
	 * any previous value with specified value.
	 * 
	 * @param key
	 * @param value
	 * @throws NullPointerException
	 *             if the key or value is null
	 */
	public void put(Key key, Value value) {
		// check for key/value null
		if (key == null || value == null) {
			throw new NullPointerException("Given key/value is null");
		}
		// if the list is empty, assign head and next to head value and increase
		// size
		if (isEmpty()) {
			this.head = new Entry();
			this.head.key = key;
			this.head.value = value;
			this.head.next = null;
			size++;
		} else {
			// check for already present key, overwrite if key already present
			if (contains(key)) {// firsy head for head value
				Entry x = this.head;
				if (key.equals(x.key)) {
					x.value = value;
				}
				// else check for next values
				else {
					while (x.next != null) {
						x = x.next;
						if (key.equals(x.key)) {
							x.value = value;
						}
					}
				}
			} else {

				// else create a new Entry and make it head and assign previous
				// head as next entry of current head
				Entry temp = this.head;
				Entry newEntry = new Entry();
				newEntry.key = key;
				newEntry.value = value;
				newEntry.next = this.head;
				this.head = newEntry;
				// increase size
				size++;
			}
		}

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
		// check for the key sequentially through the EntryList starting with
		// head
		for (Entry x = this.head; x != null; x = x.next) {
			if (key.equals(x.key))
				return x.value;
		}
		return null;
	}

	// Checks if the key is present in a Entry List, uses method get(key) to
	// find and return boolean value
	public boolean contains(Key key) {
		if (key == null)
			throw new NullPointerException("argument to contains() is null");
		return get(key) != null;
	}

	/**
	 * Removes the Value for the given Key from this symbol table.
	 * 
	 * @param key
	 * @return value that was removed or null if not found
	 * @throws NullPointerException
	 *             if the key is null
	 */

	public Value delete(Key key) {
		// calls private funciton delete() with head and key to be deleted
		head = delete(head, key);
		return head.value;
	}

	// deleted given key recursivey searching the value in EntryList starting
	// with head node
	private Entry delete(Entry x, Key key) {
		// if key null, return
		if (x == null)
			return null;
		// if key matched a key in Entry list decrement size and return the node
		if (key.equals(x.key)) {
			size--;
			return x.next;
		}
		// recursive call to next node of EntryList
		x.next = delete(x.next, key);
		return x;
	}

	/**
	 * Iterable that enumerates each key in this symbol table.
	 * 
	 * @return iter
	 */
	public Iterable<Key> keys() {
		// uses queue datastructure to enumerate the keys sequentially through
		// each index then through the linked list
		Queue<Key> queue = new Queue<Key>();
		for (Entry x = head; x != null; x = x.next)
			queue.enqueue(x.key);
		return queue;
	}

	// ------------------ DO NOT MODIFY BELOW THIS LINE -------------------//

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

			buf.append(key);

			buf.append("->");
			buf.append(item.toString());
		}
		buf.append("]");
		return buf.toString();
	}

}