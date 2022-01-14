import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.*;

public abstract class ComputerPlayer {
    public ComputerPlayer() {}

    public abstract void ComputerMove(GameBoard GB, BoardTile bt);

    public Tiles randomValidTile(GameBoard GB, BoardTile nextBt) {
        Integer[] intArray = {1, 2, 3, 4, 5, 6};
        List<Integer> intList = asList(intArray);
        Collections.shuffle(intList);
        intList.toArray(intArray);
        for (int rand : intArray) {
            TileModel TM = TileModel.values()[(rand > 2) ? 0 : 1];
            Directions orientation = Directions.values()[rand % 4];
            Tiles tile = new Tiles(TM, orientation);
            if (GB.testConformity(nextBt, tile)) {
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
                    nextBt = GB.boardGrid[row][col];
                    if (nextBt.getModel() == null) {
                        GB.playTile(nextBt, randomValidTile(GB, nextBt));
                        return;
                    }
                }
            }
        }
        ComputerMove(GB, nextBt);
    }
}

class WeightedComputerPlayer extends ComputerPlayer {
    public void ComputerMove(GameBoard GB, BoardTile unused) {
        int depth = 6; // by default iteration depth is set to 6

        GameBoard newGB = new GameBoard(GB);
        System.out.println(newGB.boardGrid[2][2].getModel());
        int[][] moveList = freeMove(newGB.boardGrid);
        int bestMove = 0;
        int numMove = Integer.MAX_VALUE;

        for (int move = 0 ; move < moveList.length ; move++) {
            System.out.println(Arrays.toString(moveList[move]));
            int n = MinMax(moveList[move], newGB, depth);
            if (n < numMove) {
                numMove = n;
                bestMove = move;
            }
        }
        int[] coordinates = moveList[bestMove];
        BoardTile nextBt = newGB.boardGrid[coordinates[0]][coordinates[1]];
        GB.playTile(nextBt, randomValidTile(newGB, nextBt));
    }

    private int MinMax(int[] move, GameBoard GB, int depth) {
        if (depth > 0) {
            BoardTile nextBt = GB.boardGrid[move[0]][move[1]];
            GB.boardGrid[move[0]][move[1]].setTile(randomValidTile(GB, nextBt));
            if (!(GB.isGameOver(nextBt) == null)) {
                int[][] moveList = freeMove(GB.boardGrid);
                for (int[] nextMove : moveList) {
                    return MinMax(nextMove, GB, depth--) + depth;
                }
            }
        }
        return depth;
    }

    private int[][] freeMove(BoardTile[][] boardGrid){
        List<int[]> moveList = new ArrayList<>();

        for (int row = 0; row < boardGrid.length; row++) {
            for (int col = 0; col < boardGrid.length; col++) {
                if (boardGrid[row][col].getModel() == null ) {
                    for (Directions dir : Directions.values()) {
                        int nextRow = row + dir.motion(0);
                        int nextCol = col + dir.motion(1);
                        if (nextRow >= 0 && nextRow < boardGrid.length && nextCol >= 0 && nextCol < boardGrid.length) {
                            Tiles neighborTile = boardGrid[nextRow][nextCol];
                            if (neighborTile.getModel() != null) {
                                System.out.println("row:"+row+"\ncol:"+col);
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
