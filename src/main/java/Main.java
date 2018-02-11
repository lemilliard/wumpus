import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.front.Game;

public class Main {

	public static void main(String[] args) {
		Board board = new Board(5, 5, 15);

		Game game = new Game(board);
		game.play();
	}
}
