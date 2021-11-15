package exercises;

import ipp.Color;
import ipp.Location;
import ipp.SnapApp;
import ipp.Sprite;
import javafx.scene.input.KeyCode;

public class PolygonDraw extends SnapApp {

  @Override
  public void start() {
    Sprite sprite1 = addSprite("sprite1");
    Sprite sprite2 = addSprite("sprite2");
    Sprite sprite3 = addSprite("sprite3");
    addThread(() -> drawHexagon(sprite1));
    addThread(() -> cyclePolygons(sprite2, 200, 3, 0.0, 0.0, 11));
    addThread(() -> drawTriangleSequential(sprite3, 0.0, 0.0, 100.0));
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