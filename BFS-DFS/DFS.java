import java.util.Iterator;

/**
 * @author Prachi Gupta, G01025257
 * INFS 519
 * Fall 2016
 */
/**
 * DFS is used for finding paths from a source vertex to every other vertex in
 * an undirected graph.
 */
public class DFS {
	private Graph graph;
	private int source;
	private boolean[] marked;
	private int[] paths;

	/**
	 * Creates a new DFS and performs search on the specified Graph.
	 * 
	 * @param graph
	 * @param source
	 */
	public DFS(Graph graph, int source) {
		// initialize graph and source vertex
		this.graph = graph;
		this.source = source;
		// create paths array to map the path between index vertice and the
		// current element for which it is updated
		this.paths = new int[graph.V()];
		// mark the visited nodes through this array
		this.marked = new boolean[graph.V()];
		// check if source is out of range
		if (source < 0 || source >= graph.V())
			throw new IndexOutOfBoundsException("vertex " + source + " is not between 0 and " + (graph.V() - 1));
		// call search routine
		search();
	}

	/**
	 * Iterative approach to DFS. Uses Stack that may grow to E. Keeps track of
	 * the marked and paths for later queries.
	 */
	private void search() {
		// create stack
		Stack<Integer> stack = new Stack<Integer>();
		// update path with source for index=source and mark it visited, push it
		// to the stack
		paths[source] = source;
		marked[source] = true;
		stack.push(source);

		while (!stack.isEmpty()) {
			// pop the value in stack and mark it visited
			int v = stack.pop();
			marked[v] = true;
			// check for neighbors of v
			for (int x : graph.neighbors(v)) {

				// if not visited for each neighbor, push it to stack and update
				// the paths array with neighbor for the last popped value of
				// stack
				if (!marked[x]) {
					stack.push(x);
					paths[x] = v;
				}
			}

		}

	}

	/**
	 * Returns whether or not a path exists from the source to v.
	 * 
	 * @param v
	 * @return true if a path exists from the source to v, false otherwise
	 */
	public boolean hasPathFromSource(int v) {
		// checks marked array if path exists
		return marked[v];
	}

	/**
	 * Returns path from the source to the given vertex v, in that order.
	 * 
	 * @param v
	 * @return path from the source to v, starts with source, ends with v
	 *         returns a null if no path exists
	 */
	public Iterable<Integer> pathFromSource(int v) {
		// check if path is present
		if (!hasPathFromSource(v)) {
			return null;
		}
		// through the paths array checks paths and pushes each elemnt to stack
		else {
			Stack<Integer> path = new Stack<Integer>();
			while (v != source) {
				path.push(v);
				v = paths[v];
			}
			path.push(source);
			return path;
		}
	}

    //------- DO NOT MODIFY BELOW THIS LINE -------//
    
    /**
     * Unit test main for the DFS class.
     * @param args 
     * @throws java.io.FileNotFoundException 
     */
    public static void main( String[] args ) throws java.io.FileNotFoundException
    {
        if( args.length != 2 )
        {
            String u = "Usage: DFS <filename> <source>";
            Stdio.println(u);
            return;
        }
        
        String fileName = args[0];
        int source      = Integer.parseInt(args[1]);
        Graph graph = GraphFactory.make( fileName );
        
        DFS dfs = new DFS( graph, source );
        Stdio.println( "Paths to source: "+source );
        for (int vertexId = 0; vertexId < graph.V(); vertexId++)
        {
            Stdio.print( "  path for "+vertexId+" : " );
            if( dfs.hasPathFromSource(vertexId) )
            {
                for( int pathVertex : dfs.pathFromSource(vertexId) )
                {
                    Stdio.print( "->" + pathVertex );
                }
            }
            Stdio.println( "" );
        }
    }
}
