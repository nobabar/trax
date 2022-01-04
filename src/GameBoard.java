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

	boolean illegalMove = false;

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

	public void setObserver(GameObserver GO) {
		this.GO = GO;
	}

	public void setCurrent(PanelButton pb) {
		if (current != null) {
			current.setBorder(new LineBorder(Color.WHITE));
		}
		this.current = pb;
	}

	public void notify(boolean success) {
		if (success) {
			switch (turn) {
			case W:
				turn = Colors.R;
				break;
			case R:
				turn = Colors.W;
				break;
			}
		} else {
			illegalMove = true;
		}
	}

	public void playTile(BoardTile bt) {
		if (current != null) {
			if (playedTiles == 0 || testConformity(bt, current.tile)) {
				bt.setTile(current.tile);
				playedTiles++;
				if (isGameOver(bt)) {
					GO.win(true);
				}
			}
			current.setBorder(new LineBorder(Color.WHITE));
			current = null;
		}
	}

	int[] getCoordinates(BoardTile bt) {
		for (int row = 0; row < boardGrid.length; row++) {
			for (int col = 0; col < boardGrid[0].length; col++) {
				if (boardGrid[row][col] == bt) {
					return new int[]{row, col};
				}
			}
		}
		return null;
	}

	Tiles adjacentTile(int[] coordinates, Directions dir) {
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

	boolean testConformity(BoardTile bt, Tiles tile) {
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

	boolean isGameOver(BoardTile bt) {
		if (playedTiles >= 4) {
			Directions[] dir = bt.getPath(turn);
			int firstPath = isLine(bt, dir[0]);
			int secondPath = isLine(bt, dir[1]);
			if (firstPath + secondPath + 1 >= 7) {
				return true;
			}
		}
		return false;
	}

	int isLine(BoardTile bt, Directions dir) {
		int[] coordinates = getCoordinates(bt);
		int row = coordinates[0] + dir.motion(0);
		int col = coordinates[1] + dir.motion(1);
		int length = 0;
		Directions nextDir = dir;
		for ( ;; ) {
			if (row >= 0 && row < ntiles && col >= 0 && col < ntiles) {
				BoardTile nextTile = boardGrid[row][col];
				if (nextTile.getModel() != null) {
					nextDir = nextTile.getPath(nextDir.opposite());
					row += nextDir.motion(0);
					col += nextDir.motion(1);
					length ++;
					continue;
				}
			}
			return length;
		}
	}

	void isLoop(BoardTile bt, Directions dir) {

	}
}
