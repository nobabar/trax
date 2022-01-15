import java.util.Collections;
import java.util.List;

import static java.util.Arrays.*;

/**
 * Computer players to play trax with even where you are alone.
 */
public abstract class ComputerPlayer {
    public ComputerPlayer() {}

    public abstract void ComputerMove(GameBoard GB, BoardTile bt);

    /**
     * Get a valid tile to play at a location.
     * @param BG the game board to play on.
     * @param nextBt where to place the tile.
     * @return a tile to play.
     */
    public Tiles randomValidTile(BoardGrid BG, BoardTile nextBt) {
        Integer[] intArray = {1, 2, 3, 4, 5, 6};
        List<Integer> intList = asList(intArray);
        Collections.shuffle(intList);
        intList.toArray(intArray);
        for (int rand : intArray) {
            TileModel TM = TileModel.values()[(rand > 2) ? 0 : 1];
            Directions orientation = Directions.values()[rand % 4];
            Tiles tile = new Tiles(TM, orientation);
            if (BG.testConformity(nextBt, tile)) {
                return tile;
            }
        }
        return null;
    }
}
