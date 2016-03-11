import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeMap;

public class Dictionary {

	public class Node {

		public String prefix;
		public boolean valid;
		public Node[] children;

		public Node(String prefix, boolean valid) {
			this.prefix = prefix;
			this.valid = valid;
			this.children = new Node[26];
		}
	}

	Node root;

	public Dictionary() {
		root = new Node("", false);
	}

	public Dictionary(String filename) {
		root = new Node("", false);
		try (BufferedReader reader = Files.newBufferedReader(
				Paths.get(filename), Charset.forName("UTF-8"));) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] words = line.split("\\s");
				for (String word : words) {
					if (!word.equals(" ")) {
						add(word);
					}
				}
			}
		} catch (IOException e) {
			System.err.println("An error has occurred with your file!! "
					+ filename.toString());
		}
	}

	/**
	 * Helper method for the general add function. This method takes in a word
	 * and traverses through all of the root nodes of the tree and adds in each
	 * word if it is not there.
	 * 
	 * 
	 * @see add(String word)
	 * @param tree
	 * @param word
	 * @return
	 */
	private Node add(Node tree, String word) {
		word = word.toLowerCase();
		if (tree == null) {
			Node newNode = new Node(word, true);
			return newNode;
		} else if (tree.prefix.equals(word)) {
			if (tree.valid == false) {
				tree.valid = true;
			}
			return tree;
		} else {
			String commonword = commonword(word, tree.prefix);
			if (commonword.equals(tree.prefix)) {
				String wordsuffix = word.substring(tree.prefix.length());
				int index = (int) wordsuffix.charAt(0) - (int) 'a';
				tree.children[index] = add(tree.children[index], wordsuffix);
				return tree;
			} else {
				Node commonNode;
				if (commonword.equals(word)) {
					commonNode = new Node(commonword, true);
				} else {
					commonNode = new Node(commonword, false);
				}
				String treesuffix = tree.prefix.substring(commonword.length());
				String wordsuffix = word.substring(commonword.length());
				tree.prefix = treesuffix;
				commonNode.children[(int) tree.prefix.charAt(0) - (int) 'a'] = tree;
				if (wordsuffix != null) {
					commonNode.children[(int) wordsuffix.charAt(0) - (int) 'a'] = add(
							commonNode.children[(int) wordsuffix.charAt(0)
									- (int) 'a'], wordsuffix);
				}
				return commonNode;
			}
		}
	}

	private String commonword(String word, String nextword) {
		String prefix = "";
		int len = Math.min(word.length(), nextword.length());
		for (int i = 0; i <= len - 1; i++) {
			if (word.substring(0, i + 1).equals(nextword.substring(0, i + 1))) {
				prefix = word.substring(0, i + 1);
			} else {
				return prefix;
			}
		}
		return prefix;
	}

	/**
	 * Add method for the user to enter a word into.
	 * 
	 * @param word
	 */
	void add(String word) {
		// add a word to the dictionary
		root = add(root, word);
	}

	/**
	 * Checks the word and goes through the tree to see if the word is in the
	 * dictionary, and returns true if it is, and false if it isn't.
	 * 
	 * @param word
	 * @return
	 */

	boolean check(String word) {
		// check to see if a word is in the dictionary
		return check(root, word);
	}

	private boolean check(Node tree, String word) {
		if (tree == null) {
			return false;
		}
		if (!word.startsWith(tree.prefix)) {
			return false;
		}
		if (word.equals(tree.prefix)) {
			if (tree.valid == false) {
				return false;
			} else {
				return true;
			}
		} else {
			String wordsuffix = word.substring(tree.prefix.length());
			return check(tree.children[(int) wordsuffix.charAt(0) - (int) 'a'],
					wordsuffix);
		}
	}

	/**
	 * Checks the prefix of a word to see if it is in the tree returns true if
	 * it is, and false if it isn't.
	 * 
	 * @param prefix
	 * @return
	 */

	boolean checkPrefix(String prefix) {
		// check to see if a prefix matches a word in the dictionary

		if (prefix.equals("")) {
			return true;
		}
		if (prefix.length() > 0) {
			return checkPrefix(
					root.children[(int) prefix.charAt(0) - (int) 'a'], prefix);
		}
		return false;
	}

	private boolean checkPrefix(Node tree, String prefix) {
		if (tree.prefix.equalsIgnoreCase(prefix)
				|| tree.prefix.startsWith(prefix)) {
			// if (tree.prefix.equalsIgnoreCase(prefix)) {
			return true;
			// }
		}
		if (prefix.startsWith(tree.prefix)) {
			String prefixsuffix = prefix.substring(tree.prefix.length());
			return checkPrefix(tree.children[(int) prefixsuffix.charAt(0)
					- (int) 'a'], prefixsuffix);
		}
		return false;
	}

	/**
	 * Prints out the entire contents of the tree in alphabetical order.
	 * 
	 */

	void print() {
		// print out the contents of the dictionary in alphabetical order one
		// word per line
		print(root, "");
	}

	void print(Node tree, String word) {
		if (tree.valid == true) {
			System.out.println(word + tree.prefix);
		}
		for (int i = 0; i < 26; i++) {
			if (tree.children[i] != null) {
				print(tree.children[i], word + tree.prefix);
			}
		}
	}

	/**
	 * Prints out the contents of the tree before sorting them.
	 */

	void printTree() {
		// print out the tree structure of the dictionary in pre-order
		printTree(root, -1);
	}

	void printTree(Node tree, int tab) {
		for (int i = 0; i < tab; i++) {
			System.out.print(" ");
		}
		System.out.print(tree.prefix);
		if (tree.valid == true) {
			System.out.println(" <T>");
		} else {
			System.out.println();
		}
		for (int i = 0; i < 26; i++) {
			if (tree.children[i] != null) {
				printTree(tree.children[i], tab + 1);
			}
		}
	}

	/**
	 * Goes through the entire tree and creates an array of entries that are
	 * similar to the word specified, then returns them.
	 * 
	 * @param word
	 * @param numSuggestions
	 * @return
	 */

	String[] suggest(String word, int numSuggestions) {
		// return an array of the entries in the dictionary that are as close as
		// possible to the word parameter
		String[] suggestions = new String[numSuggestions];
		suggestions = suggest(word, root, "", suggestions);
		return suggestions;
	}

	// put all words with the same first letter in a tree map suggest the words
	// with the longest common prefix
	// key is length of common prefix
	private TreeMap<Integer, ArrayList<String>> suggesthelp(Node tree,
			String word, String prefix,
			TreeMap<Integer, ArrayList<String>> suggest) {
		if (tree.valid == true) {
			String common = commonword(prefix + tree.prefix, word);
			Integer length = common.length();
			if (!suggest.containsKey(length)) {
				suggest.put(length, new ArrayList<String>());
			}
			suggest.get(length).add(prefix + tree.prefix);
		}
		for (int i = 0; i < 26; i++) {
			if (tree.children[i] != null) {
				suggesthelp(tree.children[i], word, prefix + tree.prefix,
						suggest);
			}
		}
		return suggest;
	}

	private String[] suggest(String word, Node tree, String prefix,
			String[] suggestions) {
		TreeMap<Integer, ArrayList<String>> suggest = new TreeMap<>();
		int count = 0;
		if (tree == null) {
			return null;
		}
		if (check(word)) {
			String[] words = { word };
			return words;
		} else {
			int index = (int) word.charAt(0) - (int) 'a';
			suggest = suggesthelp(tree.children[index], word, "", suggest);
			for (Integer key : suggest.descendingMap().keySet()) {
				for (int i = 0; i <= suggest.get(key).size() - 1; i++) {
					if (count < suggestions.length) {
						suggestions[count] = suggest.get(key).get(i);
						count++;
					} else {
						return suggestions;
					}
				}
			}
			return suggestions;
		}
	}
}