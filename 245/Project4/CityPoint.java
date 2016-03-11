/**
 * Provides access to the city point
 * 
 * @author CaylaR
 * 
 */
public class CityPoint implements Comparable<CityPoint> {

	public CityPoint previous;
	public double minDist = Double.POSITIVE_INFINITY;
	public final String cityName;
	public Edge adjacencies = null;

	/**
	 * Constructor for the Point specified
	 * 
	 * @param city
	 */
	public CityPoint(String city) {
		cityName = city;
	}

	/**
	 * Comparable method to compare one city to another
	 */
	public int compareTo(CityPoint city) {
		return Double.compare(minDist, city.minDist);
	}

	/**
	 * returns a string representation of the city
	 */
	public String toString() {
		return cityName;
	}
}