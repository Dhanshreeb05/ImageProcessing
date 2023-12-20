package model;

/**
 * This class is used to represent a pixel in the pixelGrid of the ColorImage class.
 * It stored data about the individual red, green and blue
 */
class ColorPixel extends AbstractPixel {
  private int red;
  private int green;
  private int blue;
  private int transparency;

  /**
   * Constructs a colorPixel object when RGB values are passed.
   *
   * @param red   the value of red component
   * @param green red the value of green component
   * @param blue  red the value of blue component
   * @throws IllegalArgumentException when values are out of range
   */
  protected ColorPixel(int red, int green, int blue) throws IllegalArgumentException {
    if (red < 0 || green < 0 || blue < 0 || red > 255 || green > 255 || blue > 255) {
      throw new IllegalArgumentException("Values negative or out of range!!!");
    }

    this.red = red;
    this.green = green;
    this.blue = blue;
    this.transparency = 255;
  }

  /**
   * Constructs a colorPixel object when combined RGB values are passed.
   *
   * @param rgb combined RGB values
   */
  protected ColorPixel(int rgb) {
    this.transparency = (rgb >> 24) & 0xff;
    this.red = (rgb >> 16) & 0xff;
    this.green = (rgb >> 8) & 0xff;
    this.blue = (rgb) & 0xff;
  }

  /**
   * Constructs a colorPixel object from another pixel.
   *
   * @param pixel another pixel
   */
  public ColorPixel(AbstractPixel pixel) {
    this.red = pixel.getRed();
    this.green = pixel.getGreen();
    this.blue = pixel.getBlue();
    this.transparency = 255;
  }


  protected int getRed() {
    return red;
  }

  protected int getGreen() {
    return green;
  }

  protected int getBlue() {
    return blue;
  }

  protected int getValue() {
    return Math.max(Math.max(this.red, this.green), this.blue);
  }

  protected double getIntensity() {
    return ((double) (this.red + this.green + this.blue) / 3);
  }

  protected double getLuma() {
    return (0.2126 * this.red + 0.7152 * this.green + 0.0722 * this.blue);
  }

  @Override
  public boolean equals(Object o) {
    //return when every RGB matches with values
    if (this == o) {
      return true;
    }
    if (!(o instanceof AbstractPixel)) {
      return false;
    }
    AbstractPixel other = (AbstractPixel) o;
    return other.isEqualToColorPixel(this);
  }

  @Override
  public int hashCode() {
    return this.red + this.green + this.blue + this.transparency;
  }

  @Override
  public String toString() {
    return "(" + this.red + ", " + this.green + ", " + this.blue + ", " + this.transparency + ")";
  }

  /**
   * Get the RGB values from the pixel.
   *
   * @return the integer RGBA value
   */
  protected int getRGBA() {
    return ((this.transparency & 0xFF) << 24)
            | ((this.red & 0xFF) << 16) | ((this.green & 0xFF) << 8)
            | (this.blue & 0xFF);
  }

  @Override
  protected ColorPixel brightenOrDarken(int value) {
    int red = this.red + value;
    if (red < 0) {
      red = 0;
    } else if (red > 255) {
      red = 255;
    }

    int blue = this.blue + value;
    if (blue < 0) {
      blue = 0;
    } else if (blue > 255) {
      blue = 255;
    }

    int green = this.green + value;
    if (green < 0) {
      green = 0;
    } else if (green > 255) {
      green = 255;
    }
    return new ColorPixel(red, green, blue);
  }

  @Override
  protected boolean isEqualToColorPixel(ColorPixel pixel) {
    return (this.red == pixel.red && this.green == pixel.green
            && this.blue == pixel.blue && this.transparency == pixel.transparency);
  }

  @Override
  protected boolean isEqualToGreyScalePixel(GreyscalePixel pixel) {
    return false;
  }

  protected ColorPixel convertToSepia() {
    double[][] sepiaFilter = {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};
    double[] current_values = {this.red, this.green, this.blue};
    double[] final_values = new double[3];
    for (int i = 0; i < 3; i++) {
      double value = 0;
      for (int j = 0; j < 3; j++) {
        value += sepiaFilter[i][j] * current_values[j];
      }
      final_values[i] = value;
    }

    if (final_values[0] < 0) {
      final_values[0] = 0;
    } else if (final_values[0] > 255) {
      final_values[0] = 255;
    }

    if (final_values[1] < 0) {
      final_values[1] = 0;
    } else if (final_values[1] > 255) {
      final_values[1] = 255;
    }

    if (final_values[2] < 0) {
      final_values[2] = 0;
    } else if (final_values[2] > 255) {
      final_values[2] = 255;
    }

    return new ColorPixel((int) final_values[0], (int) final_values[1], (int) final_values[2]);
  }
}
