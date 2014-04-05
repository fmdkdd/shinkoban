import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Vector;

/**
 * Hosts a map of Sokoban and dynamic objects of levels : the pusher and boxes.
 * @author Florent Marchand de Kerchove
 */
public class Level {
	 private static final String levelFileSuffix = Config.levelFileSuffix;

	 private String name;
	 private int width;
	 private int height;
	 private Map map;
	 private Vector<Box> boxes;
	 private Pusher pusher;

	 public void setPusher(Pusher p) { pusher = p; }
	 public Pusher getPusher() { return pusher; }
	 public Map getMap() { return map; }
	 public String getName() { return name; }
	 public int getWidth() { return width; }
	 public int getHeight() { return height; }

	 public void update() {
		  map.update();
		  for (Box b : boxes)
				b.update();
		  pusher.update();
	 }

	 public void draw(Graphics g) {
		  map.draw(g);
		  for (Box b : boxes)
				b.draw(g);
		  pusher.draw(g);
	 }

	 /**
	  * Loads a level from the supplied file.
	  * Used symbols :
	  * Wall               #
	  * Box                $
	  * Box on goal        *
	  * Pusher             @
	  * Pusher on goal     +
	  * Goal               !
	  * Floor              .
	  * Torch              T
	  */
	 public void load(String filename) {
		  name = filename.substring(0, filename.length()-levelFileSuffix.length());
		  boxes = new Vector<Box>();
		  map = new Map();
		  int gameX = 1;
		  int gameY = 0;
		  int maxX = 0;
		  try {
				BufferedReader br = new BufferedReader(new FileReader(filename));
 
				while (br.ready()) {
					 int c = br.read();
					 switch(c) {
					 case '#':
						  map.add(new Wall(gameX,gameY));
						  break;
					 case '@':
						  pusher = new Pusher(this,gameX,gameY);
						  map.add(new Floor(gameX, gameY));
						  break;
					 case '+':
						  pusher = new Pusher(this,gameX,gameY);
						  map.add(new Goal(gameX,gameY));
						  map.add(new Floor(gameX, gameY));
						  break;
					 case '!':
						  map.add(new Goal(gameX,gameY));
						  map.add(new Floor(gameX, gameY));
						  break;
					 case '$':
						  add(new Box(this,gameX,gameY));
						  map.add(new Floor(gameX, gameY));
						  break;
					 case '*' :
						  add(new Box(this,gameX,gameY));
						  map.add(new Goal(gameX,gameY));
						  map.add(new Floor(gameX, gameY));
						  break;
					 case '.':
						  map.add(new Floor(gameX, gameY));
						  break;
					 case 'T':
						  map.add(new Torch(gameX, gameY));
						  break;
					 case '\n':
						  if (gameX > maxX) maxX = gameX;
						  gameX = 0;
						  ++gameY;
						  break;
					 }
					 ++gameX;
				}
				width = maxX * Config.floorImg.getWidth(null);
				height = gameY * Config.floorImg.getHeight(null);
		  } catch (Exception e) {
				e.printStackTrace();
		  }
	 }

	 /** Return true iff all the boxes are on goal tiles. */
	 public boolean isSolved() {
		  return getBoxesInGoals() == boxes.size();
	 }

	 /** Return the number of boxes that are on goal tiles. */
	 public int getBoxesInGoals() {
		  int n = 0;

		  for (Box b : boxes) {
				if (map.isGoal(b.getGameX(), b.getGameY()))
					 ++n;
		  }

		  return n;
	 }

	 /** Reload the current level */
	 public void reset() {
		  load(name + Config.levelFileSuffix);
	 }

	 public void add(Box b) {
		  if (!boxes.contains(b))
				boxes.add(b);
	 }

	 public boolean isBox(int gameX, int gameY) {
		  return getBox(gameX, gameY) != null;
	 }

	 public Box getBox(int gameX, int gameY) {
		  for (Box b : boxes) {
				if (gameX == b.getGameX() && gameY == b.getGameY())
					 return b;
		  }
		  return null;
	 }

	 public Tile getFloorTile(int gameX, int gameY) {
		  return map.getFloorTile(gameX, gameY);
	 }
}