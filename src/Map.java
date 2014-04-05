import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.util.Vector;

/**
 * Map of Sokoban, hosts static tiles.
 * @author Florent Marchand de Kerchove
 */
public class Map {
	 private Vector<Wall> walls;
	 private Vector<Goal> goals;
	 private Vector<Floor> floors;
	 private Vector<Sprite> objects;
	 
	 public Map() {
		  walls = new Vector<Wall>();
		  goals = new Vector<Goal>();
		  floors = new Vector<Floor>();
		  objects = new Vector<Sprite>();
	 }

	 public void update() {
		  for (Sprite s : objects)
				s.update();
	 }

	 public void draw(Graphics g) {
		  for (Floor f : floors)
				f.draw(g);
		  for (Wall w : walls)
				w.draw(g);
		  for (Sprite s : objects)
				s.draw(g);
		  for (Goal go : goals)
				go.draw(g);
	 }

	 public void add(Wall w) {
		  if (!walls.contains(w))
				walls.add(w);
	 }

	 public void add(Goal g) {
		  if (!goals.contains(g))
				goals.add(g);
	 }

	 public void add(Floor f) {
		  if (!floors.contains(f))
				floors.add(f);
	 }

	 public void add(Torch t) {
		  if (!objects.contains(t))
				objects.add(t);
	 }

	 public Floor getFloorTile(int gameX, int gameY) {
		  for (Floor f : floors) {
				if (gameX == f.getGameX() && gameY == f.getGameY())
					 return f;
		  }
		  return null;
	 }

	 public boolean isWall(int gameX, int gameY) {
		  for (Wall w : walls) {
				if (gameX == w.getGameX() && gameY == w.getGameY())
					 return true;
		  }
		  return false;
	 }

	 public boolean isGoal(int gameX, int gameY) {
		  for (Goal g : goals) {
				if (gameX == g.getGameX() && gameY == g.getGameY())
					 return true;
		  }
		  return false;
	 }
}