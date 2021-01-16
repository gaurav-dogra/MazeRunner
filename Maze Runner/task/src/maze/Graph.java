package maze;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    List<Node> nodes = new ArrayList<>();
    Node start;

    public void addNode(String label) {
        Node node = new Node(label);
        addNode(node);
    }

    private Graph createPath(String from, String to) {
        Graph pathGraph = new Graph();
        Set<String> visited = new HashSet<>();
        visited.add(from);
        Node baseNode =  findNode(from);
        Queue<Node> allNodes = baseNode.adjacencyList;

        for (Node node : nodes) {

        }
        return pathGraph;
    }

    @Override
    public boolean equals(Object obj) {
        List<Edge> graph1Edges = new ArrayList<>();
        for (Node node : nodes) {
            graph1Edges.addAll(node.adjacencyList);
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Graph graph2 = (Graph) obj;
        List<Edge> graph2Edges = new ArrayList<>();

        for (Node node : graph2.nodes) {
            graph2Edges.addAll(node.adjacencyList);
        }

        return graph1Edges.containsAll(graph2Edges);
    }

    private void addNode(Node node) {
        if (nodes.isEmpty()) {
            start = node;
        }
        nodes.add(node);
    }

    private void addLink(Node from, Node to, int weight) {
        from.addLink(to, weight);
        to.addLink(from, weight);
    }

    public void addLink(String from, String to, int weight) {
        Node fromNode = findNode(from);
        Node toNode = findNode(to);

        if (fromNode == null || toNode == null) {
            throw new IllegalArgumentException();
        }

        addLink(fromNode, toNode, weight);
    }

    private Node findNode(String label) {
        return nodes.stream()
                .filter(node -> node.label.equals(label))
                .findAny()
                .orElse(null);
    }

    public Graph buildMST() {
        Graph mst = new Graph();
        Set<String> visited = new HashSet<>();
        int edgeCount = 0;

        mst.addNode(start.label);
        visited.add(start.label);

        PriorityQueue<Edge> queue = new PriorityQueue<>(start.adjacencyList);

        while (!queue.isEmpty() && edgeCount != nodes.size() - 1) {
            Edge edge = queue.poll();

            if (visited.contains(edge.to.label)) {
                continue;
            }

            mst.addNode(edge.to.label);
            mst.addLink(edge.from.label, edge.to.label, edge.weight);
            edgeCount++;

            visited.add(edge.to.label);
            queue.addAll(edge.to.adjacencyList);
        }

        return mst;
    }

    public boolean isConnected(String from, String to) {
        Node fromNode = findNode(from);
        if (fromNode == null)
            throw new IllegalArgumentException();

        Optional<Edge> connection = fromNode.adjacencyList
                .stream()
                .filter(edge -> edge.to.label.equals(to))
                .findAny();

        return connection.isPresent();
    }
    
    // Works correctly for any graph
    public int getTotalGraphWeight() {
        return nodes.stream()
                .flatMap(node -> node.adjacencyList.stream())
                .mapToInt(edge -> edge.weight)
                .sum() / 2;
    }

    @Override
    public String toString() {
        return nodes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
    }

    private class Node {
        final String label;
        final List<Edge> adjacencyList = new ArrayList<>();

        private Node(String label) {
            this.label = label;
        }

        private void addLink(Node other, int weight) {
            adjacencyList.add(new Edge(this, other, weight));
        }

        @Override
        public String toString() {
            return label + ": " + adjacencyList;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }

    private class Edge implements Comparable<Edge> {
        Node from;
        Node to;
        int weight;

        public Edge(Node from, Node to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return from.label + " --" + weight + "-> " + to.label;
        }

        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Edge edge = (Edge) o;

            if (weight != edge.weight) return false;
            if (!this.from.label.equals(edge.from.label)) return false;
            return to.label.equals(edge.to.label);
        }

        @Override
        public int hashCode() {
            int result = from.hashCode();
            result = 31 * result + to.hashCode();
            result = 31 * result + weight;
            return result;
        }
    }

}



