import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

/**
 * Define general buttons' method and how to display the tiles on them.
 */
abstract class Button extends JButton{
	public static final long serialVersionUID = 22120201514L;

	Tiles tile; // button's associated tile
	int cSize; // button's default size

	static BufferedImage cross;
	static BufferedImage curve;
	static {
		try {
			cross = ImageIO.read(new File(".\\img\\tile_cross.gif")); // load the image for the cross tile
			curve = ImageIO.read(new File(".\\img\\tile_curve.gif")); // load the image for the curve tile
		} catch (IOException ignored) {} // This should never happen
	}

	/**
	 * Create a general button.
	 * @param tile button's associated tile.
	 * @param cSize button's size.
	 */
	public Button(Tiles tile, int cSize) {
		this.tile = tile;
		this.cSize = cSize;
		this.setPreferredSize(new Dimension(cSize, cSize)); // buttons are squares
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.setBorder(new LineBorder(Color.WHITE));
	}

	protected void paintComponent (Graphics g) { // overrides how are button displayed
		Graphics2D g2 = (Graphics2D)g.create();

		if (tile.getModel() != null) {
			int rotation = 0;
			switch (tile.getOrientationInt()) { // display the button's tile with correct rotation
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

			BufferedImage image = switch (tile.getModel()) { // select tile image
				case CROSS -> cross;
				case CURVE -> curve;
			};

			g2.rotate(Math.toRadians(rotation), (double) cSize/2, (double) cSize/2); // rotation around the button's center
			g2.drawImage(image, 0, 0, cSize, cSize, null); // adapt the image size to button's size
		}
	}
}

/**
 * Buttons from the panel.
 */
class PanelButton extends Button {
	public static final long serialVersionUID = 1611451222120201514L;
	
	final PanelButtonObserver pbo; // observation interface

	/**
	 * @param cSize button's size.
	 * @param pt button's tile.
	 * @param pbo observation interface to set the select panel button.
	 */
	public PanelButton(int cSize, PanelTile pt, PanelButtonObserver pbo) {
		super (pt, cSize);
		this.pbo = pbo;
		ActionListener al = e -> {
			pbo.setCurrent(PanelButton.this); // set current tile when pressed
			PanelButton.this.setBorder(new LineBorder(Color.RED));
		};
		this.addActionListener(al);
	}
}

/**
 * Buttons from the board.
 */
class BoardButton extends Button {
	public static final long serialVersionUID = 20912522120201514L;
	
	final BoardButtonObserver tbo; // observation interface

	/**
	 * @param cSize button's size.
	 * @param bt button's tile.
	 * @param tbo observation interface to play tiles.
	 */
	public BoardButton(int cSize, BoardTile bt, BoardButtonObserver tbo) {
		super (bt, cSize);
		this.tbo = tbo;
		ActionListener al = e -> {
			if(bt.getModel() == null) {
				tbo.playTile(bt); // play tile here when pressed
			}
		};
		this.addActionListener(al);
	}
}
