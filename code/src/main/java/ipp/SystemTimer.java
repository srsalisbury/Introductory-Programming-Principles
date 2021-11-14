package ipp;

import java.util.TimerTask;

public class SystemTimer implements Timer {

  private java.util.Timer timer = new java.util.Timer();

  @Override
  public void schedule(TimerTask task) {
    timer.scheduleAtFixedRate(task, 20l, 20l);
  }

  @Override
  public void cancel() {
    timer.cancel();
  }
}
