package exercises;

import ipp.Location;
import ipp.SnapApp;
import ipp.Sprite;
import javafx.scene.input.KeyCode;

public class Pong extends SnapApp {

  @Override
  public void start() {
    Sprite player1 =
        addSprite("player1", "C:\\Users\\steve\\Desktop\\Desktop\\Pong_Bar.png", 40.0, 10.0);
    Sprite player2 =
        addSprite("player2", "C:\\Users\\steve\\Desktop\\Desktop\\Pong_Bar.png", 40.0, 10.0);
    Sprite ball = addSprite("ball", "C:\\Users\\steve\\Desktop\\Desktop\\Ball.png", 10.0, 10.0);
    // int p1score = 0;
    // int p2score = 0;
    Sprite[] paddles = {player1, player2};
    player1.moveTo(-290.0, 0.0);
    player2.moveTo(290.0, 0.0);
    ball.moveTo(0.0, 0.0);
    ball.setDirection(-60);
    addLoopThread(0.02, () -> paddleControl(player1, 5.0, KeyCode.A, KeyCode.Z), "player1");
    addLoopThread(0.02, () -> paddleControl(player2, 5.0, KeyCode.UP, KeyCode.DOWN), "player2");
    addLoopThread(0.02, () -> ballControl(ball, 6.0, paddles), "ball");
  }

  public void followMouse(Sprite sprite, double vel) {
    sprite.pointAt(getInputs().getMouseLocation());
    sprite.moveForward(vel);
  }

  public void followSprite(Sprite sprite, Sprite target, double vel) {
    sprite.pointAt(target.getLocation());
    sprite.moveForward(vel);
  }

  public void followArrowKeys(Sprite sprite, double vel) {
    double dx = 0.0;
    double dy = 0.0;
    if (getInputs().isKeyPressed(KeyCode.UP)) {
      dy += 1.0;
    }
    if (getInputs().isKeyPressed(KeyCode.DOWN)) {
      dy -= 1.0;
    }
    if (getInputs().isKeyPressed(KeyCode.RIGHT)) {
      dx += 1.0;
    }
    if (getInputs().isKeyPressed(KeyCode.LEFT)) {
      dx -= 1.0;
    }
    if (dx == 0.0 && dy == 0.0) {
      return;
    }
    sprite.pointAt(Location.create(sprite.getLocation().x() + dx, sprite.getLocation().y() + dy));
    sprite.moveForward(vel);
  }

  public void paddleControl(Sprite sprite, double vel, KeyCode upkey, KeyCode downkey) {
    double dy = 0.0;
    if (getInputs().isKeyPressed(upkey)) {
      dy += vel;
    }
    if (getInputs().isKeyPressed(downkey)) {
      dy -= vel;
    }
    double targetY = sprite.getLocation().y() + dy;
    if (targetY > 200.0) {
      targetY = 200.0;
    } else if (targetY < -200.0) {
      targetY = -200.0;
    }
    Location targetPos = Location.create(sprite.getLocation().x(), targetY);
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
        ball.setDirection(ball.getDirection() * -1);
      }
    }
  }
}
