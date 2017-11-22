import fr.epsi.i4.nao.model.Node;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Node board = Node.generateGraph(4, 4);

        List<Node> nodes = Node.getExistingNodes();

        System.out.println(nodes);

    }
}
