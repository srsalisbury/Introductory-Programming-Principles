package exercises;

import ipp.Color;
import ipp.Location;
import ipp.SnapApp;
import ipp.Sprite;
import javafx.scene.input.KeyCode;

public class SpriteFollow extends SnapApp {

  @Override
  public void start() {
    Sprite sprite1 = addSprite("sprite1");
    Sprite sprite2 = addSprite("sprite2");
    Sprite sprite3 = addSprite("sprite3");
    Sprite sprite4 = addSprite("sprite4");
    Sprite sprite5 = addSprite("sprite5");
    addLoopThread(0.02, () -> followMouse(sprite1, 5.0), "sprite1");
    addLoopThread(0.02, () -> followSprite(sprite2, sprite1, 4.8), "sprite2");
    addLoopThread(0.02, () -> followSprite(sprite3, sprite2, 4.6), "sprite3");
    addLoopThread(0.02, () -> followSprite(sprite4, sprite3, 4.4), "sprite4");
    addLoopThread(0.02, () -> followSprite(sprite5, sprite4, 4.2), "sprite5");
  }

  public void followMouse(Sprite sprite, double vel) {
    sprite.pointAt(getInputs().getMouseLocation());
    sprite.moveForward(vel);
  }

  public void followSprite(Sprite sprite, Sprite target, double vel) {
    sprite.pointAt(target.getLocation());
    sprite.moveForward(vel);
  }
}