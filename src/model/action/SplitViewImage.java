package model.action;

import java.awt.image.BufferedImage;

import model.AbstractPixel;
import model.ColorImageNew;
import model.ImageNew;

/**
 * This helper class has methods that can be used to generate the splitView of an image
 * before and after the action is performed.
 */
public class SplitViewImage {
  /**
   * Returns the split view of the two images that are passed.
   *
   * @param leftImage  The image before action
   * @param rightImage the image after action
   * @param percentage the percentage at which to split
   * @return the image showing the splitview
   * @throws Exception when the image dimensions don't match
   */
  public static ImageNew splitView(ImageNew leftImage,
                                   ImageNew rightImage, int percentage) throws Exception {
    // Check if both images have same length and height
    if ((leftImage.getWidth() != rightImage.getWidth())
            || (leftImage.getHeight() != rightImage.getHeight())) {
      throw new IllegalArgumentException("Both images should have same dimensions.");
    }
    //Check if percent value is between 0 and 100
    if ((percentage < 0) || (percentage > 100)) {
      throw new IllegalArgumentException("Percentage should be between 0 and 100.");
    }

    //Get left part of the Image
    int numLeftPixelWidth = Math.round(((float) leftImage.getWidth() * percentage) / 100);
    ImageNew leftImagePart = leftImage.crop(0, 0, numLeftPixelWidth, leftImage.getHeight());
    leftImagePart.saveToFile("new-outputimages/leftSplitPart.png");

    //Get right part of the Image
    ImageNew rightImagePart = rightImage.crop(0, numLeftPixelWidth,
            rightImage.getWidth() - numLeftPixelWidth, rightImage.getHeight());
    rightImagePart.saveToFile("new-outputimages/rightSplitPart.png");

    //Join the two parts


    return joinImages(leftImagePart, rightImagePart);
  }

  /**
   * Joins the two given images side by side and returns the new image.
   *
   * @param leftImage  The image before action
   * @param rightImage the image after action
   * @return the joined image
   * @throws IllegalArgumentException if the heights of the images do not match
   */
  private static ImageNew joinImages(ImageNew leftImage,
                                     ImageNew rightImage) throws IllegalArgumentException {
    //Check if same height
    if (leftImage.getHeight() != rightImage.getHeight()) {
      throw new IllegalArgumentException("The heights of the two images do not match!");
    }

    BufferedImage leftBuffer = leftImage.getBufferedImage();
    BufferedImage rightBuffer = rightImage.getBufferedImage();

    leftImage = new ColorImageNew(leftBuffer);
    rightImage = new ColorImageNew(rightBuffer);


    //Add logic here
    //create a blank new image
    int resultNumRows = leftImage.getHeight();
    int resultNumCols = leftImage.getWidth() + rightImage.getWidth();
    AbstractPixel[][] resultPixelGrid = new AbstractPixel[resultNumRows][resultNumCols];

    // for every row, add the two arrays

    for (int i = 0; i < resultNumRows; i++) {
      for (int j = 0; j < leftImage.getWidth(); j++) {
        resultPixelGrid[i][j] = leftImage.getPixel(i, j);
      }

      for (int k = 0; k < rightImage.getWidth(); k++) {
        resultPixelGrid[i][leftImage.getWidth() + k] = rightImage.getPixel(i, k);
      }
    }

    return new ColorImageNew(resultPixelGrid);
  }

}
