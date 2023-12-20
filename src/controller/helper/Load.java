package controller.helper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import controller.ControllerException;
import controller.ControllerHelper;
import model.ColorImageNew;
import model.ImageNew;

/**
 * Load is used to load an image into a image object. It implements the ControllerHelper interface.
 */
public class Load implements ControllerHelper {

  public Load() {
    // Empty constructor for new GUI controller.
  }

  @Override
  public void runHelper(Scanner scan, Appendable out, HashMap<String, ImageNew> imageDict)
          throws IOException {
    try {
      String nextLine = scan.nextLine();
      String[] parameters = nextLine.split(" ");

      String loadPath = parameters[1];
      String imageName = parameters[2];

      imageDict.put(imageName, new ColorImageNew(createBufferImageObject(loadPath)));

      out.append("Command load Successfully executed.....\n");
    } catch (Exception e) {
      ControllerException.printException(e, out);
    }
  }

  /**
   * Creates a buffered image object from the path of the image.
   *
   * @param path the file path of the image
   * @return the bufferedImage
   * @throws IOException when path is incorrect
   */
  public BufferedImage createBufferImageObject(String path) throws IOException {
    try {
      File imagefile = new File(path);
      BufferedImage bufferedImage = ImageIO.read(imagefile);
    } catch (IOException ie) {
      throw new IllegalArgumentException("File path does not exist. Cannot read file.");
    }

    //Check for supported file types.
    String formatType = path.substring(path.lastIndexOf('.') + 1);
    String[] supportedFileTypes = new String[]{"png", "jpeg", "jpg", "ppm"};
    if (!(Arrays.asList(supportedFileTypes).contains(formatType))) {
      throw new IllegalArgumentException("File type is not supported yet!");
    }

    BufferedImage bufferedImage;

    if (formatType.equals("ppm")) {
      bufferedImage = this.readPPM(path);
    } else {
      //For creating model.Image object from model.Image Path
      File imagefile = new File(path);
      bufferedImage = ImageIO.read(imagefile);
    }
    return bufferedImage;
  }

  /**
   * Creates bufferedImage object from the given ppm image.
   *
   * @param filename the filepath of the image
   * @return the buffered image object
   */
  BufferedImage readPPM(String filename) {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      throw new IllegalArgumentException("File not found......");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        bufferedImage.setRGB(j, i, getRGBA(r, g, b));
      }
    }
    return bufferedImage;
  }

  /**
   * Get the RGBA value that can be passed to the BufferImage object.
   *
   * @param red   the red value
   * @param green the green value
   * @param blue  the blue value
   * @return the RGBA value
   */
  public int getRGBA(int red, int green, int blue) {
    return ((0xFF) << 24)
            | ((red & 0xFF) << 16)
            | ((green & 0xFF) << 8)
            | (blue & 0xFF);
  }
}
