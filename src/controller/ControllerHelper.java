package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import model.ImageNew;

/**
 * This interface is used to create helper classes to take the logic out of controller.
 */
public interface ControllerHelper {

  /**
   * Execute the helper Class logic for a particular action.
   *
   * @param scan      the scanner object
   * @param out       the  Appender to append the message to
   * @param imageDict the imageDict that stores the image objects
   * @throws IOException if Scanner encounters an error while scanning
   */
  void runHelper(Scanner scan, Appendable out, HashMap<String, ImageNew> imageDict)
          throws IOException;
}
