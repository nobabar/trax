import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

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

	Colors turn = Colors.W;
	JLabel turnLabel = new JLabel("White's turn");

	public void notify(boolean success) {
		if (success) {
			switch (turn) {
			case W:
				turnLabel.setText("Red's turn");	
				turn = Colors.R;
				break;
			case R:
				turnLabel.setText("White's turn");
				turn = Colors.W;
				break;
			}
		} else {
			turnLabel.setText("Invalid tile");
		}
	}

	public void win(boolean state) {
		if (state) {
			turnLabel.setText("WIN!!");
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
				button.setPreferredSize(new Dimension(nBoardTiles, nBoardTiles));
			}
		}
		Board b = new Board(boardGrid, BoardButtonSize);
		b.setBackground(Color.BLACK);
		this.add(b);

		Box superbox = new Box(BoxLayout.Y_AXIS);
		
		int PanelButtonSize = 100;
		int nRow = GB.panelGrid.length;
		int nCol = GB.panelGrid[0].length;
		JPanel p = new JPanel(new GridLayout(nRow, nCol));
		this.panelGrid = new PanelButton[nRow][nCol];
		for (int row = 0; row < nRow; row++) {
			for (int col = 0; col < nCol; col++) {
				panelGrid[row][col] = new PanelButton(PanelButtonSize, 
						GB.panelGrid[row][col], GB);
				p.add(panelGrid[row][col]);
			}
		}
		superbox.add(p);

		Box toolbox = new Box(BoxLayout.X_AXIS);
		ActionListener clearAL = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				b.clear();
			}
		};
		JButton clear = new JButton("clear");
		clear.setPreferredSize(new Dimension(60, 40));
		clear.addActionListener(clearAL);
		toolbox.add(clear);

		toolbox.add(turnLabel);

		superbox.add(toolbox);
		this.add(superbox);
		this.repaint();
	}
}