import org.junit.Assert;
import org.junit.Test;

import controllergui.ControllerGUI;
import model.ColorImageNew;

import static org.junit.Assert.assertEquals;

/**
 * This is a class to test Image App controller using Mock View.
 */
public class ImageAppGUITestMockView {
  @Test
  public void testLoadImage() throws Exception {
    StringBuffer out = new StringBuffer();

    String expectedOutput = "Adding Features\n" +
            "Loading an Image\n" +
            "Setting Current Working Image\n" +
            "Setting Histogram Image\n";
    ColorImageNew mockModel = new ColorImageNew();

    MockView view = new MockView(out);

    ControllerGUI controller = new ControllerGUI(mockModel);
    controller.setView(view);
    controller.loadImage();
    assertEquals(expectedOutput, out.toString());
  }

  @Test
  public void testExecuteWithoutLoad() throws Exception {
    StringBuffer out = new StringBuffer();

    String expectedOutput = "Adding Features\n" +
            "Loading an Image\n" +
            "Setting Current Working Image\n" +
            "Setting Histogram Image\n";
    ColorImageNew mockModel = new ColorImageNew();

    MockView view = new MockView(out);

    ControllerGUI controller = new ControllerGUI(mockModel);
    controller.setView(view);
    try {
      controller.executeAction();
      Assert.fail("No Exception thrown when action is executed without loading an image.");
    } catch (Exception e) {
      //exception because action executed without an image
    }
  }

  @Test
  public void testExecutingAction() throws Exception {
    StringBuffer out = new StringBuffer();

    String expectedOutput = "Adding Features\n" +
            "Loading an Image\n" +
            "Setting Current Working Image\n" +
            "Setting Histogram Image\n" +
            "Executing Action\n" +
            "Get Action Params\n" +
            "Set Action Choices\n" +
            "Setting Current Working Image\n" +
            "Setting Histogram Image\n";
    ColorImageNew mockModel = new ColorImageNew();

    MockView view = new MockView(out);

    ControllerGUI controller = new ControllerGUI(mockModel);
    controller.setView(view);
    controller.loadImage();
    controller.executeAction();
    assertEquals(expectedOutput, out.toString());
  }

  @Test
  public void testDisplayPopup() throws Exception {
    StringBuffer out = new StringBuffer();

    String expectedOutput = "Adding Features\n" +
            "Loading an Image\n" +
            "Setting Current Working Image\n" +
            "Setting Histogram Image\n" +
            "Setting Histogram Image\n";
    ColorImageNew mockModel = new ColorImageNew();

    MockView view = new MockView(out);

    ControllerGUI controller = new ControllerGUI(mockModel);
    controller.setView(view);
    controller.loadImage();
    controller.updateHistogram(new ColorImageNew());
    assertEquals(expectedOutput, out.toString());
  }

  @Test
  public void saveImageRight() throws Exception {
    StringBuffer out = new StringBuffer();

    String expectedOutput = "Adding Features\n" +
            "Loading an Image\n" +
            "Setting Current Working Image\n" +
            "Setting Histogram Image\n" +
            "Saving Image\n";
    ColorImageNew mockModel = new ColorImageNew();

    MockView view = new MockView(out);

    ControllerGUI controller = new ControllerGUI(mockModel);
    controller.setView(view);
    controller.loadImage();
    controller.saveCurrentImage();
    assertEquals(expectedOutput, out.toString());
  }

  @Test
  public void saveImageWithoutLoad() throws Exception {
    StringBuffer out = new StringBuffer();

    String expectedOutput = "Adding Features\n" +
            "Loading an Image\n" +
            "Setting Current Working Image\n" +
            "Setting Histogram Image\n" +
            "Saving Image\n";
    ColorImageNew mockModel = new ColorImageNew();

    MockView view = new MockView(out);

    ControllerGUI controller = new ControllerGUI(mockModel);
    controller.setView(view);
    try {
      controller.saveCurrentImage();
      Assert.fail();
    } catch (Exception e) {
      //exception thrown as save called before load
    }
  }

  @Test
  public void testToggleSplitView() throws Exception {
    StringBuffer out = new StringBuffer();

    String expectedOutput = "Adding Features\n" +
            "Loading an Image\n" +
            "Setting Current Working Image\n" +
            "Setting Histogram Image\n" +
            "Set Action Choices\n" +
            "Split view turned ON\n" +
            "Set Action Choices\n" +
            "Setting Current Working Image\n" +
            "Split view turned OFF\n";
    ColorImageNew mockModel = new ColorImageNew();

    MockView view = new MockView(out);

    ControllerGUI controller = new ControllerGUI(mockModel);
    controller.setView(view);
    controller.loadImage();
    controller.toggleSplitView(true);
    controller.toggleSplitView(false);
    assertEquals(expectedOutput, out.toString());
  }

  @Test
  public void testSave() throws Exception {
    StringBuffer out = new StringBuffer();

    String expectedOutput = "Adding Features\n" +
            "Loading an Image\n" +
            "Setting Current Working Image\n" +
            "Setting Histogram Image\n" +
            "Saving Image\n";
    ColorImageNew mockModel = new ColorImageNew();

    MockView view = new MockView(out);

    ControllerGUI controller = new ControllerGUI(mockModel);
    controller.setView(view);
    controller.loadImage();
    Assert.assertFalse(controller.currentImageSaveState());
    controller.saveCurrentImage();
    Assert.assertTrue(controller.currentImageSaveState());
    assertEquals(expectedOutput, out.toString());
  }
}
