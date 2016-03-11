import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * FlightList is a skip list data structure
 * 
 * @author CaylaR
 * 
 */

public class FlightList {
	public FlightNode beg;
	public FlightNode end;
	public FlightNode node;

	/**
	 * Constructor for an empty item
	 */
	public FlightList() {
		beg = new FlightNode(new FlightKey(" ", " ", "00/00/0000", "00:00"),
				new FlightData(" ", 0));
		end = new FlightNode(new FlightKey("~", "~", "99/99/9999", "99:99"),
				new FlightData("~", 9999));
		beg.setNext(end);
		end.setPrevious(beg);
	}

	/**
	 * Constructor for a filename as input
	 * 
	 * @param filename
	 */
	public FlightList(String filename) {
		beg = new FlightNode(new FlightKey(" ", " ", "00/00/0000", "00:00"),
				new FlightData(" ", 0));
		end = new FlightNode(new FlightKey("~", "~", "99/99/9999", "99:99"),
				new FlightData("~", 9999));

		beg.setNext(end);
		end.setPrevious(beg);

		try (BufferedReader reader = new BufferedReader(
				new FileReader(filename))) {
			while (reader.ready()) {
				String[] line = reader.readLine().split(" ");
				addLine(line);
			}
		} catch (Exception e) {
			System.err.println("An error has occurred with your file: "
					+ filename);
		}
	}

	/**
	 * Helper that splits up all the necessary information
	 * 
	 * @param line
	 */
	public void addLine(String[] line) {
		FlightKey key = new FlightKey(line[0], line[1], line[2], line[3]);
		FlightData data = new FlightData(line[4], new Integer(line[5]));
		this.insert(key, data);
	}

	/**
	 * Helper to generate a random number to see how many new levels to build
	 * when adding a new flight
	 * 
	 * @return
	 */
	private boolean random() {
		int height;
		Random randomNumber = new Random();
		for (height = 0; randomNumber.nextInt() % 2 == 0; height++) {
		}
		return height > 0;
	}

	/**
	 * Helper for insert this creates the tower and connectors for the new node
	 * 
	 * @see insert
	 * @param newNode
	 * @param bottom
	 * @param top
	 * @return
	 */

	private boolean insertHelper(FlightNode newNode, FlightNode bottom,
			FlightNode top) {
		FlightNode current = bottom;
		while (current != null) {
			if (newNode.getKey().compareTo(current.getKey()) < 0) {
				newNode.setPrevious(current.getPrevious());
				newNode.setNext(current);
				current.getPrevious().setNext(newNode);
				current.setPrevious(newNode);
				break;
			} else if (newNode.getKey().compareTo(current.getNext().getKey()) == 0) {
				if (newNode.getData().getFlightNumber()
						.equalsIgnoreCase(current.getData().getFlightNumber())
						&& newNode.getData().getPrice() == current.getData()
								.getPrice()) {
					return false;
				} else if (newNode.getKey().compareTo(
						current.getNext().getKey()) < 0) {
					newNode.setPrevious(current.getPrevious());
					newNode.setNext(current);
					current.getPrevious().setNext(newNode);
					current.setPrevious(newNode);
					break;
				} else {
					current = current.getNext();
				}
			} else {
				current = current.getNext();
			}
		}
		if (random()) {
			FlightNode upNode = new FlightNode(newNode.getKey(),
					newNode.getData());
			newNode.setUp(upNode);
			upNode.setDown(newNode);
			if (bottom.getUp() != null && top.getUp() != null) {
				insertHelper(upNode, bottom.getUp(), top.getUp());
			} else {
				FlightNode topL = new FlightNode(new FlightKey(" ", " ",
						"00/00/0000", "00:00"), new FlightData(" ", 0));
				FlightNode topR = new FlightNode(new FlightKey("~", "~",
						"99/99/9999", "99:99"), new FlightData("~", 9999));
				bottom.setUp(topL);
				top.setUp(topR);
				topL.setDown(bottom);
				topR.setDown(top);
				topL.setNext(topR);
				topR.setPrevious(topL);
				insertHelper(upNode, topL, topR);
			}
		}
		return true;
	}

	/**
	 * This inserts the new information where needed calls insertHelper
	 * 
	 * @see insertHelper
	 * @param key
	 * @param data
	 * @return
	 */
	public boolean insert(FlightKey key, FlightData data) {
		if (key.compareTo(beg.getKey()) < 0 || key.compareTo(end.getKey()) > 0) {
			return false;
		}
		FlightNode insert = new FlightNode(key, data);
		return insertHelper(insert, beg, end);

	}

	/**
	 * Helper for find helps to traverse the skip list
	 * 
	 * @see find
	 * @param key
	 * @param newNode
	 * @return
	 */
	public FlightNode findHelper(FlightKey key, FlightNode newNode) {
		if (newNode.getNext() == null) {
			if (newNode.getDown() == null)
				return newNode;
			return findHelper(key, newNode.getDown());
		}

		if (newNode.getNext().getKey().compareTo(key) > 0) {
			if (newNode.getDown() != null)
				return findHelper(key, newNode.getDown());
			else {
				return newNode;
			}
		} else {
			return findHelper(key, newNode.getNext());
		}
	}

