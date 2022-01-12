import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

class Board extends JPanel {
	final BoardButton[][] buttonGrid;
	final int nTiles;

	public static final long serialVersionUID = 2151184L;

	public Board(BoardButton[][] boardGrid, int tileSize) {
		this.buttonGrid = boardGrid;
		this.nTiles = boardGrid.length;
		int side = nTiles*tileSize;

		this.setPreferredSize(new Dimension(side, side));
		this.setLayout(new GridLayout(nTiles, nTiles));

		for (int row = 0; row < nTiles; row++) {
			for (int col = 0; col < nTiles; col++) {
				this.add(boardGrid[row][col]);
			}
		}
	}
}