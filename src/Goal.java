import java.awt.Image;

/** 
 * Goal tile in Sokoban, where boxes rest. 
 * @author Florent Marchand de Kerchove 
 */
public class Goal extends Tile {
	 private static final Image img = Config.goalImg;
	 public Goal(int x, int y) { super(x,y); }
	 public Image getImage() { return img; }
}