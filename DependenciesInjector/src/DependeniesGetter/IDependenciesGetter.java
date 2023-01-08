package DependeniesGetter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Interface for classes that can get the dependencies of a file.
 */
public interface IDependenciesGetter {
    /**
     * Gets the dependencies of a file.
     *
     * @param file The file to get the dependencies of.
     * @return A list of the dependencies of the file.
     * @throws IOException If an error occurs while reading the file.
     */
    List<Path> getFileDependencies(Path file) throws IOException;
}
