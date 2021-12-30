import java.awt.Dimension;

public class Game implements PanelButtonObserver, TileButtonObserver{
	int ntiles;
	int tilesize;
	int side;
	TileButton[][] buttonGrid;
	
	PanelButton current;
	Colors turn = Colors.W;
	
	public Game(int ntiles, int tilesize) {
		this.ntiles = ntiles;
		this.tilesize = tilesize;
		this.side = ntiles*tilesize;
		this.buttonGrid = new TileButton[ntiles][ntiles];
		
		for (int row = 0; row < ntiles; row++) {
			for (int col = 0; col < ntiles; col++) {
				TileButton button = new TileButton(buttonGrid, side/ntiles, this);
				button.setPreferredSize(new Dimension(side/tilesize, side/tilesize));
				
				if (row == Math.ceil(ntiles/2) && col == Math.ceil(ntiles/2)) {
					button.tile = new Tiles(TileModel.CROSS, 0);
				}
				
				buttonGrid[row][col] = button;
			}
		}
	}
	
	public void setCurrent(PanelButton pb) {
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
			turn = null;
		}
	}
	
	
}
