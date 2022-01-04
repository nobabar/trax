import java.util.ArrayList;
import java.util.List;

public class Tiles {
	private TileModel model;
	private Directions orientation;
	
	public Tiles() {}
	
	public Tiles(TileModel model, Directions orientation) {
		this.model = model;
		this.orientation = orientation;
	}
	
	public Tiles(TileModel model, int orientation) {
		this.model = model;
		this.orientation = Directions.values()[orientation];
	}
	
	public Tiles(Tiles tile) {
		this.model = tile.model;
		this.orientation = tile.orientation;
	}
	
	public void setTile(TileModel model, Directions orientation) {
		this.model = model;
		this.orientation = orientation;
	}
	
	public void setTile(Tiles tile) {
		this.model = tile.model;
		this.orientation = tile.orientation;
	}
	
	public TileModel getModel() {
		return this.model;
	}
	
	public int getOrientation() {
		return this.orientation.ordinal();
	}
	
	public void clear() {
		this.model = null;
	}
	
	public Colors[] colArray() {
		return model.colArray(this.orientation);
	}
	
	public Colors getColor(Directions dir) {
		return colArray()[dir.ordinal()];
	}
	
	public Colors getColor(int dir) {
		return colArray()[dir];
	}
	
	public Directions getPath(Directions dir) {		
		Colors col = getColor(dir);
		for (int side = 0; side < 4; side++) {
			if (side != dir.ordinal() && colArray()[side] == col) {
				return Directions.values()[side];
			}
		}
		return null;
	}
	
	public Directions[] getPath(Colors col) {
		List<Directions> sideList = new ArrayList<>();
		for (int side = 0; side < 4; side++) {
			if (colArray()[side] == col) {
				sideList.add(Directions.values()[side]);
			}		
		}
		Directions[] sideArray = sideList.toArray(new Directions[0]);
		return sideArray;
	}
}

class PanelTile extends Tiles {
	PanelButtonObserver pbo;
	
	public PanelTile(TileModel model, Directions dir, PanelButtonObserver pbo) {
		super (model, dir);
		this.pbo = pbo;
	}
}

class BoardTile extends Tiles {
	BoardButtonObserver bbo;

	public BoardTile(BoardButtonObserver bbo) {
		super ();
		this.bbo = bbo;
	}
}
