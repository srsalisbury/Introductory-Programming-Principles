package exercises;

import ipp.SnapApp;
import ipp.Sprite;
import java.util.Random;
import javafx.scene.input.KeyCode;

public class JetpackRun extends SnapApp {

  private double v = 0.0;

  @Override
  public void start() {
    Sprite player =
        addSprite("player", "C:\\Users\\steve\\Desktop\\Desktop\\JetpackPlayerOff.png", 60.0, 30.0);
    player.moveTo(-260, 0.0);
    addLoopThread(0.02, () -> playerControl(player, KeyCode.SPACE), "player");
    addThread(() -> wallControl(player));
  }

  public void playerControl(Sprite player, KeyCode upkey) {
    if (getInputs().isKeyPressed(upkey)) {
      v += 0.6;
      player.setImage("C:\\Users\\steve\\Desktop\\Desktop\\JetpackPlayerOn.png", 60.0, 30.0);
    } else {
      v -= 0.6;
      player.setImage("C:\\Users\\steve\\Desktop\\Desktop\\JetpackPlayerOff.png", 60.0, 30.0);
    }
    double targetY = player.getLocation().y() + v;
    if (targetY > 190.0) {
      targetY = 190.0;
      v = 0.0;
    } else if (targetY < -190.0) {
      targetY = -190.0;
      v = 0.0;
    }
    player.moveTo(player.getLocation().x(), targetY);
  }

  public boolean runWall(double gap, double height, double speed, Sprite jetpack) {
    Sprite topWall =
        addSprite("topWall", "C:\\Users\\steve\\Desktop\\Desktop\\JetpackWall.png", 600.0, 60.0);
    Sprite bottomWall =
        addSprite("bottomWall", "C:\\Users\\steve\\Desktop\\Desktop\\JetpackWall.png", 600.0, 60.0);
    topWall.moveTo(330.0, (height + ((gap + topWall.getHitBoxHeight()) / 2)));
    bottomWall.moveTo(330.0, (height - ((gap + bottomWall.getHitBoxHeight()) / 2)));
    while (true) {
      if (topWall.isTouching(jetpack) || bottomWall.isTouching(jetpack)) {
        return true;
      }
      if (topWall.getLocation().x() <= -330.0 && bottomWall.getLocation().x() <= -330.0) {
        removeSprite(topWall);
        removeSprite(bottomWall);
        return false;
      }
      topWall.moveTo(topWall.getLocation().x() - speed, topWall.getLocation().y());
      bottomWall.moveTo(bottomWall.getLocation().x() - speed, bottomWall.getLocation().y());
      try {
        Thread.sleep(10l);
      } catch (InterruptedException e) {
        // ignoring
      }
    }
  }

  public void wallControl(Sprite jetpack) {
    Random rand = new Random();
    double gap = 180.0;
    double height = 0.0;
    double speed = 2.0;
    while (true) {
      if (runWall(gap, height, speed, jetpack)) {
        return;
      }
      int upperBound = (int) ((450 - gap) / 10);
      height = ((double) rand.nextInt(upperBound) - (upperBound / 2)) * 10.0;
    }
  }
}
