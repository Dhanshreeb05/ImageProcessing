package controller.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import controller.ControllerException;
import controller.ControllerHelper;
import model.ImageNew;

/**
 * This is a helper class for the controller. It contains the logic for
 * the calling the VerticalFlip method  of the image.
 */
public class VerticalFlip implements ControllerHelper {

  @Override
  public void runHelper(Scanner scan, Appendable out,
                        HashMap<String, ImageNew> imageDict) throws IOException {
    try {

      String nextLine = scan.nextLine();
      String[] parameters = nextLine.split(" ");

      String referImageName = parameters[1];
      String imageName = parameters[2];

      if (imageDict.containsKey(referImageName)) {
        imageDict.put(imageName, imageDict.get(referImageName).flipVertical());
        out.append("Command vertical-flip Successfully executed.....\n");
      } else {
        throw new IllegalArgumentException("Image object does not exist.");
      }

    } catch (Exception e) {
      ControllerException.printException(e, out);
    }
  }
}
