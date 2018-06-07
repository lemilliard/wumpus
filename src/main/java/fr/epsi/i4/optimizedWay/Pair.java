package fr.epsi.i4.optimizedWay;

public class Pair<T,U> {
	private T key;
	private U value;

	public Pair(T key, U value) {
		this.key = key;
		this.value = value;
	}

	public T getKey() {
		return key;
	}

	public U getValue() {
		return value;
	}
}
