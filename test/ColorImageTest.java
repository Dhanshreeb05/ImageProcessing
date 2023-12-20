import org.junit.Assert;
import org.junit.Test;

import model.ColorImage;
import model.Image;

import static org.junit.Assert.assertEquals;

/**
 * This is a test class to test functionalities given by Color Image Class.
 * It tests functionalities like load image, save image, blur, sharpen image, etc
 */
public class ColorImageTest {

  @Test
  public void loadImage() {
    try {
      Image test = new ColorImage("files\\sample_images\\strawberry.png");
      //System.out.println(test);
      test.saveToFile("outputImages\\strawberry.png");
    } catch (Exception e) {
      Assert.fail("Exception thrown for valid params");
      e.printStackTrace();
    }
  }

  @Test
  public void testImageConstructorFileCorrect() throws Exception {
    Image test = new ColorImage("files/sample_images/manhattan-small.png");
    test.saveToFile("outputImages/checkManhattan.png");
    assertEquals(test, new ColorImage("outputImages/checkManhattan.png"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImageConstructorFileWrong() throws Exception {
    Image test = new ColorImage("files/sample_images/maall.png");
    test.saveToFile("outputImages/checkManhattan.png");
    assertEquals(test, new ColorImage("outputImages/checkManhattan.png"));
  }

  @Test
  public void testSplitChannelCorrectAllColors() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");

    Image[] allChannels = testImage.splitChannel();

    for (int i = 0; i < allChannels.length; i++) {
      allChannels[i].saveToFile("outputImages/manhattanCHa" + i + ".png");
    }
    assertEquals(
            new ColorImage("outputImages/manhattanCHa0.png"),
            new ColorImage("files/sample_images/manhattan-small-red.png")
    );
    assertEquals(
            new ColorImage("outputImages/manhattanCHa1.png"),
            new ColorImage("files/sample_images/manhattan-small-green.png")
    );
    assertEquals(
            new ColorImage("outputImages/manhattanCHa2.png"),
            new ColorImage("files/sample_images/manhattan-small-blue.png")
    );
  }

  @Test
  public void testSplitChannelCorrectRed() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image[] allChannels = testImage.splitChannel();
    allChannels[0].saveToFile("outputImages/manhattan-red.png");

    assertEquals(
            new ColorImage("outputImages/manhattan-red.png"),
            new ColorImage("files/sample_images/manhattan-small-red.png")
    );
  }

  @Test
  public void testSplitChannelCorrectBlue() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image[] allChannels = testImage.splitChannel();
    allChannels[2].saveToFile("outputImages/manhattan-blue.png");

    assertEquals(
            new ColorImage("outputImages/manhattan-blue.png"),
            new ColorImage("files/sample_images/manhattan-small-blue.png")
    );
  }

  @Test
  public void testSplitChannelCorrectGreen() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image[] allChannels = testImage.splitChannel();
    allChannels[1].saveToFile("outputImages/manhattan-green.png");

