
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Queue linked list implementation that uses singly nodes and
 * double-ended references.
 * @author Zeehasham Rasheed
 * @param <Type>
 */
public class Queue <Type> implements Iterable<Type>
{
    
    // Internal helper class to keep singly linked list
    private class Node
    {
        public Type item;
        public Node next;
        
        public Node( Type item )
        {
            this.item = item;
        }
    }
    
    // Double-ended reference to front and last in the list
    private Node front;
    private Node last;
    private int size;
    
    /**
     * Creates a new, empty Queue.
     */
    public Queue()
    {
        this.size  = 0;
        this.front = null;
        this.last  = null;
    }
    
    /**
     * Returns number of items in the queue.
     * @return size
     */
    public int size()
    {
        return this.size;
    }
    
    /**
     * Determines if queue is empty.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty()
    {
        return this.size == 0;
    }
    
    /**
     * Adds an item to the end of the queue.
     * @param item 
     */
    public void enqueue( Type item )
    {
        Node newNode = new Node(item);
        if( this.front == null )
        {
            this.front = newNode;
        }
        else
        {
            // Add to end
            this.last.next = newNode;
        }
        this.last = newNode;
        this.size++;
    }
    
    /**
     * Returns item at front of queue without removing.
     * @return item at front
     * @throws java.util.NoSuchElementException if empty
     */
    public Type peek()
    {
        if( isEmpty() )
        {
            throw new NoSuchElementException("No item to dequeue");
        }
        return this.front.item;
    }
    
    /**
     * Removes item from front of queue.
     * @return item removed from front
     * @throws java.util.NoSuchElementException if empty
     */
    public Type dequeue( )
    {
        if( isEmpty() )
        {
            throw new NoSuchElementException("No item to dequeue");
        }
        
        Node n = this.front;
        this.front = this.front.next;
        this.size--;
        
        if( this.isEmpty() ) this.last = null; // prevents loitering
        return n.item;
    }
    
    /**
     * Returns Iterator that will return items starting with the oldest
     * item added and ending with the most recent item (from front to back).
     * Does not support remove.
     * @return iterator
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
                if( current == null )
                {
                    throw new java.util.NoSuchElementException("No more elements");
                }
                Node nextNode = current;
                current = current.next;
                return nextNode.item;
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
    
    
    /**
     * Unit tests the Queue data type.
     * @param args 
     */
    public static void main( String[] args )
    {
        Queue queue = new Queue();
        
        Stdio.open( args[0] );
        while( Stdio.hasNext() )
        {
            String item = Stdio.readString();
            if( ! item.equals("-") )   queue.enqueue(item);
            else                       Stdio.println( queue.dequeue() );
        }
        
        Stdio.println( "Queue=" +queue.toString() );
        
        Stdio.println( "5!="+factorial(5) );
        
        Stdio.println( queue.dequeue() );
        Stdio.println( queue.dequeue() );
        Stdio.println( queue.dequeue() );
        Stdio.println( queue.dequeue() );
        
        Stdio.close();
    }
    
    public static int factorial(int n)
    {
      // Base Case
      if(n <= 1)
      {
          System.out.println( "n="+n+" return="+1 );
          return 1;
      }
      // Recursive Case
      else
      {
          int x = n * factorial(n-1);
          System.out.println( "n="+n+" return="+x );
          return x;
      }
    }
}
