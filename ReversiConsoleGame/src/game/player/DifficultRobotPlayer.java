package game.player;

import game.Chip;
import game.GameField;
import game.WrongMoveException;

import java.awt.*;

/**
 * Autonomous Player with 1 level of recursion
 */
public class DifficultRobotPlayer extends RobotPlayer {
    public DifficultRobotPlayer(String name) {
        super(name);
    }

    @Override
    public void action(Chip color, GameField field) {
        try {
            controls.makeMove(getNextMove(color, field));
        } catch (WrongMoveException e) {
            throw new RuntimeException(e);
        }
    }

    private Point getNextMove(Chip color, GameField field, int recursionDepth) {
        Point bestMove = null;
        double bestWight = 0;

        for (int x = 0; x < field.getWidth(); x++) {
            for (int y = 0; y < field.getHeight(); y++) {
                Point p = new Point(x, y);
                if (!field.cellAt(x, y).hasChip()) {
                    double weight = cellWeight(p, color, field);

                    if (weight < 1) {
                        continue;
                    }
                    if(bestMove == null) {
                        bestMove = p;
                        bestWight = weight;
                    }
                    if (recursionDepth > 0) {
                        GameField newField = new GameField(field);
                        newField.cellAt(p).setChip(color);

                        Point opponentMove = getNextMove(Chip.getChipOppositeColor(color), newField, recursionDepth - 1);
                        // TODO: optimize
                        weight -= cellWeight(opponentMove, Chip.getChipOppositeColor(color), newField);
                    }

                    if (weight > bestWight) {
                        bestWight = weight;
                        bestMove = p;
                    }
                }
            }
        }
        return bestMove;
    }

    private Point getNextMove(Chip color, GameField field) {
        return getNextMove(color, field, 1);
    }
}
