import java.awt.Image;

/** Static floor tile. */
public class Floor extends Tile {
	 private static final Image img = Config.floorImg;
	 public Floor(int x, int y) { super(x,y); }
	 public Image getImage() { return img; }
}