import org.junit.Assert;
import org.junit.Test;

import model.ColorImageNew;
import model.Histogram;
import model.ImageNew;

/**
 * This is a test class to test level adjust function.
 */
public class LevelsAdjustImageTest {

  @Test
  public void testLevelsAdjust() throws Exception {
    try {

      ImageNew galaxyImage = new ColorImageNew("files/sample_images/galaxy.png");
      galaxyImage.saveToFile("la-output/galaxyImage.png");

      Histogram hist = new Histogram(galaxyImage);
      ImageNew galaxyHistogram = hist.toImage();
      galaxyHistogram.saveToFile("la-output/galaxyHistogram.png");

      ImageNew colorCorrectGalaxyImage = galaxyImage.levelsAdjust(20, 100, 255);
      colorCorrectGalaxyImage.saveToFile("la-output/levelsAdjustGalaxy.png");

      Histogram histLA = new Histogram(colorCorrectGalaxyImage);
      ImageNew histLAImage = histLA.toImage();
      histLAImage.saveToFile("la-output/laHistogram.png");
    } catch (Exception e) {
      // no exception to be thrown
      Assert.fail("Exception thrown for right values");
    }
  }

}