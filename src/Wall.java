import java.awt.Image;
import java.util.Vector;

/**
 *	Wall tile in Sokoban. A pusher's bane.
 * @author Florent Marchand de Kerchove 
 */
public class Wall extends Tile {
	 private static final Image img = Config.wallImg;
	 public Wall(int x, int y) { super(x,y); }
	 public Image getImage() { return img; }
}