package ipp;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class FakeTimer implements Timer {

  private List<TimerTask> tasks = new ArrayList<>();

  @Override
  public void schedule(TimerTask task) {
    tasks.add(task);
  }

  public void trigger() {
    for (TimerTask task : tasks) {
      task.run();
    }
  }

  @Override
  public void cancel() {
    tasks.clear();
  }
}
