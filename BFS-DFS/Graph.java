/**

 * @author  Prachi Gupta, G01025257
 * INFS 519
 * Fall 2016
 */
import java.util.Iterator;

/**
 * Graph class represents an undirected graph of vertices named 0 through V - 1.
 * It supports the following two primary operations: add an edge to the graph,
 * iterate over all of the vertices adjacent to a vertex. It also provides
 * methods for returning the number of vertices V and the number of edges E.
 * Parallel edges and self-loops are allowed in the graph.
 */
public class Graph {
	private int numVertices;
	private int numEdges;

	private Bag<Integer>[] vertices;

	/**
	 * Creates a new graph. Vertices are fixed. Edges can be added after
	 * creation but not removed.
	 * 
	 * @param numVertices
	 */
	public Graph(int numVertices) {
		// initialize no. of vertices passed and num of edges 
		this.numVertices = numVertices;
		this.numEdges = 0;
		//initialize the array of linked list
		vertices = (Bag<Integer>[]) new Bag[numVertices];
		for (int v = 0; v < numVertices; v++) {
			vertices[v] = new Bag<Integer>();
		}

	}

	/**
	 * Gets the number of vertices in the graph.
	 * 
	 * @return V
	 */
	public int V() {
		return numVertices;
	}

	/**
	 * Gets the number of edges in the graph.
	 * 
	 * @return E
	 */
	public int E() {
		return numEdges;
	}

	/**
	 * Gets iterator that enumerates the vertexId of the neighbors of given
	 * vertexId.
	 * 
	 * @param vertexId
	 * @return neighbor of vertexId
	 * @throws IndexOutOfBoundsException
	 *             if vertexId is invalid (less than 0, more than or equal to V)
	 */
	public Iterable<Integer> neighbors(final int vertexId) {
		// check for out of range vertice
		if (vertexId < 0 || vertexId >= numVertices)
			throw new IndexOutOfBoundsException("vertex " + vertexId + " is not between 0 and " + (numVertices - 1));
		return vertices[vertexId];
	}

	/**
	 * Adds an edge between v and w.
	 * 
	 * @param v
	 * @param w
	 * @throws IndexOutOfBoundsException
	 *             if v or w are invalid (less than 0, more than or equal to V)
	 */
	public void addEdge(int v, int w) {
		// check if both the vertices are out of range
		if (v < 0 || v >= numVertices)
			throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (numVertices - 1));
		if (w < 0 || w >= numVertices)
			throw new IndexOutOfBoundsException("vertex " + w + " is not between 0 and " + (numVertices - 1));
		// increment no. of edges and add value for index v with W and value for
		// index w as v marking edges between v and w
		numEdges++;
		vertices[v].add(w);
		vertices[w].add(v);

	}

	// ------- DO NOT MODIFY BELOW THIS LINE -------//

	/**
	 * Gets String facsimile of this graph.
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		String title = "V=" + this.V() + " E=" + this.E();
		buf.append(title);
		for (int vertexId = 0; vertexId < this.vertices.length; vertexId++) {
			Bag<Integer> neighbors = this.vertices[vertexId];
			String prefix = "\n[" + vertexId + "] neighbors=" + neighbors.size() + ": ";
			buf.append(prefix);
			boolean first = true;
			for (int neighborId : neighbors) {
				if (first)
					first = false;
				else
					buf.append(", ");
				buf.append(neighborId);
			}
		}
		return buf.toString();
	}

	/**
	 * Unit test main for the Graph class. Reads a file and prints out.
	 * 
	 * @param args
	 * @throws java.io.FileNotFoundException
	 */
	public static void main(String[] args) throws java.io.FileNotFoundException {
		String fileName = "graph.txt";
		Graph graph = GraphFactory.make(fileName);
		Stdio.println("Graph: " + graph);
	}
}
