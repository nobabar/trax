import java.util.ArrayList;
import java.util.List;

enum Directions {
	N,
	E,
	S,
	W;
	
	static int[][] move = {
			{-1, 0},
			{0, 1},
			{1, 0},
			{0, -1}
	};
	
	public int motion(int axis) {
		return move[this.ordinal()][axis];
	}
	
	public int[] motion() {
		return move[this.ordinal()];
	}
	
	public Directions opposite() {
		return Directions.values()[(this.ordinal()+2)%4];
	}
}

enum Colors {
	W,
	R;
}

public enum TileModel {
	CROSS,
	CURVE;

	static Colors[][] color = {
			{Colors.W, Colors.R, Colors.W, Colors.R},
			{Colors.W, Colors.R, Colors.R, Colors.W}
	};

	public Colors[] colArray(Directions dir) {
		Colors[] array = new Colors[4];
		for (int i = 0; i < 4; i++) {
			array[i] = color[this.ordinal()][(i+dir.ordinal())%4];
		}
		return array;
	}
	
	public Colors[] colArray(int orientation) {
		Colors[] array = new Colors[4];
		for (int i = 0; i < 4; i++) {
			array[i] = color[this.ordinal()][(i+orientation)%4];
		}
		return array;
	}
	
	public Colors getColor(int dir) {
		return color[this.ordinal()][dir];
	}
	
	public Directions[] getPath(int orientation, Colors col) {
		List<Directions> sideList = new ArrayList<>();
		for (int side = 0; side < 4; side++) {
			if (color[this.ordinal()][(side+orientation)%4] == col) {
				sideList.add(Directions.values()[side]);
			}		
		}
		Directions[] sideArray = sideList.toArray(new Directions[0]);
		return sideArray;
	}
	
	public Directions getPath(int orientation, Directions dir) {
		Colors col = getColor((dir.ordinal()+orientation)%4);
		for (int side = 0; side < 4; side++) {
			if (side != dir.ordinal() && color[this.ordinal()][(side+orientation)%4] == col) {
				return Directions.values()[side];
			}
		}
		return null;
	}
}
