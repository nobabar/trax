/**
 * Tiles from the panel.
 */
public class PanelTile extends Tiles {
    final PanelButtonObserver pbo;

    public PanelTile(TileModel model, Directions dir, PanelButtonObserver pbo) {
        super (model, dir);
        this.pbo = pbo;
    }
}