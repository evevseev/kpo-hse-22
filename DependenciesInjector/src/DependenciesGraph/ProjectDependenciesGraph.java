package DependenciesGraph;

import Graph.Graph;

import java.nio.file.Path;
import java.util.Objects;

/**
 * A graph representing the dependencies between files in a project.
 */
public class ProjectDependenciesGraph extends Graph<PathVertex> {
    public ProjectDependenciesGraph() {
        super();
    }

    public PathVertex getOrCreateVertexReference(Path file) {
        for (PathVertex vertex : this.getVertices()) {
            if (Objects.equals(vertex.getPath(), file)) {
                return vertex;
            }
        }

        PathVertex vertex = new PathVertex(file);
        this.addVertex(vertex);
        return vertex;
    }
}
