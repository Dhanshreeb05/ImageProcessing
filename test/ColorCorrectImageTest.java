import org.junit.Assert;
import org.junit.Test;

import model.ColorImageNew;
import model.Histogram;
import model.ImageNew;

/**
 * This is a test class for Color correction feature.
 */
public class ColorCorrectImageTest {

  @Test
  public void testColorCorrect() throws Exception {
    try {
      ImageNew galaxyImage = new ColorImageNew("files/sample_images/galaxy.png");
      galaxyImage.saveToFile("cc-output/galaxyImage.png");

      Histogram hist = new Histogram(galaxyImage);
      ImageNew galaxyHistogram = hist.toImage();
      galaxyHistogram.saveToFile("cc-output/galaxyHistogram.png");

      ImageNew colorCorrectGalaxyImage = galaxyImage.colorCorrect();
      colorCorrectGalaxyImage.saveToFile("cc-output/colorCorrectGalaxy.png");

      Histogram histCC = new Histogram(colorCorrectGalaxyImage);
      ImageNew ccHistogram = histCC.toImage();
      ccHistogram.saveToFile("cc-output/ccHistogram.png");
    } catch (Exception e) {
      Assert.fail("Exception thrown");
    }
  }
}