package ipp;

import java.util.TimerTask;

public interface Timer {

  void schedule(TimerTask task);

  void cancel();
}
