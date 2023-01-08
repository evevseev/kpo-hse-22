package DependenciesInjector.Exceptions;

/**
 * Exception thrown when in case of fail to compile.
 */
public class CompilerException extends Exception {
    /**
     * Creates a new instance.
     */
    public CompilerException() {
        super();
    }

    /**
     * Creates a new instance.
     *
     * @param message Message.
     */
    public CompilerException(String message) {
        super(message);
    }
}
