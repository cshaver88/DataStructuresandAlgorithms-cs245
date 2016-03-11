public class Driver {

	public static void main(String[] args) {
		Dictionary a = new Dictionary("words_ospd.txt");
		// a.add("application");
		// a.add("apple");
		// a.add("ape");
		// a.add("cat");
		// a.add("catacomb");
		// a.add("catastrophe");
		// System.out.println(a.check("apple"));
		// a.add("cats");
		// a.add("hamster");
		// a.add("hamburger");
		// System.out.println(a.root.prefix);
		// System.out.println(a.root.children[0].prefix);
		// System.out.println(a.root.children[1].prefix);
		// System.out.println(a.root.prefix);
		// a.printTree();

		// System.out.println(a.root.children[2].prefix);
		// System.out.println(a.checkPrefix("ap"));
		a.printTree();
		// String[] list = a.suggest("cata", 10);
		// for (String item : list) {
		// System.out.println(item);
		// }

		// a.print();
	}
}