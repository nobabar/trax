import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

class Board extends JPanel {
	BoardButton[][] buttonGrid;
	int ntiles;

	public static final long serialVersionUID = 2151184L;

	public Board(BoardButton[][] boardGrid, int tilesize) {
		this.buttonGrid = boardGrid;
		this.ntiles = boardGrid.length;
		int side = ntiles*tilesize;

		this.setPreferredSize(new Dimension(side, side));
		this.setLayout(new GridLayout(ntiles, ntiles));

		for (int row = 0; row < ntiles; row++) {
			for (int col = 0; col < ntiles; col++) {				
				this.add(boardGrid[row][col]);
			}
		}
	}

	void clear() {
		for (int row = 0; row < ntiles; row++) {
			for (int col = 0; col < ntiles; col++) {
				buttonGrid[row][col].tile.clear();
			}
		}
		repaint();
	}
}