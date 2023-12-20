package controller.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import controller.ControllerException;
import controller.ControllerHelper;
import model.ImageNew;

/**
 * This is a helper class for the controller. It contains the logic for
 * the calling the splitChannel method  of the image.
 */
public class RGBSplit implements ControllerHelper {
  @Override
  public void runHelper(Scanner scan, Appendable out,
                        HashMap<String, ImageNew> imageDict) throws IOException {
    try {
      String nextLine = scan.nextLine();
      String[] parameters = nextLine.split(" ");

      String referImageName = parameters[1];
      String redImageName = parameters[2];
      String greenImageName = parameters[3];
      String blueImageName = parameters[4];

      ImageNew[] splitImages = imageDict.get(referImageName).splitChannel();
      imageDict.put(redImageName, splitImages[0]);
      imageDict.put(greenImageName, splitImages[1]);
      imageDict.put(blueImageName, splitImages[2]);

      out.append("Command rgb-split Successfully executed.....\n");
    } catch (Exception e) {
      ControllerException.printException(e, out);
    }
  }
}
