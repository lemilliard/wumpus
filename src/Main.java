import fr.epsi.i4.nao.back.model.Board;
import fr.epsi.i4.nao.back.model.old.Node;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(4, 5);

        System.out.println(board);
    }

    public static void oldMain() {
        Node board = Node.generateGraph(4, 4);

        List<Node> nodes = Node.getExistingNodes();

        System.out.println(nodes);
    }
}
