package controllergui;


import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * The interface for our view class.
 */
public interface IView {

  /**
   * Allow user to select an image and return its path.
   *
   * @return the path of the selected Image
   */
  String loadAnImage();

  /**
   * open a simple popup window and display a message.
   *
   * @param message the message to display
   */
  void displayPopupMessage(String message, String title);


  /**
   * Set the given image to the current image window.
   *
   * @param loadedImage the image to set
   */
  void setCurrentImage(BufferedImage loadedImage);

  /**
   * Set the given histogram image to the histogram space.
   *
   * @param imageHistogram the histogram image
   */
  void setHistogramImage(BufferedImage imageHistogram);


  /**
   * Allow the user to select the path and filename to save the image.
   *
   * @return the path where to save the image
   */
  String saveAnImage();


  /**
   * Set the popup for split view.
   */
  void splitView(boolean state);

  /**
   * Returns the action that is to be executed on the image.
   *
   * @return the action name
   */
  String executeAction();

  /**
   * Get the user input on the values of the parameters that are to be passed to the action that
   * is being performed.
   *
   * @param action the action that is being performed
   * @return the parameters to be passed to the action
   */
  Map<String, Integer> getActionParameters(String action);

  /**
   * Set the valid choices that can be performed.
   *
   * @param choices the choices that are valid
   */
  void setActionChoices(String[] choices);

  /**
   * The features of the controller that can be used by the view.
   *
   * @param features the features that the view can call
   */
  void addFeatures(Features features);
}














