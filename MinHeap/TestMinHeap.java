
import java.util.Arrays;
import static org.junit.Assert.*;

/**
 * Test suite for MinHeap.
 * @author Zeehasham Rasheed
 */
public class TestMinHeap 
{   
    @org.junit.Test (expected = java.util.NoSuchElementException.class)
    public void testDelMinException()
    {
        MinPQ minpq = new MinHeap();
        minpq.insert(2);
        minpq.delMin();
        minpq.delMin();
    }
    
    @org.junit.Test (expected = java.util.NoSuchElementException.class)
    public void testMinException()
    {
        MinPQ minpq = new MinHeap();
        minpq.insert(2);
        minpq.delMin();
        minpq.min();
    }
    
    @org.junit.Test
    public void testIsEmpty()
    {
        MinPQ minpq = new MinHeap();
        assertEquals(   true, minpq.isEmpty()   );
        minpq.insert(2);
        assertEquals(  false, minpq.isEmpty()   );
        minpq.insert(1);
        minpq.insert(5);
        assertEquals(  false, minpq.isEmpty()   );
        minpq.delMin();
        minpq.delMin();
        minpq.delMin();
        assertEquals(   true, minpq.isEmpty()   );
    }
    
    @org.junit.Test
    public void testInsertDelMin()
    {
        MinPQ minpq = new MinHeap();
        
        minpq.insert(90);
        minpq.insert(15);
        minpq.insert(74);
        
        assertEquals(  3, minpq.size()   );
        assertEquals( 15, minpq.delMin() );
        assertEquals(  2, minpq.size()   );
        
        minpq.insert(88);
        minpq.insert(37);
        
        assertEquals(  4, minpq.size()   );
        assertEquals( 37, minpq.delMin() );
        assertEquals(  3, minpq.size()   );
        
        minpq.insert(26);
        minpq.insert(59);
        
        assertEquals(  5, minpq.size()   );
        assertEquals( 26, minpq.delMin() );
        assertEquals(  4, minpq.size()   );
    }

    @org.junit.Test
    public void testOperations()
    {
        MinPQ minpq = new MinHeap();
        
        int[] initialItems = new int[]{79,45,-76,73,66,-40,99,19,19,27};
        
        insertItems(minpq, initialItems        );
        assertEquals(     10, minpq.size()     );
        
        // sort the items, if minpq is correct, will match
        Arrays.sort(initialItems);
        int index = 0;
        while( !minpq.isEmpty() )
        {
            assertEquals( minpq.delMin(), initialItems[index++] );
        }
        
        insertItems(minpq, new int[]{94,24,89,-52,28,-2,80,70,99,66});
        assertEquals(-52, minpq.delMin() );
        assertEquals( -2, minpq.delMin() );
        assertEquals( 24, minpq.delMin() );
        assertEquals( 28, minpq.min()    );
        assertEquals( 28, minpq.delMin() );
        assertEquals( 6, minpq.size()    );
        
        int[] finalItems = new int[]{66, 70, 80, 94, 99, 89};
        index = 0;
        for( Comparable item : minpq )
        {
            assertEquals( item, finalItems[index++] );
        }
    }
    
    public static final void insertItems( MinPQ minpq, int[] items )
    {
        for (int i = 0; i < items.length; i++)
        {
            minpq.insert(items[i]);
        }
    }
    
}
