import java.awt.event.ActionListener;

/**
 * Buttons from the board.
 */
public class BoardButton extends Button {
    public static final long serialVersionUID = 20912522120201514L;

    final BoardButtonObserver tbo; // observation interface

    /**
     * @param cSize button's size.
     * @param bt button's tile.
     * @param tbo observation interface to play tiles.
     */
    public BoardButton(int cSize, BoardTile bt, BoardButtonObserver tbo) {
        super (bt, cSize);
        this.tbo = tbo;
        ActionListener al = e -> {
            if(bt.getModel() == null) {
                tbo.playTile(bt); // play tile here when pressed
            }
        };
        this.addActionListener(al);
    }
}