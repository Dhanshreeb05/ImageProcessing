package model;

import java.awt.image.BufferedImage;

import model.action.HistogramImage;
import model.action.SplitViewImage;

/**
 * This class extend the functionality of the greyscaleImage class.
 * it implements the ImageNew interface.
 */
public class GreyscaleImageNew extends GreyscaleImage implements ImageNew {

  /**
   * Generates a GreyscaleImageNew object from the given image.
   *
   * @param image the image given
   */
  public GreyscaleImageNew(Image image) {
    super(image);
  }

  /**
   * Generates a GreyscaleImageNew object from the given image and channel.
   *
   * @param image   the image given
   * @param channel the channel of the greyscale image
   */
  public GreyscaleImageNew(Image image, Channel channel) {
    super(image);
    this.imageChannel = channel;
  }

  /**
   * Generates a GreyscaleImageNew object from the given imageNew object and channel.
   *
   * @param image   the imageNew object given
   * @param channel the channel of the greyscale image
   */
  public GreyscaleImageNew(ImageNew image, Channel channel) {
    super(image, channel);
    this.imageChannel = channel;
  }

  /**
   * Generates a GreyscaleImageNew object from the given Color image and channel.
   *
   * @param colorImageNew the image given
   * @param channel       the channel of the greyscale image
   */
  public GreyscaleImageNew(ColorImageNew colorImageNew, Channel channel) {
    super(colorImageNew, channel);
    this.imageChannel = channel;
  }

  /**
   * Generates a greyscale image from a given grid of pixels.
   *
   * @param pixelGrid the given grid of pixels
   */
  public GreyscaleImageNew(AbstractPixel[][] pixelGrid) {
    super(pixelGrid);
  }


  @Override
  protected AbstractImage createAbstractImage(AbstractPixel[][] pixelGrid) {
    return new GreyscaleImageNew(pixelGrid);
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
    throw new IllegalStateException("Cannot Color Correct greyscale Image.");
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
  public ImageNew[] splitChannel() {
    throw new IllegalStateException("Cannot perform split on Greyscale");
  }

  @Override
  public ImageNew combineChannels(Image red, Image green, Image blue) {
    return null;
  }

  @Override
  public ImageNew convertToSepia() {
    throw new IllegalStateException("Cannot Convert Greyscale to Sepia");
  }

  @Override
  public ImageNew convertToGreyscale(String type) {
    return this;
  }

  @Override
  protected ColorImageNew convertToColorImage() {
    return new ColorImageNew(super.convertToColorImage());
  }

  @Override
  public ImageNew flipHorizontal() {
    return (GreyscaleImageNew) super.flipHorizontal();
  }

  @Override
  public ImageNew flipVertical() {
    return (GreyscaleImageNew) super.flipVertical();
  }

  @Override
  public ImageNew brightenDarken(int value) {
    return new GreyscaleImageNew(super.brightenDarken(value));
  }

  @Override
  public ImageNew blur() {
    return (GreyscaleImageNew) (super.blur());
  }

  @Override
  public ImageNew sharpen() {
    return (GreyscaleImageNew) super.sharpen();
  }

  @Override
  public ImageNew crop(int x, int y, int width, int height) throws IllegalArgumentException {
    return new GreyscaleImageNew(super.crop(x, y, width, height), this.imageChannel);
  }


  @Override
  public ImageNew rotateClockwise() {
    return (GreyscaleImageNew) super.rotateClockwise();
  }

  @Override
  public ImageNew rotateAntiClockwise() {
    return (GreyscaleImageNew) super.rotateAntiClockwise();
  }

  @Override
  public BufferedImage getBufferedImage() {
    return this.convertToColorImage().getBufferedImage();
  }
}