    assertEquals(
            new ColorImage("outputImages/manhattan-green.png"),
            new ColorImage("files/sample_images/manhattan-small-green.png")
    );
  }

  @Test
  public void testFlipHorizontal() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image flippedImage = testImage.flipHorizontal();
    flippedImage.saveToFile("outputimages/manhattanFH.png");
    Image expectedFlippedImage = new
            ColorImage("files/sample_images/manhattan-small-horizontal.png");
    Assert.assertNotEquals(flippedImage, testImage);
    assertEquals(expectedFlippedImage, flippedImage);
  }

  @Test
  public void testFlipHorizontalMultipleTimes() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image flippedImage = testImage.flipHorizontal()
            .flipHorizontal().flipHorizontal().flipHorizontal();
    assertEquals(testImage, flippedImage);
  }

  @Test
  public void testFlipVertical() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image flippedImage = testImage.flipVertical();
    flippedImage.saveToFile("outputimages/manhattanHV.png");
    Image expectedFlippedImage = new
            ColorImage("files/sample_images/manhattan-small-vertical.png");
    Assert.assertNotEquals(flippedImage, testImage);
    assertEquals(expectedFlippedImage, flippedImage);
  }

  @Test
  public void testFlipVerticalMultipleTimes() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image flippedImage = testImage.flipVertical().flipVertical().flipVertical().flipVertical();
    assertEquals(testImage, flippedImage);
  }

  @Test
  public void testBrighten() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image brightenImage = testImage.brightenDarken(50);
    brightenImage.saveToFile("outputimages/manhattanBrighten50.png");
    Image expectedBrightenedImage = new
            ColorImage("files/sample_images/manhattan-small-brighter-by-50.png");
    Assert.assertNotEquals(brightenImage, testImage);
    assertEquals(expectedBrightenedImage, brightenImage);
  }

  @Test
  public void testBrightenUpperCap255() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image brightenImage = testImage.brightenDarken(25);
    Image brightenImage1 = brightenImage.brightenDarken(25);
    brightenImage1.saveToFile("outputimages/manhattanBrighten25_25.png");
    Image expectedBrightenedImage = new
            ColorImage("files/sample_images/manhattan-small-brighter-by-50.png");
    Assert.assertNotEquals(brightenImage, testImage);
    Assert.assertNotEquals(brightenImage, brightenImage1);
    Assert.assertNotEquals(testImage, brightenImage1);
    assertEquals(expectedBrightenedImage, brightenImage1);
  }

  @Test
  public void testDarken() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image darkenImage = testImage.brightenDarken(-50);
    darkenImage.saveToFile("outputimages/manhattanDarken50.png");
    Image expectedOutputImage = new
            ColorImage("files/sample_images/manhattan-small-darker-by-50.png");
    Assert.assertNotEquals(testImage, darkenImage);
    assertEquals(expectedOutputImage, darkenImage);

  }

  @Test
  public void testDarkenLowerCap0() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image darkenImage = testImage.brightenDarken(-25);
    Image darkenImage1 = darkenImage.brightenDarken(-25);
    darkenImage1.saveToFile("outputimages/manhattanDarken100.png");
    Image expectedOutputImage = new
            ColorImage("files/sample_images/manhattan-small-darker-by-50.png");
    Assert.assertNotEquals(testImage, darkenImage);
    Assert.assertNotEquals(darkenImage1, darkenImage);
    Assert.assertNotEquals(testImage, darkenImage1);
    assertEquals(expectedOutputImage, darkenImage1);

  }

  @Test
  public void testDarkenBrighten() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image darkenImage = testImage.brightenDarken(-50);
    Image brightenImage = darkenImage.brightenDarken(50);
    brightenImage.saveToFile("outputimages/manhattanDarken50Brighten50.png");
    Assert.assertNotEquals(testImage, darkenImage);
    Assert.assertNotEquals(brightenImage, darkenImage);
  }

  @Test
  public void testBlur() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image expectedOutputImage = new
            ColorImage("files/sample_images/manhattan-small-blur.png");
    Assert.assertNotEquals(testImage, expectedOutputImage);
    Image blurredImage = testImage.blur();
    blurredImage.saveToFile("outputimages/testBlur.png");
    assertEquals(expectedOutputImage, blurredImage);
    Assert.assertNotEquals(testImage, expectedOutputImage);
  }

  @Test
  public void testBlurMultiple() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image expectedOutputImage = new
            ColorImage("files/sample_images/manhattan-small-blurred.png");
    Assert.assertNotEquals(testImage.blur().blur(), expectedOutputImage);
  }

  @Test
  public void testSharpen() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image expectedOutputImage = new
            ColorImage("files/sample_images/manhattan-small-sharpen.png");
    Assert.assertNotEquals(testImage, expectedOutputImage);
    Image sharpenedImage = testImage.sharpen();
    sharpenedImage.saveToFile("outputimages/test_sharpen.png");
    assertEquals(expectedOutputImage, sharpenedImage);
    Assert.assertNotEquals(testImage, expectedOutputImage);
  }

  @Test
  public void testSharpenMultiple() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image expectedOutputImage = new
            ColorImage("files/sample_images/manhattan-small-sharpen.png");
    Assert.assertNotEquals(testImage.sharpen().sharpen(),
            expectedOutputImage.sharpen());

  }

  @Test
  public void testSepia() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image expectedOutputImage = new
            ColorImage("files/sample_images/manhattan-small-sepia.png");
    Assert.assertNotEquals(testImage, expectedOutputImage);
    Image sepiaImage = testImage.convertToSepia();
    sepiaImage.saveToFile("outputimages/test_sepia.png");
    assertEquals(expectedOutputImage, sepiaImage);
    Assert.assertNotEquals(testImage, expectedOutputImage);

  }

  @Test
  public void testSepiaMultiple() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image expectedOutputImage = new
            ColorImage("files/sample_images/manhattan-small-sepia.png");
    Assert.assertNotEquals(testImage.convertToSepia().convertToSepia(),
            expectedOutputImage.sharpen().convertToSepia());
  }

  @Test
  public void saveToFileCorrect() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage.saveToFile("outputImages/savedOriginal.png");
    assertEquals(testImage, new ColorImage("outputImages/savedOriginal.png"));
  }

  @Test(expected = Exception.class)
  public void saveToFileWrongPath() throws Exception {
    String outputPath = "C:\\Users\\mule.r\\mh.png";
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage.saveToFile(outputPath);
    assertEquals(testImage, new ColorImage(outputPath));
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveToFileWrongType() throws Exception {
    String outputPath = "outputImages/savedOriginal.xyz";
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage.saveToFile(outputPath);
    assertEquals(testImage, new ColorImage(outputPath));
  }


  @Test
  public void rotateClockwise() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image testImage1 = testImage.rotateClockwise();
    testImage1.saveToFile("outputimages/manhattanBrightenRotateCW.png");
    //assertNotEquals(testImage,testImage1);
    assertEquals(
            new ColorImage("outputImages/manhattanBrightenRotateCW.png"),
            new ColorImage("files/sample_images/manhattan-small-clockwise.png")
    );
  }

  @Test
  public void rotateClockwiseMultiple() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image expectedOutputImage = testImage.rotateClockwise()
            .rotateClockwise().rotateClockwise().rotateClockwise();
    assertEquals(testImage, expectedOutputImage);

  }

  @Test
  public void rotateAntiClockwise() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image testImage1 = testImage.rotateAntiClockwise();
    testImage1.saveToFile("outputimages/manhattanBrightenRotateAntiCW.png");
    testImage.saveToFile("outputimages/manhattanSource.png");
    assertEquals(
            new ColorImage("outputImages/manhattanBrightenRotateAntiCW.png"),
            new ColorImage("files/sample_images/manhattan-small-anticlockwise.png")
    );
  }

  @Test
  public void rotateAntiClockwiseMultiple() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image expectedOutputImage = testImage.rotateAntiClockwise()
            .rotateAntiClockwise().rotateAntiClockwise().rotateAntiClockwise();
    assertEquals(testImage, expectedOutputImage);

  }

  @Test
  public void testEqualsAndHashcode() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image testImage1 = new ColorImage("files/sample_images/manhattan-small.png");
    Image testImage2 = new ColorImage("files/sample_images/manhattan-small-horizontal.png");
    assertEquals(testImage, testImage);
    assertEquals(testImage, testImage1);
    Assert.assertNotEquals(testImage, testImage2);
    assertEquals(testImage.hashCode(), testImage.hashCode());
    assertEquals(testImage.hashCode(), testImage1.hashCode());
    Assert.assertNotEquals(testImage.hashCode(), testImage2.hashCode());
  }

  @Test
  public void saveFileTest() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage.saveToFile("outputimages/saveImage.png");
    Image savedImage = new ColorImage("outputimages/saveImage.png");
    assertEquals(savedImage, testImage);
  }

  @Test
  public void testCrop() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image savedImage = testImage.crop(100, 100, 399, 99);
    savedImage.saveToFile("outputimages/croppedImage.png");
    Assert.assertNotEquals(testImage, savedImage);
  }

  @Test
  public void saveAsJPEG() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage.saveToFile("outputimages/manhattan-small.jpeg");
    try {
      Image testImage1 = new ColorImage("outputimages/manhattan-small.jpeg");
    } catch (IllegalArgumentException e) {
      Assert.fail();
    }
  }

  @Test
  public void loadPPM() throws Exception {
    Image testImage = new ColorImage("files/sample_images/Koala.ppm");
    testImage.saveToFile("outputimages/Koala.png");
    try {
      Image testImage1 = new ColorImage("outputimages/Koala.png");
    } catch (IllegalArgumentException e) {
      Assert.fail();
    }
  }

  @Test
  public void savePPM() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    testImage.saveToFile("outputImages/mahppm.ppm");
    try {
      Image testImage1 = new ColorImage("outputimages/mahppm.ppm");
    } catch (IllegalArgumentException e) {
      Assert.fail();
    }
  }

  @Test
  public void loadAndSavePPM() throws Exception {
    Image testImage = new ColorImage("files/sample_images/Koala.ppm");
    testImage.saveToFile("outputImages/koalass.ppm");

    Image savedImage = new ColorImage("outputImages/koalass.ppm");
    Image expectedImage = new ColorImage("files/sample_images/Koala.ppm");
    assertEquals(expectedImage, savedImage);
  }

  @Test
  public void convertToGreyscaleIntensity() throws Exception {
    Image testImage = new ColorImage("files/sample_images/Koala.ppm");
    Image greyscaleInten = testImage.convertToGreyscale("intensity");
    greyscaleInten.saveToFile("outputImages/k-intensity.png");

    Image expectedIntenImage = (new
            ColorImage("files/sample_images/koala-intensity-greyscale.png"));
    Image intenImage = (new ColorImage("outputImages/k-intensity.png"));
    assertEquals(intenImage, expectedIntenImage);
  }


  @Test
  public void convertToGreyscaleLuma() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image greyscaleLuma = testImage.convertToGreyscale("luma");
    greyscaleLuma.saveToFile("outputImages/manhattan-small-luma.png");

    Image expectedLumaImage = (new
            ColorImage("files/sample_images/manhattan-small-luma-greyscale.png"));
    Image lumaImage = (new ColorImage("outputImages/manhattan-small-luma.png"));
    assertEquals(lumaImage, expectedLumaImage);
  }

  @Test
  public void testEquality() throws Exception {
    Image testImage = new ColorImage("files/sample_images/manhattan-small.png");
    Image testImag2 = new ColorImage("files/sample_images/manhattan-small.png");
    Assert.assertEquals(testImag2, testImage);
    Assert.assertEquals(testImag2.convertToGreyscale("luma"), testImage.convertToGreyscale("luma"));
    Assert.assertNotEquals(testImage.convertToGreyscale("luma"), testImag2);
    Assert.assertNotEquals(testImag2.convertToGreyscale("luma"), testImage);
  }
}