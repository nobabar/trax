/**
 * The four directions of a tile.
 */
public enum Directions {
    N ("north"),
    E ("east"),
    S ("south"),
    W ("west");

    static final int[][] move = { // what are the transformation to apply to rows and columns when going in a direction
            {-1, 0},
            {0, 1},
            {1, 0},
            {0, -1}
    };

    final String name;

    Directions(String name) {
        this.name = name;
    }

    /**
     * The transform to apply along a certain axis when going in a direction.
     * @param axis rows or columns.
     * @return what to add to this axis.
     */
    public int motion(int axis) {
        return move[this.ordinal()][axis];
    }

    /**
     * @return the opposite direction of a given direction.
     */
    public Directions opposite() {
        return Directions.values()[(this.ordinal()+2)%4];
    }

    /**
     * @return the name of the direction
     */
    public String toString() {
        return this.name;
    }
}