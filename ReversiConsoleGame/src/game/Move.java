package game;

import java.awt.*;

/**
 * Stores move on field
 */
public class Move {
    /**
     * Creates instance
     * @param field field (is copied)
     * @param move move (is copied)
     */
    public Move(GameField field, Point move) {
        this.cell = new Point(move);
        this.field = new GameField(field);
    }

    private final Point cell;
    private final GameField field;

    /**
     * Gets cell which has been placed
     * @return cell
     */
    public Point getCell() {
        return cell;
    }

    /**
     * Gets field
     * @return copy of field
     */
    public GameField getField() {
        return new GameField(field);
    }
}
