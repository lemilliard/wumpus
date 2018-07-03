package fr.epsi.i4.front;

import fr.epsi.i4.back.model.board.Board;

import javax.swing.*;
import java.awt.*;

public class FrontGame extends JPanel {

	private final Board board;

	public FrontGame(Board board) {
		this.board = board;
		setLayout(new FrontBoard(board.getWidth(), board.getHeight()));
		setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
	}

	public FrontBoard getBoard() {
		return (FrontBoard) getLayout();
	}

	public void init() {
		removeAll();
		FrontCase frontCase;
		for (int y = board.getHeight() - 1; y > -1; y--) {
			for (int x = 0; x < board.getWidth(); x++) {
				frontCase = new FrontCase(board.getCase(x, y));
				add(frontCase);
			}
		}
		validate();
		repaint();
	}

	public void refresh() {
		FrontCase frontCase;
		for (Component component : getComponents()) {
			frontCase = (FrontCase) component;
			if (frontCase.getaCase().getId() == board.getAgentCase().getId()
					|| (board.getAgent().getCasePrecedente() != null
					&& frontCase.getaCase().getId() == board.getAgent().getCasePrecedente().getId())) {
				frontCase.refresh(frontCase.getaCase());
				validate();
				repaint();
			}
		}
	}
}
