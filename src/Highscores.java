import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * Manage a highscores file.
 * @author Florent Marchand de Kerchove
 */
public class Highscores implements Serializable {

	 private static final long serialVersionUID = 42L;
	 private Hashtable<String, Score> scores;

	 public Highscores() {
		  scores = new Hashtable<String, Score>();
	 }

	 public void addScore(String level, Score s) {
		  scores.put(level, s);
	 }

	 public Score getScore(String level) {
		  return scores.get(level);
	 }

	 public static void saveScores(Highscores scores) {
		  try {
				ObjectOutputStream objStream = new ObjectOutputStream(
															  new FileOutputStream(Config.scoreFile));
				objStream.writeObject(scores);
				objStream.close();
		  } catch (Exception e) {
				System.err.println("Error writing score file");
		  }
	 }

	 public static Highscores loadScores() {
		  try {
				ObjectInputStream objStream = new ObjectInputStream(
															 new FileInputStream(Config.scoreFile));
				Highscores scores = (Highscores) objStream.readObject();
				objStream.close();
				return scores;
		  } catch (Exception e) {
				System.err.println("Error reading score file");
		  }
		  return new Highscores();
	 }
}