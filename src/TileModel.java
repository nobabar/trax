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
