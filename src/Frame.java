import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JPanel implements GameObserver{
	public static final long serialVersionUID = 6181135L;

	BoardButton[][] boardGrid;
	PanelButton[][] panelGrid;

	JLabel turnLabel = new JLabel("Tour du joueur blanc");

	public void nextturn(Colors player) {
		if (player != null) {
			turnLabel.setText("Tour du joueur "+player);	
		} else {
			turnLabel.setText("Tuile invalide!");
		}
	}

	public void win(Colors winner) {
		String[] options = new String[] {"Oui", "Non", "Nouveau plateau"};
		int response = JOptionPane.showOptionDialog(null, "Joueur " + winner + " a gagné !\nNouvelle partie ?",
		        "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
		        null, options, options[0]);
		switch (response) {
		case 0:
			Game.clear();
			repaint();
			break;
		case 1:
			System.exit(0);
			break;
		case 2:
			SwingUtilities.getWindowAncestor(this).dispose();
			Game.launch();
			break;
		}
	}
	
	public Frame(GameBoard GB) {
		int nBoardTiles = GB.ntiles;
		
		Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
		int BoardButtonSize = ((int) Math.round(DimMax.height*0.75))/nBoardTiles;
		
		this.boardGrid = new BoardButton[nBoardTiles][nBoardTiles];
		for (int row = 0; row < nBoardTiles; row++) {
			for (int col = 0; col < nBoardTiles; col++) {
				BoardButton button = new BoardButton(BoardButtonSize, 
						GB.boardGrid[row][col], GB);
				boardGrid[row][col] = button;
			}
		}
		Board b = new Board(boardGrid, BoardButtonSize);
		b.setBackground(Color.BLACK);
		this.add(b);

		Box superbox = new Box(BoxLayout.Y_AXIS);
		int PanelButtonSize = 100;
		int nRow = GB.panelGrid.length;
		int nCol = GB.panelGrid[0].length;

		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(PanelButtonSize*2, PanelButtonSize*3));
		p.setLayout(new GridLayout(nRow, nCol));
		
		this.panelGrid = new PanelButton[nRow][nCol];
		for (int row = 0; row < nRow; row++) {
			for (int col = 0; col < nCol; col++) {
				PanelButton button = new PanelButton(PanelButtonSize, 
						GB.panelGrid[row][col], GB);
				panelGrid[row][col] = button;
				p.add(button);
			}
		}
		superbox.add(p);

		ActionListener clearAL = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				Game.clear();
				repaint();
			}
		};
		JButton clear = new JButton("clear");
		clear.setPreferredSize(new Dimension(80, 40));
		clear.addActionListener(clearAL);
		
		JPanel helperPanelClear = new JPanel();
		helperPanelClear.add(clear);
		superbox.add(helperPanelClear);

		JPanel helperPanelLabel = new JPanel();
		helperPanelLabel.add(turnLabel);
		superbox.add(helperPanelLabel);
		
		this.add(superbox);
	}
}