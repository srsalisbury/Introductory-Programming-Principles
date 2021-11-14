package exercises;

import ipp.Color;
import ipp.Location;
import ipp.SnapApp;
import ipp.Sprite;
import javafx.scene.input.KeyCode;

public class Exercise extends SnapApp {

  @Override
  public void start() {
    Sprite sprite1 = addSprite("sprite1");
    Sprite sprite2 = addSprite("sprite2");
    Sprite sprite3 = addSprite("sprite3");
    Sprite sprite4 = addSprite("sprite4");
    // Sprite sprite5 = addSprite("sprite5");
    // addLoopThread(0.02, () -> followMouse(sprite1, 5.0), "sprite1");
    // addLoopThread(0.02, () -> followSprite(sprite2, sprite1, 4.8), "sprite2");
    // addLoopThread(0.02, () -> followSprite(sprite3, sprite2, 4.6), "sprite3");
    // addLoopThread(0.02, () -> followSprite(sprite4, sprite3, 4.4), "sprite4");
    // addLoopThread(0.02, () -> followSprite(sprite5, sprite4, 4.2), "sprite5");
    // addLoopThread(0.02, () -> followArrowKeys(redSprite, 5.0));
    addThread(() -> drawHexagon(sprite1));
    // addThread(() -> cyclePolygons(sprite1, 200, 3, 0.0, 0.0, 11));
    // drawTriangleSequential(sprite1, 0.0, 0.0, 100.0);
    // sprite1.penDown();
    // sprite1.moveTo(Location.create(100.0, 100.0));
    // addThread(
    //     () -> {
    //       sprite1.moveTo(-150.0, 150.0);
    //       sprite1.penDown();
    //       sprite1.glideTo(150.0, 150.0, 2.0);
    //       sprite1.setPenColor(Color.RED);
    //       sprite1.setPenSize(3.0);
    //       sprite1.glideTo(-150.0, 150.0, 2.0);
    //     });
    // addThread(
    //     () -> {
    //       sprite2.moveTo(150.0, 150.0);
    //       sprite2.penDown();
    //       sprite2.glideTo(150.0, -150.0, 2.0);
    //       sprite2.setPenColor(Color.RED);
    //       sprite2.setPenSize(3.0);
    //       sprite2.glideTo(150.0, 150.0, 2.0);
    //     });
    // addThread(
    //     () -> {
    //       sprite3.moveTo(150.0, -150.0);
    //       sprite3.penDown();
    //       sprite3.glideTo(-150.0, -150.0, 2.0);
    //       sprite3.setPenColor(Color.RED);
    //       sprite3.setPenSize(3.0);
    //       sprite3.glideTo(150.0, -150.0, 2.0);
    //     });
    // addThread(
    //     () -> {
    //       sprite4.moveTo(-150.0, -150.0);
    //       sprite4.penDown();
    //       sprite4.glideTo(-150.0, 150.0, 2.0);
    //       sprite4.setPenColor(Color.RED);
    //       sprite4.setPenSize(3.0);
    //       sprite4.glideTo(-150.0, -150.0, 2.0);
    //     });
  }

  public void drawTriangleSequential(Sprite sprite, double cX, double cY, double sideLength) {
    sprite.penUp();
    sprite.moveTo(Location.create(cX, cY));
    sprite.penDown();
    sprite.turnRight(150.0);
    sprite.moveForward(sideLength);
    sprite.turnRight(120.0);
    sprite.moveForward(sideLength);
    sprite.turnRight(120.0);
    sprite.moveForward(sideLength);
    sprite.turnLeft(30.0);
    sprite.penUp();
  }

  public void drawHexagon(Sprite sprite) {
    sprite.setPenColor(Color.BLACK);
    sprite.moveTo(0.0, 0.0);
    sprite.setDirection(90.0);
    sprite.penDown();
    for (int i = 0; i < 6; i++) {
      sprite.moveForward(60.0);
      sprite.turnLeft(60.0);
    }
    sprite.penUp();
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

  public void drawPolygon(
      Sprite sprite,
      int radius,
      int segments,
      double centerX,
      double centerY,
      double startDegrees) {
    double startRadians = (Math.PI / 180.0) * startDegrees;
    sprite.penUp();
    sprite.moveTo(
        Location.create(
            centerX + radius * Math.sin(startRadians), centerY + radius * Math.cos(startRadians)));
    sprite.penDown();
    for (int i = 1; i <= segments; i++) {
      sprite.moveTo(
          Location.create(
              centerX + radius * Math.sin(((2 * Math.PI) * i / segments) + startRadians),
              centerY + radius * Math.cos(((2 * Math.PI) * i / segments) + startRadians)));
    }
    sprite.penUp();
    sprite.moveTo(Location.create(centerX, centerY));
  }

  public void cyclePolygons(
      Sprite sprite, int radius, int segments, double centerX, double centerY, int numPolygons) {
    for (int i = 0; i < numPolygons; i++) {
      drawPolygon(sprite, radius, segments, centerX, centerY, 360.0 * i / numPolygons);
    }
  }
}
