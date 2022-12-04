package game.player;

import game.ICombatControls;

/**
 * Abstract Class of the Reversi Player
 */
public abstract class Player implements IPlayerAction {
    /**
     * Create instance
     * @param name player name
     * @param controls IPlayerAction object
     */
    public Player(String name, ICombatControls controls) {
        this.name = name;
        this.controls = controls;
    }


    /**
     * Create instance
     * @param name player name
     */
    public Player(String name) {
        this.name = name;
    }

    private final String name;

    protected ICombatControls controls;

    /**
     * Get player name
     * @return player name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Set controls
     * @param controls IPlayerAction object
     */
    public void setControls(ICombatControls controls) {
        this.controls = controls;
    }
}
