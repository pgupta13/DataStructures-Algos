
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Bag supports add, size, and isEmpty in constant time.  Provides
 * iterator in time proportional to N.  Implemented using a single
 * linked list.
 * @author Zeehasham Rasheed
 * @param <Type>
 */
public class Bag<Type> implements Iterable<Type>
{
    private class Node
    {
        private final Type item;
        private Node next;
        
        public Node( Type item )
        {
            this.item = item;
            this.next = null;
        }
        
        @Override
        public String toString()
        {
            return (this.next==null)? "null" : this.next.toString();
        }
    }
    
    private Node front;
    private int size; // not strictly necessary, useful for debugging
    
    /**
     * Creates a new Bag with no elements.
     */
    public Bag()
    {
        this.size  = 0;
    }

    /**
     * Adds an item to the bag (no attempt to prevent duplicates).
     * @param item 
     */
    public void add(Type item)
    {
        Node previousFront = this.front;
        this.front = new Node(item);
        this.front.next = previousFront;
        this.size++;
    }

    /**
     * Returns the number of elements in the bag.
     * @return number of elements
     */
    public int size()
    {
        return this.size;
    }

    /**
     * 
     * @return 
     */
    public Iterator<Type> iterator()
    {
        return new Iterator<Type>()
        {
            private Node current = front;
            
            public boolean hasNext()
            {
                return current != null;
            }

            public Type next()
            {
                if( this.current == null )
                {
                    throw new NoSuchElementException("No more elements");
                }
                Type currentItem = this.current.item;
                this.current = this.current.next;
                return currentItem;
            }

            public void remove()
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
        };
    }
}
