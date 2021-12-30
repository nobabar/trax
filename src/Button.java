import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

abstract class Button extends JButton{
	Tiles tile;
	int cSize;
	static PanelButton current;

	static BufferedImage cross;
	static BufferedImage curve;
	static {
		try {
			cross = ImageIO.read(new File(".\\img\\tile_cross.gif"));
			curve = ImageIO.read(new File(".\\img\\tile_curve.gif"));
		} catch (IOException e) {}
	}
	
	public static final long serialVersionUID = 22120201514L;
	
	public Button(Tiles tile, int cSize) {
		this.tile = tile;
		this.cSize = cSize;
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.setBorder(new LineBorder(Color.WHITE));
	}

	protected void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D)g.create();

		if (tile != null) {
			int rotation = 0;
			switch (tile.orientation) {
			case 0:
				break;
			case 1:
				rotation = 270;
				break;
			case 2:
				rotation = 180;
				break;
			case 3:
				rotation = 90;
				break;
			}

			BufferedImage image = null;
			switch (tile.model) {
			case CROSS:
				image = cross;
				break;
			case CURVE:
				image = curve;
				break;
			}

			g2.rotate(Math.toRadians(rotation), cSize/2, cSize/2);
			g2.drawImage(image, 0, 0, cSize, cSize, null);
		}
	}
}

class PanelButton extends Button {
	public static final long serialVersionUID = 1611451222120201514L;
	
	PanelButtonObserver pbo;
	
	public PanelButton(Tiles tile, int cSize, PanelButtonObserver pbo) {
		super (tile, cSize);
		this.pbo = pbo;
		this.setIcon(tile.toIcon());
		ActionListener al = new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				current = PanelButton.this;
				PanelButton.this.setBorder(new LineBorder(Color.BLUE, 2));
			}
		};
		this.addActionListener(al);
		this.setPreferredSize(new Dimension(100,100));
	}
}

class TileButton extends Button {
	public static final long serialVersionUID = 20912522120201514L;
	
	int nbTiles = 0;
	TileButton[][] buttonGrid;
	TileButtonObserver tbo;

	public TileButton(TileButton[][] buttonGrid, int cSize, TileButtonObserver tbo) {
		super (null, cSize);
		this.tbo = tbo;
		this.buttonGrid = buttonGrid;
		ActionListener al = new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				if (!TileButton.this.testConformity(current.tile)
						|| TileButton.this.tile != null
						|| current == null) {
					tbo.notify(false);
					current.setBorder(new LineBorder(Color.WHITE));
					current = null;
					return;
				}
				TileButton.this.tile = current.tile;
				nbTiles++;
				current.setBorder(new LineBorder(Color.WHITE));
				current = null;
				tbo.notify(true);
			}
		};
		this.addActionListener(al);
		this.setPreferredSize(new Dimension(80,80));
	}

	int[] getCoordinates() {
		for (int row = 0; row < buttonGrid.length; row++) {
			for (int col = 0; col < buttonGrid[0].length; col++) {
				if (buttonGrid[row][col] == this) {
					return new int[]{row, col};
				}
			}
		}
		return null;
	}

	Tiles adjacentTile(int[] coordinates, Directions dir) {
		int[][] side = {
				{-1, 0},
				{0, 1},
				{1, 0},
				{0, -1}
		};
		int row = coordinates[0]+side[dir.ordinal()][0];
		int col = coordinates[1]+side[dir.ordinal()][1];
		if (row >= 0 && col >= 0) {
			Tiles tile = buttonGrid[row][col].tile;
			return tile;
		} else {
			return null;
		}
	}

	boolean testConformity(Tiles tile) {
		boolean conform = true;
		boolean neighbor = false;
		int[] coordinates = this.getCoordinates();
		for (Directions dir : Directions.values()) {
			Colors tileColor = tile.getColor(dir.ordinal());
			Tiles neighborTile = adjacentTile(coordinates, dir);
			if (neighborTile != null) {
				neighbor = true;
				Colors neighborColor = neighborTile.getColor((dir.ordinal()+2)%4);
				if (tileColor != neighborColor) {
					conform = false;
				}
			}
		}
		return (conform && neighbor);
	}
	
	public Colors isGameOver(TileButton TB)
    {
        if (TB.nbTiles < 5) {
            return null;
        }
        return Colors.W;
    }
}
