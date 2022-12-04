package game;

import java.awt.*;

/**
 * Interface for controlling combat by player
 */
public interface ICombatControls {
    /**
     * Make move on field
     * @param move move
     * @throws WrongMoveException if wrong was made
     */
    void makeMove(Point move) throws WrongMoveException;

    /**
     * Try cancel 2 last moves
     * @return success result
     */
    boolean tryCancelMove();
}
