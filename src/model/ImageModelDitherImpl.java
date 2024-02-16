package model;

import java.io.IOException;

public class ImageModelDitherImpl implements ImageModelDither{
  private final ImageModelNew delegate;
  private int[][][] imageMatrix;
  private String imageAliasName;
  private int imageWidth;
  private int imageHeight;

  /**
   * Constructor to set the private fields.
   *
   * @param matrix         Image matrix to store into the private image representation.
   * @param imageAliasName Name to the image.
   */
  public ImageModelDitherImpl(int[][][] matrix, String imageAliasName) {
    this.imageWidth = matrix.length;
    this.imageHeight = matrix[0].length;
    this.imageMatrix = new int[this.imageWidth][this.imageHeight][3];
    this.delegate = new ImageModelNewImpl(matrix, imageAliasName);

    for (int x = 0; x < this.imageWidth; x++) {
      for (int y = 0; y < this.imageHeight; y++) {
        this.imageMatrix[x][y][0] = matrix[x][y][0];
        this.imageMatrix[x][y][1] = matrix[x][y][1];
        this.imageMatrix[x][y][2] = matrix[x][y][2];
      }
    }
    this.imageAliasName = imageAliasName;
  }


  /**
   * Method to get the name of the image.
   *
   * @return Returns the name of the image.
   */
  @Override
  public String getImageAliasName() {
    return this.imageAliasName;
  }

  /**
   * Public method to compress the image.
   *
   * @param imageAlias       image name of the new-generated image.
   * @param compressionRatio compression percentage to compress the image upto that extent.
   * @return A new Image Model representation of the compressed image.
   */
  @Override
  public ImageModelDither compress(String imageAlias, Double compressionRatio) {
    return null;
  }

  /**
   * Public method to adjust the color levels of an image.
   *
   * @param imageAlias image name of the new-generated image.
   * @param b          Black value of the image.
   * @param m          Middle value of the image.
   * @param w          White image of the image.
   * @param per
   * @return A new Image Model representation of the compressed image.
   */
  @Override
  public ImageModelDither levelAdjust(String imageAlias, int b, int m, int w, double per) {
    return null;
  }

  /**
   * Implements color Correction on given Image.
   *
   * @param imageAlias Alias name for new image
   * @param per
   * @return ImageModelDither of Color corrected image
   */
  @Override
  public ImageModelDither colorCorrect(String imageAlias, double per) {
    return null;
  }

  /**
   * Method to generate Histogram of Given Image.
   *
   * @param imageAlias Alias name of Histogram
   * @return ImageModelDither with model representation of Histogram
   * @throws IOException when error performing IO operations on image file.
   */
  @Override
  public ImageModelDither generateHistogram(String imageAlias) throws IOException {
    return null;
  }

  /**
   * Generates the red component image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelDither generateRed(String imageAlias) {
    return null;
  }

  /**
   * Generates the green component image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelDither generateGreen(String imageAlias) {
    return null;
  }

  /**
   * Generates the blue component image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelDither generateBlue(String imageAlias) {
    return null;
  }

  /**
   * Generates a new blurred Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @param per
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelDither blur(String imageAlias, double per) {
    return null;
  }

  /**
   * Generates a new sharper Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @param per
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelDither sharpen(String imageAlias, double per) {
    return null;
  }

  /**
   * Generates a new brighter or darker Image of this image based on the value.
   *
   * @param value      bright by value.
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelDither bright(int value, String imageAlias) {
    return null;
  }

  /**
   * Generates a new greyscaled Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @param per
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelDither greyscale(String imageAlias, double per) {
    return null;
  }

  /**
   * Generates a new sepia toned Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @param per
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelDither sepia(String imageAlias, double per) {
    return null;
  }

  /**
   * Generates a new intensified Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelDither intensity(String imageAlias) {
    return null;
  }

  /**
   * Generates a new max Valued pixel Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelDither maxValue(String imageAlias) {
    return null;
  }

  /**
   * Generates a new vertically flipped Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelDither verticalFlipImage(String imageAlias) {
    return null;
  }

  /**
   * Generates a new horizontally flipped Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelDither horizontalFlipImage(String imageAlias) {
    return null;
  }

  /**
   * Method to Combine red component of this image and green and blue components
   * of other two images to create a new image.
   *
   * @param green      Green Channel Image.
   * @param blue       Blue Channel Image.
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelDither merge3components(ImageModelDither green, ImageModelDither blue, String imageAlias) {
    return null;
  }

  /**
   * Method to get the image width.
   *
   * @return image width.
   */
  @Override
  public int getImageWidth() {
    return 0;
  }

  /**
   * Method to get the image height.
   *
   * @return image height.
   */
  @Override
  public int getImageHeight() {
    return 0;
  }

  /**
   * Method to get an individual pixel value.
   *
   * @param x x value of the matrix.
   * @param y y value of the matrix.
   * @param i ith channel of the image.
   * @return the exact pixel value of a channel in an image.
   */
  @Override
  public int getPixelValue(int x, int y, int i) {
    return 0;
  }
}
