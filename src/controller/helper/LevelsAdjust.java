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
 * the calling the LevelsAdjust method  of the image.
 */
public class LevelsAdjust implements ControllerHelper {
  @Override
  public void runHelper(Scanner scan, Appendable out,
                        HashMap<String, ImageNew> imageDict) throws IOException {


    try {
      String nextLine = scan.nextLine();
      String[] parameters = nextLine.split(" ");

      int b = Integer.parseInt(parameters[1]);
      int m = Integer.parseInt(parameters[2]);
      int w = Integer.parseInt(parameters[3]);
      String referImageName = parameters[4];
      String destination_image_name = parameters[5];

      ImageNew resultImage = imageDict.get(referImageName).levelsAdjust(b, m, w);

      imageDict.put(destination_image_name, resultImage);
      out.append("Command levelsAdjust Successfully executed.....\n");


      splitAndSave(parameters, out, imageDict, referImageName,
              destination_image_name, 6);

      //      if (parameters.length > 6) {
      //        if (Objects.equals(parameters[6], "split")) {
      //          try {
      //            int splitPercentage = Integer.parseInt(parameters[7]);
      //            ImageNew splitOutput = new ColorImageNew().splitView(
      //                    imageDict.get(referImageName),
      //                    resultImage,
      //                    splitPercentage);
      //            imageDict.put(destination_image_name, splitOutput);
      //          } catch (Exception e) {
      //            ControllerException.printException(e, out);
      //          }
      //        }
      //      }

    } catch (Exception e) {
      ControllerException.printException(e, out);
    }
  }
}
