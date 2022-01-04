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
	
	public Colors getColor(Directions dir) {
		return model.colArray(orientation)[dir.ordinal()];
	}
	
	public Colors getColor(int dir) {
		return model.getColor((dir+orientation.ordinal())%4);
	}
	
	public Directions getPath(Directions dir) {
		return model.getPath(orientation.ordinal(), dir);
	}
	
	public Directions[] getPath(Colors col) {
		return model.getPath(orientation.ordinal(), col);
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
