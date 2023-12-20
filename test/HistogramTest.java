import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.Channel;
import model.ColorImageNew;
import model.Histogram;
import model.ImageNew;

import static org.junit.Assert.assertEquals;

/**
 * This is a test class to test Histogram.
 */
public class HistogramTest {
  private Histogram hist;

  @Before
  public void setup() throws IOException {
    ImageNew testImage = new ColorImageNew("files/sample_images/manhattan-small.png");
    hist = new Histogram(testImage);
  }

  @Test
  public void testConstructor() throws IOException {
    try {
      ImageNew testImage = new ColorImageNew("files/sample_images/manhattan-small.png");
      Histogram hist = new Histogram(testImage);
    } catch (Exception e) {
      Assert.fail("Exception thrown for no reason.");
    }
  }

  @Test
  public void testGetCount() throws IOException {
    assertEquals(249, hist.getCount(123, Channel.red));

  }

  @Test
  public void toImage() throws Exception {
    ImageNew histogramImage = hist.toImage();
    histogramImage.saveToFile("new-outputimages/histogramImage.png");
    try {
      ImageNew testImage = new ColorImageNew("new-outputimages/histogramImage.png");
    } catch (Exception e) {
      Assert.fail("Failed to save file");
    }
  }
}