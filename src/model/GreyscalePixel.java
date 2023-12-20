package model;

/**
 * This calss represents a greyscale pixel. It extends the abstractPixel class.
 */
class GreyscalePixel extends AbstractPixel {

  private int value;
  private int transparency;

  /**
   * Constructor to generate greyscale pixel.
   *
   * @param value the value of the pixel
   * @throws IllegalArgumentException is invalid value is passed
   */
  protected GreyscalePixel(int value) throws IllegalArgumentException {
    if (value < 0 || value > 255) {
      throw new IllegalArgumentException("Values negative or out of range!!!");
    }

    this.value = value;
    this.transparency = 255;
  }


  protected int getValue() {
    return this.value;
  }

  protected double getIntensity() {
    return this.value;
  }

  protected double getLuma() {
    return this.value;
  }

  @Override
  protected AbstractPixel convertToSepia() {
    return null;
  }

  @Override
  protected int getRGBA() {
    return this.value;
  }


  @Override
  public String toString() {
    return "(" + this.value + "," + this.transparency + ")";
  }

  @Override
  protected GreyscalePixel brightenOrDarken(int value) {
    int newValue = this.value + value;
    if (newValue < 0) {
      newValue = 0;
    } else if (newValue > 255) {
      newValue = 255;
    }
    return new GreyscalePixel(newValue);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AbstractPixel)) {
      return false;
    }
    AbstractPixel other = (AbstractPixel) o;
    return other.isEqualToGreyScalePixel(this);
  }

  @Override
  public int hashCode() {
    return this.value + this.transparency;
  }

  @Override
  protected boolean isEqualToColorPixel(ColorPixel pixel) {
    return false;
  }

  @Override
  protected boolean isEqualToGreyScalePixel(GreyscalePixel pixel) {
    return this.value == pixel.value || Math.abs(this.value - pixel.value) == 1;
  }

  @Override
  protected int getRed() {
    return 0;
  }

  @Override
  protected int getGreen() {
    return 0;
  }

  @Override
  protected int getBlue() {
    return 0;
  }
}
