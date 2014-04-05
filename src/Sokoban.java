import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

/**
 * Main class of Shinkoban.
 * @author Florent Marchand de Kerchove
 */
public class Sokoban extends GamePanel implements KeyListener, ItemListener {
	 private static final String frameTitle = Config.frameTitle;
	 private static final int transitionDelay = Config.transitionDelay;
	 private static final int messageDuration = Config.messageDuration;
	 private static final int messageAlphaStep = Config.messageAlphaStep;
	 private static final int fadeStep = Config.fadeStep;
	 private static final int fontX = Config.fontX;
	 private static final int fontY = Config.fontY;
	 private static final File levelDir = Config.levelDir;
	 private static final String levelFileSuffix = Config.levelFileSuffix;
	 private static final Color backgroundColor = Config.backgroundColor;

	 // Game elements
	 private Level level;
	 private Highscores scores;
	 
	 // UI elements
	 private Frame gameWindow;
	 private Panel gamePanel;
	 private Panel statusPanel;
	 private Label movesLabel;
	 private Label pushesLabel;
	 private Choice levelsChoice;

	 // transition vars
	 private int delay = transitionDelay;
	 private int alpha = 0;
	 private int msgAlpha = 0;
	 private int msgAlphaStep = messageAlphaStep;
	 private int msgElapsed = messageDuration;
	 private boolean transition = false;
	 private boolean fadeOut = true;
	 private boolean message = false;

	 public Sokoban() {
		  super();
		  // container for status display
		  statusPanel = new Panel(new GridLayout(0, 3));

		  // score labels
		  movesLabel = new Label("Moves: 0");
		  pushesLabel = new Label("Pushes: 0");
		  statusPanel.add(movesLabel);
		  statusPanel.add(pushesLabel);

		  // choice list of levels
		  levelsChoice = new Choice();
		  String defaultLevel = buildLevelChoice(levelsChoice);
		  levelsChoice.addItemListener(this);
		  statusPanel.add(levelsChoice);

		  level = new Level();
		  level.load(defaultLevel);

		  setBackground(backgroundColor);		  
		  addKeyListener(this);
		  setFocusable(true);
		  requestFocus();

		  // container for the gamePanel and status bar
		  gamePanel = new Panel(new BorderLayout());
		  gamePanel.add(this, BorderLayout.CENTER);
		  gamePanel.add(statusPanel, BorderLayout.PAGE_END);
		  
		  gameWindow = new Frame(frameTitle);
		  gameWindow.add(gamePanel);

		  scores = Highscores.loadScores();
	 }

	 public Frame getSurface() { return gameWindow; }

	 protected void draw(Graphics g) {
		  level.draw(g);
		  updateStatus();

		  if (transition) {
		  		if (delay-- > 0) return;
				else doTransition(g);
		  }
	 }

	 protected void update() {
		  level.update();
	 }

	 // crossfade level transition
	 private void doTransition(Graphics g) {
		  g.setColor(new Color(0,0,0,alpha));
		  g.fillRect(0, 0, getWidth(), getHeight());
		  if (fadeOut) {
				alpha += fadeStep;
				if (alpha >= 255) {
					 alpha = 255;
					 fadeOut = false;
					 message = true;
					 loadNextLevel();
				}
		  } else {
				if (message) {
					 msgAlpha += msgAlphaStep;
					 if (msgAlpha > 255) {
						  msgAlpha = 255;
						  --msgElapsed;
					 }
					 else if (msgAlpha < 0) {
						  msgAlpha = 0;
						  msgElapsed = messageDuration;
						  msgAlphaStep = messageAlphaStep;
						  message = false;
					 }
					 g.setColor(new Color(255,255,255,msgAlpha));
					 g.drawString(levelsChoice.getSelectedItem(), fontX, fontY);
					 if (msgElapsed < 0) {
						  msgAlphaStep *= -1;
						  msgElapsed = 0;
					 }
				} else {
					 alpha -= fadeStep;
					 if (alpha < 0) {
						  alpha = 0;
						  delay = transitionDelay;
						  fadeOut = true;
						  transition = false;
					 }
				}
		  }
	 }
	 
