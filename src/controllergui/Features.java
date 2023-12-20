package controllergui;

import model.ImageNew;

/**
 * Interface providing features for controller to communicate with view.
 */
public interface Features {

  /**
   * Load an image.
   */
  void loadImage();

  /**
   * This function updates the histogram based on the image.
   *
   * @param image the image whose histogram is used to update.
   */
  void updateHistogram(ImageNew image);

  /**
   * Save the current image to the system.
   *
   * @throws Exception if image is not saved properly
   */
  void saveCurrentImage() throws Exception;

  /**
   * Execute an action on the current image.
   */
  void executeAction() throws Exception;

  /**
   * Toggle the split view on or off.
   *
   * @param state the split-view state
   */
  void toggleSplitView(boolean state);


  /**
   * Split the image.
   *
   * @param percentage the percentage to split at
   */
  void splitCurrentImage(int percentage) throws Exception;

  /**
   * Exits the application.
   *
   * @throws Exception if any system error occurs
   */
  void exitProgram() throws Exception;

  /**
   * Checks if the currentImage is saved on the system.
   *
   * @return true if currentImage is saved, else false
   */
  boolean currentImageSaveState();
}
