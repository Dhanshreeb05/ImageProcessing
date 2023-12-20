package controllergui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import controller.helper.Load;
import controller.helper.Save;
import model.Channel;
import model.ColorImageNew;
import model.GreyscaleImageNew;
import model.ImageNew;

/**
 * This is a controller to communicate with View and model to perform required actions.
 */
public class ControllerGUI implements Features {
  private ImageNew model;
  private ImageNew currentImage;
  private ImageNew splitRightImage;
  private IView view;
  private String[] choicesForGetComponent;
  private String[] defaultChoices;
  private String[] splitViewChoices;
  private boolean isImageSaved;
  private boolean isSplitView;

  private int splitPercentage;


  /**
   * Constructer for the controller.
   *
   * @param m the ImageNew model
   */
  public ControllerGUI(ImageNew m) {
    model = m;
    this.isSplitView = false;
    this.splitPercentage = 50;

    splitViewChoices = new String[]{"Blur Image", "Sharpen Image", "Convert to Greyscale Luma",
        "Convert to Sepia", "Color Correct Image", "Levels Adjust"
    };

    defaultChoices = new String[]{ "Get Red Component", "Get Blue Component", "Get Green Component",
        "Flip Vertically", "Flip Horizontally", "Blur Image", "Sharpen Image",
        "Convert to Greyscale Luma", "Convert to Sepia", "Compress",
        "Color Correct Image", "Levels Adjust", "Darken Image", "Brighten Image"
    };

    choicesForGetComponent = new String[]{ "Flip Vertically", "Flip Horizontally", "Blur Image",
        "Sharpen Image", "Convert to Greyscale Luma", "Compress", "Levels Adjust",
        "Darken Image", "Brighten Image"
    };
  }

  /**
   * Adds the view to the controller.
   *
   * @param v the view object
   */
  public void setView(IView v) {
    view = v;
    //provide view with all the callbacks
    view.addFeatures(this);
  }


