package ipp;

import java.util.HashMap;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Inputs {

  private double mouseX;
  private double mouseY;
  private Screen screen;
  private Map<KeyCode, Boolean> pressedKeys;

  public void init(Scene scene, Screen screen) {
    this.screen = screen;
    pressedKeys = new HashMap<>();
    scene.setOnMouseMoved(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
          }
        });
    scene.setOnKeyPressed(
        new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
            pressedKeys.put(event.getCode(), true);
          }
        });
    scene.setOnKeyReleased(
        new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
            pressedKeys.put(event.getCode(), false);
          }
        });
  }

  public Location getMouseLocation() {
    return screen.fromScreenSpace(Location.create(mouseX, mouseY));
  }

  public boolean isKeyPressed(KeyCode code) {
    return pressedKeys.getOrDefault(code, false);
  }
}
