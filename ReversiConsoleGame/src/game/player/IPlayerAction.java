package game.player;

import game.Chip;
import game.GameField;

/**
 * To ask player for action during combat
 */
public interface IPlayerAction {
    /**
     * Get Player action
     * @param color player color
     * @param field game field
     */
    void action(Chip color, GameField field);
}
