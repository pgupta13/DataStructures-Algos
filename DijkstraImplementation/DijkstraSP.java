/*package PA11;


*//**
 * Dijkstra's single-source, shortest path algorithm.  Only works when all
 * weights are non-negative.
 * @author <your name>
 *//*
public class DijkstraSP
{
    // ADD CODE
	private WeightedDiedge[] edgeTo;
	private double[] distTo;
	private MinHeap<Integer> minPQ;
	
    
    *//**
     * Creates a new DijkstraSP and performs search on the specified Graph.
     * @param graph
     * @param source
     * @throws IllegalArgumentException if graph has a negative weight edge
     *//*
    public DijkstraSP( WeightedDigraph graph, int source )
    {
        // ADD CODE
    	for(WeightedDiedge e: graph.edges()){
    		if(e.weight() < 0)
    			throw new IllegalArgumentException("graph has a negative weight edge");
    	}
    	distTo = new double[graph.V()];
    	for(int i =0; i<graph.V(); i++){
    		distTo[i] = Double.MAX_VALUE;
    	}
    	distTo[source]= 0;
    	
    	edgeTo = new WeightedDiedge[graph.V()];
    	minPQ = new MinHeap(graph.V());
    	minPQ.insert(source);
    	
    	while(!minPQ.isEmpty()){
    		int removeItem =minPQ.delMin();
    		for(WeightedDiedge edge : graph.neighbors(v));
    		
    	}
    	
    	
    }
    
    *//**
     * Returns whether or not a path exists from the source to v.
     * @param v
     * @return true if a path exists from the source to v, false otherwise
     *//*
    public boolean hasPathTo( int v )
    {
        return false; // MODIFY CODE
    }
    
    *//**
     * Returns distance to the specified vertex v.  If the value is
     * Double.MAX_VALUE, the vertex is not reachable from the source.
     * @param v
     * @return distance to v from s
     *//*
    public double distTo( int v )
    {
        return 0.0; // MODIFY CODE
    }
    
    *//**
     * Returns path from the source to the given vertex v, in that order.
     * @param v
     * @return path from the source to v, starts with source, ends with v
     *         returns a null if no path exists
     *//*
    public Iterable<WeightedDiedge> pathTo( int v )
    {
        return null; // MODIFY CODE
    }
    
    
    //---------- DO NOT MODIFY BELOW THIS LINE ----------//

    *//**
     * Unit test main for the DijkstraSP class.
     * @param args 
     * @throws java.io.FileNotFoundException 
     *//*
    public static void main( String[] args ) throws java.io.FileNotFoundException
    {
        if( args.length != 2 )
        {
            String u = "Usage: DijkstraSP <filename> <source>";
            Stdio.println(u);
            return;
        }
        
        String fileName = args[0];
        int source      = Integer.parseInt(args[1]);
        WeightedDigraph graph = GraphFactory.makeWeightedDigraph(fileName);
        Stdio.println( "Graph: "+graph );
        
        DijkstraSP dijkstraSP = new DijkstraSP( graph, source );
        Stdio.println( "Paths to source: "+source );
        for (int vertexId = 0; vertexId < graph.V(); vertexId++)
        {
            Stdio.print( "  path for "+vertexId+" : " );
            if( dijkstraSP.hasPathTo(vertexId) )
            {
                for( WeightedDiedge path : dijkstraSP.pathTo(vertexId) )
                {
                    if( path != null ) Stdio.print( path.toString() );
                }
            }
            Stdio.println( "" );
        }
    }
}
*/