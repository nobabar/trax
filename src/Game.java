import javax.swing.*;

/**
 * main class, we need to start somewhere.
 */
public class Game {
    private static GameBoard GB; // the game board containing the tiles
    private static ComputerPlayer cp; // a computer player version, if requested

    /**
     * @param args Initial launch of the application
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Launch a new game, with a new game board.
     */
    public static void launch() {
        // String[] options = new String[]{"2 players", "Dummy", "Computer"};
        String[] options = new String[]{"2 players", "Dummy"}; // Computer not working yet
        int response = JOptionPane.showOptionDialog(null,
                "Welcome to trax game!",
                "Trax", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        int size = 8;
        switch (response) {
            case 0 -> {
                boolean err = true;
                do {
                    try {
                        String input = JOptionPane.showInputDialog(null,
                                "Enter game board size (between 8 and 24) :", "8");
                        if (input == null || input.length() == 0) {
                            System.exit(0);
                        }
                        size = Integer.parseInt(input);
                        err = false;
                    } catch (NumberFormatException ignored) {
                    }
                } while (err || size < 8 || size > 24); // loop over again if no size is specified or if it is incorrect
                GB = new GameBoard(size);
            }
            case 1 -> cp = new SimpleComputerPlayer();
            case 2 -> cp = new ComplexComputerPlayer();
        }
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
     * Get the AI if there is any.
     *
     * @return the AI.
     */
    public static ComputerPlayer getComputerPlayer() {
        return cp;
    }

    /**
     * Keep the current game board but resets the tiles.
     */
    public static void clear() {
        GB.clear();
    }
}
