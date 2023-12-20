import org.junit.Assert;
import org.junit.Test;

import model.ColorImageNew;
import model.ImageNew;

/**
 * Test class to test compression function of Image.
 */
public class CompressImageTest {

  @Test
  public void compressColorImage() throws Exception {
    ImageNew img = new ColorImageNew("files/sample_images/manhattan-small.png");
    ImageNew newimg100 = img.compress(100);
    ImageNew expectedImage = new ColorImageNew("files/sample_images/test-compress-100.png");
    Assert.assertEquals(newimg100, expectedImage);
  }

  @Test
  public void compressColorImage20() throws Exception {
    ImageNew img = new ColorImageNew("files/sample_images/manhattan-small.png");
    ImageNew newimg20 = img.compress(20);
    ImageNew expectedImage = new ColorImageNew("files/sample_images/test-compress-20.png");
    Assert.assertEquals(newimg20, expectedImage);
  }

  @Test
  public void compressColorImageWrong() throws Exception {
    try {
      ImageNew img = new ColorImageNew("files/sample_images/manhattan-small.png");
      ImageNew newimg = img.compress(120);
      Assert.fail("Exception not thrown");
    } catch (Exception e) {
      //exception thrown because of wrong params.
    }
  }
}
