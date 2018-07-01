
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Stack implemented using dynamically sized array.
 * @author Zeehasham Rasheed
 * @param <Type>
 */
public class Stack <Type> implements Iterable<Type>
{
    private static final int DEFAULT_CAPACITY = 8;
    
    private Type[] items;
    private int size;

    public Stack()
    {
        this.items = (Type[])new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public void push(Type item)
    {
        // If not eough room , expand
        if( this.size == this.items.length )
        {
            grow();
        }
        this.items[size] = item;
        this.size++;
    }
    
    public Type pop()
    {
        if( this.isEmpty() )
        {
            throw new IllegalArgumentException("No item to pop");
        }
        this.size--;
        Type item = this.items[size];
        this.items[size] = null; // prevent loitering
        return item;
    }
    
    public Type peek()
    {
        return this.items[size-1];
    }
    
    public int size()
    {
        return this.size;
    }
    
    public boolean isEmpty()
    {
        return this.size == 0;
    }

    private void grow()
    {
        Type[] newItems = (Type[])new Object[this.items.length*2];
        System.arraycopy(this.items, 0, newItems, 0, this.items.length);
        this.items = newItems;
    }
    
    @Override
    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for (int i = 0; i < this.size; i++)
        {
            Type item = items[i];
            if( i != 0 ) buf.append(", ");
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }

    public Iterator<Type> iterator()
    {
        return new Iterator()
        {
            private int currentIndex = size - 1;
            public boolean hasNext()
            {
                return currentIndex >= 0;
            }

            public Object next()
            {
                if( currentIndex < 0 )
                {
                    throw new NoSuchElementException("No more elements");
                }
                
                return items[currentIndex--];
            }

            public void remove()
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
        };
    }
    
}
