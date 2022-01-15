import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * Visual implementation of the board game
 */
class Board extends JPanel {
	public static final long serialVersionUID = 2151184L;

	final BoardButton[][] buttonGrid; // the tile grid version in buttons
	final int nTiles; // number of tiles

	/**
	 * Create a new game board.
	 * @param boardGrid a grid of tiles.
	 * @param tileSize the size of each tile, will determine the button's size.
	 */
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