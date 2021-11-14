package ipp;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Location {

  public static Location create(double x, double y) {
    return new AutoValue_Location(x, y);
  }

  public abstract double x();

  public abstract double y();
}
