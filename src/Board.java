import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

class Board extends JPanel {
	int ntiles;
	int tilesize;
	int side;
	TileButton[][] buttonGrid;
	
	public static final long serialVersionUID = 2151184L;
	
	public Board(int ntiles, int tilesize, TileButtonObserver tbo) {
		this.ntiles = ntiles;
		this.tilesize = tilesize;
		this.side = ntiles*tilesize;
		this.buttonGrid = new TileButton[ntiles][ntiles];
		this.setPreferredSize(new Dimension(side, side));
		this.setLayout(new GridLayout(ntiles, ntiles));
		
		for (int row = 0; row < ntiles; row++) {
			for (int col = 0; col < ntiles; col++) {
				TileButton button = new TileButton(buttonGrid, side/ntiles, tbo);
				button.setPreferredSize(new Dimension(side/tilesize, side/tilesize));
				
				if (row == Math.ceil(ntiles/2) && col == Math.ceil(ntiles/2)) {
					button.tile = new Tiles(TileModel.CROSS, 0);
				}
				
				this.add(button);
				buttonGrid[row][col] = button;
			}
		}
	}
	
	void clear() {
		for (int row = 0; row < ntiles; row++) {
			for (int col = 0; col < ntiles; col++) {
				if (row != Math.ceil(ntiles/2) || col != Math.ceil(ntiles/2)) {
					buttonGrid[row][col].tile = null;
				}
			}
		}
		repaint();
	}
}