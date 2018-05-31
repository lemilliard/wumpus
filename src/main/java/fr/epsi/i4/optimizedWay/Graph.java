package fr.epsi.i4.optimizedWay;

import java.util.ArrayList;

public class Graph {
	private Node[] nodes;
	private int noOfNodes;
	//private Edge[] edges;
	private ArrayList<Edge> listEdges;
	private int noOfEdges;

	public Graph(ArrayList<Edge> listEdges) {
		this.listEdges = listEdges;
		// create all nodes ready to be updated with the edges
		this.noOfNodes = calculateNoOfNodes(this.listEdges);
		this.nodes = new Node[this.noOfNodes];
		for (int n = 0; n < this.noOfNodes; n++) {
			this.nodes[n] = new Node();
		}
		// add all the edges to the nodes, each edge added to two nodes (to and from)
		this.noOfEdges = listEdges.size();
		for (int edgeToAdd = 0; edgeToAdd < this.noOfEdges; edgeToAdd++) {
			this.nodes[this.listEdges.get(edgeToAdd).getFromNodeIndex()].getEdges().add(this.listEdges.get(edgeToAdd));
			this.nodes[this.listEdges.get(edgeToAdd).getToNodeIndex()].getEdges().add(this.listEdges.get(edgeToAdd));
		}
	}
	private int calculateNoOfNodes(ArrayList<Edge> edges) {
		int noOfNodes = 0;
		for (Edge e : edges) {
			if (e.getToNodeIndex() > noOfNodes)
				noOfNodes = e.getToNodeIndex();
			if (e.getFromNodeIndex() > noOfNodes)
				noOfNodes = e.getFromNodeIndex();
		}
		noOfNodes++;
		return noOfNodes;
	}
	// next video to implement the Dijkstra algorithm !!!
	public void calculateShortestDistances() {
		// node 0 as source
		this.nodes[8].setDistanceFromSource(0);
		int nextNode = 8;
		// visit every node
		for (int i = 8; i < this.nodes.length; i++) {
			// loop around the edges of current node
			ArrayList<Edge> currentNodeEdges = this.nodes[nextNode].getEdges();
			for (int joinedEdge = 0; joinedEdge < currentNodeEdges.size(); joinedEdge++) {
				int neighbourIndex = currentNodeEdges.get(joinedEdge).getNeighbourIndex(nextNode);
				// only if not visited
				if (!this.nodes[neighbourIndex].isVisited()) {
					int tentative = this.nodes[nextNode].getDistanceFromSource() + currentNodeEdges.get(joinedEdge).getLength();
					if (tentative < nodes[neighbourIndex].getDistanceFromSource()) {
						nodes[neighbourIndex].setDistanceFromSource(tentative);
					}
				}
			}
			// all neighbours checked so node visited
			nodes[nextNode].setVisited(true);
			// next node must be with shortest distance
			nextNode = getNodeShortestDistanced();
		}
	}
	// now we're going to implement this method in next part !
	private int getNodeShortestDistanced() {
		int storedNodeIndex = 0;
		int storedDist = Integer.MAX_VALUE;
		for (int i = 8; i < this.nodes.length; i++) {
			int currentDist = this.nodes[i].getDistanceFromSource();
			if (!this.nodes[i].isVisited() && currentDist < storedDist) {
				storedDist = currentDist;
				storedNodeIndex = i;
			}
		}
		return storedNodeIndex;
	}
	// display result
	public void printResult(int idGold) {
		String output = "Number of nodes = " + this.noOfNodes;
		output += "\nNumber of edges = " + this.noOfEdges;
		for (int i = 8; i < this.nodes.length; i++) {
			output += ("\nThe shortest distance from node 8 to node " + i + " is " + nodes[i].getDistanceFromSource());
		}
		System.out.println(output);

		String outputGold = "";
		for (int i = 8; i < this.nodes.length; i++) {
			if (i == idGold) {
				outputGold = "\nThe shortest distance from node 8 to node (Gold) " + i + " is " + nodes[i].getDistanceFromSource();
			}
		}
		System.out.println(outputGold);
		System.out.println("");
	}
	public Node[] getNodes() {
		return nodes;
	}
	public int getNoOfNodes() {
		return noOfNodes;
	}
	public ArrayList<Edge> getEdges() {
		return listEdges;
	}
	public int getNoOfEdges() {
		return noOfEdges;
	}
}
