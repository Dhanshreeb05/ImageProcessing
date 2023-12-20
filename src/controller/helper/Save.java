package controller.helper;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import controller.ControllerException;
import controller.ControllerHelper;
import model.ImageNew;
import model.ImageType;

/**
 * This is a helper class for the controller. It contains the logic for
 * saving the image to the file system.
 */
public class Save implements ControllerHelper {
  public Save() {
    // Empty constructor for new GUI controller
  }

  @Override
  public void runHelper(Scanner scan, Appendable out,
                        HashMap<String, ImageNew> imageDict) throws IOException {
    try {
      String nextLine = scan.nextLine();
      String[] parameters = nextLine.split(" ");

      String path = parameters[1];
      String referImageName = parameters[2];

      String formatType = path.substring(path.lastIndexOf('.') + 1);
      String[] supportedFileTypes = new String[]{"png", "jpeg", "jpg", "ppm"};
      if (!(Arrays.asList(supportedFileTypes).contains(formatType))) {
        throw new IllegalArgumentException("File type is not supported yet!");
      }

      ImageNew imageToSave = imageDict.get(referImageName);
      BufferedImage bufferedImage = imageToSave.getBufferedImage();

      saveHelper(path, bufferedImage);

      out.append("Command save Successfully executed.....\n");
    } catch (Exception e) {
      ControllerException.printException(e, out);
    }
  }

  /**
   * Helper method to save bufferImage object to file system.
   *
   * @param path          the path of file to save
   * @param bufferedImage the bufferImage object to save as an image
   * @throws Exception if error is occurred
   */
  public void saveHelper(String path, BufferedImage bufferedImage) throws Exception {
    String formatType = path.substring(path.lastIndexOf('.') + 1);
    Files.deleteIfExists(new File(path).toPath());
    Files.createDirectories(Paths.get(path).getParent());

    if (formatType.equals("ppm")) {
      boolean success = this.writePPM(path, bufferedImage);
      if (!success) {
        throw new Exception("File not saved.\n");
      }
    } else {
      ImageIO.write(bufferedImage, String.valueOf(ImageType.PNG), new File(path));
    }
  }

  /**
   * Saves a PPM file to the specified location.
   *
   * @param filename The path to save the PPM image
   * @return true if PPM file is successfully saved, else false
   */
  boolean writePPM(String filename, BufferedImage bufferedImage) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
      int numRows = bufferedImage.getHeight();
      int numCols = bufferedImage.getWidth();

      bw.write("P3\n");
      bw.write(numCols + " " + numRows + "\n");
      bw.write("255\n");

      for (int i = 0; i < numRows; i++) {
        for (int j = 0; j < numCols; j++) {
          int[] rgb = getRGB(bufferedImage.getRGB(j, i));

          bw.write(rgb[0] + "\n" + rgb[1] + "\n" + rgb[2] + "\n");
        }
      }

      return true;
    } catch (IOException e) {
      return false;
    }
  }


  /**
   * Get the RGB values from the pixel.
   *
   * @param rgb the RGB value
   * @return array containing the RED, GREEN, BLUE values
   */
  public int[] getRGB(int rgb) {
    int transparency = (rgb >> 24) & 0xff;
    int red = (rgb >> 16) & 0xff;
    int green = (rgb >> 8) & 0xff;
    int blue = (rgb) & 0xff;
    return new int[]{red, green, blue};

  }
}
