import fr.epsi.i4.back.model.Mode;
import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.front.Game;

import static fr.epsi.i4.back.model.Mode.AUTO;
import static fr.epsi.i4.back.model.Mode.MANUAL;

public class Main {

	public static void main(String[] args) {
		Board board = new Board(5, 5, 15);

		Game game = new Game(board, AUTO, 10, 90);
		game.play();
	}
}
