public class MinHeap <Type extends Comparable>
{
    private final Type[] items;
    private int size;

    public MinHeap( int numItems )
    {
        // Add one, zero is dummy
        this.items = (Type[])new Comparable[numItems+1];
        this.size  = 0;
    }

    public void insert( Type item )
    {
        if( isFull() )
        {
            throw new IllegalArgumentException("Heap is full");
        }
        
        // Add at next open position and swim
        size++;
        this.items[size] = item;
        swim(size);
        //System.out.println("PQ: "+ this.toString() );
    }

    private void swim( int k )
    {
        // Look at parent, if we are less, swap
        while( k > 1 && greater(k/2, k) )
        {
            // swap parent and child
            swap(k/2,k);
            k = k/2; // move up
        }
    }

    public Type min()
    {
        return this.items[1];
    }

    public Type delMin()
    {
        //if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");

        Type maxItem = this.items[1];
        // Move the last item to top, easily keeps
        // Binary tree full.  Sink to maintain heap invariant
        this.items[1] = this.items[this.size];
        this.items[this.size] = null;  // no loitering
        this.size--;
        sink(1);
        return maxItem;
    }

    private void sink( int k )
    {
        // While we have another level of childen within size
        while( 2*k <= this.size ) // equals is important to get right child
        {
            // Find larger of children, exchange with them
            // Why? Know after swap heap invariant met for k
            int j = 2*k; // left child index
            // Be careful, don't look at right child if null
            // We know we have a left child, use size instead of null
            if( j < this.size && greater(j, j+1) ) j++;
            //System.out.println("Largest child: "+ j + " = " + items[j]);
            // Now j points to largest child
            if( greater(k, j) == false ) break;
            
            // Otherwise, swap and continue sink
            swap(k,j);
            k = j; // move down
        }
    }    

    private boolean greater( int x, int y )
    {
        if( items[x].compareTo(items[y]) > 0 ) return true;
        return false;
    }

    private void swap( int x, int y )
    {
        Type itemX = items[x];
        items[x]   = items[y];
        items[y]   = itemX;
    }

    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public boolean isFull()
    {
        return size == this.items.length - 1;
    }

    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        for( int i = 0; i < this.items.length; i++ )
        {
            if( i != 0 ) buf.append(", ");
            Type item = this.items[i];
            if( item == null ) buf.append( "-" );
            else buf.append( item.toString() );
        }
        return buf.toString();
    }

    
    /**
     * Unit tests the BinaryHeap data type.
     * @param args 
     */
    public static void main( String[] args )
    {
        MinHeap<Integer> pq = new MinHeap<Integer>(8);
        pq.insert(8);
        pq.insert(5);
        pq.insert(4);
        pq.insert(3);
        pq.insert(6);

        while( pq.isEmpty() == false )
        {
            //System.out.println("PQ: "+ pq);
            System.out.println( pq.delMin() );
        }
    }
}
