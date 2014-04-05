import java.awt.Graphics;
import java.awt.Image;

/** Handles the position and drawing of the game tiles. */
public abstract class Tile {
	 // The game position is also stored, since it's easier than
	 // collision detection (and I built it that way from the start)
	 private int gameX;
	 private int gameY;
	 private int screenX;
	 private int screenY;

	 public Tile() {}

	 public Tile(int gameX, int gameY) {
		  setGameX(gameX);
		  setGameY(gameY);
		  setScreenX(gameX * getImageWidth());
		  setScreenY(gameY * getImageHeight());
	 }

	 public int getGameX() { return gameX; }
	 public int getGameY() { return gameY; }
	 public int getScreenX() { return screenX; }
	 public int getScreenY() { return screenY; }
	 public void setGameX(int x) { gameX = x; }
	 public void setGameY(int y) { gameY = y; }
	 public void setScreenX(int x) { screenX = x; }
	 public void setScreenY(int y) { screenY = y; }

	 public int getImageWidth() { return getImage().getWidth(null); }
	 public int getImageHeight() { return getImage().getHeight(null); }
	 public abstract Image getImage();

	 public void draw(Graphics g) {
		  g.drawImage(getImage(), getScreenX(), getScreenY(), null);
	 }
}