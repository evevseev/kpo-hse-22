package DependenciesGraph;

import java.nio.file.Path;

/**
 * Represents a vertex in the graph.
 */
public class PathVertex {
    private final Path file;

    /**
     * Creates a new vertex.
     *
     * @param file The file that this vertex represents.
     */
    public PathVertex(Path file) {
        this.file = file;
    }

    /**
     * Gets the file that this vertex represents.
     *
     * @return The file that this vertex represents.
     */
    public Path getPath() {
        return this.file;
    }

    @Override
    public String toString() {
        return this.file.toString();
    }
}