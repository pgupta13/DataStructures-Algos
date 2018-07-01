
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DynamicArray<Type> implements List<Type>
{
    public static final int DEFAULT_INIT_CAPACITY = 2;

    private Type[] items;
    private int size;

    public DynamicArray()
    {
        this.items = (Type[])new Object[DEFAULT_INIT_CAPACITY];
        this.size  = 0;
    }

    public DynamicArray(int initialCapacity)
    {
        this.items = (Type[])new Object[initialCapacity];
        this.size  = 0;
    }

    public Type get(int i)
    {
        checkRange( i );
        return this.items[i];
    }
    
    
    public void set(int i, Type item)
    {
        checkRange( i );
        checkNull(item);
        this.items[i] = item;
    }

    public void add(Type item)
    {
        checkNull(item);
        checkGrow( );
        this.items[this.size] = item;
        this.size++;
    }

    
    public Type remove(int i)
    {
        checkRange( i );
        Type item = this.items[i];
        // Shift left
        for( int j = i; j < this.size-1; j++ )
        {
            this.items[j] = this.items[j+1];
        }
        this.size--;
        this.items[size] = null; // no loitering
        checkShrink( );
        return item;
    }

    public void insert(int i, Type item)
    {
        checkRange( i );
        checkNull(item);
        checkGrow( );
        // Shift right
        for( int j = this.size; j > i; j-- )
        {
            this.items[j] = this.items[j-1];
        }
        this.items[i] = item;
        size++;
    }

    public int search(Object o)
    {
        for( int i = 0; i < this.size; i++ )
        {
            Type item = this.items[i];
            if( item.equals(o) )
            {
                return i;
            }
        }
        return -1;
    }
    
    public Iterator<Type> iterator()
    {
        // Create anonymous Iterator.  Note: Creates class and instance of it
        return new Iterator<Type>()
        {
            private int current = 0;

            public boolean hasNext()
            {
                return current < size;
            }

            public Type next()
            {
                if( current == size )
                {
                    throw new NoSuchElementException("No more elements");
                }
                return get(current++);
            }

            public void remove()
            {
                throw new UnsupportedOperationException("Not supported");
            }
        };
    }

    public int size()
    {
        return this.size;
    }

    @Override
    public String toString()
    {
        // Uses the iterator to build String
        StringBuilder buf = new StringBuilder();
        boolean first = true;
        buf.append("[");
        for (Type item : this)
        {
            if( first ) first = false;
            else buf.append( ", " );
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }


    // Utility methods
    private void checkRange( int i )
    {
        if( i < 0 || i >= this.size ) throw new IndexOutOfBoundsException("Index out of range: "+i);
    }

    private void checkNull( Type item )
    {
        if( item == null ) throw new NullPointerException("Item cannot be null");
    }

    private void checkGrow( )
    {
        if( this.size == this.items.length )
        {
            resize(this.size*2); // grow
        }
    }

    private void checkShrink( )
    {
        // Note the size>0 prevents shrinking capacity from 2 to 0
        if( this.size > 0 && this.size == this.items.length / 4 )
        {
            resize(this.items.length / 2); // shrink
        }
    }

    private void resize( int newCapacity )
    {
        //Stdio.println("Resizing to "+newCapacity);
        Type[] newItems = (Type[])new Object[newCapacity];
        System.arraycopy(this.items, 0, newItems, 0, this.size);
        this.items = newItems; // overwrite, previous garbage collected
    }


    public static void main(String[] args)
    {
        DynamicArray<String> list = new DynamicArray<String>();
        list.add("Alpha");
        list.add("Bravo");
        list.add("Charlie");
        list.add("Delta");

        // Example iterator-while loop, little verbose
        Iterator<String> iter = list.iterator();
        while( iter.hasNext() )
        {
            String item = iter.next();
            Stdio.println(item);
        }

        // Enhanced for-each shortcut, compiler converts to
        // the above equivalent iterator-while loop
        for (String item : list)
        {
            Stdio.println(item);
        }
        
        // Check that we throw exception
        //iter.next();
        
        // Iterator testing
        java.util.ArrayList<String> ll = new java.util.ArrayList<String>();
        ll.add("A");
        ll.add("B");
        ll.add("C");
        ll.add("D");
        
        Iterator<String> itr = ll.iterator();
        String s = itr.next();
        Stdio.println( "next="+s );
        
        Stdio.println( ll );
        s = itr.next();
        Stdio.println( "next="+s );
        
        Stdio.println( ll );
        itr.remove();
        Stdio.println( "removed?" );
        
        Stdio.println( ll );
        s = itr.next();
        Stdio.println( "next="+s );
        
        Stdio.println( ll );
        itr.remove();
        Stdio.println( "removed?" );
        
//        Stdio.println( ll );
//        s = itr.next();
//        Stdio.println( "next="+s );
        
        Stdio.println( ll );
        itr.remove();
        Stdio.println( "removed?" );
        
        Stdio.println( ll );
    }


}


