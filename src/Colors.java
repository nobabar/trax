/**
 * The two colors that can be found on the tile, also the colors of the two players.
 */
public enum Colors {
    W ("white"),
    R ("red");

    final String name;

    Colors(String name) {
        this.name = name;
    }

    /**
     * @return the opposite color.
     */
    public Colors opposite() {
        return Colors.values()[(this.ordinal()+1)%2];
    }

    /**
     * @return the name of the color.
     */
    public String toString() {
        return this.name;
    }
}