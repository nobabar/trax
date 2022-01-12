import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * main class, we need to start somewhere.
 */
public class Game {
	private static GameBoard GB;

	/**
	 * @param args
	 * Initial launch of the application
	 */
	public static void main(String[] args) {
		launch();
	}

	/**
	 * Launch a new game, with a new game board.
	 */
	public static void launch() {
		int size = 8;
		boolean err = true;
		do {
			try{
				String input = JOptionPane.showInputDialog(null, "Enter game board size (between 8 and 24) :");
				if (input == null || input.length() == 0) {
					System.exit(0);
				}
				size = Integer.parseInt(input);
				err = false;
			}catch(NumberFormatException ignored) {}
		} while (err || size < 8 || size > 24); // loop over again if no size is specified or if it is incorrect
		GB = new GameBoard(size);

		Frame F = new Frame(GB);

		GB.setObserver(F);

		JFrame frame = new JFrame("Trax");
		frame.getContentPane().add(F);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Keep the current game board but resets the tiles.
	 */
	public static void clear() {
		GB.clear();
	}
}
