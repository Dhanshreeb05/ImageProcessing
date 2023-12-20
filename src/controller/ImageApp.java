package controller;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

/**
 * Demonstrates a simple command-line-based Applications.
 */
public class ImageApp {
  private String path;

  /**
   * Main method to run the application. Can take in command line argument.
   * Only 'run script path-to-file' command line argument is supported.
   * If no argument is passed, opens up an interactive console, where commands
   * can be entered manually.
   *
   * @param args commandline arguments
   * @throws IOException when I/O operations are performed incorrectly
   */
  public static void main(String[] args) throws IOException {

    if (args.length > 0) {
      //If run command line argument is passed
      //if (args[0].equals("run") && args[1].equals("script")) {
      if (args[0].equals("-file")) {
        try {
          Scanner scan = new Scanner(System.in);
          String filePath = args[1];
          Reader in = new FileReader(filePath);
          ImageController controller = new ImageController(in, System.out);
          controller.runController();

          System.out.append("Command run script script-file Successfully executed.....\n");
        } catch (Exception e) {
          System.out.println(e);
        }
      } else {
        System.out.println("Only 'run script path-to-script' argument is supported!.");
      }
    } else {
      // If command line argument is not passed
      System.out.println("Welcome to our controller.ImageApp:");
      System.out.println("Please enter your commands :\n");
      new ImageController(new InputStreamReader(System.in), System.out).runController();
      System.out.println("Thank you for using ImageApp!\n");
    }
  }

}
