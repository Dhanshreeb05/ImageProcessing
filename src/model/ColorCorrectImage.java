package model;

/**
 * This helper class contains the logic for Color corrected an image.
 */
public class ColorCorrectImage {
  /**
   * Returns the color corrected image.
   *
   * @param image the image to color correct
   * @return the color corrected image
   */
  public static ImageNew colorCorrect(ImageNew image) {
    Histogram hist = new Histogram(image);

    // Get the value for peak in Red, Green and Blue
    int redPeakValue = hist.getPeak(Channel.red);
    int greenPeakValue = hist.getPeak(Channel.green);
    int bluePeakValue = hist.getPeak(Channel.blue);

    int avgPeakValue = Math.round(((float) (redPeakValue + greenPeakValue + bluePeakValue)) / 3);

    int redOffset = calculateOffset(avgPeakValue, redPeakValue);
    int greenOffset = calculateOffset(avgPeakValue, greenPeakValue);
    int blueOffset = calculateOffset(avgPeakValue, bluePeakValue);

    return changeOffsetValues(image, redOffset, greenOffset, blueOffset);
  }

  /**
   * Calculates the offset by which to move the channel's value for the peaks to coincide.
   *
   * @param avgPeakValue the average peak where the peaks should coincide
   * @param peakValue    the current value of the peak for the channel
   * @return the offset by which to move the channel's value
   */
  public static int calculateOffset(int avgPeakValue, int peakValue) {
    return avgPeakValue - peakValue;
  }

  /**
   * Returns the image with RGB values changed by the given offsets.
   *
   * @param image       the image whose values have to be changed
   * @param redOffset   the value to change the red offset by
   * @param greenOffset the value to change the green offset by
   * @param blueOffset  the value to change the blue offset by
   * @return the image with the values changed by the offsets
   */
  public static ImageNew changeOffsetValues(ImageNew image, int redOffset,
                                            int greenOffset, int blueOffset) {
    int numRows = image.getHeight();
    int numCols = image.getWidth();

    AbstractPixel[][] pixelGrid = new AbstractPixel[numRows][numCols];

    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        AbstractPixel newPixel = new ColorPixel(image.getPixel(i, j));
        int newRed = adjustValueBetween0and255(newPixel.getRed() + redOffset);
        int newGreen = adjustValueBetween0and255(newPixel.getGreen() + greenOffset);
        int newBlue = adjustValueBetween0and255(newPixel.getBlue() + blueOffset);

        pixelGrid[i][j] = new ColorPixel(newRed, newGreen, newBlue);
      }
    }

    return new ColorImageNew(pixelGrid);
  }

  /**
   * Return the adjusted value that is in the range of 0 and 255.
   *
   * @param value the value to adjust
   * @return the adjusted value
   */
  private static int adjustValueBetween0and255(int value) {
    if (value < 0) {
      return 0;
    } else {
      return Math.min(value, 255);
    }
  }
}
