package fr.epsi.i4.optimizedWay;

import java.util.*; // For HashMap

public final class Dijkstra {

    /**
     * Given a directed, weighted graph G and a source node s, produces the
     * distances from s to each other node in the graph.  If any nodes in
     * the graph are unreachable from s, they will be reported at distance
     * +infinity.
     *
     * @param graph  The graph upon which to run Dijkstra's algorithm.
     * @param source The source node in the graph.
     * @return A map from nodes in the graph to their distances from the source.
     */
    public static <T> Map<T, Double> shortestPaths(DirectedGraph<T> graph, T source) {
        /* Create a Fibonacci heap storing the distances of unvisited nodes
         * from the source node.
         */
        FibonacciHeap<T> pq = new FibonacciHeap<T>();

        /* The Fibonacci heap uses an internal representation that hands back
         * Entry objects for every stored element.  This map associates each
         * node in the graph with its corresponding Entry.
         */
        Map<T, FibonacciHeap.Entry<T>> entries = new HashMap<T, FibonacciHeap.Entry<T>>();

        /* Maintain a map from nodes to their distances.  Whenever we expand a
         * node for the first time, we'll put it in here.
         */
        Map<T, Double> result = new HashMap<T, Double>();

        /* Add each node to the Fibonacci heap at distance +infinity since
         * initially all nodes are unreachable.
         */
        for (T node : graph)
            entries.put(node, pq.enqueue(node, Double.POSITIVE_INFINITY));

        /* Update the source so that it's at distance 0.0 from itself; after
         * all, we can get there with a path of length zero!
         */
        pq.decreaseKey(entries.get(source), 0.0);

        /* Keep processing the queue until no nodes remain. */
        while (!pq.isEmpty()) {
            /* Grab the current node.  The algorithm guarantees that we now
             * have the shortest distance to it.
             */
            FibonacciHeap.Entry<T> curr = pq.dequeueMin();

            /* Store this in the result table. */
            result.put(curr.getValue(), curr.getPriority());

            /* Update the priorities of all of its edges. */
            for (Map.Entry<T, Double> arc : graph.edgesFrom(curr.getValue()).entrySet()) {
                /* If we already know the shortest path from the source to
                 * this node, don't add the edge.
                 */
                if (result.containsKey(arc.getKey()))
                    continue;

                /* Compute the cost of the path from the source to this node,
                 * which is the cost of this node plus the cost of this edge.
                 */
                double pathCost = curr.getPriority() + arc.getValue();

                /* If the length of the best-known path from the source to
                 * this node is longer than this potential path cost, update
                 * the cost of the shortest path.
                 */
                FibonacciHeap.Entry<T> dest = entries.get(arc.getKey());
                if (pathCost < dest.getPriority())
                    pq.decreaseKey(dest, pathCost);
            }
        }

        /* Finally, report the distances we've found. */
        return result;
    }
}










//package fr.epsi.i4.optimizedWay;
//
//import fr.epsi.i4.back.model.Agent;
//import fr.epsi.i4.back.model.board.Board;
//import fr.epsi.i4.back.model.board.Case;
//import fr.epsi.i4.back.model.board.content.Content;
//import fr.epsi.i4.back.model.board.content.Wumpus;
//
//import java.util.ArrayList;
//
//public class Dijkstra {
//
//    private final Board board;
//    private final Agent agent;
//    int toursUtilises = 0;
//    private int[] posPit;
//
//    public Dijkstra(Board board) {
//        this.board = board;
//        this.agent = new Agent(board);
//    }
//
//
//    //a finir
//    private Case posWumpus(Board board) {
//
//        ArrayList<Case> posWumpus = board.getCaseContent(Content.WUMPUS);
//
//        for (int i = 0; i < posWumpus.size(); i++) {
//            Case caseWumpus += caseWumpus;
//        }
//        return ;
//    }
//
////a changer
//    private int[] posPit(Board board)
//    {
//        int[] posPit = board.getCaseContent(Content.PIT);
//        int[] posPitX = new int[0];
//        int[] posPitY = new int[0];
//
//        for (int i = 0; i < posPit.length / 2; i++) {
//            posPitX[i] += posPit[i];
//        }
//
//        for (int i = posPit.length / 2; i < posPit.length; i++) {
//            posPitY[i] += posPit[i];
//        }
//
//        for (int i = 0; i < posPitX.length; i++) {
//            return board.getCase(posPitX[i], posPitY[i]);
//        }
//
//    }
//// a refaire
//    public int comparison() {
//        if (posAgentX == posWumpusX && posAgentY == posWumpusY)
//        {
//            toursUtilises += 1;
//        }
//
//        return toursUtilises +=1;
//    }
//}
