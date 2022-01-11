import java.util.Arrays;
import java.awt.Color;
import javax.swing.border.LineBorder;

public class GameBoard implements PanelButtonObserver, BoardButtonObserver{
	GameObserver GO;

	int ntiles;
	BoardTile[][] boardGrid;
	PanelTile[][] panelGrid;
	int playedTiles = 0;

	PanelButton current;
	Colors turn = Colors.W;

	public GameBoard(int ntiles) {		
		panelGrid = new PanelTile[][] {
			{new PanelTile(TileModel.CROSS, Directions.N, this),
				new PanelTile(TileModel.CROSS, Directions.E, this)},
			{new PanelTile(TileModel.CURVE, Directions.N, this),
					new PanelTile(TileModel.CURVE, Directions.W, this)},
			{new PanelTile(TileModel.CURVE, Directions.S, this),
						new PanelTile(TileModel.CURVE, Directions.E, this)}
		};

		this.ntiles = ntiles;
		this.boardGrid = new BoardTile[ntiles][ntiles];

		for (int row = 0; row < ntiles; row++) {
			for (int col = 0; col < ntiles; col++) {
				BoardTile bt = new BoardTile(this);
				boardGrid[row][col] = bt;
			}
		}
	}
	
	public void clear() {
		for (int row = 0; row < ntiles; row++) {
			for (int col = 0; col < ntiles; col++) {
				boardGrid[row][col].clear();;
			}
		}
		playedTiles = 0;
	}

	public void setObserver(GameObserver GO) {
		this.GO = GO;
	}

	public void setCurrent(PanelButton pb) {
		if (current != null) {
			current.setBorder(new LineBorder(Color.WHITE));
		}
		this.current = pb;
	}

	public void playTile(BoardTile bt) {
		if (current != null) {
			if (playedTiles == 0 || testConformity(bt, current.tile)) {
				bt.setTile(current.tile);
				playedTiles++;
				turn = turn.opposite();
				GO.nextturn(turn);
				isGameOver(bt);
			}
			current.setBorder(new LineBorder(Color.WHITE));
			current = null;
		}
	}

	private int[] getCoordinates(BoardTile bt) {
		for (int row = 0; row < boardGrid.length; row++) {
			for (int col = 0; col < boardGrid[0].length; col++) {
				if (boardGrid[row][col] == bt) {
					return new int[]{row, col};
				}
			}
		}
		return null;
	}

	private Tiles adjacentTile(int[] coordinates, Directions dir) {
		int row = coordinates[0]+dir.motion(0);
		int col = coordinates[1]+dir.motion(1);
		if (row >= 0 && row < ntiles && col >= 0 && col < ntiles) {
			Tiles tile = boardGrid[row][col];
			if (tile.getModel() != null) {
				return tile;
			}
		}
		return null;
	}

	private boolean testConformity(BoardTile bt, Tiles tile) {
		boolean conform = true;
		boolean neighbor = false;
		int[] coordinates = getCoordinates(bt);
		for (Directions dir : Directions.values()) {
			Colors tileColor = tile.getColor(dir.ordinal());
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

	private void isGameOver(BoardTile bt) {
		if (playedTiles >= 4) {
			for (Colors col : new Colors[] {turn, turn.opposite()}) {
				Directions[] dir = bt.getPath(col);
				int[] firstPath = dichotomyGO(bt, dir[0]);
				int[] secondPath = dichotomyGO(bt, dir[1]);
				
				if ((firstPath.length == 0 && secondPath.length == 0) 
						|| Math.max(firstPath[1], secondPath[1]) 
						- Math.min(firstPath[0], secondPath[0]) >= 6
						|| Math.max(firstPath[3], secondPath[3]) 
						- Math.min(firstPath[2], secondPath[2]) >= 6) {
					GO.win(col);
					return;
				}
			}	
		}
	}

	private int[] dichotomyGO(BoardTile bt, Directions dir) {
		int[] coordinates = getCoordinates(bt);
		int row = coordinates[0] + dir.motion(0);
		int col = coordinates[1] + dir.motion(1);

		int minrow = coordinates[0];
		int maxrow = coordinates[0];
		int mincol = coordinates[1];
		int maxcol = coordinates[1];

		Directions nextDir = dir;
		for ( ;; ) {
			if (row >= 0 && row < ntiles && col >= 0 && col < ntiles) {
				BoardTile nextTile = boardGrid[row][col];
				if (nextTile.getModel() != null) {
					if (row < minrow) {
						minrow = row;
					} else if (row > maxrow) {
						maxrow = row;
					}

					if (col < mincol) {
						mincol = col;
					} else if (col > maxcol) {
						maxcol = col;
					}
					
					nextDir = nextTile.getPath(nextDir.opposite());
					row += nextDir.motion(0);
					col += nextDir.motion(1);
					if (Arrays.equals(new int[] {row, col}, coordinates)) {
						return new int[0];
					}
					continue;
				}
			}
			return new int[] {minrow, maxrow, mincol, maxcol};
		}
	}
}
