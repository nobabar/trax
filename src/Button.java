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
		this.setPreferredSize(new Dimension(cSize, cSize));
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.setBorder(new LineBorder(Color.WHITE));
	}

	protected void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D)g.create();

		if (tile.getModel() != null) {
			int rotation = 0;
			switch (tile.getOrientation()) {
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
			switch (tile.getModel()) {
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
	
	public PanelButton(int cSize, PanelTile pt, PanelButtonObserver pbo) {
		super (pt, cSize);
		this.pbo = pbo;
		ActionListener al = new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				pbo.setCurrent(PanelButton.this);
				PanelButton.this.setBorder(new LineBorder(Color.RED));
			}
		};
		this.addActionListener(al);
	}
}

class BoardButton extends Button {
	public static final long serialVersionUID = 20912522120201514L;
	
	BoardButtonObserver tbo;
	
	public BoardButton(int cSize, BoardTile bt, BoardButtonObserver tbo) {
		super (bt, cSize);
		this.tbo = tbo;
		ActionListener al = new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				if(bt.getModel() == null) {
					tbo.playTile(bt);
				}
			}
		};
		this.addActionListener(al);
	}
}
