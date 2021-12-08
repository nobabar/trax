import javax.swing.ImageIcon;

enum Directions {
	N,
	E,
	S,
	W;
}

enum Sides {
	U,
	R,
	D,
	L;
}

enum Colors {
	W,
	R;
}

enum TileModel {
	CROSS,
	CURVE;
	
	int rotation;
	static Colors[][] color = {
			{Colors.W, Colors.R, Colors.W, Colors.R},
			{Colors.W, Colors.R, Colors.R, Colors.W}
	};
	static ImageIcon icon_cross = new ImageIcon(".\\img\\tile_cross.gif");
	static ImageIcon icon_curve = new ImageIcon(".\\img\\tile_curve.gif");
	static RotateIcon[][] icon = {
			{new RotateIcon(icon_cross, Rotate.UP),
				new RotateIcon(icon_cross, Rotate.LEFT),
				new RotateIcon(icon_cross, Rotate.DOWN),
				new RotateIcon(icon_cross, Rotate.RIGHT)},
			{new RotateIcon(icon_curve, Rotate.UP),
				new RotateIcon(icon_curve, Rotate.LEFT),
				new RotateIcon(icon_curve, Rotate.DOWN),
				new RotateIcon(icon_curve, Rotate.RIGHT)}
	};
	
	public String Show(int orientation) {
		return "--"+color[ordinal()][(Directions.N.ordinal()+orientation)%4]+"--\n"+
				color[ordinal()][(Directions.W.ordinal()+orientation)%4]+"---"+color[ordinal()][(Directions.E.ordinal()+orientation)%4]+"\n"+
			   "--"+color[ordinal()][(Directions.S.ordinal()+orientation)%4]+"--";
	}
	
	public RotateIcon getIcon(int orientation) {
		return icon[ordinal()][orientation];
	}
}

public class Tiles {
	TileModel model;
	int orientation;
	
	public Tiles(TileModel model, int orientation) {
		this.model = model;
		this.orientation = orientation;
	}
	
	public Tiles(Tiles tile) {
		
	}
	
	public String toString() {
		return model.Show(orientation);
	}
	
	public RotateIcon toIcon() {
		return model.getIcon(orientation);
	}
	
	public Colors getColor(int dir) {
		return model.color[model.ordinal()][(dir+orientation)%4];
	}
}
