package DependenciesInjector.Exceptions;

/**
 * Exception thrown when an error occurs while reading a file.
 */
public class FileReadingException extends CompilerException {
    /**
     * Creates a new exception.
     *
     * @param message The message of the exception.
     */
    public FileReadingException(String message) {
        super(message);
    }
}
