import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Prachi Gupta Username: pgupta13 G01025257
 * INFS 519
 * Fall 2016
 */

/**
 * SymbolTable stores key/value pairs where keys map to unique values. Binary
 * search tree is a symbol table that addresses the weaknesses of unordered list
 * and ordered arrays.
 * 
 * @param <Key>
 * @param <Value>
 */

/**
 * This class uses two arrays for keys and values individually and manipulates
 * them using different insert, delete, iterator functions defined below. The
 * implementation using two arrays is simple and easy to understand and uses a
 * key to associated value.
 */
public class BinarySearchTreeST<Key extends Comparable, Value> implements OrderedSymbolTable<Key, Value> {
	// creating arrays for keys and values and a variable to check the size of
	// the array
	private int capacity = 8;
	private Key[] keys;
	private Value[] valueArray;
	private int size;

	// constructor for the class where it initializes the arrays to a minimum
	// capacity of 8
	public BinarySearchTreeST() {
		keys = (Key[]) new Comparable[capacity];
		valueArray = (Value[]) new Object[capacity];
		// ADD CODE
	}

	// returns size
	public int size() {
		return size;
	}

	// check for empty condition
	public boolean isEmpty() {
		return size() == 0; // MODIFY CODE
	}

	// put method to insert key value pair inside the BST, this method uses a
	// unique method called rank for the key passed which defines where the key
	// value pair should be inserted
	public void put(Key key, Value value) {
		// if value null the delete the key from BST as no null vales are
		// allowed in BST
		if (value == null) {
			delete(key);
			return;
		}

		int i = rank(key);

		// key is already in table. Update
		if (i < size && key.compareTo(keys[i]) == 0) {
			valueArray[i] = value;
			return;
		}

		// insert new key-value pair
		if (size == keys.length)
			resize(keys.length * 2);

		for (int j = size; j > i; j--) {
			keys[j] = keys[j - 1];
			valueArray[j] = valueArray[j - 1];
		}
		keys[i] = key;
		valueArray[i] = value;
		size++;

	}

	// get methos returns the value associated with the key
	public Value get(Key key) {
		if (isEmpty())
			return null;
		int i = rank(key);
		if (i < size && keys[i].compareTo(key) == 0)
			return valueArray[i];
		else
			return null;// MODIFY CODE
	}

	// iterator for keys
	public Iterable<Key> keys() {
		int low = 0, high = size - 1;
		// uses dynamic array datastructure
		DynamicArray<Key> q = new DynamicArray<Key>();
		for (int i = rank(keys[low]); i < rank(keys[high]); i++)
			q.insert(i, keys[i]);
		if (max() != null)
			q.insert(high, keys[rank(keys[high])]);
		return q; // MODIFY CODE
	}

	// returns the minimum key in the BST
	// throws NoSuchElementException exception for no key
	public Key min() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException("No such element found");
		return keys[0]; // MODIFY CODE
	}

	// returns the max key in the whole tree
	// throws NoSuchElementException exception for no key
	public Key max() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException("No such element found");
		return keys[size - 1]; // MODIFY CODE
	}

	// deletes the max key value pair on the basis of key
	// throws NoSuchElementException exception for no key
	public void deleteMax() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException("No such element found");
		delete(max());
	}

	// deletes the max key value pair on the basis of key
	// throws NoSuchElementException exception for no key
	public void deleteMin() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException("No such element found");
		delete(min());
	}

	//delets the key value pair associated with the key passed
	public void delete(Key key) {
		//check if empty, then return
		if (isEmpty())
			return;

		//define rank
		int i = rank(key);
		//compare and check key, replace the next key with current key and same with value
		if (i < size && key.compareTo(keys[i]) == 0) {
			for (int j = i; j < size - 1; j++) {
				keys[j] = keys[j + 1];
				valueArray[j] = valueArray[j + 1];
			}

			size--;
			keys[size] = null; // free memory
			keys[size] = null; // free memory

			// resize if 1/4 full
			if (size == keys.length / 4)
				resize(keys.length / 4);
		}
	}

	// resizing the previous array size into new double sized array when the
	// array is full
	private void resize(int capacity) {
		assert capacity >= size;
		Key k[] = (Key[]) new Comparable[capacity];
		Value v[] = (Value[]) new Object[capacity];

		//copy keys to new array
		System.arraycopy(keys, 0, k, 0, (keys.length < k.length) ? keys.length : k.length);
		keys = k;

		//copy values to new array
		System.arraycopy(valueArray, 0, v, 0, valueArray.length);
		valueArray = v;
	}

	// this method defines the rank for a particular tree
	// by using the rank, insertion in the tree is done
	public int rank(Key key) {
		int start = 0, end = size - 1;
		// check which subtree is the apt position for inserting the new key by
		// using start, mid and size-1 variables
		while (start <= end) {
			int mid = start + (end - start) / 2;
			int cmp = key.compareTo(keys[mid]);
			if (cmp < 0)
				end = mid - 1;
			else if (cmp > 0)
				start = mid + 1;
			else
				return mid;
		}
		return start;
	}

    
    //--------------------- DO NOT MODIFY BELOW THIS -----------------------//

    @Override
    public String toString()
    {
        // Uses the iterator to build String
        StringBuilder buf = new StringBuilder();
        boolean first = true;
        buf.append("[");
        for (Key key : this.keys())
        {
            Value item = this.get(key);
            if( first ) first = false;
            else buf.append( ", " );
            buf.append( key );
            buf.append( "->" );
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }

    /**
     * Unit tests the ST data type.
     * @param args 
     */
    public static void main(String[] args)
    {
        Stdio.open( args[0] );
        BinarySearchTreeST<Integer,String> st = new BinarySearchTreeST<Integer,String>();
        while( Stdio.hasNext() )
        {
            String method = Stdio.readString();
            if( method.equalsIgnoreCase("insert") )
            {
                int key    = Stdio.readInt();
                String val = Stdio.readString();
                st.put( key, val );
                Stdio.println( "insert="+st.toString() );
            }
            else if( method.equalsIgnoreCase("deleteMin") )
            {
                st.deleteMin();
                Stdio.println( "deleteMin" );
            }
            else if( method.equalsIgnoreCase("deleteMax") )
            {
                st.deleteMax();
                Stdio.println( "deleteMax" );
            }
            else if( method.equalsIgnoreCase("size") )
            {
                Stdio.println( "size="+st.size() );
            }
            else if( method.equalsIgnoreCase("min") )
            {
                Stdio.println( "min="+st.min() );
            }
            else if( method.equalsIgnoreCase("max") )
            {
                Stdio.println( "max="+st.max() );
            }
            else if( method.equalsIgnoreCase("isEmpty") )
            {
                Stdio.println( "isEmpty?="+st.isEmpty() );
            }
        }
        Stdio.println( "Final symbol table=" +st.toString() );
        Stdio.close();
    }
}

