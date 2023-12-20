package controller.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import controller.ControllerException;
import model.ColorImageNew;
import model.ImageNew;

/**
 * This is a helper class for the controller. It contains the logic for
 * the common methods used by other helper classes.
 */
public class CommonHelpers {
  /**
   * This helper method is used to return the splitView image for the controller.
   * It takes in the list of parameters and checks if split is called.
   * It also takes in the image-before-action and the image-after action and applies the
   * splitView based on the given percentage.
   *
   * @param parameters           the command parameters
   * @param out                  the appendable output
   * @param imageDict            the hashmap of the imageDict
   * @param referImageName       the image-before-action
   * @param destinationImageName the image-after action
   * @param parameterPosition    the position on where the split is expected
   * @throws IOException if Scanner encounters an error while scanning
   */
  public static void splitAndSave(String[] parameters, Appendable out,
                                  HashMap<String, ImageNew> imageDict,
                                  String referImageName,
                                  String destinationImageName,
                                  int parameterPosition) throws IOException {
    if (parameters.length > parameterPosition) {
      if (Objects.equals(parameters[parameterPosition], "split")) {
        try {
          int splitPercentage = Integer.parseInt(parameters[parameterPosition + 1]);
          ImageNew splitOutput = new ColorImageNew().splitView(
                  imageDict.get(referImageName),
                  imageDict.get(destinationImageName),
                  splitPercentage
          );
          imageDict.put(destinationImageName, splitOutput);
          out.append("Split Image saved.....\n");
        } catch (Exception e) {
          ControllerException.printException(e, out);
        }
      }
    }
  }
}
