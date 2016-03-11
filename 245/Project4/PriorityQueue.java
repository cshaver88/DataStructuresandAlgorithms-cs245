public class PriorityQueue {
	private HeapNode[] mamaHeap;
	private int MAX;
	public int length;

	public PriorityQueue(int max) {
		MAX = max;
		mamaHeap = new HeapNode[MAX];
		length = 0;
		mamaHeap[0] = new HeapNode(Integer.MIN_VALUE, Integer.MIN_VALUE);
	}

	private int leftside(int position) {
		return 2 * position;
	}

	private int mama(int position) {
		return position / 2;
	}

	public boolean contains(int elem) {
		for (int i = 1; i < this.length; i++) {
			if (mamaHeap[i].elem == elem) {
				return true;
			}
		}
		return false;
	}

	public void insert(int element, int priority) {
		length++;
		HeapNode node = new HeapNode(element, priority);
		mamaHeap[length] = node;
		int current = length;

		while (mamaHeap[current].priority < mamaHeap[mama(current)].priority) {
			transfer(current, mama(current));
			current = mama(current);
		}
	}

	public void reduceKey(int element, int priority) {
		for (int i = 1; i < this.length; i++) {
			if (mamaHeap[i].elem == element) {
				mamaHeap[i].priority = priority;
				smash(1);
				break;
			}
		}
	}

	private boolean amIaLeaf(int position) {
		return ((position > length / 2) && (position <= length));
	}

	private void transfer(int position1, int position2) {
		HeapNode temporary;

		temporary = mamaHeap[position1];
		mamaHeap[position1] = mamaHeap[position2];
		mamaHeap[position2] = temporary;
	}

	private void smash(int position) {
		int smallestchild;
		while (!amIaLeaf(position)) {
			smallestchild = leftside(position);
			if ((smallestchild < length)
					&& (mamaHeap[smallestchild].priority > mamaHeap[smallestchild + 1].priority))
				smallestchild = smallestchild + 1;
			if (mamaHeap[position].priority <= mamaHeap[smallestchild].priority)
				return;
			transfer(position, smallestchild);
			position = smallestchild;
		}
	}

	public int removeSmallest() {
		transfer(1, length);
		length--;
		if (length != 0)
			smash(1);
		return mamaHeap[length + 1].elem;
	}

	public void printStuff() {
		int i;
		for (i = 1; i <= length; i++)
			System.out.print(mamaHeap[i] + " ");
		System.out.println();
	}

	public class HeapNode {
		int elem;
		int priority;

		HeapNode(int elem, int priority) {
			this.elem = elem;
			this.priority = priority;
		}
	}

}