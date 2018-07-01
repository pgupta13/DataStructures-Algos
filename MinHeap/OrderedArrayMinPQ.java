
import java.util.Iterator;

/**
 * Min priority queue implemented with a dynamic array and 
 * insertion sort.  Not a particularly good implementation
 * as the insert requires O(n).  Supports grow operation
 * but not shrink.
 */
public class OrderedArrayMinPQ implements MinPQ
{
    private static final int DEFAULT_CAPACITY = 2;

    private Comparable[] keys;
    private int size;
    
    public OrderedArrayMinPQ()
    {
        this(DEFAULT_CAPACITY);
    }
    
    public OrderedArrayMinPQ(int initCapacity)
    {
        this.keys = new Comparable[initCapacity];
        this.size = 0;
    }
    
    public int size()
    {
        return this.size;
    }
    
    public boolean isEmpty()
    {
        return size() == 0;
    }
    
    public void insert( Comparable item )
    {
        if( this.size == this.keys.length )
        {
            this.grow();
        }
        this.keys[size] = item;
        // Keep sorted, i.e. make sure item[size] is max
        sortInsert(this.keys, size);
        this.size++;
    }
    
    public Comparable delMin( )
    {
        if( isEmpty() )
        {
            throw new IllegalArgumentException("No item to delete");
        }
        
        this.size--;
        Comparable item = this.keys[size];
        this.keys[size]= null; // prevents loitering
         
        return item;
    }

    public Comparable min()
    {
        if( isEmpty() ) return null;
        else return this.keys[size];
    }

    private void grow()
    {
        Comparable[] newItems = new Comparable[this.keys.length*2];
        System.arraycopy(this.keys, 0, newItems, 0, this.keys.length);
        this.keys = newItems;
    }

    /** COPIED FROM InsertionSort, modified to be descending order
     * Insert item at pos into already sorted items [0,pos-1].
     * Uses variant which avoids swap routine.
     * @param items
     * @param pos index of item to insert
     */
    private static void sortInsert( Comparable[] items, int pos )
    {
        Comparable unsortedItem = items[pos];
        int j = pos;
        while( j > 0 && more( unsortedItem, items[j-1] ) )
        {
            // Shift to the right
            items[j] = items[j-1];
            j--;
        }
        items[j] = unsortedItem;
    }

    /**
     * Is v &gt w (note: strictly greater than)
     * @param v
     * @param w
     * @return 
     */
    public static boolean more( Comparable v, Comparable w )
    {
        return (v.compareTo(w) > 0);
    }
    
    /**
     * Returns Iterator that returns keys in order.  Does not support remove.
     * @return iterator
     */
    public Iterator<Comparable> iterator()
    {
        return new Iterator<Comparable>()
        {
            private int current = size-1;

            public boolean hasNext()
            {
                return current >= 0;
            }

            public Comparable next()
            {
                if( current < 0 )
                {
                    throw new java.util.NoSuchElementException("No more elements");
                }
                return keys[current--];
            }

            public void remove()
            {
                throw new UnsupportedOperationException("Not supported.");
            }

        };
    }
    
    @Override
    public String toString()
    {
        // Uses the iterator to build String
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        boolean first = true;
        for (Comparable key : this)
        {
            if( first ) first = false;
            else buf.append(", ");
            buf.append( key.toString() );
        }
        buf.append("]");
        return buf.toString();
    }
    
    
    /**
     * Unit tests the Queue data type.
     * @param args
     */
    public static void main( String[] args )
    {
        OrderedArrayMinPQ minpq = new OrderedArrayMinPQ();
        
        Stdio.open( args[0] );
        while( Stdio.hasNext() )
        {
            String sItem = Stdio.readString();
            if( ! sItem.equals("-") )   minpq.insert( Integer.parseInt(sItem) );
            else                        Stdio.print( minpq.delMin() +" " );
        }
        
        Stdio.println( "" );
        Stdio.println( "Final queue=" +minpq.toString() );
        Stdio.close();
    }

    
}
