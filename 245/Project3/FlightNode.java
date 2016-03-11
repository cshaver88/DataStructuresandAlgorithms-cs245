public class FlightNode {
	/**
	 * Creates the nodes for the skip list and gets other information from the
	 * FlightKey and FlightData
	 */

	public FlightKey key;
	public FlightData data;
	public FlightNode next, previous, down, up;

	public FlightNode(FlightKey key, FlightData data) {
		this.key = key;
		this.data = data;
		this.next = null;
		this.previous = null;
		this.down = null;
		this.up = null;
	}

	public void setKey(FlightKey key) {
		this.key = key;
	}

	public void setData(FlightData data) {
		this.data = data;
	}

	public void setNext(FlightNode next) {
		this.next = next;
	}

	public void setPrevious(FlightNode previous) {
		this.previous = previous;
	}

	public void setDown(FlightNode down) {
		this.down = down;
	}

	public void setUp(FlightNode up) {
		this.up = up;
	}

	public FlightKey getKey() {
		return key;
	}

	public FlightData getData() {
		return data;
	}

	public FlightNode getNext() {
		return next;
	}

	public FlightNode getPrevious() {
		return previous;
	}

	public FlightNode getDown() {
		return down;
	}

	public FlightNode getUp() {
		return up;
	}

	public String toString() {
		return data.toString() + ", " + key.toString();
	}
}