package controller.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import controller.ControllerException;
import controller.ControllerHelper;
import model.ImageNew;

/**
 * This is a helper class for the controller. It contains the logic for
 * the calling the RotateAntiClockwise method  of the image.
 */
public class RotateAntiClockwise implements ControllerHelper {
  @Override
  public void runHelper(Scanner scan, Appendable out,
                        HashMap<String, ImageNew> imageDict) throws IOException {
    try {
      String nextLine = scan.nextLine();
      String[] parameters = nextLine.split(" ");

      String referImageName = parameters[1];
      String destination_image_name = parameters[2];

      imageDict.put(destination_image_name,
              imageDict.get(referImageName).rotateAntiClockwise());

      out.append("Command rotate-anticlockwise Successfully executed.....\n");
    } catch (Exception e) {
      ControllerException.printException(e, out);
    }
  }
}
