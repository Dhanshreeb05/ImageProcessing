package model;

/**
 * This interface represents the new image with more functionalities. It extends the
 * Image interface.
 */
public interface ImageNew extends Image {

  /**
   * Create a compression version of an image.
   * This must be supported by the script command "compress percentage image-name dest-image-name".
   * Percentages between 0 and 100 are considered valid.
   *
   * @param percentage the percent to compress by
   * @return the compressed version of the image
   */
  ImageNew compress(int percentage) throws IllegalArgumentException;

  /**
   * Produce an image that represents the histogram of a given image.
   * The size of this image should be 256x256. It should contain the histograms for the
   * red, green and blue channels as line graphs. The grid pattern shown in the examples
   * is optional, but helpful ( Hint : the BufferedImage class may be helpful, specifically
   * because it provides an ability to draw on it). This should be supported by the script
   * command "histogram image-name dest-image-name".
   *
   * @return an image of dimensions 256x256 representing the histogram
   */
  ImageNew histogram();

  /**
   * Color-correct an image by aligning the meaningful peaks of
   * its histogram. This must be supported by the script command
   * "color-correct image-name dest-image-name".
   *
   * @return the color corrected image
   */
  ImageNew colorCorrect();


  /**
   * Adjust levels of an image. This must be supported by the script
   * command "levels-adjust b m w image-name dest-image-name" where b, m and w are the three
   * relevant black, mid and white values respectively. These values should be ascending in
   * that order, and should be within 0 and 255 for this command to work correctly.
   *
   * @return an image with color level adjustments
   */
  ImageNew levelsAdjust(int b, int m, int w) throws IllegalArgumentException;


  /**
   * Specify a vertical line to generate a split view of operations.
   * The operations that must support this are blur, sharpen, sepia, greyscale, color correction
   * and levels adjustment. The script commands for these operations must accommodate an
   * optional parameter for the placement of the splitting line. For example, blur can be
   * done by "blur image-name dest-image-name" or "blur image-name dest-image split p" in
   * that order where 'p' is a percentage of the width (e.g. 50 means place the line halfway
   * through the width of the image). The output image should show only the relevant part
   * suitably transformed, with the original image in the remaining part.
   *
   * @return a split image
   */
  ImageNew splitView(ImageNew leftImage, ImageNew rightImage, int percentage) throws Exception;

  @Override
  ImageNew[] splitChannel();

  @Override
  ImageNew flipHorizontal();

  @Override
  ImageNew flipVertical();

  @Override
  ImageNew brightenDarken(int value);

  @Override
  ImageNew blur();

  @Override
  ImageNew sharpen();

  @Override
  ImageNew convertToSepia();

  @Override
  ImageNew rotateClockwise();

  @Override
  ImageNew rotateAntiClockwise();

  @Override
  ImageNew crop(int x, int y, int width, int height) throws IllegalArgumentException;

  @Override
  ImageNew convertToGreyscale(String type);

  @Override
  ImageNew combineChannels(Image red, Image green, Image blue);
}
