import java.awt.Color;

import javax.swing.border.LineBorder;

/**
 * The game board, keep tracks of tiles and their placement, checks victory conditions.
 */
public class GameBoard implements PanelButtonObserver, BoardButtonObserver {
    GameObserver GO; // observation interface.

    final int nTiles;
    final PanelTile[][] panelGrid; // grid of the lateral panel tiles
    final BoardGrid grid; // grid of the board tiles
    int playedTiles = 0;

    PanelButton current; // the selected tile from the lateral panel
    Colors turn = Colors.W; // color of the player whose turn it is

    /**
     * Create a new board with n side tiles, all board are squares. Fill the two grids.
     *
     * @param nTiles number of side tiles.
     */
    public GameBoard(int nTiles) {
        panelGrid = new PanelTile[][]{
                {new PanelTile(TileModel.CROSS, Directions.N, this),
                        new PanelTile(TileModel.CROSS, Directions.E, this)},
                {new PanelTile(TileModel.CURVE, Directions.N, this),
                        new PanelTile(TileModel.CURVE, Directions.W, this)},
                {new PanelTile(TileModel.CURVE, Directions.S, this),
                        new PanelTile(TileModel.CURVE, Directions.E, this)}
        };

        this.nTiles = nTiles;

        grid = new BoardGrid(nTiles);
        for (int row = 0; row < nTiles*nTiles; row++) {
            grid.add(new BoardTile(this));
        }
    }

    /**
     * Resets the game board and its tiles.
     */
    public void clear() {
        grid.clear();
        playedTiles = 0;
    }

    /**
     * Allows communication between the game board and the user interface. Used to indicate game over and players' turn.
     *
     * @param GO observation interface.
     */
    public void setObserver(GameObserver GO) {
        this.GO = GO;
    }

    /**
     * Define the selected tile from the lateral panel
     *
     * @param pb the selected tile.
     */
    public void setCurrent(PanelButton pb) {
        if (current != null) {
            current.setBorder(new LineBorder(Color.WHITE));
        }
        this.current = pb;
    }

    /**
     * Get the coordinates of a tile in the board.
     * @param bt the tile we are searching for.
     * @return an array of the coordinates (row, col).
     */
    public int[] getCoordinates(BoardTile bt) {
        return grid.getCoordinates(bt);
    }

    /**
     * Retrieve a tile from its coordinates.
     * @param row tiles' row.
     * @param col tiles' column.
     * @return the wanted tile.
     */
    public BoardTile getTile(int row, int col) {
        return grid.getTile(row, col);
    }

    /**
     * Play a new tile.
     *
     * @param bt the tile to play on.
     */
    public void playTile(BoardTile bt) {
		if (current != null) { // do nothing if no tile have been selected from the panel
            if (playedTiles == 0 || grid.testConformity(bt, current.tile)) { // check if you can play this tile here
                bt.setTile(current.tile);
                playedTiles++;
                if (playedTiles >= 4) {
                    GO.win(grid.isGameOver(bt, turn));
                }
				if (Game.getComputerPlayer() != null){
                    Game.getComputerPlayer().ComputerMove(this, bt);
				}
                turn = turn.opposite(); // switch to next player
                GO.nextTurn(turn);
            }
            current.setBorder(new LineBorder(Color.WHITE));
            current = null;
        }
    }

    /**
     * Play a new tile.
     *
     * @param bt   the tile to play on.
     * @param tile the tile to play.
     */
    public void playTile(BoardTile bt, Tiles tile) {
        bt.setTile(tile);
        playedTiles++;
        turn = turn.opposite(); // switch to next player
        GO.nextTurn(turn);
        if (playedTiles >= 4) {
            GO.win(grid.isGameOver(bt, turn));
        }
    }
}
