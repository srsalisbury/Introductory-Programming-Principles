package ipp;

public interface Animator {

  public interface Callback {

    void update(double amountDone);

    void done();
  }

  void startAnimation(double secs, Callback callback);

  void stop();
}
