
/**
 * List objects provide abstract operations for operating on lists of items.
 * @author Zeehasham Rasheed
 * @param <Type>
 */
public interface List<Type>
{
    /**
     * Gets the item at index i
     * @param i
     * @return item at i
     * @throws IndexOutOfBoundsException if i is not in range
     */
    public Type get(int i);
    
    /**
     * Overwrites the item at position i with specified item
     * @param i
     * @param item
     * @throws IndexOutOfBoundsException if i is not in range
     * @throws NullPointerException is item is null
     */
    public void set(int i, Type item);

    /**
     * Appends item to the end of the list.
     * @param item
     * @throws NullPointerException if item is null
     */
    public void add(Type item);

    /**
     * Removes item at index from the list.
     * @param i
     * @return item at i
     * @throws IndexOutOfBoundsException if i is not in range
     */
    public Type remove(int i);

    /**
     * Inserts item at index into the list at index i.
     * @param i
     * @param item
     * @throws IndexOutOfBoundsException if i is not in range
     * @throws NullPointerException is item is null
     */
    public void insert(int i, Type item);

    /**
     * Gets the size of the list (number of items).
     * @return size
     */
    public int size();
}
