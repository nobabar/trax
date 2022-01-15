import java.util.ArrayList;
import java.util.List;

/**
 * A computer player that use a prediction method to determine its moves.
 */
public class ComplexComputerPlayer extends ComputerPlayer {
    public void ComputerMove(GameBoard GB, BoardTile unused) {
        int depth = 4; // by default iteration depth is set to 4

        BoardGrid grid = new BoardGrid(GB.grid);

        int[][] moveList = freeMove(grid);
        int bestMove = 0;
        int numMove = 0;

        for (int move = 0 ; move < moveList.length ; move++) {
            System.out.println("move to: ("+moveList[move][0]+","+moveList[move][1]+")");
            int n = MinMax(moveList[move], grid, depth);
            if (n > numMove) { // initial depth is 6, we want to preserve a high depth
                numMove = n;
                bestMove = move;
            }
        }
        int[] coordinates = moveList[bestMove];
        BoardTile nextBt = grid.getTile(coordinates[0], coordinates[1]);
        GB.playTile(nextBt, randomValidTile(grid, nextBt));
    }

    /**
     * Explore move possibilities to determine the most optimal one.
     * @param move a move to start with
     * @param BG the board grid to play on.
     * @param depth the current depth reached.
     * @return the depth reached, 0 if no winning move was determined.
     */
    private int MinMax(int[] move, BoardGrid BG, int depth) {
        if (depth > 0) { // while max depth is not reached yet
            BoardTile nextBt = BG.getTile(move[0], move[1]);
            BG.getTile(move[0], move[1]).setTile(randomValidTile(BG, nextBt));
            if (!(BG.isGameOver(nextBt, null) == null)) { // if the move is a winning move return the depth reached
                int[][] moveList = freeMove(BG);
                for (int[] nextMove : moveList) {
                    return MinMax(nextMove, BG, depth--) + depth;// otherwise, continue the exploration with recursion
                }
            }
        }
        return depth;
    }

    /**
     * List all possible moves on the current board
     * @param BG the board to play on.
     * @return a two-dimensional array containing the coordinates (row and col) of possible moves.
     */
    private int[][] freeMove(BoardGrid BG){
        List<int[]> moveList = new ArrayList<>();

        for (int row = 0; row < BG.nTiles; row++) {
            for (int col = 0; col < BG.nTiles; col++) {
                if (BG.getTile(row, col).getModel() == null ) {
                    for (Directions dir : Directions.values()) {
                        int nextRow = row + dir.motion(0);
                        int nextCol = col + dir.motion(1);
                        if (nextRow >= 0 && nextRow < BG.nTiles && nextCol >= 0 && nextCol < BG.nTiles) {
                            Tiles neighborTile = BG.getTile(nextRow, nextCol);
                            if (neighborTile.getModel() != null) {
                                moveList.add(new int[] {row, col});
                                break;
                            }
                        }
                    }
                }
            }
        }
        return moveList.toArray(new int[0][0]);
    }
}