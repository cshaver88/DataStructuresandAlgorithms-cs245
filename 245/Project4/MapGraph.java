/** A class representing a graph. Stores an array of nodes and adjacency list.
 * 
 */
import java.awt.Point;

public class MapGraph {

	public CityNode[] nodes;

	private Edge[] adjacencyList;

	private int numNodes = 0;
	private int numEdges = 0;

	public final int EPS_DIST = 5;

	MapGraph(int numNodes) {
		this.numNodes = numNodes;
		nodes = new CityNode[numNodes];
		adjacencyList = new Edge[numNodes];
	}

	/**
	 * Returns a node with index i
	 */
	CityNode getNode(int i) {
		return nodes[i];
	}

	/**
	 * Returns the head of the linked list of edges for a vertex with id = i
	 */
	Edge getEdge(int i) {
		return adjacencyList[i];
	}

	/**
	 * Adds a node to the graph
	 * 
	 * @param node
	 */
	public void addNode(CityNode node) {
		for (int i = 0; i < numNodes; i++) {
			if (nodes[i] == null) {
				nodes[i] = node;
				break;
			}
		}
	}

	public int numNodes() {
		return numNodes;
	}

	/**
	 * Adds the edge to the linked list for this vertexId
	 * 
	 * @param vertexId
	 * @param edge
	 */
	public void addEdge(int nodeId, Edge edge) {
		if (nodes[nodeId].getVertex().adjacencies == (null)) {
			nodes[nodeId].getVertex().adjacencies = edge;
		} else {
			edge.next = nodes[nodeId].getVertex().adjacencies;
			nodes[nodeId].getVertex().adjacencies = edge;
		}

	}

	/**
	 * Given the location of the click, return the node of the graph at this
	 * location.
	 */
	public CityNode getVertex(Point loc) {
		for (CityNode node : nodes) {
			Point p = node.getLocation();
			if (Math.abs(loc.x - p.x) < EPS_DIST
					&& Math.abs(loc.y - p.y) < EPS_DIST) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Returns the array of all edges for drawing: each element in the array
	 * corresponds to one edge and is the array of two Point objects
	 * (corresponding to the locations of the two nodes connected by this edge).
	 */
	public Point[][] getEdges() {
		Point[][] edges = new Point[numEdges][2];

		return edges;

	}

	/**
	 * Returns the array of nodes as points. Used by MapGraph to draw little
	 * circles at the location of the nodes
	 */
	public Point[] getNodeLocations() {
		Point[] location = new Point[numNodes];
		for (int i = 0; i < numNodes; i++) {
			location[i] = nodes[i].getLocation();
		}
		return location;
	}

	/**
	 * Returns the array of cities corresponding to the vertices of this graph
	 * in the array
	 * 
	 * @return
	 */
	public String[] getCities() {
		String[] cities = new String[numNodes];
		for (int i = 0; i < numNodes; i++) {
			cities[i] = nodes[i].getCity();
		}

		return cities;
	}

} // class MapGraph