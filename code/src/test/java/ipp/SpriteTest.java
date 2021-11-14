package ipp;

import static com.google.common.truth.Truth.assertThat;
import static ipp.LocationSubject.assertThat;
import static ipp.Testing.EPSILON;

import com.google.common.testing.FakeTicker;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SpriteTest {

  private Animator animator;
  private FakeTicker fakeTicker = new FakeTicker();
  private FakeTimer fakeTimer = new FakeTimer();

  @Before
  public void initialize() {
    animator = TimeAnimator.createTestTimeAnimator(fakeTicker, fakeTimer);
  }

  @Test
  public void checkPointAt() throws Exception {
    Sprite s = Sprite.makeDefaultSprite("testsprite", animator);
    s.pointAt(Location.create(1.0, 1.0));
    assertThat(s.getDirection()).isWithin(EPSILON).of(45.0);
    s.pointAt(Location.create(1.0, 0.0));
    assertThat(s.getDirection()).isWithin(EPSILON).of(90.0);
    s.pointAt(Location.create(0.0, 1.0));
    assertThat(s.getDirection()).isWithin(EPSILON).of(0.0);
    s.pointAt(Location.create(-1.0, 0.0));
    assertThat(s.getDirection()).isWithin(EPSILON).of(-90.0);
    s.pointAt(Location.create(0.0, -1.0));
    assertThat(s.getDirection()).isWithin(EPSILON).of(180.0);
  }

  @Test
  public void checkMoveForward() throws Exception {
    Sprite s = Sprite.makeDefaultSprite("testsprite", animator);
    s.pointAt(Location.create(1.0, 1.0));
    s.moveForward(5.0);
    double x = 5.0 / Math.sqrt(2.0);
    assertThat(s.getLocation()).isApproximatelyEqualTo(Location.create(x, x));
    s.moveTo(1.0, 1.0);
    s.setDirection(-90.0);
    s.moveForward(2.0);
    assertThat(s.getLocation()).isApproximatelyEqualTo(Location.create(-1.0, 1.0));
    s.setDirection(180.0);
    s.moveForward(2.0);
    assertThat(s.getLocation()).isApproximatelyEqualTo(Location.create(-1.0, -1.0));
    s.setDirection(90.0);
    s.moveForward(2.0);
    assertThat(s.getLocation()).isApproximatelyEqualTo(Location.create(1.0, -1.0));
    s.setDirection(0.0);
    s.moveForward(2.0);
    assertThat(s.getLocation()).isApproximatelyEqualTo(Location.create(1.0, 1.0));
  }

  @Test
  public void testDirection() throws Exception {
    Sprite s = Sprite.makeDefaultSprite("testsprite", animator);
    s.setDirection(270.0);
    assertThat(s.getDirection()).isWithin(EPSILON).of(-90.0);
    s.setDirection(178.9f);
    s.turnRight(1.0);
    assertThat(s.getDirection()).isWithin(EPSILON).of(179.9);
    s.turnRight(1.0);
    assertThat(s.getDirection()).isWithin(EPSILON).of(-179.1);
    s.turnLeft(1.0);
    assertThat(s.getDirection()).isWithin(EPSILON).of(179.9);
  }

  @Test
  public void testGlide() throws Exception {
    Sprite s = Sprite.makeDefaultSprite("testsprite", animator);
    new Thread(() -> s.glideTo(2.0, 2.0, 1.0)).start();
    try {
      // Sleeping thread allows computer to complete initialization of other thread, allowing
      // glideTo to set up.
      // glideTo is on other thread because it does not return until it completes
      // which makes testing it incrementally not possible on one thread.
      Thread.sleep(10l);
    } catch (InterruptedException e) {
      // ignoring
    }
    fakeTicker.advance(500, TimeUnit.MILLISECONDS);
    fakeTimer.trigger();
    assertThat(s.getLocation()).isApproximatelyEqualTo(Location.create(1.0, 1.0));
    fakeTicker.advance(250, TimeUnit.MILLISECONDS);
    fakeTimer.trigger();
    assertThat(s.getLocation()).isApproximatelyEqualTo(Location.create(1.5, 1.5));
    fakeTicker.advance(300, TimeUnit.MILLISECONDS);
    fakeTimer.trigger();
    assertThat(s.getLocation()).isApproximatelyEqualTo(Location.create(2.0, 2.0));
  }

  @Test
  public void testWait() throws Exception {
    Sprite s = Sprite.makeDefaultSprite("testsprite", animator);
    new Thread(
            () -> {
              s.moveTo(0.0, 0.0);
              s.wait(2.0);
              s.moveTo(1.0, 1.0);
              s.wait(0.0);
              s.moveTo(2.0, 2.0);
              s.wait(-1.0);
              s.moveTo(3.0, 3.0);
            })
        .start();
    try {
      // Sleep commands used to allow wait commands to initialize each time they are called
      Thread.sleep(10l);
      assertThat(s.getLocation()).isApproximatelyEqualTo(Location.create(0.0, 0.0));
      fakeTicker.advance(1000, TimeUnit.MILLISECONDS);
      fakeTimer.trigger();
      Thread.sleep(10l);
      assertThat(s.getLocation()).isApproximatelyEqualTo(Location.create(0.0, 0.0));
      fakeTicker.advance(1000, TimeUnit.MILLISECONDS);
      fakeTimer.trigger();
      Thread.sleep(10l);
      assertThat(s.getLocation()).isApproximatelyEqualTo(Location.create(1.0, 1.0));
      fakeTicker.advance(1, TimeUnit.MILLISECONDS);
      fakeTimer.trigger();
      Thread.sleep(10l);
      assertThat(s.getLocation()).isApproximatelyEqualTo(Location.create(2.0, 2.0));
      fakeTicker.advance(1, TimeUnit.MILLISECONDS);
      fakeTimer.trigger();
      Thread.sleep(10l);
      assertThat(s.getLocation()).isApproximatelyEqualTo(Location.create(3.0, 3.0));
    } catch (InterruptedException e) {
      // ignoring
    }
  }

  @Test
  public void testCollision() throws Exception {
    assertThat(
            Hitbox.triArea(
                Location.create(-2.0, 0.0), Location.create(1.0, 1.0), Location.create(1.0, -1.0)))
        .isEqualTo(3.0);
    assertThat(
            Hitbox.quadArea(
                Location.create(1.0, 1.0),
                Location.create(-1.0, 1.0),
                Location.create(-1.0, -1.0),
                Location.create(1.0, -1.0)))
        .isEqualTo(4.0);
    Sprite s1 = Sprite.makeDefaultSprite("testsprite1", animator);
    Sprite s2 = Sprite.makeDefaultSprite("testsprite2", animator);
    s1.moveTo(40.0, 0.0);
    s2.moveTo(-40.0, 0.0);
    fakeTicker.advance(100, TimeUnit.MILLISECONDS);
    fakeTimer.trigger();
    assertThat(s1.isTouching(s2)).isEqualTo(false);
    assertThat(s2.isTouching(s1)).isEqualTo(false);
    s1.moveTo(11.0, 0.0);
    s2.moveTo(-11.0, 0.0);
    fakeTicker.advance(100, TimeUnit.MILLISECONDS);
    fakeTimer.trigger();
    assertThat(s1.isTouching(s2)).isEqualTo(false);
    assertThat(s2.isTouching(s1)).isEqualTo(false);
    s1.moveTo(9.0, 9.0);
    s2.moveTo(-9.0, -9.0);
    fakeTicker.advance(1, TimeUnit.MILLISECONDS);
    fakeTimer.trigger();
    assertThat(s1.isTouching(s2)).isEqualTo(true);
    assertThat(s2.isTouching(s1)).isEqualTo(true);
    s1.moveTo(0.0, 0.0);
    s2.moveTo(0.0, 0.0);
    fakeTicker.advance(100, TimeUnit.MILLISECONDS);
    fakeTimer.trigger();
    assertThat(s1.isTouching(s2)).isEqualTo(true);
    assertThat(s2.isTouching(s1)).isEqualTo(true);
  }
}
