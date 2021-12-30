import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JPanel implements TileButtonObserver, PanelButtonObserver{
	public static final long serialVersionUID = 6181135L;
	
	Colors turn = Colors.W;
	JLabel turnLabel = new JLabel("White's turn");
	PanelButton current;
	
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

	public void setCurrent(PanelButton tile) {
		this.current = tile;
	}
	
	public Frame() {
		int ntiles = 15;
		int tilesize = 50;
		
		Board b = new Board(ntiles, tilesize, this);
		b.setBackground(Color.BLACK);
		this.add(b);

		Box superbox = new Box(BoxLayout.Y_AXIS);
		Box tilebox = new Box(BoxLayout.X_AXIS);
		Box leftbox = new Box(BoxLayout.Y_AXIS);
		Box rightbox = new Box(BoxLayout.Y_AXIS);
		superbox.add(tilebox);
		tilebox.add(leftbox);
		tilebox.add(rightbox);

		int buttonSize = 100;

		leftbox.add(new PanelButton(new Tiles(TileModel.CROSS, 0), buttonSize, this));
		rightbox.add(new PanelButton(new Tiles(TileModel.CROSS, 1), buttonSize, this));

		leftbox.add(new PanelButton(new Tiles(TileModel.CURVE, 0), buttonSize, this));
		rightbox.add(new PanelButton(new Tiles(TileModel.CURVE, 3), buttonSize, this));
		leftbox.add(new PanelButton(new Tiles(TileModel.CURVE, 2), buttonSize, this));
		rightbox.add(new PanelButton(new Tiles(TileModel.CURVE, 1), buttonSize, this));

		ActionListener clearAL = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				b.clear();
			}
		};
		JButton clear = new JButton("clear");
		clear.setPreferredSize(new Dimension(60, 120));
		clear.addActionListener(clearAL);
		superbox.add(clear);

		superbox.add(turnLabel);

		this.add(superbox);
		this.repaint();
	}

	public static void main(String[] args) {
		Frame f = new Frame();
		JFrame frame = new JFrame("Jimp");
		frame.getContentPane().add(f);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);	
	}
}