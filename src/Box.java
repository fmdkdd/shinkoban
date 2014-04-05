import java.awt.Image;

/**
 * Box sprite in Sokoban. Has a tendency to get stuck.
 * @author Florent Marchand de Kerchove 
 */
public class Box extends Sprite {
	 private static final Image onFloor = Config.boxImg;
	 private static final Image onGoal = Config.boxOnGoalImg;

	 private Level level;
	 private Image image;

	 public Box(Level l, int gameX, int gameY) {
		  super(gameX, gameY);
		  level = l;
		  image = onFloor;
	 }

	 public Image getImage() {
		  if (image == null)
				return onFloor;
		  return image;
	 }

	 public void update() {
		  switch(getState()) {
		  case STANDING: break;
		  case MOVING:
				if (getScreenX() < getNScreenX()) setScreenX(getScreenX() + getXstep());
				if (getScreenX() > getNScreenX()) setScreenX(getScreenX() - getXstep());
				if (getScreenY() < getNScreenY()) setScreenY(getScreenY() + getYstep());
				if (getScreenY() > getNScreenY()) setScreenY(getScreenY() - getYstep());
				if (getScreenX() == getNScreenX() && getScreenY() == getNScreenY())
					 setState(State.STANDING);
				break;
		  case PUSHING: break;
		  }

		  if (level.getMap().isGoal(getGameX(),getGameY()))
				image = onGoal;
		  else
				image = onFloor;
	 }

	 public void move(int dGameX, int dGameY) {
		  if (getState() == State.MOVING)
				return;
		  if (!canMove(dGameX, dGameY))
				return;
		  if (dGameX == 0 && dGameY == 0)
				return;

		  setNScreenX(getScreenX());
		  setNScreenY(getScreenY());

		  if (dGameX != 0) {
				setNScreenX(getScreenX() +
								dGameX * level.getFloorTile(getGameX(), getGameY()).getImageWidth());
				setDirection(dGameX > 0 ? Direction.RIGHT : Direction.LEFT);
				setState(State.MOVING);
				setGameX(getGameX() + dGameX);
		  }
		  if (dGameY != 0) {
				setNScreenY(getScreenY() +
								dGameY * level.getFloorTile(getGameX(), getGameY()).getImageHeight());
				setDirection(dGameY > 0 ? Direction.UP : Direction.DOWN);
				setState(State.MOVING);
				setGameY(getGameY() + dGameY);
		  }
	 }

	 public boolean canMove(int dGameX, int dGameY) {
		  int nGameX = getGameX() + dGameX;
		  int nGameY = getGameY() + dGameY;

		  if (level.getMap().isWall(nGameX, nGameY) || level.isBox(nGameX, nGameY))
				return false;
		  return true;
	 }
}