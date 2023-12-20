package controller.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import controller.ControllerException;
import controller.ControllerHelper;
import model.ColorImageNew;
import model.ImageNew;

/**
 * This is a helper class for the controller. It contains the logic for
 * the calling the color compress method  of the image.
 */
public class Compress implements ControllerHelper {
  @Override
  public void runHelper(Scanner scan, Appendable out,
                        HashMap<String, ImageNew> imageDict) throws IOException {
    try {
      String nextLine = scan.nextLine();
      String[] parameters = nextLine.split(" ");

      int compressPercentage = Integer.parseInt(parameters[1]);
      String referImageName = parameters[2];
      String imageName = parameters[3];

      if (imageDict.containsKey(referImageName)) {
        imageDict.put(
                imageName,
                new ColorImageNew((ColorImageNew) imageDict.get(
                        referImageName)).compress(compressPercentage)
        );

        out.append("Command compress Successfully executed.....\n");
      } else {
        throw new IllegalArgumentException("Image object does not exist.");
      }
    } catch (Exception e) {
      ControllerException.printException(e, out);
    }
  }

}
