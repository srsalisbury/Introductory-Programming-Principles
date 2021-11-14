package ipp;

import java.io.FileNotFoundException;
import javafx.scene.image.Image;

public interface Sprite {

  static Sprite makeDefaultSprite(String name, Animator animator) throws FileNotFoundException {
    return new TestSprite(
        name, animator, "C:\\Users\\steve\\Desktop\\Desktop\\Test_Cursor.png", 20.0, 20.0);
  }

  String name();

  void moveTo(Location loc);

  default void moveTo(double x, double y) {
    moveTo(Location.create(x, y));
  }

  void glideTo(Location loc, double secs);

  default void glideTo(double x, double y, double secs) {
    glideTo(Location.create(x, y), secs);
  }

  void setDirection(double dir);

  void pointAt(Location loc);

  void turnLeft(double deg);

  void turnRight(double deg);

  void moveForward(double dist);

  void penUp();

  void penDown();

  void setPen(boolean isDown);

  void setPenColor(Color color);

  void setPenSize(double size);

  void setImage(String path, double height, double width);

  void wait(double secs);

  boolean isTouching(Sprite other);

  Location getLocation();

  double getDirection();

  double getHBHeight();

  double getHBWidth();

  boolean isPenDown();

  Color getPenColor();

  double getPenSize();

  Image getImage();

  void addListener(Listener listener);

  void removeListener(Listener listener);

  public interface Listener {

    void moved(Location fromLoc);

    void turned();

    void changedImage(Image image, double height, double width);
  }
}
