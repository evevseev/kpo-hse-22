package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


/**
 * A graph data structure.
 * Do not use in real project, work is still in progress.
 * Only necessary methods for this project are implemented!
 *
 * @param <ValT> The type of the values stored in the graph.
 */
public class Graph<ValT> {
    private final HashMap<ValT, HashSet<ValT>> adjacencyList;

    /**
     * Creates a new graph.
     */
    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex The vertex to add.
     */
    public void addVertex(ValT vertex) {
        this.adjacencyList.put(vertex, new HashSet<>());
    }

    /**
     * Adds an edge to the graph.
     *
     * @param from The vertex to add the edge from.
     * @param to   The vertex to add the edge to.
     */
    public void addEdge(ValT from, ValT to) {
        this.adjacencyList.get(from).add(to);
    }

    /**
     * Returns the vertices of the graph.
     *
     * @return The vertices of the graph.
     */
    public HashSet<ValT> getVertices() {
        return new HashSet<>(this.adjacencyList.keySet());
    }

    /**
     * Returns the edges of the graph topologically sorted.
     *
     * @return The edgesof the graph topologically sorted.
     */
    public List<ValT> topologicalSort() {
        List<ValT> sortedList = new ArrayList<>();
        HashSet<ValT> visited = new HashSet<>();
        for (ValT vertex : this.adjacencyList.keySet()) {
            if (!visited.contains(vertex)) {
                topologicalSort(vertex, visited, sortedList);
            }
        }
        // reverse
        return sortedList;
    }

    private void topologicalSort(ValT vertex, HashSet<ValT> visited, List<ValT> sortedList) {
        visited.add(vertex);
        for (ValT neighbor : this.adjacencyList.get(vertex)) {
            if (!visited.contains(neighbor)) {
                topologicalSort(neighbor, visited, sortedList);
            }
        }
        sortedList.add(vertex);
    }

    /**
     * Returns first cycle found in the graph. If no cycle is found, null is returned.
     *
     * @return First cycle found in the graph.
     */
    public GraphCycle<ValT> findCycle() {
        HashSet<ValT> visited = new HashSet<>();
        HashSet<ValT> currentPath = new HashSet<>();

        for (ValT vertex : this.adjacencyList.keySet()) {
            if (!visited.contains(vertex)) {
                GraphCycle<ValT> cycle = findCycle(vertex, visited, currentPath);
                if (cycle != null) {
                    return cycle;
                }
            }
        }

        return null;
    }

    private GraphCycle<ValT> findCycle(ValT vertex, HashSet<ValT> visited, HashSet<ValT> currentPath) {
        visited.add(vertex);
        currentPath.add(vertex);

        for (ValT neighbor : this.adjacencyList.get(vertex)) {
            if (!visited.contains(neighbor)) {
                GraphCycle<ValT> cycle = findCycle(neighbor, visited, currentPath);
                if (cycle != null) {
                    return cycle;
                }
            } else if (currentPath.contains(neighbor)) {
                return new GraphCycle<>(vertex, neighbor);
            }
        }

        currentPath.remove(vertex);
        return null;
    }

    @Override
    public String toString() {
        return this.adjacencyList.toString();
    }
}

