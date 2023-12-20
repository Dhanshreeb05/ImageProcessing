package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

/**
 * This is an implementation of the Image interface. This class is used to store a color image.
 * It stores information about values of red, blue, green components of every pixel in the image.
 */
public class ColorImage extends AbstractImage {

  /**
   * This constructor creates an image object from another image object.
   *
   * @param image The image to create the image object from
   */
  public ColorImage(Image image) {
    super(image);
  }

  /**
   * This constructor creates a color image from a given 2D grid of pixels.
   *
   * @param grid The 2D grid of colorPixels.
   */
  public ColorImage(AbstractPixel[][] grid) {
    //For creating model.Image object from grid wise data
    super(grid);
  }

  /**
   * Generates an image from a BufferImage object.
   *
   * @param bufferedImage the BufferedImage object
   */
  public ColorImage(BufferedImage bufferedImage) {
    this.numCols = bufferedImage.getWidth();
    this.numRows = bufferedImage.getHeight();
    this.pixelGrid = new ColorPixel[numRows][numCols];

    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        pixelGrid[i][j] = new ColorPixel(bufferedImage.getRGB(j, i));
      }
    }
  }

  /**
   * Generates an image object from an image file using its file path.
   *
   * @param path the path to the image file
   * @throws IllegalArgumentException when file not found
   */
  public ColorImage(String path) throws IllegalArgumentException, IOException {

    try {
      File imagefile = new File(path);
      BufferedImage bufferedImage = ImageIO.read(imagefile);
    } catch (IOException ie) {
      throw new IllegalArgumentException("File path does not exist. Cannot read file.");
    }

    //Check for supported file types.
    String formatType = path.substring(path.lastIndexOf('.') + 1);
    String[] supportedFileTypes = new String[]{"png", "jpeg", "jpg", "ppm"};
    if (!(Arrays.asList(supportedFileTypes).contains(formatType))) {
      throw new IllegalArgumentException("File type is not supported yet!");
    }

    if (formatType.equals("ppm")) {
      this.readPPM(path);
    } else {
      //For creating model.Image object from model.Image Path
      File imagefile = new File(path);
      BufferedImage bufferedImage = ImageIO.read(imagefile);
      this.numCols = bufferedImage.getWidth();
      this.numRows = bufferedImage.getHeight();
      this.pixelGrid = new ColorPixel[numRows][numCols];

      for (int i = 0; i < numRows; i++) {
        for (int j = 0; j < numCols; j++) {
          pixelGrid[i][j] = new ColorPixel(bufferedImage.getRGB(j, i));
        }
      }
    }
  }

  /**
   * Initializes an empty image.
   */
  public ColorImage() {
    super();
  }

  @Override
  protected int getValueOfPixel(AbstractPixel pixel) {
    return pixel.getRGBA();
  }

  @Override
  protected AbstractPixel createNewPixel(int value) {
    return new ColorPixel(value);
  }

  @Override
  public Image[] splitChannel() {

    Image redChannel = new GreyscaleImage(this, Channel.red);
    Image greenChannel = new GreyscaleImage(this, Channel.green);
    Image blueChannel = new GreyscaleImage(this, Channel.blue);

    return new Image[]{redChannel, greenChannel, blueChannel};
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
  protected AbstractImage createAbstractImage(AbstractPixel[][] pixelGrid) {
    return new ColorImage(pixelGrid);
  }

  @Override
  public Image combineChannels(Image red, Image green, Image blue) {

    int redHeight = red.getHeight();
    int redWidth = red.getWidth();
    int blueHeight = blue.getHeight();
    int blueWidth = blue.getWidth();
    int greenHeight = green.getHeight();
    int greenWidth = green.getWidth();

    if (redHeight != blueHeight || redHeight != greenHeight
            || redWidth != blueWidth || redWidth != greenWidth) {
      throw new IllegalArgumentException("Images are not of the same size!!");
    }
    AbstractPixel[][] pixelGrid_combined = new ColorPixel[redHeight][redWidth];
    for (int i = 0; i < redHeight; i++) {
      for (int j = 0; j < redWidth; j++) {
        int red_value = red.getPixel(i, j).getValue();
        int green_value = green.getPixel(i, j).getValue();
        int blue_value = blue.getPixel(i, j).getValue();
        pixelGrid_combined[i][j] = new ColorPixel(red_value, green_value, blue_value);
      }
    }
    return new ColorImage(pixelGrid_combined);
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
    double[] value = {0, 0, 0};
    for (int i = 0; i < blurSharpenFilter.length; i++) {
      for (int j = 0; j < blurSharpenFilter.length; j++) {
        value[0] += blurSharpenFilter[i][j] * subgrid[i][j].getRed();
        value[1] += blurSharpenFilter[i][j] * subgrid[i][j].getGreen();
        value[2] += blurSharpenFilter[i][j] * subgrid[i][j].getBlue();
      }
    }
    for (int i = 0; i < 3; i++) {
      if (value[i] < 0) {
        value[i] = 0;
      } else if (value[i] > 255) {
        value[i] = 255;
      }
    }
    return new ColorPixel((int) Math.floor(value[0]),
            (int) Math.floor(value[1]), (int) Math.floor(value[2]));
  }

  @Override
  public Image convertToSepia() {
    AbstractImage sepiaImage = createAbstractImage(new AbstractPixel[numRows][numCols]);
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        AbstractPixel pixel_i_j = pixelGrid[i][j].convertToSepia();
        sepiaImage.setPixel(i, j, pixel_i_j);
      }
    }
    return sepiaImage;
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
    return other.isEqualToColorImage(this);
  }

  @Override
  protected boolean isEqualToGreyScaleImage(GreyscaleImage image) {
    return false;
  }

  @Override
  public Image convertToGreyscale(String type) throws IllegalArgumentException {
    if (!(type.equals("value") || type.equals("intensity") || type.equals("luma"))) {
      throw new IllegalArgumentException("Invalid conversion type.");
    }
    GreyscalePixel[][] greyscalePixelGrid = new GreyscalePixel[numRows][numCols];
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        switch (type) {
          case "value":
            greyscalePixelGrid[i][j] = new GreyscalePixel(this.pixelGrid[i][j].getValue());
            break;
          case "intensity":
            greyscalePixelGrid[i][j] = new GreyscalePixel(
                    (int) this.pixelGrid[i][j].getIntensity());
            break;
          case "luma":
            greyscalePixelGrid[i][j] = new GreyscalePixel((int) this.pixelGrid[i][j].getLuma());
            break;
          default:
            break;
        }
      }
    }
    return new GreyscaleImage(greyscalePixelGrid);
  }
}
