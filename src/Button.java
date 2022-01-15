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

	/**
	 * Overrides how are button displayed.
	 * @param g what to display.
	 */
	protected void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

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
