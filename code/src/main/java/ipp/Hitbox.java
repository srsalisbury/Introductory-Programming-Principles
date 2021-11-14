package ipp;

public class Hitbox {

  public static boolean isTouching(Sprite sprite1, Sprite sprite2) {
    if (tooFar(sprite1, sprite2)) {
      return false;
    }
    if (tooClose(sprite1, sprite2)) {
      return true;
    }
    double sprite1X = sprite1.getLocation().x();
    double sprite1Y = sprite1.getLocation().y();
    double sprite2X = sprite2.getLocation().x();
    double sprite2Y = sprite2.getLocation().y();
    double sprite1Rot = Math.toRadians(sprite1.getDirection());
    double sprite2Rot = Math.toRadians(sprite2.getDirection());
    Location sprite1UR =
        Location.create(
            sprite1X
                + (sprite1.getHBWidth() / 2) * Math.cos(sprite1Rot)
                + (sprite1.getHBHeight() / 2) * Math.sin(sprite1Rot),
            sprite1Y
                + (sprite1.getHBWidth() / 2) * Math.sin(sprite1Rot)
                + (sprite1.getHBHeight() / 2) * Math.cos(sprite1Rot));
    Location sprite1UL =
        Location.create(
            sprite1X
                - (sprite1.getHBWidth() / 2) * Math.cos(sprite1Rot)
                + (sprite1.getHBHeight() / 2) * Math.sin(sprite1Rot),
            sprite1Y
                - (sprite1.getHBWidth() / 2) * Math.sin(sprite1Rot)
                + (sprite1.getHBHeight() / 2) * Math.cos(sprite1Rot));
    Location sprite1DR =
        Location.create(
            sprite1X
                + (sprite1.getHBWidth() / 2) * Math.cos(sprite1Rot)
                - (sprite1.getHBHeight() / 2) * Math.sin(sprite1Rot),
            sprite1Y
                + (sprite1.getHBWidth() / 2) * Math.sin(sprite1Rot)
                - (sprite1.getHBHeight() / 2) * Math.cos(sprite1Rot));
    Location sprite1DL =
        Location.create(
            sprite1X
                - (sprite1.getHBWidth() / 2) * Math.cos(sprite1Rot)
                - (sprite1.getHBHeight() / 2) * Math.sin(sprite1Rot),
            sprite1Y
                - (sprite1.getHBWidth() / 2) * Math.sin(sprite1Rot)
                - (sprite1.getHBHeight() / 2) * Math.cos(sprite1Rot));
    Location sprite2UR =
        Location.create(
            sprite2X
                + (sprite2.getHBWidth() / 2) * Math.cos(sprite2Rot)
                + (sprite2.getHBHeight() / 2) * Math.sin(sprite2Rot),
            sprite2Y
                + (sprite2.getHBWidth() / 2) * Math.sin(sprite2Rot)
                + (sprite2.getHBHeight() / 2) * Math.cos(sprite2Rot));
    Location sprite2UL =
        Location.create(
            sprite2X
                - (sprite2.getHBWidth() / 2) * Math.cos(sprite2Rot)
                + (sprite2.getHBHeight() / 2) * Math.sin(sprite2Rot),
            sprite2Y
                - (sprite2.getHBWidth() / 2) * Math.sin(sprite2Rot)
                + (sprite2.getHBHeight() / 2) * Math.cos(sprite2Rot));
    Location sprite2DR =
        Location.create(
            sprite2X
                + (sprite2.getHBWidth() / 2) * Math.cos(sprite2Rot)
                - (sprite2.getHBHeight() / 2) * Math.sin(sprite2Rot),
            sprite2Y
                + (sprite2.getHBWidth() / 2) * Math.sin(sprite2Rot)
                - (sprite2.getHBHeight() / 2) * Math.cos(sprite2Rot));
    Location sprite2DL =
        Location.create(
            sprite2X
                - (sprite2.getHBWidth() / 2) * Math.cos(sprite2Rot)
                - (sprite2.getHBHeight() / 2) * Math.sin(sprite2Rot),
            sprite2Y
                - (sprite2.getHBWidth() / 2) * Math.sin(sprite2Rot)
                - (sprite2.getHBHeight() / 2) * Math.cos(sprite2Rot));
    return (isPointWithin(sprite1UL, sprite1UR, sprite1DL, sprite1DR, sprite2UL)
        || isPointWithin(sprite1UL, sprite1UR, sprite1DL, sprite1DR, sprite2UR)
        || isPointWithin(sprite1UL, sprite1UR, sprite1DL, sprite1DR, sprite2DL)
        || isPointWithin(sprite1UL, sprite1UR, sprite1DL, sprite1DR, sprite2DR)
        || isPointWithin(sprite2UL, sprite2UR, sprite2DL, sprite2DR, sprite1UL)
        || isPointWithin(sprite2UL, sprite2UR, sprite2DL, sprite2DR, sprite1UR)
        || isPointWithin(sprite2UL, sprite2UR, sprite2DL, sprite2DR, sprite1DL)
        || isPointWithin(sprite2UL, sprite2UR, sprite2DL, sprite2DR, sprite1DR));
  }

  private static boolean tooFar(Sprite sprite1, Sprite sprite2) {
    double radius1 =
        distBetween(
            Location.create(0.0, 0.0),
            Location.create(sprite1.getHBWidth(), sprite1.getHBHeight()));
    double radius2 =
        distBetween(
            Location.create(0.0, 0.0),
            Location.create(sprite2.getHBWidth(), sprite2.getHBHeight()));
    double distance = distBetween(sprite1.getLocation(), sprite2.getLocation());
    return ((radius1 + radius2) < distance);
  }

  private static boolean tooClose(Sprite sprite1, Sprite sprite2) {
    double radius1 = Math.min(sprite1.getHBHeight(), sprite1.getHBWidth()) / 2;
    double radius2 = Math.min(sprite2.getHBHeight(), sprite2.getHBWidth()) / 2;
    double distance = distBetween(sprite1.getLocation(), sprite2.getLocation());
    return ((radius1 + radius2) >= distance);
  }

  private static boolean isPointWithin(
      Location uL, Location uR, Location dL, Location dR, Location point) {
    double area1 = triArea(uR, uL, point);
    double area2 = triArea(uL, dL, point);
    double area3 = triArea(dL, dR, point);
    double area4 = triArea(dR, uR, point);
    double areaWithin = quadArea(uR, uL, dL, dR);
    return Math.abs(area1 + area2 + area3 + area4 - areaWithin) <= 1.0e-5;
  }

  private static double distBetween(Location loc1, Location loc2) {
    return Math.sqrt(Math.pow((loc1.x() - loc2.x()), 2) + Math.pow((loc1.y() - loc2.y()), 2));
  }

  static double triArea(Location loc1, Location loc2, Location loc3) {
    return (0.5
        * Math.abs(
            loc1.x() * (loc2.y() - loc3.y())
                + loc2.x() * (loc3.y() - loc1.y())
                + loc3.x() * (loc1.y() - loc2.y())));
  }

  static double quadArea(Location uR, Location uL, Location dL, Location dR) {
    double product1 = (uR.x() * uL.y() + uL.x() * dL.y() + dL.x() * dR.y() + dR.x() * uR.y());
    double product2 = (uR.y() * uL.x() + uL.y() * dL.x() + dL.y() * dR.x() + dR.y() * uR.x());
    return (0.5 * Math.abs(product1 - product2));
  }
}
