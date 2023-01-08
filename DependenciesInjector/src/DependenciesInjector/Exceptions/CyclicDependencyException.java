package DependenciesInjector.Exceptions;

import DependenciesGraph.PathVertex;
import Graph.GraphCycle;

/**
 * Exception thrown when a cyclic dependency is found.
 */
public class CyclicDependencyException extends CompilerException {

    private final GraphCycle<PathVertex> cycle;

    /**
     * Creates a new exception.
     *
     * @param cycle The cycle that was found.
     */
    public CyclicDependencyException(GraphCycle<PathVertex> cycle) {
        super();

        this.cycle = cycle;
    }

    /**
     * @return Cycle that was found.
     */
    public GraphCycle<PathVertex> getCycle() {
        return new GraphCycle<PathVertex>(this.cycle);
    }

    @Override
    public String getMessage() {
        return String.format("Cyclic dependency detected: %s -> %s", this.cycle.vertex1, this.cycle.vertex2);
    }
}
