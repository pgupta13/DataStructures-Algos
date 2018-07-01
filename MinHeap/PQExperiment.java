
public class PQExperiment
{
    /**
     * Compares running times for priority queue implementations.
     * @param args 
     */
    public static void main( String[] args )
    {
        if( args.length != 1 )
        {
            String u = "Usage: PQExperiment <n>";
            Stdio.println(u);
            return;
        }
        
        int n = Integer.parseInt( args[0] );
        int bounds = 100; // keep generated number between [0,bounds)
        Comparable[] items = Stdio.generate( n , bounds);
        
        
        Clock clockHeap = new Clock();
        MinHeap pqHeap         = new MinHeap(n);
        experiment( items, pqHeap );
        Stdio.println( "BinaryHeap took = " + clockHeap);
        
        Clock clockArray = new Clock();
        OrderedArrayMinPQ pqArray = new OrderedArrayMinPQ(n);
        experiment( items, pqArray );
        Stdio.println( "OrderedArray took = " + clockArray);
    }
    
    /**
     * Inserts items into the priority queue and then deletes them.  Also
     * would be good to perform series of mixed insert/delMax operations.
     * @param items
     * @param pq 
     */
    public static void experiment( Comparable[] items, MinPQ pq )
    {
        for (Comparable item : items)
        {
            pq.insert(item);
        }
        while( pq.isEmpty() == false )
        {
            pq.delMin();
        }
    }
}
