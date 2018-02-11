package fr.epsi.i4.front;

import javax.swing.*;

import fr.epsi.i4.back.model.board.Board;

public class FrontBoard extends JPanel {

	private final Board board;

	public FrontBoard(Board board) {
		this.board = board;
		setLayout(new FrontBoardGrid(board.getWidth(), board.getHeight()));
		setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
	}

	public FrontBoardGrid getBoard() {
		return (FrontBoardGrid) getLayout();
	}

	public void refresh() {
		removeAll();
		for (int y = board.getHeight() - 1; y > -1; y--) {
			for (int x = 0; x < board.getWidth(); x++) {
				FrontCase frontCase = new FrontCase(board.getCase(x, y));
				add(frontCase);
				//				frontCase.validate();
				//				frontCase.repaint();
			}
		}
		validate();
		repaint();
	}
}