	/**
	 * Finds the specified key uses findHelper
	 * 
	 * @see findHelper
	 * @param key
	 * @return
	 */
	public boolean find(FlightKey key) {
		FlightNode n = findHelper(key, beg);
		return (!n.getKey().equals(key));

	}

	/**
	 * Returns�a�list�of�nodes�that�have�the�same�origin�and�destination�cities�
	 * and�the�same� date� as� the� key,� with� departure� times� in�
	 * increasing� order� from� the� requested�departure� time.�
	 * 
	 * @param key
	 * @return
	 */
	public ArrayList<FlightNode> successors(FlightKey key) {
		FlightNode beginning = beg;
		boolean isHere = false;
		ArrayList<FlightNode> list = new ArrayList<FlightNode>();

		while (beginning.getNext().getKey() != null) {
			beginning = beginning.getNext();
			while ((beginning.getKey().getOrigin().equalsIgnoreCase(key
					.getOrigin()))
					&& (beginning.getKey().getDest().equalsIgnoreCase(key
							.getDest()))
					&& (beginning.getKey().getDate().equalsIgnoreCase(key
							.getDate()))) {
				isHere = true;
				if (Integer.parseInt(beginning.getKey().getTime()
						.substring(0, 2)
						+ beginning.getKey().getTime().substring(3)) > Integer
						.parseInt(key.getTime().substring(0, 2)
								+ key.getTime().substring(3))) {
					list.add(beginning);
				}
				beginning = beginning.getNext();

			}
			if (isHere) {
				break;
			}
		}

		return list;
	}

	/**
	 * Returns�a�list�of�nodes�that�have�the�same�origin�and�destination�cities�
	 * and�the�same� date� as� the� key,� with� departure� times� in�
	 * decreasing� order� from� the� requested� departure� time
	 * 
	 * @param key
	 * @return
	 */
	public ArrayList<FlightNode> predecessors(FlightKey key) {
		FlightNode beginning = beg;
		boolean isHere = false;
		ArrayList<FlightNode> list = new ArrayList<FlightNode>();

		while (beginning.getNext().getKey() != null) {
			beginning = beginning.getNext();
			while ((beginning.getKey().getOrigin().equalsIgnoreCase(key
					.getOrigin()))
					&& (beginning.getKey().getDest().equalsIgnoreCase(key
							.getDest()))
					&& (beginning.getKey().getDate().equalsIgnoreCase(key
							.getDate()))) {
				isHere = true;
				if (Integer.parseInt(beginning.getKey().getTime()
						.substring(0, 2)
						+ beginning.getKey().getTime().substring(3)) < Integer
						.parseInt(key.getTime().substring(0, 2)
								+ key.getTime().substring(3))) {
					list.add(beginning);
				}
				beginning = beginning.getNext();

			}
			if (isHere) {
				break;
			}
		}

		return list;
	}

	/**
	 * Returns�a�list�of�nodes�that�have�the�same�origin�and�destination�cities�
	 * and�the�same� date� as� the� key,� with� departure� times� within� the�
	 * timeDifference� of� the� departure� time�of� the� key
	 * 
	 * @param key
	 * @param timeDifference
	 * @return
	 */
	public ArrayList<FlightNode> findFlights(FlightKey key, int timeDifference) {
		FlightNode beginning = beg;
		boolean isHere = false;

		int top = Integer.parseInt(key.getTime().substring(0, 2))

		+ timeDifference;
		int bottom = Integer.parseInt(key.getTime().substring(0, 2))

		- timeDifference;

		if (top > 24) {
			top = 2400;
		}
		if (bottom < 0) {
			bottom = 00;
		}

		ArrayList<FlightNode> list = new ArrayList<FlightNode>();

		while (beginning.getNext().getKey() != null) {
			beginning = beginning.getNext();
			while ((beginning.getKey().getOrigin().equalsIgnoreCase(key
					.getOrigin()))
					&& (beginning.getKey().getDest().equalsIgnoreCase(key
							.getDest()))
					&& (beginning.getKey().getDate().equalsIgnoreCase(key
							.getDate()))) {
				isHere = true;

				if (Integer.parseInt(beginning.getKey().getTime()
						.substring(0, 2))

				>= bottom
						&& Integer.parseInt(beginning.getKey().getTime()
								.substring(0, 2))

						<= top) {
					list.add(beginning);

				}
				beginning = beginning.getNext();

			}
			if (isHere) {
				break;
			}
		}

		return list;
	}

	/**
	 * Prints�the�elements�of�the�skip�list��on�all�the�levels�starting�from�the
	 * �topmost�level.�
	 * 
	 */
	public void print() {

		FlightNode start = beg;
		while (start.getUp() != null) {
			start = start.getUp();
		}
		FlightNode newNode = start.getNext();
		int index = 1;
		System.out.println("The current level is: " + index);
		while (true) {
			while (newNode.getNext() != null) {
				System.out.println(newNode);
				newNode = newNode.getNext();
			}
			if (start.getDown() != null) {
				start = start.getDown();
				newNode = start.getNext();
				index++;
				System.out.println("\nThe current level is: " + index);
			} else {
				break;
			}
		}

	}

}