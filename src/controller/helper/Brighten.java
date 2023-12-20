package controller.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import controller.ControllerException;
import controller.ControllerHelper;
import model.ImageNew;

import static controller.helper.CommonHelpers.splitAndSave;

/**
 * This is a helper class for the controller. It contains the logic for
 * the calling the brighten/darken method of the image.
 */
public class Brighten implements ControllerHelper {
  @Override
  public void runHelper(Scanner scan, Appendable out, HashMap<String,
          ImageNew> imageDict) throws IOException {
    try {
      String nextLine = scan.nextLine();
      String[] parameters = nextLine.split(" ");

      int brightenValue = Integer.parseInt(parameters[1]);
      String referImageName = parameters[2];
      String imageName = parameters[3];

      if (imageDict.containsKey(referImageName)) {
        imageDict.put(imageName, imageDict.get(referImageName).brightenDarken(brightenValue));
        out.append("Command brighten Successfully executed.....\n");

        splitAndSave(parameters, out, imageDict, referImageName,
                imageName, 4);
      } else {
        throw new IllegalArgumentException("Image object does not exist.");
      }

    } catch (Exception e) {
      ControllerException.printException(e, out);
    }
  }
}
