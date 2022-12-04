package console;

import game.GameEngine;
import game.player.*;

import java.util.Scanner;

public class Interface {
    /**
     * Variants of the game
     */
    public enum GameMode {
        HUMAN_VS_HUMAN,
        HUMAN_VS_AI_EASY,
        HUMAN_VS_AI_HARD,
        AI_EASY_VS_AI_HARD
    }

    private GameEngine gameEngine;
    private GameMode gameMode;
    private Player firstPlayer;
    private Player secondPlayer;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Start game in console
     */
    public void run() {
        printWelcomeScreen();
        getGameMode();

        while (true) {
            printGameMenu();
            if (!Utilities.requestContinuing(scanner)) {
                System.out.println("Goodbye!");
                break;
            }
            startRound();
            printRoundResults();
            System.out.println("\n");
        }
    }

    private void printGameMenu() {
        printBestScoreTable();
        System.out.println("Press ENTER to star new round!\n" +
                "Enter 'q' to quit");
    }

    private void startRound() {
        System.out.println("ROUND STARTED!");
        gameEngine.startNewCombat();
        gameEngine.play();
    }

    private void printRoundResults() {
        System.out.println("ROUND FINISHED!");
        Player winner = gameEngine.lastGameWinner();

        if(winner == null) {
            System.out.println("Draw!");
        } else {
            System.out.println("Winner: " + winner);
        }

        printScoreTable();
    }

    private void printWelcomeScreen() {
        System.out.println("""
                Welcome to Reversi game!
                Select game mode (enter number):
                1. Player vs Player
                2. Player vs AI easy
                3. Player vs AI hard
                4. AI easy vs AI hard
                """);
    }

    private void printBestScoreTable() {
        System.out.println("-=-=-=-=Best Score-=-=-=-=-=\n" +
                firstPlayer + ": " + gameEngine.getBestScore(firstPlayer) + "\n" +
                secondPlayer + ": " + gameEngine.getBestScore(secondPlayer) + "\n" +
                "-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }

    private void printScoreTable() {
        System.out.println("______Last Round Score______\n" +
                firstPlayer + ": " + gameEngine.getLastGameScore(firstPlayer) + "\n" +
                secondPlayer + ": " + gameEngine.getLastGameScore(secondPlayer) + "\n" +
                "------------------------------");
    }

    private void getGameMode() {
        int mode = Utilities.requestNumberInput(scanner, 1, 4);
        switch (mode) {
            case 1 -> gameMode = GameMode.HUMAN_VS_HUMAN;
            case 2 -> gameMode = GameMode.HUMAN_VS_AI_EASY;
            case 3 -> gameMode = GameMode.HUMAN_VS_AI_HARD;
            case 4 -> gameMode = GameMode.AI_EASY_VS_AI_HARD;
        }

        initEngine();
    }

    private void initEngine() {
        switch (gameMode) {
            case HUMAN_VS_HUMAN -> {
                firstPlayer = new ConsolePlayer("Player 1");
                secondPlayer = new ConsolePlayer("Player 2");
            }
            case HUMAN_VS_AI_EASY -> {
                firstPlayer = new ConsolePlayer("Player 1");
                secondPlayer = new RobotPlayer("AI easy");
            }
            case HUMAN_VS_AI_HARD -> {
                firstPlayer = new ConsolePlayer("Player 1");
                secondPlayer = new DifficultRobotPlayer("AI hard");
            }
            case AI_EASY_VS_AI_HARD -> {
                firstPlayer = new RobotPlayer("AI easy");
                secondPlayer = new DifficultRobotPlayer("AI hard");
            }
        }

        gameEngine = new GameEngine(firstPlayer, secondPlayer);
    }
}

