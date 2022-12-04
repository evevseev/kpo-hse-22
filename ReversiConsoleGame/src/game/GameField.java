package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static game.Chip.getChipOppositeColor;

/**
 * Game field
 */
public class GameField {
    /**
     * Creates default field
     */
    public GameField() {
        this(8, 8);

        field[3][3].setChip(Chip.WHITE);
        field[3][4].setChip(Chip.BLACK);
        field[4][3].setChip(Chip.BLACK);
        field[4][4].setChip(Chip.WHITE);
    }

    /**
     * Creates field with specific size
     *
     * @param height height
     * @param width  width
     */
    public GameField(int height, int width) {
        this.height = height;
        this.width = width;
        this.field = new Cell[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                field[y][x] = new Cell();
            }
        }
    }

    /**
     * Copy constructor
     *
     * @param field field to copy
     */
    public GameField(GameField field) {
        this(field.getHeight(), field.getWidth());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.field[y][x] = new Cell(field.cellAt(x, y).getChip());
            }
        }
    }

    /**
     * Gets height
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets width
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets total size
     *
     * @return size
     */
    public int size() {
        return getWidth() * getHeight();
    }


    /**
     * Represent cell on the field
     */
    public static class Cell {
        /**
         * Create cell
         */
        public Cell() {
        }

        /**
         * Create cell with a given chip color
         */
        public Cell(Chip chip) {
            this.setChip(chip);
        }

        private Chip chip = null;

        /**
         * Get Chip
         *
         * @return chip
         */
        public Chip getChip() {
            return chip;
        }

        /**
         * Set Chip
         *
         * @param state chip
         */
        public void setChip(Chip state) {
            this.chip = state;
        }

        /**
         * Check if chip has been placed
         *
         * @return true if so
         */
        public boolean hasChip() {
            return chip != null;
        }
    }

    private final int height;
    private final int width;
    private final Cell[][] field;

    /**
     * Returns pointer to cell
     *
     * @param x x
     * @param y y
     * @return pointer to cell
     */
    public Cell cellAt(int x, int y) {
        return field[x][y];
    }

    /**
     * Returns pointer to cell
     *
     * @param point coords
     * @return pointer to cell
     */
    public Cell cellAt(Point point) {
        return cellAt(point.x, point.y);
    }


    /**
     * Checks if coords are within borders
     *
     * @param chip point
     * @return true if so
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean pointWithinField(Point chip) {
        return chip.x >= 0 && chip.x < getWidth() && chip.y >= 0 && chip.y < getHeight();
    }

    /**
     * Get all possible moves with chip of given color
     *
     * @param color color
     * @return list of possible moves
     */
    public List<Point> getPossibleMoves(Chip color) {
        List<Point> possibleMoves = new ArrayList<>();
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Point p = new Point(x, y);
                if (isMoveCorrect(p, color)) {
                    possibleMoves.add(p);
                }
            }
        }

        return possibleMoves;
    }

    /**
     * Checks if cell of given color has neighbours
     *
     * @param chip  coords
     * @param color color
     * @return true if so
     */
    public boolean hasOpponentsNeighbourChip(Point chip, Chip color) {
        Chip oppositeColor = getChipOppositeColor(color);
        for (int x = chip.x - 1; x <= chip.x + 1; x++) {
            for (int y = chip.y - 1; y <= chip.y + 1; y++) {
                if (chip.x == x && chip.y == y) {
                    continue;
                }
                Point p = new Point(x, y);

                if (!pointWithinField(p)) {
                    continue;
                }

                if (cellAt(p).getChip() == oppositeColor) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get chips which will be closed by placing chip
     *
     * @param chip  placing chip
     * @param color color of the placing chip
     * @return
     */
    public List<Point> getClosingCells(Point chip, Chip color) {
        // TODO: get more than one closing chip
        ArrayList<Point> closingChips = new ArrayList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                Point absolutePoint = new Point(chip.x + x, chip.y + y);

                if (!pointWithinField(absolutePoint)) {
                    continue;
                }

                if (cellAt(absolutePoint).getChip() == getChipOppositeColor(color)) {
                    ArrayList<Point> closingChipsInDirection = new ArrayList<>();
                    for (int i = 1; i < Math.max(getWidth(), getHeight()); i++) {
                        Point p = new Point(chip.x + x * i, chip.y + y * i);
                        if (!pointWithinField(p) || !cellAt(p).hasChip()) {
                            break;
                        }
                        if (cellAt(p).getChip() == color) {
                            closingChips.addAll(closingChipsInDirection);
                            break;
                        }
                        if (cellAt(p).getChip() == getChipOppositeColor(color)) {
                            closingChipsInDirection.add(p);
                        }
                    }
                }
            }
        }
        return closingChips;
    }

    /**
     * Validate move
     *
     * @param move  move
     * @param color color of placing chip
     * @return true if move is valid
     * @throws WrongMoveException raised when move is wring
     */
    public boolean validateMove(Point move, Chip color) throws WrongMoveException {
        if (move == null) {
            throw new WrongMoveException("Move is null");
        }

        if (!pointWithinField(move)) {
            throw new WrongMoveException("Move is out of field");
        }

        if (cellAt(move.x, move.y).getChip() != null) {
            throw new WrongMoveException("There is already a chip on this position");
        }

        // при очередном ходе фишку можно ставить на свободную клетку в любом
        // направлении, но обязательно рядом хотя бы с одной из фишек противника;
        if (!hasOpponentsNeighbourChip(move, color)) {
            throw new WrongMoveException("There is no opponent's chip near this position");
        }

        // фишка должна ставиться так, чтобы хотя бы одна из фишек противника
        // оказалась замкнутой своими фишками. При этом замкнутые фишки противника
        // меняют цвет и становятся своими;
        if (getClosingCells(move, color).isEmpty()) {
            throw new WrongMoveException("There is no closing chips");
        }

        return true;
    }

    /**
     * Check if move is valid without raising exception
     *
     * @param move  move
     * @param color color of placing chip
     * @return
     */
    public boolean isMoveCorrect(Point move, Chip color) {
        try {
            return validateMove(move, color);
        } catch (WrongMoveException e) {
            return false;
        }
    }

    /**
     * Count chips of given color
     *
     * @param color color
     * @return count
     */
    public int countChips(Chip color) {
        int count = 0;
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (cellAt(i, j).getChip() == color) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Returns list of empty cells
     *
     * @return list of cells
     */
    public List<Point> getEmptyCells() {
        List<Point> emptyCells = new ArrayList<>();
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (cellAt(i, j).getChip() == null) {
                    emptyCells.add(new Point(i, j));
                }
            }
        }
        return emptyCells;
    }
}

