import java.util.NoSuchElementException;

//import MyMultiKeyList.Link;
//import MyMultiKeyList.MyIterator;

public class MyMultiKeyList implements MultiKeyList {

	private Link head;
	private int numKeys, linkcount;
	public Link dummy;

	// public Link current;

	public MyMultiKeyList(int num) {
		numKeys = num;
		dummy = new Link(null, new Comparable[numKeys]);
		head = dummy;
		// current = head;
		linkcount = 0;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	// Adds an object to the list. If the length of keys is not the same
	// as the number of keys in the list, throw an IllegalArgumentException
	public void add(Comparable[] keys, Object elem) {
		if (keys.length != numKeys) {
			throw new IllegalArgumentException();
		}

		Link newLink = new Link(elem, keys);
		linkcount++;
		// for (int i = 0; i < keys.length ; i ++){
		// newLink.setKey(i, keys[i]);
		// }
		for (int i = 0; i < keys.length; i++) {
			if (head.getnext(i) == null) {
				head.setnext(i, newLink);
				newLink.setprevious(i, head);
			} else {
				MyIterator iterator = new MyIterator(0, i);
				while (iterator.hasNext()) {
					Link current = iterator.next();
					if (newLink.key(i).compareTo(current.key(i)) <= 0) {
						Link temp = current.getprevious(i);
						newLink.setprevious(i, current.getprevious(i));
						newLink.setnext(i, current);
						current.setprevious(i, newLink);
						temp.setnext(i, newLink);

						break;
					} else if (current.getnext(i) == null) {
						current.setnext(i, newLink);
						newLink.setprevious(i, current);
						break;
					}
				}
			}
		}
	}

	public void delete(Link index) {

		for (int i = 0; i < numKeys; i++) {
			if (index.getnext(i) == null) {
				index.getprevious(i).setnext(i, null);
			} else if (index.getprevious(i) == null) {
				index.getnext(i).setprevious(i, null);
			} else {
				Link prev = index.getprevious(i);
				prev.setnext(i, index.getnext(i));
				index.getnext(i).setprevious(i, prev);
			}
		}
	}

	@Override
	// Get an iterator to iterate over a particular key. If keyIndex is not
	// within the range of allowed keys, throw an IllegalArgumentException
	public MultiKeyListIterator iterator(int keyIndex) {
		if (keyIndex >= numKeys) {
			throw new IllegalArgumentException();

		} else {
			MyIterator iter = new MyIterator(0, keyIndex);
			return iter;
		}
	}

	@Override
	// Get an interface to the element at a particular index of the list. If
	// keyIndex is not
	// within the range of allowed keys, throw an IllegalArgumentException
	public Link get(int index, int keyIndex) { // index == num used to iterate
												// through the list keyIndex ==
												// apple banana...
		Link linkIWantBack = head;
		if (index >= linkcount) {
			throw new IllegalArgumentException();
		}
		if (keyIndex >= numKeys) {
			throw new IllegalArgumentException("Index out of Range!!");
		}
		for (int i = 0; i <= index; i++) {
			linkIWantBack = linkIWantBack.getnext(keyIndex);
		}
		return linkIWantBack;
	}

	@Override
	// Remove the ith element in the list using the given key index.
	public void removeIndex(int index, int keyIndex) {
		Link linkIWantToRemove = head;
		for (int i = 0; i <= index; i++) {
			linkIWantToRemove = linkIWantToRemove.getnext(keyIndex);
		}
		delete(linkIWantToRemove);
		linkIWantToRemove = null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	// Remove the element matching *all* keys == apple 5 and 2014
	public void remove(Comparable[] keys) {

		Link linkIWantToRemove = head;
		for (int k = 0; k <= linkcount - 1; k++) {
			linkIWantToRemove = linkIWantToRemove.getnext(k);
			while (linkIWantToRemove != null) {
				for (int i = 0; i <= numKeys - 1; i++) {
					System.out.println(i);
					if (linkIWantToRemove.key(k).compareTo(keys[i]) != 0) {
						break;
					}
					for (int j = 0; j <= numKeys - 1; j++) {
						delete(linkIWantToRemove);
					}
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	// Remove the element matching the key at the given index key is apple and
	// keyIndex is 0
	public void remove(Comparable key, int keyIndex) {

		Link linkIWantToRemove = head.getnext(keyIndex);

		while (linkIWantToRemove != null) {
			if (linkIWantToRemove.key(keyIndex).compareTo(key) == 0) {
				delete(linkIWantToRemove);
			}
			linkIWantToRemove = linkIWantToRemove.getnext(keyIndex);
		}
	}

	public class Link implements ListElem {

		Object data;
		@SuppressWarnings("rawtypes")
		Comparable[] keys;
		Link[] next;
		Link[] previous;

		@SuppressWarnings("rawtypes")
		Link(Object data, Comparable[] keys) {

			this.data = data;
			this.keys = new Comparable[keys.length];
			for (int i = 0; i < keys.length; i++) {
				this.keys[i] = keys[i];
			}
			this.next = new Link[numKeys];
			this.previous = new Link[numKeys];
		}

		@Override
		public Object data() {
			return data;
		}

		@Override
		public int numKeys() {
			return numKeys;
		}

		public Link getnext(int index) {
			return next[index];
		}

		public Link getprevious(int index) {
			return previous[index];
		}

		void setprevious(int index, Link prevarr) {
			previous[index] = prevarr;
		}

		void setnext(int index, Link nextarr) {
			next[index] = nextarr;
		}

		void setData(Object elem) {
			data = elem;
		}

		void setNumKeys(int num) {
			numKeys = num;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Comparable key(int index) {
			return keys[index];
		}

		@SuppressWarnings("rawtypes")
		void setKey(int index, Comparable xyz) {
			keys[index] = xyz;

		}
	}

	private class MyIterator implements MultiKeyListIterator {

		private Link current;
		private int key;
		private Link elemtoremove;

		public MyIterator(int index, int key) { // index is the key in which to
												// iterate over num now has no
												// use case 1 call next case 2
												// call previous case 3 call
												// delete after next and case4
												// call delete after previous

			this.key = key;
			current = head;
			elemtoremove = null;

			for (int i = 0; i < index; i++) {
				current = current.getnext(key);
			}
		}

		@Override
		// Returns true if there is a next element. If hasNext returns false,
		// then next should
		// throw a NoSuchElement exception
		public boolean hasNext() {
			if (current != null && current.getnext(key) != null) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		// Returns the next element in the list, and move the cursor position
		// forward
		// Throws a NoSuchElementException if there is no next element
		public Link next() {

			if (hasNext() == false) {
				throw new NoSuchElementException();
			} else {
				current = current.getnext(key);
				elemtoremove = current;
			}

			return current;
		}

		@Override
		// Returns true if there is a previous element. If hasPrevious returns
		// false, then previous should
		// throw a NoSuchElement exception
		public boolean hasPrevious() {
			return current == head ? false : true;
		}

		@Override
		// Returns the previous element in the list, and move the cursor
		// position backwards
		// Throws a NoSuchElementException if there is no previous element
		// Alternating calls to next and previous will return the same element
		public Link previous() {
			Link old;

			if (hasPrevious() == false) {
				throw new NoSuchElementException();
			} else {
				old = current;
				current = current.getprevious(key);
				elemtoremove = old;
			}

			return old;

		}

		@Override
		// Removes the element last returned by next() or previous(). If remove
		// is
		// called before next is called, or of remove is called twice in a row
		// without an intervening call to next or previous, then an
		// IllegalStateExeception is thrown
		public void remove() {
			Link next, previous;

			if (elemtoremove == null) {
				throw new IllegalStateException();
			}
			for (int i = 0; i < numKeys; i++) {
				previous = elemtoremove.getprevious(i);
				next = elemtoremove.getnext(i);
				previous.setnext(i, next);
				if (next != null) {
					next.setprevious(i, previous);
				}
			}
			current = current.getprevious(key);
			elemtoremove = null;
		}
	}
}