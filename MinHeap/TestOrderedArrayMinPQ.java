
import static org.junit.Assert.*;

/**
 * Test suite for OrderedArrayMinPQTest.
 * @author Zeehasham Rasheed
 */
public class TestOrderedArrayMinPQ 
{
    @org.junit.Test
    public void testInsertDelMin()
    {
        OrderedArrayMinPQ minpq = new OrderedArrayMinPQ();
        
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
}
