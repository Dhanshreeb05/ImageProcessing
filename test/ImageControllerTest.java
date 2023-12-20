import org.junit.Test;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import controller.ImageController;

import static org.junit.Assert.assertEquals;

/**
 * This is a test class to test the controller for the image processing application.
 * It tests all the functionalities provided by the controller.
 */
public class ImageControllerTest {

  @Test
  public void testGo() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new FileReader("test/testInput.txt");
    //Reader in = new FileReader("test/testInput.txt");


    String expectedOutput = Files.readString(Paths.get("test/expectedOutput.txt"));


    //Reader in = new StringReader("load files/sample_images/manhattan-small.png ms q");
    ImageController controller = new ImageController(in, out);
    controller.runController();


    assertEquals(expectedOutput, out.toString());
  }

  @Test
  public void testExample() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new FileReader("test/testInput2.txt");


    String expectedOutput = Files.readString(Paths.get("test/expectedOutput2.txt"));


    //Reader in = new StringReader("load files/sample_images/manhattan-small.png ms q");
    ImageController controller = new ImageController(in, out);
    controller.runController();


    assertEquals(expectedOutput, out.toString());
  }

  @Test
  public void testScriptCommand() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new FileReader("res/scriptCommand.txt");
    //Reader in = new FileReader("test/testInput.txt");


    String expectedOutput = Files.readString(Paths.get("test/expectedScriptOutput.txt"));


    //Reader in = new StringReader("load files/sample_images/manhattan-small.png ms q");
    ImageController controller = new ImageController(in, out);
    controller.runController();


    assertEquals(expectedOutput, out.toString());
  }

}