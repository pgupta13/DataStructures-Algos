
import java.util.NoSuchElementException;

/**
 * SymbolTable stores key/value pairs where keys map to unique values.
 * Implementation that relies on keys with proper equals and compareTo implementation.
 * @param <Key>
 * @param <Value>
 */
public interface OrderedSymbolTable <Key extends Comparable, Value>
{
    /**
     * Gets the number of elements currently in the symbol table.
     * @return size
     */
    public int size();

    /**
     * Determines if there are no elements in the  symbol table.
     * @return true if no elements, false otherwise
     */
    public boolean isEmpty();

    /**
     * Inserts the value into the table using specified key.  Overwrites
     * any previous value with specified value.
     * @param key
     * @param value
     * @throws NullPointerException if the key or value is null
     */
    public void put( Key key, Value value );

    /**
     * Finds Value for the given Key.
     * @param key
     * @return value that key maps to or null if not found
     * @throws NullPointerException if the key is null
     */
    public Value get( Key key );

    /**
     * Iterable that enumerates (in sorted order) each key in the table.
     * @return iter
     */
    public Iterable<Key> keys();

    /**
     * Finds and returns minimum key
     * @return smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() throws NoSuchElementException;
    
    /**
     * Finds and returns maximum key
     * @return largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max() throws NoSuchElementException;

    /**
     * Removes the minimum key from the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() throws NoSuchElementException;

    /**
     * Removes the maximum key from the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() throws NoSuchElementException;
}
