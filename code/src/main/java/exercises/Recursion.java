package exercises;

import ipp.SnapApp;
import ipp.Sprite;
import java.util.Random;

public class Recursion extends SnapApp {

  @Override
  public void start() {
    // Sprite sprite1 = addSprite("sprite1");
    Sprite sprite2 = addSprite("sprite2");
    // sprite1.moveTo(0.0, 0.0);
    // sprite1.setDirection(0.0);
    // addThread(() -> drawTree(sprite1, 50.0));
    // addThread(() -> {
    //   double r = 150.0;
    //   sprite2.setDirection(-60.0);
    //   sprite2.moveForward(r);
    //   sprite2.setDirection(90.0);
    //   snowflake(sprite2, (r / Math.sqrt(3.0)));
    //   sprite2.turnRight(120.0);
    //   snowflake(sprite2, (r / Math.sqrt(3.0)));
    //   sprite2.turnRight(120.0);
    //   snowflake(sprite2, (r / Math.sqrt(3.0)));
    //   sprite2.moveTo(0.0, 0.0);
    //   sprite2.setDirection(0.0);
    // });
    addThread(
        () -> {
          sprite2.moveTo(-200.0, 0.0);
          sprite2.setDirection(90.0);
          wave(sprite2, 100.0);
        });
  }

  public void drawTree(Sprite sprite, double len) {
    if (len < 10.0) {
      return;
    }
    Random rand = new Random();
    double leftTurn = 15.0 + rand.nextInt(10);
    double rightTurn = 15.0 + rand.nextInt(10);
    sprite.penDown();
    sprite.moveForward(len);
    sprite.penUp();
    sprite.turnLeft(leftTurn);
    drawTree(sprite, (len * 0.8));
    sprite.turnRight(rightTurn + leftTurn);
    drawTree(sprite, (len * 0.8));
    sprite.turnLeft(rightTurn);
    sprite.moveForward(-len);
  }

  public void snowflake(Sprite sprite, double len) {
    if (len < 2.0) {
      sprite.penDown();
      sprite.moveForward(len * 3.0);
      sprite.penUp();
      return;
    }
    snowflake(sprite, (len / 3.0));
    sprite.turnLeft(60.0);
    snowflake(sprite, (len / 3.0));
    sprite.turnRight(120.0);
    snowflake(sprite, (len / 3.0));
    sprite.turnLeft(60.0);
    snowflake(sprite, (len / 3.0));
  }

  public void wave(Sprite sprite, double len) {
    if (len < 2.0) {
      sprite.penDown();
      sprite.moveForward(len * 4.0);
      sprite.penUp();
      return;
    }
    wave(sprite, (len / 4.0));
    sprite.turnLeft(90.0);
    wave(sprite, (len / 4.0));
    sprite.turnRight(90.0);
    wave(sprite, (len / 4.0));
    sprite.turnRight(90.0);
    wave(sprite, (len / 4.0));
    wave(sprite, (len / 4.0));
    sprite.turnLeft(90.0);
    wave(sprite, (len / 4.0));
    sprite.turnLeft(90.0);
    wave(sprite, (len / 4.0));
    sprite.turnRight(90.0);
    wave(sprite, (len / 4.0));
  }
}
