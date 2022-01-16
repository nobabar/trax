import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Color;

/**
 * The main user interface, defined the graphics components and arrange them.
 */
public class Frame extends JPanel implements GameObserver {
    public static final long serialVersionUID = 6181135L;

    final BoardButton[][] boardGrid; // grid of the game board buttons
    final PanelButton[][] panelGrid; // grid of the game panel buttons

    JLabel turnLabel = new JLabel("white player's turn");

    /**
     * Switch the players' turn display, called from the game board.
     *
     * @param player the player whose turn it is.
     */
    public void nextTurn(Colors player) {
        if (player != null) {
            turnLabel.setText(player + " player's turn");
        } else { // if no player is specified that mean it was an invalid move
            turnLabel.setText("invalid tile!");
        }
        repaint();
    }

    /**
     * React to the game over call from game board.
     *
     * @param winner the winning player.
     */
    public void win(Colors winner) {
        if (winner != null) {
            String message;
            if (Game.getComputerPlayer() != null) {
                message = winner == Colors.W ? "player" : "AI";
            } else {
                message = winner + " player";
            }
            message += " won!\nNew game?";

            String[] options = new String[]{"Yes", "No", "New board"};
            int response = JOptionPane.showOptionDialog(null,
                    message,
                    "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);
            switch (response) {
                case 0 -> {
                    Game.clear(); // only cleans the board if players want a new game
                    repaint();
                }
                case 1 -> System.exit(0); // close the application
                case 2 -> {
                    SwingUtilities.getWindowAncestor(this).dispose(); // dispose this board
                    Game.launch(); // and create a new one
                }
            }
        }
    }

    /**
     * Creation of the main frame.
     *
     * @param GB the game board linked to this frame.
     */
    public Frame(GameBoard GB) {
        int nBoardTiles = GB.nTiles;

        this.setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel();

        Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize(); // the frame can not be bigger than screen
        int BoardButtonSize = ((int) Math.round(DimMax.height * 0.75)) / nBoardTiles; // adapt tile size to this maximum

        this.boardGrid = new BoardButton[nBoardTiles][nBoardTiles];
        for (int row = 0; row < nBoardTiles; row++) {
            for (int col = 0; col < nBoardTiles; col++) {
                BoardButton button = new BoardButton(BoardButtonSize, // create a button for each location of the board
                        GB.getTile(row, col), GB);
                boardGrid[row][col] = button;
            }
        }
        Board b = new Board(boardGrid, BoardButtonSize);
        b.setBackground(Color.BLACK);
        mainPanel.add(b, BorderLayout.CENTER);

        Box superBox = new Box(BoxLayout.Y_AXIS);
        int PanelButtonSize = 100; // set a default size for panel buttons
        int nRow = GB.panelGrid.length;
        int nCol = GB.panelGrid[0].length;

        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(PanelButtonSize * nCol, PanelButtonSize * nRow));
        p.setLayout(new GridLayout(nRow, nCol));

        this.panelGrid = new PanelButton[nRow][nCol];
        for (int row = 0; row < nRow; row++) {
            for (int col = 0; col < nCol; col++) {
                PanelButton button = new PanelButton(PanelButtonSize, // create a button for each tile on the panel
                        GB.panelGrid[row][col], GB);
                panelGrid[row][col] = button;
                p.add(button);
            }
        }
        superBox.add(p);

        JButton clear = new JButton("clear");
        clear.setPreferredSize(new Dimension(100, 30));
        ActionListener clearAL = e -> {
            Game.clear(); // resets the game board
            repaint();
        };
        clear.addActionListener(clearAL);

        JPanel helperPanelClear = new JPanel();
        helperPanelClear.add(clear);
        superBox.add(helperPanelClear); // need to wrap the button in a panel to preserve its dimensions

        JButton newBoard = new JButton("new board");
        newBoard.setPreferredSize(new Dimension(100, 30));
        ActionListener createNew = e -> {
            SwingUtilities.getWindowAncestor(this).dispose(); // dispose this board
            Game.launch(); // and create a new one
        };
        newBoard.addActionListener(createNew);

        JPanel helperPanelNew = new JPanel();
        helperPanelNew.add(newBoard);
        superBox.add(helperPanelNew); // need to wrap the button in a panel to preserve its dimensions

        if (Game.getComputerPlayer() == null) {
            JPanel helperPanelLabel = new JPanel();
            helperPanelLabel.add(turnLabel);
            superBox.add(helperPanelLabel);// need to wrap the label in a panel to preserve its dimensions
        }

        mainPanel.add(superBox, BorderLayout.EAST);
        this.add(mainPanel, BorderLayout.CENTER);

        JLabel rules = new LinkLabel("rules", "http://www.gamerz.net/pbmserv/trax.html");
        JLabel sep = new JLabel(" | "); sep.setForeground(Color.BLUE.darker());
        JLabel code = new LinkLabel("source code", "https://github.com/nobabar/trax");

        JPanel bottomRightPanel = new JPanel(new BorderLayout());
        JPanel linkPanel = new JPanel();

        linkPanel.add(rules);
        linkPanel.add(sep);
        linkPanel.add(code); // wrap the three labels in a panel

        bottomRightPanel.add(linkPanel, BorderLayout.LINE_END); // add the link panel in the far right of another panel
        this.add(bottomRightPanel, BorderLayout.PAGE_END); // and put this panel on the bottom of the main frame
    }
}