/**
 * @author  Prachi Gupta, G01025257
 * INFS 519
 * Fall 2016
 */
/**
 * BFS is for finding shortest paths (number of edges) from a source vertex (or
 * a set of source vertices) to every other vertex in an undirected graph
 */
public class BFS {
	private Graph graph;
	private int source;
	private boolean[] marked;
	private int[] paths;

	/**
	 * Creates a new BFS and performs search on the specified Graph.
	 * 
	 * @param graph
	 * @param source
	 */
	public BFS(Graph graph, int source) {
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
	 * Iterative approach to BFS. Uses Queue that may grow to E. Keeps track of
	 * the marked and paths for later queries.
	 */
	private void search() {

		Queue<Integer> q = new Queue<Integer>();
		paths[source] = source;
		marked[source] = true;
		q.enqueue(source);
		// update path with source for index=source and mark it visited, enqueue
		// in to the stack
		// to the stack
		while (!q.isEmpty()) {
			int v = q.dequeue();
			for (int w : graph.neighbors(v)) {
				if (!marked[w]) {
					paths[w] = v;
					marked[w] = true;
					q.enqueue(w);
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
		if (!hasPathFromSource(v))
			return null;
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

	// ------- DO NOT MODIFY BELOW THIS LINE -------//

	/**
	 * Unit test main for the BFS class.
	 * 
	 * @param args
	 * @throws java.io.FileNotFoundException
	 */
	public static void main(String[] args) throws java.io.FileNotFoundException {
		if (args.length != 2) {
			String u = "Usage: BFS <filename> <source>";
			Stdio.println(u);
			return;
		}

		String fileName = args[0];
		int source = Integer.parseInt(args[1]);
		Graph graph = GraphFactory.make(fileName);

		BFS bfs = new BFS(graph, source);
		Stdio.println("Paths to source: " + source);
		for (int vertexId = 0; vertexId < graph.V(); vertexId++) {
			Stdio.print("  path for " + vertexId + " : ");
			if (bfs.hasPathFromSource(vertexId)) {
				for (int pathVertex : bfs.pathFromSource(vertexId)) {
					Stdio.print("->" + pathVertex);
				}
			}
			Stdio.println("");
		}
	}
}
