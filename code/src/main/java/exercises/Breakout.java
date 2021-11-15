package exercises;

import ipp.Location;
import ipp.SnapApp;
import ipp.Sprite;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyCode;

public class Breakout extends SnapApp {

  @Override
  public void start() {
    Sprite player =
        addSprite("player1", "images/Breakout_Bar.png", 10.0, 40.0);
    Sprite ball = addSprite("ball", "images/Ball.png", 10.0, 10.0);
    // int p1score = 0;
    // int p2score = 0;
    Sprite[] paddles = {player};
    player.moveTo(0.0, -215.0);
    ball.moveTo(0.0, -200.0);
    ball.setDirection(-60);
    addLoopThread(0.02, () -> paddleControl(player, 5.0, KeyCode.LEFT, KeyCode.RIGHT), "player");
    addThread(() -> ball.wait(1.0));
    addLoopThread(0.02, () -> ballControl(ball, 6.0, paddles), "ball");
    addThread(() -> targetControl(ball, 6.0));
  }

  public void paddleControl(Sprite sprite, double vel, KeyCode leftkey, KeyCode rightkey) {
    double dx = 0.0;
    if (getInputs().isKeyPressed(rightkey)) {
      dx += vel;
    }
    if (getInputs().isKeyPressed(leftkey)) {
      dx -= vel;
    }
    double targetX = sprite.getLocation().x() + dx;
    if (targetX > 275.0) {
      targetX = 275.0;
    } else if (targetX < -275.0) {
      targetX = -275.0;
    }
    Location targetPos = Location.create(targetX, sprite.getLocation().y());
    sprite.moveTo(targetPos);
  }

  public void ballControl(Sprite ball, double vel, Sprite[] paddles) {
    ball.moveForward(vel);
    // if (Math.abs(ball.getLocation().x()) > 295) {
    //   if(ball.getLocation().x() < 0) {
    //     p2score++;
    //   } else {
    //     p1score++;
    //   }
    //   ball.setLocation(Location.create(0.0, 0.0));
    //   ball.setDirection(-60);
    //   ball.wait(1.0);
    // }
    if (Math.abs(ball.getLocation().x()) > 295) {
      ball.moveForward(-vel);
      ball.setDirection(ball.getDirection() * -1);
    }
    if (Math.abs(ball.getLocation().y()) > 220) {
      ball.moveForward(-vel);
      ball.setDirection(ball.getDirection() * -1 + 180);
    }
    for (Sprite paddle : paddles) {
      if (ball.isTouching(paddle)) {
        ball.moveForward(-vel);
        ball.setDirection(ball.getDirection() * -1 + 180);
      }
    }
  }

  public void targetControl(Sprite ball, double vel) {
    List<Sprite> targets = new ArrayList<>();
    for (int row = 0; row < 6; row++) {
      for (int col = 0; col < 10; col++) {
        Sprite target;
        if (row == 0) {
          target =
              addSprite(
                  ("target" + row * 10 + col),
                  "images/Breakout_Target_Blue.png",
                  20.0,
                  54.5);
        } else if (row == 1) {
          target =
              addSprite(
                  ("target" + row * 10 + col),
                  "images/Breakout_Target_LightBlue.png",
                  20.0,
                  54.5);
        } else if (row == 2) {
          target =
              addSprite(
                  ("target" + row * 10 + col),
                  "images/Breakout_Target_Green.png",
                  20.0,
                  54.5);
        } else if (row == 3) {
          target =
              addSprite(
                  ("target" + row * 10 + col),
                  "images/Breakout_Target_Yellow.png",
                  20.0,
                  54.5);
        } else if (row == 4) {
          target =
              addSprite(
                  ("target" + row * 10 + col),
                  "images/Breakout_Target_Orange.png",
                  20.0,
                  54.5);
        } else {
          target =
              addSprite(
                  ("target" + row * 10 + col),
                  "images/Breakout_Target_Red.png",
                  20.0,
                  54.5);
        }
        double targetX = 59.5 * col - 267.75;
        double targetY = (-25 * row) + 150;
        target.moveTo(targetX, targetY);
        targets.add(target);
      }
    }
    while (true) {
      if (targets.size() == 0) {
        break;
      }
      for (int i = 0; i < targets.size(); i++) {
        Sprite target = targets.get(i);
        if (ball.isTouching(target)) {
          targets.remove(target);
          removeSprite(target);
          ball.moveForward(-vel);
          if (Math.abs(ball.getLocation().x() - target.getLocation().x()) >= 30.25) {
            ball.setDirection(ball.getDirection() * -1);
          } else {
            ball.setDirection(ball.getDirection() * -1 + 180);
          }
          continue;
        }
      }
    }
  }
}
