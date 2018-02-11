package fr.epsi.i4.front;

import java.awt.*;

public class FrontBoardGrid extends GridLayout {

	private int width;

	private int height;

	public FrontBoardGrid(int width, int height) {
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
