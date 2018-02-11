import fr.epsi.i4.back.model.board.Board;
import fr.epsi.i4.front.FrontGame;

public class Main {

	public static void main(String[] args) {
		Board board = new Board(5, 5, 15);

		FrontGame frontGame = new FrontGame(board);
		frontGame.play();
	}
}
