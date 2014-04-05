import java.awt.Image;

/** Handles moving tiles. Most of the work is delegated
 *	 to subclasses to allow custom behavior. */
public abstract class Sprite extends Tile {
	 private static final int spriteXStep = Config.spriteXStep;
	 private static final int spriteYStep = Config.spriteYStep;

	 public enum State { STANDING, MOVING, PUSHING };
	 public enum Direction { UP, DOWN, LEFT, RIGHT };

	 private State state = State.STANDING;
	 private Direction dir = Direction.DOWN;
	 // where to go
	 private int nScreenX;
	 private int nScreenY;
	 // how fast to go there
	 private int xstep = spriteXStep;
	 private int ystep = spriteYStep;

	 public Sprite() {}

	 public Sprite(int gameX, int gameY) {
		  super(gameX,gameY);
	 }

	 public State getState() { return state; }
	 public Direction getDirection() { return dir; }
	 public int getNScreenX() { return nScreenX; }
	 public int getNScreenY() { return nScreenY; }
	 public int getXstep() { return xstep; }
	 public int getYstep() { return ystep; }
	 public void setState(State s) { state = s; }
	 public void setDirection(Direction d) { dir = d; }
	 public void setNScreenX(int x) { nScreenX = x; }
	 public void setNScreenY(int y) { nScreenY = y; }
	 
	 public abstract Image getImage();
	 /** Whether the sprite can move in the specified location. */
	 public abstract boolean canMove(int dGameX, int dGameY);
	 /** Move the sprite to the specified location. */
	 public abstract void move(int dGameX, int dGameY);
	 /** Update the sprite logic (e.g. states transition) */
	 public abstract void update();
}