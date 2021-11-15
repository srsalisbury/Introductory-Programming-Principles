package exercises;

import ipp.Color;
import ipp.Location;
import ipp.SnapApp;
import ipp.Sprite;
import javafx.scene.input.KeyCode;

public class SpriteDraw extends SnapApp {

  @Override
  public void start() {
    Sprite sprite1 = addSprite("sprite1");
    Sprite sprite2 = addSprite("sprite2");
    Sprite sprite3 = addSprite("sprite3");
    Sprite sprite4 = addSprite("sprite4");
    addThread(
        () -> {
          sprite1.moveTo(-150.0, 150.0);
          sprite1.penDown();
          sprite1.glideTo(150.0, 150.0, 2.0);
          sprite1.setPenColor(Color.RED);
          sprite1.setPenSize(3.0);
          sprite1.glideTo(-150.0, 150.0, 2.0);
        });
    addThread(
        () -> {
          sprite2.moveTo(150.0, 150.0);
          sprite2.penDown();
          sprite2.glideTo(150.0, -150.0, 2.0);
          sprite2.setPenColor(Color.RED);
          sprite2.setPenSize(3.0);
          sprite2.glideTo(150.0, 150.0, 2.0);
        });
    addThread(
        () -> {
          sprite3.moveTo(150.0, -150.0);
          sprite3.penDown();
          sprite3.glideTo(-150.0, -150.0, 2.0);
          sprite3.setPenColor(Color.RED);
          sprite3.setPenSize(3.0);
          sprite3.glideTo(150.0, -150.0, 2.0);
        });
    addThread(
        () -> {
          sprite4.moveTo(-150.0, -150.0);
          sprite4.penDown();
          sprite4.glideTo(-150.0, 150.0, 2.0);
          sprite4.setPenColor(Color.RED);
          sprite4.setPenSize(3.0);
          sprite4.glideTo(-150.0, -150.0, 2.0);
        });
  }
}