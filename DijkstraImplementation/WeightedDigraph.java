package PA11;
import java.util.Iterator;

/**
 * WeightedDigraph represents a set of vertices and edges that connect them.
 * Uses an adjacency list data structure where the vertex identifier is from
 * 0 to V-1.
 * <p>
 * The graph represents a directed, weighted graph.
 * @author Zeehasham Rasheed
 */
public class WeightedDigraph
{
    private final Bag<WeightedDiedge>[] vertices;
    private int E;
    
    /**
     * Creates a new graph.  Vertices are fixed.  Edges can be added 
     * after creation but not removed.
     * @param numVertices
     */
    public WeightedDigraph( int numVertices )
    {
        this.vertices = (Bag<WeightedDiedge>[])new Bag[numVertices];
        for (int i = 0; i < vertices.length; i++)
        {
            this.vertices[i] = new Bag();
        }
    }
    
    /**
     * Gets the number of vertices in the graph.
     * @return V
     */
    public int V()
    {
        return this.vertices.length;
    }
    
    /**
     * Gets the number of edges in the graph.
     * @return E
     */
    public int E()
    {
        return this.E;
    }
    
    /**
     * Gets iterator that enumerates the vertexId of the neighbors of given
     * vertexId.
     * @param vertexId
     * @return neighbor of vertexId
     * @throws IndexOutOfBoundsException if vertexId is invalid
     *         (less than 0, more than or equal to V)
     */
    public Iterable<WeightedDiedge> neighbors( final int vertexId )
    {
        return new Iterable()
        {
            public Iterator iterator()
            {
                return vertices[vertexId].iterator();
            }
        };
    }

    /**
     * Gets all the edges of the graph.
     * @return 
     */
    public Iterable<WeightedDiedge> edges()
    {
        Bag<WeightedDiedge> iter = new Bag<WeightedDiedge>();
        for (Bag<WeightedDiedge> neighbors : vertices)
        {
            for( WeightedDiedge e : neighbors )
            {
                iter.add(e);
            }
        }
        return iter;
    }

    /**
     * Adds an edge between v and w.
     * @param from
     * @param to
     * @param weight 
     * @throws IndexOutOfBoundsException if v or w are invalid
     *         (less than 0, more than or equal to V)
     */
    public void addEdge( int from, int to, double weight )
    {
        this.addEdge( new WeightedDiedge(from,to,weight) );
    }

    /**
     * Adds an edge between v and w.
     * @param e
     * @throws IndexOutOfBoundsException if v or w are invalid
     *         (less than 0, more than or equal to V)
     * @throws NullPointerException if e is null
     */
    public void addEdge( WeightedDiedge e )
    {
        this.vertices[e.from()].add(e);
        this.E++;
    }

    /**
     * Gets String facsimile of this graph.
     * @return 
     */
    @Override
    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        String title = "V=" + this.V() + " E=" + this.E();
        buf.append( title );
        for (int vertexId = 0; vertexId < this.vertices.length; vertexId++)
        {
            Bag<WeightedDiedge> neighbors = this.vertices[vertexId];
            String prefix = "\n["+vertexId+"] neighbors="+neighbors.size()+": ";
            buf.append( prefix );
            boolean first = true;
            for( WeightedDiedge edge : neighbors )
            {
                if( first ) first = false;
                else buf.append( ", " );
                buf.append( edge.toString() );
            }
        }
        return buf.toString();
    }
    
    
    /**
     * Unit test main for the Graph class.  Reads a file and prints out.
     * @param args 
     * @throws java.io.FileNotFoundException 
     */
    public static void main( String[] args ) throws java.io.FileNotFoundException
    {
        String fileName = args[0];
        WeightedDigraph graph = GraphFactory.makeWeightedDigraph( fileName );
        Stdio.println( "Graph: "+graph );
    }
}