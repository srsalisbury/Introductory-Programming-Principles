package ipp;

import static com.google.common.truth.Truth.assertAbout;
import static ipp.Testing.EPSILON;

import com.google.common.truth.FailureMetadata;
import com.google.common.truth.Subject;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class LocationSubject extends Subject<LocationSubject, Location> {

  public static LocationSubject assertThat(@NullableDecl Location location) {
    return assertAbout(LOCATION_SUBJECT_FACTORY).that(location);
  }

  public static Subject.Factory<LocationSubject, Location> locations() {
    return LOCATION_SUBJECT_FACTORY;
  }

  private static final Subject.Factory<LocationSubject, Location> LOCATION_SUBJECT_FACTORY =
      LocationSubject::new;

  private LocationSubject(FailureMetadata failureMetadata, @NullableDecl Location subject) {
    super(failureMetadata, subject);
  }

  public void isApproximatelyEqualTo(Location other) {
    isApproximatelyEqualTo(EPSILON, other);
  }

  public void isApproximatelyEqualTo(double epsilon, Location other) {
    check("x()").that(actual().x()).isWithin(epsilon).of(other.x());
    check("y()").that(actual().y()).isWithin(epsilon).of(other.y());
  }
}
