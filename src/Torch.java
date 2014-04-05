import java.awt.Image;
import java.util.Random;

/** Animated sprite depicting a torch.
 *  Used for dramatic effect. */
public class Torch extends Sprite {
	 private final Animation torchAnim;

	 public Torch(int gameX, int gameY) {
		  torchAnim = Config.createTorchAnim();

		  setGameX(gameX);
		  setGameY(gameY);
		  setScreenX(gameX * getImageWidth());
		  setScreenY(gameY * getImageHeight());

		  // choose a random frame to start the animation
		  // this way multiple torches don't flicker at the
		  // exact same time
		  Random r = new Random();
		  int n = r.nextInt(torchAnim.size());
		  for (; n > 0; --n)
				torchAnim.update();
	 }
	 
	 public Image getImage() {
		  return torchAnim.getSprite();
	 }

	 public void update() {
		  torchAnim.update();
	 }

	 // torches don't move
	 public boolean canMove(int dGameX, int dGameY) { return false; }
	 public void move(int dGameX, int dGameY) {}
}