package controller;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Scanner;

import controller.helper.BlueComponent;
import controller.helper.Blur;
import controller.helper.Brighten;
import controller.helper.ColorCorrect;
import controller.helper.Compress;
import controller.helper.GreenComponent;
import controller.helper.Histogram;
import controller.helper.HorizontalFlip;
import controller.helper.IntensityComponent;
import controller.helper.LevelsAdjust;
import controller.helper.Load;
import controller.helper.LumaComponent;
import controller.helper.RGBCombine;
import controller.helper.RGBSplit;
import controller.helper.RedComponent;
import controller.helper.RotateAntiClockwise;
import controller.helper.RotateClockwise;
import controller.helper.Save;
import controller.helper.Sepia;
import controller.helper.Sharpen;
import controller.helper.ValueComponent;
import controller.helper.VerticalFlip;
import model.Image;
import model.ImageNew;

/**
 * This is the controller class which communicates with the model and the view of our project.
 * It has functions to accept user input and perform functions corresponding to it.
 */
public class ImageController {
  final Readable in;
  final Appendable out;
  private Image model;

  /**
   * This constructor is used to initialize the controller object.
   *
   * @param in  the readable object to get the input from
   * @param out the Appendable object to append the output to
   */
  public ImageController(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  /**
   * This helper function is used to help handle and print exceptions without crashing the program.
   *
   * @param e The exception to print
   * @throws IOException If any error occurs during appending message
   */
  private void printException(Exception e) throws IOException {
    this.out.append("Exception occurred... : ")
            .append(e.getMessage()).append("\n Try again with valid arguments...\n");

  }


  /**
   * This executes and starts the ImageController.
   *
   * @throws IOException when any input error occurs.
   */
  public void runController() throws IOException {

    Scanner scan = new Scanner(this.in);

    HashMap<String, ImageNew> imageDict = new HashMap<>();

    while (true) {
      String sc = scan.next();
      switch (sc) {

        case "load":
          new Load().runHelper(scan, out, imageDict);
          break;

        case "brighten":
          new Brighten().runHelper(scan, out, imageDict);
          break;

        case "vertical-flip":
          new VerticalFlip().runHelper(scan, out, imageDict);
          break;

        case "horizontal-flip":
          new HorizontalFlip().runHelper(scan, out, imageDict);
          break;

        case "value-component":
          new ValueComponent().runHelper(scan, out, imageDict);
          break;

        case "luma-component":
          new LumaComponent().runHelper(scan, out, imageDict);
          break;

        case "intensity-component":
          new IntensityComponent().runHelper(scan, out, imageDict);
          break;

        case "red-component":
          new RedComponent().runHelper(scan, out, imageDict);
          break;

        case "blue-component":
          new BlueComponent().runHelper(scan, out, imageDict);
          break;

        case "green-component":
          new GreenComponent().runHelper(scan, out, imageDict);
          break;

        case "rgb-split":
          new RGBSplit().runHelper(scan, out, imageDict);
          break;

        case "rgb-combine":
          new RGBCombine().runHelper(scan, out, imageDict);
          break;

        case "blur":
          new Blur().runHelper(scan, out, imageDict);
          break;

        case "rotate-clockwise":
          new RotateClockwise().runHelper(scan, out, imageDict);
          break;

        case "rotate-anticlockwise":
          new RotateAntiClockwise().runHelper(scan, out, imageDict);
          break;

        case "sharpen":
          new Sharpen().runHelper(scan, out, imageDict);
          break;

        case "sepia":
          new Sepia().runHelper(scan, out, imageDict);
          break;

        case "save":
          new Save().runHelper(scan, out, imageDict);
          break;

        case "compress":
          new Compress().runHelper(scan, out, imageDict);
          break;

        case "histogram":
          new Histogram().runHelper(scan, out, imageDict);
          break;

        case "color-correct":
          new ColorCorrect().runHelper(scan, out, imageDict);
          break;

        case "levels-adjust":
          new LevelsAdjust().runHelper(scan, out, imageDict);
          break;

        case "run":
          try {
            String filePath = scan.next();
            Reader in = new FileReader(filePath);
            //StringBuffer out = new StringBuffer();

            ImageController controller = new ImageController(in, this.out);
            controller.runController();

            this.out.append("Command run script-file Successfully executed.....\n");
          } catch (Exception e) {
            printException(e);
          }

          break;

        case "quit":
        case "exit":
          this.out.append("Application ended !!!\n\n");
          return;

        default:
          if (sc.matches("^#.*")) {
            this.out.append("Comment Ignored....\n");
            scan.nextLine();
            break;
          } else {
            this.out.append("Invalid command. please try again!\n");
          }

      }
    }


  }


}
