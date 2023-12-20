package controllergui;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

import controller.ImageController;
import model.ColorImageNew;
import model.ImageNew;

/**
 * This is the entry class of our application.
 */
public class ImageAppGUI {
  private String path;

  /**
   * This method starts the application.
   *
   * @param args optional command line arguments
   * @throws IOException throws exception if app crashes
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
      } else if (args[0].equals("-text")) {
        // If command line argument is not passed
        System.out.println("Welcome to our controller.ImageApp:");
        System.out.println("Please enter your commands :\n");
        new ImageController(new InputStreamReader(System.in), System.out).runController();
        System.out.println("Thank you for using ImageApp!\n");
      } else {
        System.out.println("Only -file and -text arguments are supported!");
      }
    } else {
      System.out.println("Starting GUI app");
      ImageNew model = new ColorImageNew();
      ControllerGUI controller = new ControllerGUI(model);
      IView view = new JFrameView("Image Application");
      controller.setView(view);
    }
  }
}
