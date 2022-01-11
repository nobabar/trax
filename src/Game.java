import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game {
	private static GameBoard GB;
	
	private Game() {}

	public static boolean launch() {
		int size = 8;
		boolean err = true;
		do {
			try{
				String input = JOptionPane.showInputDialog(null, "Entrez la taille du plateau (entre 8 et 24) :");
				if (input == null || input.length() == 0) {
					System.exit(0);
				}
				size = Integer.parseInt(input);
				err = false;
			}catch(NumberFormatException e) {}
		} while (err || size < 8 || size > 24);
		GB = new GameBoard(size);

		Frame F = new Frame(GB);

		GB.setObserver(F);

		JFrame frame = new JFrame("Trax");
		frame.getContentPane().add(F);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		return false;
	}
	
	public static void clear() {
		GB.clear();
	}
}
