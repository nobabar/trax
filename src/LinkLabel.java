import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Special labels than can host a clickable link.
 */
public class LinkLabel extends JLabel {
    String display; // what to display on the label
    String link; // the link it should redirect to

    /**
     * Make the label clickable and add some reactions when hovering it with the mouse.
     */
    MouseAdapter ma = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (
                    URISyntaxException | IOException err) {
                err.printStackTrace();
            }
        }

        final Font f = LinkLabel.this.getFont();

        @Override
        public void mouseEntered(MouseEvent e) {
            LinkLabel.this.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            LinkLabel.this.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
        }
    };

    /**
     * Set a label with a name and a redirecting link.
     * @param display the string to display.
     * @param link the url to redirect to.
     */
    public LinkLabel(String display, String link){
        this.display = display;
        this.link = link;

        this.setText(display);
        this.setForeground(Color.BLUE.darker());
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // transform cursor into little hand
        this.addMouseListener(ma);
    }
}
