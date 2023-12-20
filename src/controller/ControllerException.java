package controller;

import java.io.IOException;

/**
 * This class handles the exceptions that occur as part of the controller.
 * It prevents the controller from crashing if any illegal command is passed.
 */
public class ControllerException {

  /**
   * This function prints the exception when the model throws an exception.
   *
   * @param e   The exception that has to be handled
   * @param out The Appender to append the message to
   * @throws IOException when IO exception occurs at the time of application run
   */
  public static void printException(Exception e, Appendable out) throws IOException {
    out.append("Exception occurred... : ").append(e.getMessage())
            .append("\n Try again with valid arguments...\n");
  }
}
