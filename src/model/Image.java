package model;

import java.awt.image.BufferedImage;

/**
 * This interface is used to represent an Image object.
 */
public interface Image {

  /**
   * Split the image into three images representing each of the three channels (Red, Green, Blue).
   *
   * @return an array of 3 greyscale images
   */
  Image[] splitChannel();

  /**
   * Flip the image Horizontally.
   *
   * @return
   */
  Image flipHorizontal();

  /**
   * Flip the image Vertically.
   *
   * @return
   */
  Image flipVertical();

  /**
   * Brighten the picture by the given value and return it.
   *
   * @param value The value to brighten by
   * @return
   */
  Image brightenDarken(int value);

  /**
   * Blur the image using the default filter.
   *
   * @return
   */
  Image blur();

  /**
   * Sharpen the image using the default filter.
   *
   * @return
   */
  Image sharpen();

  /**
   * Convert the image to the sepia tone.
   *
   * @return
   */
  Image convertToSepia();

  /**
   * Save the model.Image to the specified path in the given type.
   *
   * @param path the path to store the image
   * @throws IllegalArgumentException when Path cannot be accessed
   */
  void saveToFile(String path) throws Exception;

  /**
   * Rotate the image clockwise by 90 degrees.
   *
   * @return
   */
  Image rotateClockwise();

  /**
   * Rotate the image anticlockwise by 90 degrees.
   *
   * @return
   */
  Image rotateAntiClockwise();

  /**
   * This function is used to crop the given image.
   *
   * @param x      the row of the starting pixel
   * @param y      the column of the starting pixel
   * @param length the length of the cropped image
   * @param width  the width of the cropped image
   * @return cropped Image
   * @throws IllegalArgumentException when the starting pixel does not exist or
   *                                  when the length or width are invalid
   */
  Image crop(int x, int y, int length, int width) throws IllegalArgumentException;

  /**
   * This function returns the width/num of columns in the image.
   *
   * @return width of image
   */
  int getWidth();

  /**
   * This function returns the height/num of rows in the image.
   *
   * @return height of image
   */
  int getHeight();

  /**
   * Returns a pixel from a image located at a specific location.
   *
   * @param row the row value of the pixel
   * @param col the column value of the pixel
   * @return the pixel at that position
   */
  AbstractPixel getPixel(int row, int col);

  /**
   * Convert the image to greyscale image of the specified type.
   * Type can be of ("luma", "value", " intensity").
   *
   * @param type the type of greyscale image to generate ("luma", "value", " intensity")
   * @return the greyscale image of the particular type
   */
  Image convertToGreyscale(String type);

  /**
   * Create and return an image by combining red, green and blue channels of the image.
   *
   * @param red   The red channel of the image
   * @param green The green channel of the image
   * @param blue  The blue channel of the image
   * @return The image with all 3 channels
   */
  Image combineChannels(Image red, Image green, Image blue);

  /**
   * create and return the BufferedImage object.
   *
   * @return the BufferedImage image
   */
  BufferedImage getBufferedImage();

}
