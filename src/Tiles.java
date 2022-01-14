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
	
	public TileModel getModel() {
		return this.model;
	}
	
	public Directions getOrientation() { return this.orientation; }

	public int getOrientationInt() { return this.orientation.ordinal(); }

	public void clear() {
		this.model = null;
	}
	
	public Colors[] colArray() {
		return model.colArray(this.orientation);
	}

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

	public String toString() {
		return "--"+colArray()[0]+"--\n"+
				colArray()[3]+"---"+colArray()[1]+"\n"+
				"--"+colArray()[2]+"--";
	}
}

class PanelTile extends Tiles {
	final PanelButtonObserver pbo;
	
	public PanelTile(TileModel model, Directions dir, PanelButtonObserver pbo) {
		super (model, dir);
		this.pbo = pbo;
	}
}

class BoardTile extends Tiles {
	final BoardButtonObserver bbo;

	public BoardTile() {
		super (); // board tile are empty at first
		this.bbo = null;
	}

	public BoardTile(BoardButtonObserver bbo) {
		super (); // board tile are empty at first
		this.bbo = bbo;
	}

	public BoardTile(BoardTile bt) {
		super (bt.getModel(), bt.getOrientation()); // board tile are empty at first
		this.bbo = bt.bbo;
	}
}
