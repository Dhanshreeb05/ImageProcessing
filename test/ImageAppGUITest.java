import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import controllergui.ControllerGUI;
import controllergui.JFrameView;
import model.ColorImageNew;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This is a test class to test functionalities of controller using Mockito.
 */
public class ImageAppGUITest {


  private ControllerGUI controller;
  private JFrameView view;

  @Before
  public void setUp() {
    // Create a mock model
    ColorImageNew mockModel = mock(ColorImageNew.class);

    // Create a real view
    view = mock(JFrameView.class);

    // Create the controller with the mock model and real view
    controller = new ControllerGUI(mockModel);
    controller.setView(view);
    when(view.loadAnImage()).thenReturn("files/sample_images/manhattan-small.png");
    controller.loadImage();
  }

  @Test
  public void loadAnImageEmptyPath() {
    when(view.loadAnImage()).thenReturn(" ");
    try {
      controller.loadImage();
      Assert.fail("No Exception thrown for invalid file path");
    } catch (IllegalArgumentException e) {
      System.out.println(e);
    }
  }

  @Test
  public void loadAnImage() throws IOException {
    when(view.loadAnImage()).thenReturn("files/sample_images/manhattan-small.png");
    controller.loadImage();

    verify(view, atLeast(1)).loadAnImage();
  }

  @Test
  public void executeAction() throws Exception {
    Map<String, Integer> resultDict = new HashMap<>();
    resultDict.put("compressValue", 10);
    when(view.executeAction()).thenReturn("Compress");
    when(view.getActionParameters("Compress")).thenReturn(resultDict);
    controller.executeAction();

    verify(view).executeAction();
    verify(view).getActionParameters("Compress");
    verify(view, atLeast(1)).setCurrentImage(ArgumentMatchers.any(BufferedImage.class));
    verify(view, atLeast(1)).setHistogramImage(ArgumentMatchers.any(BufferedImage.class));
  }

  @Test
  public void executeActionGetRed() throws Exception {

    when(view.executeAction()).thenReturn("Get Red Component");
    controller.executeAction();

    verify(view).executeAction();
    verify(view, atLeast(1)).setCurrentImage(ArgumentMatchers.any(BufferedImage.class));
    verify(view, atLeast(1)).setHistogramImage(ArgumentMatchers.any(BufferedImage.class));
  }

  @Test
  public void executeActionGetBlue() throws Exception {

    when(view.executeAction()).thenReturn("Get Blue Component");
    controller.executeAction();

    verify(view).executeAction();
    verify(view, atLeast(1)).setCurrentImage(ArgumentMatchers.any(BufferedImage.class));
    verify(view, atLeast(1)).setHistogramImage(ArgumentMatchers.any(BufferedImage.class));
  }

  @Test
  public void executeActionGetGreen() throws Exception {

    when(view.executeAction()).thenReturn("Get Green Component");
    controller.executeAction();

    verify(view).executeAction();
    verify(view, atLeast(1)).setCurrentImage(ArgumentMatchers.any(BufferedImage.class));
    verify(view, atLeast(1)).setHistogramImage(ArgumentMatchers.any(BufferedImage.class));
  }

  @Test
  public void executeActionFlipVertically() throws Exception {
    when(view.executeAction()).thenReturn("Flip Vertically");
    controller.executeAction();

    verify(view).executeAction();
    verify(view, atLeast(1)).setCurrentImage(ArgumentMatchers.any(BufferedImage.class));
    verify(view, atLeast(1)).setHistogramImage(ArgumentMatchers.any(BufferedImage.class));
  }

  @Test
  public void executeActionFlipHorizontally() throws Exception {
    when(view.executeAction()).thenReturn("Flip Horizontally");
    controller.executeAction();

    verify(view).executeAction();
    verify(view, atLeast(1)).setCurrentImage(ArgumentMatchers.any(BufferedImage.class));
    verify(view, atLeast(1)).setHistogramImage(ArgumentMatchers.any(BufferedImage.class));
  }

  @Test
  public void executeActionLevelsAdjust() throws Exception {
    Map<String, Integer> resultDict = new HashMap<>();
    resultDict.put("blackValue", 1);
    resultDict.put("midValue", 2);
    resultDict.put("whiteValue", 3);
    when(view.executeAction()).thenReturn("Levels Adjust");
    when(view.getActionParameters("Levels Adjust")).thenReturn(resultDict);
    controller.executeAction();

    verify(view).executeAction();
    verify(view).getActionParameters("Levels Adjust");
    verify(view, atLeast(1)).setCurrentImage(ArgumentMatchers.any(BufferedImage.class));
    verify(view, atLeast(1)).setHistogramImage(ArgumentMatchers.any(BufferedImage.class));
  }

  @Test
  public void executeActionLevelsAdjustFail() throws Exception {
    Map<String, Integer> resultDict = new HashMap<>();
    resultDict.put("blackValue", 1);
    resultDict.put("midValue", 30);
    resultDict.put("whiteValue", 3);
    when(view.executeAction()).thenReturn("Levels Adjust");
    when(view.getActionParameters("Levels Adjust")).thenReturn(resultDict);
    controller.executeAction();

    verify(view).executeAction();
    verify(view).getActionParameters("Levels Adjust");
    verify(view).displayPopupMessage(anyString(), anyString());
    verify(view, atLeast(1)).setCurrentImage(ArgumentMatchers.any(BufferedImage.class));
    verify(view, atLeast(1)).setHistogramImage(ArgumentMatchers.any(BufferedImage.class));
  }
}