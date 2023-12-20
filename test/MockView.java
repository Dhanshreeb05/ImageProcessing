import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import controllergui.Features;
import controllergui.IView;

/**
 * This is a mock view class for testing Controller.
 */
public class MockView implements IView {

  Appendable ap;

  MockView(Appendable ap) {
    this.ap = ap;
  }

  @Override
  public String loadAnImage() {
    try {
      ap.append("Loading an Image\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return "files/sample_images/manhattan-small.png";
  }

  @Override
  public void displayPopupMessage(String message, String title) {
    try {
      ap.append("Display Popup for Different Messages\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void setCurrentImage(BufferedImage loadedImage) {
    try {
      ap.append("Setting Current Working Image\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void setHistogramImage(BufferedImage imageHistogram) {
    try {
      ap.append("Setting Histogram Image\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String saveAnImage() {
    try {
      ap.append("Saving Image\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return "files/sample_images/manhattan-small.png";
  }

  @Override
  public void splitView(boolean state) {
    try {
      ap.append("Split view turned " + (state ? "ON\n" : "OFF\n"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String executeAction() {
    try {
      ap.append("Executing Action\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return "Darken Image";
  }

  @Override
  public Map<String, Integer> getActionParameters(String action) {
    try {
      ap.append("Get Action Params\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Map<String, Integer> resultDict = new HashMap<>();
    resultDict.put("darkenValue", 50);
    return resultDict;
  }

  @Override
  public void setActionChoices(String[] choices) {
    try {
      ap.append("Set Action Choices\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void addFeatures(Features features) {
    try {
      ap.append("Adding Features\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
