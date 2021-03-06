import java.util.Arrays;

/**
 * The tile grid of the board game, with all functions related to this grid.
 */
public class BoardGrid {
    private final BoardTile[][] grid; // the grid
    int nTiles;
    int row = 0;
    int col = 0;

    public BoardGrid(int nTiles) {
        this.nTiles = nTiles;
        this.grid = new BoardTile[nTiles][nTiles];
    }

    public BoardGrid(BoardGrid model) {
        this.nTiles = model.nTiles;
        this.grid = new BoardTile[nTiles][nTiles];
        for (int row = 0; row < nTiles*nTiles; row++) {
            this.add(new BoardTile());
        }
    }

    /**
     * Used to instantiate the grid, add tiles row by row.
     * @param tile a board tile.
     */
    public void add(BoardTile tile) {
        try {
            grid[row][col] = tile;
            if (col == nTiles - 1) row++;
            col = (col + 1) % nTiles;
        } catch (Exception ignored) {
        }
    }

    /**
     * Retrieve a tile from its position in the board.
     * @param row tile's row.
     * @param col tile's column.
     * @return the tile in question.
     */
    public BoardTile getTile(int row, int col) {
        return grid[row][col];
    }

    /**
     * keep the current board but resets the tiles on it.
     */
    public void clear() {
        for (int row = 0; row < nTiles; row++) {
            for (int col = 0; col < nTiles; col++) {
                grid[row][col].clear();
            }
        }
    }

    /**
     * Get the coordinates of a tile on the board.
     *
     * @param bt the tile we are searching for.
     * @return the coordinates (row and column) of the tile.
     */
    public int[] getCoordinates(BoardTile bt) {
        for (int row = 0; row < nTiles; row++) {
            for (int col = 0; col < nTiles; col++) {
                if (grid[row][col] == bt) {
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
     * @param dir direction where to go.
     * @return the neighbor tile.
     */
    private Tiles adjacentTile(int[] coordinates, Directions dir) {
        int row = coordinates[0] + dir.motion(0);
        int col = coordinates[1] + dir.motion(1);
        if (row >= 0 && row < nTiles && col >= 0 && col < nTiles) { // check that we are not on the board sides
            Tiles tile = grid[row][col];
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
            if (coordinates != null) {
                Tiles neighborTile = adjacentTile(coordinates, dir);
                if (neighborTile != null) {
                    neighbor = true;
                    Colors neighborColor = neighborTile.getColor(dir.opposite());
                    if (tileColor != neighborColor) {
                        conform = false;
                    }
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
    public Colors isGameOver(BoardTile bt, Colors turn) {
        Colors[] players = turn == null ? Colors.values() : new Colors[]{turn, turn.opposite()};
        for (Colors col : players) { // test the current player color first
            Directions[] dir = bt.getPath(col); // get the direction of the player color in the played tile
            int[] firstPath = dichotomyGO(bt, dir[0]); // split the search in the two directions
            int[] secondPath = dichotomyGO(bt, dir[1]);

            if ((firstPath.length == 0 && secondPath.length == 0) // if we have a loop
                    || Math.max(firstPath[1], secondPath[1]) - Math.min(firstPath[0], secondPath[0]) >= 6 // if the path goes over 6 rows or more
                    || Math.max(firstPath[3], secondPath[3]) - Math.min(firstPath[2], secondPath[2]) >= 6) { // if the path goes over 6 columns or more
                return col;
            }
        }
        return null;
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
                BoardTile nextTile = grid[row][col];
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
