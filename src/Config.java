import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.Vector;
import javax.swing.ImageIcon;

/**
 * Hard coded config file for parameters.
 * @author Florent Marchand de Kerchove 
 */
abstract class Config {
	 static final String frameTitle = "Of boxes and man";
	 static final Dimension gamePanelSize = new Dimension(410,410);
	 // scaling of the drawing buffer
	 static final int defaultScale = 2;
	 static final Color backgroundColor = new Color(20,25,20);
	 static final Color transitionColor = backgroundColor;
	 // font used for level transition message
	 static final Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	 static final int fontX = 60;
	 static final int fontY = 100;
	 static final int desiredFPS = 50;
	 static final int frameDelay = 1000/desiredFPS;

	 // I should have an artDir to be consistent ...
	 static final File levelDir = new File("levels");
	 static final String levelFileSuffix = ".map";
	 static final String scoreFile = "levels/highscores";

	 static final int transitionDelay = 30;
	 static final int fadeStep = 3;
	 static final int messageAlphaStep = 3;
	 static final int messageDuration = 50;
	 // careful : must be divisors of the floors tiles dimensions
	 // otherwise the pusher get stuck
	 // if you want ultra fast mode (without animations, try x=20 and y=24)
	 static final int spriteXStep = 2;
	 static final int spriteYStep = 2;

	 static final Image defaultImage = createImage("art/standing_down.png");
	 static final Animation pusherStandingUpAnim = createPusherStandingUpAnim();
	 static final Animation pusherStandingDownAnim = createPusherStandingDownAnim();
	 static final Animation pusherStandingLeftAnim = createPusherStandingLeftAnim();
	 static final Animation pusherStandingRightAnim = createPusherStandingRightAnim();
	 static final Animation pusherMovingUpAnim = createPusherMovingUpAnim();
	 static final Animation pusherMovingDownAnim = createPusherMovingDownAnim();
	 static final Animation pusherMovingLeftAnim = createPusherMovingLeftAnim();
	 static final Animation pusherMovingRightAnim = createPusherMovingRightAnim();
	 static final Animation pusherPushingUpAnim = createPusherPushingUpAnim();
	 static final Animation pusherPushingDownAnim = createPusherPushingDownAnim();
	 static final Animation pusherPushingLeftAnim = createPusherPushingLeftAnim();
	 static final Animation pusherPushingRightAnim = createPusherPushingRightAnim();
	 static final Image floorImg = createImage("art/floor.png");
	 static final Image wallImg = createImage("art/wall.png");
	 static final Image goalImg = createImage("art/goal.png");
	 static final Image boxImg = createImage("art/box.png");
	 static final Image boxOnGoalImg = createImage("art/box_on_goal.png");
	 static final Animation.Frame torch1 = new Animation.Frame(createImage("art/torch2.png"),10);
	 static final Animation.Frame torch2 = new Animation.Frame(createImage("art/torch3.png"),15);
	 static final Animation.Frame torch3 = new Animation.Frame(createImage("art/torch4.png"),25);

	 static Image createImage(String path) {
		  File img = new File(path);

		  if (img.exists())
				return new ImageIcon(path).getImage();
		  else {
				System.err.println("Couldn't find image : " + path);
				return defaultImage;
		  }
	 }

	 static Animation createTorchAnim() {
		  Animation anim = new Animation(true);
		  anim.add(torch1);
		  anim.add(torch2);
		  anim.add(torch3);
		  return anim;
	 }

	 static Animation createPusherStandingUpAnim() {
		  Animation anim = new Animation();
		  anim.add(createImage("art/standing_up.png"));
		  return anim;
	 }

	 static Animation createPusherStandingDownAnim() {
		  Animation anim = new Animation();
		  anim.add(createImage("art/standing_down.png"));
		  return anim;
	 }

	 static Animation createPusherStandingLeftAnim() {
		  Animation anim = new Animation();
		  anim.add(createImage("art/standing_left.png"));
		  return anim;
	 }

	 static Animation createPusherStandingRightAnim() {
		  Animation anim = new Animation();
		  anim.add(createImage("art/standing_right.png"));
		  return anim;
	 }
	 
	 static Animation createPusherMovingUpAnim() {
		  Animation anim = new Animation();
		  anim.add(createImage("art/standing_up.png"));
		  anim.add(createImage("art/moving_up1.png"));
		  anim.add(createImage("art/moving_up2.png"));
		  anim.add(createImage("art/moving_up3.png"));
		  anim.add(createImage("art/moving_up4.png"));
		  anim.add(createImage("art/moving_up5.png"));
		  anim.add(createImage("art/moving_up6.png"));
		  return anim;
	 }

	 static Animation createPusherMovingDownAnim() {
		  Animation anim = new Animation();
		  anim.add(createImage("art/standing_down.png"));
		  anim.add(createImage("art/moving_down1.png"));
		  anim.add(createImage("art/moving_down2.png"));
		  anim.add(createImage("art/moving_down3.png"));
		  anim.add(createImage("art/moving_down4.png"));
		  anim.add(createImage("art/moving_down5.png"));
		  anim.add(createImage("art/moving_down6.png"));
		  return anim;
	 }

	 static Animation createPusherMovingLeftAnim() {
		  Animation anim = new Animation();
		  anim.add(createImage("art/standing_left.png"));
		  anim.add(createImage("art/moving_left1.png"));
		  anim.add(createImage("art/moving_left2.png"));
		  anim.add(createImage("art/moving_left3.png"));
		  anim.add(createImage("art/moving_left4.png"));
		  anim.add(createImage("art/moving_left5.png"));
		  anim.add(createImage("art/moving_left6.png"));
		  return anim;
	 }

	 static Animation createPusherMovingRightAnim() {
		  Animation anim = new Animation();
		  anim.add(createImage("art/standing_right.png"));
		  anim.add(createImage("art/moving_right1.png"));
		  anim.add(createImage("art/moving_right2.png"));
		  anim.add(createImage("art/moving_right3.png"));
		  anim.add(createImage("art/moving_right4.png"));
		  anim.add(createImage("art/moving_right5.png"));
		  anim.add(createImage("art/moving_right6.png"));
		  return anim;
	 }

	 static Animation createPusherPushingUpAnim() {
		  Animation anim = new Animation();
		  anim.add(new Animation.Frame(createImage("art/push_up1.png"),2));
		  anim.add(new Animation.Frame(createImage("art/push_up2.png"),2));
		  anim.add(new Animation.Frame(createImage("art/push_up3.png"),2));
		  return anim;
	 }

	 static Animation createPusherPushingDownAnim() {
		  Animation anim = new Animation();
		  anim.add(new Animation.Frame(createImage("art/push_down1.png"),2));
		  anim.add(new Animation.Frame(createImage("art/push_down2.png"),2));
		  anim.add(new Animation.Frame(createImage("art/push_down3.png"),2));
		  return anim;
	 }

	 static Animation createPusherPushingLeftAnim() {
		  Animation anim = new Animation();
		  anim.add(new Animation.Frame(createImage("art/push_left1.png"),2));
		  anim.add(new Animation.Frame(createImage("art/push_left2.png"),2));
		  anim.add(new Animation.Frame(createImage("art/push_left3.png"),2));
		  return anim;
	 }

	 static Animation createPusherPushingRightAnim() {
		  Animation anim = new Animation();
		  anim.add(new Animation.Frame(createImage("art/push_right1.png"),2));
		  anim.add(new Animation.Frame(createImage("art/push_right2.png"),2));
		  anim.add(new Animation.Frame(createImage("art/push_right3.png"),2));
		  return anim;
	 }
}