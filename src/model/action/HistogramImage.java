package model.action;

import model.Histogram;
import model.ImageNew;


/**
 * This helper class is used to generate a histogram Image for an Image.
 */
public class HistogramImage {
  /**
   * Returns the image object representing the histogram of the given image.
   *
   * @param image the image whose histogram is to be generated
   * @return the image object representing the histogram
   */
  public static ImageNew histogram(ImageNew image) {
    Histogram hist = new Histogram(image);
    return hist.toImage();
  }
}
