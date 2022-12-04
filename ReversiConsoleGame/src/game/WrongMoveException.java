package game;

/**
 * Raises on incorrect move
 */
public class WrongMoveException extends Exception {
    public WrongMoveException(String message) {
        super(message);
    }
}
