import org.junit.Assert;
import org.junit.Test;

import model.ColorImageNew;
import model.ImageNew;

import static org.junit.Assert.assertEquals;

/**
 * This is a test class to test color Image new.
 */
public class ColorImageNewTest {

  @Test
  public void testCrop() throws Exception {
    ImageNew testImage = new ColorImageNew("files/sample_images/manhattan-small.png");
    testImage.saveToFile("new-outputimages/Image.png");

    ImageNew savedImage = testImage.crop(100, 100, 50, 50);
    savedImage.saveToFile("new-outputimages/croppedImage.png");

    Assert.assertNotEquals(testImage, savedImage);
  }

  @Test
  public void testsplitView() throws Exception {
    try {
      ImageNew testImage = new ColorImageNew("files/sample_images/manhattan-small.png");
      testImage.saveToFile("new-outputimages/Image.png");

      ImageNew savedImage = testImage.splitView(testImage, testImage.blur(), 60);
      savedImage.saveToFile("new-outputimages/splitJoinedImageBlur30.png");
    } catch (Exception e) {
      Assert.fail("Exception thrown");
    }
  }

  @Test
  public void testCompress() throws Exception {
    try {
      ImageNew testImage = new ColorImageNew("files/sample_images/manhattan-small.png");
      ImageNew compressedImage = testImage.compress(50);
    } catch (Exception e) {
      Assert.fail("Exception thrown");
    }
  }

  @Test
  public void splitChannel() throws Exception {
    ImageNew testImage = new ColorImageNew("files/sample_images/manhattan-small.png");
    ImageNew[] allChannels = testImage.splitChannel();
    allChannels[0].saveToFile("new-outputimages/manhattan-red.png");
    allChannels[1].saveToFile("new-outputimages/manhattan-green.png");
    allChannels[2].saveToFile("new-outputimages/manhattan-blue.png");

    assertEquals(
            new ColorImageNew("new-outputimages/manhattan-red.png"),
            new ColorImageNew("files/sample_images/manhattan-small-red.png")
    );
  }

  @Test
  public void convertToSepia() throws Exception {
    ImageNew testImage = new ColorImageNew("files/sample_images/manhattan-small.png");
    ImageNew expectedOutputImage = new
            ColorImageNew("files/sample_images/manhattan-small-sepia.png");

    Assert.assertNotEquals(testImage, expectedOutputImage);

    ImageNew sepiaImage = testImage.convertToSepia();
    sepiaImage.saveToFile("new-outputimages/test_sepia.png");

    assertEquals(expectedOutputImage, sepiaImage);

    Assert.assertNotEquals(testImage, expectedOutputImage);

  }

  @Test
  public void convertToGreyscale() throws Exception {
    ImageNew testImage = new ColorImageNew("files/sample_images/manhattan-small.png");
    testImage.saveToFile("new-outputimages/Image.png");

    ImageNew savedImage = testImage.convertToGreyscale("value");
    savedImage.saveToFile("new-outputimages/greyscaleLuma.png");

    Assert.assertNotEquals(testImage, savedImage);
  }
}