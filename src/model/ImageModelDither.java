package model;

import java.io.IOException;

public interface ImageModelDither {
  /*void display();*/

  /**
   * Method to get the name of the image.
   *
   * @return Returns the name of the image.
   */
  String getImageAliasName();

  /**
   * Public method to compress the image.
   *
   * @param imageAlias       image name of the new-generated image.
   * @param compressionRatio compression percentage to compress the image upto that extent.
   * @return A new Image Model representation of the compressed image.
   */
  ImageModelDither compress(String imageAlias, Double compressionRatio);

  /**
   * Public method to adjust the color levels of an image.
   *
   * @param imageAlias image name of the new-generated image.
   * @param b          Black value of the image.
   * @param m          Middle value of the image.
   * @param w          White image of the image.
   * @return A new Image Model representation of the compressed image.
   */
  ImageModelDither levelAdjust(String imageAlias, int b, int m, int w, double per);

  /**
   * Implements color Correction on given Image.
   *
   * @param imageAlias Alias name for new image
   * @return ImageModelDither of Color corrected image
   */
  ImageModelDither colorCorrect(String imageAlias, double per);

  /**
   * Method to generate Histogram of Given Image.
   *
   * @param imageAlias Alias name of Histogram
   * @return ImageModelDither with model representation of Histogram
   * @throws IOException when error performing IO operations on image file.
   */
  ImageModelDither generateHistogram(String imageAlias) throws IOException;

  /**
   * Generates the red component image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModelDither generateRed(String imageAlias);

  /**
   * Generates the green component image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModelDither generateGreen(String imageAlias);

  /**
   * Generates the blue component image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModelDither generateBlue(String imageAlias);

  /**
   * Generates a new blurred Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModelDither blur(String imageAlias, double per);

  /**
   * Generates a new sharper Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModelDither sharpen(String imageAlias, double per);

  /**
   * Generates a new brighter or darker Image of this image based on the value.
   *
   * @param value      bright by value.
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModelDither bright(int value, String imageAlias);

  /**
   * Generates a new greyscaled Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModelDither greyscale(String imageAlias, double per);

  /**
   * Generates a new sepia toned Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModelDither sepia(String imageAlias, double per);

  /**
   * Generates a new intensified Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModelDither intensity(String imageAlias);

  /**
   * Generates a new max Valued pixel Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModelDither maxValue(String imageAlias);

  /**
   * Generates a new vertically flipped Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModelDither verticalFlipImage(String imageAlias);

  /**
   * Generates a new horizontally flipped Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModelDither horizontalFlipImage(String imageAlias);

  /**
   * Method to Combine red component of this image and green and blue components
   * of other two images to create a new image.
   *
   * @param green      Green Channel Image.
   * @param blue       Blue Channel Image.
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModelDither merge3components(ImageModelDither green, ImageModelDither blue, String imageAlias);


  /**
   * Method to get the image width.
   *
   * @return image width.
   */
  int getImageWidth();


  /**
   * Method to get the image height.
   *
   * @return image height.
   */
  int getImageHeight();


  /**
   * Method to get an individual pixel value.
   *
   * @param x x value of the matrix.
   * @param y y value of the matrix.
   * @param i ith channel of the image.
   * @return the exact pixel value of a channel in an image.
   */
  int getPixelValue(int x, int y, int i);


}
