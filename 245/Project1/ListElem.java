public interface ListElem {
	public int numKeys();

	@SuppressWarnings("rawtypes")
	public Comparable key(int index);

	public Object data();
}