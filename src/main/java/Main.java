import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.front.Game;

import static fr.epsi.i4.back.model.Mode.AUTO;

public class Main {

	public static void main(String[] args) {
		Board board = new Board(5, 5, 15, false);

		Game game = new Game(board, AUTO, 10, 100, 10000000, false, false);
		game.play();
	}
}
