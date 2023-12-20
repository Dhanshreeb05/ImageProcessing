package controller.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import controller.ControllerException;
import controller.ControllerHelper;
import model.ColorImageNew;
import model.ImageNew;

import static controller.helper.CommonHelpers.splitAndSave;


/**
 * This is a helper class for the controller. It contains the logic for
 * the calling the LumaComponent method  of the image.
 */
public class LumaComponent implements ControllerHelper {
  @Override
  public void runHelper(Scanner scan, Appendable out,
                        HashMap<String, ImageNew> imageDict) throws IOException {
    try {
      String nextLine = scan.nextLine();
      String[] parameters = nextLine.split(" ");

      String referImageName = parameters[1];
      String imageName = parameters[2];

      if (imageDict.containsKey(referImageName)) {
        imageDict.put(imageName,
                (new ColorImageNew((ColorImageNew) imageDict
                        .get(referImageName))).convertToGreyscale("luma"));
        out.append("Command luma-component Successfully executed.....\n");

        splitAndSave(parameters, out, imageDict, referImageName,
                imageName, 6);

      } else {
        throw new IllegalArgumentException("Image object does not exist.");
      }
    } catch (Exception e) {
      ControllerException.printException(e, out);
    }
  }
}
