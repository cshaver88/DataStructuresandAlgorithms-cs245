@SuppressWarnings("rawtypes")
public class FlightKey implements Comparable {

	/**
	 * Creates the flight information for each flight
	 */
	public String origin, destination, date, time;

	public FlightKey() {
		this.origin = this.destination = this.date = this.time = "";
	}

	public FlightKey(String o, String d, String date, String t) {
		this.origin = o;
		this.destination = d;
		this.date = date;
		this.time = t;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDest() {
		return destination;
	}

	public void setDest(String destination) {
		this.destination = destination;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String toString() {
		return origin + ", " + destination + ", " + date + ", " + time;
	}

	/**
	 * Compares the FlightKey information already in the skiplist to the new
	 * item to insert
	 */
	@Override
	public int compareTo(Object other) {
		FlightKey key = (FlightKey) other;
		if (this.origin.compareTo(key.origin) == 0) {
			if (this.destination.compareTo(key.destination) == 0) {
				if (this.date.compareTo(key.date) == 0) {
					if (this.time.compareTo(key.time) == 0) {
						return 0;
					} else {
						return this.time.compareTo(key.time);
					}
				} else {
					return this.date.compareTo(key.date);
				}
			} else {
				return this.destination.compareTo(key.destination);
			}
		} else {
			return this.origin.compareTo(key.origin);
		}
	}
}