package ipp;

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class SnapApp extends Application {

  private Screen screen;
  private Animator animator;
  private Inputs inputs;

  public abstract void start();

  public void start(Stage stage) {
    screen = new Screen(600, 450);
    animator = TimeAnimator.createJavaFXTimeAnimator();
    inputs = new Inputs();
    Scene scene = new Scene(screen.getGroup(), screen.getWidth(), screen.getHeight());
    inputs.init(scene, screen);
    stage.setTitle("Introductory Programming Principles");
    stage.setScene(scene);
    stage.show();
    stage.setOnCloseRequest(
        event -> {
          Platform.exit();
          System.exit(0);
        });
    start();
  }

  public Sprite addSprite(String name) {
    try {
      Sprite sprite = Sprite.makeDefaultSprite(name, animator);
      screen.addSprite(sprite);
      return sprite;
    } catch (FileNotFoundException e) {
      System.out.println(e);
      Platform.exit();
      return null;
    }
  }

  public Sprite addSprite(String name, String filepath, double height, double width) {
    try {
      Sprite sprite = new TestSprite(name, animator, filepath, height, width);
      screen.addSprite(sprite);
      return sprite;
    } catch (FileNotFoundException e) {
      System.out.println(e);
      Platform.exit();
      return null;
    }
  }

  public void removeSprite(Sprite sprite) {
    screen.removeSprite(sprite);
  }

  public Inputs getInputs() {
    return inputs;
  }

  public void addThread(Runnable runnable) {
    new Thread(runnable).start();
  }

  public void addLoopThread(double secs, Runnable loopable, String name) {
    long millis = (long) (secs * 1000.0);
    addThread(
        () -> {
          while (true) {
            loopable.run();
            try {
              Thread.sleep(millis);
            } catch (InterruptedException e) {
              // ignoring
            }
          }
        });
  }
}
