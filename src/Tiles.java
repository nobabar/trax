import java.util.ArrayList;
import java.util.List;

/**
 * Define general tiles' method.
 */
public class Tiles {
	private TileModel model;
	private Directions orientation;

	/**
	 * Empty construct to call for board tiles.
	 */
	public Tiles() {}

	/**
	 * Construct a tile from a model and a rotation.
	 * @param model the model (cross or curve).
	 * @param orientation the rotation applied to the tile.
	 */
	public Tiles(TileModel model, Directions orientation) {
		this.model = model;
		this.orientation = orientation;
	}

	/**
	 * Construct a tile given another tile as a model.
	 * @param tile the model.
	 */
	public Tiles(Tiles tile) {
		this.model = tile.model;
		this.orientation = tile.orientation;
	}

	/**
	 * Set a tile model and rotation afterward, used for board tiles.
	 * @param model the model (cross or curve).
	 * @param orientation the rotation applied to the tile.
	 */
	public void setTile(TileModel model, Directions orientation) {
		this.model = model;
		this.orientation = orientation;
	}

	/**
	 * Set a tile model and rotation afterward from a model tile, used for board tiles.
	 * @param tile the model.
	 */
	public void setTile(Tiles tile) {
		this.model = tile.model;
		this.orientation = tile.orientation;
	}

	/**
	 * @return the model of a tile.
	 */
	public TileModel getModel() {
		return this.model;
	}

	/**
	 * @return the direction of the tile's north.
	 */
	public Directions getOrientation() { return this.orientation; }

	/**
	 * @return the orientation of the tile's north.
	 */
	public int getOrientationInt() { return this.orientation.ordinal(); }

	/**
	 * Resets a tile's model.
	 */
	public void clear() {
		this.model = null;
	}

	/**
	 * @return the array representing the tile, in the correct orientation.
	 */
	public Colors[] colArray() { return model.colArray(this.orientation); }

	/**
	 * Get the color of a side.
	 * @param dir the side.
	 * @return a color.
	 */
	public Colors getColor(Directions dir) {
		return colArray()[dir.ordinal()];
	}

	/**
	 * Get the color of a side.
	 * @param dir the side.
	 * @return a color.
	 */
	public Colors getColor(int dir) {
		return colArray()[dir];
	}

	/**
	 * Get the next direction to follow when coming from a given direction.
	 * @param dir the direction you are from.
	 */
	public Directions getPath(Directions dir) {
		Colors col = getColor(dir);
		for (int side = 0; side < 4; side++) {
			if (side != dir.ordinal() && colArray()[side] == col) {
				return Directions.values()[side];
			}
		}
		return null; // this should never happen
	}

	/**
	 * Get both directions of a path of a given color.
	 * @param col the desired color.
	 * @return the two directions to explore.
	 */
	public Directions[] getPath(Colors col) {
		List<Directions> sideList = new ArrayList<>();
		for (int side = 0; side < 4; side++) {
			if (colArray()[side] == col) {
				sideList.add(Directions.values()[side]);
			}		
		}
		return sideList.toArray(new Directions[0]);
	}

	/**
	 * Print the tile in the terminal, was used in debugging phase.
	 * @return a string representing the tile.
	 */
	public String toString() {
		return "--"+colArray()[0]+"--\n"+
				colArray()[3]+"---"+colArray()[1]+"\n"+
				"--"+colArray()[2]+"--";
	}
}
