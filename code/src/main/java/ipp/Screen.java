package ipp;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class Screen {

  private Canvas canvas;
  private GraphicsContext gc;
  private Group group = new Group();
  private Map<Sprite, SpriteListener> listeners = new HashMap<>();

  public Screen(int width, int height) {
    canvas = new Canvas(width, height);
    gc = canvas.getGraphicsContext2D();
    gc.setLineCap(StrokeLineCap.ROUND);
    group.getChildren().add(canvas);
  }

  public void clear() {}

  public Group getGroup() {
    return group;
  }

  public int getWidth() {
    return (int) canvas.getWidth();
  }

  public int getHeight() {
    return (int) canvas.getHeight();
  }

  public Location toScreenSpace(Location loc) {
    return Location.create(loc.x() + getWidth() / 2, -1.0 * loc.y() + getHeight() / 2);
  }

  public Location fromScreenSpace(Location loc) {
    return Location.create(loc.x() - getWidth() / 2, -1.0 * (loc.y() - getHeight() / 2));
  }

  public void addSprite(Sprite sprite) {
    SpriteListener listener = new SpriteListener(sprite);
    listeners.put(sprite, listener);
    sprite.addListener(listener);
    Platform.runLater(() -> group.getChildren().add(listener.imageView));
  }

  public void removeSprite(Sprite sprite) {
    SpriteListener listener = listeners.remove(sprite);
    if (listener == null) {
      System.err.println("Attempted to remove nonexistent sprite");
      return;
    }
    sprite.removeListener(listener);
    Platform.runLater(() -> group.getChildren().remove(listener.imageView));
  }

  private void drawLine(Location start, Location end, ipp.Color color, double thickness) {
    gc.setStroke(Color.color(color.r(), color.g(), color.b()));
    gc.setLineWidth(thickness);
    gc.beginPath();
    gc.moveTo(start.x(), start.y());
    gc.lineTo(end.x(), end.y());
    gc.stroke();
  }

  private class SpriteListener implements Sprite.Listener {

    private Sprite sprite;
    private ImageView imageView;

    public SpriteListener(Sprite sprite) {
      this.sprite = sprite;
      this.imageView = initImage(sprite);
    }

    private ImageView initImage(Sprite sprite) {
      ImageView iV = new ImageView(sprite.getImage());
      iV.setFitHeight(sprite.getHBHeight());
      iV.setFitWidth(sprite.getHBWidth());
      iV.setX(sprite.getLocation().x() - iV.getFitWidth() / 2);
      iV.setY(sprite.getLocation().y() - iV.getFitHeight() / 2);
      iV.setRotate(sprite.getDirection());
      iV.setPreserveRatio(true);
      return iV;
    }

    public void moved(Location fromLoc) {
      boolean penDown = sprite.isPenDown();
      ipp.Color penColor = sprite.getPenColor();
      double penSize = sprite.getPenSize();
      Location screenFromLoc = toScreenSpace(fromLoc);
      Location screenLoc = toScreenSpace(sprite.getLocation());
      Platform.runLater(
          () -> {
            imageView.setX(screenLoc.x() - imageView.getFitWidth() / 2);
            imageView.setY(screenLoc.y() - imageView.getFitHeight() / 2);
            if (penDown) {
              drawLine(screenFromLoc, screenLoc, penColor, penSize);
            }
          });
    }

    public void turned() {
      double direction = sprite.getDirection();
      Platform.runLater(() -> imageView.setRotate(direction));
    }

    public void changedImage(Image image, double height, double width) {
      Platform.runLater(
          () -> {
            imageView.setImage(image);
            imageView.setFitHeight(height);
            imageView.setFitWidth(width);
            Location screenLoc = toScreenSpace(sprite.getLocation());
            imageView.setX(screenLoc.x() - imageView.getFitWidth() / 2);
            imageView.setY(screenLoc.y() - imageView.getFitHeight() / 2);
          });
    }
  }
}
