package fr.epsi.i4.optimizedWay;

import java.util.ArrayList;
import java.util.List;

public class HashList<T,U> {
	List<T> keys = new ArrayList<>();
	List<U> values = new ArrayList<>();

	public void addItem(T key, U value) {
		keys.add(key);
		values.add(value);
	}

	public void getItem(T key) {
		values.get(keys.indexOf(key));
	}

	public int size() {
		return keys.size();
	}

	public Pair<T,U> getPair(int index) {
		Pair<T,U> pair = new Pair<>(keys.get(index), values.get(index));
		return pair;
	}
}