	 /**
	  * Builds a level list from the files in the levels
	  * directory.
	  * @param levelsChoice The Choice widget to represent the list.
	  * @return The first (lexicographical order) level in the list.
	  */
	 private String buildLevelChoice(Choice levelsChoice) {
		  String[] levels = null;

		  try {
				levels = levelDir.list(new FilenameFilter() {
						  public boolean accept(File dir, String name) {
								return name.endsWith(levelFileSuffix);
						  }
					 });
		  } catch (SecurityException e) {
				System.err.println("Couldn't read files in '"
										 + levelDir
										 + "' directory.");
				System.exit(1);
		  }

		  if (levels == null) {
				System.err.println("No level files found in '"
										 + levelDir
										 + "' directory.");
				System.exit(1);
		  }
		  
		  Arrays.sort(levels);
		  for (String name : levels) {
				// drop the extension from filename and add that to level list
				levelsChoice.add(name.substring(0,
				   name.length()-levelFileSuffix.length()));
		  }

		  // return path to first level
		  return levelDir.getPath() + File.separator + levels[0];
	 }
	 
	 public void updateStatus() {
		  String moves = "Moves: " + level.getPusher().getMovesCount();
		  String pushes = "Pushes: " + level.getPusher().getPushesCount();
		  Score score = scores.getScore(level.getName());
		  if (score != null) {
				moves += " (" + score.moves() + ")";
				pushes += " (" + score.pushes() + ")";
		  }
		  movesLabel.setText(moves);
		  pushesLabel.setText(pushes);
	 }

	 private void recordHighscore() {
		  Score pre = scores.getScore(level.getName());
		  Score cur = new Score(level.getPusher().getMovesCount(),
										level.getPusher().getPushesCount());
		  // check if we beat the highscore for this level
		  if (pre == null || cur.betterThan(pre)) {
				scores.addScore(level.getName(), cur);
				Highscores.saveScores(scores);
		  }
	 }

	 // cycle between level in levels directory
	 private void loadNextLevel() {
		  int next = levelsChoice.getSelectedIndex()+1;
		  if (next >= levelsChoice.getItemCount())
				next = 0;
		  levelsChoice.select(next);
		  level.load(Config.levelDir.getPath()
						 + File.separator
						 + levelsChoice.getSelectedItem()
						 + Config.levelFileSuffix);
	 }

	 // level list listener
	 public void itemStateChanged(ItemEvent e) {
		  if (e.getSource() == levelsChoice) {
				level.load(Config.levelDir.getPath() + File.separator
							  + e.getItem() + Config.levelFileSuffix);
				requestFocus();
		  }
	 }

	 public void keyPressed(KeyEvent e) {}
	 public void keyTyped(KeyEvent e) {}
	 public void keyReleased(KeyEvent e) {
		  // movement is disallowed when the level is over
		  if (transition)
				return;

		  if (!level.isSolved()) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_DOWN:
					 level.getPusher().move(0,1);
					 break;
				case KeyEvent.VK_UP:
					 level.getPusher().move(0,-1);
					 break;
				case KeyEvent.VK_LEFT:
					 level.getPusher().move(-1,0);
					 break;
				case KeyEvent.VK_RIGHT:
					 level.getPusher().move(1,0);
					 break;
				}
				
				// if this was a winning move
				if (level.isSolved()) {
					 recordHighscore();
					 transition = true;
				}
		  }
		  switch (e.getKeyCode()) {
		  case KeyEvent.VK_ENTER:
				level.reset();
				break;
		  case KeyEvent.VK_SPACE:
				transition = true;
				break;
		  }
		  requestFocus();
	 }

	 public static void main(String args[]) {
		  Sokoban soko = new Sokoban();
		  Frame frame = soko.getSurface();

		  frame.addWindowListener(new WindowAdapter() {
					 public void windowClosing(WindowEvent e) {
						  System.exit(0);
					 }
				});

		  frame.pack();
		  frame.setVisible(true);
	 }
}