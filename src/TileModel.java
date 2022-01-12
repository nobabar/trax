/**
 * The four directions of a tile.
 */
enum Directions {
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

/**
 * The two colors that can be found on the tile, also the colors of the two players.
 */
enum Colors {
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

/**
 * The different playable tiles and their properties.
 */
public enum TileModel {
	CROSS,
	CURVE;

	/**
	 * Colors of the tile sides, first is the cross tile then the curve tile.
	 * Directions go as defined in the direction enum.
	 */
	static final Colors[][] color = {
			{Colors.W, Colors.R, Colors.W, Colors.R},
			{Colors.W, Colors.R, Colors.R, Colors.W}
	};

	/**
	 * Rotate the original tile array to make a certain direction points up.
	 * @param dir the direction to place upwards.
	 * @return the transformed array.
	 */
	public Colors[] colArray(Directions dir) {
		Colors[] array = new Colors[4];
		for (int i = 0; i < 4; i++) {
			array[i] = color[this.ordinal()][(i+dir.ordinal())%4];
		}
		return array;
	}
}
