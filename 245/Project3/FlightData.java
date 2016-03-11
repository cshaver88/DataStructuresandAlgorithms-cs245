public class FlightData {

	/**
	 * Creates the Data portion of the flightKey
	 */
	// should contain flightNumber and price.
	private String flightNumber;
	private int price;

	public FlightData() {
		this.flightNumber = "";
		this.price = 0;
	}

	public FlightData(String flight, int price) {
		this.flightNumber = flight;
		this.price = price;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String toString() {
		return flightNumber + ", " + price;
	}

}