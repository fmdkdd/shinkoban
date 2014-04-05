import java.io.Serializable;

/** 
 * Stores scoring information for Sokoban levels.
 * @author Florent Marchand de Kerchove
 */
public class Score implements Serializable {
	 private static final long serialVersionUID = 43L;
	 private int moves;
	 private int pushes;
	 
	 public Score(int moves, int pushes) {
		  this.moves = moves;
		  this.pushes = pushes;
	 }
	 
	 public int moves() { return moves; }
	 public int pushes() { return pushes; }
	 public boolean betterThan(Score s) {
		  return (s.moves()+s.pushes() > moves+pushes);
	 }
}