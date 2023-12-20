package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a histogram object for an image.
 */
public class Histogram {
  ImageNew imageSource;
  ArrayList<Integer> redValues;
  ArrayList<Integer> greenValues;
  ArrayList<Integer> blueValues;
  Map<Integer, Integer> redCounts;
  Map<Integer, Integer> greenCounts;
  Map<Integer, Integer> blueCounts;

  /**
   * Constructor used to create a Histogram object.
   * It takes in an Image object.
   *
   * @param image The image whose data is used to create the histogram.
   */
  public Histogram(ImageNew image) {
    int numRows = image.getHeight();
    int numCols = image.getWidth();

    this.imageSource = image;

    this.redValues = new ArrayList<>();
    this.blueValues = new ArrayList<>();
    this.greenValues = new ArrayList<>();

    this.redCounts = new HashMap<>();
    this.greenCounts = new HashMap<>();
    this.blueCounts = new HashMap<>();

    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        AbstractPixel pixel = image.getPixel(i, j);
        redValues.add(pixel.getRed());
        greenValues.add(pixel.getGreen());
        blueValues.add(pixel.getBlue());
      }
    }

    for (int i = 0; i < 256; i++) {
      redCounts.put(i, this.getCount(i, Channel.red));
      greenCounts.put(i, this.getCount(i, Channel.green));
      blueCounts.put(i, this.getCount(i, Channel.blue));
    }
  }

  /**
   * Get the number of pixels in the image that have the given value for the specific channel.
   *
   * @param value   the value for which the count is to be found
   * @param channel the channel to which the value count belongs
   * @return the count - number of pixels that have the value for the channel
   * @throws IllegalArgumentException when value is not between 0 and 255
   */
  public int getCount(int value, Channel channel) throws IllegalArgumentException {
    // Check if value is between 0 and 255
    if ((value < 0) || (value > 255)) {
      throw new IllegalArgumentException("Value should be between 0 and 255!");
    }

    if (channel == Channel.red) {
      return Collections.frequency(this.redValues, value);
    } else if (channel == Channel.green) {
      return Collections.frequency(this.greenValues, value);
    } else if (channel == Channel.blue) {
      return Collections.frequency(this.blueValues, value);
    } else {
      return 0;
    }
  }

  /**
   * Get the value that is the peak of the channel in the histogram.
   *
   * @param channel the channel whose peak is to be found
   * @return the value that is at the peak
   */
  public int getPeak(Channel channel) {
    int peakValue = 0;
    int maxCount = 0;
    for (int i = 11; i < 245; i++) {
      if (this.getCount(i, channel) > maxCount) {
        peakValue = i;
        maxCount = this.getCount(i, channel);
      }
    }
    return peakValue;
  }

  private int getPeakForHistogram(Channel channel) {
    int peakValue = 0;
    int maxCount = 0;
    for (int i = 0; i <= 255; i++) {
      if (this.getCount(i, channel) > maxCount) {
        peakValue = i;
        maxCount = this.getCount(i, channel);
      }
    }
    return maxCount;
  }

  /**
   * Returns an Image object of the histogram.
   *
   * @return The image object of the histogram
   */
  public ImageNew toImage() {
    // Create a 256x256 image and make every pixel white
    AbstractPixel[][] pixelGrid = new AbstractPixel[256][256];
    for (int i = 0; i < 256; i++) {
      for (int j = 0; j < 256; j++) {
        pixelGrid[i][j] = new ColorPixel(255, 255, 255);
      }
    }

    //Draw the grid
    drawGrid20(pixelGrid);

    int startHeightRed = 0;
    int startHeightGreen = 0;
    int startHeightBlue = 0;

    int endHeightRed = 0;
    int endHeightGreen = 0;
    int endHeightBlue = 0;

    int redPeakCount = getPeakForHistogram(Channel.red);
    int greenPeakCount = getPeakForHistogram(Channel.green);
    int bluePeakCount = getPeakForHistogram(Channel.blue);
    int maxPeak = Math.max(Math.max(redPeakCount, greenPeakCount), bluePeakCount);

    for (int i = 0; i < 256; i++) {
      //System.out.println("i -> " + i);
      endHeightRed = getHeightValueBetween0and256(maxPeak, redCounts.get(i));
      endHeightGreen = getHeightValueBetween0and256(maxPeak, greenCounts.get(i));
      endHeightBlue = getHeightValueBetween0and256(maxPeak, blueCounts.get(i));


      //System.out.println(endHeightRed + " " + endHeightGreen + " " + endHeightBlue);
      // Draw the red line
      drawVerticalLine(
              pixelGrid,
              i,
              startHeightRed,
              endHeightRed,
              Channel.red
      );

      // Draw the green line
      drawVerticalLine(
              pixelGrid,
              i,
              startHeightGreen,
              endHeightGreen,
              Channel.green
      );

      // Draw the blue line
      drawVerticalLine(
              pixelGrid,
              i,
              startHeightBlue,
              endHeightBlue,
              Channel.blue
      );

      startHeightRed = endHeightRed;
      startHeightGreen = endHeightGreen;
      startHeightBlue = endHeightBlue;
    }
    return new ColorImageNew(pixelGrid).flipVertical();
  }

  /**
   * Converts the count values and approximates them in the range of 0 - 255.
   * This helper method is used when converting the histogram to image object.
   *
   * @param maxPeak    The max peak in the histogram
   * @param valueCount the count of the value
   * @return the value approximation between 0 and 256
   */
  public int getHeightValueBetween0and256(int maxPeak, int valueCount) {
    //System.out.println("maxPeak = " + maxPeak + ", valueCount = " + valueCount);
    return Math.round((float) (valueCount * 255) / maxPeak);
  }

  /**
   * This is a method to give the points where the lines need to be drawn.
   *
   * @param pixelGrid The pixelgrid to draw the grid on
   */
  public void drawGrid20(AbstractPixel[][] pixelGrid) {
    AbstractPixel pixel = new ColorPixel(200, 200, 200);

    for (int j = 0; j < 256; j++) {
      for (int i = 1; i < 21; i++) {
        int curr = Math.round(((float) (255 * i)) / 20);

        pixelGrid[curr][j] = pixel;
        pixelGrid[j][curr] = pixel;
      }
    }
  }


  /**
   * This helper function is used to draw the vertical lines that make up the histogram.
   * It can draw the lines based on channel and values.
   *
   * @param pixelGrid  The pixel grid that will be used to generate the image
   * @param value      the value for which the line is to be drawn
   * @param startPoint The starting point of the line
   * @param endPoint   The end point of the vertical line
   * @param channel    The channel of the value
   * @throws IllegalArgumentException when the value is not between 0 and 255
   */
  public void drawVerticalLine(AbstractPixel[][] pixelGrid, int value, int startPoint,
                               int endPoint, Channel channel) throws IllegalArgumentException {
    // Check if value is between 0 and 255
    if ((value < 0) || (value > 255)) {
      throw new IllegalArgumentException("Value should be between 0 and 255!");
    }

    // Check if value is between 0 and 255
    if ((startPoint < 0) || (startPoint > 255)) {
      throw new IllegalArgumentException("StartPoint should be between 0 and 255!");
    }

    // Check if value is between 0 and 255
    if ((endPoint < 0) || (endPoint > 255)) {
      throw new IllegalArgumentException("EndPoint should be between 0 and 255!");
    }

    // Default start from bottom
    AbstractPixel pixel = null;
    int top = 255;
    int bottom = 255;

    // Check for which pixel
    if (channel == Channel.red) {
      pixel = new ColorPixel(255, 0, 0);
    } else if (channel == Channel.green) {
      pixel = new ColorPixel(0, 255, 0);
    } else if (channel == Channel.blue) {
      pixel = new ColorPixel(0, 0, 255);
    } else {
      pixel = new ColorPixel(0, 0, 0);
    }

    // Check for the top and bottom points
    if (startPoint <= endPoint) {
      top = startPoint;
      bottom = endPoint;
    } else {
      top = endPoint;
      bottom = startPoint;
    }

    // Draw the line
    for (int i = top; i <= bottom; i++) {
      pixelGrid[i][value] = pixel;
    }
    // System.out.println("Line Drawn: " + channel + " - " + value);
  }

}
