package model;

/**
 * This helper class contains the logic for doing the levelAdjustment to an image.
 */
public class LevelsAdjustImage {
  /**
   * This method is used to level adjust the image based on given values of b, m, w.
   *
   * @param image the image to adjust leves for
   * @param b     black values
   * @param m     mid values
   * @param w     white values
   * @return the level adjusted image
   * @throws IllegalArgumentException if values are invalid
   */
  public static ImageNew levelsAdjust(ImageNew image,
                                      int b, int m, int w) throws IllegalArgumentException {
    if (b >= m || m >= w) {
      throw new IllegalArgumentException("b, m, w values should be ascending in that order");
    }

    if (b < 0 || b > 255 || m < 0 || m > 255 || w < 0 || w > 255) {
      throw new IllegalArgumentException("values should be within 0 and 255");
    }

    int numRows = image.getHeight();
    int numCols = image.getWidth();

    AbstractPixel[][] pixelGrid = new AbstractPixel[numRows][numCols];

    Double[] abcValues = getABC(b, m, w);
    double aDouble = abcValues[0];
    double bDouble = abcValues[1];
    double cDouble = abcValues[2];
    //System.out.println("_a = " + _a + ", _b = " + _b + ", _c = " + _c);


    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        AbstractPixel pixel = new ColorPixel(image.getPixel(i, j));
        int newRedValue = getNewValueForPixel(aDouble, bDouble, cDouble, pixel.getRed());
        int newGreenValue = getNewValueForPixel(aDouble, bDouble, cDouble, pixel.getGreen());
        int newBlueValue = getNewValueForPixel(aDouble, bDouble, cDouble, pixel.getBlue());

        pixelGrid[i][j] = new ColorPixel(newRedValue, newGreenValue, newBlueValue);
      }
    }


    return new ColorImageNew(pixelGrid);
  }

  /**
   * Helper method to get the new value of the pixel after level adjustment.
   *
   * @param a     the value of a
   * @param b     the value of b
   * @param c     the value of c
   * @param value the old value of the pixel
   * @return the newValue of the pixel
   */
  public static int getNewValueForPixel(double a, double b, double c, int value) {
    int newValue = (int) Math.round((a * (value * value)) + (b * value) + c);
    return adjustValueBetween0and255(newValue);
  }

  /**
   * Return the adjusted value that is in the range of 0 and 255.
   *
   * @param value the value to adjust
   * @return the adjusted value
   */
  private static int adjustValueBetween0and255(int value) {
    if (value < 0) {
      return 0;
    } else {
      return Math.min(value, 255);
    }
  }

  /**
   * Helper method to get the values of a, b,c for the quadratic equation.
   *
   * @param b the position of the black (shadow) points
   * @param m the position of the mid-points
   * @param w the position of the white (highlight) points
   * @return the array containing values of a, b and c
   */
  public static Double[] getABC(int b, int m, int w) {
    double capitalADouble = (((b * b) * (m - w)) - ((b)
            * ((m * m) - (w * w))) + (w * (m * m)) - (m * (w * w)));
    double capitalAaDouble = ((((-1) * (b)) * (128 - 255)) + (128 * w) - (255 * m));
    double capitalAbDouble = (((b * b) * (128 - 255)) + (255 * (m * m)) - (128 * (w * w)));
    double capitalAcDouble = (((b * b) * ((255 * m) - (128 * w)))
            - (b * (255 * (m * m)) - (128 * (w * w))));
    double aDouble = capitalAaDouble / capitalADouble;
    double bDouble = capitalAbDouble / capitalADouble;
    double cDouble = capitalAcDouble / capitalADouble;

    return new Double[]{aDouble, bDouble, cDouble};
  }
}
