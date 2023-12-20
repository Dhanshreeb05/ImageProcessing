package controller.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import controller.ControllerException;
import controller.ControllerHelper;
import model.ImageNew;

/**
 * This is a helper class for the controller. It contains the logic for
 * the calling the HorizontalFlip method  of the image.
 */
public class HorizontalFlip implements ControllerHelper {


  /**
   * This is a helper class for the controller. It contains the logic for
   * the calling the horizontalFlip method  of the image.
   */
  @Override
  public void runHelper(Scanner scan, Appendable out, HashMap<String,
          ImageNew> imageDict) throws IOException {
    try {
      String nextLine = scan.nextLine();
      String[] parameters = nextLine.split(" ");

      String referImageName = parameters[1];
      String imageName = parameters[2];

      if (imageDict.containsKey(referImageName)) {
        imageDict.put(imageName, imageDict.get(referImageName).flipHorizontal());
        out.append("Command horizontal-flip Successfully executed.....\n");
      } else {
        throw new IllegalArgumentException("Image object does not exist.");
      }

    } catch (Exception e) {
      ControllerException.printException(e, out);
    }
  }
}
