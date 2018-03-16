package fr.epsi.i4.util;

import java.security.SecureRandom;

/**
 * Created by tkint on 15/12/2017.
 */
public class Randomizer {

	private int min;

	private int max;

	private int size;

	private int[] possibilities;

	public Randomizer(int min, int max) {
		this.min = min;
		this.max = max;
		this.size = max - min;
		this.possibilities = new int[size];
		for (int i = 0; i < size; i++) {
			this.possibilities[i] = min + i;
		}
	}

	private int randomIndex() {
		SecureRandom secureRandom = new SecureRandom();
		return secureRandom.nextInt(size);
	}

	public int randomize() {
		int rand = 0;
		if (size > 0) {
			shuffle();
			rand = possibilities[0];
		}
		return rand;
	}

	private void shuffle() {
		if (size > 1) {
			int first;
			int second = -1;
			for (int i = 0; i < size; i++) {
				first = randomIndex();
				while (second == -1 || second == first) {
					second = randomIndex();
				}
				swap(first, second);
			}
		}
	}

	private void swap(int i, int j) {
		possibilities[i] = possibilities[i] + possibilities[j];
		possibilities[j] = possibilities[i] - possibilities[j];
		possibilities[i] = possibilities[i] - possibilities[j];
	}
}
