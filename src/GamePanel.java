import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

/** A game panel to take care of rendering with double buffering. */
public abstract class GamePanel extends JPanel {
	 private static final Dimension size = Config.gamePanelSize;
	 private static final int frameDelay = Config.frameDelay;
	 private static final Color backgroundColor = Config.backgroundColor;
	 private static final int defaultScale = 2;

	 private Timer animator;
	 private Image bufferImg;
	 private Graphics buffer;
	 private int scale = defaultScale;

	 public int getScale() { return scale; }
	 public void setScale(int scale) { this.scale = scale; }

	 // called when added to container
	 public void addNotify() {
		  super.addNotify();
		  // to be safe... we can't create an image to draw on
		  // if the drawing area is not visible
		  setMinimumSize(size);
		  setPreferredSize(size);
		  setSize(size);
		  start();
	 }

	 private void start() {
		  if (animator == null)
				animator = new Timer();
		  animator.scheduleAtFixedRate(new TimerTask() {
					 public void run() {
						  mainLoop();
					 }
				}, 0, frameDelay);
	 }
	 
	 /** Override to draw on the panel */
	 protected abstract void draw(Graphics g);
	 /** Override to update game logic before drawing */
	 protected abstract void update();

	 private void mainLoop() {
		  update();
		  render();
		  blit();
	 }

	 /** Render to back buffer */
	 private void render() {
		  if (bufferImg == null) {
				bufferImg = createImage(getWidth(), getHeight());
				if (bufferImg == null) {
					 System.err.println("Couldn't create buffer image.\n");
					 return;
				}
		  }
		  
		  if (buffer == null) {
				buffer = bufferImg.getGraphics();
				buffer.setFont(Config.font);
		  }

		  // clear background
		  buffer.setColor(backgroundColor);
		  buffer.fillRect(0,0, getWidth(), getHeight());
		  
		  // render game elements
		  draw(buffer);
	 }

	 /** Draw back buffer to screen */
	 private void blit() {
		  Graphics g = getGraphics();
		  if (g != null && buffer != null)
				g.drawImage(bufferImg, 0, 0,
								bufferImg.getWidth(null) * scale,
								bufferImg.getHeight(null) * scale,
								null);
		  g.dispose();
	 }
}