import org.junit.Assert;
import org.junit.Test;

import model.ColorImage;
import model.GreyscaleImage;
import model.Image;

/**
 * This is a Test class to test methods on Greyscale Image test.
 * This tests functionality like blur, darken,sepia etc.
 */
public class GreyscaleImageTest {

  @Test
  public void testConvertToColorImage() throws Exception {
    Image testColorImage = new ColorImage("files/sample_images/manhattan-small.png");
    GreyscaleImage testGreyScaleImage = new GreyscaleImage(testColorImage);
    testGreyScaleImage.saveToFile("outputimages/saveGreyscale.png");
    try {
      Image testColorImage1 = new ColorImage("outputimages/saveGreyscale.png");
    } catch (IllegalArgumentException e) {
      Assert.fail("File not saved properly");
    }
  }

  @Test
  public void testSplitChannelCorrectAllColors() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image testGreyscale = testImage.convertToGreyscale("value");
    try {

      Image[] allChannels = testGreyscale.splitChannel();
      Assert.fail("No Exception thrown");
    } catch (IllegalStateException e) {
      //excpetion thrown
    }
  }

  @Test
  public void testFlipHorizontal() throws Exception {
    Image testImage = new
            ColorImage("files/sample_images/manhattan-small-luma-greyscale.png");
    testImage = testImage.convertToGreyscale("luma");
    Image flippedImage = testImage.flipHorizontal();
    flippedImage.saveToFile("outputimages/manhattan_greyscale_luma.png");
    Image expectedFlippedImage = new
            ColorImage("files/sample_images/manhattan-small-luma-greyscale-horizontal.png");
    expectedFlippedImage = expectedFlippedImage.convertToGreyscale("luma");
    Assert.assertNotEquals(flippedImage, testImage);
    Assert.assertEquals(expectedFlippedImage, flippedImage);
  }

  @Test
  public void testFlipHorizontalMultipleTimes() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage = testImage.convertToGreyscale("luma");
    Image flippedImage = testImage.flipHorizontal()
            .flipHorizontal().flipHorizontal().flipHorizontal();
    Assert.assertEquals(testImage, flippedImage);
  }

  @Test
  public void testFlipVertical() throws Exception {
    Image testImage = new
            ColorImage("files/sample_images/manhattan-small-luma-greyscale.png");
    testImage = testImage.convertToGreyscale("luma");
    Image flippedImage = testImage.flipVertical();
    flippedImage.saveToFile("outputimages/manhattan_greyscale_luma_vertical_flip.png");
    Image expectedFlippedImage = new
            ColorImage("files/sample_images/manhattan-small-luma-greyscale-vertical.png");
    expectedFlippedImage = expectedFlippedImage.convertToGreyscale("luma");
    Assert.assertNotEquals(flippedImage, testImage);
    Assert.assertEquals(expectedFlippedImage, flippedImage);
  }

  @Test
  public void testFlipVerticalMultipleTimes() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage = testImage.convertToGreyscale("luma");
    Image flippedImage = testImage.flipVertical().flipVertical().flipVertical().flipVertical();
    Assert.assertEquals(testImage, flippedImage);
  }

  @Test
  public void testBrighten() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage = testImage.convertToGreyscale("luma");
    Image brightenImage = testImage.brightenDarken(50);
    brightenImage.saveToFile("outputimages/manhattanBrighten50.png");
    Image expectedBrightenedImage = new
            ColorImage("files/sample_images/manhattan-small-brighter-by-50.png");
    expectedBrightenedImage = expectedBrightenedImage.convertToGreyscale("luma");
    Assert.assertNotEquals(brightenImage, testImage);
    Assert.assertEquals(expectedBrightenedImage, brightenImage);
  }

  @Test
  public void testBrightenUpperCap255() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage = testImage.convertToGreyscale("luma");
    Image brightenImage = testImage.brightenDarken(25);
    Image brightenImage1 = brightenImage.brightenDarken(25);
    brightenImage1.saveToFile("outputimages/manhattanBrighten25_25.png");
    Image expectedBrightenedImage = new
            ColorImage("files/sample_images/manhattan-small-brighter-by-50.png");
    expectedBrightenedImage = expectedBrightenedImage.convertToGreyscale("luma");
    Assert.assertNotEquals(brightenImage, testImage);
    Assert.assertNotEquals(brightenImage, brightenImage1);
    Assert.assertNotEquals(testImage, brightenImage1);
    Assert.assertEquals(expectedBrightenedImage, brightenImage1);
  }

  @Test
  public void testDarken() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage.convertToGreyscale("luma");
    Image darkenImage = testImage.brightenDarken(-50);
    darkenImage.saveToFile("outputimages/manhattanDarken50.png");
    Image expectedOutputImage = new
            ColorImage("files/sample_images/manhattan-small-darker-by-50.png");
    expectedOutputImage.convertToGreyscale("luma");
    Assert.assertNotEquals(testImage, darkenImage);
    Assert.assertEquals(expectedOutputImage, darkenImage);
  }

  @Test
  public void testDarkenLowerCap0() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage.convertToGreyscale("luma");
    Image darkenImage = testImage.brightenDarken(-25);
    Image darkenImage1 = darkenImage.brightenDarken(-25);
    darkenImage1.saveToFile("outputimages/manhattanDarken100.png");
    Image expectedOutputImage = new
            ColorImage("files/sample_images/manhattan-small-darker-by-50.png");
    expectedOutputImage.convertToGreyscale("luma");
    Assert.assertNotEquals(testImage, darkenImage);
    Assert.assertNotEquals(darkenImage1, darkenImage);
    Assert.assertNotEquals(testImage, darkenImage1);
    Assert.assertEquals(expectedOutputImage, darkenImage1);

  }

  @Test
  public void testSepia() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage = testImage.convertToGreyscale("luma");
    try {
      Image sepiaImage = testImage.convertToSepia();
      Assert.fail("No Exception thrown for sepia");
    } catch (IllegalStateException e) {
      //exception thrown
    }

  }

  @Test(expected = IllegalArgumentException.class)
  public void saveToFileWrongType() throws Exception {
    String outputPath = "outputImages/savedOriginal.xyz";
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage.saveToFile(outputPath);
    Assert.assertEquals(testImage, new ColorImage(outputPath));
  }


  @Test
  public void rotateClockwise() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage = testImage.convertToGreyscale("luma");
    testImage = testImage.rotateClockwise();
    testImage.saveToFile("outputimages/manhattanGreyscaleRotateCW.png");
    Image expectedOutputImage = new
            ColorImage("files/sample_images/manhattan-small-greyscale-clockwise.png")
            .convertToGreyscale("luma");
    Assert.assertEquals(expectedOutputImage,
            testImage);
  }

  @Test
  public void rotateClockwiseMultiple() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage.convertToGreyscale("luma");
    Image expectedOutputImage = testImage.rotateClockwise()
            .rotateClockwise().rotateClockwise().rotateClockwise();
    Assert.assertEquals(testImage, expectedOutputImage);

  }

  @Test
  public void rotateAntiClockwise() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage = testImage.convertToGreyscale("luma");
    testImage.saveToFile("outputimages/manhattan-luma.png");
    testImage = testImage.rotateAntiClockwise();
    testImage.saveToFile("outputimages/manhattanLumaRotateAntiCW1.png");
    Image expectedOutputImage = new
            ColorImage("files/sample_images/"
            + "manhattan-small-greyscale-anticlockwise.png");
    expectedOutputImage = expectedOutputImage.convertToGreyscale("luma");
    Assert.assertEquals(expectedOutputImage,
            testImage);
  }

  @Test
  public void rotateAntiClockwiseMultiple() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage = testImage.convertToGreyscale("luma");
    Image expectedOutputImage = testImage.rotateAntiClockwise()
            .rotateAntiClockwise().rotateAntiClockwise().rotateAntiClockwise();

    Assert.assertEquals(testImage, expectedOutputImage);

  }

  @Test
  public void testEqualsAndHashcode() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image testImage1 = new ColorImage("files/sample_images/manhattan-small.png");
    Image testImage2 = new ColorImage("files/sample_images/manhattan-small-horizontal.png");
    testImage.convertToGreyscale("luma");
    testImage1.convertToGreyscale("luma");
    testImage2.convertToGreyscale("luma");
    Assert.assertEquals(testImage, testImage);
    Assert.assertEquals(testImage, testImage1);
    Assert.assertNotEquals(testImage, testImage2);
    Assert.assertEquals(testImage.hashCode(), testImage.hashCode());
    Assert.assertEquals(testImage.hashCode(), testImage1.hashCode());
    Assert.assertNotEquals(testImage.hashCode(), testImage2.hashCode());
  }

  @Test
  public void testCrop() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage.convertToGreyscale("luma");
    Image savedImage = testImage.crop(100, 100, 399, 99);
    savedImage.saveToFile("outputimages/croppedImage.png");
    try {
      Image testImage1 = new ColorImage("outputimages/croppedImage.png");
    } catch (IllegalArgumentException e) {
      Assert.fail("Exception wrongly thrown");
    }

  }

  @Test
  public void saveAsJPEG() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage = testImage.convertToGreyscale("luma");
    testImage.saveToFile("outputimages/manhattan-small.jpeg");
    //Assert.assertEquals(savedImage, testImage);
    try {
      testImage = new ColorImage("outputimages/manhattan-small.jpeg");
    } catch (IllegalArgumentException e) {
      Assert.fail("Test Fail");
    }
  }
}