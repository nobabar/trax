enum Directions {
	N ("nord"),
	E ("est"),
	S ("sud"),
	W ("ouest");
	
	static int[][] move = {
			{-1, 0},
			{0, 1},
			{1, 0},
			{0, -1}
	};
	
	String name;
	
	private Directions(String name) {
		this.name = name;
	}
	
	public int motion(int axis) {
		return move[this.ordinal()][axis];
	}
	
	public int[] motion() {
		return move[this.ordinal()];
	}
	
	public Directions opposite() {
		return Directions.values()[(this.ordinal()+2)%4];
	}
	
	public String toString() {
		return this.name;
	}
}

enum Colors {
	W ("blanc"),
	R ("rouge");
	
	String name;
	
	private Colors(String name) {
		this.name = name;
	}
	
	public Colors opposite() {
		return Colors.values()[(this.ordinal()+1)%2];
	}
	
	public String toString() {
		return this.name;
	}
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
}
