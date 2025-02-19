package ninjabrainbot.util;

import java.util.List;

public interface ISet<T> extends Iterable<T> {

	/**
	 * @return true if something was added to the set.
	 */
	public boolean add(T t);

	/**
	 * @return true if something was added to the set.
	 */
	public boolean insert(T t, int index);

	public void remove(T t);

	public void clear();

	public int size();

	public T get(int index);

	public List<T> toList();

	public void setFromList(List<T> list);

	public default T getLast() {
		return get(size() - 1);
	}

}
