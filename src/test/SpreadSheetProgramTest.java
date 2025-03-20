import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import spreadsheet.SpreadSheetProgram;

import static org.junit.Assert.assertTrue;

/**
 * Test class for SpreadSheetProgram.
 */
public class SpreadSheetProgramTest {

  private final InputStream originalIn = System.in;
  private final PrintStream originalOut = System.out;

  private ByteArrayOutputStream outContent;
  private ByteArrayInputStream inContent;

  @Before
  public void setUpStreams() {
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void restoreStreams() {
    System.setIn(originalIn);
    System.setOut(originalOut);
  }

  @Test
  public void testMainMethodComponentCreation() {
    String input = "q\n";
    inContent = new ByteArrayInputStream(input.getBytes());
    System.setIn(inContent);

    SpreadSheetProgram.main(new String[]{});
    String output = outContent.toString();
    assertTrue("Program should start and exit without errors",
            output.contains("Thank you for using this program!"));
  }

  @Test
  public void testMainMethodControllerExecution() {
    String input = "q\n";
    inContent = new ByteArrayInputStream(input.getBytes());
    System.setIn(inContent);

    SpreadSheetProgram.main(new String[]{});

    String output = outContent.toString();
    assertTrue("Output should contain welcome message",
            output.contains("Welcome to the spreadsheet program!"));
    assertTrue("Output should contain farewell message",
            output.contains("Thank you for using this program!"));
  }

  @Test
  public void testMainMethodWithCommands() {
    String input = "assign-value A 1 42.5\nprint-value A 1\nq\n";
    inContent = new ByteArrayInputStream(input.getBytes());
    System.setIn(inContent);

    SpreadSheetProgram.main(new String[]{});

    String output = outContent.toString();
    assertTrue("Output should show the value was set and retrieved",
            output.contains("Value: 42.5"));
  }
}
