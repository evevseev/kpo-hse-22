package game;

/**
 * Chip colors
 */
public enum Chip {
    WHITE, BLACK;

    /**
     * Get chip of opposite color
     * @param color chip
     * @return chip of opposite color
     */
    public static Chip getChipOppositeColor(Chip color) {
        if (color == WHITE) {
            return BLACK;
        } else {
            return WHITE;
        }
    }
}
