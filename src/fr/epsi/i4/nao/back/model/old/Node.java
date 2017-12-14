package fr.epsi.i4.nao.back.model.old;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tkint on 21/11/2017.
 */
public class Node {

    private int value;
    private Coordinate coordinate;
    private List<Node> neighbors;
    private static List<Node> existingNodes;

    public Node(int x, int y) {
        coordinate = new Coordinate(x, y);
        neighbors = new ArrayList<>();
        getExistingNodes().add(this);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", coordinate=" + coordinate +
                ", neighbors=" + neighbors.size() +
                "}\n";
    }

    public static Node generateGraph(int width, int height) {
        Node graph = new Node(0, 0);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Node node = getNodeByCoordinates(x, y);
                if (node == null) {
                    node = new Node(x, y);
                }
                node.createNeigborHorizontal(x - 1, width);
                node.createNeigborHorizontal(x + 1, width);
                node.createNeigborVertical(y - 1, height);
                node.createNeigborVertical(y + 1, height);
            }
        }

        return graph;
    }

    public static List<Node> getExistingNodes() {
        if (existingNodes == null) {
            existingNodes = new ArrayList<>();
        }
        return existingNodes;
    }

    private Node addNode(Node node) {
        neighbors.add(node);
        return node;
    }

    private static Node getNodeByCoordinates(int x, int y) {
        int i = 0;
        Node node = null;
        while (i < getExistingNodes().size() && node == null) {
            if (getExistingNodes().get(i).getCoordinate().getX() == x && getExistingNodes().get(i).getCoordinate().getY() == y) {
                node = getExistingNodes().get(i);
            }
            i++;
        }
        return node;
    }

    private Node createNeigborHorizontal(int x, int max) {
        Node node = null;
        if (x >= 0 && x < max) {
            node = getNodeByCoordinates(x, getCoordinate().getY());
            if (node == null) {
                node = new Node(x, getCoordinate().getY());
            }
            addNode(node);
        }
        return node;
    }

    private Node createNeigborVertical(int y, int max) {
        Node node = null;
        if (y >= 0 && y < max) {
            node = getNodeByCoordinates(getCoordinate().getX(), y);
            if (node == null) {
                node = new Node(getCoordinate().getX(), y);
            }
            addNode(node);
        }
        return node;
    }
}
