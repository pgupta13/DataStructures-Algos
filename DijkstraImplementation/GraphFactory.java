package PA11;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * GraphFactory creates graphs from various inputs including text files.
 * @author Zeehasham Rasheed
 */
public class GraphFactory
{   
    /**
     * Creates a new graph from input file.  Format is expected to have
     * the integer number of vertices on the first line followed by a
     * variable number of lines with each line having the edge as two
     * vertex identifiers.
     * <p>
     * For example, graph with 4 vertices and 5 edges. 
     * 4
     * 0 3 0.10
     * 0 2 0.20
     * 0 1 0.85
     * 1 2 0.23
     * 2 3 0.64
     * 
     * @param fileName
     * @return 
     * @throws java.io.FileNotFoundException 
     */
    public static WeightedDigraph makeWeightedDigraph( String fileName ) throws FileNotFoundException
    {
        File file = new File(fileName);
        Scanner in = new Scanner(file);
        if( in.hasNextInt() == false )
        {
            throw new IllegalArgumentException("Expected V, got no line");
        }
        
        int V = in.nextInt();
        WeightedDigraph graph = new WeightedDigraph(V);
        while( in.hasNextInt() )
        {
            int v = in.nextInt();
            if( in.hasNextInt() == false )
            {
                String e = "Expected v w, got no w for v=" + v;
                throw new IllegalArgumentException(e);
            }
            int w = in.nextInt();
            
            double weight = in.nextDouble();
            
            graph.addEdge(v, w, weight);
        }
        return graph;
    }
}
