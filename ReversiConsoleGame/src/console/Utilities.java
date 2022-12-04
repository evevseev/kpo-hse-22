package console;

import game.Chip;
import game.GameField;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class with Console Utilities
 */
final public class Utilities {
    private Utilities() {
    }

    public final static int CELL_SPACING = 4;

    public final static char CELL_WHITE = '○';
    public final static char CELL_BLACK = '●';
    public final static char CELL_POSSIBLE = '◌';

    /**
     * Get Symbol for given color
     *
     * @param color color
     * @return symbol
     */
    public static char getColorSymbol(Chip color) {
        return switch (color) {
            case WHITE -> CELL_WHITE;
            case BLACK -> CELL_BLACK;
            default -> ' ';
        };
    }


    private static String printFieldRowSeparator(int width) {
        StringBuilder resString = new StringBuilder();

        for (int i = 0; i < CELL_SPACING * (width); i++) {
            if (i % CELL_SPACING == 0) {
                resString.append('+');
            }
            resString.append('-');
        }
        resString.append("+\n");
        return resString.toString();
    }

    /**
     * Get pretty string representation of field
     */

    public static String formatField(GameField field) {
        return formatField(field, null);
    }

    /**
     * Get pretty string representation of field with possible moves for given color
     *
     * @param field                 field
     * @param printPossibleMovesFor color
     * @return string
     */
    public static String formatField(GameField field, Chip printPossibleMovesFor) {
        // TODO: fix spacing
        if (field == null) return "";

        int height = field.getHeight();
        int width = field.getHeight();

        StringBuilder resString = new StringBuilder();

        List<Point> possibleMoves = new ArrayList<>();
        if (printPossibleMovesFor != null) {
            possibleMoves = field.getPossibleMoves(printPossibleMovesFor);
        }
        // HEADER
        resString.append(printFieldRowSeparator(width + 1));
        resString.append("|y\\x").append(" ".repeat(CELL_SPACING - 3));
        for (int i = 0; i < width; i++) {
            resString.append('|');
            resString.append(i + 1);
            resString.append(" ".repeat(CELL_SPACING - 1));
        }
        resString.append("|\n");

        // BODY
        resString.append(printFieldRowSeparator(width + 1));
        for (int i = 0; i < height * 2; ++i) {
            if (i % 2 == 0) {
                resString.append("| ");
                resString.append(i / 2 + 1);
                resString.append("  |");

                for (int j = 0; j < width; j++) {
                    GameField.Cell cell = field.cellAt(j, i / 2);
                    if (cell.hasChip()) {
                        resString.append(getColorSymbol(cell.getChip()));
                        resString.append(" ".repeat(CELL_SPACING - 1));
                    } else if (printPossibleMovesFor != null && possibleMoves.contains(new Point(j, i / 2))) {
                        resString.append(CELL_POSSIBLE);
                        resString.append(" ".repeat(CELL_SPACING - 1));
                    } else {
                        resString.append(" ".repeat(CELL_SPACING));
                    }
                    resString.append("|");
                }
                resString.append("\n");
            } else {
                resString.append(printFieldRowSeparator(width + 1));
            }
        }
        return resString.toString();
    }

    /**
     * Get number from user
     * @param scanner scanner
     * @param from lower bound
     * @param to upper bound
     * @return number
     */
    public static int requestNumberInput(Scanner scanner, int from, int to) {
        String rawInput = scanner.nextLine();
        try {
            int num = Integer.parseInt(rawInput);
            if (num >= from && num <= to) {
                return num;
            } else {
                System.out.println("Wrong input, expected number from " + from + " to " + to);
                return requestNumberInput(scanner, from, to);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Wrong input, expected number from " + from + " to " + to);
            return requestNumberInput(scanner, from, to);
        }
    }

    /**
     * Request confirmation of continuing
     * @param scanner scanner
     * @return result
     */
    public static boolean requestContinuing(Scanner scanner) {
        String answer = scanner.nextLine();
        return !"q".equals(answer);
    }

}
