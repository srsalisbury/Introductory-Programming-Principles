package ipp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javafx.scene.image.Image;

public class TestSprite implements Sprite {

  private String name;
  private Location location;
  private double direction;
  private double hbHeight;
  private double hbWidth;
  private boolean penDown;
  private Color penColor;
  private double penSize;
  private List<Listener> listeners;
  private Animator animator;
  private String resourcePath;
  private Image image;

  public TestSprite(String name, Animator animator, String resourcePath, double height, double width)
      throws IllegalArgumentException {
    this.name = name;
    location = Location.create(0.0, 0.0);
    direction = 0.0;
    penDown = false;
    penColor = Color.BLACK;
    penSize = 1.0;
    listeners = new ArrayList<>();
    this.animator = animator;
    setImage(resourcePath, height, width);
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public void moveTo(Location loc) {
    Location oldLoc = location;
    location = loc;
    for (Listener listener : listeners) {
      listener.moved(oldLoc);
    }
  }

  @Override
  public void glideTo(Location loc, double secs) {
    Location startLoc = location;
    double dx = loc.x() - startLoc.x();
    double dy = loc.y() - startLoc.y();
    CountDownLatch doneSignal = new CountDownLatch(1);
    animator.startAnimation(
        secs,
        new Animator.Callback() {
          @Override
          public void update(double amountDone) {
            moveTo(Location.create(startLoc.x() + amountDone * dx, startLoc.y() + amountDone * dy));
          }

          @Override
          public void done() {
            moveTo(loc);
            doneSignal.countDown();
          }
        });
    try {
      doneSignal.await();
    } catch (InterruptedException e) {
      // ignoring
    }
  }

  @Override
  public void setDirection(double dir) {
    double d = dir;
    while (d > 180.0) {
      d -= 360.0;
    }
    while (d <= -180.0) {
      d += 360.0;
    }
    direction = d;
    for (Listener listener : listeners) {
      listener.turned();
    }
  }

  @Override
  public void pointAt(Location loc) {
    double opp = loc.y() - location.y();
    double adj = loc.x() - location.x();
    double dir = Math.toDegrees(Math.atan2(opp, adj));
    setDirection(-1.0 * (dir - 90.0));
  }

  @Override
  public void turnLeft(double deg) {
    setDirection(direction - deg);
  }

  @Override
  public void turnRight(double deg) {
    setDirection(direction + deg);
  }

  @Override
  public void moveForward(double dist) {
    double dx = dist * Math.cos(Math.toRadians((-1.0 * direction) + 90.0));
    double dy = dist * Math.sin(Math.toRadians((-1.0 * direction) + 90.0));
    moveTo(Location.create(location.x() + dx, location.y() + dy));
  }

  @Override
  public void penUp() {
    penDown = false;
  }

  @Override
  public void penDown() {
    penDown = true;
  }

  @Override
  public void setPen(boolean isDown) {
    penDown = isDown;
  }

  @Override
  public void setPenColor(Color color) {
    penColor = color;
  }

  @Override
  public void setPenSize(double size) {
    penSize = size;
  }

  @Override
  public void setImage(String path, double height, double width) throws IllegalArgumentException {
    if (path == resourcePath) {
      return;
    }
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
    if (inputStream == null) {
      throw new IllegalArgumentException("Invalid path given: " + path);
    }
    image = new Image(inputStream);
    resourcePath = path;
    hbHeight = height;
    hbWidth = width;
    for (Listener listener : listeners) {
      listener.changedImage(image, height, width);
    }
  }

  @Override
  public void wait(double secs) {
    CountDownLatch doneSignal = new CountDownLatch(1);
    animator.startAnimation(
        secs,
        new Animator.Callback() {
          @Override
          public void update(double amountDone) {}

          @Override
          public void done() {
            doneSignal.countDown();
          }
        });
    try {
      doneSignal.await();
    } catch (InterruptedException e) {
      // ignoring
    }
  }

  @Override
  public boolean isTouching(Sprite other) {
    return Hitbox.isTouching(this, other);
  }

  @Override
  public Location getLocation() {
    return location;
  }

  @Override
  public double getDirection() {
    return direction;
  }

  @Override
  public double getHBHeight() {
    return hbHeight;
  }

  @Override
  public double getHBWidth() {
    return hbWidth;
  }

  @Override
  public boolean isPenDown() {
    return penDown;
  }

  @Override
  public Color getPenColor() {
    return penColor;
  }

  @Override
  public double getPenSize() {
    return penSize;
  }

  @Override
  public Image getImage() {
    return image;
  }

  @Override
  public void addListener(Listener listener) {
    listeners.add(listener);
  }

  @Override
  public void removeListener(Listener listener) {
    listeners.remove(listener);
  }
}
