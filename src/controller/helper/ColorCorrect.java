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
 * the calling the color correcting method  of the image.
 */
public class ColorCorrect implements ControllerHelper {
  @Override
  public void runHelper(Scanner scan, Appendable out, HashMap<String,
          ImageNew> imageDict) throws IOException {
    try {
      String nextLine = scan.nextLine();
      String[] parameters = nextLine.split(" ");

      String referImageName = parameters[1];
      String destination_image_name = parameters[2];

      ImageNew resultImage = imageDict.get(referImageName).colorCorrect();

      imageDict.put(destination_image_name, resultImage);
      out.append("Command colorCorrect Successfully executed.....\n");

      splitAndSave(parameters, out, imageDict, referImageName,
              destination_image_name, 3);

    } catch (Exception e) {
      ControllerException.printException(e, out);
    }
  }


}
