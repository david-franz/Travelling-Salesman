import java.util.ArrayList;
import java.util.List;

public class CompleteGraph {

    public class Node {

        private final int ID;
        private final double x, y;
        private List<Node> outgoingNodes;

        private double averageDistToChildNodes;
        private boolean visited;

        public Node(int ID, double x, double y) {
            this.ID = ID;
            this.x = x;
            this.y = y;
            this.outgoingNodes = new ArrayList<>();
            this.visited = false;
        }

        public void addEdge(Node n) {
            outgoingNodes.add(n);
        }

        public void calculateAverageDist() {
            double totalDistToChildNodes = 0;
            for(Node n : outgoingNodes) {
                totalDistToChildNodes += calculateDistToNode(n);
            }
            averageDistToChildNodes = totalDistToChildNodes / outgoingNodes.size();
        }

        public double calculateDistToNode(Node n) {
            return Math.sqrt(Math.pow(this.x - n.x, 2) + Math.pow(this.y - n.y, 2));
        }

        public void markVisited() {
            visited = true;
        }

        public Node findNextNode(Node startNode) {
            // choose a random candidate for the nextnode (with condition that it hasn't been visited)
            Node nextNode = null;
            for(Node n : outgoingNodes) {
                if(!n.visited) {
                    nextNode = n;
                    break;
                }
            }

            // choose next node with smallest average distance to outgoing nodes
            for(Node n : outgoingNodes) {
                if(n.visited) continue; // only consider outgoing nodes that haven't been visited
                if(n.averageDistToChildNodes < nextNode.averageDistToChildNodes) {
                    nextNode = n;
                }
            }

            if(nextNode == null) nextNode = startNode;

            return nextNode;
        }

        public int getID() {
            return ID;
        }
    }

    private List<Node> nodes;

    public CompleteGraph() {
        nodes = new ArrayList<>();
    }

    public void addNode(int ID, double xCoordinate, double yCoordinate) {
        Node node = new Node(ID, xCoordinate, yCoordinate);
        nodes.add(node);
    }

    public void processNodes() {
        for(Node n1 : nodes) {
            for(Node n2 : nodes) {
                if(n1.equals(n2)) continue;
                n1.addEdge(n2);
            }
        }

        for(Node n : nodes) {
            n.calculateAverageDist();
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }
}
