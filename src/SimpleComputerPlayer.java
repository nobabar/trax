/**
 * A simple computer player to play with. It determines its moves randomly and based on the last move the player played.
 */
public class SimpleComputerPlayer extends ComputerPlayer {
    public void ComputerMove(GameBoard GB, BoardTile bt) {
        int[] coordinates = GB.getCoordinates(bt);
        BoardTile nextBt = null;

        for (Colors color : new Colors[]{Colors.R, Colors.W}) {
            Directions[] path = bt.getPath(color);
            for (Directions dir : path) {
                int row = coordinates[0] + dir.motion(0);
                int col = coordinates[1] + dir.motion(1);
                if (row >= 0 && row < GB.nTiles && col >= 0 && col < GB.nTiles) {
                    nextBt = GB.getTile(row, col);
                    if (nextBt.getModel() == null) {
                        GB.playTile(nextBt, randomValidTile(GB.grid, nextBt));
                        return;
                    }
                }
            }
        }
        ComputerMove(GB, nextBt);
    }
}