  @Override
  public void loadImage() {
    // Load a image
    try {
      String loadPath = view.loadAnImage();
      Load ld = new Load();
      BufferedImage loadedImage = ld.createBufferImageObject(loadPath);
      System.out.println(loadedImage);

      //Save original Image
      ImageNew originalImage = new ColorImageNew(loadedImage);
      this.currentImage = new ColorImageNew(loadedImage);

      view.setCurrentImage(loadedImage);
      this.updateHistogram(originalImage);
      this.isImageSaved = false;

    } catch (IllegalStateException e) {
      System.out.println("No file was chosen!");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void updateHistogram(ImageNew image) {
    view.setHistogramImage(image.histogram().getBufferedImage());
  }

  @Override
  public void saveCurrentImage() throws Exception {
    // Save an image
    try {
      String savePath;
      int valid = 0;
      do {
        savePath = view.saveAnImage();
        //System.out.println("lastindex : " + savePath.lastIndexOf('.'));
        String formatType = savePath.substring(savePath.lastIndexOf('.') + 1);
        //System.out.println("formatType  : " + formatType);
        String[] supportedFileTypes = new String[]{"png", "jpeg", "jpg", "ppm"};
        if (Arrays.asList(supportedFileTypes).contains(formatType)) {
          //System.out.println("Found  : " + formatType);
          valid = 1;
        } else {
          view.displayPopupMessage("Only png, jpg and ppm formats are allowed!",
                  "Error saving file!");
        }
      }
      while (valid == 0);

      Save sv = new Save();
      sv.saveHelper(savePath, currentImage.getBufferedImage());
      this.isImageSaved = true;
    } catch (IllegalStateException e) {
      System.out.println("No file was chosen!");
    }

  }


  @Override
  public void executeAction() throws Exception {
    String actionToPerform = view.executeAction();
    System.out.println("ActionToPerform : " + actionToPerform);

    Map<String, Integer> params;
    ImageNew newImage = null;

    switch (actionToPerform) {

      case "Get Red Component":
        newImage = new ColorImageNew(new GreyscaleImageNew(this.currentImage,
                Channel.red).getBufferedImage());

        // Update the actions that can be performed if split view is off
        if (!this.isSplitView) {
          view.setActionChoices(this.choicesForGetComponent);
        }

        break;

      case "Get Blue Component":
        newImage = new ColorImageNew(new GreyscaleImageNew(this.currentImage,
                Channel.blue).getBufferedImage());
        if (!this.isSplitView) {
          view.setActionChoices(this.choicesForGetComponent);
        }
        break;

      case "Get Green Component":
        newImage = new ColorImageNew(new GreyscaleImageNew(this.currentImage,
                Channel.green).getBufferedImage());

        if (!this.isSplitView) {
          view.setActionChoices(this.choicesForGetComponent);
        }
        break;

      case "Flip Vertically":
        newImage = this.currentImage.flipVertical();
        if (!this.isSplitView) {
          view.setActionChoices(this.defaultChoices);
        }

        break;

      case "Flip Horizontally":
        newImage = this.currentImage.flipHorizontal();
        if (!this.isSplitView) {
          view.setActionChoices(this.defaultChoices);
        }
        break;

      case "Blur Image":
        newImage = this.currentImage.blur();
        if (!this.isSplitView) {
          view.setActionChoices(this.defaultChoices);
        }
        break;

      case "Sharpen Image":
        newImage = this.currentImage.sharpen();
        if (!this.isSplitView) {
          view.setActionChoices(this.defaultChoices);
        }
        break;

      case "Convert to Greyscale Luma":
        newImage = new ColorImageNew(this.currentImage
                .convertToGreyscale("luma").getBufferedImage());
        if (!this.isSplitView) {
          view.setActionChoices(this.defaultChoices);
        }
        break;

      case "Convert to Sepia":
        newImage = this.currentImage.convertToSepia();
        if (!this.isSplitView) {
          view.setActionChoices(this.defaultChoices);
        }
        break;

      case "Compress":
        int valid = 0;
        do {
          params = view.getActionParameters("Compress");
          if (params.get("compressValue") > 0 && params.get("compressValue") < 100) {
            valid = 1;
          } else {
            view.displayPopupMessage("Value should be between 0 and 100!",
                    "Error Compressing Image!");
          }
        }
        while (valid == 0);

        newImage = this.currentImage.compress(params.get("compressValue"));
        if (!this.isSplitView) {
          view.setActionChoices(this.defaultChoices);
        }
        break;

      case "Color Correct Image":
        newImage = this.currentImage.colorCorrect();
        if (!this.isSplitView) {
          view.setActionChoices(this.defaultChoices);
        }
        break;

      case "Levels Adjust":
        params = view.getActionParameters("Levels Adjust");
        int b = params.get("blackValue");
        int m = params.get("midValue");
        int w = params.get("whiteValue");
        try {
          newImage = this.currentImage.levelsAdjust(b, m, w);

          if (!this.isSplitView) {
            view.setActionChoices(this.defaultChoices);
          }
        } catch (Exception e) {
          view.displayPopupMessage(e.getMessage(), "Error occurred!");
        }
        break;

      case "Brighten Image":
        params = view.getActionParameters("Brighten Image");
        newImage = this.currentImage.brightenDarken(params.get("brightenValue"));

        if (!this.isSplitView) {
          view.setActionChoices(this.defaultChoices);
        }
        break;


      case "Darken Image":
        params = view.getActionParameters("Darken Image");
        newImage = this.currentImage.brightenDarken(-1 * (params.get("darkenValue")));

        if (!this.isSplitView) {
          view.setActionChoices(this.defaultChoices);
        }
        break;

      default:
        System.out.println("Invalid Action");
        break;
    }

    if (this.isSplitView) {
      // just show image and nothing more
      this.splitRightImage = newImage;
      view.setCurrentImage(model.splitView(this.currentImage,
              this.splitRightImage, this.splitPercentage).getBufferedImage());

    } else {
      this.currentImage = newImage;
      // Update current Image
      view.setCurrentImage(newImage.getBufferedImage());
      // Update histogram
      view.setHistogramImage(this.currentImage.histogram().getBufferedImage());
    }

    // Update saved state
    this.isImageSaved = false;
  }

  @Override
  public void toggleSplitView(boolean state) {
    this.isSplitView = state;
    if (state) {
      this.splitRightImage = this.currentImage;
      view.setActionChoices(this.splitViewChoices);
      view.splitView(true);
    } else {
      view.setActionChoices(this.defaultChoices);
      view.setCurrentImage(this.currentImage.getBufferedImage());
      view.splitView(false);
    }
    System.out.println("state = " + this.isSplitView);
  }

  @Override
  public void splitCurrentImage(int percentage) throws Exception {
    this.splitPercentage = percentage;
    view.setCurrentImage(model.splitView(this.currentImage,
            this.splitRightImage, this.splitPercentage).getBufferedImage());
  }


  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public boolean currentImageSaveState() {
    return this.isImageSaved;
  }
}
