package fr.epsi.i4.nao.front;

import java.awt.*;

public class FrontBoard extends GridLayout {

	private int width;

	private int height;

	public FrontBoard(int width, int height) {
		this.width = width;
		this.height = height;
		this.setColumns(width);
		this.setRows(height);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
