package game.player;

import game.Chip;
import game.GameField;
import game.WrongMoveException;

import java.util.List;
import java.awt.Point;

/**
 * Autonomous Player without
 */
public class RobotPlayer extends Player {
    public RobotPlayer(String name) {
        super(name);
    }

    @Override
    public void action(Chip color, GameField field) {
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
                    if (weight > bestWight) {
                        bestWight = weight;
                        bestMove = p;
                    }
                }
            }
        }
        try {
            controls.makeMove(bestMove);
        } catch (WrongMoveException e) {
            throw new RuntimeException(e);
        }
    }

    protected double cellWeight(Point cell, Chip color, GameField field) {
        if (cell == null) {
            return 0;
        }
        double si = 0;
        List<Point> closingCells = field.getClosingCells(cell, color);
        for (Point closingCell : closingCells) {
            si += 1;
            if (closingCell.x == 0 || closingCell.x == field.getWidth() - 1) {
                if (closingCell.y == 0 || closingCell.y == field.getHeight() - 1) {
                    si += 1;
                }
            }
        }

        double ss = 0;
        if (cell.x == 0 || cell.x == field.getWidth() - 1) {
            ss += 0.4;
            if (cell.y == 0 || cell.y == field.getHeight() - 1) {
                ss += 0.4;
            }
        }

        return ss + si;
    }
}
