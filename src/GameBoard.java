import java.util.Arrays;

import java.awt.Color;

import javax.swing.border.LineBorder;

/**
 * The game board, keep tracks of tiles and their placement, checks victory conditions.
 */
public class GameBoard implements PanelButtonObserver, BoardButtonObserver {
    GameObserver GO; // observation interface.

    final int nTiles;
    final BoardTile[][] boardGrid; // grid of the game board tiles
    final PanelTile[][] panelGrid; // grid of the lateral panel tiles
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
        this.boardGrid = new BoardTile[nTiles][nTiles];

        for (int row = 0; row < nTiles; row++) {
            for (int col = 0; col < nTiles; col++) {
                BoardTile bt = new BoardTile(this);
                boardGrid[row][col] = bt;
            }
        }
    }

    /**
     * Resets the game board and its tiles.
     */
    public void clear() {
        for (int row = 0; row < nTiles; row++) {
            for (int col = 0; col < nTiles; col++) {
                boardGrid[row][col].clear();
            }
        }
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
     * Play a new tile.
     *
     * @param bt the tile to play on.
     */
    public void playTile(BoardTile bt) {
		if (current != null) { // do nothing if no tile have been selected from the panel
            if (playedTiles == 0 || testConformity(bt, current.tile)) { // check if you can play this tile here
                bt.setTile(current.tile);
                playedTiles++;
				isGameOver(bt);
				if (Game.getComputerPlayer() != null){
                    Game.getComputerPlayer().ComputerMove(this, bt);
				}
                turn = turn.opposite(); // switch to next player
                GO.nextTurn(null);
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
        isGameOver(bt);
    }

    /**
     * Get the coordinates of a tile on the board.
     *
     * @param bt the tile we are searching for.
     * @return the coordinates (row and column) of the tile.
     */
    public int[] getCoordinates(BoardTile bt) {
        for (int row = 0; row < boardGrid.length; row++) {
            for (int col = 0; col < boardGrid[0].length; col++) {
                if (boardGrid[row][col] == bt) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    /**
     * Get the neighbor tile in a given direction, if there is any.
     *
     * @param coordinates position of the starting tile.
     * @param dir         direction where to go.
     * @return the neighbor tile.
     */
    private Tiles adjacentTile(int[] coordinates, Directions dir) {
        int row = coordinates[0] + dir.motion(0);
        int col = coordinates[1] + dir.motion(1);
        if (row >= 0 && row < nTiles && col >= 0 && col < nTiles) { // check that we are not on the board sides
            Tiles tile = boardGrid[row][col];
            if (tile.getModel() != null) {
                return tile;
            }
        }
        return null; // this should never happen
    }

    /**
     * Check that we can place that tile in the wished place.
     *
     * @param bt   the selected location.
     * @param tile the tile to place.
     * @return if the move is valid or not.
     */
    public boolean testConformity(BoardTile bt, Tiles tile) {
        boolean conform = true;
        boolean neighbor = false; // the tile must have at least one neighbor
        int[] coordinates = getCoordinates(bt);
        for (Directions dir : Directions.values()) {
            Colors tileColor = tile.getColor(dir.ordinal());
            assert coordinates != null; // this should never happen
            Tiles neighborTile = adjacentTile(coordinates, dir);
            if (neighborTile != null) {
                neighbor = true;
                Colors neighborColor = neighborTile.getColor(dir.opposite());
                if (tileColor != neighborColor) {
                    conform = false;
                }
            }
        }
        return (conform && neighbor);
    }

    /**
     * Checks if the move played is a winning move.
     *
     * @param bt tile of the last played move.
     */
    private void isGameOver(BoardTile bt) {
        if (playedTiles >= 4) { // no winning combination below 4 played tiles
            for (Colors col : new Colors[]{turn, turn.opposite()}) { // test the current player color first
                Directions[] dir = bt.getPath(col); // get the direction of the player color in the played tile
                int[] firstPath = dichotomyGO(bt, dir[0]); // split the search in the two directions
                int[] secondPath = dichotomyGO(bt, dir[1]);

                if ((firstPath.length == 0 && secondPath.length == 0) // if we have a loop
                        || Math.max(firstPath[1], secondPath[1])
                        - Math.min(firstPath[0], secondPath[0]) >= 6 // if the path goes over 6 rows or more
                        || Math.max(firstPath[3], secondPath[3])
                        - Math.min(firstPath[2], secondPath[2]) >= 6) { // if the path goes over 6 columns or more
                    GO.win(col);
                    return;
                }
            }
        }
    }

    /**
     * Follow the path from the starting tile and store the area occupied by this path.
     *
     * @param bt  the starting tile.
     * @param dir the direction to follow.
     * @return the coordinates of the rectangle's corners that is covered by the path.
     */
    private int[] dichotomyGO(BoardTile bt, Directions dir) {
        int[] coordinates = getCoordinates(bt);
        assert coordinates != null; // this should never happen
        int row = coordinates[0] + dir.motion(0);
        int col = coordinates[1] + dir.motion(1);

        int minRow = coordinates[0];
        int maxRow = coordinates[0];
        int minCol = coordinates[1];
        int maxCol = coordinates[1]; // start with a 1 by 1 rectangle around the starting tile

        Directions nextDir = dir;
        for (; ; ) { // loop as long as we have tiles ahead
            if (row >= 0 && row < nTiles && col >= 0 && col < nTiles) { // check that we didn't hit the sides
                BoardTile nextTile = boardGrid[row][col];
                if (nextTile.getModel() != null) { // check that we have a tile ahead
                    if (row < minRow) { // expend the rectangle rows
                        minRow = row;
                    } else if (row > maxRow) {
                        maxRow = row;
                    }

                    if (col < minCol) { // expend the rectangle columns
                        minCol = col;
                    } else if (col > maxCol) {
                        maxCol = col;
                    }

                    nextDir = nextTile.getPath(nextDir.opposite());
                    row += nextDir.motion(0);
                    col += nextDir.motion(1);
                    if (Arrays.equals(new int[]{row, col}, coordinates)) { // if we looped back to the starting tile
                        return new int[0];
                    }
                    continue;
                }
            }
            return new int[]{minRow, maxRow, minCol, maxCol};
        }
    }
}
