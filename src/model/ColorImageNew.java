package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.imageio.ImageIO;

import model.action.HistogramImage;
import model.action.SplitViewImage;

/**
 * This class extend the functionality of the ColorImage class.
 * it implements the ImageNew interface.
 */
public class ColorImageNew extends ColorImage implements ImageNew {

  /**
   * Creates a colorImageNew object from the given image.
   *
   * @param image the given image
   */
  public ColorImageNew(Image image) {
    this.pixelGrid = new AbstractPixel[image.getHeight()][image.getWidth()];
    this.numRows = image.getHeight();
    this.numCols = image.getWidth();

    for (int i = 0; i < this.pixelGrid.length; i++) {
      for (int j = 0; j < this.pixelGrid[0].length; j++) {
        this.pixelGrid[i][j] = createNewPixel(image.getPixel(i, j).getRGBA());
      }
    }
  }


  /**
   * Generates a color ImageNew object from the grid of pixels.
   *
   * @param grid the grid of pixels
   */
  public ColorImageNew(AbstractPixel[][] grid) {
    //For creating model.Image object from grid wise data
    super(grid);
  }

  /**
   * Creates a colorImageNew object from the given image.
   *
   * @param image the given image
   */
  public ColorImageNew(ImageNew image) {
    super(image);
  }


  /**
   * Creates a colorImageNew object from the given file path.
   *
   * @param path the file path of the image
   * @throws IOException when image not found
   */
  public ColorImageNew(String path) throws IOException {
    //this = new ColorImageNew(new ColorImage(path));
    super(path);
  }

  /**
   * Creates an empty ColorImage Object.
   */
  public ColorImageNew() {
    super();
  }

  public ColorImageNew(BufferedImage bufferImageObject) {
    super(bufferImageObject);
  }

  @Override
  public ImageNew compress(int percentage) throws IllegalArgumentException {
    return CompressImage.compress(this, percentage);
  }

  @Override
  public ImageNew histogram() {
    return HistogramImage.histogram(this);
  }

  @Override
  public ImageNew colorCorrect() {
    return ColorCorrectImage.colorCorrect(this);
  }

  @Override
  public ImageNew levelsAdjust(int b, int m, int w) throws IllegalArgumentException {
    return LevelsAdjustImage.levelsAdjust(this, b, m, w);
  }

  @Override
  public ImageNew splitView(ImageNew leftImage,
                            ImageNew rightImage, int percentage) throws Exception {
    return SplitViewImage.splitView(leftImage, rightImage, percentage);
  }


  @Override
  protected AbstractImage createAbstractImage(AbstractPixel[][] pixelGrid) {
    return new ColorImageNew(pixelGrid);
  }

  @Override
  public ImageNew[] splitChannel() {
    ImageNew redChannel = new GreyscaleImageNew(this, Channel.red);
    ImageNew greenChannel = new GreyscaleImageNew(this, Channel.green);
    ImageNew blueChannel = new GreyscaleImageNew(this, Channel.blue);

    return new ImageNew[]{redChannel, greenChannel, blueChannel};
  }

  @Override
  public ImageNew flipHorizontal() {
    return (ColorImageNew) super.flipHorizontal();
  }

  @Override
  public ImageNew flipVertical() {
    return (ColorImageNew) super.flipVertical();
  }

  @Override
  public ImageNew brightenDarken(int value) {
    return (ColorImageNew) super.brightenDarken(value);
  }

  @Override
  public ImageNew blur() {
    return (ColorImageNew) (super.blur());
  }

  @Override
  public ImageNew sharpen() {
    return (ColorImageNew) super.sharpen();
  }

  @Override
  public ImageNew convertToSepia() {
    return (ColorImageNew) super.convertToSepia();
  }

  @Override
  public ImageNew rotateClockwise() {
    return (ColorImageNew) super.rotateClockwise();
  }

  @Override
  public ImageNew rotateAntiClockwise() {
    return (ColorImageNew) super.rotateAntiClockwise();
  }

  @Override
  public ImageNew crop(int x, int y, int width, int height) throws IllegalArgumentException {
    return (ColorImageNew) (super.crop(x, y, width, height));
  }

  @Override
  public ImageNew convertToGreyscale(String type) {
    return new GreyscaleImageNew(super.convertToGreyscale(type));
  }

  @Override
  public ImageNew combineChannels(Image red, Image green, Image blue) {
    return new ColorImageNew(super.combineChannels(red, green, blue));
  }


  @Override
  public void saveToFile(String path) throws Exception {
    //super.saveToFile(path);

    //Check for supported file types.
    String formatType = path.substring(path.lastIndexOf('.') + 1);
    String[] supportedFileTypes = new String[]{"png", "jpeg", "jpg", "ppm"};
    if (!(Arrays.asList(supportedFileTypes).contains(formatType))) {
      throw new IllegalArgumentException("File type is not supported yet!");
    }

    BufferedImage bufferedImage = new BufferedImage(numCols, numRows, BufferedImage.TYPE_INT_ARGB);
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        bufferedImage.setRGB(j, i, pixelGrid[i][j].getRGBA());
      }
    }
    Files.deleteIfExists(new File(path).toPath());
    Files.createDirectories(Paths.get(path).getParent());

    if (formatType.equals("ppm")) {
      boolean success = this.writePPM(path);
      if (!success) {
        throw new Exception("File not saved.\n");
      }
    } else {
      ImageIO.write(bufferedImage, String.valueOf(ImageType.PNG), new File(path));
    }
  }


}
