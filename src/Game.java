import javax.swing.JFrame;

public class Game {
	public static void main(String[] args) {
		GameBoard GB = new GameBoard(16);
		Frame F = new Frame(GB);
		
		GB.setObserver(F);
		
		JFrame frame = new JFrame("Trax");
		frame.getContentPane().add(F);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
