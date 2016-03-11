public class HashMap {

	int size;
	BabyHash[] table;
	BabyHash first;
	BabyHash last;

	public HashMap(int length) {

		table = new BabyHash[length];
		this.size = length;
	}
	
	public int iD(String city, int length) {
		long hash = hash(city, length);

		for (BabyHash node = table[(int) hash]; node != null; node = node.next) {
			if (city.compareTo(node.city) == 0) {
				return node.iD;
			}
		}
		return -1;
	}

	static long hash(String node, int tablelength) {
		long hash = 0;
		int index;

		for (index = 0; index < node.length(); index++) {
			hash = (hash << 4) + node.charAt(index);
		}

		return hash % tablelength;
	}

	public void insert(String[] cityArray) {

		for (int j = 0; j < cityArray.length; j++) {
			int i = (int) hash(cityArray[j], cityArray.length);
			if (table[i] == null) {
				table[i] = new BabyHash(j, cityArray[j]);
			} else {
				BabyHash node = table[i];
				while (node.next != null) {
					node = node.next;
				}
				node.next = new BabyHash(j, cityArray[j]);
			}
		}
	}

	public class BabyHash {

		int iD;
		String city;
		BabyHash next;

		public BabyHash(int cityId, String cityName) {

			iD = cityId;
			city = cityName;
			next = null;
		}

		public String print() {
			return city;
		}

	}

}