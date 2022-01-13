import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class ComputerPlayer {
    public abstract void ComputerMove(GameBoard gameBoard, BoardTile bt);
}

class SimpleComputerPlayer extends ComputerPlayer {
    public SimpleComputerPlayer() {}

    public void ComputerMove(GameBoard GB, BoardTile bt) {
        int[] coordinates = GB.getCoordinates(bt);
        BoardTile nextBt = null;

        for (Colors color : new Colors[]{Colors.R, Colors.W}) {
            Directions[] path = bt.getPath(color);
            for (Directions dir : path) {
                int row = coordinates[0] + dir.motion(0);
                int col = coordinates[1] + dir.motion(1);
                if (row >= 0 && row < GB.nTiles && col >= 0 && col < GB.nTiles) {
                    nextBt = GB.boardGrid[row][col];
                    if (nextBt.getModel() == null) {
                        Integer[] intArray = {1, 2, 3, 4, 5, 6};
                        List<Integer> intList = Arrays.asList(intArray);
                        Collections.shuffle(intList);
                        intList.toArray(intArray);
                        for (int rand : intArray) {
                            TileModel TM = TileModel.values()[(rand > 2) ? 0 : 1];
                            Directions orientation = Directions.values()[rand % 4];
                            Tiles tile = new Tiles(TM, orientation);
                            if (GB.testConformity(nextBt, tile)) {
                                GB.playTile(nextBt, tile);
                                return;
                            }
                        }
                    }
                }
            }
        }
        ComputerMove(GB, nextBt);
    }
}

class WeightedComputerPlayer extends ComputerPlayer {
    public void ComputerMove(GameBoard GB, BoardTile bt) {}
}
