import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Buttons from the panel.
 */
public class PanelButton extends Button {
    public static final long serialVersionUID = 1611451222120201514L;

    final PanelButtonObserver pbo; // observation interface

    /**
     * @param cSize button's size.
     * @param pt button's tile.
     * @param pbo observation interface to set the select panel button.
     */
    public PanelButton(int cSize, PanelTile pt, PanelButtonObserver pbo) {
        super (pt, cSize);
        this.pbo = pbo;
        ActionListener al = e -> {
            pbo.setCurrent(PanelButton.this); // set current tile when pressed
            PanelButton.this.setBorder(new LineBorder(Color.RED));
        };
        this.addActionListener(al);
    }
}