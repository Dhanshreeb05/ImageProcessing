package model;

/**
 * This is an implementation of the Image interface. This class is used to store a Greyscale image.
 * It stores information about value of every pixel in the image.
 */
public class GreyscaleImage extends AbstractImage {

  protected Channel imageChannel;

  /**
   * This constructor generates a greyscale image from a given color image object.
   *
   * @param image The image to generate the Greyscale image from
   */
  public GreyscaleImage(Image image) {
    super(image);
    this.imageChannel = null;
  }

  /**
   * This constructor generates a greyscale image from a given color image object.
   * This also takes an input Imagechannel specifying a specific type of channel.
   *
   * @param image        The image to generate the Greyscale image from
   * @param imageChannel the type of channel
   */
  public GreyscaleImage(Image image, Channel imageChannel) {
    this.imageChannel = imageChannel;
    this.numRows = image.getHeight();
    this.numCols = image.getWidth();
    this.pixelGrid = new GreyscalePixel[this.numRows][this.numCols];

    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        if (this.imageChannel == Channel.red) {
          this.pixelGrid[i][j] = new GreyscalePixel(
                  image.getPixel(i, j).getRed());
        } else if (this.imageChannel == Channel.green) {
          this.pixelGrid[i][j] = new GreyscalePixel(
                  image.getPixel(i, j).getGreen());
        } else if (this.imageChannel == Channel.blue) {
          this.pixelGrid[i][j] = new GreyscalePixel(
                  image.getPixel(i, j).getBlue());
        } else {
          this.pixelGrid[i][j] = new GreyscalePixel(image.getPixel(i, j).getValue());
        }
      }
    }
  }

  /**
   * This constructor generates a greyscale image from a 2D grid of greyscale pixels.
   *
   * @param grid the 2d grid of greyscale pixels
   */
  public GreyscaleImage(AbstractPixel[][] grid) {
    //For creating model.Image object from grid wise data
    super(grid);
  }

  /**
   * This constructor generates a greyscale image from a 2D grid of
   * greyscale pixels and the channel.
   *
   * @param pixelGrid    the 2d grid of greyscale pixels
   * @param imageChannel the channel of the greyscale image
   */
  public GreyscaleImage(AbstractPixel[][] pixelGrid, Channel imageChannel) {
    super(pixelGrid);
    this.imageChannel = imageChannel;

  }

  @Override
  protected int getValueOfPixel(AbstractPixel pixel) {
    return pixel.getValue();
  }

  @Override
  protected AbstractPixel createNewPixel(int value) {
    return new GreyscalePixel(value);
  }

  @Override
  public Image[] splitChannel() {
    throw new IllegalStateException("Cannot perform split on Greyscale");
  }

  @Override
  protected AbstractImage createAbstractImage(AbstractPixel[][] pixelGrid) {
    return new GreyscaleImage(pixelGrid);
  }


  @Override
  public Image combineChannels(Image red, Image green, Image blue) {
    return null;
  }


  /**
   * This helper method is used by blur and sharpen methods. It generates a greyscale pixel based
   * on the subgrid of pixels and the filters that are passed to it.
   *
   * @param blurSharpenFilter the 2D blur filter
   * @param subgrid           the subgrid to apply that filter on
   * @return the generated greyscale pixel
   */
  @Override
  protected AbstractPixel blurOrSharpen(double[][] blurSharpenFilter, AbstractPixel[][] subgrid) {
    double value = 0;
    for (int i = 0; i < blurSharpenFilter.length; i++) {
      for (int j = 0; j < blurSharpenFilter.length; j++) {
        value += blurSharpenFilter[i][j] * subgrid[i][j].getValue();
      }
    }

    if (value < 0) {
      value = 0;
    } else if (value > 255) {
      value = 255;
    }
    return new GreyscalePixel((int) Math.floor(value));
  }

  @Override
  public Image convertToSepia() {
    throw new IllegalStateException("Cannot Convert Greyscale to Sepia");
  }

  @Override
  public void saveToFile(String path) throws Exception {
    this.convertToColorImage().saveToFile(path);
  }

  @Override
  public int hashCode() {
    int hashcode = 0;
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        hashcode += pixelGrid[i][j].hashCode() * i * j;
      }
    }
    return hashcode;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AbstractImage)) {
      return false;
    }
    AbstractImage other = (AbstractImage) o;
    return other.isEqualToGreyScaleImage(this);
  }


  @Override
  protected boolean isEqualToColorImage(ColorImage image) {
    return false;
  }

  /**
   * Converts the greyscale image object to ColorImage object. If imageChannel is specified, it is
   * taken into consideration, else image is assumed to be a general greyscale image.
   *
   * @return The colorImage object generated from the greyscale image
   */
  protected ColorImage convertToColorImage() {

    ColorPixel[][] newColorGrid = new ColorPixel[numRows][numCols];
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        if (this.imageChannel == Channel.red) {
          newColorGrid[i][j] = new ColorPixel(
                  this.pixelGrid[i][j].getValue(),
                  0,
                  0
          );
        } else if (this.imageChannel == Channel.green) {
          newColorGrid[i][j] = new ColorPixel(
                  0,
                  this.pixelGrid[i][j].getValue(),
                  0
          );
        } else if (this.imageChannel == Channel.blue) {
          newColorGrid[i][j] = new ColorPixel(
                  0,
                  0,
                  this.pixelGrid[i][j].getValue()
          );
        } else {
          newColorGrid[i][j] = new ColorPixel(
                  this.pixelGrid[i][j].getValue(),
                  this.pixelGrid[i][j].getValue(),
                  this.pixelGrid[i][j].getValue()
          );
        }
      }
    }
    return new ColorImage(newColorGrid);
  }

  @Override
  public Image convertToGreyscale(String type) {
    return this;
  }

  @Override
  public Image crop(int x, int y, int length, int width) throws IllegalArgumentException {
    //add implementation
    if (x < 0 || x > numRows || y < 0 || y > numCols || length < 0
            || width < 0 || y + length > numCols || x + width > numRows) {
      throw new IllegalArgumentException("Invalid Values passed!!");
    }

    AbstractPixel[][] pixelGrid = new AbstractPixel[width][length];
    for (int i = 0; i < width; i++) {
      System.arraycopy(this.pixelGrid[x + i], y, pixelGrid[i], 0, length);
    }
    return new GreyscaleImage(pixelGrid, imageChannel);
  }
}
