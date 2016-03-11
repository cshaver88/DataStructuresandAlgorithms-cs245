public class Edge {
	
	public final CityPoint targetCity;
	public final double cost;
	public Edge next = null;

	public Edge(CityPoint target, double cityCost) {
		targetCity = target;
		cost = cityCost;
	}

	public String toString() {
		return this.targetCity.toString();
	}
}