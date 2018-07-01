package PA3;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Prachi Gupta 
 * G01025257
 * username : pgupta13
 * INFS 519
 * Fall 2016
 */

/**
 * Queue.java class uses the concept of Linked Lists to return and manipulate
 * its first and last nodes for addition(Enqueue) and removal(dequeue) of nodes
 * 
 * implements already defined QueueAPI interface and Iterator methods
 * 
 * It has two inner classes used widely in the class namely ListNode and
 * QueueIterator
 * 
 * It has functions like iterator peek() which returns top item and boolean
 * function isEmpty() for checking if the queue is empty. Maintains variables
 * like size which is maintained all over the program to check the size of the
 * queue
 */
public class Queue<Type> implements QueueAPI<Type> {
	// Add Node inner class here
	private int modCount;
	private int size;
	private ListNode head;
	private ListNode tail;

	/**
	 * ListNode class is analogous to the Node class of a Linked List It has
	 * variables like Type type data and ListNode type next which is used to set
	 * the link to next element in the linked list
	 * 
	 * the constructor when passed with a new item sets up a new node with that
	 * data
	 */
	private class ListNode {
		private Type data;
		private ListNode next = null;

		public ListNode(Type data) {
			this.data = data;
		}

		public Type setData(Type Data) {
			return data;
		}

		public Type getData() {
			return data;
		}

		public void setNext(ListNode next) {
			this.next = next;
		}

		public ListNode getNext() {
			return this.next;
		}
	}

	/**
	 * This is an iterator specifically for the Queue, it implements
	 * Iterator<Type> Uses variables listNode type current position to for
	 * iteration towards next element in queue has functions such as next(),
	 * hasNext() and remove() implemented
	 */

	private class QueueIterator<Type> implements Iterator<Type> {

		private ListNode currentPosition;
		private int currentModCount;

		private QueueIterator() {
			this.currentPosition = head;
			this.currentModCount = modCount;
		}

		/**
		 * Returns true if the iteration has more elements
		 * 
		 * @return true if more, false otherwise
		 */

		public boolean hasNext() {
			return currentPosition != null;
		}

		/**
		 * Returns the current element and moves (if possible) to next element
		 * making it the current element.
		 * 
		 * @return next element in the iteration
		 * @throws NoSuchElementException
		 *             if no more elements
		 * @throws ConcurrentModificationException
		 *             when concurrently two iterator objects are running which
		 *             is not supported
		 */
		public Type next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
			if (this.currentModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			Type data = (Type) this.currentPosition.data;
			this.currentPosition = this.currentPosition.next;
			return data;
		}

		/**
		 * Removes the last element returned by next. This method can be called
		 * only once per call to next. The behavior of an iterator is
		 * unspecified if the underlying collection is modified while iterating.
		 * 
		 * @throws UnsupportedOperationException
		 *             if not supported
		 */

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Constructor for Queue class. initializes head and tail with null and size
	 * with zero.
	 */

	public Queue() {
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * Returns number of items in the queue.
	 * 
	 * @return size
	 */
	public int size() {
		return size; // MODIFY
	}

	/**
	 * Returns true if no items, false otherwise
	 * 
	 * @return true or false
	 */
	public boolean isEmpty() {
		return head == null; // MODIFY
	}

	/**
	 * adds item to end of queue
	 * 
	 * @param new
	 *            item to be added
	 */

	public void enqueue(Type item) {

		ListNode newItem = new ListNode(item);

		if (isEmpty()) {
			head = newItem;
			tail = newItem;

		}

		tail.next = newItem;
		tail = tail.next;

		size++;

	}

	/**
	 * return the top node.data in the queue
	 * 
	 * @return head.data
	 * @throws NoSuchElementException
	 *             if queue is empty
	 */

	public Type peek() {
		if (isEmpty())
			throw new NoSuchElementException("Queue empty");
		return head.data;

	}

	/**
	 * removes item from front of queue
	 * 
	 * @return the item removed of type Type
	 * @throws NoSuchElementException
	 *             if queue is empty
	 */

	public Type dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException("Queue empty");
		}
		Type oldHead = head.data;
		head = head.next;
		size--;
		return oldHead; // MODIFY
	}

	/**
	 * iterator used for iterating through the queue
	 * 
	 * @return the QueueIterator object
	 */
	public Iterator<Type> iterator() {

		return new QueueIterator<Type>();
	}

	// --------------------- DO NOT MODIFY BELOW THIS -----------------------//

	@Override
	public String toString() {
		// Uses the iterator to build String
		StringBuilder buf = new StringBuilder();
		boolean first = true;
		buf.append("[");
		for (Type item : this) {
			if (first)
				first = false;
			else
				buf.append(", ");
			buf.append(item.toString());
		}
		buf.append("]");
		return buf.toString();
	}

	/**
	 * Unit tests the Queue data type.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Queue newobj = new Queue();
		newobj.enqueue("andy");
		System.out.println(" enq andy");
		newobj.enqueue("mike");
		System.out.println(" enq mike");
		newobj.enqueue("bob");
		System.out.println(newobj);
		System.out.println(" enq bob");
		System.out.println(newobj.size);
		newobj.dequeue();
		System.out.println(" denq andy");
		newobj.enqueue("liz");
		System.out.println(" enq liz");
		newobj.dequeue();
		System.out.println(" denq mike");
		newobj.dequeue();
		System.out.println(" denq bob");
		newobj.enqueue("larry");
		System.out.println(" enq larry");
		newobj.enqueue("moe");
		System.out.println(" enq moe");
		newobj.enqueue("curly");
		System.out.println(" enq curly");
		System.out.println(newobj.size);
		System.out.println(newobj);
	}
}
