package console;

import game.Chip;
import game.GameField;
import game.WrongMoveException;
import game.player.Player;

import java.awt.*;
import java.util.Scanner;

/**
 * Player with console input
 */
public class ConsolePlayer extends Player {
    /**
     * Create instance
     * @param name player name
     */
    public ConsolePlayer(String name) {
        super(name);
    }

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void action(Chip color, GameField field) {
        System.out.println(Utilities.getColorSymbol(color) + " " + getName() + ", enter your move (x y) or \"undo\" to cancel move:");
        System.out.println(Utilities.formatField(field, color));

        String rawInput = scanner.nextLine();
        if ("undo".equals(rawInput)) {
            boolean success = controls.tryCancelMove();
            if (!success) {
                System.out.println("Can't undo move");
                return;
            }
            return;
        }

        try {
            controls.makeMove(parseMove(rawInput, field));
        } catch (WrongMoveException e) {
            System.out.println("Wrong move! " + e.getMessage());
        }
    }

    // TODO: fix undo
    private Point parseMove(String rawInput, GameField field) {
        String[] input = rawInput.split(" ");
        if (input.length != 2) {
            System.out.println("Wrong input, expected 2 numbers");
            return parseMove(scanner.nextLine(), field);
        }
        try {
            int x = Integer.parseInt(input[0]) - 1;
            int y = Integer.parseInt(input[1]) - 1;

            if (x < 0 || x > field.getWidth() || y < 0 || y > field.getHeight()) {
                System.out.println("Wrong input, expected numbers from 0 to " + field.getWidth());
                return parseMove(scanner.nextLine(), field);
            }
            return new Point(x, y);
        } catch (NumberFormatException e) {
            System.out.println("Wrong input, expected numbers");
            return parseMove(scanner.nextLine(), field);
        }
    }
}
