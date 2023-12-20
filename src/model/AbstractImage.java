package model;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * This is an Abstract Class which implements the Image Interface. It ensures that every image
 * will have a height and width and a grid of pixels. This class also helps in double dispatch
 * implementation for the equals method.
 */
public abstract class AbstractImage implements Image {

  protected AbstractPixel[][] pixelGrid;
  protected int numRows;
  protected int numCols;


  /**
   * Generates an empty abstractImage object.
   */
  protected AbstractImage() {
    //Empty constructor for calling split

  }

  /**
   * Generates an abstract image object from the given image.
   *
   * @param image the image object
   */
  protected AbstractImage(Image image) {
    this.numRows = image.getHeight();
    this.numCols = image.getWidth();

    this.pixelGrid = new AbstractPixel[this.numRows][this.numCols];

    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        this.pixelGrid[i][j] = createNewPixel(getValueOfPixel(image.getPixel(i, j)));
      }
    }
  }

  /**
   * Generates an abstractImage object from a given grid of pixels.
   *
   * @param grid the grid of pixels
   */
  protected AbstractImage(AbstractPixel[][] grid) {
    //For creating model.Image object from grid wise data

    this.pixelGrid = new AbstractPixel[grid.length][grid[0].length];
    for (int i = 0; i < grid.length; i++) {
      this.pixelGrid[i] = Arrays.copyOf(grid[i], grid[i].length);
    }
    this.numRows = grid.length;
    this.numCols = grid[0].length;

  }

  /**
   * Returns  the value of the pixel.
   *
   * @param pixel the pixel
   * @return the value of the pixel
   */
  protected abstract int getValueOfPixel(AbstractPixel pixel);

  /**
   * Creats new abstract pixel with the given value.
   *
   * @param value value of the pixel to be made
   * @return abstract pixel of given value
   */
  protected abstract AbstractPixel createNewPixel(int value);

  /**
   * Sets a AbstractPixel to a particular location in the PixelGrid.
   *
   * @param i     the row value of the position
   * @param j     the column value of the position
   * @param pixel The pixel that need to be set in the pixelGrid
   */
  void setPixel(int i, int j, AbstractPixel pixel) {
    this.pixelGrid[i][j] = pixel;
  }

  @Override
  public Image flipHorizontal() {
    AbstractImage flipped_horizontal = createAbstractImage(this.pixelGrid);
    for (int i = 0; i < this.pixelGrid.length; i++) {
      for (int j = 0; j < this.pixelGrid[i].length / 2; j++) {
        AbstractPixel temp = flipped_horizontal.pixelGrid[i][j];
        flipped_horizontal.pixelGrid[i][j] = flipped_horizontal
                .pixelGrid[i][flipped_horizontal.pixelGrid[i].length - 1 - j];
        flipped_horizontal.pixelGrid[i][flipped_horizontal.pixelGrid[i].length - 1 - j] = temp;
      }
    }
    return flipped_horizontal;
  }

  @Override
  public Image flipVertical() {
    AbstractImage flipped_vertical = createAbstractImage(this.pixelGrid);
    Collections.reverse(Arrays.asList(flipped_vertical.pixelGrid));
    return flipped_vertical;
  }

  @Override
  public Image brightenDarken(int value) {
    AbstractImage brighten_image = createAbstractImage(this.pixelGrid);
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        brighten_image.pixelGrid[i][j] = brighten_image.pixelGrid[i][j].brightenOrDarken(value);
      }
    }
    return brighten_image;
  }

  @Override
  public int getWidth() {
    return this.numCols;
  }

  @Override
  public int getHeight() {
    return this.numRows;
  }


  @Override
  public AbstractPixel getPixel(int i, int j) {
    return this.pixelGrid[i][j];
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        sb.append(pixelGrid[i][j] + "\t");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Helper function to create a image form the grid of pixels.
   *
   * @param pixelGrid the grid of pixels
   * @return the AbstractImage
   */
  protected abstract AbstractImage createAbstractImage(AbstractPixel[][] pixelGrid);

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
    return createAbstractImage(pixelGrid);
  }

  @Override
  public Image blur() {
    AbstractImage blurredImage = createAbstractImage(new AbstractPixel[numRows][numCols]);
    double[][] blur_filter = {
            {0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}
    };
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        AbstractPixel[][] subgrid = {{
                i - 1 >= 0 && j - 1 >= 0 ? pixelGrid[i - 1][j - 1] : createNewPixel(0),
                i - 1 >= 0 ? pixelGrid[i - 1][j] : createNewPixel(0),
                i - 1 >= 0 && j + 1 < numCols
                        ? pixelGrid[i - 1][j + 1] : createNewPixel(0)}, {
                j - 1 >= 0 ? pixelGrid[i][j - 1] : createNewPixel(0),
                pixelGrid[i][j],
                j + 1 < numCols ? pixelGrid[i][j + 1] : createNewPixel(0)}, {
                i + 1 < numRows && j - 1 >= 0
                        ? pixelGrid[i + 1][j - 1] : createNewPixel(0),
                i + 1 < numRows ? pixelGrid[i + 1][j] : createNewPixel(0),
                i + 1 < numRows && j + 1 < numCols
                        ? pixelGrid[i + 1][j + 1] : createNewPixel(0)}
        };
        AbstractPixel pixel_i_j = blurOrSharpen(blur_filter, subgrid);
        blurredImage.setPixel(i, j, pixel_i_j);
      }
    }
    return blurredImage;
  }

  @Override
  public Image sharpen() {
    AbstractImage sharpenedImage = createAbstractImage(new AbstractPixel[numRows][numCols]);
    double[][] sharpen_filter = {
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125,},
            {-0.125, 0.25, 1, 0.25, -0.125,},
            {-0.125, 0.25, 0.25, 0.25, -0.125,},
            {-0.125, -0.125, -0.125, -0.125, -0.125}
    };
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        AbstractPixel[][] subgrid = createSubgridForSharpen(i, j);
        AbstractPixel pixel_i_j = blurOrSharpen(sharpen_filter, subgrid);
        sharpenedImage.setPixel(i, j, pixel_i_j);
      }
    }
    return sharpenedImage;
  }

  /**
   * Creates a subgrid from a grid of pixels.
   *
   * @param i the num of rows
   * @param j the num of cols
   * @return the subgrid of pixels
   */
  private AbstractPixel[][] createSubgridForSharpen(int i, int j) {
    return new AbstractPixel[][]{{
            i - 2 >= 0 && j - 2 >= 0 ? pixelGrid[i - 2][j - 2] : createNewPixel(0),
            i - 2 >= 0 && j - 1 >= 0 ? pixelGrid[i - 2][j - 1] : createNewPixel(0),
            i - 2 >= 0 ? pixelGrid[i - 2][j] : createNewPixel(0),
            i - 2 >= 0 && j + 1 < numCols ? pixelGrid[i - 2][j + 1] :
                    createNewPixel(0),
            i - 2 >= 0 && j + 2 < numCols ? pixelGrid[i - 2][j + 2] :
                    createNewPixel(0)}, {
            i - 1 >= 0 && j - 2 >= 0 ? pixelGrid[i - 1][j - 2] : createNewPixel(0),
            i - 1 >= 0 && j - 1 >= 0 ? pixelGrid[i - 1][j - 1] :
                    createNewPixel(0),
            i - 1 >= 0 ? pixelGrid[i - 1][j] : createNewPixel(0),
            i - 1 >= 0 && j + 1 < numCols ? pixelGrid[i - 1][j + 1] :
                    createNewPixel(0),
            i - 1 >= 0 && j + 2 < numCols ? pixelGrid[i - 1][j + 2] :
                    createNewPixel(0)}, {
            j - 2 >= 0 ? pixelGrid[i][j - 2] : createNewPixel(0),
            j - 1 >= 0 ? pixelGrid[i][j - 1] : createNewPixel(0),
            pixelGrid[i][j],
            j + 1 < numCols ? pixelGrid[i][j + 1] : createNewPixel(0),
            j + 2 < numCols ? pixelGrid[i][j + 2] : createNewPixel(0)}, {
            i + 1 < numRows && j - 2 >= 0 ? pixelGrid[i + 1][j - 2] :
                    createNewPixel(0),
            i + 1 < numRows && j - 1 >= 0 ? pixelGrid[i + 1][j - 1] :
                    createNewPixel(0),
            i + 1 < numRows ? pixelGrid[i + 1][j] : createNewPixel(0),
            i + 1 < numRows && j + 1 < numCols ? pixelGrid[i + 1][j + 1] :
                    createNewPixel(0),
            i + 2 < numRows && j + 2 < numCols ? pixelGrid[i + 1][j + 2] :
                    createNewPixel(0)}, {
            i + 2 < numRows && j - 2 >= 0 ? pixelGrid[i + 2][j - 2] :
                    createNewPixel(0),
            i + 2 < numRows && j - 1 >= 0 ? pixelGrid[i + 2][j - 1] :
                    createNewPixel(0),
            i + 2 < numRows ? pixelGrid[i + 2][j] : createNewPixel(0),
            i + 2 < numRows && j + 1 < numCols ? pixelGrid[i + 2][j + 1] :
                    createNewPixel(0),
            i + 2 < numRows && j + 2 < numCols ? pixelGrid[i + 2][j + 2] :
                    createNewPixel(0)},
    };
  }

  @Override
  public Image rotateClockwise() {

    int newCols = this.pixelGrid.length;
    int newRows = this.pixelGrid[0].length;

    AbstractPixel[][] newGrid = new AbstractPixel[newRows][newCols];

    for (int i = 0; i < newCols; i++) {
      for (int j = 0; j < newRows; j++) {
        newGrid[j][newCols - 1 - i] = createNewPixel(this.pixelGrid[i][j].getRGBA());
      }
    }
    return createAbstractImage(newGrid);
  }

  @Override
  public Image rotateAntiClockwise() {

    int newCols = this.pixelGrid.length;
    int newRows = this.pixelGrid[0].length;

    AbstractPixel[][] newGrid = new AbstractPixel[newRows][newCols];

    for (int i = 0; i < newCols; i++) {
      for (int j = 0; j < newRows; j++) {
        newGrid[newRows - 1 - j][i] = createNewPixel(this.pixelGrid[i][j].getRGBA());
      }
    }
    return createAbstractImage(newGrid);
  }

  /**
   * Helper function to read a ppm file into the current instance of the ColorImage object.
   *
   * @param filename The path to the ppm file
   */
  protected void readPPM(String filename) {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      throw new IllegalArgumentException("File not found......");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    AbstractPixel[][] pixelGrid = new ColorPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixelGrid[i][j] = new ColorPixel(r, g, b);
      }
    }
    this.pixelGrid = pixelGrid;
    this.numRows = height;
    this.numCols = width;
  }


  @Override
  public void saveToFile(String path) throws Exception {
    //Check for supported file types.
    String formatType = path.substring(path.lastIndexOf('.') + 1);
    String[] supportedFileTypes = new String[]{"png", "jpeg", "jpg", "ppm"};
    if (!(Arrays.asList(supportedFileTypes).contains(formatType))) {
      throw new IllegalArgumentException("File type is not supported yet!");
    }

    BufferedImage bufferedImage = new BufferedImage(numCols, numRows, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        bufferedImage.setRGB(j, i, pixelGrid[i][j].getRGBA());
      }
    }
    Files.deleteIfExists(new File(path).toPath());
    Files.createDirectories(Paths.get(path).getParent());

    if (formatType.equals("ppm")) {
      boolean success = this.writePPM(path);
      if (!success) {
        throw new Exception("File not saved.\n");
      }
    } else {
      ImageIO.write(bufferedImage, String.valueOf(ImageType.PNG), new File(path));
    }
  }

  @Override
  public BufferedImage getBufferedImage() {
    BufferedImage bufferedImage = new BufferedImage(numCols, numRows, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        bufferedImage.setRGB(j, i, pixelGrid[i][j].getRGBA());
      }
    }
    return bufferedImage;
  }


  /**
   * Saves a PPM file to the specified location.
   *
   * @param filename The path to save the PPM image
   * @return true if PPM file is successfully saved, else false
   */
  boolean writePPM(String filename) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
      bw.write("P3\n");
      bw.write(numCols + " " + numRows + "\n");
      bw.write("255\n");

      for (int i = 0; i < numRows; i++) {
        for (int j = 0; j < numCols; j++) {
          bw.write(pixelGrid[i][j].getRed() + "\n" + pixelGrid[i][j].getGreen()
                  + "\n" + pixelGrid[i][j].getBlue() + "\n");
        }
      }

      return true;
    } catch (IOException e) {
      return false;
    }
  }


  /**
   * Uses a kernel on a subgrid to get the new pixel.
   *
   * @param blurFilter the blur/sharpen filter
   * @param subgrid    the subgrid on which to apply the filter
   * @return the final pixel
   */
  protected abstract AbstractPixel blurOrSharpen(double[][] blurFilter, AbstractPixel[][] subgrid);


  // Below code is for DOUBLE DISPATCH implementation.

  protected boolean isEqualToColorImage(ColorImage image) {
    return checkEquality(image);
  }

  ;

  protected boolean isEqualToColorImage(GreyscaleImage image) {
    return false;
  }

  protected boolean isEqualToGreyScaleImage(ColorImage image) {
    return false;
  }

  protected boolean isEqualToGreyScaleImage(GreyscaleImage image) {
    return checkEquality(image);
  }

  ;

  /**
   * Check is given image is equal to self.
   *
   * @param image the image to check against
   * @return true if equal, else false
   */
  private boolean checkEquality(AbstractImage image) {
    boolean isEqual = true;
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        isEqual = pixelGrid[i][j].equals(image.getPixel(i, j));
        if (!isEqual) {
          break;
        }
      }
    }
    return isEqual;
  }

}
