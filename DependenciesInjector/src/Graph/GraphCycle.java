package Graph;

/**
 * A cycle in a graph.
 */
public class GraphCycle<ValT> {
    /**
     * The vertex that is part of the cycle.
     */

    public ValT vertex1;
    /**
     * The vertex that is part of the cycle.
     */
    public ValT vertex2;

    /**
     * Creates a new cycle.
     *
     * @param vertex1 The vertex that is part of the cycle.
     * @param vertex2 The vertex that is part of the cycle.
     */
    public GraphCycle(ValT vertex1, ValT vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }

    /**
     * Creates a copy of a cycle from an existing one.
     *
     * @param cycle The cycle to copy.
     */
    public GraphCycle(GraphCycle<ValT> cycle) {
        this.vertex1 = cycle.vertex1;
        this.vertex2 = cycle.vertex2;
    }
}
