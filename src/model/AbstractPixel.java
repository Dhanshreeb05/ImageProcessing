package model;

/**
 * This abstract class makes sure that every Pixel class that extends it has methods that
 * return the data about the pixel.
 */
public abstract class AbstractPixel {


  /**
   * Returns the value of the pixel. In general, value is the max of values of
   * red, green, blue values.
   *
   * @return the value of the pixel
   */
  protected abstract int getValue();

  /**
   * Returns a pixel that is brightened/Darkened.
   * If value is positive pixel is brightened by that value or else it is darkened.
   *
   * @param value the value to brighten/darken by
   * @return a brightened/darkened pixel
   */
  protected abstract AbstractPixel brightenOrDarken(int value);

  //Code for Double Dispatch
  protected abstract boolean isEqualToColorPixel(ColorPixel pixel);

  protected boolean isEqualToColorPixel(GreyscalePixel pixel) {
    return false;
  }

  protected boolean isEqualToGreyScalePixel(ColorPixel pixel) {
    return false;
  }

  protected abstract boolean isEqualToGreyScalePixel(GreyscalePixel pixel);


  protected abstract int getRed();

  protected abstract int getGreen();

  protected abstract int getBlue();

  protected abstract double getIntensity();

  protected abstract double getLuma();

  protected abstract AbstractPixel convertToSepia();

  protected abstract int getRGBA();
}
