package controllergui;


import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.awt.Component;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class represents a JFrameView. it extends JFrame class and implements the IView class.
 */
public class JFrameView extends JFrame implements IView {

  private JButton loadButton;
  private JButton saveButton;
  private JToggleButton splitToggleButton;
  private JButton executeButton;
  private JComboBox<String> cb;
  private JLabel currentImage;
  private JLabel histDisplay;
  private String[] choices;
  private JSlider splitSlider;
  private JScrollPane currentImageScroller;
  private JScrollPane histScroller;


  /**
   * Constructor to construct the view.
   *
   * @param caption the title of the window
   */
  public JFrameView(String caption) {
    super(caption);


    setMinimumSize(new Dimension(1500, 900));

    setSize(new Dimension(1000, 500));
    setLocation(10, 10);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    this.setLayout(new GridBagLayout());

    GridBagConstraints c = new GridBagConstraints();

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 10;
    c.weighty = 10;

    loadButton = new JButton("Load");
    loadButton.setActionCommand("Load Button");
    this.add(loadButton, c);


    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.gridx = 1;
    c.gridy = 0;
    c.weightx = 10;
    c.weighty = 10;
    //save button
    saveButton = new JButton("Save");
    saveButton.setActionCommand("Save Button");
    this.add(saveButton, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.gridx = 2;
    c.gridy = 0;
    c.weightx = 10;
    c.weighty = 10;
    // SPlit Toggle button
    splitToggleButton = new JToggleButton("Split-view OFF");
    this.add(splitToggleButton, c);


    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.gridx = 0;
    c.gridy = 8;
    c.weightx = 10;
    // Slider for split
    splitSlider = new JSlider(1, 99, 50);
    // paint the ticks and tracks
    splitSlider.setPaintTrack(true);
    splitSlider.setPaintTicks(true);
    splitSlider.setPaintLabels(true);
    // set spacing
    splitSlider.setMajorTickSpacing(49);
    splitSlider.setMinorTickSpacing(4);
    this.add(splitSlider, c);


    // slider invisible by default
    splitSlider.setVisible(false);

    c.fill = GridBagConstraints.BOTH;
    c.gridwidth = 2;
    c.gridheight = 6;
    c.gridx = 0;
    c.gridy = 1;

    // Space for current Image
    currentImage = new JLabel(new ImageIcon("res/demo-image.jpg"));
    this.add(currentImage, c);


    // Scrollable pane for current Image
    currentImageScroller = new JScrollPane(
            currentImage,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    currentImageScroller.setMaximumSize(new Dimension(500, 200));
    currentImageScroller.setMinimumSize(new Dimension(500, 200));
    currentImageScroller.setPreferredSize(new Dimension(500, 200));
    this.add(currentImageScroller, c);


    c.fill = GridBagConstraints.CENTER;
    c.gridwidth = 1;
    c.gridheight = 6;
    c.gridx = 2;
    c.gridy = 1;

    // histogram Display
    histDisplay = new JLabel(new ImageIcon("la-output/galaxyHistogram.png"));
    this.add(histDisplay);


    // Scrollable pane for histogram
    histScroller = new JScrollPane(
            histDisplay,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.add(histScroller, c);

    choices = new String[]{ "Get Red Component", "Get Blue Component", "Get Green Component",
        "Flip Vertically", "Flip Horizontally", "Blur Image", "Sharpen Image",
        "Convert to Greyscale Luma", "Convert to Sepia", "Compress", "Color Correct Image",
        "Levels Adjust", "Darken Image", "Brighten Image"
    };

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.gridx = 0;
    c.gridy = 9;
    c.weightx = 10;
    c.weighty = 10;

    cb = new JComboBox<String>(choices);

    cb.setMaximumSize(cb.getPreferredSize());
    cb.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.add(cb, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridwidth = 1;
    c.gridheight = 5;
    c.gridx = 2;
    c.gridy = 9;
    c.weightx = 10;
    c.weighty = 10;

    //Execute Button
    executeButton = new JButton("Execute");
    executeButton.setActionCommand("Execute Button");
    this.add(executeButton, c);

    //Set buttons visibility before load
    saveButton.setVisible(false);
    splitToggleButton.setVisible(false);
    splitSlider.setVisible(false);
    executeButton.setVisible(false);
    histScroller.setVisible(false);
    cb.setVisible(false);
    currentImageScroller.setVisible(false);


    //pack();
    setVisible(true);


  }

  @Override
  public String loadAnImage() {
    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "IMAGE FILES", "jpg", "ppm", "png");
    chooser.setFileFilter(filter);
    int returnVal = chooser.showOpenDialog(null);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      String chosenFilePath = file.getAbsolutePath();

      //Set buttons visibility before load
      saveButton.setVisible(true);
      splitToggleButton.setVisible(true);
      // splitSlider.setVisible(true);
      executeButton.setVisible(true);
      histScroller.setVisible(true);
      cb.setVisible(true);
      currentImageScroller.setVisible(true);

      System.out.println("You chose to open this file: " + chosenFilePath);
      return chosenFilePath;
    } else {
      throw new IllegalStateException("Image not chosen");
    }
  }


  @Override
  public void displayPopupMessage(String message, String title) {
    JOptionPane.showMessageDialog(null, message, title,
            JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void setCurrentImage(BufferedImage loadedImage) {
    this.currentImage.setIcon(new ImageIcon(loadedImage));
  }

  @Override
  public void setHistogramImage(BufferedImage imageHistogram) {
    this.histDisplay.setIcon(new ImageIcon(imageHistogram));
  }


  @Override
  public String saveAnImage() {
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image Files", "jpg", "png", "ppm");
    fileChooser.setFileFilter(filter);

    // Set the default filename
    File defaultFile = new File("image.png");
    fileChooser.setSelectedFile(defaultFile);

    int returnValue = fileChooser.showSaveDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      System.out.println("You chose to save this file: " + selectedFile.getAbsolutePath());
      return selectedFile.getAbsolutePath();
    }

    return null;
  }

  @Override
  public void splitView(boolean state) {
    splitSlider.setVisible(state);
  }


  @Override
  public String executeAction() {
    String action = String.valueOf(this.cb.getSelectedItem());
    System.out.println(action);
    return action;
  }


  @Override
  public Map<String, Integer> getActionParameters(String action) {
    Map<String, Integer> resultDict = new HashMap<>();

    switch (action) {

      case "Darken Image":
        int darkenValue = getInputValue("Enter a value to darken by: ");
        resultDict.put("darkenValue", darkenValue);
        break;

      case "Brighten Image":
        int brightenValue = getInputValue("Get value to Brighten by: ");
        resultDict.put("brightenValue", brightenValue);
        break;

      case "Compress":
        int compressValue = getInputValue("Get value to Compress by: ");
        resultDict.put("compressValue", compressValue);
        break;

      case "Levels Adjust":
        int blackValue;
        int midValue;
        int whiteValue;
        int valid = 0;
        do {
          blackValue = getInputValue("Enter the Black value : ");
          if (blackValue < 0 || blackValue > 255) {
            this.displayPopupMessage("Try again!",
                    "values should be within 0 and 255");
          } else {
            valid = 1;
          }
        }
        while (valid == 0);

        valid = 0;
        do {
          midValue = getInputValue("Enter the Mid value : ");
          if (midValue < 0 || midValue > 255) {
            this.displayPopupMessage("Try again!",
                    "values should be within 0 and 255");
          } else {
            valid = 1;
          }
        }
        while (valid == 0);

        valid = 0;
        do {
          whiteValue = getInputValue("Enter the White value : ");
          if (whiteValue < 0 || whiteValue > 255) {
            this.displayPopupMessage("Try again!",
                    "values should be within 0 and 255");
          } else {
            valid = 1;
          }
        }
        while (valid == 0);

        resultDict.put("blackValue", blackValue);
        resultDict.put("midValue", midValue);
        resultDict.put("whiteValue", whiteValue);
        break;

      default:
        System.out.println("Invalid Case");
        break;
    }

    return resultDict;
  }

  private int getInputValue(String message) {
    int inputValue = 0;
    int valid = 0;
    do {
      try {
        try {
          inputValue = Integer.parseInt(JOptionPane.showInputDialog(this, message));
          System.out.println("inputValue : " + inputValue);
          valid = 1;
        } catch (NumberFormatException e) {
          System.out.println(e.getMessage());
          this.displayPopupMessage("NumberFormatException!", "NumberFormatException!");
          return 0;
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
        this.displayPopupMessage("Try again!", "Error getting input!");
      }
    }
    while (valid == 0);

    return inputValue;
  }


  @Override
  public void setActionChoices(String[] choices) {
    try {
      this.choices = choices;
      this.cb.removeAllItems();
      this.cb.setModel(new DefaultComboBoxModel<>(choices));
    } catch (Exception e) {
      //throw exception for wrong choice
    }

  }


  @Override
  public void addFeatures(Features features) {

    // Load button
    loadButton.addActionListener(evt -> features.loadImage());

    // Save button
    saveButton.addActionListener(evt -> {
      try {
        features.saveCurrentImage();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    //Execute Button
    executeButton.addActionListener(evt -> {
      try {
        features.executeAction();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    // Slider Change

    this.splitSlider.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
          int value = source.getValue();

          try {
            features.splitCurrentImage(value);
          } catch (Exception ex) {
            throw new RuntimeException(ex);
          }

          System.out.println("Slider value: " + value);
        }
      }
    });


    // Split Toggle
    ItemListener itemListener = new ItemListener() {

      /**
       * method is invoked automatically whenever you
       * click or un-click on the Button.
       *
       * @param itemEvent the event to be processed
       */

      public void itemStateChanged(ItemEvent itemEvent) {

        int state = itemEvent.getStateChange();

        if (state == ItemEvent.SELECTED) {
          features.toggleSplitView(true);
          splitToggleButton.setText("Split-view ON");
        } else {
          features.toggleSplitView(false);
          splitToggleButton.setText("Split-view OFF");
        }
      }
    };

    splitToggleButton.addItemListener(itemListener);

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        boolean imagesaveState = features.currentImageSaveState();
        if (imagesaveState) {
          try {
            features.exitProgram();
          } catch (Exception ex) {
            throw new RuntimeException(ex);
          }
        } else {

          int confirmed = JOptionPane.showConfirmDialog(null,
                  "Are you sure you want to exit the program without saving to disk?",
                  "Exit Warning!",
                  JOptionPane.YES_NO_OPTION);

          if (confirmed == JOptionPane.YES_OPTION) {
            try {
              features.exitProgram();
            } catch (Exception ex) {
              throw new RuntimeException(ex);
            }
          }
        }

      }
    });

  }


}
