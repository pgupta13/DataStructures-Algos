
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Prachi Gupta G01025257 User: pgupta13
 * INFS 519
 * Fall 2016
 */

/**
 * MinHeap class is an implementation concept of priority queue where we keep
 * the child elements at position 2*k and 2*k+1 Then we run swim and sink
 * functions to keep the Min heap property which says the parent node is smaller
 * than the child node
 */
public class MinHeap implements MinPQ {
	public static final int DEFAULT_CAPACITY = 8;

	int position; // gives the current position in the array where next element
					// can b inserted

	int size; // gives size of the minheap array

	Comparable[] minheap;
	// When no size if specified for initializing the Min Heap array, default
	// size is taken which is 8

	public MinHeap() {
		// ADD CODE
		minheap = new Comparable[DEFAULT_CAPACITY];
		position = 0;
	}

	// When no size if specified for initializing the Min Heap array, default
	// size is taken which is initialCapacity

	public MinHeap(int initialCapacity) {
		// ADD CODE
		minheap = new Comparable[initialCapacity + 1];
		position = 0;
	}

	// this function takes an argument of type Comparable and inserts it in the
	// heap, after wards swim functions is called to check the Min Heap property
	public void insert(Comparable item) {
		// function call for checking the size of current array anf then
		// icreasing the array capacity by double if the size of elements are
		// beyond max capacity of array
		checkGrow();
		// check for length of array before inserting
		if (minheap.length > 0) {
			// if size falls below 0, this conditions check for the proper
			// insertion in the array
			if (size <= 0) {
				position = 0;
				size = 0;
			}
			// starting point of inserting into the array, root element added at
			// position = 1 and increents the position by1 for next insertions
			if (position == 0) {
				minheap[position + 1] = item;
				position = 2;
			} else {
				// after the root node, child elements are added to the array in
				// this loop
				minheap[position++] = item;
				swim(position - 1);
			}
			size++;
		}

	}

	// this function takes in int k as input parameter and checks for if the
	// parent elements are greater than the child node, then swaps if the
	// condition is true
	private void swim(int k) {
		// ADD CODE
		int checkItem = minheap[k].compareTo(minheap[k / 2]);
		while (k > 1 && checkItem < 0) {
			if (minheap[k].compareTo(minheap[k / 2]) < 0) {
				// swap parent and child
				swap(k, k / 2);
				k = k / 2; // move up, takes floor
			} else
				break;
		}

	}

	// returns minimum element in the heap, i.e. the root node
	public Comparable min() {
		return minheap[1]; // MODIFY
	}

	// this function deletes the smallest element on the heap i.e the root node
	// and sets the last element in the array as forst one, then a sink
	// function is called in order to sink down the element in order to maintain
	// the min heap property

	public Comparable delMin() {
		Comparable toBeDeleted = minheap[1];
		// make the root element as the last element in the array
		minheap[1] = minheap[position - 1];
		// make it null and decrement size and position by 1
		minheap[position - 1] = null;
		position--;
		size--;
		// calling sink function in order to percolate down the root element for
		// min heap property
		sink(1);
		return toBeDeleted; // MODIFY
	}

	// sink function is called by delMin function in order to percolate down the
	// root element in case it is larger than its child elements
	private void sink(int k) {

		int childnode = 1;
		Comparable temp = minheap[k];

		// loop for swapping the parent with child node
		for (; k * 2 <= size; k = childnode) {
			// child element in min heap is at 2*k or 2* k+1
			childnode = k * 2;
			if (childnode != size && minheap[childnode + 1].compareTo(minheap[childnode]) < 0)
				childnode++;
			if (temp.compareTo(minheap[childnode]) > 0) {
				minheap[k] = minheap[childnode];
			} else
				break;

		}
		minheap[k] = temp;
	}

	// function used to compare which of the two elemnts are greater than the
	// other, it takes two parameters which are the indexes of the array and
	// uses compareTo function
	private boolean greater(int x, int y) {
		if (minheap[x].compareTo(minheap[y]) > 0)
			return true;
		else
			return false;// MODIFY
	}

