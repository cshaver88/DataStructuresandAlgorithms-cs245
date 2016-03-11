import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class Dijkstra {

	private int sourceVertex = -1; // store the id of the source vertex
	private MapGraph graph;
	HashMap table;
	private int cityNum = 0;

	Dijkstra(String filename) {
		loadGraph(filename);
	}

	public MapGraph getGraph() {
		return graph;
	}

	/**
	 * Compute all the shortest paths from the source vertex to all the other
	 * vertices in the graph; This function is called from GUIApp, when the user
	 * clicks on the city.
	 */
	public void computePaths(CityNode vSource) {
		this.sourceVertex = table.iD(vSource.getCity(), cityNum);

		for (CityNode node : graph.nodes) {
			node.getVertex().minDist = Double.POSITIVE_INFINITY;
			node.getVertex().previous = null;
		}
		vSource.getVertex().minDist = 0.;

		PriorityQueue queue = new PriorityQueue(cityNum);
		CityPoint targetCity = vSource.getVertex();

		queue.insert(table.iD(targetCity.cityName, cityNum),
				(int) targetCity.minDist);
		while (queue.length > 0) {

			CityPoint point = graph.nodes[queue.removeSmallest()].getVertex();

			Edge edge = point.adjacencies;
			while (edge != null) {

				CityPoint city = edge.targetCity;
				double cityCost = edge.cost;
				double distance = point.minDist + cityCost;
				if (distance < city.minDist) {

					city.minDist = distance;
					if (queue.contains(table.iD(city.cityName, cityNum))) {
						queue.reduceKey(table.iD(city.cityName, cityNum),
								(int) distance);
					} else {
						queue.insert(table.iD(city.cityName, cityNum),
								(int) distance);
					}
					city.previous = point;
				}
				if (edge.next == null) {
					break;
				}
				edge = edge.next;
			}
		}
	}

	/**
	 * Returns the shortest path between the source vertex and this vertex.
	 * Returns the array of node id-s on the path
	 */
	public ArrayList<Integer> shortestPath(CityNode targetCity) {
		ArrayList<Integer> road = new ArrayList<>();

		for (CityPoint point = targetCity.getVertex(); point != null; point = point.previous) {
			road.add(table.iD(point.cityName, cityNum));
		}
		Collections.reverse(road);
		return road;
	}

	/**
	 * Loads graph info from the text file into MapGraph graph
	 * 
	 * @param filename
	 */
	public void loadGraph(String filename) {

		CityNode[] node = null;
		String[] city = null;

		try (BufferedReader reader = new BufferedReader(
				new FileReader(filename))) {
			while (reader.ready()) {
				if (reader.readLine().equals("NODES")) {
					cityNum = Integer.parseInt(reader.readLine());
					node = new CityNode[cityNum];
					city = new String[cityNum];
					for (int i = 0; i < cityNum; i++) {
						String[] citySplit = reader.readLine().split(" ");
						node[i] = new CityNode(citySplit[0],
								Double.parseDouble(citySplit[1]),
								Double.parseDouble(citySplit[2]));
						city[i] = citySplit[0];
					}
				}

				table = new HashMap(cityNum);
				table.insert(city);
				graph = new MapGraph(cityNum);
				for (CityNode n : node) {
					graph.addNode(n);
				}
				if (reader.readLine().contains("ARC")) {
					while (reader.ready()) {

						String[] ARC_Line = reader.readLine().split("\\s");
						int id = table.iD(ARC_Line[0], cityNum);

						graph.addEdge(
								id,
								new Edge(graph.nodes[table.iD(ARC_Line[1],
										cityNum)].getVertex(), Integer
										.parseInt(ARC_Line[2])));

						graph.addEdge(
								table.iD(ARC_Line[1], cityNum),
								new Edge(graph.nodes[id].getVertex(), Integer
										.parseInt(ARC_Line[2])));
					}

				}
			}
		} catch (Exception e) {
			System.err.println("An error has occurred" + e);
		}
	}

	public static void main(String[] args) {

		Dijkstra newDijkstra = new Dijkstra(args[0]);
		GUIApp newProgram = new GUIApp(newDijkstra);
	}

}