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
 * the calling the combineChannels method  of the image.
 */
public class RGBCombine implements ControllerHelper {
  @Override
  public void runHelper(Scanner scan, Appendable out,
                        HashMap<String, ImageNew> imageDict) throws IOException {
    try {
      String nextLine = scan.nextLine();
      String[] parameters = nextLine.split(" ");

      String destinationImageName = parameters[1];
      String redImageName = parameters[2];
      String greenImageName = parameters[3];
      String blueImageName = parameters[4];

      imageDict.put(destinationImageName, new ColorImageNew().combineChannels(
              imageDict.get(redImageName),
              imageDict.get(greenImageName),
              imageDict.get(blueImageName)
      ));

      out.append("Command rgb-combine Successfully executed.....\n");
    } catch (Exception e) {
      ControllerException.printException(e, out);
    }
  }
}
