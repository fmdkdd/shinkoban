import java.awt.Image;
import java.util.Vector;

/** 
 * Pusher sprite in Sokoban. Pushes boxes around.
 * @author Florent Marchand de Kerchove
 */
public class Pusher extends Sprite {
	 private static final Animation standingUpAnim = Config.pusherStandingUpAnim;
	 private static final Animation standingDownAnim = Config.pusherStandingDownAnim;
	 private static final Animation standingLeftAnim = Config.pusherStandingLeftAnim;
	 private static final Animation standingRightAnim = Config.pusherStandingRightAnim;
	 private static final Animation movingUpAnim = Config.pusherMovingUpAnim;
	 private static final Animation movingDownAnim = Config.pusherMovingDownAnim;
	 private static final Animation movingLeftAnim = Config.pusherMovingLeftAnim;
	 private static final Animation movingRightAnim = Config.pusherMovingRightAnim;
	 private static final Animation pushingUpAnim = Config.pusherPushingUpAnim;
	 private static final Animation pushingDownAnim = Config.pusherPushingDownAnim;
	 private static final Animation pushingLeftAnim = Config.pusherPushingLeftAnim;
	 private static final Animation pushingRightAnim = Config.pusherPushingRightAnim;

	 private int moves;
	 private int pushes;
	 private Level level;
	 private Animation anim;

	 public Pusher(Level l, int gameX, int gameY) {
		  super(gameX, gameY);
		  level = l;
		  moves = 0;
		  pushes = 0;
		  anim = standingDownAnim;
	 }

	 public int getMovesCount() { return moves; }
	 public int getPushesCount() { return pushes; }

	 public void update() {
		  switch(getState()) {
		  case STANDING:
				break;
		  case MOVING:
		  case PUSHING:
				if (getScreenX() < getNScreenX()) setScreenX(getScreenX() + getXstep());
				if (getScreenX() > getNScreenX()) setScreenX(getScreenX() - getXstep());
				if (getScreenY() < getNScreenY()) setScreenY(getScreenY() + getYstep());
				if (getScreenY() > getNScreenY()) setScreenY(getScreenY() - getYstep());
				if (getScreenX() == getNScreenX() && getScreenY() == getNScreenY()) {
					 setState(State.STANDING);
					 switch(getDirection()) {
					 case UP: anim = standingUpAnim; break;
					 case DOWN: anim = standingDownAnim; break;
					 case LEFT: anim = standingLeftAnim; break;
					 case RIGHT: anim = standingRightAnim; break;
					 }					 
					 anim.reset();
				}
				break;
		  }

		  anim.update();
	 }

	 public void move(int dGameX, int dGameY) {
		  if (getState() == State.MOVING || getState() == State.PUSHING)
				return;
		  if (dGameX == 0 && dGameY == 0)
				return;
		  if (!canMove(dGameX, dGameY)) {
				if (dGameX > 0) {
					 setDirection(Direction.RIGHT);
					 anim = standingRightAnim;
				}
				if (dGameX < 0) {
					 setDirection(Direction.LEFT);
					 anim = standingLeftAnim;
				}
				if (dGameY > 0) {
					 setDirection(Direction.DOWN);
					 anim = standingDownAnim;
				}
				if (dGameY < 0) {
					 setDirection(Direction.UP);
					 anim = standingUpAnim;
				}
				anim.reset();
				return;
		  }


		  setNScreenX(getScreenX());
		  setNScreenY(getScreenY());

		  if (dGameX != 0) {
				setNScreenX(getScreenX() +
								dGameX * level.getFloorTile(getGameX(), getGameY()).getImageWidth());
				if (dGameX > 0) {
					 setDirection(Direction.RIGHT);
					 anim = movingRightAnim;
				} else {
					 setDirection(Direction.LEFT);
					 anim = movingLeftAnim;
				}
				setState(State.MOVING);
				anim.reset();
				setGameX(getGameX() + dGameX);
		  }
		  if (dGameY != 0) {
				setNScreenY(getScreenY() +
								dGameY * level.getFloorTile(getGameX(), getGameY()).getImageHeight());
				if (dGameY > 0) {
					 setDirection(Direction.DOWN);
					 anim = movingDownAnim;
				} else {
					 setDirection(Direction.UP);
					 anim = movingUpAnim;
				}
				setState(State.MOVING);
				anim.reset();
				setGameY(getGameY() + dGameY);
		  }

		  if (level.isBox(getGameX(), getGameY())) {
				Box b = level.getBox(getGameX(), getGameY());
				b.move(dGameX, dGameY);
				++pushes;
				switch (getDirection()) {
				case UP: anim = pushingUpAnim; break;
				case DOWN: anim = pushingDownAnim; break;
				case LEFT: anim = pushingLeftAnim; break; 
				case RIGHT: anim = pushingRightAnim; break;
				}
				setState(State.PUSHING);
				anim.reset();
		  } else {
				++moves;
		  }
	 }

	 public Image getImage() {
		  if (anim == null)
				return standingDownAnim.getSprite();
		  return anim.getSprite();
	 }

	 public boolean canMove(int dGameX, int dGameY) {
		  int nGameX = getGameX() + dGameX;
		  int nGameY = getGameY() + dGameY;

		  if (level.getMap().isWall(nGameX, nGameY))
				return false;
		  if (level.isBox(nGameX, nGameY))
			   return level.getBox(nGameX, nGameY).canMove(dGameX, dGameY);

		  return true;
	 }
}