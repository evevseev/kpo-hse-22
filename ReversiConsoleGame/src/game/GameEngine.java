package game;

import game.player.Player;

import java.util.ArrayList;

/**
 * Core class of Reversi Game
 */
public class GameEngine {
    /**
     * Create Game instance
     * @param firstPlayer first player
     * @param secondPlayer seconds player
     */
    public GameEngine(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    private final Player firstPlayer;
    private final Player secondPlayer;
    private Combat currentCombat;
    ArrayList<Combat> previousCombats = new ArrayList<>();

    /**
     * Creates new round and sets it active
     */
    public void startNewCombat() {
        if (Math.random() > 0.5) {
            currentCombat = new Combat(firstPlayer, secondPlayer);
        } else {
            currentCombat = new Combat(secondPlayer, firstPlayer);
        }
        firstPlayer.setControls(currentCombat);
        secondPlayer.setControls(currentCombat);
    }

    /**
     * Starts active round
     */
    public void play() {
        if (currentCombat == null) {
            throw new IllegalStateException("No game started");
        }

        while (!currentCombat.isFinished()) {
            currentCombat.requestPlayerAction();
        }

        previousCombats.add(currentCombat);
    }

    /**
     * Gets best score from all rounds
     * @param player player
     * @return best score
     */
    public int getBestScore(Player player) {
        int bestScore = 0;
        for (Combat session : previousCombats) {
            int score = session.getScore(player);
            if (score > bestScore) {
                bestScore = score;
            }
        }
        return bestScore;
    }

    /**
     * Gets last game player score
     * @param player player
     * @return score
     */
    public int getLastGameScore(Player player) {
        return previousCombats.get(previousCombats.size() - 1).getScore(player);
    }

    /**
     * Gets winner of last finished round
     * @return winner
     */
    public Player lastGameWinner() {
        if (previousCombats.size() == 0) {
            return null;
        }

        return (Player) previousCombats.get(previousCombats.size() - 1).getWinner();
    }

    /**
     * Get first player
     * @return player
     */
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * Get second player
     * @return player
     */
    public Player getSecondPlayer() {
        return secondPlayer;
    }
}