	// function swap is used to exchange the two elements, it takes two
	// parameters which are the indexes of the array and swaps them
	private void swap(int x, int y) {
		// ADD CODE
		Comparable temp = minheap[x];
		minheap[x] = minheap[y];
		minheap[y] = temp;
	}

	// size returns the current size of the array
	public int size() {
		return size; // MODIFY
	}

	// check if the array is empty
	public boolean isEmpty() {
		return size == 0; // MODIFY
	}

	// checks the growth of the array, if the size is beyond the capacity,
	// doubles the array size
	private void checkGrow() {
		int i = size + 2;
		int oldContainerSize = minheap.length;
		if (i > oldContainerSize) {
			int newContainerSize = oldContainerSize * 2;
			minheap = Arrays.copyOf(minheap, newContainerSize);

		}

		// MODIFY
	}

	/**
	 * USE IS OPTIONAL, NOT NECESSARY Utility method that compares a and b.
	 * Conceptually return a &lt b.
	 * 
	 * @param a
	 * @param b
	 * @return true if a strictly less than b, false otherwise
	 */
	public static boolean less(Comparable a, Comparable b) {
		return a.compareTo(b) < 0;
	}

	/**
	 * This is an iterator specifically for the Queue, it implements
	 * Iterator<Type> Uses variables listNode type current position to for
	 * iteration towards next element in queue has functions such as next(),
	 * hasNext() and remove() implemented
	 */

	private class HeapIterator implements Iterator<Comparable> {

		private int pos = 1; // starts from 1 since the root element is at 1
		/**
		 * Returns true if the iteration has more elements
		 * 
		 * @return true if more, false otherwise
		 */
		public boolean hasNext() {
			boolean flag = false;
			if (pos <= size) {
				flag = minheap[pos] != null;
			}
			return flag;
		}

		/**
		 * Returns the current element and moves (if possible) to next element
		 * making it the current element.
		 * 
		 * @return next element in the iteration
		 * @throws NoSuchElementException
		 *             if no more elements
		 */
		public Comparable next() {
			if (this.hasNext())
				return (Comparable) minheap[pos++];
			else
				throw new NoSuchElementException();
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
	 * Returns Iterator that returns items in level order. Does not support
	 * remove.
	 * 
	 * @return iterator
	 */
	public Iterator<Comparable> iterator() {
		return new HeapIterator(); // MODIFY
	}

	// --------------------- DO NOT MODIFY BELOW THIS -----------------------//

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		boolean first = true;
		for (Comparable item : this) {
			if (first)
				first = false;
			else
				buf.append(", ");

			buf.append(item.toString());
		}
		return buf.toString();
	}

	/**
	 * Unit tests the BinaryHeap data type.
	 * 
	 * @param args
	 */
	 public static void main(String[] args)
	    {
	        Stdio.open( args[0] );
	        MinPQ pq = new MinHeap();
	        while( Stdio.hasNext() )
	        {
	            String method = Stdio.readString();
	            if( method.equalsIgnoreCase("insert") )
	            {
	                int value = Stdio.readInt();
	                pq.insert( value );
	                Stdio.println( "insert="+pq.toString() );
	            }
	            else if( method.equalsIgnoreCase("delMin") )
	            {
	                Stdio.println( "delMin="+pq.delMin() );
	            }
	            else if( method.equalsIgnoreCase("size") )
	            {
	                Stdio.println( "size="+pq.size() );
	            }
	            else if( method.equalsIgnoreCase("min") )
	            {
	                Stdio.println( "min="+pq.min() );
	            }
	            else if( method.equalsIgnoreCase("empty") )
	            {
	                while( pq.isEmpty() == false )
	                {
	                    Stdio.println( "delMin="+pq.delMin() );
	                }
	            }
	        }
	        Stdio.println( "Final priority queue=" +pq.toString() );
	        Stdio.close();
	    }
	}
