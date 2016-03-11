public class LinkedList {

	private LinkElem head = null;
	private LinkElem tail = null;

	/**
	 * Adds an element to the LinkedList
	 */
	public void add(int elem) {
		LinkElem newNode = new LinkElem(elem);
		if (head == null) {
			head = newNode;
			tail = head;
		} else {
			if (tail != null) {
				tail.setNext(newNode);
				newNode.setNext(null);
				tail = newNode;
			}
		}
	}

	/**
	 * Prints all the elements of the list
	 */
	public void printList() {
		LinkElem curr = head;
		while (curr != null) {
			System.out.print(curr.elem() + " ");
			curr = curr.next();
		}
		System.out.println();
	}

	/**
	 * Finds the middle element in the linked list that starts with link
	 */
	private static LinkElem findMiddle(LinkElem link) {

		LinkElem slow, fast;
		slow = link;
		fast = link.next();

		while ((fast != null) && fast.next() != null) {
			slow = slow.next();
			fast = fast.next().next();

		}
		return slow;
	}

	/**
	 * Merges two linked lists: one with head1 and the other with head2. Returns
	 * the head of the merged list.
	 */
	public static LinkElem mergeLists(LinkElem head1, LinkElem head2) {
		// fill in code
		LinkElem current = null;
		LinkElem temp = null;
		while (head1 != null && head2 != null) {
			if (head1.elem() <= head2.elem()) {
				if (temp == null) {
					temp = head1;
					current = head1;
				} else {
					temp.setNext(head1);
					temp = temp.next();
				}
				head1 = head1.next();
			} else {
				if (temp == null) {
					temp = head2;
					current = head2;
				} else {
					temp.setNext(head2);
					temp = temp.next();
				}
				head2 = head2.next();
			}
		}
		if (head1 != null) {
			temp.setNext(head1);
		} else {
			temp.setNext(head2);
		}
		return current;
	}

	//
	//
	// while(current1.next() != null || current2.next() != null){
	// if (current1.elem() <= current2.elem()) {
	// if (temp == null) {
	// return temp = current1;
	// }else{
	// temp.setNext(current1);
	// current1 = current1.next();
	// }
	// }
	// else{
	// if(temp == null){
	// return temp = current2;
	// }
	// else{
	// temp.setNext(current2);
	// current2 = current2.next();
	// }
	// }
	// }

	/**
	 * Recursively divides the LinkedList into two sublists: one that goes from
	 * the element "begLink" to the middle element of the list; and another one
	 * that goes from the element after the middle element and till the
	 * "endLink". Merges the lists using mergeLists method and returns the new
	 * head.
	 */
	public static LinkElem divideList(LinkElem begLink, LinkElem endLink) {
		// fill in code
		// use find middle to set the end of the begLink and set the head to
		// find middle.next() for endLink
		LinkElem head1, head2;
		if (begLink == endLink) {
			return begLink;
		} else {
			LinkElem middle = findMiddle(begLink);
			LinkElem temp = middle.next();
			middle.setNext(null);
			head1 = divideList(begLink, middle);
			// middle.setNext(temp);
			head2 = divideList(temp, endLink);
		}
		return mergeLists(head1, head2);
	}

	/**
	 * Sort this linked list using merge sort. This method should call
	 * divideList.
	 */
	public void mergeSort() {
		// fill in code
		head = divideList(head, tail);
	}

	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		int[] test = { 15, 24, 66, 18, 14, 55, 9, 1, 2012, 78, 22, 37 };
		for (int i = 0; i < test.length; i++) {
			list.add(test[i]);
		}
		list.printList();

		list.mergeSort();

		list.printList();

	}
}