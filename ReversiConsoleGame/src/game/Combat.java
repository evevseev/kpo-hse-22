package game;

import game.player.IPlayerAction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents one round
 */
public class Combat implements ICombatControls {

    /**
     * Creates new round
     * @param whitePlayer white player
     * @param blackPlayer black player
     */
    public Combat(IPlayerAction whitePlayer, IPlayerAction blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentPlayer = blackPlayer;
        this.field = new GameField();
    }

    private final IPlayerAction whitePlayer;
    private final IPlayerAction blackPlayer;
    private IPlayerAction winner;

    /**
     * Current field
     */
    private GameField field;

    /**
     * Current player
     */
    private IPlayerAction currentPlayer;
    private final ArrayList<Move> moves = new ArrayList<>();
    private boolean isFinished = false;

    /**
     * Request player to make move/undo and other actions
     */
    public void requestPlayerAction() {
        if (isFinished) {
            return;
        }

        Chip moveColor = currentPlayer == whitePlayer ? Chip.WHITE : Chip.BLACK;
        currentPlayer.action(moveColor, field);
    }

    /**
     * Get winner of the round
     * @return winner of the round (null if round is not finished or draw)
     */
    public IPlayerAction getWinner() {
        if (!isFinished) {
            return null;
        }
        return winner;
    }

    /**
     * Check if game has finished
     * @return is game finished
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Get total score of the player
     * @param player player to get score of
     * @return score
     */
    public int getScore(IPlayerAction player) {
        if (player == whitePlayer) {
            return field.countChips(Chip.WHITE);
        } else if (player == blackPlayer) {
            return field.countChips(Chip.BLACK);
        } else {
            throw new IllegalArgumentException("Player is not in this combat");
        }
    }

    /**
     * Apply mplayer's move
     * @param chip move
     * @param color color
     * @param closingChips chips to change color
     */
    private void applyMove(Point chip, Chip color, List<Point> closingChips) {
        field.cellAt(chip.x, chip.y).setChip(color);
        for (Point closingChip : closingChips) {
            field.cellAt(closingChip.x, closingChip.y).setChip(color);
        }

        moves.add(new Move(field, chip));

        currentPlayer = currentPlayer == whitePlayer ? blackPlayer : whitePlayer;

        //  игра заканчивается, если доска заполнена, или на ней присутствуют
        //  фишки только одного цвета, или ни один из игроков не может сделать очередной
        //  ход.
        int whiteChips = field.countChips(Chip.WHITE);
        int blackChips = field.countChips(Chip.BLACK);
        List<Point> emptyCells = field.getEmptyCells();

        if (emptyCells.isEmpty() || whiteChips == 0 || blackChips == 0) {
            if (whiteChips > blackChips) {
                winner = whitePlayer;
            } else if (whiteChips < blackChips) {
                winner = blackPlayer;
            }
            isFinished = true;
        }

        for (Point cell : emptyCells) {
            if (field.isMoveCorrect(cell, Chip.getChipOppositeColor(color))) {
                return;
            }
        }

        // Determine winner
        if (whiteChips > blackChips) {
            winner = whitePlayer;
        } else if (whiteChips < blackChips) {
            winner = blackPlayer;
        }
        isFinished = true;
    }

    // Implementation of  ICombatControls Interface
    @Override
    public void makeMove(Point move) throws WrongMoveException {
        Chip moveColor = currentPlayer == whitePlayer ? Chip.WHITE : Chip.BLACK;
        field.validateMove(move, moveColor);
        applyMove(move, moveColor, field.getClosingCells(move, moveColor));
    }

    @Override
    public boolean tryCancelMove() {
        if(moves.size() < 2) {
            return false;
        } else if (moves.size() == 2) {
            field = new GameField();
            moves.clear();
            return true;
        } else {
            field = moves.get(moves.size() - 1).getField();

            moves.subList(moves.size() - 3, moves.size()).clear();
            return true;
        }
    }
}
