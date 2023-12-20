README for Assignment 6

We have completed all the functionalities mentioned in the Assignment.
We have modified some of our old code based on the Suggestions in Assignment 5 code walk.


Modifications- 
1. Removed duplicated code as much as possible. - Justification - removing redundant code gives cleaner code which is easier for modifcation and understanding.
2. Added command design pattern to our controller. - Justification - Adding this makes the controller scalable when more and more functionalities are added

Added-
A new view for GUI application
A new controller for the GUI application to communicate between the view and the model.

____________________________________________________________________________________________________________________________


Interfaces in Code.
1. Image
    This is an interface that defines all the public methods that can be performed on an image. It includes functions like blur, shapren, darken, split, etc
2. Pixel
    this is an interface that defines all the public methods that can be performed on a pixel.
3. Controller Helper
4. ImageNew
   This is an interface that defines all the new features to be added.
5. Features
    This interface defines the public methods of the controller that are accessible by the view.
6. IView
    This interface defines the public methods of the view that are accessible by the controller.

Classes in Code.
1. AbstractImage
    This class has implementations for all the methods which are same/similar for Greyscale and Color Image.
2. ColorImage
    This is a class that stores/performs all the functions on a color image. It stores a color image as a matrix/grid of color pixels.
3. GreyscaleImage
    This is a class that stores/performs all the functions on a greyscale image. it stores a greyscale image as a matrix/grid of greyscale pixels.
4. AbstractPixel
    This class has implementations for all the methods which are same/similar for Greyscale and Color Pixel.
5. GreyscalePixel
    This is a class that stores/performs all the functions on a greyscale pixel. a grid of greyscale pixels forms a greyscale image.
    Each greyscale pixel has 2 values -> value and transparency.
6. ColorPixel
    This is a class that stores/performs all the functions on a color pixel. a grid of color pixels forms a color image.
    Each color pixel has 4 values -> red, blue, green and transparency.
7. ControllerException
    This is the class used to handle exceptions that occur in the controller.
    It has functions that print exception messages without crashing the application.
8. ImageApp
    This class is a representation of our image application. It calls the Image Controller.
9. ImageController
    It is the controller that works alongside classes in the view. It mainly takes in the input and
    calls appropriate methods in the model and passes along the output to the view.
10. ControllerException
    This helper class contains logic on handling some exceptions in the controller.
11. ColorImageNew
    This class represent the new color image and has new functionalities.
12. GreyscaleImageNew
    This class represent the new greyscale image and has new functionalities.
13. HistogramImage
     Class to represent histogram.
14. ControllerGUI
    This class represents the controller for GUI application
15. JFrameView
    This class represents the main frame in our GUI window
16. ImageAppGUI
    This class is a representation of our application. It calls the Image Controller for the
    GUI mode or interactive mode based on the conditions.

-------------------------------------------------------------------------------------------------

# Enums in Code.
1. Channel
    This enum is used to store the channels accepted in a greyscale image.
    The values can be red,blue,green.
2. Image Type

-------------------------------------------------------------------------------------------------

# Instructions to start the GUI of the program
1. Run the JAR file of the program
- java -jar Program.jar

-------------------------------------------------------------------------------------------------

# Instructions on running the command scripts:
1.a.  Run the ImageAppGUI main class in the controllergui package from the Command line with arguments "-text" to start
    the app in interactive text mode:
- java -jar Program.jar -text

1.b.  Load the script using the command "run path-to-script". (e.g 'run res/scriptCommand.txt')
- run script-file.txt

    OR

2.   Run the ImageAppGUI main class in the controllergui package from the Command line with arguments "-file path-to-script"
- java -jar Program.jar -file path-of-script-file


3.   Run the ImageAppGUI main class in the controllergui package without any arguments to run the GUI.
-------------------------------------------------------------------------------------------------

Citation
Image used : Blue, Green, and Red Abstract Illustration
Dimensions : 640 x 427
License: Free to use
Owned by: Alexander Grey
URL: https://www.pexels.com/photo/blue-green-and-red-abstract-illustration-1566909/




