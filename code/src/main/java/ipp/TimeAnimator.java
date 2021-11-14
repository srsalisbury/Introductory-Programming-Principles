package ipp;

import com.google.auto.value.AutoValue;
import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;

public class TimeAnimator implements Animator {

  private Timer timer;
  private List<AnimatedTask> tasks = new ArrayList<>();
  private Ticker ticker;
  private boolean stopWhenDone = false;

  public static TimeAnimator createTestTimeAnimator(Ticker ticker, Timer timer) {
    return new TimeAnimator(ticker, timer, false);
  }

  public static TimeAnimator createJavaFXTimeAnimator() {
    return new TimeAnimator(Ticker.systemTicker(), new SystemTimer(), true);
  }

  private TimeAnimator(Ticker ticker, Timer timer, boolean javaFX) {
    this.ticker = ticker;
    this.timer = timer;
    TimerTask task;
    if (javaFX) {
      task =
          new TimerTask() {
            @Override
            public void run() {
              Platform.runLater(
                  new Runnable() {
                    @Override
                    public void run() {
                      stepSprites();
                    }
                  });
            }
          };
    } else {
      task =
          new TimerTask() {
            @Override
            public void run() {
              stepSprites();
            }
          };
    }
    timer.schedule(task);
  }

  private void stepSprites() {
    List<AnimatedTask> completeTasks = new ArrayList<>();
    for (AnimatedTask task : tasks) {
      if (task.isDone()) {
        completeTasks.add(task);
      } else {
        task.step();
      }
    }
    for (AnimatedTask task : completeTasks) {
      task.done();
    }
    tasks.removeAll(completeTasks);
    if (tasks.isEmpty() && stopWhenDone) {
      timer.cancel();
    }
  }

  @Override
  public synchronized void startAnimation(double secs, Callback callback) {
    tasks.add(AnimatedTask.create(secs, callback, Stopwatch.createStarted(ticker)));
  }

  @Override
  public synchronized void stop() {
    stopWhenDone = true;
  }

  @AutoValue
  public abstract static class AnimatedTask {

    public static AnimatedTask create(double totalSecs, Callback callback, Stopwatch stopwatch) {
      return new AutoValue_TimeAnimator_AnimatedTask(
          (long) (totalSecs * 1000.0), callback, stopwatch);
    }

    private double amountDone() {
      if (totalMillis() <= 0) {
        return 1.0;
      }
      return (double) stopwatch().elapsed(TimeUnit.MILLISECONDS) / totalMillis();
    }

    public boolean isDone() {
      return amountDone() >= 1.0;
    }

    public void step() {
      callback().update(amountDone());
    }

    public void done() {
      callback().done();
    }

    public abstract long totalMillis();

    public abstract Callback callback();

    public abstract Stopwatch stopwatch();
  }
}
