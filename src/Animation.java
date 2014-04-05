import java.awt.Image;
import java.util.Vector;

/** Handles multi-frame animation. */
public class Animation {
	 /** An animation frame */
	 public static class Frame {
		  private Image sprite;
		  private int delay;

		  public Frame(Image sprite, int delay) {
				this.sprite = sprite;
				this.delay = delay;
		  }

		  public Image getSprite() { return sprite; }
		  /** Return the number of times this frame must be repeated
			* in the animation. */
		  public int getDelay() { return delay; }
	 }

	 private Vector<Frame> frames;
	 private int activeFrame;
	 private int updatesSinceDraw;
	 // should the animation loop from the end (ping pong) ?
	 private boolean reverse;
	 // are we reading the frames backward (only used if reverse is true)
	 private boolean backwards;

	 public Animation() {
		  this(false);
	 }

	 public Animation(boolean reverse) {
		  frames = new Vector<Frame>();
		  this.reverse = reverse;
		  reset();
	 }

	 /** Return the total number of frames of this animation.
	  * This is different from the number of images it contains. */
	 public int size() {
		  int size = 0;
		  for (Frame f : frames)
				size += f.getDelay();
		  return size;
	 }

	 /** Reset the animation. Called when changing states. */
	 public void reset() {
		  activeFrame = 0;
		  updatesSinceDraw = 0;
		  backwards = false;
	 }

	 public void add(Image i) {
		  frames.add(new Frame(i, 1));
	 }

	 public void add(Frame f) {
		  frames.add(f);
	 }

	 /** Return the current sprite to draw */
	 public Image getSprite() {
		  return frames.get(activeFrame).getSprite();
	 }

	 public int getSpriteWidth() {
		  return frames.get(activeFrame).getSprite().getWidth(null);
	 }

	 public int getSpriteHeight() {
		  return frames.get(activeFrame).getSprite().getHeight(null);
	 }

	 /** Advance the animation's current frame. Must be called
	  *  by the client. */
	 public void update() {
		  ++updatesSinceDraw;
		  if (updatesSinceDraw >= frames.get(activeFrame).getDelay()) {
				updatesSinceDraw = 0;
				if (!reverse) {
					 ++activeFrame;
					 if (activeFrame >= frames.size())
						  activeFrame = 0;
				}
				else {
					 if (!backwards) {
						  ++activeFrame;
						  if (activeFrame >= frames.size()) {
								activeFrame -= 2;
								backwards = true;
						  }
					 }
					 else {
						  --activeFrame;
						  if (activeFrame < 0) {
								activeFrame += 2;
								backwards = false;
						  }
					 }
				}
		  }
	 }
}