import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Graphics;

import javax.swing.ImageIcon;

enum Rotate {
	UP,
	RIGHT,
	DOWN,
	LEFT;
}

public class RotateIcon extends ImageIcon {
	private ImageIcon icon;
	private Rotate rotate;
	static boolean scale = true;
	
	public static final long serialVersionUID = 1815201205931514L;
	
	public RotateIcon(ImageIcon icon, Rotate rotate) {
		this.icon = icon;
		this.rotate = rotate;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public Image getImage() {
		return icon.getImage();
	}
	
	public Rotate getRotation(){
		return rotate;
	}

	@Override
	public int getIconWidth() {
		if (rotate == Rotate.DOWN)
			return icon.getIconWidth();
		else
			return icon.getIconHeight();
	}

	@Override
	public int getIconHeight() {
		if (rotate == Rotate.DOWN)
			return icon.getIconHeight();
		else
			return icon.getIconWidth();
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D)g.create();

		int width = icon.getIconWidth()/2;
		int height = icon.getIconHeight()/2;
		
		int rotation = 0;
		switch (rotate) {
			case UP:
				break;
			case LEFT:
				rotation = 270;
				break;
			case DOWN:
				rotation = 180;
				break;
			case RIGHT:
				rotation = 90;
				break;
		}
		
		g2.translate(x+width, y+height);
		g2.rotate(Math.toRadians(rotation));

		icon.paintIcon(c, g2, -width, -height);
		g2.dispose();
	}
}