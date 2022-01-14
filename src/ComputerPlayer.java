import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.*;

public abstract class ComputerPlayer {
    public ComputerPlayer() {}

    public abstract void ComputerMove(GameBoard GB, BoardTile bt);

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

class SimpleComputerPlayer extends ComputerPlayer {
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

class ComplexComputerPlayer extends ComputerPlayer {
    public void ComputerMove(GameBoard GB, BoardTile unused) {
        int depth = 5; // by default iteration depth is set to 6

        BoardGrid grid = new BoardGrid(GB.grid);

        int[][] moveList = freeMove(grid);
        int bestMove = 0;
        int numMove = Integer.MAX_VALUE;

        for (int move = 0 ; move < moveList.length ; move++) {
            System.out.println("move to: ("+moveList[move][0]+","+moveList[move][1]+")");
            int n = MinMax(moveList[move], grid, depth);
            if (n < numMove) {
                numMove = n;
                bestMove = move;
            }
        }
        int[] coordinates = moveList[bestMove];
        BoardTile nextBt = grid.getTile(coordinates[0], coordinates[1]);
        GB.playTile(nextBt, randomValidTile(grid, nextBt));
    }

    private int MinMax(int[] move, BoardGrid BG, int depth) {
        if (depth > 0) {
            BoardTile nextBt = BG.getTile(move[0], move[1]);
            BG.getTile(move[0], move[1]).setTile(randomValidTile(BG, nextBt));
            if (!(BG.isGameOver(nextBt, null) == null)) {
                int[][] moveList = freeMove(BG);
                for (int[] nextMove : moveList) {
                    return MinMax(nextMove, BG, depth--) + depth;
                }
            }
        }
        return depth;
    }

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
