/**
 * Tiles of board.
 */
public class BoardTile extends Tiles {
    final BoardButtonObserver bbo;

    /**
     * Create a completely empty tile.
     */
    public BoardTile() {
        super (); // board tile are empty at first
        this.bbo = null;
    }

    /**
     * Create a tile that can talk to the board game.
     * @param bbo the interface used to talk with the board.
     */
    public BoardTile(BoardButtonObserver bbo) {
        super (); // board tile are empty at first
        this.bbo = bbo;
    }

    /**
     * Create a tile from another tile as model.
     * @param bt the tile used as model.
     */
    public BoardTile(BoardTile bt) {
        super (bt.getModel(), bt.getOrientation());
        this.bbo = bt.bbo;
    }
}